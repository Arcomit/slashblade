//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.entity;

import net.minecraft.world.*;
import net.minecraft.util.*;
import mods.flammpfeil.slashblade.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.network.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraft.world.server.*;
import javax.annotation.*;
import net.minecraft.particles.*;
import mods.flammpfeil.slashblade.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.potion.*;
import net.minecraft.util.math.vector.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.network.datasync.*;

public class EntityJudgementCut extends ProjectileEntity implements IShootable
{
    private static final DataParameter<Integer> COLOR;
    private static final DataParameter<Integer> FLAGS;
    private int lifetime;
    private int seed;
    private double damage;
    private boolean cycleHit;
    public UUID shootingEntity;
    private SoundEvent livingEntitySound;
    EnumSet<FlagsState> flags;
    int intFlags;
    
    public int getSeed() {
        return this.seed;
    }
    
    public boolean doCycleHit() {
        return this.cycleHit;
    }
    
    public void setCycleHit(final boolean cycleHit) {
        this.cycleHit = cycleHit;
    }
    
    protected SoundEvent getHitEntitySound() {
        return this.livingEntitySound;
    }
    
    public EntityJudgementCut(final EntityType<? extends ProjectileEntity> entityTypeIn, final World worldIn) {
        super((EntityType)entityTypeIn, worldIn);
        this.lifetime = 10;
        this.seed = -1;
        this.damage = 1.0;
        this.cycleHit = false;
        this.livingEntitySound = SoundEvents.WITHER_HURT;
        this.flags = EnumSet.noneOf(FlagsState.class);
        this.intFlags = 0;
        this.setNoGravity(true);
        this.seed = this.random.nextInt(360);
    }
    
    public static EntityJudgementCut createInstance(final FMLPlayMessages.SpawnEntity packet, final World worldIn) {
        return new EntityJudgementCut(SlashBlade.RegistryEvents.JudgementCut, worldIn);
    }
    
    protected void defineSynchedData() {
        this.entityData.define((DataParameter)EntityJudgementCut.COLOR, (Object)3355647);
        this.entityData.define((DataParameter)EntityJudgementCut.FLAGS, (Object)0);
    }
    
    protected void addAdditionalSaveData(final CompoundNBT compound) {
        NBTHelper.getNBTCoupler(compound).put("Color", this.getColor()).put("damage", this.damage).put("crit", this.getIsCritical()).put("clip", this.isNoClip()).put("OwnerUUID", this.shootingEntity).put("Lifetime", this.getLifetime());
    }
    
    protected void readAdditionalSaveData(final CompoundNBT compound) {
        NBTHelper.getNBTCoupler(compound).get("Color", this::setColor, new Integer[0]).get("damage", v -> this.damage = v, this.damage).get("crit", this::setIsCritical, new Boolean[0]).get("clip", this::setNoClip, new Boolean[0]).get("OwnerUUID", v -> this.shootingEntity = v, true, new UUID[0]).get("Lifetime", this::setLifetime, new Integer[0]);
    }
    
    public IPacket<?> getAddEntityPacket() {
        return (IPacket<?>)NetworkHooks.getEntitySpawningPacket((Entity)this);
    }
    
    public void shoot(final double x, final double y, final double z, final float velocity, final float inaccuracy) {
        this.setDeltaMovement(0.0, 0.0, 0.0);
    }
    
    @OnlyIn(Dist.CLIENT)
    public boolean shouldRenderAtSqrDistance(final double distance) {
        double d0 = this.getBoundingBox().getSize() * 10.0;
        if (Double.isNaN(d0)) {
            d0 = 1.0;
        }
        d0 = d0 * 64.0 * getViewScale();
        return distance < d0 * d0;
    }
    
    @OnlyIn(Dist.CLIENT)
    public void lerpTo(final double x, final double y, final double z, final float yaw, final float pitch, final int posRotationIncrements, final boolean teleport) {
        this.setPos(x, y, z);
        this.setRot(yaw, pitch);
    }
    
    @OnlyIn(Dist.CLIENT)
    public void lerpMotion(final double x, final double y, final double z) {
        this.setDeltaMovement(0.0, 0.0, 0.0);
    }
    
    private void setFlags(final FlagsState value) {
        this.flags.add(value);
        this.refreshFlags();
    }
    
    private void removeFlags(final FlagsState value) {
        this.flags.remove(value);
        this.refreshFlags();
    }
    
    private void refreshFlags() {
        if (this.level.isClientSide) {
            final int newValue = (int)this.entityData.get((DataParameter)EntityJudgementCut.FLAGS);
            if (this.intFlags != newValue) {
                this.intFlags = newValue;
                EnumSetConverter.convertToEnumSet(this.flags, FlagsState.values(), this.intFlags);
            }
        }
        else {
            final int newValue = EnumSetConverter.convertToInt(this.flags);
            if (this.intFlags != newValue) {
                this.entityData.set((DataParameter)EntityJudgementCut.FLAGS, (Object)newValue);
                this.intFlags = newValue;
            }
        }
    }
    
    public void setIsCritical(final boolean value) {
        if (value) {
            this.setFlags(FlagsState.Critical);
        }
        else {
            this.removeFlags(FlagsState.Critical);
        }
    }
    
    public boolean getIsCritical() {
        this.refreshFlags();
        return this.flags.contains(FlagsState.Critical);
    }
    
    public void setNoClip(final boolean value) {
        this.noPhysics = value;
        if (value) {
            this.setFlags(FlagsState.NoClip);
        }
        else {
            this.removeFlags(FlagsState.NoClip);
        }
    }
    
