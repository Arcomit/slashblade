//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.util;

import java.util.function.*;
import net.minecraft.util.math.vector.*;
import mods.flammpfeil.slashblade.item.*;
import mods.flammpfeil.slashblade.*;
import net.minecraft.entity.*;
import com.google.common.collect.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.*;
import java.util.*;
import mods.flammpfeil.slashblade.entity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import mods.flammpfeil.slashblade.ability.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;
import mods.flammpfeil.slashblade.capability.concentrationrank.*;

public class AttackManager
{
    public static void areaAttack(final LivingEntity playerIn, final Consumer<LivingEntity> beforeHit) {
        areaAttack(playerIn, beforeHit, 1.0f, true, true, false);
    }
    
    public static EntitySlashEffect doSlash(final LivingEntity playerIn, final float roll) {
        return doSlash(playerIn, roll, false);
    }
    
    public static EntitySlashEffect doSlash(final LivingEntity playerIn, final float roll, final boolean mute) {
        return doSlash(playerIn, roll, mute, false);
    }
    
    public static EntitySlashEffect doSlash(final LivingEntity playerIn, final float roll, final boolean mute, final boolean critical) {
        return doSlash(playerIn, roll, mute, critical, 1.0);
    }
    
    public static EntitySlashEffect doSlash(final LivingEntity playerIn, final float roll, final boolean mute, final boolean critical, final double damage) {
        return doSlash(playerIn, roll, Vector3d.ZERO, mute, critical, damage);
    }
    
    public static EntitySlashEffect doSlash(final LivingEntity playerIn, final float roll, final Vector3d centerOffset, final boolean mute, final boolean critical, final double damage) {
        return doSlash(playerIn, roll, centerOffset, mute, critical, damage, KnockBacks.cancel);
    }
    
    public static EntitySlashEffect doSlash(final LivingEntity playerIn, final float roll, final Vector3d centerOffset, final boolean mute, final boolean critical, final double damage, final KnockBacks knockback) {
        final int colorCode = playerIn.getMainHandItem().getCapability(ItemSlashBlade.BLADESTATE).map(state -> state.getColorCode()).orElseGet(() -> 16777215);
        return doSlash(playerIn, roll, colorCode, centerOffset, mute, critical, damage, knockback);
    }
    
    public static EntitySlashEffect doSlash(final LivingEntity playerIn, final float roll, final int colorCode, final Vector3d centerOffset, final boolean mute, final boolean critical, final double damage, final KnockBacks knockback) {
        if (playerIn.level.isClientSide) {
            return null;
        }
        Vector3d pos = playerIn.position().add(0.0, playerIn.getEyeHeight() * 0.75, 0.0).add(playerIn.getLookAngle().scale(0.30000001192092896));
        pos = pos.add(VectorHelper.getVectorForRotation(-90.0f, playerIn.getViewYRot(0.0f)).scale(centerOffset.y)).add(VectorHelper.getVectorForRotation(0.0f, playerIn.getViewYRot(0.0f) + 90.0f).scale(centerOffset.z)).add(playerIn.getLookAngle().scale(centerOffset.z));
        final EntitySlashEffect jc = new EntitySlashEffect(SlashBlade.RegistryEvents.SlashEffect, playerIn.level);
        jc.setPos(pos.x, pos.y, pos.z);
        jc.setOwner((Entity)playerIn);
        jc.setRotationRoll(roll);
        jc.yRot = playerIn.yRot;
        jc.xRot = 0.0f;
        jc.setColor(colorCode);
        jc.setMute(mute);
        jc.setIsCritical(critical);
        jc.setDamage(damage);
        jc.setKnockBack(knockback);
        playerIn.level.addFreshEntity((Entity)jc);
        return jc;
    }
    
    public static List<Entity> areaAttack(final LivingEntity playerIn, final Consumer<LivingEntity> beforeHit, final float ratio, final boolean forceHit, final boolean resetHit, final boolean mute) {
        return areaAttack(playerIn, beforeHit, ratio, forceHit, resetHit, mute, null);
    }
    
