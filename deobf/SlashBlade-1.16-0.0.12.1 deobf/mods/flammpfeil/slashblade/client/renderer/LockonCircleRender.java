//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.client.renderer;

import net.minecraft.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.*;
import mods.flammpfeil.slashblade.item.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraftforge.eventbus.api.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;
import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.platform.*;
import mods.flammpfeil.slashblade.client.renderer.model.*;
import java.awt.*;
import mods.flammpfeil.slashblade.client.renderer.model.obj.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.vector.*;
import net.minecraft.client.renderer.*;
import mods.flammpfeil.slashblade.capability.inputstate.*;
import mods.flammpfeil.slashblade.util.*;

public class LockonCircleRender
{
    static final ResourceLocation modelLoc;
    static final ResourceLocation textureLoc;
    
    public static LockonCircleRender getInstance() {
        return SingletonHolder.instance;
    }
    
    private LockonCircleRender() {
    }
    
    public void register() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onRenderLiving(final RenderWorldLastEvent event) {
        final PlayerEntity player = (PlayerEntity)Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        if (!player.getCapability(CapabilityInputState.INPUT_STATE).filter(input -> input.getCommands().contains(InputCommand.SNEAK)).isPresent()) {
            return;
        }
        final ItemStack stack = player.getMainHandItem();
        if (stack.isEmpty()) {
            return;
        }
        if (!(stack.getItem() instanceof ItemSlashBlade)) {
            return;
        }
        stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(s -> {
            final Entity tmpTarget = s.getTargetEntity(player.level);
            if (tmpTarget == null) {
                return;
            }
            if (!tmpTarget.isAlive()) {
                return;
            }
            if (!tmpTarget.showVehicleHealth()) {
                return;
            }
            final LivingEntity target = (LivingEntity)tmpTarget;
            final float health = 1.0f - target.getHealth() / target.getMaxHealth();
            final float partialTicks = event.getPartialTicks();
            final ActiveRenderInfo ari = Minecraft.getInstance().gameRenderer.getMainCamera();
            final Vector3d pos = target.getEyePosition(partialTicks).subtract(0.0, target.getEyeHeight() / 2.0, 0.0).subtract(ari.getPosition());
            final float[] col = s.getEffectColor().getColorComponents(null);
            final float alpha = 0.6640625f;
            try {
                RenderSystem.pushMatrix();
                RenderSystem.multMatrix(event.getMatrixStack().last().pose());
                RenderSystem.translated(pos.x, pos.y, pos.z);
                final double scale = 0.00625;
                RenderSystem.scaled(scale, scale, scale);
                final double rotYaw = ari.getYRot();
                final double rotPitch = ari.getXRot();
                RenderSystem.rotatef((float)(rotYaw + 180.0), 0.0f, -1.0f, 0.0f);
                RenderSystem.rotatef((float)rotPitch, -1.0f, 0.0f, 0.0f);
                RenderSystem.disableCull();
                RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                RenderSystem.alphaFunc(519, 0.05f);
                RenderSystem.enableBlend();
                RenderSystem.depthMask(true);
                RenderSystem.enableDepthTest();
                RenderSystem.depthFunc(519);
                RenderSystem.shadeModel(7425);
                final WavefrontObject model = BladeModelManager.getInstance().getModel(LockonCircleRender.modelLoc);
                final ResourceLocation resourceTexture = LockonCircleRender.textureLoc;
                Minecraft.getInstance().getTextureManager().bind(resourceTexture);
                final BufferBuilder bb = Tessellator.getInstance().getBuilder();
                bb.begin(4, WavefrontObject.POSITION_TEX_LMAP_COL_NORMAL);
                Face.setCol(new Color((s.getEffectColor().getRGB() & 0xFFFFFF) | 0xAA000000, true));
                model.tessellatePart((IVertexBuilder)bb, "lockonBase");
                Tessellator.getInstance().end();
                try {
                    RenderSystem.pushMatrix();
                    RenderSystem.translatef(0.0f, 0.0f, health * 10.0f);
                    bb.begin(4, WavefrontObject.POSITION_TEX_LMAP_COL_NORMAL);
                    Face.setCol(new Color(0, true));
                    model.tessellatePart((IVertexBuilder)bb, "lockonHealthMask");
                    Tessellator.getInstance().end();
                }
                finally {
                    RenderSystem.popMatrix();
                }
                RenderSystem.depthFunc(515);
                bb.begin(4, WavefrontObject.POSITION_TEX_LMAP_COL_NORMAL);
                Face.setCol(new Color((s.getEffectColor().getRGB() & 0xFFFFFF) | 0xAA000000, true));
                model.tessellatePart((IVertexBuilder)bb, "lockonHealth");
                Tessellator.getInstance().end();
                Face.resetCol();
                RenderSystem.alphaFunc(518, 0.01f);
                RenderSystem.depthMask(true);
                RenderSystem.shadeModel(7424);
                RenderSystem.enableCull();
                RenderSystem.disableBlend();
                RenderSystem.disableFog();
                RenderSystem.defaultBlendFunc();
            }
            finally {
                RenderSystem.popMatrix();
            }
        });
    }
    
    static {
        modelLoc = new ResourceLocation("slashblade", "model/util/lockon.obj");
        textureLoc = new ResourceLocation("slashblade", "model/util/lockon.png");
    }
    
    private static final class SingletonHolder
    {
        private static final LockonCircleRender instance;
        
        static {
            instance = new LockonCircleRender(null);
        }
    }
}
