//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.client.renderer.layers;

import net.minecraft.client.renderer.entity.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraftforge.common.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.potion.*;
import com.mojang.blaze3d.matrix.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import mods.flammpfeil.slashblade.util.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;
import mods.flammpfeil.slashblade.event.client.*;
import net.minecraft.util.*;
import mods.flammpfeil.slashblade.client.renderer.model.*;
import net.minecraft.util.math.vector.*;
import mods.flammpfeil.slashblade.client.renderer.util.*;
import mods.flammpfeil.slashblade.client.renderer.model.obj.*;
import jp.nyatla.nymmd.*;
import java.io.*;

public class LayerMainBlade<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M>
{
    final LazyOptional<MmdPmdModelMc> bladeholder;
    final LazyOptional<MmdMotionPlayerGL2> motionPlayer;
    
    public LayerMainBlade(final IEntityRenderer<T, M> entityRendererIn) {
        super((IEntityRenderer)entityRendererIn);
        this.bladeholder = (LazyOptional<MmdPmdModelMc>)LazyOptional.of(() -> {
            try {
                return new MmdPmdModelMc(new ResourceLocation("slashblade", "model/bladeholder.pmd"));
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            catch (MmdException e2) {
                e2.printStackTrace();
            }
            catch (IOException e3) {
                e3.printStackTrace();
            }
            return null;
        });
        this.motionPlayer = (LazyOptional<MmdMotionPlayerGL2>)LazyOptional.of(() -> {
            final MmdMotionPlayerGL2 mmp = new MmdMotionPlayerGL2();
            this.bladeholder.ifPresent(pmd -> {
                try {
                    mmp.setPmd((MmdPmdModel_BasicClass)pmd);
                }
                catch (MmdException e) {
                    e.printStackTrace();
                }
            });
            return mmp;
        });
    }
    
    private float modifiedSpeed(final float baseSpeed, final LivingEntity entity) {
        float modif = 6.0f;
        if (EffectUtils.hasDigSpeed(entity)) {
            modif = (float)(6 - (1 + EffectUtils.getDigSpeedAmplification(entity)));
        }
        else if (entity.hasEffect(Effects.DIG_SLOWDOWN)) {
            modif = (float)(6 + (1 + entity.getEffect(Effects.DIG_SLOWDOWN).getAmplifier()) * 2);
        }
        modif /= 6.0f;
        return baseSpeed / modif;
    }
    
    public void render(final MatrixStack matrixStack, final IRenderTypeBuffer bufferIn, final int lightIn, final T entity, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch) {
        final float motionYOffset = 1.5f;
        final double motionScale = 0.125;
        final double modelScaleBase = 0.0078125;
        final ItemStack stack = entity.getItemInHand(Hand.MAIN_HAND);
        final LazyOptional<ISlashBladeState> state = (LazyOptional<ISlashBladeState>)stack.getCapability(CapabilitySlashBlade.BLADESTATE);
        state.ifPresent(s -> this.motionPlayer.ifPresent(mmp -> {
            ComboState combo;
            double time;
            for (combo = s.getComboSeq(), time = TimeValueHelper.getMSecFromTicks(Math.max(0L, entity.level.getGameTime() - s.getLastActionTime()) + partialTicks); combo != ComboState.NONE && combo.getTimeoutMS() < time; time -= combo.getTimeoutMS(), combo = combo.getNextOfTimeout()) {}
            if (combo == ComboState.NONE) {
                combo = s.getComboRoot();
            }
            final MmdVmdMotionMc motion = BladeMotionManager.getInstance().getMotion(combo.getMotionLoc());
            double maxSeconds = 0.0;
            try {
                mmp.setVmd((MmdVmdMotion_BasicClass)motion);
                maxSeconds = TimeValueHelper.getMSecFromFrames(motion.getMaxFrame());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            final double start = TimeValueHelper.getMSecFromFrames((float)combo.getStartFrame());
            final double end = TimeValueHelper.getMSecFromFrames((float)combo.getEndFrame());
            double span = Math.abs(end - start);
            span = Math.min(maxSeconds, span);
            final boolean isRoop = combo.getRoop();
            if (isRoop) {
                time %= span;
            }
            time = Math.min(span, time);
            time += start;
            try {
                mmp.updateMotion((float)time);
            }
            catch (MmdException e2) {
                e2.printStackTrace();
            }
            try (final MSAutoCloser msacA = MSAutoCloser.pushMatrix(matrixStack)) {
                UserPoseOverrider.invertRot(matrixStack, (Entity)entity, partialTicks);
                matrixStack.translate(0.0, (double)motionYOffset, 0.0);
                matrixStack.scale((float)motionScale, (float)motionScale, (float)motionScale);
                matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180.0f));
                final ResourceLocation textureLocation = s.getTexture().orElseGet(() -> BladeModelManager.resourceDefaultTexture);
                final WavefrontObject obj = BladeModelManager.getInstance().getModel(s.getModel().orElse(null));
                try (final MSAutoCloser msac = MSAutoCloser.pushMatrix(matrixStack)) {
                    final int idx = mmp.getBoneIndexByName("hardpointA");
                    if (0 <= idx) {
                        final float[] buf = new float[16];
                        mmp._skinning_mat[idx].getValue(buf);
                        final Matrix4f mat = new Matrix4f(buf);
                        mat.transpose();
                        matrixStack.scale(-1.0f, 1.0f, 1.0f);
                        final MatrixStack.Entry entry = matrixStack.last();
                        entry.pose().multiply(mat);
                        matrixStack.scale(-1.0f, 1.0f, 1.0f);
                    }
                    final float modelScale = (float)(modelScaleBase * (1.0 / motionScale));
                    matrixStack.scale(modelScale, modelScale, modelScale);
                    String part;
                    if (s.isBroken()) {
                        part = "blade_damaged";
                    }
                    else {
                        part = "blade";
                    }
                    BladeRenderState.renderOverrided(stack, obj, part, textureLocation, matrixStack, bufferIn, lightIn);
                    BladeRenderState.renderOverridedLuminous(stack, obj, part + "_luminous", textureLocation, matrixStack, bufferIn, lightIn);
                }
                try (final MSAutoCloser msac = MSAutoCloser.pushMatrix(matrixStack)) {
                    final int idx = mmp.getBoneIndexByName("hardpointB");
                    if (0 <= idx) {
                        final float[] buf = new float[16];
                        mmp._skinning_mat[idx].getValue(buf);
                        final Matrix4f mat = new Matrix4f(buf);
                        mat.transpose();
                        matrixStack.scale(-1.0f, 1.0f, 1.0f);
                        final MatrixStack.Entry entry = matrixStack.last();
                        entry.pose().multiply(mat);
                        matrixStack.scale(-1.0f, 1.0f, 1.0f);
                    }
                    final float modelScale = (float)(modelScaleBase * (1.0 / motionScale));
                    matrixStack.scale(modelScale, modelScale, modelScale);
                    BladeRenderState.renderOverrided(stack, obj, "sheath", textureLocation, matrixStack, bufferIn, lightIn);
                    BladeRenderState.renderOverridedLuminous(stack, obj, "sheath_luminous", textureLocation, matrixStack, bufferIn, lightIn);
                    if (s.isCharged(entity)) {}
                }
            }
        }));
    }
}
