//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.entity;

import net.minecraft.block.*;
import it.unimi.dsi.fastutil.ints.*;
import net.minecraftforge.common.util.*;
import mods.flammpfeil.slashblade.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.network.*;
import net.minecraft.util.math.vector.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraft.world.*;
import net.minecraftforge.event.*;
import net.minecraft.particles.*;
import net.minecraft.util.math.shapes.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraftforge.entity.*;
import net.minecraft.entity.monster.*;
import mods.flammpfeil.slashblade.ability.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.entity.projectile.*;
import javax.annotation.*;
import net.minecraft.world.server.*;
import mods.flammpfeil.slashblade.util.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.network.datasync.*;

public class EntityAbstractSummonedSword extends ProjectileEntity implements IShootable
{
    private static final DataParameter<Integer> COLOR;
    private static final DataParameter<Integer> FLAGS;
    private static final DataParameter<Integer> HIT_ENTITY_ID;
    private static final DataParameter<Float> OFFSET_YAW;
    private static final DataParameter<Float> ROLL;
    private static final DataParameter<Byte> PIERCE;
    private static final DataParameter<String> MODEL;
    private static final DataParameter<Integer> DELAY;
    private int ticksInGround;
    private boolean inGround;
    private BlockState inBlockState;
    private int ticksInAir;
    private double damage;
    private IntOpenHashSet alreadyHits;
    public UUID shootingEntity;
    private Entity hitEntity;
    static final int ON_GROUND_LIFE_TIME = 100;
    private SoundEvent hitEntitySound;
    private SoundEvent hitEntityPlayerSound;
    private SoundEvent hitGroundSound;
    EnumSet<FlagsState> flags;
    int intFlags;
    private static final String defaultModelName = "slashblade:model/util/ss";
    private static final ResourceLocation defaultModel;
    public LazyOptional<ResourceLocation> modelLoc;
    private static final ResourceLocation defaultTexture;
    public LazyOptional<ResourceLocation> textureLoc;
    
    protected SoundEvent getHitEntitySound() {
        return this.hitEntitySound;
    }
    
    protected SoundEvent getHitEntityPlayerSound() {
        return this.hitEntityPlayerSound;
    }
    
    protected SoundEvent getHitGroundSound() {
        return this.hitGroundSound;
    }
    
    public EntityAbstractSummonedSword(final EntityType<? extends ProjectileEntity> entityTypeIn, final World worldIn) {
        super((EntityType)entityTypeIn, worldIn);
        this.damage = 1.0;
        this.hitEntity = null;
        this.hitEntitySound = SoundEvents.TRIDENT_HIT;
        this.hitEntityPlayerSound = SoundEvents.TRIDENT_HIT;
        this.hitGroundSound = SoundEvents.TRIDENT_HIT_GROUND;
        this.flags = EnumSet.noneOf(FlagsState.class);
        this.intFlags = 0;
        this.modelLoc = (LazyOptional<ResourceLocation>)LazyOptional.of(() -> new ResourceLocation(this.getModelName() + ".obj"));
        this.textureLoc = (LazyOptional<ResourceLocation>)LazyOptional.of(() -> new ResourceLocation(this.getModelName() + ".png"));
        this.setNoGravity(true);
    }
    
    public static EntityAbstractSummonedSword createInstance(final FMLPlayMessages.SpawnEntity packet, final World worldIn) {
        return new EntityAbstractSummonedSword(SlashBlade.RegistryEvents.SummonedSword, worldIn);
    }
    
    protected void defineSynchedData() {
        this.entityData.define((DataParameter)EntityAbstractSummonedSword.COLOR, (Object)3355647);
        this.entityData.define((DataParameter)EntityAbstractSummonedSword.FLAGS, (Object)0);
        this.entityData.define((DataParameter)EntityAbstractSummonedSword.HIT_ENTITY_ID, (Object)(-1));
        this.entityData.define((DataParameter)EntityAbstractSummonedSword.OFFSET_YAW, (Object)0.0f);
        this.entityData.define((DataParameter)EntityAbstractSummonedSword.ROLL, (Object)0.0f);
        this.entityData.define((DataParameter)EntityAbstractSummonedSword.PIERCE, (Object)0);
        this.entityData.define((DataParameter)EntityAbstractSummonedSword.MODEL, (Object)"");
        this.entityData.define((DataParameter)EntityAbstractSummonedSword.DELAY, (Object)10);
    }
    
