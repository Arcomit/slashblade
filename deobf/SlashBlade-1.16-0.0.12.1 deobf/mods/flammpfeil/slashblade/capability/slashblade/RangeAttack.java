//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.capability.slashblade;

import mods.flammpfeil.slashblade.util.*;
import net.minecraftforge.registries.*;

public class RangeAttack extends RegistryBase<RangeAttack>
{
    public static final RangeAttack NONE;
    
    RangeAttack(final String name) {
        super(name);
    }
    
    @Override
    public String getPath() {
        return "rangeattack";
    }
    
    @Override
    public RangeAttack getNone() {
        return RangeAttack.NONE;
    }
    
    static {
        NONE = new RangeAttack(RangeAttack.BaseInstanceName);
    }
}
