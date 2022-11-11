//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.capability.mobeffect;

import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;
import net.minecraft.util.*;
import javax.annotation.*;
import net.minecraft.nbt.*;

public class MobEffectCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT>
{
    @CapabilityInject(IMobEffectState.class)
    public static Capability<IMobEffectState> MOB_EFFECT;
    protected LazyOptional<IMobEffectState> state;
    static final String tagState = "MobEffect";
    
    public MobEffectCapabilityProvider() {
        this.state = (LazyOptional<IMobEffectState>)LazyOptional.of(MobEffectCapabilityProvider.MOB_EFFECT::getDefaultInstance);
    }
    
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side) {
        return (LazyOptional<T>)MobEffectCapabilityProvider.MOB_EFFECT.orEmpty((Capability)cap, (LazyOptional)this.state);
    }
    
    public CompoundNBT serializeNBT() {
        final CompoundNBT baseTag = new CompoundNBT();
        this.state.ifPresent(state -> baseTag.put("MobEffect", MobEffectCapabilityProvider.MOB_EFFECT.writeNBT((Object)state, (Direction)null)));
        return baseTag;
    }
    
    public void deserializeNBT(final CompoundNBT baseTag) {
        this.state.ifPresent(state -> MobEffectCapabilityProvider.MOB_EFFECT.readNBT((Object)state, (Direction)null, (INBT)baseTag.getCompound("MobEffect")));
    }
    
    static {
        MobEffectCapabilityProvider.MOB_EFFECT = null;
    }
}
