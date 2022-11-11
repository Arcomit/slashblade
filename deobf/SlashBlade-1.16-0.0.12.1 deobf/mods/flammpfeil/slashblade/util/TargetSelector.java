//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.util;

import net.minecraft.entity.projectile.*;
import mods.flammpfeil.slashblade.entity.*;
import java.util.stream.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import com.google.common.collect.*;
import java.util.function.*;
import net.minecraft.util.math.vector.*;
import net.minecraftforge.common.*;
import net.minecraft.entity.ai.attributes.*;
import mods.flammpfeil.slashblade.event.*;
import mods.flammpfeil.slashblade.item.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraftforge.eventbus.api.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;
import net.minecraft.world.server.*;
import net.minecraft.particles.*;
import net.minecraft.entity.boss.dragon.*;
import javax.annotation.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;

public class TargetSelector
{
    public static final EntityPredicate lockon;
    public static final EntityPredicate lockon_focus;
    static final String AttackableTag = "RevengeAttacker";
    public static final EntityPredicate areaAttack;
    
    static boolean isAttackable(final Entity revengeTarget, final Entity attacker) {
        return revengeTarget != null && attacker != null && (revengeTarget == attacker || revengeTarget.isAlliedTo(attacker));
    }
    
    public static EntityPredicate getAreaAttackPredicate(final double reach) {
        return TargetSelector.areaAttack.range(reach);
    }
    
    public static List<Entity> getReflectableEntitiesWithinAABB(final LivingEntity attacker) {
        final double reach = getResolvedReach(attacker);
        final AxisAlignedBB aabb = getResolvedAxisAligned(attacker.getBoundingBox(), attacker.getLookAngle(), reach);
        final World world = attacker.level;
        final Object o;
        return Stream.of(world.getEntitiesOfClass((Class)ProjectileEntity.class, aabb).stream().filter(e -> (((ProjectileEntity)e).getOwner() == null || ((ProjectileEntity)e).getOwner() != attacker) && (!(e instanceof IShootable) || e.getShooter() != attacker))).flatMap(s -> s).filter(e -> e.distanceToSqr((Entity)attacker) < o * o).collect((Collector<? super Object, ?, List<Entity>>)Collectors.toList());
    }
    
    public static List<Entity> getExtinguishableEntitiesWithinAABB(final LivingEntity attacker) {
        final double reach = getResolvedReach(attacker);
        final AxisAlignedBB aabb = getResolvedAxisAligned(attacker.getBoundingBox(), attacker.getLookAngle(), reach);
        final World world = attacker.level;
        final Object o;
        return (List<Entity>)world.getEntitiesOfClass((Class)TNTEntity.class, aabb).stream().filter(e -> e.distanceToSqr((Entity)attacker) < o * o).collect(Collectors.toList());
    }
    
    public static List<Entity> getTargettableEntitiesWithinAABB(final World world, final LivingEntity attacker) {
        final double reach = getResolvedReach(attacker);
        final List<Entity> list1 = (List<Entity>)Lists.newArrayList();
        final AxisAlignedBB aabb = getResolvedAxisAligned(attacker.getBoundingBox(), attacker.getLookAngle(), reach);
        final Object o;
        list1.addAll((Collection<? extends Entity>)world.getEntitiesOfClass((Class)EnderDragonEntity.class, aabb.inflate(5.0)).stream().flatMap(d -> Arrays.stream(d.getSubEntities())).filter(e -> e.distanceToSqr((Entity)attacker) < o * o).collect(Collectors.toList()));
        list1.addAll(getReflectableEntitiesWithinAABB(attacker));
        list1.addAll(getExtinguishableEntitiesWithinAABB(attacker));
        final EntityPredicate predicate = getAreaAttackPredicate(reach);
        list1.addAll((Collection<? extends Entity>)world.getEntitiesOfClass((Class)LivingEntity.class, aabb, (Predicate)null).stream().filter(t -> predicate.test(attacker, t)).collect(Collectors.toList()));
        return list1;
    }
    
    public static <E extends net.minecraft.entity.Entity> List<Entity> getTargettableEntitiesWithinAABB(final World world, final double reach, final E owner) {
        final AxisAlignedBB aabb = ((Entity)owner).getBoundingBox().inflate(reach);
        final List<Entity> list1 = (List<Entity>)Lists.newArrayList();
        list1.addAll((Collection<? extends Entity>)world.getEntitiesOfClass((Class)EnderDragonEntity.class, aabb.inflate(5.0)).stream().flatMap(d -> Arrays.stream(d.getSubEntities())).filter(e -> e.distanceToSqr((Entity)owner) < reach * reach).collect(Collectors.toList()));
        LivingEntity user;
        if (((IShootable)owner).getShooter() instanceof LivingEntity) {
            user = (LivingEntity)((IShootable)owner).getShooter();
        }
        else {
            user = null;
        }
        list1.addAll(getReflectableEntitiesWithinAABB(world, reach, (Entity)owner));
        final EntityPredicate predicate = getAreaAttackPredicate(0.0);
        list1.addAll((Collection<? extends Entity>)world.getEntitiesOfClass((Class)LivingEntity.class, aabb, (Predicate)null).stream().filter(t -> predicate.test(user, t)).collect(Collectors.toList()));
        return list1;
    }
    
