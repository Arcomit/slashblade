//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.event;

import net.minecraft.entity.*;
import net.minecraft.util.math.vector.*;
import mods.flammpfeil.slashblade.util.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.nbt.*;
import net.minecraftforge.eventbus.api.*;

public class KnockBackHandler
{
    static final String NBT_KEY = "knockback_factor";
    
    public static void setCancel(final LivingEntity target) {
        setFactor(target, 0.0, 0.0, 0.0);
    }
    
    public static void setSmash(final LivingEntity target, final double horizontalFactor) {
        setFactor(target, horizontalFactor, 0.0, 0.0);
    }
    
    public static void setVertical(final LivingEntity target, final double verticalFactor) {
        setFactor(target, 0.0, verticalFactor, -verticalFactor);
    }
    
    public static void setFactor(final LivingEntity target, final double horizontalFactor, final double verticalFactor, final double addFallDistance) {
        NBTHelper.putVector3d(target.getPersistentData(), "knockback_factor", new Vector3d(horizontalFactor, verticalFactor, addFallDistance));
    }
    
    @SubscribeEvent
    public static void onLivingKnockBack(final LivingKnockBackEvent event) {
        final LivingEntity target = event.getEntityLiving();
        final CompoundNBT nbt = target.getPersistentData();
        if (!nbt.contains("knockback_factor")) {
            return;
        }
        final Vector3d factor = NBTHelper.getVector3d(nbt, "knockback_factor");
        nbt.remove("knockback_factor");
        if (target.fallDistance < 0.0f) {
            target.fallDistance = 0.0f;
        }
        final LivingEntity livingEntity = target;
        livingEntity.fallDistance += (float)factor.z;
        if (target.getRandom().nextDouble() < target.getAttribute(Attributes.KNOCKBACK_RESISTANCE).getValue()) {
            return;
        }
        target.hasImpulse = true;
        Vector3d motion = target.getDeltaMovement();
        if (factor.x == 0.0) {
            event.setCanceled(true);
            motion = motion.multiply(0.0, 1.0, 0.0);
        }
        else {
            event.setStrength((float)(event.getStrength() * factor.x));
        }
        if (0.0 < factor.y) {
            target.setOnGround(false);
            event.getEntityLiving().setDeltaMovement(motion.x, Math.max(motion.y, factor.y), motion.z);
        }
        else if (factor.y < 0.0) {
            event.getEntityLiving().setDeltaMovement(motion.x, Math.min(motion.y, factor.y), motion.z);
        }
    }
}
