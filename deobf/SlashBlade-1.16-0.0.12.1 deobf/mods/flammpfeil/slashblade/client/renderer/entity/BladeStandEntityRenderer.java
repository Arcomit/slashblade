//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.client.renderer.entity;

import net.minecraft.client.*;
import net.minecraft.entity.item.*;
import com.mojang.blaze3d.matrix.*;
import net.minecraft.client.renderer.*;
import mods.flammpfeil.slashblade.entity.*;
import net.minecraft.util.*;
import mods.flammpfeil.slashblade.client.renderer.util.*;
import net.minecraft.util.math.vector.*;
import mods.flammpfeil.slashblade.init.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraftforge.common.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.model.*;

public class BladeStandEntityRenderer extends ItemFrameRenderer
{
    private final ItemRenderer itemRenderer;
    
    public BladeStandEntityRenderer(final EntityRendererManager renderManagerIn) {
        super(renderManagerIn, Minecraft.getInstance().getItemRenderer());
        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
    }
    
    public void render(final ItemFrameEntity entity, final float entityYaw, final float partialTicks, final MatrixStack matrixStackIn, final IRenderTypeBuffer bufferIn, final int packedLightIn) {
        if (entity instanceof BladeStandEntity) {
            this.doRender((BladeStandEntity)entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }
    
    public void doRender(final BladeStandEntity entity, final float entityYaw, final float partialTicks, final MatrixStack matrixStackIn, final IRenderTypeBuffer bufferIn, final int packedLightIn) {
        if (entity.currentTypeStack.isEmpty()) {
            if (entity.currentType == null || entity.currentType == Items.AIR) {
                entity.currentTypeStack = new ItemStack((IItemProvider)Items.ITEM_FRAME);
            }
            else {
                entity.currentTypeStack = new ItemStack((IItemProvider)entity.currentType);
            }
            entity.currentTypeStack.setEntityRepresentation((Entity)entity);
        }
        try (final MSAutoCloser msac = MSAutoCloser.pushMatrix(matrixStackIn)) {
            final BlockPos blockpos = entity.getPos();
            final Vector3d vec = Vector3d.upFromBottomCenterOf((Vector3i)blockpos, 0.75).subtract(entity.position());
            matrixStackIn.translate(vec.x, vec.y, vec.z);
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(entity.xRot));
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0f - entity.yRot));
            try (final MSAutoCloser msacB = MSAutoCloser.pushMatrix(matrixStackIn)) {
                final int i = entity.getRotation();
                matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(i * 360.0f / 8.0f));
                matrixStackIn.scale(2.0f, 2.0f, 2.0f);
                final Item type = entity.currentType;
                if (type == SBItems.bladestand_1) {
                    matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-90.0f));
                }
                else if (type == SBItems.bladestand_2) {
                    matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-90.0f));
                }
                else if (type == SBItems.bladestand_v) {
                    matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-90.0f));
                }
                else if (type == SBItems.bladestand_s) {
                    matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-90.0f));
                }
                else if (type == SBItems.bladestand_1w) {
                    matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0f));
                    matrixStackIn.translate(0.0, 0.0, -0.15000000596046448);
                }
                else if (type == SBItems.bladestand_2w) {
                    matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0f));
                    matrixStackIn.translate(0.0, 0.0, -0.15000000596046448);
                }
                matrixStackIn.pushPose();
                matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0f));
                matrixStackIn.scale(0.5f, 0.5f, 0.5f);
                matrixStackIn.translate(0.0, 0.0, 0.44);
                this.renderItem(entity, entity.currentTypeStack, matrixStackIn, bufferIn, packedLightIn);
                matrixStackIn.popPose();
                if (entity.currentType == SBItems.bladestand_1w || type == SBItems.bladestand_2w) {
                    matrixStackIn.translate(0.0, 0.0, -0.1899999976158142);
                }
                else if (entity.currentType == SBItems.bladestand_1) {}
                matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-180.0f));
                this.renderItem(entity, entity.getItem(), matrixStackIn, bufferIn, packedLightIn);
            }
        }
        final RenderNameplateEvent renderNameplateEvent = new RenderNameplateEvent((Entity)entity, entity.getDisplayName(), (EntityRenderer)this, matrixStackIn, bufferIn, packedLightIn, partialTicks);
        MinecraftForge.EVENT_BUS.post((Event)renderNameplateEvent);
        if (renderNameplateEvent.getResult() != Event.Result.DENY && (renderNameplateEvent.getResult() == Event.Result.ALLOW || this.shouldShowName((ItemFrameEntity)entity))) {
            this.renderNameTag((ItemFrameEntity)entity, renderNameplateEvent.getContent(), matrixStackIn, bufferIn, packedLightIn);
        }
    }
    
    private void renderItem(final BladeStandEntity entity, final ItemStack itemstack, final MatrixStack matrixStackIn, final IRenderTypeBuffer bufferIn, final int packedLightIn) {
        if (!itemstack.isEmpty()) {
            final IBakedModel ibakedmodel = this.itemRenderer.getModel(itemstack, entity.level, (LivingEntity)null);
            this.itemRenderer.render(itemstack, ItemCameraTransforms.TransformType.FIXED, false, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, ibakedmodel);
        }
    }
}
