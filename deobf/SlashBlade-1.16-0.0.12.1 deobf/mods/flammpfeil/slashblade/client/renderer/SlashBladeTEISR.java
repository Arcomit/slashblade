//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.client.renderer;

import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.model.*;
import com.mojang.blaze3d.matrix.*;
import net.minecraft.client.renderer.*;
import mods.flammpfeil.slashblade.entity.*;
import mods.flammpfeil.slashblade.item.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraftforge.common.capabilities.*;
import mods.flammpfeil.slashblade.client.renderer.model.*;
import mods.flammpfeil.slashblade.client.renderer.util.*;
import java.awt.*;
import net.minecraft.util.math.*;
import mods.flammpfeil.slashblade.client.renderer.model.obj.*;
import net.minecraft.util.math.vector.*;
import mods.flammpfeil.slashblade.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;

public class SlashBladeTEISR extends ItemStackTileEntityRenderer
{
    private void bindTexture(final ResourceLocation res) {
        Minecraft.getInstance().getTextureManager().bind(res);
    }
    
    public void renderByItem(final ItemStack itemStackIn, final ItemCameraTransforms.TransformType type, final MatrixStack matrixStack, final IRenderTypeBuffer bufferIn, final int combinedLightIn, final int combinedOverlayIn) {
        if (!(itemStackIn.getItem() instanceof ItemSlashBlade)) {
            return;
        }
        final ItemSlashBlade item = (ItemSlashBlade)itemStackIn.getItem();
        if (itemStackIn.hasTag() && itemStackIn.getTag().contains("SlashBladeIcon")) {
            itemStackIn.readShareTag(itemStackIn.getTag());
            itemStackIn.removeTagKey("SlashBladeIcon");
        }
        this.renderBlade(itemStackIn, type, matrixStack, bufferIn, combinedLightIn, combinedOverlayIn);
    }
    
    boolean checkRenderNaked() {
        final ItemStack mainHand = BladeModel.user.getMainHandItem();
        return !(mainHand.getItem() instanceof ItemSlashBlade);
    }
    
