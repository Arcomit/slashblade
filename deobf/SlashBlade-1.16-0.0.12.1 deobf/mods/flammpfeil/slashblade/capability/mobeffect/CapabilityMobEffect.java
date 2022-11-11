//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.capability.mobeffect;

import net.minecraftforge.common.capabilities.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import mods.flammpfeil.slashblade.util.*;
import javax.annotation.*;

public class CapabilityMobEffect
{
    @CapabilityInject(IMobEffectState.class)
    public static Capability<IMobEffectState> MOB_EFFECT;
    
    public static void register() {
        CapabilityManager.INSTANCE.register((Class)IMobEffectState.class, (Capability.IStorage)new Capability.IStorage<IMobEffectState>() {
            @Nullable
            public INBT writeNBT(final Capability<IMobEffectState> capability, final IMobEffectState instance, final Direction side) {
                final CompoundNBT nbt = new CompoundNBT();
                NBTHelper.getNBTCoupler(nbt).put("StunTimeout", instance.getStunTimeOut()).put("FreezeTimeout", instance.getFreezeTimeOut());
                return (INBT)nbt;
            }
            
            public void readNBT(final Capability<IMobEffectState> capability, final IMobEffectState instance, final Direction side, final INBT nbt) {
                NBTHelper.getNBTCoupler((CompoundNBT)nbt).get("StunTimeout", instance::setStunTimeOut, new Long[0]).get("FreezeTimeout", instance::setFreezeTimeOut, new Long[0]);
            }
        }, () -> new MobEffectState());
    }
    
    static {
        CapabilityMobEffect.MOB_EFFECT = null;
    }
}
