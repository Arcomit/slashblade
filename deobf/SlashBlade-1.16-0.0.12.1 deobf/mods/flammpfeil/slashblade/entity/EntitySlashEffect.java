//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.entity;

import net.minecraft.world.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import mods.flammpfeil.slashblade.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.network.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraft.util.math.vector.*;
import mods.flammpfeil.slashblade.event.*;
import net.minecraft.entity.*;
import mods.flammpfeil.slashblade.util.*;
import java.util.*;
import net.minecraft.world.server.*;
import javax.annotation.*;
import net.minecraft.potion.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.network.datasync.*;

public class EntitySlashEffect extends ProjectileEntity implements IShootable
{
    private static final DataParameter<Integer> COLOR;
    private static final DataParameter<Integer> FLAGS;
    private static final DataParameter<Float> ROTATION_OFFSET;
    private static final DataParameter<Float> ROTATION_ROLL;
    private static final DataParameter<Float> BASESIZE;
    private int lifetime;
    private KnockBacks action;
    private double damage;
    private boolean cycleHit;
    private List<Entity> alreadyHits;
    public UUID shootingEntity;
    private SoundEvent livingEntitySound;
    EnumSet<FlagsState> flags;
    int intFlags;
    
    public KnockBacks getKnockBack() {
        return this.action;
    }
    
    public void setKnockBack(final KnockBacks action) {
        this.action = action;
    }
    
    public void setKnockBackOrdinal(final int ordinal) {
        if (0 <= ordinal && ordinal < KnockBacks.values().length) {
            this.action = KnockBacks.values()[ordinal];
        }
        else {
            this.action = KnockBacks.cancel;
        }
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
    
    public EntitySlashEffect(final EntityType<? extends ProjectileEntity> entityTypeIn, final World worldIn) {
        super((EntityType)entityTypeIn, worldIn);
        this.lifetime = 10;
        this.action = KnockBacks.cancel;
        this.damage = 1.0;
        this.cycleHit = false;
        this.alreadyHits = (List<Entity>)Lists.newArrayList();
        this.livingEntitySound = SoundEvents.WITHER_HURT;
        this.flags = EnumSet.noneOf(FlagsState.class);
        this.intFlags = 0;
        this.setNoGravity(true);
    }
    
    public static EntitySlashEffect createInstance(final FMLPlayMessages.SpawnEntity packet, final World worldIn) {
        return new EntitySlashEffect(SlashBlade.RegistryEvents.SlashEffect, worldIn);
    }
    
    protected void defineSynchedData() {
        this.entityData.define((DataParameter)EntitySlashEffect.COLOR, (Object)3355647);
        this.entityData.define((DataParameter)EntitySlashEffect.FLAGS, (Object)0);
        this.entityData.define((DataParameter)EntitySlashEffect.ROTATION_OFFSET, (Object)0.0f);
        this.entityData.define((DataParameter)EntitySlashEffect.ROTATION_ROLL, (Object)0.0f);
        this.entityData.define((DataParameter)EntitySlashEffect.BASESIZE, (Object)1.0f);
    }
    
    protected void addAdditionalSaveData(final CompoundNBT compound) {
        NBTHelper.getNBTCoupler(compound).put("RotationOffset", this.getRotationOffset()).put("RotationRoll", this.getRotationRoll()).put("BaseSize", this.getBaseSize()).put("Color", this.getColor()).put("damage", this.damage).put("crit", this.getIsCritical()).put("clip", this.isNoClip()).put("OwnerUUID", this.shootingEntity).put("Lifetime", this.getLifetime()).put("Knockback", this.getKnockBack().ordinal());
    }
    
    protected void readAdditionalSaveData(final CompoundNBT compound) {
        NBTHelper.getNBTCoupler(compound).get("RotationOffset", this::setRotationOffset, new Float[0]).get("RotationRoll", this::setRotationRoll, new Float[0]).get("BaseSize", this::setBaseSize, new Float[0]).get("Color", this::setColor, new Integer[0]).get("damage", v -> this.damage = v, this.damage).get("crit", this::setIsCritical, new Boolean[0]).get("clip", this::setNoClip, new Boolean[0]).get("OwnerUUID", v -> this.shootingEntity = v, true, new UUID[0]).get("Lifetime", this::setLifetime, new Integer[0]).get("Knockback", this::setKnockBackOrdinal, new Integer[0]);
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
            final int newValue = (int)this.entityData.get((DataParameter)EntitySlashEffect.FLAGS);
            if (this.intFlags != newValue) {
                this.intFlags = newValue;
                EnumSetConverter.convertToEnumSet(this.flags, FlagsState.values(), this.intFlags);
            }
        }
        else {
            final int newValue = EnumSetConverter.convertToInt(this.flags);
            if (this.intFlags != newValue) {
                this.entityData.set((DataParameter)EntitySlashEffect.FLAGS, (Object)newValue);
                this.intFlags = newValue;
            }
        }
    }
    
    public void setIndirect(final boolean value) {
        if (value) {
            this.setFlags(FlagsState.Indirect);
        }
        else {
            this.removeFlags(FlagsState.Indirect);
        }
    }
    
    public boolean getIndirect() {
        this.refreshFlags();
        return this.flags.contains(FlagsState.Indirect);
    }
    
