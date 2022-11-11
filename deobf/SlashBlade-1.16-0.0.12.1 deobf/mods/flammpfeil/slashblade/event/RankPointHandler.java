//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.event;

import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.entity.*;
import net.minecraftforge.eventbus.api.*;
import mods.flammpfeil.slashblade.capability.concentrationrank.*;

public class RankPointHandler
{
    public static RankPointHandler getInstance() {
        return SingletonHolder.instance;
    }
    
    private RankPointHandler() {
    }
    
    public void register() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLivingDeathEvent(final LivingHurtEvent event) {
        final LivingEntity victim = event.getEntityLiving();
        if (victim != null) {
            victim.getCapability(CapabilityConcentrationRank.RANK_POINT).ifPresent(cr -> cr.addRankPoint(victim, -cr.getUnitCapacity()));
        }
        final Entity trueSource = event.getSource().getEntity();
        if (!(trueSource instanceof LivingEntity)) {
            return;
        }
        trueSource.getCapability(CapabilityConcentrationRank.RANK_POINT).ifPresent(cr -> cr.addRankPoint(event.getSource()));
    }
    
    private static final class SingletonHolder
    {
        private static final RankPointHandler instance;
        
        static {
            instance = new RankPointHandler(null);
        }
    }
}
