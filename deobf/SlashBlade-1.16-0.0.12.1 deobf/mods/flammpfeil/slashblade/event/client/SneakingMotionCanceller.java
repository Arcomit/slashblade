//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.event.client;

import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import mods.flammpfeil.slashblade.item.*;
import net.minecraft.client.renderer.entity.model.*;
import net.minecraft.client.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.math.vector.*;
import net.minecraftforge.eventbus.api.*;

public class SneakingMotionCanceller
{
    public static SneakingMotionCanceller getInstance() {
        return SingletonHolder.instance;
    }
    
    private SneakingMotionCanceller() {
    }
    
    public void register() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onRenderPlayerEventPre(final RenderPlayerEvent.Pre event) {
        final ItemStack stack = event.getPlayer().getMainHandItem();
        if (stack.isEmpty()) {
            return;
        }
        if (!(stack.getItem() instanceof ItemSlashBlade)) {
            return;
        }
        if (!((PlayerModel)event.getRenderer().getModel()).crouching) {
            return;
        }
        ((PlayerModel)event.getRenderer().getModel()).crouching = false;
        final Vector3d offset = event.getRenderer().getRenderOffset((AbstractClientPlayerEntity)event.getPlayer(), event.getPartialRenderTick()).scale(-1.0);
        event.getMatrixStack().translate(offset.x, offset.y, offset.z);
    }
    
    private static final class SingletonHolder
    {
        private static final SneakingMotionCanceller instance;
        
        static {
            instance = new SneakingMotionCanceller(null);
        }
    }
}
