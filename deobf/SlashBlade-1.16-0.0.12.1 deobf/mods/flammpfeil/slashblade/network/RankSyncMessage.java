//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.network;

import net.minecraft.network.*;
import net.minecraftforge.fml.network.*;
import java.util.function.*;
import net.minecraftforge.fml.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.api.distmarker.*;
import mods.flammpfeil.slashblade.capability.concentrationrank.*;
import java.util.concurrent.*;

public class RankSyncMessage
{
    public long rawPoint;
    
    public static RankSyncMessage decode(final PacketBuffer buf) {
        final RankSyncMessage msg = new RankSyncMessage();
        msg.rawPoint = buf.readLong();
        return msg;
    }
    
    public static void encode(final RankSyncMessage msg, final PacketBuffer buf) {
        buf.writeLong(msg.rawPoint);
    }
    
    public static void handle(final RankSyncMessage msg, final Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
        if (ctx.get().getDirection() != NetworkDirection.PLAY_TO_CLIENT) {
            return;
        }
        final Consumer<Long> handler = (Consumer<Long>)DistExecutor.callWhenOn(Dist.CLIENT, () -> () -> RankSyncMessage::setPoint);
        if (handler != null) {
            ctx.get().enqueueWork(() -> handler.accept(msg.rawPoint));
        }
    }
    
    @OnlyIn(Dist.CLIENT)
    public static void setPoint(final long point) {
        final PlayerEntity pl = (PlayerEntity)Minecraft.getInstance().player;
        pl.getCapability(CapabilityConcentrationRank.RANK_POINT).ifPresent(cr -> {
            final long time = pl.level.getGameTime();
            final IConcentrationRank.ConcentrationRanks oldRank = cr.getRank(time);
            cr.setRawRankPoint(point);
            cr.setLastUpdte(time);
            if (oldRank.level < cr.getRank(time).level) {
                cr.setLastRankRise(time);
            }
        });
    }
}
