//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.entity;

import net.minecraft.entity.*;

public interface IShootable
{
    void shoot(final double p0, final double p1, final double p2, final float p3, final float p4);
    
    Entity getShooter();
    
    void setShooter(final Entity p0);
    
    double getDamage();
}
