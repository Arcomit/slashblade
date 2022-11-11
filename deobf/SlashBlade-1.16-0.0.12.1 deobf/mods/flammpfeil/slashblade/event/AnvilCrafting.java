//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.event;

import net.minecraft.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.*;
import net.minecraft.item.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.entity.player.*;
import net.minecraft.advancements.*;
import java.util.*;

public class AnvilCrafting
{
    private static final ResourceLocation REFORGE;
    
    public static AnvilCrafting getInstance() {
        return SingletonHolder.instance;
    }
    
    private AnvilCrafting() {
    }
    
    public void register() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onAnvilUpdateEvent(final AnvilUpdateEvent event) {
        final ItemStack base = event.getLeft();
        final ItemStack material = event.getRight();
        if (base.isEmpty()) {
            return;
        }
        if (material.isEmpty()) {
            return;
        }
        final AnvilCraftingRecipe recipe = AnvilCraftingRecipe.getRecipe(material);
        if (recipe == null) {
            return;
        }
        if (!recipe.matches(base)) {
            return;
        }
        event.setMaterialCost(1);
        event.setCost(recipe.getLevel());
        event.setOutput(recipe.getResult(base));
    }
    
    @SubscribeEvent
    public void onAnvilRepairEvent(final AnvilRepairEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayerEntity)) {
            return;
        }
        final ItemStack material = event.getIngredientInput();
        final AnvilCraftingRecipe recipe = AnvilCraftingRecipe.getRecipe(material);
        if (recipe == null) {
            return;
        }
        final ItemStack base = event.getItemInput();
        if (!recipe.matches(base)) {
            return;
        }
        grantCriterion((ServerPlayerEntity)event.getPlayer(), AnvilCrafting.REFORGE);
    }
    
    private static void grantCriterion(final ServerPlayerEntity player, final ResourceLocation resourcelocation) {
        final Advancement adv = player.getServer().getAdvancements().getAdvancement(resourcelocation);
        if (adv == null) {
            return;
        }
        final AdvancementProgress advancementprogress = player.getAdvancements().getOrStartProgress(adv);
        if (advancementprogress.isDone()) {
            return;
        }
        for (final String s : advancementprogress.getRemainingCriteria()) {
            player.getAdvancements().award(adv, s);
        }
    }
    
    static {
        REFORGE = new ResourceLocation("slashblade", "tips/reforge");
    }
    
    private static final class SingletonHolder
    {
        private static final AnvilCrafting instance;
        
        static {
            instance = new AnvilCrafting(null);
        }
    }
}
