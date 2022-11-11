//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.capability.inputstate;

import java.util.*;
import mods.flammpfeil.slashblade.util.*;
import net.minecraft.entity.*;

public interface IInputState
{
    EnumSet<InputCommand> getCommands();
    
    default EnumSet<InputCommand> getCommands(final LivingEntity owner) {
        final EnumSet<InputCommand> commands = this.getCommands().clone();
        if (owner.isOnGround()) {
            commands.add(InputCommand.ON_GROUND);
        }
        else {
            commands.add(InputCommand.ON_AIR);
        }
        return commands;
    }
}
