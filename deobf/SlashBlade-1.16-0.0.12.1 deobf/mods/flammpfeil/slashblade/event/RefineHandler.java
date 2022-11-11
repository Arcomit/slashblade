//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.event;

import net.minecraft.util.*;
import net.minecraftforge.event.*;
import mods.flammpfeil.slashblade.item.*;
import net.minecraftforge.common.*;
import net.minecraft.block.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraft.item.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.entity.player.*;
import net.minecraft.advancements.*;
import java.util.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;

public class RefineHandler
{
    private static final ResourceLocation REFINE;
    
    public static RefineHandler getInstance() {
        return SingletonHolder.instance;
    }
    
    private RefineHandler() {
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
        if (!(base.getItem() instanceof ItemSlashBlade)) {
            return;
        }
        if (material.isEmpty()) {
            return;
        }
        final boolean isRepairable = base.getItem().isValidRepairItem(base, material);
        if (!isRepairable) {
            return;
        }
        final int level = material.getHarvestLevel(ToolType.get("proudsoul"), (PlayerEntity)null, (BlockState)null);
        if (level < 0) {
            return;
        }
        final ItemStack result = base.copy();
        final int refineLimit = (6 <= level) ? Integer.MAX_VALUE : Math.max(10, 50 * (level - 1));
        int cost = 0;
        while (cost < material.getCount()) {
            ++cost;
            final float damage = result.getCapability((Capability)ItemSlashBlade.BLADESTATE).map(s -> {
                s.setDamage(s.getDamage() - (0.2f + 0.05f * level));
                if (s.getRefine() < refineLimit) {
                    s.setRefine(s.getRefine() + 1);
                }
                return s.getDamage();
            }).orElse(0.0f);
            if (damage <= 0.0f) {
                break;
            }
        }
        event.setMaterialCost(cost);
        final int levelCostBase = Math.max(1, 2 * (level - 1));
        event.setCost(levelCostBase * cost);
        event.setOutput(result);
    }
    
    @SubscribeEvent
    public void onAnvilRepairEvent(final AnvilRepairEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayerEntity)) {
            return;
        }
        final ItemStack material = event.getIngredientInput();
        final ItemStack base = event.getItemInput();
        if (base.isEmpty()) {
            return;
        }
        if (!(base.getItem() instanceof ItemSlashBlade)) {
            return;
        }
        if (material.isEmpty()) {
            return;
        }
        final boolean isRepairable = base.getItem().isValidRepairItem(base, material);
        if (!isRepairable) {
            return;
        }
        final int level = material.getHarvestLevel(ToolType.get("proudsoul"), (PlayerEntity)null, (BlockState)null);
        if (level < 0) {
            return;
        }
        grantCriterion((ServerPlayerEntity)event.getPlayer(), RefineHandler.REFINE);
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
        REFINE = new ResourceLocation("slashblade", "tips/refine");
    }
    
    private static final class SingletonHolder
    {
        private static final RefineHandler instance;
        
        static {
            instance = new RefineHandler(null);
        }
    }
}
