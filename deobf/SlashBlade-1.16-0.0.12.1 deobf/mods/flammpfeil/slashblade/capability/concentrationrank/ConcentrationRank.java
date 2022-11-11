//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.capability.concentrationrank;

import net.minecraft.util.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;

public class ConcentrationRank implements IConcentrationRank
{
    long rankpoint;
    long lastupdate;
    long lastrankrise;
    public static long UnitCapacity;
    
    public ConcentrationRank() {
        this.rankpoint = 0L;
        this.lastupdate = 0L;
    }
    
    @Override
    public long getRawRankPoint() {
        return this.rankpoint;
    }
    
    @Override
    public void setRawRankPoint(final long point) {
        this.rankpoint = point;
    }
    
    @Override
    public long getLastUpdate() {
        return this.lastupdate;
    }
    
    @Override
    public void setLastUpdte(final long time) {
        this.lastupdate = time;
    }
    
    @Override
    public long getLastRankRise() {
        return this.lastrankrise;
    }
    
    @Override
    public void setLastRankRise(final long time) {
        this.lastrankrise = time;
    }
    
    @Override
    public long getUnitCapacity() {
        return ConcentrationRank.UnitCapacity;
    }
    
    @Override
    public float getRankPointModifier(final DamageSource ds) {
        return 0.1f;
    }
    
    @Override
    public float getRankPointModifier(final ComboState combo) {
        return 0.1f;
    }
    
    static {
        ConcentrationRank.UnitCapacity = 300L;
    }
}
