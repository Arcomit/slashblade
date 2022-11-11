//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.specialattack;

import mods.flammpfeil.slashblade.entity.*;
import java.util.*;
import net.minecraft.util.math.vector.*;
import mods.flammpfeil.slashblade.item.*;
import mods.flammpfeil.slashblade.util.*;
import net.minecraft.entity.*;
import mods.flammpfeil.slashblade.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;

public class JudgementCut
{
    public static EntityJudgementCut doJudgementCutJust(final LivingEntity user) {
        final EntityJudgementCut sa = doJudgementCut(user);
        sa.setDamage(sa.getDamage() + 1.0);
        sa.setIsCritical(true);
        return sa;
    }
    
    public static EntityJudgementCut doJudgementCut(final LivingEntity user) {
        final World worldIn = user.level;
        final Vector3d eyePos = user.getEyePosition(1.0f);
        final double airReach = 5.0;
        final double entityReach = 7.0;
        final ItemStack stack = user.getMainHandItem();
        Optional<Vector3d> resultPos = stack.getCapability(ItemSlashBlade.BLADESTATE).filter(s -> s.getTargetEntity(worldIn) != null).map(s -> Optional.of(s.getTargetEntity(worldIn).getEyePosition(1.0f))).orElseGet(() -> Optional.empty());
        if (!resultPos.isPresent()) {
            final Optional<RayTraceResult> raytraceresult = RayTraceHelper.rayTrace(worldIn, (Entity)user, eyePos, user.getLookAngle(), 5.0, 7.0, entity -> !((Entity)entity).isSpectator() && ((Entity)entity).isAlive() && ((Entity)entity).isPickable() && entity != user);
            Vector3d pos;
            final RayTraceResult.Type type;
            final Entity target;
            final Vector3d hitVec;
            resultPos = raytraceresult.map(rtr -> {
                pos = null;
                type = ((RayTraceResult)rtr).getType();
                switch (type) {
                    case ENTITY: {
                        target = rtr.getEntity();
                        pos = target.position().add(0.0, (double)(target.getEyeHeight() / 2.0f), 0.0);
                        break;
                    }
                    case BLOCK: {
                        hitVec = (pos = ((RayTraceResult)rtr).getLocation());
                        break;
                    }
                }
                return pos;
            });
        }
        final Vector3d pos2 = resultPos.orElseGet(() -> eyePos.add(user.getLookAngle().scale(5.0)));
        final EntityJudgementCut jc = new EntityJudgementCut(SlashBlade.RegistryEvents.JudgementCut, worldIn);
        jc.setPos(pos2.x, pos2.y, pos2.z);
        jc.setOwner((Entity)user);
        stack.getCapability(ItemSlashBlade.BLADESTATE).ifPresent(state -> jc.setColor(state.getColorCode()));
        worldIn.addFreshEntity((Entity)jc);
        worldIn.playSound((PlayerEntity)null, jc.getX(), jc.getY(), jc.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 0.5f, 0.8f / (user.getRandom().nextFloat() * 0.4f + 0.8f));
        return jc;
    }
}
