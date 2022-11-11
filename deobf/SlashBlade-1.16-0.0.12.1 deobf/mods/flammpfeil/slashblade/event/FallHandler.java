//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.event;

import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.event.entity.player.*;
import mods.flammpfeil.slashblade.item.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.server.*;
import net.minecraft.particles.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.vector.*;
import net.minecraft.enchantment.*;
import net.minecraftforge.common.*;
import net.minecraft.entity.ai.attributes.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;

public class FallHandler
{
    public static FallHandler getInstance() {
        return SingletonHolder.instance;
    }
    
    private FallHandler() {
    }
    
    public void register() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onFall(final LivingFallEvent event) {
        resetState(event.getEntityLiving());
    }
    
    @SubscribeEvent
    public void onFlyableFall(final PlayerFlyableFallEvent event) {
        resetState(event.getEntityLiving());
    }
    
    public static void resetState(final LivingEntity user) {
        user.getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(state -> {
            state.setFallDecreaseRate(0.0f);
            final ComboState combo = state.getComboSeq();
            if (combo.isAerial()) {
                state.setComboSeq(combo.getNextOfTimeout());
            }
        });
    }
    
    public static void spawnLandingParticle(final LivingEntity user, final float fallFactor) {
        if (!user.level.isClientSide) {
            final int x = MathHelper.floor(user.getX());
            final int y = MathHelper.floor(user.getY() - 0.5);
            final int z = MathHelper.floor(user.getZ());
            final BlockPos pos = new BlockPos(x, y, z);
            final BlockState state = user.level.getBlockState(pos);
            final float f = (float)MathHelper.ceil(fallFactor);
            if (!state.isAir((IBlockReader)user.level, pos)) {
                final double d0 = Math.min(0.2f + f / 15.0f, 2.5);
                final int i = (int)(150.0 * d0);
                if (!state.addLandingEffects((ServerWorld)user.level, pos, state, user, i)) {
                    ((ServerWorld)user.level).sendParticles((IParticleData)new BlockParticleData(ParticleTypes.BLOCK, state), user.getX(), user.getY(), user.getZ(), i, 0.0, 0.0, 0.0, 0.15000000596046448);
                }
            }
        }
    }
    
    public static void spawnLandingParticle(final Entity user, final Vector3d targetPos, final Vector3d normal, final float fallFactor) {
        if (!user.level.isClientSide) {
            final Vector3d blockPos = targetPos.add(normal.normalize().scale(0.5));
            final int x = MathHelper.floor(blockPos.x());
            final int y = MathHelper.floor(blockPos.y());
            final int z = MathHelper.floor(blockPos.z());
            final BlockPos pos = new BlockPos(x, y, z);
            final BlockState state = user.level.getBlockState(pos);
            final float f = (float)MathHelper.ceil(fallFactor);
            if (!state.isAir((IBlockReader)user.level, pos)) {
                final double d0 = Math.min(0.2f + f / 15.0f, 2.5);
                final int i = (int)(150.0 * d0);
                ((ServerWorld)user.level).sendParticles((IParticleData)new BlockParticleData(ParticleTypes.BLOCK, state), targetPos.x(), targetPos.y(), targetPos.z(), i, 0.0, 0.0, 0.0, 0.15000000596046448);
            }
        }
    }
    
    public static void fallDecrease(final LivingEntity user) {
        if (!user.isNoGravity() && !user.isOnGround()) {
            user.fallDistance = 1.0f;
            final float currentRatio = user.getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).map(state -> {
                final float decRatio = state.getFallDecreaseRate();
                float newDecRatio = decRatio + 0.05f;
                newDecRatio = Math.min(1.0f, newDecRatio);
                state.setFallDecreaseRate(newDecRatio);
                return decRatio;
            }).orElseGet(() -> 1.0f);
            double gravityReductionFactor = 0.8500000238418579;
            final int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.FALL_PROTECTION, user);
            if (0 < level) {
                gravityReductionFactor = Math.min(0.93, gravityReductionFactor + 0.2 * level);
            }
            final ModifiableAttributeInstance gravity = user.getAttribute((Attribute)ForgeMod.ENTITY_GRAVITY.get());
            final double g = gravity.getValue() * gravityReductionFactor;
            final Vector3d motion = user.getDeltaMovement();
            if (motion.y < 0.0) {
                user.setDeltaMovement(motion.x, (motion.y + g) * currentRatio, motion.z);
            }
        }
    }
    
    public static void fallResist(final LivingEntity user) {
        if (!user.isNoGravity() && !user.isOnGround()) {
            user.fallDistance = 1.0f;
            final Vector3d motion = user.getDeltaMovement();
            final ModifiableAttributeInstance gravity = user.getAttribute((Attribute)ForgeMod.ENTITY_GRAVITY.get());
            final double g = gravity.getValue();
            if (motion.y < 0.0) {
                user.setDeltaMovement(motion.x, motion.y + g + 0.0020000000949949026, motion.z);
            }
        }
    }
    
    private static final class SingletonHolder
    {
        private static final FallHandler instance;
        
        static {
            instance = new FallHandler(null);
        }
    }
}
