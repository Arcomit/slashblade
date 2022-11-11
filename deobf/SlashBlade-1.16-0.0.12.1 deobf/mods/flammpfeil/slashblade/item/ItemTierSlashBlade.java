//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.item;

import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.item.crafting.*;
import java.util.function.*;

public class ItemTierSlashBlade implements IItemTier
{
    private final LazyValue<Ingredient> repairMaterial;
    
    public ItemTierSlashBlade(final Supplier<Ingredient> repairMaterialIn) {
        this.repairMaterial = (LazyValue<Ingredient>)new LazyValue((Supplier)repairMaterialIn);
    }
    
    public int getUses() {
        return 100;
    }
    
    public float getSpeed() {
        return 0.0f;
    }
    
    public float getAttackDamageBonus() {
        return 0.0f;
    }
    
    public int getLevel() {
        return 3;
    }
    
    public int getEnchantmentValue() {
        return 10;
    }
    
    public Ingredient getRepairIngredient() {
        return (Ingredient)this.repairMaterial.get();
    }
}
