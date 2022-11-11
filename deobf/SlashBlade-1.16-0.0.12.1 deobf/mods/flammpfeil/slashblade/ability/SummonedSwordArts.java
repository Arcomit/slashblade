//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.ability;

import net.minecraftforge.common.*;
import mods.flammpfeil.slashblade.event.*;
import mods.flammpfeil.slashblade.item.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraftforge.eventbus.api.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import net.minecraft.util.math.vector.*;
import net.minecraft.util.math.*;
import mods.flammpfeil.slashblade.*;
import mods.flammpfeil.slashblade.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import mods.flammpfeil.slashblade.util.*;
import net.minecraft.util.*;

public class SummonedSwordArts
{
    public static SummonedSwordArts getInstance() {
        return SingletonHolder.instance;
    }
    
    private SummonedSwordArts() {
    }
    
    public void register() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onInputChange(final InputCommandEvent event) {
        final EnumSet<InputCommand> old = event.getOld();
        final EnumSet<InputCommand> current = event.getCurrent();
        final ServerPlayerEntity sender = event.getPlayer();
        final boolean onDown = !old.contains(InputCommand.M_DOWN) && current.contains(InputCommand.M_DOWN);
        final boolean onUp = old.contains(InputCommand.M_DOWN) && !current.contains(InputCommand.M_DOWN);
        if (onDown) {
            final World worldIn = sender.level;
            sender.getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(state -> {
                if (sender.experienceLevel <= 0) {
                    return;
                }
                sender.giveExperiencePoints(-1);
                final EntityRayTraceResult er;
                final Entity target;
                boolean isMatch;
                final Optional<Entity> foundTarget = Stream.of((Optional[])new Optional[] { Optional.ofNullable(state.getTargetEntity(sender.level)), RayTraceHelper.rayTrace(sender.level, (Entity)sender, sender.getEyePosition(1.0f), sender.getLookAngle(), 12.0, 12.0, null).filter(r -> r.getType() == RayTraceResult.Type.ENTITY).filter(r -> {
                        er = r;
                        target = r.getEntity();
                        isMatch = true;
                        if (target instanceof LivingEntity) {
                            isMatch = TargetSelector.lockon_focus.test((LivingEntity)sender, (LivingEntity)target);
                        }
                        return isMatch;
                    }).map(r -> r.getEntity()) }).filter(Optional::isPresent).map((Function<? super Optional, ? extends Entity>)Optional::get).findFirst();
                final Vector3d start;
                final Vector3d end;
                final RayTraceResult result;
                final Vector3d targetPos = foundTarget.map(e -> new Vector3d(e.getX(), e.getY() + e.getEyeHeight() * 0.5, e.getZ())).orElseGet(() -> {
                    start = sender.getEyePosition(1.0f);
                    end = start.add(sender.getLookAngle().scale(40.0));
                    result = (RayTraceResult)worldIn.clip(new RayTraceContext(start, end, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, (Entity)sender));
                    return result.getLocation();
                });
                final int counter = StatHelper.increase(sender, SlashBlade.RegistryEvents.SWORD_SUMMONED, 1);
                final boolean sided = counter % 2 == 0;
                final EntityAbstractSummonedSword ss = new EntityAbstractSummonedSword(SlashBlade.RegistryEvents.SummonedSword, worldIn);
                final Vector3d pos = sender.getEyePosition(1.0f).add(VectorHelper.getVectorForRotation(0.0f, sender.getViewYRot(0.0f) + 90.0f).scale(sided ? 1.0 : -1.0));
                ss.setPos(pos.x, pos.y, pos.z);
                final Vector3d dir = targetPos.subtract(pos).normalize();
                ss.shoot(dir.x, dir.y, dir.z, 3.0f, 0.0f);
                ss.setOwner((Entity)sender);
                ss.setColor(state.getColorCode());
                ss.setRoll(sender.getRandom().nextFloat() * 360.0f);
                worldIn.addFreshEntity((Entity)ss);
                sender.playNotifySound(SoundEvents.CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 0.2f, 1.45f);
            });
        }
    }
    
    private static final class SingletonHolder
    {
        private static final SummonedSwordArts instance;
        
        static {
            instance = new SummonedSwordArts(null);
        }
    }
}