    public static List<Entity> areaAttack(final LivingEntity playerIn, final Consumer<LivingEntity> beforeHit, final float ratio, final boolean forceHit, final boolean resetHit, final boolean mute, final List<Entity> exclude) {
        List<Entity> founds = (List<Entity>)Lists.newArrayList();
        final float modifiedRatio = (1.0f + EnchantmentHelper.getSweepingDamageRatio(playerIn) * 0.5f) * ratio;
        final AttributeModifier am = new AttributeModifier("SweepingDamageRatio", (double)modifiedRatio, AttributeModifier.Operation.MULTIPLY_BASE);
        if (!playerIn.level.isClientSide()) {
            try {
                playerIn.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(am);
                founds = TargetSelector.getTargettableEntitiesWithinAABB(playerIn.level, playerIn);
                if (exclude != null) {
                    founds.removeAll(exclude);
                }
                for (final Entity entity : founds) {
                    if (entity instanceof LivingEntity) {
                        beforeHit.accept((LivingEntity)entity);
                    }
                    doMeleeAttack(playerIn, entity, forceHit, resetHit);
                }
            }
            finally {
                playerIn.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(am);
            }
        }
        if (!mute) {
            playerIn.level.playSound((PlayerEntity)null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 0.5f, 0.4f / (playerIn.getRandom().nextFloat() * 0.4f + 0.8f));
        }
        return founds;
    }
    
    public static <E extends net.minecraft.entity.Entity> List<Entity> areaAttack(final E owner, final Consumer<LivingEntity> beforeHit, final double reach, final boolean forceHit, final boolean resetHit) {
        return areaAttack(owner, beforeHit, reach, forceHit, resetHit, null);
    }
    
    public static <E extends net.minecraft.entity.Entity> List<Entity> areaAttack(final E owner, final Consumer<LivingEntity> beforeHit, final double reach, final boolean forceHit, final boolean resetHit, final List<Entity> exclude) {
        List<Entity> founds = (List<Entity>)Lists.newArrayList();
        final AxisAlignedBB bb = ((Entity)owner).getBoundingBox();
        if (!((Entity)owner).level.isClientSide()) {
            founds = TargetSelector.getTargettableEntitiesWithinAABB(((Entity)owner).level, reach, owner);
            if (exclude != null) {
                founds.removeAll(exclude);
            }
            for (final Entity entity : founds) {
                if (entity instanceof LivingEntity) {
                    beforeHit.accept((LivingEntity)entity);
                }
                final float baseAmount = (float)((IShootable)owner).getDamage();
                doAttackWith(DamageSource.indirectMagic((Entity)owner, ((IShootable)owner).getShooter()), baseAmount, entity, forceHit, resetHit);
            }
        }
        return founds;
    }
    
    public static void doManagedAttack(final Consumer<Entity> attack, final Entity target, final boolean forceHit, final boolean resetHit) {
        if (forceHit) {
            target.invulnerableTime = 0;
        }
        attack.accept(target);
        if (resetHit) {
            target.invulnerableTime = 0;
        }
    }
    
    public static void doAttackWith(final DamageSource src, final float amount, final Entity target, final boolean forceHit, final boolean resetHit) {
        doManagedAttack(t -> t.hurt(src, amount), target, forceHit, resetHit);
    }
    
    public static void doMeleeAttack(final LivingEntity attacker, final Entity target, final boolean forceHit, final boolean resetHit) {
        if (attacker instanceof PlayerEntity) {
            doManagedAttack(t -> attacker.getMainHandItem().getCapability(ItemSlashBlade.BLADESTATE).ifPresent(state -> {
                final IConcentrationRank.ConcentrationRanks rankBonus = attacker.getCapability(ConcentrationRankCapabilityProvider.RANK_POINT).map(rp -> rp.getRank(attacker.getCommandSenderWorld().getGameTime())).orElse(IConcentrationRank.ConcentrationRanks.NONE);
                float modifiedRatio = rankBonus.level / 2.0f;
                if (attacker instanceof PlayerEntity && IConcentrationRank.ConcentrationRanks.S.level <= rankBonus.level) {
                    final int level = ((PlayerEntity)attacker).experienceLevel;
                    modifiedRatio = Math.max(modifiedRatio, (float)Math.min(level, state.getRefine()));
                }
                final AttributeModifier am = new AttributeModifier("RankDamageBonus", (double)modifiedRatio, AttributeModifier.Operation.ADDITION);
                try {
                    state.setOnClick(true);
                    attacker.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(am);
                    ((PlayerEntity)attacker).attack(t);
                }
                finally {
                    attacker.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(am);
                    state.setOnClick(false);
                }
            }), target, forceHit, resetHit);
        }
        else {
            final float baseAmount = (float)attacker.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
            doAttackWith(DamageSource.mobAttack(attacker), baseAmount, target, forceHit, resetHit);
        }
        ArrowReflector.doReflect(target, (Entity)attacker);
        TNTExtinguisher.doExtinguishing(target, attacker);
    }
}
