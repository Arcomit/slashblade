//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.client.renderer.entity;

import mods.flammpfeil.slashblade.entity.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraft.item.*;
import javax.annotation.*;
import net.minecraft.client.renderer.entity.*;
import com.mojang.blaze3d.matrix.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.vector.*;
import net.minecraft.util.math.*;
import mods.flammpfeil.slashblade.client.renderer.model.*;
import mods.flammpfeil.slashblade.client.renderer.util.*;
import mods.flammpfeil.slashblade.client.renderer.model.obj.*;
import net.minecraft.entity.*;
import mods.flammpfeil.slashblade.init.*;
import net.minecraft.util.*;

@OnlyIn(Dist.CLIENT)
public class SlashEffectRenderer<T extends EntitySlashEffect> extends EntityRenderer<T>
{
    private static final ResourceLocation modelLocation;
    private static final ResourceLocation textureLocation;
    private static LazyValue<ItemStack> enchantedItem;
    
    @Nullable
    public ResourceLocation getEntityTexture(final T entity) {
        return SlashEffectRenderer.textureLocation;
    }
    
    public SlashEffectRenderer(final EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }
    
    public void render(final T entity, final float entityYaw, final float partialTicks, final MatrixStack matrixStackIn, final IRenderTypeBuffer bufferIn, final int packedLightIn) {
        try (final MSAutoCloser msac = MSAutoCloser.pushMatrix(matrixStackIn)) {
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-MathHelper.lerp(partialTicks, entity.yRotO, entity.yRot) - 90.0f));
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entity.xRotO, entity.xRot)));
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(entity.getRotationRoll()));
            final WavefrontObject model = BladeModelManager.getInstance().getModel(SlashEffectRenderer.modelLocation);
            final int lifetime = entity.getLifetime();
            final float progress = Math.min((float)lifetime, entity.tickCount + partialTicks) / lifetime;
            final double deathTime = lifetime;
            double baseAlpha = Math.min(deathTime, Math.max(0.0f, lifetime - entity.tickCount - partialTicks)) / deathTime;
            baseAlpha = -Math.pow(baseAlpha - 1.0, 4.0) + 1.0;
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(entity.getRotationOffset() - 135.0f * progress));
            matrixStackIn.scale(1.0f, 0.25f, 1.0f);
            final float baseScale = 1.2f;
            matrixStackIn.scale(baseScale, baseScale, baseScale);
            final float yscale = 0.03f;
            final float scale = entity.getBaseSize() * MathHelper.lerp(progress, 0.03f, 0.035f);
            final int color = entity.getColor() & 0xFFFFFF;
            final ResourceLocation rl = this.getEntityTexture(entity);
            final int alpha = (0xFF & (int)(255.0 * baseAlpha)) << 24;
            try (final MSAutoCloser msacb = MSAutoCloser.pushMatrix(matrixStackIn)) {
                final float windscale = entity.getBaseSize() * MathHelper.lerp(progress, 0.035f, 0.03f);
                matrixStackIn.scale(windscale, yscale, windscale);
                Face.setAlphaOverride(Face.alphaOverrideYZZ);
                Face.setUvOperator(1.0f, 1.0f, 0.0f, -0.8f + progress * 0.3f);
                BladeRenderState.setCol(0x222222 | alpha);
                BladeRenderState.renderOverridedColorWrite(ItemStack.EMPTY, model, "base", rl, matrixStackIn, bufferIn, packedLightIn);
            }
            try (final MSAutoCloser msacb = MSAutoCloser.pushMatrix(matrixStackIn)) {
                matrixStackIn.scale(scale, yscale, scale);
                Face.setAlphaOverride(Face.alphaOverrideYZZ);
                Face.setUvOperator(1.0f, 1.0f, 0.0f, -0.35f + progress * -0.15f);
                BladeRenderState.setCol(color | alpha);
                BladeRenderState.renderOverridedColorWrite(ItemStack.EMPTY, model, "base", rl, matrixStackIn, bufferIn, packedLightIn);
            }
            try (final MSAutoCloser msacb = MSAutoCloser.pushMatrix(matrixStackIn)) {
                final float windscale = entity.getBaseSize() * MathHelper.lerp(progress, 0.03f, 0.0375f);
                matrixStackIn.scale(windscale, yscale, windscale);
                Face.setAlphaOverride(Face.alphaOverrideYZZ);
                Face.setUvOperator(1.0f, 1.0f, 0.0f, -0.5f + progress * -0.2f);
                BladeRenderState.setCol(0x404040 | alpha);
                BladeRenderState.renderOverridedLuminous(ItemStack.EMPTY, model, "base", rl, matrixStackIn, bufferIn, packedLightIn);
            }
            try (final MSAutoCloser msacb = MSAutoCloser.pushMatrix(matrixStackIn)) {
                matrixStackIn.scale(scale, yscale, scale);
                Face.setAlphaOverride(Face.alphaOverrideYZZ);
                Face.setUvOperator(1.0f, 1.0f, 0.0f, -0.35f + progress * -0.15f);
                BladeRenderState.setCol(color | alpha);
                BladeRenderState.renderOverridedLuminous(ItemStack.EMPTY, model, "base", rl, matrixStackIn, bufferIn, packedLightIn);
            }
        }
    }
    
    static {
        modelLocation = new ResourceLocation("slashblade", "model/util/slash.obj");
        textureLocation = new ResourceLocation("slashblade", "model/util/slash.png");
        SlashEffectRenderer.enchantedItem = (LazyValue<ItemStack>)new LazyValue(() -> new ItemStack((IItemProvider)SBItems.proudsoul));
    }
}
