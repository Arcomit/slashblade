//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.ability;

import net.minecraftforge.common.*;
import mods.flammpfeil.slashblade.event.*;
import mods.flammpfeil.slashblade.item.*;
import java.util.function.*;
import net.minecraft.entity.*;
import mods.flammpfeil.slashblade.util.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.event.*;
import net.minecraft.client.*;
import net.minecraft.client.entity.player.*;
import net.minecraftforge.api.distmarker.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;
import net.minecraft.command.arguments.*;
import net.minecraft.util.math.*;
import mods.flammpfeil.slashblade.capability.inputstate.*;

public class LockOnManager
{
    public static LockOnManager getInstance() {
        return SingletonHolder.instance;
    }
    
    private LockOnManager() {
    }
    
    public void register() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onInputChange(final InputCommandEvent event) {
        if (event.getOld().contains(InputCommand.SNEAK) == event.getCurrent().contains(InputCommand.SNEAK)) {
            return;
        }
        final ServerPlayerEntity player = event.getPlayer();
        final ItemStack stack = event.getPlayer().getMainHandItem();
        if (stack.isEmpty()) {
            return;
        }
        if (!(stack.getItem() instanceof ItemSlashBlade)) {
            return;
        }
        Entity targetEntity;
        if (event.getOld().contains(InputCommand.SNEAK) && !event.getCurrent().contains(InputCommand.SNEAK)) {
            targetEntity = null;
        }
        else {
            final Optional<RayTraceResult> result = RayTraceHelper.rayTrace(player.level, (Entity)player, player.getEyePosition(1.0f), player.getLookAngle(), 12.0, 12.0, null);
            final EntityRayTraceResult er;
            final Entity target;
            boolean isMatch;
            final LivingEntity livingEntity;
            Optional<Entity> foundEntity = result.filter(r -> r.getType() == RayTraceResult.Type.ENTITY).filter(r -> {
                er = r;
                target = r.getEntity();
                isMatch = true;
                if (target instanceof LivingEntity) {
                    isMatch = TargetSelector.lockon_focus.test(livingEntity, (LivingEntity)target);
                }
                return isMatch;
            }).map(r -> r.getEntity());
            if (!foundEntity.isPresent()) {
                final List<LivingEntity> entities = (List<LivingEntity>)player.level.getNearbyEntities((Class)LivingEntity.class, TargetSelector.lockon, (LivingEntity)player, player.getBoundingBox().inflate(12.0, 6.0, 12.0));
                foundEntity = entities.stream().map(s -> s).min(Comparator.comparingDouble(e -> e.distanceToSqr((Entity)player)));
            }
            targetEntity = foundEntity.orElse(null);
        }
        stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(s -> s.setTargetEntityId(targetEntity));
    }
    
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onEntityUpdate(final TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
        if (Minecraft.getInstance().player == null) {
            return;
        }
        final ClientPlayerEntity player = Minecraft.getInstance().player;
        final ItemStack stack = player.getMainHandItem();
        if (stack.isEmpty()) {
            return;
        }
        if (!(stack.getItem() instanceof ItemSlashBlade)) {
            return;
        }
        stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(s -> {
            final Entity target = s.getTargetEntity(player.level);
            if (target == null) {
                return;
            }
            if (!target.isAlive()) {
                return;
            }
            final LivingEntity entity = (LivingEntity)player;
            if (!entity.level.isClientSide) {
                return;
            }
            if (!entity.getCapability((Capability)CapabilityInputState.INPUT_STATE).filter(input -> input.getCommands().contains(InputCommand.SNEAK)).isPresent()) {
                return;
            }
            final float partialTicks = Minecraft.getInstance().getFrameTime();
            final float oldYawHead = entity.yHeadRot;
            final float oldYawOffset = entity.yBodyRot;
            final float oldPitch = entity.xRot;
            final float oldYaw = entity.yRot;
            final float prevYawHead = entity.yHeadRotO;
            final float prevYawOffset = entity.yBodyRotO;
            final float prevYaw = entity.yRotO;
            final float prevPitch = entity.xRotO;
            entity.lookAt(EntityAnchorArgument.Type.EYES, target.position().add(0.0, target.getEyeHeight() / 2.0, 0.0));
            float step = 0.125f * partialTicks;
            step *= (float)Math.min(1.0, Math.abs(MathHelper.wrapDegrees(oldYaw - entity.yHeadRot) * 0.5));
            entity.xRot = MathHelper.rotLerp(step, oldPitch, entity.xRot);
            entity.yRot = MathHelper.rotLerp(step, oldYaw, entity.yRot);
            entity.yHeadRot = MathHelper.rotLerp(step, oldYawHead, entity.yHeadRot);
            entity.yBodyRot = oldYawOffset;
            entity.yBodyRotO = prevYawOffset;
            entity.yHeadRotO = prevYawHead;
            entity.yRotO = prevYaw;
            entity.xRotO = prevPitch;
        });
    }
    
    private static final class SingletonHolder
    {
        private static final LockOnManager instance;
        
        static {
            instance = new LockOnManager(null);
        }
    }
}
