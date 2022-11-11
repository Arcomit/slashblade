//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.client.renderer.entity;

import mods.flammpfeil.slashblade.entity.*;
import net.minecraft.client.renderer.entity.*;
import com.mojang.blaze3d.matrix.*;
import net.minecraft.client.*;
import net.minecraft.util.math.*;
import mods.flammpfeil.slashblade.client.renderer.util.*;
import net.minecraftforge.client.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.vector.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class PlacePreviewEntityRenderer extends EntityRenderer<PlacePreviewEntity>
{
    public PlacePreviewEntityRenderer(final EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }
    
    public void render(final PlacePreviewEntity entityIn, final float entityYaw, final float partialTicks, final MatrixStack matrixStackIn, final IRenderTypeBuffer bufferIn, final int packedLightIn) {
        final BlockState blockstate = entityIn.getBlockState();
        if (blockstate.getRenderShape() == BlockRenderType.MODEL) {
            final World world = entityIn.getCommandSenderWorld();
            if (blockstate != world.getBlockState(entityIn.blockPosition()) && blockstate.getRenderShape() != BlockRenderType.INVISIBLE) {
                matrixStackIn.pushPose();
                final PlayerEntity player = (PlayerEntity)Minecraft.getInstance().player;
                final Vector3d pos = player.getLookAngle().add(0.0, 0.5, 0.0).scale(3.0).align((EnumSet)EnumSet.of(Direction.Axis.X, Direction.Axis.Y, Direction.Axis.Z));
                final Vector3d offset = entityIn.position().subtract(player.position()).scale(-1.0).align((EnumSet)EnumSet.of(Direction.Axis.X, Direction.Axis.Y, Direction.Axis.Z));
                matrixStackIn.translate(offset.x, offset.y, offset.z);
                matrixStackIn.translate(pos.x, pos.y + 1.0, pos.z);
                final BlockPos blockpos = new BlockPos(entityIn.getX(), entityIn.getBoundingBox().maxY, entityIn.getZ());
                matrixStackIn.translate(0.5, 0.5, 0.0);
                final BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRenderer();
                final Iterator var11 = RenderType.chunkBufferLayers().iterator();
                final RenderType type = BladeRenderState.getPlacePreviewBlendLuminous(this.getEntityTexture(entityIn));
                ForgeHooksClient.setRenderLayer(type);
                blockrendererdispatcher.getModelRenderer().tesselateBlock((IBlockDisplayReader)world, blockrendererdispatcher.getBlockModel(blockstate), blockstate, blockpos, matrixStackIn, bufferIn.getBuffer(type), false, new Random(), blockstate.getSeed(entityIn.blockPosition()), OverlayTexture.NO_OVERLAY);
                ForgeHooksClient.setRenderLayer((RenderType)null);
                matrixStackIn.popPose();
                super.render((Entity)entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
            }
        }
    }
    
    public ResourceLocation getEntityTexture(final PlacePreviewEntity entity) {
        return AtlasTexture.LOCATION_BLOCKS;
    }
}