    public void setMute(final boolean value) {
        if (value) {
            this.setFlags(FlagsState.Mute);
        }
        else {
            this.removeFlags(FlagsState.Mute);
        }
    }
    
    public boolean getMute() {
        this.refreshFlags();
        return this.flags.contains(FlagsState.Mute);
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
        if (this.tickCount == 2) {
            if (!this.getMute()) {
                this.playSound(SoundEvents.TRIDENT_THROW, 0.8f, 0.625f + 0.1f * this.random.nextFloat());
            }
            else {
                this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 0.5f, 0.4f / (this.random.nextFloat() * 0.4f + 0.8f));
            }
            if (this.getIsCritical()) {
                this.playSound(this.getHitEntitySound(), 0.2f, 0.4f + 0.25f * this.random.nextFloat());
            }
        }
        if (this.getShooter() != null && !this.getShooter().isInWaterOrRain() && this.tickCount < this.getLifetime() * 0.75) {
            final Vector3d start = this.position();
            final Vector4f normal = new Vector4f(1.0f, 0.0f, 0.0f, 1.0f);
            final float progress = this.tickCount / (float)this.lifetime;
            normal.transform(new Quaternion(Vector3f.YP, -this.yRot - 90.0f, true));
            normal.transform(new Quaternion(Vector3f.ZP, this.xRot, true));
            normal.transform(new Quaternion(Vector3f.XP, this.getRotationRoll(), true));
            normal.transform(new Quaternion(Vector3f.YP, 140.0f + this.getRotationOffset() - 200.0f * progress, true));
            final Vector3d normal3d = new Vector3d((double)normal.x(), (double)normal.y(), (double)normal.z());
            final BlockRayTraceResult rayResult = this.getCommandSenderWorld().clip(new RayTraceContext(start.add(normal3d.scale(1.5)), start.add(normal3d.scale(3.0)), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, (Entity)null));
            if (rayResult.getType() == RayTraceResult.Type.BLOCK) {
                FallHandler.spawnLandingParticle((Entity)this, rayResult.getLocation(), normal3d, 3.0f);
            }
        }
        if (this.getShooter() != null) {
            final AxisAlignedBB bb = this.getBoundingBox();
            if (this.tickCount % 2 == 0) {
                final boolean forceHit = true;
                List<Entity> hits;
                if (!this.getIndirect() && this.getShooter() instanceof LivingEntity) {
                    final LivingEntity shooter = (LivingEntity)this.getShooter();
                    final float ratio = (float)this.damage * (this.getIsCritical() ? 1.1f : 1.0f);
                    hits = AttackManager.areaAttack(shooter, this.action.action, ratio, forceHit, false, true, this.alreadyHits);
                }
                else {
                    hits = AttackManager.areaAttack(this, this.action.action, 4.0, forceHit, false, this.alreadyHits);
                }
                if (!this.doCycleHit()) {
                    this.alreadyHits.addAll(hits);
                }
            }
        }
        this.tryDespawn();
    }
    
    protected void tryDespawn() {
        if (!this.level.isClientSide && this.getLifetime() < this.tickCount) {
            this.remove();
        }
    }
    
    public int getColor() {
        return (int)this.getEntityData().get((DataParameter)EntitySlashEffect.COLOR);
    }
    
    public void setColor(final int value) {
        this.getEntityData().set((DataParameter)EntitySlashEffect.COLOR, (Object)value);
    }
    
    public int getLifetime() {
        return Math.min(this.lifetime, 1000);
    }
    
    public void setLifetime(final int value) {
        this.lifetime = value;
    }
    
    public float getRotationOffset() {
        return (float)this.getEntityData().get((DataParameter)EntitySlashEffect.ROTATION_OFFSET);
    }
    
    public void setRotationOffset(final float value) {
        this.getEntityData().set((DataParameter)EntitySlashEffect.ROTATION_OFFSET, (Object)value);
    }
    
    public float getRotationRoll() {
        return (float)this.getEntityData().get((DataParameter)EntitySlashEffect.ROTATION_ROLL);
    }
    
    public void setRotationRoll(final float value) {
        this.getEntityData().set((DataParameter)EntitySlashEffect.ROTATION_ROLL, (Object)value);
    }
    
    public float getBaseSize() {
        return (float)this.getEntityData().get((DataParameter)EntitySlashEffect.BASESIZE);
    }
    
    public void setBaseSize(final float value) {
        this.getEntityData().set((DataParameter)EntitySlashEffect.BASESIZE, (Object)value);
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
        COLOR = EntityDataManager.defineId((Class)EntitySlashEffect.class, DataSerializers.INT);
        FLAGS = EntityDataManager.defineId((Class)EntitySlashEffect.class, DataSerializers.INT);
        ROTATION_OFFSET = EntityDataManager.defineId((Class)EntitySlashEffect.class, DataSerializers.FLOAT);
        ROTATION_ROLL = EntityDataManager.defineId((Class)EntitySlashEffect.class, DataSerializers.FLOAT);
        BASESIZE = EntityDataManager.defineId((Class)EntitySlashEffect.class, DataSerializers.FLOAT);
    }
    
    enum FlagsState
    {
        Critical, 
        NoClip, 
        Mute, 
        Indirect;
    }
}
