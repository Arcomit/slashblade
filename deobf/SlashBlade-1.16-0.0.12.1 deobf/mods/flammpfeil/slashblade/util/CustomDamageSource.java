//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.util;

import mods.flammpfeil.slashblade.entity.*;
import net.minecraft.entity.*;
import javax.annotation.*;
import net.minecraft.util.*;

public class CustomDamageSource
{
    public static DamageSource causeSummonedSwordDamage(final EntityAbstractSummonedSword sword, @Nullable final Entity indirectEntityIn) {
        return new IndirectEntityDamageSource("slashblade_summonedsword", (Entity)sword, indirectEntityIn).setProjectile();
    }
}
