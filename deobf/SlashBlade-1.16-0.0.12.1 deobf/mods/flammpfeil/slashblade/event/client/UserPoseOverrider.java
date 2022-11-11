//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.event.client;

import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import mods.flammpfeil.slashblade.item.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import com.mojang.blaze3d.matrix.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.vector.*;
import net.minecraft.nbt.*;

public class UserPoseOverrider
{
    private static final String TAG_ROT = "sb_yrot";
    private static final String TAG_ROT_PREV = "sb_yrot_prev";
    
    public static UserPoseOverrider getInstance() {
        return SingletonHolder.instance;
    }
    
    private UserPoseOverrider() {
    }
    
    public void register() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onRenderPlayerEventPre(final RenderLivingEvent.Pre event) {
        final ItemStack stack = event.getEntity().getMainHandItem();
        if (stack.isEmpty()) {
            return;
        }
        if (!(stack.getItem() instanceof ItemSlashBlade)) {
            return;
        }
        final float rot = event.getEntity().getPersistentData().getFloat("sb_yrot");
        final float rotPrev = event.getEntity().getPersistentData().getFloat("sb_yrot_prev");
        final MatrixStack matrixStackIn = event.getMatrixStack();
        final LivingEntity entityLiving = event.getEntity();
        final float partialTicks = event.getPartialRenderTick();
        final float f = MathHelper.rotLerp(partialTicks, entityLiving.yBodyRotO, entityLiving.yBodyRot);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0f - f));
        anotherPoseRotP(matrixStackIn, entityLiving, partialTicks);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.rotLerp(partialTicks, rot, rotPrev)));
        anotherPoseRotN(matrixStackIn, entityLiving, partialTicks);
        matrixStackIn.mulPose(Vector3f.YN.rotationDegrees(180.0f - f));
    }
    
    public static void anotherPoseRotP(final MatrixStack matrixStackIn, final LivingEntity entityLiving, final float partialTicks) {
        final boolean isPositive = true;
        final float np = 1.0f;
        final float f = entityLiving.getSwimAmount(partialTicks);
        if (entityLiving.isFallFlying()) {
            final float f2 = entityLiving.getFallFlyingTicks() + partialTicks;
            final float f3 = MathHelper.clamp(f2 * f2 / 100.0f, 0.0f, 1.0f);
            if (!entityLiving.isAutoSpinAttack()) {
                matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(1.0f * f3 * (-90.0f - entityLiving.xRot)));
            }
            final Vector3d vector3d = entityLiving.getViewVector(partialTicks);
            final Vector3d vector3d2 = entityLiving.getDeltaMovement();
            final double d0 = Entity.getHorizontalDistanceSqr(vector3d2);
            final double d2 = Entity.getHorizontalDistanceSqr(vector3d);
            if (d0 > 0.0 && d2 > 0.0) {
                final double d3 = (vector3d2.x * vector3d.x + vector3d2.z * vector3d.z) / Math.sqrt(d0 * d2);
                final double d4 = vector3d2.x * vector3d.z - vector3d2.z * vector3d.x;
                matrixStackIn.mulPose(Vector3f.YP.rotation((float)(1.0 * Math.signum(d4) * Math.acos(d3))));
            }
        }
        else if (f > 0.0f) {
            final float f4 = entityLiving.isInWater() ? (-90.0f - entityLiving.xRot) : -90.0f;
            final float f5 = MathHelper.lerp(f, 0.0f, f4);
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(1.0f * f5));
            if (entityLiving.isVisuallySwimming()) {
                matrixStackIn.translate(0.0, -1.0, 0.30000001192092896);
            }
        }
    }
    
    public static void anotherPoseRotN(final MatrixStack matrixStackIn, final LivingEntity entityLiving, final float partialTicks) {
        final boolean isPositive = false;
        final float np = -1.0f;
        final float f = entityLiving.getSwimAmount(partialTicks);
        if (entityLiving.isFallFlying()) {
            final Vector3d vector3d = entityLiving.getViewVector(partialTicks);
            final Vector3d vector3d2 = entityLiving.getDeltaMovement();
            final double d0 = Entity.getHorizontalDistanceSqr(vector3d2);
            final double d2 = Entity.getHorizontalDistanceSqr(vector3d);
            if (d0 > 0.0 && d2 > 0.0) {
                final double d3 = (vector3d2.x * vector3d.x + vector3d2.z * vector3d.z) / Math.sqrt(d0 * d2);
                final double d4 = vector3d2.x * vector3d.z - vector3d2.z * vector3d.x;
                matrixStackIn.mulPose(Vector3f.YP.rotation((float)(-1.0 * Math.signum(d4) * Math.acos(d3))));
            }
            final float f2 = entityLiving.getFallFlyingTicks() + partialTicks;
            final float f3 = MathHelper.clamp(f2 * f2 / 100.0f, 0.0f, 1.0f);
            if (!entityLiving.isAutoSpinAttack()) {
                matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-1.0f * f3 * (-90.0f - entityLiving.xRot)));
            }
        }
        else if (f > 0.0f) {
            if (entityLiving.isVisuallySwimming()) {
                matrixStackIn.translate(0.0, 1.0, -0.30000001192092896);
            }
            final float f4 = entityLiving.isInWater() ? (-90.0f - entityLiving.xRot) : -90.0f;
            final float f5 = MathHelper.lerp(f, 0.0f, f4);
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-1.0f * f5));
        }
    }
    
    public static void setRot(final Entity target, float rotYaw, final boolean isOffset) {
        final CompoundNBT tag = target.getPersistentData();
        final float prevRot = tag.getFloat("sb_yrot");
        tag.putFloat("sb_yrot_prev", prevRot);
        if (isOffset) {
            rotYaw += prevRot;
        }
        tag.putFloat("sb_yrot", rotYaw);
    }
    
    public static void resetRot(final Entity target) {
        final CompoundNBT tag = target.getPersistentData();
        tag.putFloat("sb_yrot_prev", 0.0f);
        tag.putFloat("sb_yrot", 0.0f);
    }
    
    public static void invertRot(final MatrixStack matrixStack, final Entity entity, final float partialTicks) {
        final float rot = entity.getPersistentData().getFloat("sb_yrot");
        final float rotPrev = entity.getPersistentData().getFloat("sb_yrot_prev");
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(MathHelper.rotLerp(partialTicks, rot, rotPrev)));
    }
    
    private static final class SingletonHolder
    {
        private static final UserPoseOverrider instance;
        
        static {
            instance = new UserPoseOverrider(null);
        }
    }
}
