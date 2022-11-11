//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.event;

import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.player.*;
import mods.flammpfeil.slashblade.util.*;
import java.util.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraftforge.eventbus.api.*;

public class BladeMaterialTooltips
{
    public static final String BLADE_DATA = "BladeData";
    
    public static BladeMaterialTooltips getInstance() {
        return SingletonHolder.instance;
    }
    
    private BladeMaterialTooltips() {
    }
    
    public void register() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onItemTooltipEvent(final ItemTooltipEvent event) {
        final List<ITextComponent> tooltip = (List<ITextComponent>)event.getToolTip();
        final ItemStack stack = event.getItemStack();
        if (stack.hasTag() && stack.getTag().contains("BladeData")) {
            final CompoundNBT bladeData = stack.getTag().getCompound("BladeData");
            final String translationKey = NBTHelper.getNBTCoupler(bladeData).getChild("tag").getChild("ShareTag").getRawCompound().getString("translationKey");
            event.getToolTip().add(new TranslationTextComponent(translationKey));
        }
    }
    
    private static final class SingletonHolder
    {
        private static final BladeMaterialTooltips instance;
        
        static {
            instance = new BladeMaterialTooltips(null);
        }
    }
}
