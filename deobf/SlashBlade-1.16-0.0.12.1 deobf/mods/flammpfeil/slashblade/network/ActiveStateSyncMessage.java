//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.network;

import net.minecraft.nbt.*;
import net.minecraft.network.*;
import java.util.function.*;
import net.minecraftforge.fml.network.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import mods.flammpfeil.slashblade.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;

public class ActiveStateSyncMessage
{
    public CompoundNBT activeTag;
    public int id;
    
    public static ActiveStateSyncMessage decode(final PacketBuffer buf) {
        final ActiveStateSyncMessage msg = new ActiveStateSyncMessage();
        msg.id = buf.readInt();
        msg.activeTag = buf.readNbt();
        return msg;
    }
    
    public static void encode(final ActiveStateSyncMessage msg, final PacketBuffer buf) {
        buf.writeInt(msg.id);
        buf.writeNbt(msg.activeTag);
    }
    
    public static void handle(final ActiveStateSyncMessage msg, final Supplier<NetworkEvent.Context> ctx) {
        ServerPlayerEntity sender;
        Entity target;
        ItemStack stack;
        ctx.get().enqueueWork(() -> {
            if (!msg.activeTag.hasUUID("BladeUniqueId")) {
                return;
            }
            else {
                sender = ctx.get().getSender();
                target = Minecraft.getInstance().level.getEntity(msg.id);
                if (target instanceof LivingEntity) {
                    stack = ((LivingEntity)target).getItemInHand(Hand.MAIN_HAND);
                    if (!stack.isEmpty()) {
                        if (!(!(stack.getItem() instanceof ItemSlashBlade))) {
                            stack.getCapability(ItemSlashBlade.BLADESTATE).filter(state -> state.getUniqueId().equals(msg.activeTag.getUUID("BladeUniqueId"))).ifPresent(state -> state.setActiveState(msg.activeTag));
                        }
                    }
                }
                return;
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
