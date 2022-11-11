//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.ability;

import net.minecraftforge.common.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraft.entity.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.event.entity.living.*;
import mods.flammpfeil.slashblade.item.*;
import mods.flammpfeil.slashblade.capability.mobeffect.*;
import net.minecraft.potion.*;
import java.util.*;

public class Untouchable
{
    static final int JUMP_TICKS = 10;
    
    public static Untouchable getInstance() {
        return SingletonHolder.instance;
    }
    
    private Untouchable() {
    }
    
    public void register() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public static void setUntouchable(final LivingEntity entity, final int ticks) {
        entity.getCapability((Capability)CapabilityMobEffect.MOB_EFFECT).ifPresent(ef -> {
            ef.setManagedUntouchable(entity.level.getGameTime(), ticks);
            ef.storeEffects(entity.getActiveEffectsMap().keySet());
            ef.storeHealth(entity.getHealth());
        });
    }
    
    private boolean checkUntouchable(final LivingEntity entity) {
        final Optional<Boolean> isUntouchable = (Optional<Boolean>)entity.getCapability((Capability)CapabilityMobEffect.MOB_EFFECT).map(ef -> ef.isUntouchable(entity.getCommandSenderWorld().getGameTime()));
        return isUntouchable.orElseGet(() -> false);
    }
    
    private void doWitchTime(final Entity entity) {
        if (entity == null) {
            return;
        }
        if (!(entity instanceof LivingEntity)) {
            return;
        }
        StunManager.setStun((LivingEntity)entity);
    }
    
    @SubscribeEvent
    public void onLivingHurt(final LivingHurtEvent event) {
        if (this.checkUntouchable(event.getEntityLiving())) {
            event.setCanceled(true);
            this.doWitchTime(event.getSource().getEntity());
        }
    }
    
    @SubscribeEvent
    public void onLivingDamage(final LivingDamageEvent event) {
        if (this.checkUntouchable(event.getEntityLiving())) {
            event.setCanceled(true);
            this.doWitchTime(event.getSource().getEntity());
        }
    }
    
    @SubscribeEvent
    public void onLivingAttack(final LivingAttackEvent event) {
        if (this.checkUntouchable(event.getEntityLiving())) {
            event.setCanceled(true);
            this.doWitchTime(event.getSource().getEntity());
        }
    }
    
    @SubscribeEvent
    public void onLivingDeath(final LivingDeathEvent event) {
        if (this.checkUntouchable(event.getEntityLiving())) {
            event.setCanceled(true);
            this.doWitchTime(event.getSource().getEntity());
            final LivingEntity entity = event.getEntityLiving();
            entity.getCapability((Capability)CapabilityMobEffect.MOB_EFFECT).ifPresent(ef -> {
                if (ef.hasUntouchableWorked()) {
                    entity.getActiveEffectsMap().keySet().stream().filter(p -> !ef.getEffectSet().contains(p) && !p.isBeneficial()).forEach(p -> entity.removeEffect(p));
                    final float storedHealth = ef.getStoredHealth();
                    if (ef.getStoredHealth() < storedHealth) {
                        entity.setHealth(ef.getStoredHealth());
                    }
                }
            });
        }
    }
    
    @SubscribeEvent
    public void onLivingTicks(final LivingEvent.LivingUpdateEvent event) {
        final LivingEntity entity = event.getEntityLiving();
        if (entity.level.isClientSide) {
            return;
        }
        entity.getCapability((Capability)CapabilityMobEffect.MOB_EFFECT).ifPresent(ef -> {
            if (ef.hasUntouchableWorked()) {
                ef.setUntouchableWorked(false);
                entity.getActiveEffectsMap().keySet().stream().filter(p -> !ef.getEffectSet().contains(p) && !p.isBeneficial()).forEach(p -> entity.removeEffect(p));
                final float storedHealth = ef.getStoredHealth();
                if (ef.getStoredHealth() < storedHealth) {
                    entity.setHealth(ef.getStoredHealth());
                }
            }
        });
    }
    
    @SubscribeEvent
    public void onPlayerJump(final LivingEvent.LivingJumpEvent event) {
        if (!event.getEntityLiving().getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).isPresent()) {
            return;
        }
        setUntouchable(event.getEntityLiving(), 10);
    }
    
    private static final class SingletonHolder
    {
        private static final Untouchable instance;
        
        static {
            instance = new Untouchable(null);
        }
    }
}
