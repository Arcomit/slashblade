//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.ability;

import mods.flammpfeil.slashblade.entity.ai.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraft.util.math.vector.*;
import net.minecraftforge.event.entity.*;
import net.minecraft.entity.*;
import mods.flammpfeil.slashblade.capability.mobeffect.*;

public class StunManager
{
    static final int DEFAULT_STUN_TICKS = 10;
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onEntityJoinWorldEvent(final EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof CreatureEntity)) {
            return;
        }
        final CreatureEntity entity = (CreatureEntity)event.getEntity();
        entity.goalSelector.addGoal(-1, (Goal)new StunGoal(entity));
    }
    
    @SubscribeEvent
    public void onEntityLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        final LivingEntity target = event.getEntityLiving();
        if (!(target instanceof CreatureEntity)) {
            return;
        }
        if (target == null) {
            return;
        }
        if (target.level == null) {
            return;
        }
        final boolean onStun = target.getCapability((Capability)CapabilityMobEffect.MOB_EFFECT).filter(state -> state.isStun(target.level.getGameTime())).isPresent();
        if (onStun) {
            final Vector3d motion = target.getDeltaMovement();
            if (5.0f < target.fallDistance) {
                target.setDeltaMovement(motion.x, motion.y - 2.0, motion.z);
            }
            else if (motion.y < 0.0) {
                target.setDeltaMovement(motion.x, motion.y * 0.25, motion.z);
            }
        }
    }
    
    public static void setStun(final LivingEntity target, final LivingEntity attacker) {
        setStun(target);
    }
    
    public static void setStun(final LivingEntity target) {
        setStun(target, 10L);
    }
    
    public static void setStun(final LivingEntity target, final long duration) {
        if (!(target instanceof CreatureEntity)) {
            return;
        }
        if (target.level == null) {
            return;
        }
        target.getCapability((Capability)CapabilityMobEffect.MOB_EFFECT).ifPresent(state -> state.setManagedStun(target.level.getGameTime(), duration));
    }
    
    public static void removeStun(final LivingEntity target) {
        if (target.level == null) {
            return;
        }
        if (!(target instanceof LivingEntity)) {
            return;
        }
        target.getCapability((Capability)CapabilityMobEffect.MOB_EFFECT).ifPresent(state -> {
            state.clearStunTimeOut();
            state.clearFreezeTimeOut();
        });
    }
    
    @SubscribeEvent
    public void onEntityCanUpdate(final EntityEvent.CanUpdate event) {
        if (event.isCanceled()) {
            return;
        }
        final Entity target = event.getEntity();
        if (target == null) {
            return;
        }
        if (target.level == null) {
            return;
        }
        final boolean onFreeze = target.getCapability((Capability)CapabilityMobEffect.MOB_EFFECT).filter(state -> state.isFreeze(target.level.getGameTime())).isPresent();
        if (onFreeze) {
            event.setCanUpdate(false);
        }
    }
    
    public static void setFreeze(final LivingEntity target, final long duration) {
        if (target.level == null) {
            return;
        }
        if (!(target instanceof LivingEntity)) {
            return;
        }
        target.getCapability((Capability)CapabilityMobEffect.MOB_EFFECT).ifPresent(state -> state.setManagedFreeze(target.level.getGameTime(), duration));
    }
}
