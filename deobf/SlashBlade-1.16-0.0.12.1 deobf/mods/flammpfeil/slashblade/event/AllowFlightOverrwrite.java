//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.event;

import net.minecraftforge.common.*;
import net.minecraftforge.fml.event.server.*;
import net.minecraftforge.eventbus.api.*;

public class AllowFlightOverrwrite
{
    public static AllowFlightOverrwrite getInstance() {
        return SingletonHolder.instance;
    }
    
    private AllowFlightOverrwrite() {
    }
    
    public void register() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onFMLServerAboutToStartEvent(final FMLServerAboutToStartEvent event) {
        event.getServer().setFlightAllowed(true);
    }
    
    private static final class SingletonHolder
    {
        private static final AllowFlightOverrwrite instance;
        
        static {
            instance = new AllowFlightOverrwrite(null);
        }
    }
}
