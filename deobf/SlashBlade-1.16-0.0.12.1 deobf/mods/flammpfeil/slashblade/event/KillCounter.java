//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.event;

import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.living.*;
import mods.flammpfeil.slashblade.item.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraftforge.eventbus.api.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;

public class KillCounter
{
    public static KillCounter getInstance() {
        return SingletonHolder.instance;
    }
    
    private KillCounter() {
    }
    
    public void register() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLivingDeathEvent(final LivingDeathEvent event) {
        final Entity trueSource = event.getSource().getEntity();
        if (!(trueSource instanceof LivingEntity)) {
            return;
        }
        final ItemStack stack = ((LivingEntity)trueSource).getMainHandItem();
        if (stack.isEmpty()) {
            return;
        }
        if (!(stack.getItem() instanceof ItemSlashBlade)) {
            return;
        }
        stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(state -> state.setKillCount(state.getKillCount() + 1));
    }
    
    private static final class SingletonHolder
    {
        private static final KillCounter instance;
        
        static {
            instance = new KillCounter(null);
        }
    }
}
