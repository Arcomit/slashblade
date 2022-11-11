//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.client.renderer.model;

import net.minecraftforge.api.distmarker.*;
import mods.flammpfeil.slashblade.client.renderer.model.obj.*;
import net.minecraft.util.*;
import com.google.common.cache.*;
import java.util.concurrent.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;

@OnlyIn(Dist.CLIENT)
public class BladeModelManager
{
    WavefrontObject defaultModel;
    public static final ResourceLocation resourceDefaultModel;
    public static final ResourceLocation resourceDefaultTexture;
    public static final ResourceLocation resourceDurabilityModel;
    public static final ResourceLocation resourceDurabilityTexture;
    LoadingCache<ResourceLocation, WavefrontObject> cache;
    
    public static BladeModelManager getInstance() {
        return SingletonHolder.instance;
    }
    
    private BladeModelManager() {
        this.defaultModel = new WavefrontObject(BladeModelManager.resourceDefaultModel);
        this.cache = (LoadingCache<ResourceLocation, WavefrontObject>)CacheBuilder.newBuilder().build(CacheLoader.asyncReloading((CacheLoader)new CacheLoader<ResourceLocation, WavefrontObject>() {
            public WavefrontObject load(final ResourceLocation key) throws Exception {
                try {
                    return new WavefrontObject(key);
                }
                catch (Exception e) {
                    return BladeModelManager.this.defaultModel;
                }
            }
        }, (Executor)Executors.newCachedThreadPool()));
    }
    
    @SubscribeEvent
    public void reload(final TextureStitchEvent.Pre event) {
        this.cache.invalidateAll();
        this.defaultModel = new WavefrontObject(BladeModelManager.resourceDefaultModel);
    }
    
    public WavefrontObject getModel(final ResourceLocation loc) {
        if (loc != null) {
            try {
                return (WavefrontObject)this.cache.get((Object)loc);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.defaultModel;
    }
    
    static {
        resourceDefaultModel = new ResourceLocation("slashblade", "model/blade.obj");
        resourceDefaultTexture = new ResourceLocation("slashblade", "model/blade.png");
        resourceDurabilityModel = new ResourceLocation("slashblade", "model/util/durability.obj");
        resourceDurabilityTexture = new ResourceLocation("slashblade", "model/util/durability.png");
    }
    
    private static final class SingletonHolder
    {
        private static final BladeModelManager instance;
        
        static {
            instance = new BladeModelManager(null);
        }
    }
}
