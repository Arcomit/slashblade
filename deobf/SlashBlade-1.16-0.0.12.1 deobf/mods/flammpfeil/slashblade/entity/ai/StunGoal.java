//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.entity.ai;

import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.*;
import java.util.*;
import mods.flammpfeil.slashblade.capability.mobeffect.*;

public class StunGoal extends Goal
{
    private final CreatureEntity entity;
    
    public StunGoal(final CreatureEntity creature) {
        this.entity = creature;
        this.setFlags((EnumSet)EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK, Goal.Flag.TARGET));
    }
    
    public boolean canUse() {
        final boolean onStun = this.entity.getCapability(CapabilityMobEffect.MOB_EFFECT).filter(state -> state.isStun(this.entity.level.getGameTime())).isPresent();
        return onStun;
    }
    
    public void stop() {
        this.entity.getCapability(CapabilityMobEffect.MOB_EFFECT).ifPresent(state -> state.clearStunTimeOut());
    }
}
