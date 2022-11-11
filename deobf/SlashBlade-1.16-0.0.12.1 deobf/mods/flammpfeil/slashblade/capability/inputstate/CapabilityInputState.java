//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.capability.inputstate;

import net.minecraftforge.common.capabilities.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import javax.annotation.*;
import mods.flammpfeil.slashblade.util.*;
import java.util.*;

public class CapabilityInputState
{
    @CapabilityInject(IInputState.class)
    public static Capability<IInputState> INPUT_STATE;
    
    public static void register() {
        CapabilityManager.INSTANCE.register((Class)IInputState.class, (Capability.IStorage)new Capability.IStorage<IInputState>() {
            static final String KEY = "Command";
            
            @Nullable
            public INBT writeNBT(final Capability<IInputState> capability, final IInputState instance, final Direction side) {
                final CompoundNBT nbt = new CompoundNBT();
                nbt.putInt("Command", EnumSetConverter.convertToInt(instance.getCommands()));
                return (INBT)nbt;
            }
            
            public void readNBT(final Capability<IInputState> capability, final IInputState instance, final Direction side, final INBT nbt) {
                final CompoundNBT tags = (CompoundNBT)nbt;
                instance.getCommands().addAll((Collection<?>)EnumSetConverter.convertToEnumSet(InputCommand.class, InputCommand.values(), tags.getInt("Command")));
            }
        }, () -> new InputState());
    }
    
    static {
        CapabilityInputState.INPUT_STATE = null;
    }
}
