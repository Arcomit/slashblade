//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.util;

import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;

public class StatHelper
{
    static final long MAX_VALUE = 2147483647L;
    
    public static int increase(final ServerPlayerEntity player, final ResourceLocation loc, final int amount) {
        final Stat<?> stat = (Stat<?>)Stats.CUSTOM.get((Object)loc);
        final ServerStatisticsManager stats = player.getStats();
        final int oldValue = stats.getValue((Stat)stat);
        int newValue = (int)Math.min(oldValue + (long)amount, 2147483647L);
        if (oldValue == newValue) {
            --newValue;
        }
        stats.setValue((PlayerEntity)player, (Stat)stat, newValue);
        return newValue;
    }
}
