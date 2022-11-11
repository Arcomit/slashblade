//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.client.renderer.util;

import java.awt.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import com.mojang.blaze3d.matrix.*;
import java.util.function.*;
import mods.flammpfeil.slashblade.event.client.*;
import mods.flammpfeil.slashblade.client.renderer.model.obj.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.vertex.*;
import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.platform.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.*;

public class BladeRenderState extends RenderState
{
    private static final Color defaultColor;
    private static Color col;
    public static final int MAX_LIGHT = 15728864;
    public static final VertexFormat POSITION_TEX;
    public static final RenderType BLADE_GLINT;
    protected static final RenderState.TransparencyState LIGHTNING_ADDITIVE_TRANSPARENCY;
    protected static final RenderState.TransparencyState LIGHTNING_REVERSE_TRANSPARENCY;
    
    public static void setCol(final int rgba) {
        setCol(rgba, true);
    }
    
    public static void setCol(final int rgb, final boolean hasAlpha) {
        setCol(new Color(rgb, hasAlpha));
    }
    
    public static void setCol(final Color value) {
        BladeRenderState.col = value;
    }
    
    public static void resetCol() {
        BladeRenderState.col = BladeRenderState.defaultColor;
    }
    
    public BladeRenderState(final String p_i225973_1_, final Runnable p_i225973_2_, final Runnable p_i225973_3_) {
        super(p_i225973_1_, p_i225973_2_, p_i225973_3_);
    }
    
    public static void renderOverrided(final ItemStack stack, final WavefrontObject model, final String target, final ResourceLocation texture, final MatrixStack matrixStackIn, final IRenderTypeBuffer bufferIn, final int packedLightIn) {
        renderOverrided(stack, model, target, texture, matrixStackIn, bufferIn, packedLightIn, BladeRenderState::getSlashBladeBlend, true);
    }
    
    public static void renderOverridedColorWrite(final ItemStack stack, final WavefrontObject model, final String target, final ResourceLocation texture, final MatrixStack matrixStackIn, final IRenderTypeBuffer bufferIn, final int packedLightIn) {
        renderOverrided(stack, model, target, texture, matrixStackIn, bufferIn, packedLightIn, BladeRenderState::getSlashBladeBlendColorWrite, true);
    }
    
    public static void renderOverridedLuminous(final ItemStack stack, final WavefrontObject model, final String target, final ResourceLocation texture, final MatrixStack matrixStackIn, final IRenderTypeBuffer bufferIn, final int packedLightIn) {
        renderOverrided(stack, model, target, texture, matrixStackIn, bufferIn, 15728864, BladeRenderState::getSlashBladeBlendLuminous, false);
    }
    
    public static void renderOverridedReverseLuminous(final ItemStack stack, final WavefrontObject model, final String target, final ResourceLocation texture, final MatrixStack matrixStackIn, final IRenderTypeBuffer bufferIn, final int packedLightIn) {
        renderOverrided(stack, model, target, texture, matrixStackIn, bufferIn, 15728864, BladeRenderState::getSlashBladeBlendReverseLuminous, false);
    }
    
    public static void renderOverrided(final ItemStack stack, final WavefrontObject model, final String target, final ResourceLocation texture, final MatrixStack matrixStackIn, final IRenderTypeBuffer bufferIn, final int packedLightIn, final Function<ResourceLocation, RenderType> getRenderType, final boolean enableEffect) {
        final RenderOverrideEvent event = RenderOverrideEvent.onRenderOverride(stack, model, target, texture, matrixStackIn, bufferIn);
        if (event.isCanceled()) {
            return;
        }
        final RenderType rt = getRenderType.apply(event.getTexture());
        IVertexBuilder vb = bufferIn.getBuffer(rt);
        Face.setCol(BladeRenderState.col);
        Face.setLightMap(packedLightIn);
        Face.setMatrix(matrixStackIn);
        event.getModel().tessellateOnly(vb, new String[] { event.getTarget() });
        if (stack.hasFoil()) {
            vb = bufferIn.getBuffer(BladeRenderState.BLADE_GLINT);
            event.getModel().tessellateOnly(vb, new String[] { event.getTarget() });
        }
        Face.resetMatrix();
        Face.resetLightMap();
        Face.resetCol();
        Face.resetAlphaOverride();
        Face.resetUvOperator();
        resetCol();
    }
    
    public static IVertexBuilder getBuffer(final IRenderTypeBuffer bufferIn, final RenderType renderTypeIn, final boolean glintIn) {
        return null;
    }
    
    public static RenderType getSlashBladeBlend(final ResourceLocation p_228638_0_) {
        final RenderType.State state = RenderType.State.builder().setTextureState(new RenderState.TextureState(p_228638_0_, false, false)).setTransparencyState(BladeRenderState.TRANSLUCENT_TRANSPARENCY).setDiffuseLightingState(BladeRenderState.DIFFUSE_LIGHTING).setAlphaState(BladeRenderState.DEFAULT_ALPHA).setLightmapState(BladeRenderState.LIGHTMAP).setShadeModelState(BladeRenderState.SMOOTH_SHADE).createCompositeState(true);
        return (RenderType)RenderType.create("slashblade_blend", WavefrontObject.POSITION_TEX_LMAP_COL_NORMAL, 4, 256, true, false, state);
    }
    
