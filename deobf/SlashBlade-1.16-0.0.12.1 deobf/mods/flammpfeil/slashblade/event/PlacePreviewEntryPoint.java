//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.event;

import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.entity.*;
import mods.flammpfeil.slashblade.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.eventbus.api.*;

public class PlacePreviewEntryPoint
{
    public static PlacePreviewEntryPoint getInstance() {
        return SingletonHolder.instance;
    }
    
    private PlacePreviewEntryPoint() {
    }
    
    public void register() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onClick(final PlayerInteractEvent.RightClickItem event) {
        final PlayerEntity trueSource = event.getPlayer();
        if (!(trueSource instanceof LivingEntity)) {
            return;
        }
        final ItemStack stack = event.getItemStack();
        if (stack.isEmpty()) {
            return;
        }
        if (stack.getItem() != SBItems.proudsoul) {
            return;
        }
        final World worldIn = trueSource.getCommandSenderWorld();
    }
    
    private static final class SingletonHolder
    {
        private static final PlacePreviewEntryPoint instance;
        
        static {
            instance = new PlacePreviewEntryPoint(null);
        }
    }
}