    public void addAdditionalSaveData(final CompoundNBT compound) {
        NBTHelper.getNBTCoupler(compound).put("Color", this.getColor()).put("life", (short)this.ticksInGround).put("inBlockState", (this.inBlockState != null) ? NBTUtil.writeBlockState(this.inBlockState) : null).put("inGround", this.inGround).put("damage", this.damage).put("crit", this.getIsCritical()).put("clip", this.isNoClip()).put("PierceLevel", this.getPierce()).put("OwnerUUID", this.shootingEntity).put("model", this.getModelName()).put("Delay", this.getDelay());
    }
    
    public void readAdditionalSaveData(final CompoundNBT compound) {
        NBTHelper.getNBTCoupler(compound).get("Color", this::setColor, new Integer[0]).get("life", v -> this.ticksInGround = v, new Integer[0]).get("inBlockState", v -> this.inBlockState = NBTUtil.readBlockState(v), new CompoundNBT[0]).get("inGround", v -> this.inGround = v, new Boolean[0]).get("damage", v -> this.damage = v, this.damage).get("crit", this::setIsCritical, new Boolean[0]).get("clip", this::setNoClip, new Boolean[0]).get("PierceLevel", this::setPierce, new Byte[0]).get("OwnerUUID", v -> this.shootingEntity = v, true, new UUID[0]).get("model", this::setModelName, new String[0]).get("Delay", this::setDelay, new Integer[0]);
    }
    
    public IPacket<?> getAddEntityPacket() {
        return (IPacket<?>)NetworkHooks.getEntitySpawningPacket((Entity)this);
    }
    
