//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.entity;

import net.minecraft.entity.item.*;
import net.minecraft.nbt.*;
import mods.flammpfeil.slashblade.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.network.*;
import net.minecraft.entity.*;
import net.minecraft.particles.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.block.*;

public class BladeItemEntity extends ItemEntity
{
    public BladeItemEntity(final EntityType<? extends BladeItemEntity> p_i50217_1_, final World p_i50217_2_) {
        super((EntityType)p_i50217_1_, p_i50217_2_);
    }
    
    public void init() {
        this.setInvulnerable(true);
        final CompoundNBT compoundnbt = this.saveWithoutId(new CompoundNBT());
        compoundnbt.remove("Dimension");
        compoundnbt.putShort("Health", (short)100);
        compoundnbt.putShort("Age", (short)(-32768));
        this.load(compoundnbt);
    }
    
    public static BladeItemEntity createInstanceFromPacket(final FMLPlayMessages.SpawnEntity packet, final World worldIn) {
        return new BladeItemEntity(SlashBlade.RegistryEvents.BladeItem, worldIn);
    }
    
    public IPacket<?> getAddEntityPacket() {
        return (IPacket<?>)NetworkHooks.getEntitySpawningPacket((Entity)this);
    }
    
    public void tick() {
        if (this.isOnGround() && this.tickCount % 40 == 0) {
            ++this.tickCount;
        }
        super.tick();
        if (!this.isInWater() && !this.isOnGround() && this.tickCount % 6 == 0) {
            this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 0.5f, 2.5f);
        }
        if (this.level.isClientSide) {
            if (this.random.nextInt(5) == 0 && this.getAirSupply() < 0) {
                final Direction direction = Direction.UP;
                final double d0 = this.getX() - this.random.nextFloat() * 0.1f;
                final double d2 = this.getY() - this.random.nextFloat() * 0.1f;
                final double d3 = this.getZ() - this.random.nextFloat() * 0.1f;
                final double d4 = 0.4f - (this.random.nextFloat() + this.random.nextFloat()) * 0.4f;
                this.level.addParticle((IParticleData)ParticleTypes.PORTAL, d0 + direction.getStepX() * d4, d2 + 2.0 + direction.getStepY() * d4, d3 + direction.getStepZ() * d4, this.random.nextGaussian() * 0.005, -2.0, this.random.nextGaussian() * 0.005);
            }
            if (!this.isOnGround() && !this.isInWater() && this.random.nextInt(3) == 0) {
                final Direction direction = Direction.UP;
                final double d0 = this.getX() - this.random.nextFloat() * 0.1f;
                final double d2 = this.getY() - this.random.nextFloat() * 0.1f;
                final double d3 = this.getZ() - this.random.nextFloat() * 0.1f;
                final double d4 = 0.4f - (this.random.nextFloat() + this.random.nextFloat()) * 0.4f;
                this.level.addParticle((IParticleData)ParticleTypes.END_ROD, d0 + direction.getStepX() * d4, d2 + direction.getStepY() * d4, d3 + direction.getStepZ() * d4, this.random.nextGaussian() * 0.005, this.random.nextGaussian() * 0.005, this.random.nextGaussian() * 0.005);
            }
        }
    }
    
    public boolean isOnFire() {
        return super.isOnFire();
    }
    
    public boolean causeFallDamage(final float distance, final float damageMultiplier) {
        super.causeFallDamage(distance, damageMultiplier);
        final int i = MathHelper.ceil(distance);
        if (i > 0) {
            this.playSound(SoundEvents.GENERIC_BIG_FALL, 1.0f, 1.0f);
            this.hurt(DamageSource.FALL, (float)i);
            final int j = MathHelper.floor(this.getX());
            final int k = MathHelper.floor(this.getY() - 0.20000000298023224);
            final int l = MathHelper.floor(this.getZ());
            final BlockState blockstate = this.level.getBlockState(new BlockPos(j, k, l));
            if (!blockstate.isAir()) {
                final SoundType soundtype = blockstate.getSoundType((IWorldReader)this.level, new BlockPos(j, k, l), (Entity)this);
                this.playSound(soundtype.getFallSound(), soundtype.getVolume() * 0.5f, soundtype.getPitch() * 0.75f);
            }
            if (this.isGlowing() && this.getAirSupply() < 0) {
                this.setGlowing(false);
            }
        }
        return false;
    }
    
    public float getBrightness() {
        if (this.getAirSupply() < 0) {
            return 1.572888E7f;
        }
        return super.getBrightness();
    }
}
