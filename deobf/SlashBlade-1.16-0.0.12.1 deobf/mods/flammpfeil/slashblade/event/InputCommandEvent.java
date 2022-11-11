//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.event;

import net.minecraftforge.eventbus.api.*;
import net.minecraft.entity.player.*;
import java.util.*;
import mods.flammpfeil.slashblade.util.*;
import net.minecraftforge.common.*;

public class InputCommandEvent extends Event
{
    ServerPlayerEntity player;
    EnumSet<InputCommand> old;
    EnumSet<InputCommand> current;
    
    public InputCommandEvent(final ServerPlayerEntity player, final EnumSet<InputCommand> old, final EnumSet<InputCommand> current) {
        this.player = player;
        this.old = old;
        this.current = current;
    }
    
    public ServerPlayerEntity getPlayer() {
        return this.player;
    }
    
    public EnumSet<InputCommand> getOld() {
        return this.old;
    }
    
    public EnumSet<InputCommand> getCurrent() {
        return this.current;
    }
    
    public static InputCommandEvent onInputChange(final ServerPlayerEntity player, final EnumSet<InputCommand> old, final EnumSet<InputCommand> current) {
        final InputCommandEvent event = new InputCommandEvent(player, old, current);
        MinecraftForge.EVENT_BUS.post((Event)event);
        return event;
    }
}