    public static <E extends net.minecraft.entity.Entity> List<Entity> getReflectableEntitiesWithinAABB(final World world, final double reach, final E owner) {
        final AxisAlignedBB aabb = ((Entity)owner).getBoundingBox().inflate(reach);
        return Stream.of(world.getEntitiesOfClass((Class)ProjectileEntity.class, aabb).stream().filter(e -> e.getOwner() == null || e.getOwner() != ((IShootable)owner).getShooter())).flatMap(s -> s).filter(e -> ((ProjectileEntity)e).distanceToSqr((Entity)owner) < reach * reach && e != owner).collect((Collector<? super Object, ?, List<Entity>>)Collectors.toList());
    }
    
    public static AxisAlignedBB getResolvedAxisAligned(AxisAlignedBB bb, final Vector3d dir, final double reach) {
        final double padding = 1.0;
        if (dir == Vector3d.ZERO) {
            bb = bb.inflate(reach * 2.0);
        }
        else {
            bb = bb.move(dir.scale(reach * 0.5)).inflate(reach);
        }
        bb = bb.inflate(1.0);
        return bb;
    }
    
    public static double getResolvedReach(final LivingEntity user) {
        double reach = 4.0;
        final ModifiableAttributeInstance attrib = user.getAttribute((Attribute)ForgeMod.REACH_DISTANCE.get());
        if (attrib != null) {
            reach = attrib.getValue() - 1.0;
        }
        return reach;
    }
    
    @SubscribeEvent
    public static void onInputChange(final InputCommandEvent event) {
        final EnumSet<InputCommand> old = (EnumSet<InputCommand>)event.getOld();
        final EnumSet<InputCommand> current = (EnumSet<InputCommand>)event.getCurrent();
        final ServerPlayerEntity sender = event.getPlayer();
        if (old.contains(InputCommand.M_DOWN) || !current.contains(InputCommand.M_DOWN) || !current.contains(InputCommand.SNEAK)) {
            return;
        }
        final ItemStack stack = sender.getMainHandItem();
        if (stack.isEmpty()) {
            return;
        }
        if (!(stack.getItem() instanceof ItemSlashBlade)) {
            return;
        }
        stack.getCapability(ItemSlashBlade.BLADESTATE).ifPresent(s -> {
            final Entity tmp = s.getTargetEntity(sender.level);
            if (tmp == null) {
                return;
            }
            if (!(tmp instanceof LivingEntity)) {
                return;
            }
            final LivingEntity target = (LivingEntity)tmp;
            if (target.getLastHurtByMob() == sender) {
                return;
            }
            target.setLastHurtByMob((LivingEntity)sender);
            if (target.level instanceof ServerWorld) {
                final ServerWorld sw = (ServerWorld)target.level;
                sw.sendParticles(sender, (IParticleData)ParticleTypes.ANGRY_VILLAGER, false, target.getX(), target.getY() + target.getEyeHeight(), target.getZ(), 5, target.getBbWidth() * 1.5, (double)target.getBbHeight(), target.getBbWidth() * 1.5, 0.02);
            }
        });
    }
    
    static {
        lockon = new EntityPredicate().range(12.0).selector((Predicate)new AttackablePredicate());
        lockon_focus = new EntityPredicate().range(12.0);
        areaAttack = new EntityPredicate() {
            public boolean test(@Nullable final LivingEntity attacker, final LivingEntity target) {
                boolean isAttackable = false;
                isAttackable |= TargetSelector.isAttackable((Entity)target.getLastHurtByMob(), (Entity)attacker);
                if (!isAttackable && target instanceof MobEntity) {
                    isAttackable |= TargetSelector.isAttackable((Entity)((MobEntity)target).getTarget(), (Entity)attacker);
                }
                if (isAttackable) {
                    target.addTag("RevengeAttacker");
                }
                return super.test(attacker, target);
            }
        }.range(12.0).ignoreInvisibilityTesting().selector((Predicate)new AttackablePredicate());
    }
    
    public static class AttackablePredicate implements Predicate<LivingEntity>
    {
        @Override
        public boolean test(final LivingEntity livingentity) {
            if (livingentity instanceof ArmorStandEntity) {
                return ((ArmorStandEntity)livingentity).isMarker();
            }
            if (livingentity instanceof IMob) {
                return true;
            }
            if (livingentity.isGlowing()) {
                return true;
            }
            if (livingentity instanceof WolfEntity && ((WolfEntity)livingentity).isAngry()) {
                return true;
            }
            if (livingentity.getTags().contains("RevengeAttacker")) {
                livingentity.removeTag("RevengeAttacker");
                return true;
            }
            return livingentity.getTeam() != null;
        }
    }
}