    public static RenderType getSlashBladeBlendColorWrite(final ResourceLocation p_228638_0_) {
        final RenderType.State state = RenderType.State.builder().setTextureState(new RenderState.TextureState(p_228638_0_, false, false)).setTransparencyState(BladeRenderState.TRANSLUCENT_TRANSPARENCY).setDiffuseLightingState(RenderState.NO_DIFFUSE_LIGHTING).setAlphaState(BladeRenderState.DEFAULT_ALPHA).setLightmapState(BladeRenderState.LIGHTMAP).setShadeModelState(BladeRenderState.SMOOTH_SHADE).setWriteMaskState(BladeRenderState.COLOR_WRITE).createCompositeState(true);
        return (RenderType)RenderType.create("slashblade_blend", WavefrontObject.POSITION_TEX_LMAP_COL_NORMAL, 4, 256, true, false, state);
    }
    
    public static RenderType getSlashBladeBlendLuminous(final ResourceLocation p_228638_0_) {
        final RenderType.State state = RenderType.State.builder().setTextureState(new RenderState.TextureState(p_228638_0_, true, false)).setTransparencyState(BladeRenderState.LIGHTNING_ADDITIVE_TRANSPARENCY).setDiffuseLightingState(RenderState.NO_DIFFUSE_LIGHTING).setAlphaState(BladeRenderState.DEFAULT_ALPHA).setLightmapState(RenderState.LIGHTMAP).setShadeModelState(BladeRenderState.SMOOTH_SHADE).setWriteMaskState(BladeRenderState.COLOR_WRITE).createCompositeState(false);
        return (RenderType)RenderType.create("slashblade_blend_luminous", WavefrontObject.POSITION_TEX_LMAP_COL_NORMAL, 4, 256, true, false, state);
    }
    
    public static RenderType getSlashBladeBlendReverseLuminous(final ResourceLocation p_228638_0_) {
        final RenderType.State state = RenderType.State.builder().setTextureState(new RenderState.TextureState(p_228638_0_, true, false)).setTransparencyState(BladeRenderState.LIGHTNING_REVERSE_TRANSPARENCY).setDiffuseLightingState(RenderState.NO_DIFFUSE_LIGHTING).setAlphaState(BladeRenderState.DEFAULT_ALPHA).setLightmapState(RenderState.LIGHTMAP).setShadeModelState(BladeRenderState.SMOOTH_SHADE).setWriteMaskState(BladeRenderState.COLOR_WRITE).createCompositeState(false);
        return (RenderType)RenderType.create("slashblade_blend_luminous", WavefrontObject.POSITION_TEX_LMAP_COL_NORMAL, 4, 256, true, false, state);
    }
    
    public static RenderType getPlacePreviewBlendLuminous(final ResourceLocation p_228638_0_) {
        final RenderType.State state = RenderType.State.builder().setTextureState(new RenderState.TextureState(p_228638_0_, true, false)).setTransparencyState(BladeRenderState.LIGHTNING_ADDITIVE_TRANSPARENCY).setDiffuseLightingState(RenderState.NO_DIFFUSE_LIGHTING).setAlphaState(BladeRenderState.DEFAULT_ALPHA).setLightmapState(RenderState.LIGHTMAP).setShadeModelState(BladeRenderState.SMOOTH_SHADE).setWriteMaskState(BladeRenderState.COLOR_WRITE).createCompositeState(false);
        return (RenderType)RenderType.create("placepreview_blend_luminous", DefaultVertexFormats.BLOCK, 7, 256, true, false, state);
    }
    
    static {
        defaultColor = Color.white;
        BladeRenderState.col = BladeRenderState.defaultColor;
        POSITION_TEX = new VertexFormat(ImmutableList.builder().add((Object)DefaultVertexFormats.ELEMENT_POSITION).add((Object)DefaultVertexFormats.ELEMENT_UV0).build());
        BLADE_GLINT = (RenderType)RenderType.create("blade_glint", BladeRenderState.POSITION_TEX, 4, 256, RenderType.State.builder().setTextureState(new RenderState.TextureState(ItemRenderer.ENCHANT_GLINT_LOCATION, true, false)).setWriteMaskState(BladeRenderState.COLOR_WRITE).setCullState(BladeRenderState.NO_CULL).setDepthTestState(BladeRenderState.EQUAL_DEPTH_TEST).setTransparencyState(BladeRenderState.GLINT_TRANSPARENCY).setTexturingState(BladeRenderState.ENTITY_GLINT_TEXTURING).createCompositeState(false));
        LIGHTNING_ADDITIVE_TRANSPARENCY = new RenderState.TransparencyState("lightning_transparency", () -> {
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            return;
        }, () -> {
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            return;
        });
        LIGHTNING_REVERSE_TRANSPARENCY = new RenderState.TransparencyState("lightning_transparency", () -> {
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            RenderSystem.blendEquation(32779);
        }, () -> {
            RenderSystem.blendEquation(32774);
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
        });
    }
}