    private boolean renderBlade(final ItemStack stack, final ItemCameraTransforms.TransformType transformType, final MatrixStack matrixStack, final IRenderTypeBuffer bufferIn, final int combinedLightIn, final int combinedOverlayIn) {
        if (transformType != ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND && transformType != ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND && transformType != ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND && transformType != ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND && transformType != ItemCameraTransforms.TransformType.NONE) {
            try (final MSAutoCloser msacA = MSAutoCloser.pushMatrix(matrixStack)) {
                matrixStack.translate(0.5, 0.5, 0.5);
                if (transformType == ItemCameraTransforms.TransformType.GROUND) {
                    matrixStack.translate(0.0, 0.15000000596046448, 0.0);
                    this.renderIcon(stack, matrixStack, bufferIn, combinedLightIn, 0.005f);
                }
                else if (transformType == ItemCameraTransforms.TransformType.GUI) {
                    this.renderIcon(stack, matrixStack, bufferIn, combinedLightIn, 0.008f, true);
                }
                else if (transformType == ItemCameraTransforms.TransformType.FIXED) {
                    if (stack.isFramed() && stack.getFrame() instanceof BladeStandEntity) {
                        this.renderModel(stack, matrixStack, bufferIn, combinedLightIn);
                    }
                    else {
                        matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0f));
                        this.renderIcon(stack, matrixStack, bufferIn, combinedLightIn, 0.0095f);
                    }
                }
                else {
                    this.renderIcon(stack, matrixStack, bufferIn, combinedLightIn, 0.0095f);
                }
            }
            return true;
        }
        if (BladeModel.user == null) {
            return false;
        }
        final EnumSet<SwordType> types = SwordType.from(stack);
        boolean handle = false;
        if (!types.contains(SwordType.NoScabbard)) {
            handle = ((BladeModel.user.getMainArm() == HandSide.RIGHT) ? (transformType == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND) : (transformType == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND));
        }
        if (handle) {
            BladeFirstPersonRender.getInstance().render(matrixStack, bufferIn, combinedLightIn);
        }
        return false;
    }
    
    private void renderIcon(final ItemStack stack, final MatrixStack matrixStack, final IRenderTypeBuffer bufferIn, final int lightIn, final float scale) {
        this.renderIcon(stack, matrixStack, bufferIn, lightIn, scale, false);
    }
    
    private void renderIcon(final ItemStack stack, final MatrixStack matrixStack, final IRenderTypeBuffer bufferIn, final int lightIn, final float scale, final boolean renderDurability) {
        matrixStack.scale(scale, scale, scale);
        final EnumSet<SwordType> types = SwordType.from(stack);
        final ResourceLocation modelLocation = stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).filter(s -> s.getModel().isPresent()).map(s -> (ResourceLocation)s.getModel().get()).orElseGet(() -> BladeModelManager.resourceDefaultModel);
        final WavefrontObject model = BladeModelManager.getInstance().getModel(modelLocation);
        final ResourceLocation textureLocation = stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).filter(s -> s.getTexture().isPresent()).map(s -> (ResourceLocation)s.getTexture().get()).orElseGet(() -> BladeModelManager.resourceDefaultTexture);
        String renderTarget;
        if (types.contains(SwordType.Broken)) {
            renderTarget = "item_damaged";
        }
        else if (!types.contains(SwordType.NoScabbard)) {
            renderTarget = "item_blade";
        }
        else {
            renderTarget = "item_bladens";
        }
        BladeRenderState.renderOverrided(stack, model, renderTarget, textureLocation, matrixStack, bufferIn, lightIn);
        BladeRenderState.renderOverridedLuminous(stack, model, renderTarget + "_luminous", textureLocation, matrixStack, bufferIn, lightIn);
        if (renderDurability) {
            final WavefrontObject durabilityModel = BladeModelManager.getInstance().getModel(BladeModelManager.resourceDurabilityModel);
            final float durability = stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).map(s -> s.getDurabilityForDisplay()).orElse(0.0f);
            matrixStack.translate(0.0, 0.0, 0.10000000149011612);
            if (BladeModel.user != null && BladeModel.user.getMainHandItem() == stack) {
                BladeRenderState.setCol(new Color(15658734));
                BladeRenderState.renderOverrided(stack, durabilityModel, "base", BladeModelManager.resourceDurabilityTexture, matrixStack, bufferIn, lightIn);
                matrixStack.translate(0.0, 0.0, 0.10000000149011612);
                BladeRenderState.setCol(Color.black);
                BladeRenderState.renderOverrided(stack, durabilityModel, "color_r", BladeModelManager.resourceDurabilityTexture, matrixStack, bufferIn, lightIn);
            }
            else {
                final Color aCol = new Color(0.25f, 0.25f, 0.25f, 1.0f);
                final Color bCol = new Color(10824803);
                final int r = 0xFF & (int)MathHelper.lerp((float)aCol.getRed(), (float)bCol.getRed(), durability);
                final int g = 0xFF & (int)MathHelper.lerp((float)aCol.getGreen(), (float)bCol.getGreen(), durability);
                final int b = 0xFF & (int)MathHelper.lerp((float)aCol.getBlue(), (float)bCol.getBlue(), durability);
                BladeRenderState.setCol(new Color(r, g, b));
                BladeRenderState.renderOverrided(stack, durabilityModel, "base", BladeModelManager.resourceDurabilityTexture, matrixStack, bufferIn, lightIn);
                final boolean isBroken = types.contains(SwordType.Broken);
                matrixStack.translate(0.0, 0.0, (double)(-2.0f * durability));
                BladeRenderState.renderOverrided(stack, durabilityModel, isBroken ? "color_r" : "color", BladeModelManager.resourceDurabilityTexture, matrixStack, bufferIn, lightIn);
            }
        }
    }
    
    private void renderModel(final ItemStack stack, final MatrixStack matrixStack, final IRenderTypeBuffer bufferIn, final int lightIn) {
        final float scale = 0.003125f;
        matrixStack.scale(scale, scale, scale);
        final float defaultOffset = 130.0f;
        matrixStack.translate((double)defaultOffset, 0.0, 0.0);
        final EnumSet<SwordType> types = SwordType.from(stack);
        final ResourceLocation modelLocation = stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).filter(s -> s.getModel().isPresent()).map(s -> (ResourceLocation)s.getModel().get()).orElseGet(() -> BladeModelManager.resourceDefaultModel);
        final WavefrontObject model = BladeModelManager.getInstance().getModel(modelLocation);
        final ResourceLocation textureLocation = stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).filter(s -> s.getTexture().isPresent()).map(s -> (ResourceLocation)s.getTexture().get()).orElseGet(() -> BladeModelManager.resourceDefaultTexture);
        Vector3d bladeOffset = Vector3d.ZERO;
        float bladeOffsetRot = 0.0f;
        final float bladeOffsetBaseRot = -3.0f;
        Vector3d sheathOffset = Vector3d.ZERO;
        float sheathOffsetRot = 0.0f;
        float sheathOffsetBaseRot = -3.0f;
        boolean vFlip = false;
        boolean hFlip = false;
        boolean hasScabbard = !types.contains(SwordType.NoScabbard);
        if (stack.isFramed() && stack.getFrame() instanceof BladeStandEntity) {
            final BladeStandEntity stand = (BladeStandEntity)stack.getFrame();
            final Item type = stand.currentType;
            final Pose pose = stand.getPose();
            switch (pose.ordinal()) {
                case 0: {
                    vFlip = false;
                    hFlip = false;
                    break;
                }
                case 1: {
                    vFlip = true;
                    hFlip = false;
                    break;
                }
                case 2: {
                    vFlip = true;
                    hFlip = true;
                    break;
                }
                case 3: {
                    vFlip = false;
                    hFlip = true;
                    break;
                }
                case 4: {
                    vFlip = false;
                    hFlip = false;
                    hasScabbard = false;
                    break;
                }
                case 5: {
                    vFlip = false;
                    hFlip = true;
                    hasScabbard = false;
                    break;
                }
            }
            if (type == SBItems.bladestand_1) {
                bladeOffset = Vector3d.ZERO;
                sheathOffset = Vector3d.ZERO;
            }
            else if (type == SBItems.bladestand_2) {
                bladeOffset = new Vector3d(0.0, 21.5, 0.0);
                if (hFlip) {
                    sheathOffset = new Vector3d(-40.0, -27.0, 0.0);
                }
                else {
                    sheathOffset = new Vector3d(40.0, -27.0, 0.0);
                }
                sheathOffsetBaseRot = -4.0f;
            }
            else if (type == SBItems.bladestand_v) {
                bladeOffset = new Vector3d(-100.0, 230.0, 0.0);
                sheathOffset = new Vector3d(-100.0, 230.0, 0.0);
                bladeOffsetRot = 80.0f;
                sheathOffsetRot = 80.0f;
            }
            else if (type == SBItems.bladestand_s) {
                if (hFlip) {
                    bladeOffset = new Vector3d(60.0, -25.0, 0.0);
                    sheathOffset = new Vector3d(60.0, -25.0, 0.0);
                }
                else {
                    bladeOffset = new Vector3d(-60.0, -25.0, 0.0);
                    sheathOffset = new Vector3d(-60.0, -25.0, 0.0);
                }
            }
            else if (type == SBItems.bladestand_1w) {
                bladeOffset = Vector3d.ZERO;
                sheathOffset = Vector3d.ZERO;
            }
            else if (type == SBItems.bladestand_2w) {
                bladeOffset = new Vector3d(0.0, 21.5, 0.0);
                if (hFlip) {
                    sheathOffset = new Vector3d(-40.0, -27.0, 0.0);
                }
                else {
                    sheathOffset = new Vector3d(40.0, -27.0, 0.0);
                }
                sheathOffsetBaseRot = -4.0f;
            }
        }
        try (final MSAutoCloser msac = MSAutoCloser.pushMatrix(matrixStack)) {
            String renderTarget;
            if (types.contains(SwordType.Broken)) {
                renderTarget = "blade_damaged";
            }
            else {
                renderTarget = "blade";
            }
            matrixStack.translate(bladeOffset.x, bladeOffset.y, bladeOffset.z);
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(bladeOffsetRot));
            if (vFlip) {
                matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0f));
                matrixStack.translate(0.0, -15.0, 0.0);
                matrixStack.translate(0.0, 5.0, 0.0);
            }
            if (hFlip) {
                final double offset = defaultOffset;
                matrixStack.translate(-offset, 0.0, 0.0);
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0f));
                matrixStack.translate(offset, 0.0, 0.0);
            }
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(bladeOffsetBaseRot));
            BladeRenderState.renderOverrided(stack, model, renderTarget, textureLocation, matrixStack, bufferIn, lightIn);
            BladeRenderState.renderOverridedLuminous(stack, model, renderTarget + "_luminous", textureLocation, matrixStack, bufferIn, lightIn);
        }
        if (hasScabbard) {
            try (final MSAutoCloser msac = MSAutoCloser.pushMatrix(matrixStack)) {
                final String renderTarget = "sheath";
                matrixStack.translate(sheathOffset.x, sheathOffset.y, sheathOffset.z);
                matrixStack.mulPose(Vector3f.ZP.rotationDegrees(sheathOffsetRot));
                if (vFlip) {
                    matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0f));
                    matrixStack.translate(0.0, -15.0, 0.0);
                    matrixStack.translate(0.0, 5.0, 0.0);
                }
                if (hFlip) {
                    final double offset = defaultOffset;
                    matrixStack.translate(-offset, 0.0, 0.0);
                    matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0f));
                    matrixStack.translate(offset, 0.0, 0.0);
                }
                matrixStack.mulPose(Vector3f.ZP.rotationDegrees(sheathOffsetBaseRot));
                BladeRenderState.renderOverrided(stack, model, renderTarget, textureLocation, matrixStack, bufferIn, lightIn);
                BladeRenderState.renderOverridedLuminous(stack, model, renderTarget + "_luminous", textureLocation, matrixStack, bufferIn, lightIn);
            }
        }
    }
}
