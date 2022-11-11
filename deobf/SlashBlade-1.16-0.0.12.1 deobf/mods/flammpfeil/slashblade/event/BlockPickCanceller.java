//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.event;

import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.*;
import mods.flammpfeil.slashblade.item.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraft.client.entity.player.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.eventbus.api.*;

public class BlockPickCanceller
{
    public static BlockPickCanceller getInstance() {
        return SingletonHolder.instance;
    }
    
    private BlockPickCanceller() {
    }
    
    public void register() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onBlockPick(final InputEvent.ClickInputEvent event) {
        if (!event.isPickBlock()) {
            return;
        }
        final ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        if (player.getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).isPresent()) {
            event.setCanceled(true);
        }
    }
    
    private static final class SingletonHolder
    {
        private static final BlockPickCanceller instance;
        
        static {
            instance = new BlockPickCanceller(null);
        }
    }
}
