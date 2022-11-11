//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.ability;

import net.minecraft.entity.*;
import mods.flammpfeil.slashblade.item.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.math.vector.*;
import net.minecraft.item.*;
import mods.flammpfeil.slashblade.util.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;
import java.util.*;

public class ArrowReflector
{
    public static boolean isMatch(final Entity arrow, final Entity attacker) {
        return arrow != null && arrow instanceof ProjectileEntity;
    }
    
    public static void doReflect(final Entity arrow, final Entity attacker) {
        if (!isMatch(arrow, attacker)) {
            return;
        }
        arrow.hurtMarked = true;
        if (attacker != null) {
            Vector3d dir = attacker.getLookAngle();
            if (!(attacker instanceof LivingEntity)) {
                final ItemStack stack = ((LivingEntity)attacker).getMainHandItem();
                if (!stack.isEmpty()) {
                    if (stack.getItem() instanceof ItemSlashBlade) {
                        final Entity target = stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).map(s -> s.getTargetEntity(attacker.level)).orElse(null);
                        if (target != null) {
                            dir = arrow.position().subtract(target.getEyePosition(1.0f)).normalize();
                        }
                        else {
                            dir = arrow.position().subtract(attacker.getLookAngle().scale(10.0).add(attacker.getEyePosition(1.0f))).normalize();
                        }
                    }
                }
            }
            arrow.setDeltaMovement(dir);
            ((ProjectileEntity)arrow).shoot(dir.x, dir.y, dir.z, 1.1f, 0.5f);
            arrow.setNoGravity(true);
            if (arrow instanceof AbstractArrowEntity) {
                ((AbstractArrowEntity)arrow).setCritArrow(true);
            }
        }
    }
    
    public static void doTicks(final LivingEntity attacker) {
        final ItemStack stack = attacker.getMainHandItem();
        if (stack.isEmpty()) {
            return;
        }
        if (!(stack.getItem() instanceof ItemSlashBlade)) {
            return;
        }
        stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(s -> {
            int ticks = attacker.getTicksUsingItem();
            if (ticks == 0) {
                return;
            }
            final ComboState old = s.getComboSeq();
            final ComboState current = s.resolvCurrentComboState(attacker);
            if (old != current) {
                ticks -= (int)TimeValueHelper.getTicksFromMSec((float)old.getTimeoutMS());
            }
            final double period = TimeValueHelper.getTicksFromFrames((float)(current.getEndFrame() - current.getStartFrame())) * (1.0f / current.getSpeed());
            if (ticks < period) {
                final List<Entity> founds = TargetSelector.getReflectableEntitiesWithinAABB(attacker);
                founds.forEach(e -> doReflect(e, (Entity)attacker));
            }
        });
    }
}
