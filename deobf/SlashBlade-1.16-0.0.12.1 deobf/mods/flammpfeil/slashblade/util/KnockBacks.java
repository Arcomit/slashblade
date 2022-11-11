//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.util;

import java.util.function.*;
import net.minecraft.entity.*;
import mods.flammpfeil.slashblade.event.*;

public enum KnockBacks
{
    cancel(e -> KnockBackHandler.setFactor(e, 0.0, 0.0, 0.0)), 
    toss(e -> KnockBackHandler.setVertical(e, 0.75)), 
    meteor(e -> KnockBackHandler.setVertical(e, -5.5)), 
    smash(e -> KnockBackHandler.setSmash(e, 1.5));
    
    public final Consumer<LivingEntity> action;
    
    private KnockBacks(final Consumer<LivingEntity> action) {
        this.action = action;
    }
}