    public void shoot(final double x, final double y, final double z, final float velocity, final float inaccuracy) {
        final Vector3d vec3d = new Vector3d(x, y, z).normalize().add(this.random.nextGaussian() * 0.007499999832361937 * inaccuracy, this.random.nextGaussian() * 0.007499999832361937 * inaccuracy, this.random.nextGaussian() * 0.007499999832361937 * inaccuracy).scale((double)velocity);
        this.setDeltaMovement(vec3d);
        final float f = MathHelper.sqrt(getHorizontalDistanceSqr(vec3d));
        this.yRot = (float)(MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875);
        this.xRot = (float)(MathHelper.atan2(vec3d.y, (double)f) * 57.2957763671875);
        this.yRotO = this.yRot;
        this.xRotO = this.xRot;
        this.ticksInGround = 0;
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
        this.setDeltaMovement(x, y, z);
        if (this.xRotO == 0.0f && this.yRotO == 0.0f) {
            final float f = MathHelper.sqrt(x * x + z * z);
            this.xRot = (float)(MathHelper.atan2(y, (double)f) * 57.2957763671875);
            this.yRot = (float)(MathHelper.atan2(x, z) * 57.2957763671875);
            this.xRotO = this.xRot;
            this.yRotO = this.yRot;
            this.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, this.xRot);
            this.ticksInGround = 0;
        }
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
            final int newValue = (int)this.entityData.get((DataParameter)EntityAbstractSummonedSword.FLAGS);
            if (this.intFlags != newValue) {
                this.intFlags = newValue;
                EnumSetConverter.convertToEnumSet(this.flags, FlagsState.values(), this.intFlags);
            }
        }
        else {
            final int newValue = EnumSetConverter.convertToInt(this.flags);
            if (this.intFlags != newValue) {
                this.entityData.set((DataParameter)EntityAbstractSummonedSword.FLAGS, (Object)newValue);
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
        if (this.getHitEntity() != null) {
            final Entity hits = this.getHitEntity();
            if (!hits.isAlive()) {
                this.burst();
            }
            else {
                this.setPos(hits.getX(), hits.getY() + hits.getEyeHeight() * 0.5f, hits.getZ());
                int delay = this.getDelay();
                --delay;
                this.setDelay(delay);
                if (!this.level.isClientSide && delay < 0) {
                    this.burst();
                }
            }
            return;
        }
        final boolean disallowedHitBlock = this.isNoClip();
        final BlockPos blockpos = new BlockPos(this.getX(), this.getY(), this.getZ());
        final BlockState blockstate = this.level.getBlockState(blockpos);
        if (!blockstate.isAir((IBlockReader)this.level, blockpos) && !disallowedHitBlock) {
            final VoxelShape voxelshape = blockstate.getCollisionShape((IBlockReader)this.level, blockpos);
            if (!voxelshape.isEmpty()) {
                for (final AxisAlignedBB axisalignedbb : voxelshape.toAabbs()) {
                    if (axisalignedbb.move(blockpos).contains(new Vector3d(this.getX(), this.getY(), this.getZ()))) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }
        if (this.isInWaterOrRain()) {
            this.clearFire();
        }
        if (this.inGround && !disallowedHitBlock) {
            if (this.inBlockState != blockstate && this.level.noCollision(this.getBoundingBox().inflate(0.06))) {
                this.burst();
            }
            else if (!this.level.isClientSide) {
                this.tryDespawn();
            }
        }
        else {
            Vector3d motionVec = this.getDeltaMovement();
            if (this.xRotO == 0.0f && this.yRotO == 0.0f) {
                final float f = MathHelper.sqrt(getHorizontalDistanceSqr(motionVec));
                this.yRot = (float)(MathHelper.atan2(motionVec.x, motionVec.z) * 57.2957763671875);
                this.xRot = (float)(MathHelper.atan2(motionVec.y, (double)f) * 57.2957763671875);
                this.yRotO = this.yRot;
                this.xRotO = this.xRot;
            }
            ++this.ticksInAir;
            final Vector3d positionVec = this.position();
            Vector3d movedVec = positionVec.add(motionVec);
            RayTraceResult raytraceresult = (RayTraceResult)this.level.clip(new RayTraceContext(positionVec, movedVec, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, (Entity)this));
            if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
                movedVec = raytraceresult.getLocation();
            }
            while (this.isAlive()) {
                EntityRayTraceResult entityraytraceresult = this.getRayTrace(positionVec, movedVec);
                if (entityraytraceresult != null) {
                    raytraceresult = (RayTraceResult)entityraytraceresult;
                }
                if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
                    final Entity entity = ((EntityRayTraceResult)raytraceresult).getEntity();
                    final Entity entity2 = this.getShooter();
                    if (entity instanceof PlayerEntity && entity2 instanceof PlayerEntity && !((PlayerEntity)entity2).canHarmPlayer((PlayerEntity)entity)) {
                        raytraceresult = null;
                        entityraytraceresult = null;
                    }
                }
                if (raytraceresult != null && !disallowedHitBlock && !ForgeEventFactory.onProjectileImpact((Entity)this, raytraceresult)) {
                    this.onHit(raytraceresult);
                    this.hasImpulse = true;
                }
                if (entityraytraceresult == null) {
                    break;
                }
                if (this.getPierce() <= 0) {
                    break;
                }
                raytraceresult = null;
            }
            motionVec = this.getDeltaMovement();
            final double mx = motionVec.x;
            final double my = motionVec.y;
            final double mz = motionVec.z;
            if (this.getIsCritical()) {
                for (int i = 0; i < 4; ++i) {
                    this.level.addParticle((IParticleData)ParticleTypes.CRIT, this.getX() + mx * i / 4.0, this.getY() + my * i / 4.0, this.getZ() + mz * i / 4.0, -mx, -my + 0.2, -mz);
                }
            }
            this.setPos(this.getX() + mx, this.getY() + my, this.getZ() + mz);
            final float f2 = MathHelper.sqrt(getHorizontalDistanceSqr(motionVec));
            if (disallowedHitBlock) {
                this.yRot = (float)(MathHelper.atan2(-mx, -mz) * 57.2957763671875);
            }
            else {
                this.yRot = (float)(MathHelper.atan2(mx, mz) * 57.2957763671875);
            }
            this.xRot = (float)(MathHelper.atan2(my, (double)f2) * 57.2957763671875);
            while (this.xRot - this.xRotO < -180.0f) {
                this.xRotO -= 360.0f;
            }
            while (this.xRot - this.xRotO >= 180.0f) {
                this.xRotO += 360.0f;
            }
            while (this.yRot - this.yRotO < -180.0f) {
                this.yRotO -= 360.0f;
            }
            while (this.yRot - this.yRotO >= 180.0f) {
                this.yRotO += 360.0f;
            }
            this.xRot = MathHelper.lerp(0.2f, this.xRotO, this.xRot);
            this.yRot = MathHelper.lerp(0.2f, this.yRotO, this.yRot);
            final float f3 = 0.99f;
            final float f4 = 0.05f;
            if (this.isInWater()) {
                for (int j = 0; j < 4; ++j) {
                    final float f5 = 0.25f;
                    this.level.addParticle((IParticleData)ParticleTypes.BUBBLE, this.getX() - mx * 0.25, this.getY() - my * 0.25, this.getZ() - mz * 0.25, mx, my, mz);
                }
            }
            this.setDeltaMovement(motionVec.scale((double)f3));
            if (!this.isNoGravity() && !disallowedHitBlock) {
                final Vector3d vec3d3 = this.getDeltaMovement();
                this.setDeltaMovement(vec3d3.x, vec3d3.y - 0.05000000074505806, vec3d3.z);
            }
            this.checkInsideBlocks();
        }
        if (!this.level.isClientSide && this.ticksInGround <= 0 && 100 < this.tickCount) {
            this.remove();
        }
    }
    
    protected void tryDespawn() {
        ++this.ticksInGround;
        if (100 <= this.ticksInGround) {
            this.burst();
        }
    }
    
    protected void onHit(final RayTraceResult raytraceResultIn) {
        final RayTraceResult.Type type = raytraceResultIn.getType();
        switch (type) {
            case ENTITY: {
                this.onHitEntity((EntityRayTraceResult)raytraceResultIn);
                break;
            }
            case BLOCK: {
                this.onHitBlock((BlockRayTraceResult)raytraceResultIn);
                break;
            }
        }
    }
    
    protected void onHitBlock(final BlockRayTraceResult blockraytraceresult) {
        final BlockState blockstate = this.level.getBlockState(blockraytraceresult.getBlockPos());
        this.inBlockState = blockstate;
        final Vector3d vec3d = blockraytraceresult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(vec3d);
        final Vector3d vec3d2 = this.position().subtract(vec3d.normalize().scale(0.05000000074505806));
        this.setPos(vec3d2.x, vec3d2.y, vec3d2.z);
        this.playSound(this.getHitGroundSound(), 1.0f, 2.2f / (this.random.nextFloat() * 0.2f + 0.9f));
        this.inGround = true;
        this.setIsCritical(false);
        this.setPierce((byte)0);
        this.resetAlreadyHits();
        blockstate.onProjectileHit(this.level, blockstate, blockraytraceresult, (ProjectileEntity)this);
    }
    
    public void doForceHitEntity(final Entity target) {
        this.onHitEntity(new EntityRayTraceResult(target));
    }
    
    protected void onHitEntity(final EntityRayTraceResult p_213868_1_) {
        final Entity targetEntity = p_213868_1_.getEntity();
        int i = MathHelper.ceil(this.getDamage());
        if (this.getPierce() > 0) {
            if (this.alreadyHits == null) {
                this.alreadyHits = new IntOpenHashSet(5);
            }
            if (this.alreadyHits.size() >= this.getPierce() + 1) {
                this.burst();
                return;
            }
            this.alreadyHits.add(targetEntity.getId());
        }
        if (this.getIsCritical()) {
            i += this.random.nextInt(i / 2 + 2);
        }
        final Entity shooter = this.getShooter();
        DamageSource damagesource;
        if (shooter == null) {
            damagesource = CustomDamageSource.causeSummonedSwordDamage(this, (Entity)this);
        }
        else {
            damagesource = CustomDamageSource.causeSummonedSwordDamage(this, shooter);
            if (shooter instanceof LivingEntity) {
                Entity hits = targetEntity;
                if (targetEntity instanceof PartEntity) {
                    hits = ((PartEntity)targetEntity).getParent();
                }
                ((LivingEntity)shooter).setLastHurtMob(hits);
            }
        }
        final int fireTime = targetEntity.getRemainingFireTicks();
        if (this.isOnFire() && !(targetEntity instanceof EndermanEntity)) {
            targetEntity.setSecondsOnFire(5);
        }
        if (targetEntity.hurt(damagesource, (float)i)) {
            if (targetEntity instanceof LivingEntity) {
                final LivingEntity targetLivingEntity = (LivingEntity)targetEntity;
                StunManager.setStun(targetLivingEntity);
                if (!this.level.isClientSide && this.getPierce() <= 0) {
                    Entity hits2 = targetEntity;
                    if (targetEntity instanceof PartEntity) {
                        hits2 = ((PartEntity)targetEntity).getParent();
                    }
                    this.setHitEntity(hits2);
                }
                if (!this.level.isClientSide && shooter instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(targetLivingEntity, shooter);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)shooter, (Entity)targetLivingEntity);
                }
                this.affectEntity(targetLivingEntity, this.getPotionEffects(), 1.0);
                if (shooter != null && targetLivingEntity != shooter && targetLivingEntity instanceof PlayerEntity && shooter instanceof ServerPlayerEntity) {
                    ((ServerPlayerEntity)shooter).playNotifySound(this.getHitEntityPlayerSound(), SoundCategory.PLAYERS, 0.18f, 0.45f);
                }
            }
            this.playSound(this.getHitEntitySound(), 1.0f, 1.2f / (this.random.nextFloat() * 0.2f + 0.9f));
            if (this.getPierce() <= 0 && (this.getHitEntity() == null || !this.getHitEntity().isAlive())) {
                this.burst();
            }
        }
        else {
            targetEntity.setRemainingFireTicks(fireTime);
            this.yRot += 180.0f;
            this.yRotO += 180.0f;
            this.ticksInAir = 0;
            if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7) {
                this.burst();
            }
        }
    }
    
    public int getColor() {
        return (int)this.getEntityData().get((DataParameter)EntityAbstractSummonedSword.COLOR);
    }
    
    public void setColor(final int value) {
        this.getEntityData().set((DataParameter)EntityAbstractSummonedSword.COLOR, (Object)value);
    }
    
    public byte getPierce() {
        return (byte)this.getEntityData().get((DataParameter)EntityAbstractSummonedSword.PIERCE);
    }
    
    public void setPierce(final byte value) {
        this.getEntityData().set((DataParameter)EntityAbstractSummonedSword.PIERCE, (Object)value);
    }
    
    public int getDelay() {
        return (int)this.getEntityData().get((DataParameter)EntityAbstractSummonedSword.DELAY);
    }
    
    public void setDelay(final int value) {
        this.getEntityData().set((DataParameter)EntityAbstractSummonedSword.DELAY, (Object)value);
    }
    
    @Nullable
    protected EntityRayTraceResult getRayTrace(final Vector3d p_213866_1_, final Vector3d p_213866_2_) {
        return ProjectileHelper.getEntityHitResult(this.level, (Entity)this, p_213866_1_, p_213866_2_, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0), p_213871_1_ -> !p_213871_1_.isSpectator() && p_213871_1_.isAlive() && p_213871_1_.isPickable() && (p_213871_1_ != this.getShooter() || this.ticksInAir >= 5) && (this.alreadyHits == null || !this.alreadyHits.contains(p_213871_1_.getId())));
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
        this.playSound(SoundEvents.GLASS_BREAK, 1.0f, 1.2f / (this.random.nextFloat() * 0.2f + 0.9f));
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
    
    private void resetAlreadyHits() {
        if (this.alreadyHits != null) {
            this.alreadyHits.clear();
        }
    }
    
    public void setHitEntity(final Entity hitEntity) {
        if (hitEntity != this) {
            this.entityData.set((DataParameter)EntityAbstractSummonedSword.HIT_ENTITY_ID, (Object)hitEntity.getId());
            this.entityData.set((DataParameter)EntityAbstractSummonedSword.OFFSET_YAW, (Object)(this.random.nextFloat() * 360.0f));
            this.setDelay(100);
        }
    }
    
    @Nullable
    public Entity getHitEntity() {
        if (this.hitEntity == null) {
            final int id = (int)this.entityData.get((DataParameter)EntityAbstractSummonedSword.HIT_ENTITY_ID);
            if (0 <= id) {
                this.hitEntity = this.level.getEntity(id);
            }
        }
        return this.hitEntity;
    }
    
    public float getOffsetYaw() {
        return (float)this.entityData.get((DataParameter)EntityAbstractSummonedSword.OFFSET_YAW);
    }
    
    public float getRoll() {
        return (float)this.entityData.get((DataParameter)EntityAbstractSummonedSword.ROLL);
    }
    
    public void setRoll(final float value) {
        this.entityData.set((DataParameter)EntityAbstractSummonedSword.ROLL, (Object)value);
    }
    
    public void setDamage(final double damageIn) {
        this.damage = damageIn;
    }
    
    public double getDamage() {
        return this.damage;
    }
    
    public void setModelName(final String name) {
        this.entityData.set((DataParameter)EntityAbstractSummonedSword.MODEL, (Object)Optional.ofNullable(name).orElse("slashblade:model/util/ss"));
    }
    
    public String getModelName() {
        String name = (String)this.entityData.get((DataParameter)EntityAbstractSummonedSword.MODEL);
        if (name == null || name.length() == 0) {
            name = "slashblade:model/util/ss";
        }
        return name;
    }
    
    public ResourceLocation getModelLoc() {
        return (ResourceLocation)this.modelLoc.orElse((Object)EntityAbstractSummonedSword.defaultModel);
    }
    
    public ResourceLocation getTextureLoc() {
        return (ResourceLocation)this.textureLoc.orElse((Object)EntityAbstractSummonedSword.defaultTexture);
    }
    
    public void push(final Entity entityIn) {
    }
    
    static {
        COLOR = EntityDataManager.defineId((Class)EntityAbstractSummonedSword.class, DataSerializers.INT);
        FLAGS = EntityDataManager.defineId((Class)EntityAbstractSummonedSword.class, DataSerializers.INT);
        HIT_ENTITY_ID = EntityDataManager.defineId((Class)EntityAbstractSummonedSword.class, DataSerializers.INT);
        OFFSET_YAW = EntityDataManager.defineId((Class)EntityAbstractSummonedSword.class, DataSerializers.FLOAT);
        ROLL = EntityDataManager.defineId((Class)EntityAbstractSummonedSword.class, DataSerializers.FLOAT);
        PIERCE = EntityDataManager.defineId((Class)EntityAbstractSummonedSword.class, DataSerializers.BYTE);
        MODEL = EntityDataManager.defineId((Class)EntityAbstractSummonedSword.class, DataSerializers.STRING);
        DELAY = EntityDataManager.defineId((Class)EntityAbstractSummonedSword.class, DataSerializers.INT);
        defaultModel = new ResourceLocation("slashblade:model/util/ss.obj");
        defaultTexture = new ResourceLocation("slashblade:model/util/ss.png");
    }
    
    enum FlagsState
    {
        Critical, 
        NoClip;
    }
}
