//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.event.client;

import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraft.item.*;
import mods.flammpfeil.slashblade.client.renderer.model.obj.*;
import net.minecraft.util.*;
import com.mojang.blaze3d.matrix.*;
import net.minecraft.client.renderer.*;
import net.minecraftforge.common.*;

@Cancelable
@OnlyIn(Dist.CLIENT)
public class RenderOverrideEvent extends Event
{
    ItemStack stack;
    WavefrontObject model;
    String target;
    ResourceLocation texture;
    MatrixStack matrixStack;
    IRenderTypeBuffer buffer;
    WavefrontObject originalModel;
    String originalTarget;
    ResourceLocation originalTexture;
    
    public ResourceLocation getTexture() {
        return this.texture;
    }
    
    public void setTexture(final ResourceLocation texture) {
        this.texture = texture;
    }
    
    public ResourceLocation getOriginalTexture() {
        return this.originalTexture;
    }
    
    public WavefrontObject getOriginalModel() {
        return this.originalModel;
    }
    
    public String getOriginalTarget() {
        return this.originalTarget;
    }
    
    public ItemStack getStack() {
        return this.stack;
    }
    
    public WavefrontObject getModel() {
        return this.model;
    }
    
    public void setModel(final WavefrontObject model) {
        this.model = model;
    }
    
    public String getTarget() {
        return this.target;
    }
    
    public void setTarget(final String target) {
        this.target = target;
    }
    
    public MatrixStack getMatrixStack() {
        return this.matrixStack;
    }
    
    public IRenderTypeBuffer getBuffer() {
        return this.buffer;
    }
    
    public RenderOverrideEvent(final ItemStack stack, final WavefrontObject model, final String target, final ResourceLocation texture, final MatrixStack matrixStack, final IRenderTypeBuffer buffer) {
        this.stack = stack;
        this.model = model;
        this.originalModel = model;
        this.target = target;
        this.originalTarget = target;
        this.texture = texture;
        this.originalTexture = texture;
        this.matrixStack = matrixStack;
        this.buffer = buffer;
    }
    
    public static RenderOverrideEvent onRenderOverride(final ItemStack stack, final WavefrontObject model, final String target, final ResourceLocation texture, final MatrixStack matrixStack, final IRenderTypeBuffer buffer) {
        final RenderOverrideEvent event = new RenderOverrideEvent(stack, model, target, texture, matrixStack, buffer);
        MinecraftForge.EVENT_BUS.post((Event)event);
        return event;
    }
}
