//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.capability.inputstate;

import java.util.*;
import mods.flammpfeil.slashblade.util.*;

public class InputState implements IInputState
{
    EnumSet<InputCommand> commands;
    
    public InputState() {
        this.commands = EnumSet.noneOf(InputCommand.class);
    }
    
    public EnumSet<InputCommand> getCommands() {
        return this.commands;
    }
}
