//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.ability;

import mods.flammpfeil.slashblade.util.*;
import net.minecraftforge.common.*;
import mods.flammpfeil.slashblade.event.*;
import mods.flammpfeil.slashblade.item.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraft.util.math.vector.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.server.*;
import java.util.*;
import net.minecraft.network.play.server.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;
import mods.flammpfeil.slashblade.capability.mobeffect.*;
import mods.flammpfeil.slashblade.*;
import mods.flammpfeil.slashblade.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.math.*;

public class SlayerStyleArts
{
    static final EnumSet<InputCommand> fowerd_sprint_sneak;
    static final EnumSet<InputCommand> move;
    
    public static SlayerStyleArts getInstance() {
        return SingletonHolder.instance;
    }
    
    private SlayerStyleArts() {
    }
    
    public void register() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onInputChange(final InputCommandEvent event) {
        final EnumSet<InputCommand> old = event.getOld();
        final EnumSet<InputCommand> current = event.getCurrent();
        final ServerPlayerEntity sender = event.getPlayer();
        final World worldIn = sender.level;
        if (!old.contains(InputCommand.SPRINT)) {
            boolean isHandled = false;
            if (current.containsAll(SlayerStyleArts.fowerd_sprint_sneak)) {
                isHandled = sender.getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).map(state -> {
                    final Entity target = state.getTargetEntity(worldIn);
                    if (target == null) {
                        return false;
                    }
                    if (target == sender.getLastHurtMob() && sender.tickCount < sender.getLastHurtMobTimestamp() + 100) {
                        final LivingEntity hitEntity = sender.getLastHurtMob();
                        if (hitEntity != null) {
                            doTeleport((Entity)sender, hitEntity);
                        }
                    }
                    else {
                        final EntityAbstractSummonedSword ss = new EntityAbstractSummonedSword(SlashBlade.RegistryEvents.SummonedSword, worldIn) {
                            final /* synthetic */ ServerPlayerEntity val$sender;
                            
                            @Override
                            protected void onHitEntity(final EntityRayTraceResult p_213868_1_) {
                                super.onHitEntity(p_213868_1_);
                                final LivingEntity target = this.val$sender.getLastHurtMob();
                                if (target != null && this.getHitEntity() == target) {
                                    doTeleport((Entity)this.val$sender, target);
                                }
                            }
                        };
                        final Vector3d lastPos = sender.getEyePosition(1.0f);
                        ss.xOld = lastPos.x;
                        ss.yOld = lastPos.y;
                        ss.zOld = lastPos.z;
                        final Vector3d targetPos = target.position().add(0.0, target.getBbHeight() / 2.0, 0.0).add(sender.getLookAngle().scale(-2.0));
                        ss.setPos(targetPos.x, targetPos.y, targetPos.z);
                        final Vector3d dir = sender.getLookAngle();
                        ss.shoot(dir.x, dir.y, dir.z, 0.5f, 0.0f);
                        ss.setOwner((Entity)sender);
                        ss.setDamage(0.009999999776482582);
                        ss.setColor(state.getColorCode());
                        worldIn.addFreshEntity((Entity)ss);
                        sender.playNotifySound(SoundEvents.CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 0.2f, 1.45f);
                        ss.doForceHitEntity(target);
                    }
                    return true;
                }).orElse(false);
            }
            if (!isHandled && sender.isOnGround() && current.contains(InputCommand.SPRINT) && current.stream().anyMatch(cc -> SlayerStyleArts.move.contains(cc))) {
                final int count = sender.getCapability((Capability)CapabilityMobEffect.MOB_EFFECT).map(ef -> ef.doAvoid(sender.level.getGameTime())).orElse(0);
                if (0 < count) {
                    Untouchable.setUntouchable((LivingEntity)sender, 10);
                    final float moveForward = (current.contains(InputCommand.FORWARD) == current.contains(InputCommand.BACK)) ? 0.0f : (current.contains(InputCommand.FORWARD) ? 1.0f : -1.0f);
                    final float moveStrafe = (current.contains(InputCommand.LEFT) == current.contains(InputCommand.RIGHT)) ? 0.0f : (current.contains(InputCommand.LEFT) ? 1.0f : -1.0f);
                    final Vector3d input = new Vector3d((double)moveStrafe, 0.0, (double)moveForward);
                    sender.moveRelative(3.0f, input);
                    final Vector3d motion = this.maybeBackOffFromEdge(sender.getDeltaMovement(), (LivingEntity)sender);
                    sender.playNotifySound(SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 0.5f, 1.2f);
                    sender.move(MoverType.SELF, motion);
                    sender.moveTo(sender.position());
                    sender.getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(state -> state.updateComboSeq((LivingEntity)sender, state.getComboRootAir()));
                }
                isHandled = true;
            }
        }
    }
    
    private static void doTeleport(Entity entityIn, final LivingEntity target) {
        if (!(entityIn.level instanceof ServerWorld)) {
            return;
        }
        if (entityIn instanceof PlayerEntity) {
            final PlayerEntity player = (PlayerEntity)entityIn;
            player.playNotifySound(SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 0.75f, 1.25f);
            player.getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(state -> state.updateComboSeq((LivingEntity)player, state.getComboRootAir()));
            Untouchable.setUntouchable((LivingEntity)player, 10);
        }
        final ServerWorld worldIn = (ServerWorld)entityIn.level;
        final Vector3d tereportPos = target.position().add(0.0, target.getBbHeight() / 2.0, 0.0).add(entityIn.getLookAngle().scale(-2.0));
        final double x = tereportPos.x;
        final double y = tereportPos.y;
        final double z = tereportPos.z;
        final float yaw = entityIn.yRot;
        final float pitch = entityIn.xRot;
        final Set<SPlayerPositionLookPacket.Flags> relativeList = Collections.emptySet();
        final BlockPos blockpos = new BlockPos(x, y, z);
        if (!World.isInSpawnableBounds(blockpos)) {
            return;
        }
        if (entityIn instanceof ServerPlayerEntity) {
            final ChunkPos chunkpos = new ChunkPos(new BlockPos(x, y, z));
            worldIn.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, chunkpos, 1, (Object)entityIn.getId());
            entityIn.stopRiding();
            if (((ServerPlayerEntity)entityIn).isSleeping()) {
                ((ServerPlayerEntity)entityIn).stopSleepInBed(true, true);
            }
            if (worldIn == entityIn.level) {
                ((ServerPlayerEntity)entityIn).connection.teleport(x, y, z, yaw, pitch, (Set)relativeList);
            }
            else {
                ((ServerPlayerEntity)entityIn).teleportTo(worldIn, x, y, z, yaw, pitch);
            }
            entityIn.setYHeadRot(yaw);
        }
        else {
            final float f1 = MathHelper.wrapDegrees(yaw);
            float f2 = MathHelper.wrapDegrees(pitch);
            f2 = MathHelper.clamp(f2, -90.0f, 90.0f);
            if (worldIn == entityIn.level) {
                entityIn.moveTo(x, y, z, f1, f2);
                entityIn.setYHeadRot(f1);
            }
            else {
                entityIn.unRide();
                final Entity entity = entityIn;
                entityIn = entityIn.getType().create((World)worldIn);
                if (entityIn == null) {
                    return;
                }
                entityIn.restoreFrom(entity);
                entityIn.moveTo(x, y, z, f1, f2);
                entityIn.setYHeadRot(f1);
                worldIn.addFromAnotherDimension(entityIn);
            }
        }
        if (!(entityIn instanceof LivingEntity) || !((LivingEntity)entityIn).isFallFlying()) {
            entityIn.setDeltaMovement(entityIn.getDeltaMovement().multiply(1.0, 0.0, 1.0));
            entityIn.setOnGround(false);
        }
        if (entityIn instanceof CreatureEntity) {
            ((CreatureEntity)entityIn).getNavigation().stop();
        }
    }
    
    protected Vector3d maybeBackOffFromEdge(Vector3d vec, final LivingEntity mover) {
        double d0 = vec.x;
        double d2 = vec.z;
        final double d3 = 0.05;
        while (d0 != 0.0 && mover.level.noCollision((Entity)mover, mover.getBoundingBox().move(d0, (double)(-mover.maxUpStep), 0.0))) {
            if (d0 < 0.05 && d0 >= -0.05) {
                d0 = 0.0;
            }
            else if (d0 > 0.0) {
                d0 -= 0.05;
            }
            else {
                d0 += 0.05;
            }
        }
        while (d2 != 0.0 && mover.level.noCollision((Entity)mover, mover.getBoundingBox().move(0.0, (double)(-mover.maxUpStep), d2))) {
            if (d2 < 0.05 && d2 >= -0.05) {
                d2 = 0.0;
            }
            else if (d2 > 0.0) {
                d2 -= 0.05;
            }
            else {
                d2 += 0.05;
            }
        }
        while (d0 != 0.0 && d2 != 0.0 && mover.level.noCollision((Entity)mover, mover.getBoundingBox().move(d0, (double)(-mover.maxUpStep), d2))) {
            if (d0 < 0.05 && d0 >= -0.05) {
                d0 = 0.0;
            }
            else if (d0 > 0.0) {
                d0 -= 0.05;
            }
            else {
                d0 += 0.05;
            }
            if (d2 < 0.05 && d2 >= -0.05) {
                d2 = 0.0;
            }
            else if (d2 > 0.0) {
                d2 -= 0.05;
            }
            else {
                d2 += 0.05;
            }
        }
        vec = new Vector3d(d0, vec.y, d2);
        return vec;
    }
    
    static {
        fowerd_sprint_sneak = EnumSet.of(InputCommand.FORWARD, InputCommand.SPRINT, InputCommand.SNEAK);
        move = EnumSet.of(InputCommand.FORWARD, InputCommand.BACK, InputCommand.LEFT, InputCommand.RIGHT);
    }
    
    private static final class SingletonHolder
    {
        private static final SlayerStyleArts instance;
        
        static {
            instance = new SlayerStyleArts(null);
        }
    }
}
