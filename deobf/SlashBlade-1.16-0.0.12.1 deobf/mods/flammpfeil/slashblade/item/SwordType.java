//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.item;

import net.minecraft.item.*;
import java.util.*;
import net.minecraftforge.common.util.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;

public enum SwordType
{
    None, 
    EdgeFragment, 
    Broken, 
    Perfect, 
    Enchanted, 
    Bewitched, 
    SoulEeater, 
    FiercerEdge, 
    NoScabbard, 
    Sealed, 
    Cursed;
    
    public static EnumSet<SwordType> from(final ItemStack itemStackIn) {
        final EnumSet<SwordType> types = EnumSet.noneOf(SwordType.class);
        final LazyOptional<ISlashBladeState> state = (LazyOptional<ISlashBladeState>)itemStackIn.getCapability(ItemSlashBlade.BLADESTATE);
        if (state.isPresent()) {
            itemStackIn.getCapability(ItemSlashBlade.BLADESTATE).ifPresent(s -> {
                if (s.isBroken()) {
                    types.add(SwordType.Broken);
                }
                if (s.isNoScabbard()) {
                    types.add(SwordType.NoScabbard);
                }
                if (s.isSealed()) {
                    types.add(SwordType.Cursed);
                }
                if (!s.isSealed() && itemStackIn.isEnchanted() && (itemStackIn.hasCustomHoverName() || s.isDefaultBewitched())) {
                    types.add(SwordType.Bewitched);
                }
            });
        }
        else {
            types.add(SwordType.NoScabbard);
            types.add(SwordType.EdgeFragment);
        }
        if (itemStackIn.isEnchanted()) {
            types.add(SwordType.Enchanted);
        }
        return types;
    }
}