    public boolean isNoClip() {
        if (!this.level.isClientSide) {
            return this.noPhysics;
        }
        this.refreshFlags();
        return this.flags.contains(FlagsState.NoClip);
    }
    
    public void tick() {
        super.tick();
        if (this.tickCount < 8 && this.tickCount % 2 == 0) {
            this.playSound(this.getHitEntitySound(), 0.2f, 0.5f + 0.25f * this.random.nextFloat());
        }
        if (this.getShooter() != null) {
            final AxisAlignedBB bb = this.getBoundingBox();
            if (this.tickCount % 2 == 0) {
                final KnockBacks knockBackType = this.getIsCritical() ? KnockBacks.toss : KnockBacks.cancel;
                AttackManager.areaAttack(this, knockBackType.action, 4.0, this.doCycleHit(), false);
            }
            final int count = 3;
            if (this.getIsCritical() && 0 < this.tickCount && this.tickCount <= 3) {
                final EntitySlashEffect jc = new EntitySlashEffect(SlashBlade.RegistryEvents.SlashEffect, this.level);
                jc.absMoveTo(this.getX(), this.getY(), this.getZ(), 120.0f * this.tickCount + this.seed, 0.0f);
                jc.setRotationRoll(30.0f);
                jc.setOwner(this.getShooter());
                jc.setMute(false);
                jc.setIsCritical(true);
                jc.setDamage(1.0);
                jc.setColor(this.getColor());
                jc.setBaseSize(0.5f);
                jc.setKnockBack(KnockBacks.cancel);
                jc.setIndirect(true);
                this.level.addFreshEntity((Entity)jc);
            }
        }
        this.tryDespawn();
    }
    
    protected void tryDespawn() {
        if (!this.level.isClientSide && this.getLifetime() < this.tickCount) {
            this.burst();
        }
    }
    
    public int getColor() {
        return (int)this.getEntityData().get((DataParameter)EntityJudgementCut.COLOR);
    }
    
    public void setColor(final int value) {
        this.getEntityData().set((DataParameter)EntityJudgementCut.COLOR, (Object)value);
    }
    
    public int getLifetime() {
        return Math.min(this.lifetime, 1000);
    }
    
    public void setLifetime(final int value) {
        this.lifetime = value;
    }
    
    @Nullable
    public Entity getShooter() {
        return (this.shootingEntity != null && this.level instanceof ServerWorld) ? ((ServerWorld)this.level).getEntity(this.shootingEntity) : null;
    }
    
    public void setOwner(final Entity shooter) {
        this.shootingEntity = ((shooter != null) ? shooter.getUUID() : null);
    }
    
    public List<EffectInstance> getPotionEffects() {
        final List<EffectInstance> effects = (List<EffectInstance>)PotionUtils.getAllEffects(this.getPersistentData());
        if (effects.isEmpty()) {
            effects.add(new EffectInstance(Effects.POISON, 1, 1));
        }
        return effects;
    }
    
    public void burst() {
        if (!this.level.isClientSide) {
            if (this.level instanceof ServerWorld) {
                ((ServerWorld)this.level).sendParticles((IParticleData)ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(), 16, 0.5, 0.5, 0.5, 0.25);
            }
            this.burst(this.getPotionEffects(), null);
        }
        super.remove();
    }
    
    public void burst(final List<EffectInstance> effects, @Nullable final Entity focusEntity) {
        final AxisAlignedBB axisalignedbb = this.getBoundingBox().inflate(4.0, 2.0, 4.0);
        final List<Entity> list = TargetSelector.getTargettableEntitiesWithinAABB(this.level, 2.0, this);
        final double distanceSq;
        double factor;
        list.stream().filter(e -> e instanceof LivingEntity).map(e -> e).forEach(e -> {
            distanceSq = this.distanceToSqr(e);
            if (distanceSq < 9.0) {
                factor = 1.0 - Math.sqrt(distanceSq) / 4.0;
                if (e == focusEntity) {
                    factor = 1.0;
                }
                this.affectEntity((LivingEntity)e, effects, factor);
            }
        });
    }
    
    public void affectEntity(final LivingEntity focusEntity, final List<EffectInstance> effects, final double factor) {
        for (final EffectInstance effectinstance : this.getPotionEffects()) {
            final Effect effect = effectinstance.getEffect();
            if (effect.isInstantenous()) {
                effect.applyInstantenousEffect((Entity)this, this.getShooter(), focusEntity, effectinstance.getAmplifier(), factor);
            }
            else {
                final int duration = (int)(factor * effectinstance.getDuration() + 0.5);
                if (duration <= 0) {
                    continue;
                }
                focusEntity.addEffect(new EffectInstance(effect, duration, effectinstance.getAmplifier(), effectinstance.isAmbient(), effectinstance.isVisible()));
            }
        }
    }
    
    public void setDamage(final double damageIn) {
        this.damage = damageIn;
    }
    
    public double getDamage() {
        return this.damage;
    }
    
    @Nullable
    public EntityRayTraceResult getRayTrace(final Vector3d p_213866_1_, final Vector3d p_213866_2_) {
        return ProjectileHelper.getEntityHitResult(this.level, (Entity)this, p_213866_1_, p_213866_2_, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0), p_213871_1_ -> !p_213871_1_.isSpectator() && p_213871_1_.isAlive() && p_213871_1_.isPickable() && p_213871_1_ != this.getShooter());
    }
    
    static {
        COLOR = EntityDataManager.defineId((Class)EntityJudgementCut.class, DataSerializers.INT);
        FLAGS = EntityDataManager.defineId((Class)EntityJudgementCut.class, DataSerializers.INT);
    }
    
    enum FlagsState
    {
        Critical, 
        NoClip;
    }
}
