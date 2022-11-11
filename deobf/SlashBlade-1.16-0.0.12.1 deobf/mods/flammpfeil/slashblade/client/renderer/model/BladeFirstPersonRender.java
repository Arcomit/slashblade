//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.client.renderer.model;

import mods.flammpfeil.slashblade.client.renderer.layers.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.entity.*;
import com.mojang.blaze3d.matrix.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.client.settings.*;
import net.minecraft.util.*;
import mods.flammpfeil.slashblade.item.*;
import mods.flammpfeil.slashblade.client.renderer.util.*;
import net.minecraft.util.math.vector.*;
import net.minecraft.client.entity.player.*;
import net.minecraft.item.*;

public class BladeFirstPersonRender
{
    private LayerMainBlade layer;
    
    private BladeFirstPersonRender() {
        this.layer = null;
        final Minecraft mc = Minecraft.getInstance();
        final EntityRenderer renderer = mc.getEntityRenderDispatcher().getRenderer((Entity)mc.player);
        if (renderer instanceof IEntityRenderer) {
            this.layer = new LayerMainBlade((IEntityRenderer)renderer);
        }
    }
    
    public static BladeFirstPersonRender getInstance() {
        return SingletonHolder.instance;
    }
    
    public void render(final MatrixStack matrixStack, final IRenderTypeBuffer bufferIn, final int combinedLightIn) {
        if (this.layer == null) {
            return;
        }
        final Minecraft mc = Minecraft.getInstance();
        final boolean flag = mc.getCameraEntity() instanceof LivingEntity && ((LivingEntity)mc.getCameraEntity()).isSleeping();
        if (mc.options.getCameraType() != PointOfView.FIRST_PERSON || flag || mc.options.hideGui || mc.gameMode.isAlwaysFlying()) {
            return;
        }
        final ClientPlayerEntity player = mc.player;
        final ItemStack stack = player.getItemInHand(Hand.MAIN_HAND);
        if (stack.isEmpty()) {
            return;
        }
        if (!(stack.getItem() instanceof ItemSlashBlade)) {
            return;
        }
        try (final MSAutoCloser msac = MSAutoCloser.pushMatrix(matrixStack)) {
            final MatrixStack.Entry me = matrixStack.last();
            me.pose().setIdentity();
            me.normal().setIdentity();
            matrixStack.translate(0.0, 0.0, -0.5);
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180.0f));
            matrixStack.scale(1.2f, 1.0f, 1.0f);
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(-mc.player.xRot));
            final float partialTicks = mc.getFrameTime();
            this.layer.render(matrixStack, bufferIn, combinedLightIn, (LivingEntity)mc.player, 0.0f, 0.0f, partialTicks, 0.0f, 0.0f, 0.0f);
        }
    }
    
    private static final class SingletonHolder
    {
        private static final BladeFirstPersonRender instance;
        
        static {
            instance = new BladeFirstPersonRender(null);
        }
    }
}
