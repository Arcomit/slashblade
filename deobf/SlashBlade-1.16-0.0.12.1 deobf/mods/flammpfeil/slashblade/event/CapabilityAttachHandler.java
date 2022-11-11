//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.event;

import net.minecraft.util.*;
import net.minecraftforge.event.*;
import net.minecraft.entity.*;
import mods.flammpfeil.slashblade.capability.inputstate.*;
import net.minecraftforge.common.capabilities.*;
import mods.flammpfeil.slashblade.capability.mobeffect.*;
import mods.flammpfeil.slashblade.capability.concentrationrank.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraft.item.*;
import mods.flammpfeil.slashblade.item.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;

public class CapabilityAttachHandler
{
    public static final ResourceLocation MOBEFFECT_KEY;
    public static final ResourceLocation INPUTSTATE_KEY;
    public static final ResourceLocation RANK_KEY;
    public static final ResourceLocation BLADESTATE_KEY;
    
    @SubscribeEvent
    public void AttachCapabilities_Entity(final AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof LivingEntity)) {
            return;
        }
        event.addCapability(CapabilityAttachHandler.INPUTSTATE_KEY, (ICapabilityProvider)new InputStateCapabilityProvider());
        event.addCapability(CapabilityAttachHandler.MOBEFFECT_KEY, (ICapabilityProvider)new MobEffectCapabilityProvider());
        event.addCapability(CapabilityAttachHandler.RANK_KEY, (ICapabilityProvider)new ConcentrationRankCapabilityProvider());
    }
    
    @SubscribeEvent
    public void AttachCapabilities_ItemStack(final AttachCapabilitiesEvent<ItemStack> event) {
        if (!(((ItemStack)event.getObject()).getItem() instanceof ItemSlashBlade)) {
            return;
        }
        event.addCapability(CapabilityAttachHandler.BLADESTATE_KEY, (ICapabilityProvider)new BladeStateCapabilityProvider());
    }
    
    static {
        MOBEFFECT_KEY = new ResourceLocation("slashblade", "mobeffect");
        INPUTSTATE_KEY = new ResourceLocation("slashblade", "inputstate");
        RANK_KEY = new ResourceLocation("slashblade", "concentration");
        BLADESTATE_KEY = new ResourceLocation("slashblade", "bladestate");
    }
}
