//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.client.renderer.entity;

import mods.flammpfeil.slashblade.entity.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraft.util.*;
import javax.annotation.*;
import net.minecraft.client.renderer.entity.*;
import com.mojang.blaze3d.matrix.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.vector.*;
import net.minecraft.util.math.*;
import mods.flammpfeil.slashblade.client.renderer.model.*;
import java.awt.*;
import mods.flammpfeil.slashblade.client.renderer.util.*;
import net.minecraft.item.*;
import mods.flammpfeil.slashblade.client.renderer.model.obj.*;
import net.minecraft.entity.*;

@OnlyIn(Dist.CLIENT)
public class JudgementCutRenderer<T extends EntityJudgementCut> extends EntityRenderer<T>
{
    private static final ResourceLocation modelLocation;
    private static final ResourceLocation textureLocation;
    
    @Nullable
    public ResourceLocation getEntityTexture(final T entity) {
        return JudgementCutRenderer.textureLocation;
    }
    
    public JudgementCutRenderer(final EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }
    
    public void render(final T entity, final float entityYaw, final float partialTicks, final MatrixStack matrixStackIn, final IRenderTypeBuffer bufferIn, final int packedLightIn) {
        try (final MSAutoCloser msac = MSAutoCloser.pushMatrix(matrixStackIn)) {
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entity.yRotO, entity.yRot) - 90.0f));
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entity.xRotO, entity.xRot)));
            final WavefrontObject model = BladeModelManager.getInstance().getModel(JudgementCutRenderer.modelLocation);
            final int lifetime = entity.getLifetime();
            final double deathTime = lifetime;
            double baseAlpha = Math.min(deathTime, Math.max(0.0f, lifetime - entity.tickCount - partialTicks)) / deathTime;
            baseAlpha = -Math.pow(baseAlpha - 1.0, 4.0) + 1.0;
            final int seed = entity.getSeed();
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees((float)seed));
            final float scale = 0.01f;
            matrixStackIn.scale(scale, scale, scale);
            final int color = entity.getColor() & 0xFFFFFF;
            final Color col = new Color(color);
            final float[] hsb = Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), null);
            final int baseColor = Color.HSBtoRGB(0.5f + hsb[0], hsb[1], 0.2f) & 0xFFFFFF;
            try (final MSAutoCloser msacB = MSAutoCloser.pushMatrix(matrixStackIn)) {
                for (int l = 0; l < 5; ++l) {
                    matrixStackIn.scale(0.95f, 0.95f, 0.95f);
                    BladeRenderState.setCol(baseColor | (0xFF & (int)(102.0 * baseAlpha)) << 24);
                    BladeRenderState.renderOverridedReverseLuminous(ItemStack.EMPTY, model, "base", this.getEntityTexture(entity), matrixStackIn, bufferIn, packedLightIn);
                }
            }
            for (int loop = 3, i = 0; i < loop; ++i) {
                try (final MSAutoCloser msacB2 = MSAutoCloser.pushMatrix(matrixStackIn)) {
                    final float cycleTicks = 15.0f;
                    final float wave = (entity.tickCount + cycleTicks / loop * i + partialTicks) % cycleTicks;
                    final float waveScale = 1.0f + 0.03f * wave;
                    matrixStackIn.scale(waveScale, waveScale, waveScale);
                    BladeRenderState.setCol(baseColor | (int)(136.0f * ((cycleTicks - wave) / cycleTicks) * baseAlpha) << 24);
                    BladeRenderState.renderOverridedReverseLuminous(ItemStack.EMPTY, model, "base", this.getEntityTexture(entity), matrixStackIn, bufferIn, packedLightIn);
                }
            }
            for (int windCount = 5, l = 0; l < windCount; ++l) {
                try (final MSAutoCloser msacB3 = MSAutoCloser.pushMatrix(matrixStackIn)) {
                    matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(360.0f / windCount * l));
                    matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(30.0f));
                    final double rotWind = 18.0;
                    final double offsetBase = 7.0;
                    final double offset = l * offsetBase;
                    final double motionLen = offsetBase * (windCount - 1);
                    final double ticks = entity.tickCount + partialTicks + seed;
                    final double offsetTicks = ticks + offset;
                    final double progress = offsetTicks % motionLen / motionLen;
                    double rad = 6.283185307179586;
                    rad *= progress;
                    final float windScale = (float)(0.4 + progress);
                    matrixStackIn.scale(windScale, windScale, windScale);
                    matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees((float)(rotWind * offsetTicks)));
                    final Color cc = new Color(col.getRed(), col.getGreen(), col.getBlue(), 0xFF & (int)Math.min(0.0, 255.0 * Math.sin(rad) * baseAlpha));
                    BladeRenderState.setCol(cc);
                    BladeRenderState.renderOverridedColorWrite(ItemStack.EMPTY, model, "wind", this.getEntityTexture(entity), matrixStackIn, bufferIn, 15728864);
                }
            }
        }
    }
    
    static {
        modelLocation = new ResourceLocation("slashblade", "model/util/slashdim.obj");
        textureLocation = new ResourceLocation("slashblade", "model/util/slashdim.png");
    }
}
