//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.util;

import net.minecraftforge.registries.*;
import java.util.*;
import net.minecraft.util.*;
import com.google.common.collect.*;

public abstract class RegistryBase<V extends IForgeRegistryEntry<V>> extends ForgeRegistryEntry<V>
{
    public static Map<ResourceLocation, Map<ResourceLocation, Object>> registries;
    private String name;
    protected static String BaseInstanceName;
    public final ResourceLocation path;
    
    public RegistryBase(final String name) {
        this.name = name;
        this.path = new ResourceLocation("slashblade", this.getPath() + "/" + name);
        this.getRegistry().put(this.path, this);
    }
    
    public Map<ResourceLocation, Object> getRegistry() {
        final ResourceLocation key = this.delegate.name();
        if (!RegistryBase.registries.containsKey(key)) {
            RegistryBase.registries.put(key, Maps.newHashMap());
        }
        return RegistryBase.registries.get(key);
    }
    
    public abstract String getPath();
    
    public V valueOf(final String name) {
        final Object result = this.getRegistry().get(new ResourceLocation("slashblade", this.getPath() + "/" + name));
        return (V)result;
    }
    
    public String getName() {
        return this.name;
    }
    
    public V orNone(final V src) {
        if (src == null) {
            return this.getNone();
        }
        return src;
    }
    
    public abstract V getNone();
    
    static {
        RegistryBase.registries = (Map<ResourceLocation, Map<ResourceLocation, Object>>)Maps.newHashMap();
        RegistryBase.BaseInstanceName = "none";
    }
}
