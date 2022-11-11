//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.capability.inputstate;

import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;
import net.minecraft.util.*;
import javax.annotation.*;
import net.minecraft.nbt.*;

public class InputStateCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT>
{
    @CapabilityInject(IInputState.class)
    public static Capability<IInputState> INPUT_STATE;
    protected LazyOptional<IInputState> state;
    static final String tagState = "InputState";
    
    public InputStateCapabilityProvider() {
        this.state = (LazyOptional<IInputState>)LazyOptional.of(InputStateCapabilityProvider.INPUT_STATE::getDefaultInstance);
    }
    
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side) {
        return (LazyOptional<T>)InputStateCapabilityProvider.INPUT_STATE.orEmpty((Capability)cap, (LazyOptional)this.state);
    }
    
    public CompoundNBT serializeNBT() {
        final CompoundNBT baseTag = new CompoundNBT();
        this.state.ifPresent(state -> baseTag.put("InputState", InputStateCapabilityProvider.INPUT_STATE.writeNBT((Object)state, (Direction)null)));
        return baseTag;
    }
    
    public void deserializeNBT(final CompoundNBT baseTag) {
        this.state.ifPresent(state -> InputStateCapabilityProvider.INPUT_STATE.readNBT((Object)state, (Direction)null, (INBT)baseTag.getCompound("InputState")));
    }
    
    static {
        InputStateCapabilityProvider.INPUT_STATE = null;
    }
}
