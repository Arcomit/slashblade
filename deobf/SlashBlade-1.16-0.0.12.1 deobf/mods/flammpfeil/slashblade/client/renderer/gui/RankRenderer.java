//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.client.renderer.gui;

import net.minecraftforge.api.distmarker.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.screen.*;
import net.minecraft.world.*;
import net.minecraft.client.entity.player.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import mods.flammpfeil.slashblade.capability.concentrationrank.*;
import org.lwjgl.opengl.*;

@OnlyIn(Dist.CLIENT)
public class RankRenderer
{
    static ResourceLocation RankImg;
    
    public static RankRenderer getInstance() {
        return SingletonHolder.instance;
    }
    
    private RankRenderer() {
    }
    
    public void register() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void renderTick(final TickEvent.RenderTickEvent event) {
        final Minecraft mc = Minecraft.getInstance();
        if (event.phase == TickEvent.Phase.START) {
            return;
        }
        if (mc.player == null) {
            return;
        }
        if (!Minecraft.renderNames()) {
            return;
        }
        if (mc.screen != null && !(mc.screen instanceof ChatScreen)) {
            return;
        }
        final World world = (World)mc.level;
        final ClientPlayerEntity player = mc.player;
        final long time = System.currentTimeMillis();
        this.renderRankHud(event.renderTickTime, player, time);
    }
    
    private void renderRankHud(final Float partialTicks, final ClientPlayerEntity player, final long time) {
        final Minecraft mc = Minecraft.getInstance();
        player.getCapability(CapabilityConcentrationRank.RANK_POINT).ifPresent(cr -> {
            final long now = player.level.getGameTime();
            final IConcentrationRank.ConcentrationRanks rank = cr.getRank(now);
            if (rank == IConcentrationRank.ConcentrationRanks.NONE) {
                return;
            }
            GL11.glPushMatrix();
            GL11.glPushAttrib(1048575);
            final int k = mc.getWindow().getGuiScaledWidth();
            final int l = mc.getWindow().getGuiScaledHeight();
            GL11.glTranslatef((float)(k * 2 / 3), (float)(l / 5), 0.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            mc.getTextureManager().bind(RankRenderer.RankImg);
            boolean showTextRank = false;
            final long textTimeout = cr.getLastRankRise() + 20L;
            final long visibleTimeout = cr.getLastUpdate() + 120L;
            if (now < textTimeout) {
                showTextRank = true;
            }
            if (now < visibleTimeout) {
                final int rankOffset = 32 * (rank.level - 1);
                final int textOffset = showTextRank ? 128 : 0;
                final int progress = (int)(33.0f * cr.getRankProgress(now));
                final int progressIcon = (int)(18.0f * cr.getRankProgress(now));
                final int progressIconInv = 17 - progressIcon;
                drawTexturedQuad(0, 0, 0 + textOffset + 64, rankOffset, 64, 32, -95.0);
                drawTexturedQuad(0, progressIconInv + 7, 0 + textOffset, rankOffset + progressIconInv + 7, 64, progressIcon, -90.0);
                drawTexturedQuad(0, 32, 0, 240, 64, 16, -90.0);
                drawTexturedQuad(16, 32, 16, 224, progress, 16, -95.0);
            }
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        });
    }
    
    public static void drawTexturedQuad(final int x, final int y, final int u, final int v, final int width, final int height, final double zLevel) {
        final float var7 = 0.00390625f;
        final float var8 = 0.00390625f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder wr = tessellator.getBuilder();
        wr.begin(7, DefaultVertexFormats.POSITION_TEX);
        wr.vertex((double)(x + 0), (double)(y + height), zLevel).uv((u + 0.0f) * var7, (v + height) * var8).endVertex();
        wr.vertex((double)(x + width), (double)(y + height), zLevel).uv((u + width) * var7, (v + height) * var8).endVertex();
        wr.vertex((double)(x + width), (double)(y + 0), zLevel).uv((u + width) * var7, (v + 0) * var8).endVertex();
        wr.vertex((double)(x + 0), (double)(y + 0), zLevel).uv((u + 0) * var7, (v + 0) * var8).endVertex();
        tessellator.end();
    }
    
    static {
        RankRenderer.RankImg = new ResourceLocation("slashblade", "textures/gui/rank.png");
    }
    
    private static final class SingletonHolder
    {
        private static final RankRenderer instance;
        
        static {
            instance = new RankRenderer(null);
        }
    }
}
