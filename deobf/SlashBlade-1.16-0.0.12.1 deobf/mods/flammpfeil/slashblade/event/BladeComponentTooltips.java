//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.event;

import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.inventory.container.*;
import net.minecraft.util.text.*;
import mods.flammpfeil.slashblade.item.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraft.enchantment.*;
import java.util.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;

public class BladeComponentTooltips
{
    public static BladeComponentTooltips getInstance() {
        return SingletonHolder.instance;
    }
    
    private BladeComponentTooltips() {
    }
    
    public void register() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onItemTooltipEvent(final ItemTooltipEvent event) {
        final List<ITextComponent> tooltip = (List<ITextComponent>)event.getToolTip();
        final ItemStack stack = event.getItemStack();
        final AnvilCraftingRecipe recipe = AnvilCraftingRecipe.getRecipe(stack);
        if (recipe == null) {
            return;
        }
        ItemStack blade = ItemStack.EMPTY;
        boolean hasAnvil = false;
        if (event.getPlayer() != null && event.getPlayer().containerMenu instanceof RepairContainer) {
            hasAnvil = true;
            blade = event.getPlayer().containerMenu.getSlot(0).getItem();
        }
        tooltip.add((ITextComponent)new TranslationTextComponent("slashblade.tooltip.material").withStyle(TextFormatting.DARK_AQUA));
        tooltip.add(this.getRequirements("slashblade.tooltip.material.requiredobjects.anvil", hasAnvil, new Object[0]));
        tooltip.add(this.getRequirements(recipe.getTranslationKey(), recipe.getTranslationKey().equals(blade.getDescriptionId()), new Object[0]));
        if (0 < recipe.getKillcount()) {
            tooltip.add(this.getRequirements("slashblade.tooltip.material.killcount", blade.getCapability((Capability)ItemSlashBlade.BLADESTATE).filter(s -> recipe.getKillcount() <= s.getKillCount()).isPresent(), recipe.getKillcount()));
        }
        if (0 < recipe.getRefine()) {
            tooltip.add(this.getRequirements("slashblade.tooltip.material.refine", blade.getCapability((Capability)ItemSlashBlade.BLADESTATE).filter(s -> recipe.getRefine() <= s.getRefine()).isPresent(), recipe.getRefine()));
        }
        if (recipe.isBroken()) {
            tooltip.add(this.getRequirements("slashblade.tooltip.material.broken", blade.getCapability((Capability)ItemSlashBlade.BLADESTATE).filter(s -> s.isBroken()).isPresent(), new Object[0]));
        }
        if (recipe.isNoScabbard()) {
            tooltip.add(this.getRequirements("slashblade.tooltip.material.noscabbard", blade.getCapability((Capability)ItemSlashBlade.BLADESTATE).filter(s -> s.isNoScabbard()).isPresent(), new Object[0]));
        }
        if (0 < recipe.getEnchantments().size()) {
            tooltip.add(this.getRequirements("slashblade.tooltip.material.enchantments", this.checkEnchantments(recipe.getEnchantments(), blade), recipe.getLevel()));
        }
        if (0 < recipe.getLevel()) {
            tooltip.add(this.getRequirements("slashblade.tooltip.material.level", event.getPlayer() != null && recipe.getLevel() <= event.getPlayer().experienceLevel, recipe.getLevel()));
        }
    }
    
    private boolean checkEnchantments(final Map<Enchantment, Integer> requirements, final ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        if (!stack.isEnchanted()) {
            return false;
        }
        for (final Map.Entry<Enchantment, Integer> entry : requirements.entrySet()) {
            if (entry.getValue() > EnchantmentHelper.getItemEnchantmentLevel((Enchantment)entry.getKey(), stack)) {
                return false;
            }
        }
        return true;
    }
    
    ITextComponent getRequirements(final String key, final boolean check, final Object... args) {
        final TranslationTextComponent tc = new TranslationTextComponent(key, args);
        if (check) {
            tc.withStyle(TextFormatting.GREEN);
        }
        return (ITextComponent)tc;
    }
    
    private static final class SingletonHolder
    {
        private static final BladeComponentTooltips instance;
        
        static {
            instance = new BladeComponentTooltips(null);
        }
    }
}
