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
import mods.flammpfeil.slashblade.client.renderer.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import mods.flammpfeil.slashblade.client.renderer.model.obj.*;

@OnlyIn(Dist.CLIENT)
public class SummonedSwordRenderer<T extends EntityAbstractSummonedSword> extends EntityRenderer<T>
{
    @Nullable
    public ResourceLocation getEntityTexture(final T entity) {
        return entity.getTextureLoc();
    }
    
    public SummonedSwordRenderer(final EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }
    
    public void render(final T entity, final float entityYaw, final float partialTicks, final MatrixStack matrixStack, final IRenderTypeBuffer bufferIn, final int packedLightIn) {
        try (final MSAutoCloser msac = MSAutoCloser.pushMatrix(matrixStack)) {
            final Entity hits = entity.getHitEntity();
            final boolean hasHitEntity = hits != null;
            if (hasHitEntity) {
                matrixStack.mulPose(Vector3f.YN.rotationDegrees(MathHelper.lerp(partialTicks, hits.yRotO, hits.yRot) - 90.0f));
                matrixStack.mulPose(Vector3f.YN.rotationDegrees(entity.getOffsetYaw()));
            }
            else {
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entity.yRotO, entity.yRot) - 90.0f));
            }
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entity.xRotO, entity.xRot)));
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(entity.getRoll()));
            final float scale = 0.0075f;
            matrixStack.scale(scale, scale, scale);
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0f));
            if (hasHitEntity) {
                matrixStack.translate(0.0, 0.0, -100.0);
            }
            final WavefrontObject model = BladeModelManager.getInstance().getModel(entity.getModelLoc());
            BladeRenderState.setCol(entity.getColor(), false);
            BladeRenderState.renderOverridedLuminous(ItemStack.EMPTY, model, "ss", this.getEntityTexture(entity), matrixStack, bufferIn, packedLightIn);
        }
    }
}
