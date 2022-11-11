//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.client.renderer.entity;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.*;
import net.minecraft.entity.item.*;
import com.mojang.blaze3d.matrix.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.vector.*;
import net.minecraft.util.*;
import mods.flammpfeil.slashblade.item.*;
import net.minecraftforge.common.capabilities.*;
import mods.flammpfeil.slashblade.client.renderer.model.*;
import mods.flammpfeil.slashblade.client.renderer.util.*;
import net.minecraft.item.*;
import java.util.*;
import mods.flammpfeil.slashblade.client.renderer.model.obj.*;
import net.minecraft.entity.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;

public class BladeItemEntityRenderer extends ItemRenderer
{
    public BladeItemEntityRenderer(final EntityRendererManager renderManagerIn) {
        super(renderManagerIn, Minecraft.getInstance().getItemRenderer());
    }
    
    public boolean shouldSpreadItems() {
        return false;
    }
    
    public boolean shouldBob() {
        return false;
    }
    
    public void render(final ItemEntity itemIn, final float entityYaw, float partialTicks, final MatrixStack matrixStackIn, final IRenderTypeBuffer bufferIn, final int packedLightIn) {
        this.shadowRadius = 0.0f;
        if (!itemIn.getItem().isEmpty()) {
            this.renderBlade(itemIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
        else {
            partialTicks = (float)(itemIn.bobOffs * 20.0 - itemIn.getAge());
            super.render(itemIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }
    
    private void renderBlade(final ItemEntity itemIn, final float entityYaw, final float partialTicks, final MatrixStack matrixStackIn, final IRenderTypeBuffer bufferIn, final int packedLightIn) {
        try (final MSAutoCloser msac = MSAutoCloser.pushMatrix(matrixStackIn)) {
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(entityYaw));
            final ItemStack current = itemIn.getItem();
            final EnumSet<SwordType> types = SwordType.from(current);
            final ItemStack itemStack;
            final ResourceLocation modelLocation = current.getCapability((Capability)ItemSlashBlade.BLADESTATE).map(state -> state.getModel().orElseGet(() -> BladeModelManager.resourceDefaultModel)).orElseGet(() -> {
                if (itemStack.hasTag() && itemStack.getTag().contains("Model")) {
                    return new ResourceLocation(itemStack.getTag().getString("Model"));
                }
                else {
                    return BladeModelManager.resourceDefaultModel;
                }
            });
            final ItemStack itemStack2;
            final ResourceLocation textureLocation = current.getCapability((Capability)ItemSlashBlade.BLADESTATE).map(state -> state.getTexture().orElseGet(() -> BladeModelManager.resourceDefaultTexture)).orElseGet(() -> {
                if (itemStack2.hasTag() && itemStack2.getTag().contains("Texture")) {
                    return new ResourceLocation(itemStack2.getTag().getString("Texture"));
                }
                else {
                    return BladeModelManager.resourceDefaultTexture;
                }
            });
            final WavefrontObject model = BladeModelManager.getInstance().getModel(modelLocation);
            final float scale = 0.00625f;
            try (final MSAutoCloser msac2 = MSAutoCloser.pushMatrix(matrixStackIn)) {
                float xOffset = 0.0f;
                float heightOffset;
                String renderTarget;
                if (types.contains(SwordType.EdgeFragment)) {
                    heightOffset = 225.0f;
                    xOffset = 200.0f;
                    renderTarget = "blade_fragment";
                }
                else if (types.contains(SwordType.Broken)) {
                    heightOffset = 100.0f;
                    xOffset = 30.0f;
                    renderTarget = "blade_damaged";
                }
                else {
                    heightOffset = 225.0f;
                    xOffset = 120.0f;
                    renderTarget = "blade";
                }
                if (itemIn.isInWater()) {
                    matrixStackIn.translate(0.0, 0.02500000037252903, 0.0);
                    matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(itemIn.bobOffs));
                    matrixStackIn.scale(scale, scale, scale);
                    matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0f));
                }
                else if (!itemIn.isOnGround()) {
                    matrixStackIn.scale(scale, scale, scale);
                    final float speed = -81.0f;
                    matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(speed * (itemIn.tickCount + partialTicks)));
                    matrixStackIn.translate((double)xOffset, 0.0, 0.0);
                }
                else {
                    matrixStackIn.scale(scale, scale, scale);
                    matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(60.0f + (float)Math.toDegrees(itemIn.bobOffs / 6.0)));
                    matrixStackIn.translate((double)heightOffset, 0.0, 0.0);
                }
                BladeRenderState.renderOverrided(current, model, renderTarget, textureLocation, matrixStackIn, bufferIn, packedLightIn);
                BladeRenderState.renderOverridedLuminous(current, model, renderTarget + "_luminous", textureLocation, matrixStackIn, bufferIn, packedLightIn);
            }
            if ((itemIn.isInWater() || itemIn.isOnGround()) && !types.contains(SwordType.NoScabbard)) {
                try (final MSAutoCloser msac2 = MSAutoCloser.pushMatrix(matrixStackIn)) {
                    matrixStackIn.translate(0.0, 0.02500000037252903, 0.0);
                    matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(itemIn.bobOffs));
                    if (!itemIn.isInWater()) {
                        matrixStackIn.translate(0.75, 0.0, -0.4);
                    }
                    matrixStackIn.scale(scale, scale, scale);
                    matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0f));
                    final String renderTarget2 = "sheath";
                    BladeRenderState.renderOverrided(current, model, renderTarget2, textureLocation, matrixStackIn, bufferIn, packedLightIn);
                    BladeRenderState.renderOverridedLuminous(current, model, renderTarget2 + "_luminous", textureLocation, matrixStackIn, bufferIn, packedLightIn);
                }
            }
        }
    }
}
