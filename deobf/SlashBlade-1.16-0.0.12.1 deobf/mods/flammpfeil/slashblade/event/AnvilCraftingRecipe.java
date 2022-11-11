//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.event;

import net.minecraft.item.*;
import com.google.common.collect.*;
import net.minecraft.enchantment.*;
import mods.flammpfeil.slashblade.util.*;
import net.minecraft.nbt.*;
import mods.flammpfeil.slashblade.item.*;
import net.minecraftforge.common.capabilities.*;
import java.util.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;

public class AnvilCraftingRecipe
{
    int level;
    int killcount;
    int refine;
    boolean broken;
    boolean noScabbard;
    String translationKey;
    Map<Enchantment, Integer> Enchantments;
    ItemStack result;
    CompoundNBT overwriteTag;
    
    public AnvilCraftingRecipe() {
        this.level = 1;
        this.killcount = 0;
        this.refine = 0;
        this.broken = false;
        this.noScabbard = false;
        this.translationKey = null;
        this.Enchantments = (Map<Enchantment, Integer>)Maps.newHashMap();
        this.result = ItemStack.EMPTY;
        this.overwriteTag = null;
    }
    
    public static AnvilCraftingRecipe getRecipe(final ItemStack material) {
        if (!material.hasTag()) {
            return null;
        }
        final CompoundNBT tag = material.getOrCreateTag();
        if (!tag.contains("RequiredBlade")) {
            return null;
        }
        final AnvilCraftingRecipe recipe = new AnvilCraftingRecipe();
        recipe.readNBT(tag.getCompound("RequiredBlade"));
        recipe.setEnchantments(EnchantmentHelper.getEnchantments(material));
        return recipe;
    }
    
    public void readNBT(final CompoundNBT tag) {
        NBTHelper.getNBTCoupler(tag).get("level", this::setLevel, new Integer[0]).get("killCount", this::setKillcount, new Integer[0]).get("refine", this::setRefine, new Integer[0]).get("broken", this::setBroken, new Boolean[0]).get("noScabbard", this::setNoScabbard, new Boolean[0]).get("translationKey", this::setTranslationKey, new String[0]).get("result", this::setResultWithNBT, new CompoundNBT[0]).get("overwriteTag", this::setOverwriteTag, new CompoundNBT[0]);
    }
    
    public INBT writeNBT() {
        final CompoundNBT tag = new CompoundNBT();
        NBTHelper.getNBTCoupler(tag).put("level", this.getLevel()).put("killCount", this.getKillcount()).put("refine", this.getRefine()).put("broken", this.isBroken()).put("noScabbard", this.isNoScabbard()).put("translationKey", this.getTranslationKey()).put("result", this.getResult().save(new CompoundNBT())).put("overwriteTag", this.getOverwriteTag());
        return (INBT)tag;
    }
    
    public boolean matches(final ItemStack base) {
        if (base.isEmpty()) {
            return false;
        }
        if (!this.translationKey.isEmpty() && !base.getDescriptionId().equals(this.translationKey)) {
            return false;
        }
        if (this.needBlade()) {
            if (!(base.getItem() instanceof ItemSlashBlade)) {
                return false;
            }
            final boolean stateMatches = base.getCapability((Capability)ItemSlashBlade.BLADESTATE).filter(state -> this.getKillcount() <= state.getKillCount() && this.getRefine() <= state.getRefine() && this.isNoScabbard() == state.isNoScabbard() && !((this.isBroken() ^ state.isBroken()) & this.isBroken())).isPresent();
            if (!stateMatches) {
                return false;
            }
        }
        if (!this.getEnchantments().isEmpty()) {
            for (final Map.Entry<Enchantment, Integer> entry : this.getEnchantments().entrySet()) {
                if (EnchantmentHelper.getItemEnchantmentLevel((Enchantment)entry.getKey(), base) < entry.getValue()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean needBlade() {
        return this.getKillcount() != 0 || this.getRefine() != 0 || this.broken || this.noScabbard;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public void setLevel(final int level) {
        this.level = level;
    }
    
    public int getKillcount() {
        return this.killcount;
    }
    
    public void setKillcount(final int killcount) {
        this.killcount = killcount;
    }
    
    public int getRefine() {
        return this.refine;
    }
    
    public void setRefine(final int refine) {
        this.refine = refine;
    }
    
    public boolean isBroken() {
        return this.broken;
    }
    
    public void setBroken(final boolean broken) {
        this.broken = broken;
    }
    
    public boolean isNoScabbard() {
        return this.noScabbard;
    }
    
    public void setNoScabbard(final boolean noScabbard) {
        this.noScabbard = noScabbard;
    }
    
    public String getTranslationKey() {
        return this.translationKey;
    }
    
    public void setTranslationKey(final String translationKey) {
        this.translationKey = translationKey;
    }
    
    public Map<Enchantment, Integer> getEnchantments() {
        return this.Enchantments;
    }
    
    public void setEnchantments(final Map<Enchantment, Integer> enchantments) {
        this.Enchantments = enchantments;
    }
    
    public ItemStack getResult() {
        return this.result.copy();
    }
    
    public ItemStack getResult(final ItemStack base) {
        ItemStack result;
        if (this.isOnlyTagOverwrite()) {
            final CompoundNBT tag = base.save(new CompoundNBT());
            tag.merge(this.getOverwriteTag().copy());
            result = ItemStack.of(tag);
        }
        else {
            result = this.getResult();
            base.getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(oldState -> result.getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(newState -> {
                newState.setKillCount(oldState.getKillCount());
                newState.setRefine(oldState.getRefine());
            }));
            final Map<Enchantment, Integer> destMap = (Map<Enchantment, Integer>)EnchantmentHelper.getEnchantments(result);
            final Map<Enchantment, Integer> srcMap = (Map<Enchantment, Integer>)EnchantmentHelper.getEnchantments(base);
            for (final Map.Entry<Enchantment, Integer> srcEntry : srcMap.entrySet()) {
                final Enchantment key = srcEntry.getKey();
                final int srcLevel = srcEntry.getValue();
                if (destMap.containsKey(key)) {
                    final int destLevel = destMap.get(key);
                    if (destLevel >= srcLevel) {
                        continue;
                    }
                    destMap.put(key, srcLevel);
                }
                else {
                    destMap.put(key, srcLevel);
                }
            }
            EnchantmentHelper.setEnchantments((Map)destMap, result);
        }
        result.getShareTag();
        return result;
    }
    
    public void setResult(final ItemStack result) {
        this.result = result;
    }
    
    public void setResultWithNBT(final CompoundNBT tag) {
        this.setResult(ItemStack.of(tag));
    }
    
    public boolean isOnlyTagOverwrite() {
        return this.overwriteTag != null;
    }
    
    public CompoundNBT getOverwriteTag() {
        return this.overwriteTag;
    }
    
    public void setOverwriteTag(final CompoundNBT overwriteTag) {
        this.overwriteTag = overwriteTag;
    }
}
