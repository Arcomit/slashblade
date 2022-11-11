//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.capability.concentrationrank;

import net.minecraftforge.common.capabilities.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import mods.flammpfeil.slashblade.util.*;
import javax.annotation.*;

public class CapabilityConcentrationRank
{
    @CapabilityInject(IConcentrationRank.class)
    public static Capability<IConcentrationRank> RANK_POINT;
    
    public static void register() {
        CapabilityManager.INSTANCE.register((Class)IConcentrationRank.class, (Capability.IStorage)new Capability.IStorage<IConcentrationRank>() {
            @Nullable
            public INBT writeNBT(final Capability<IConcentrationRank> capability, final IConcentrationRank instance, final Direction side) {
                return (INBT)NBTHelper.getNBTCoupler(new CompoundNBT()).put("rawPoint", instance.getRawRankPoint()).put("lastupdate", instance.getLastUpdate()).getRawCompound();
            }
            
            public void readNBT(final Capability<IConcentrationRank> capability, final IConcentrationRank instance, final Direction side, final INBT nbt) {
                NBTHelper.getNBTCoupler((CompoundNBT)nbt).get("rawPoint", instance::setRawRankPoint, new Long[0]).get("lastupdate", instance::setLastUpdte, new Long[0]);
            }
        }, () -> new ConcentrationRank());
    }
    
    static {
        CapabilityConcentrationRank.RANK_POINT = null;
    }
}
