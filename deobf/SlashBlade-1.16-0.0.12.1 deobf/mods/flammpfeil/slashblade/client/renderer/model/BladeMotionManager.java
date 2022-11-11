//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.client.renderer.model;

import net.minecraft.util.*;
import mods.flammpfeil.slashblade.init.*;
import java.io.*;
import jp.nyatla.nymmd.*;
import com.google.common.cache.*;
import java.util.concurrent.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;

public class BladeMotionManager
{
    MmdVmdMotionMc defaultMotion;
    LoadingCache<ResourceLocation, MmdVmdMotionMc> cache;
    
    public static BladeMotionManager getInstance() {
        return SingletonHolder.instance;
    }
    
    private BladeMotionManager() {
        try {
            this.defaultMotion = new MmdVmdMotionMc(DefaultResources.ExMotionLocation);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (MmdException e2) {
            e2.printStackTrace();
        }
        this.cache = (LoadingCache<ResourceLocation, MmdVmdMotionMc>)CacheBuilder.newBuilder().build(CacheLoader.asyncReloading((CacheLoader)new CacheLoader<ResourceLocation, MmdVmdMotionMc>() {
            public MmdVmdMotionMc load(final ResourceLocation key) throws Exception {
                try {
                    return new MmdVmdMotionMc(key);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return BladeMotionManager.this.defaultMotion;
                }
            }
        }, (Executor)Executors.newCachedThreadPool()));
    }
    
    @SubscribeEvent
    public void reload(final TextureStitchEvent.Pre event) {
        this.cache.invalidateAll();
        try {
            this.defaultMotion = new MmdVmdMotionMc(DefaultResources.ExMotionLocation);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (MmdException e2) {
            e2.printStackTrace();
        }
    }
    
    public MmdVmdMotionMc getMotion(final ResourceLocation loc) {
        if (loc != null) {
            try {
                return (MmdVmdMotionMc)this.cache.get((Object)loc);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.defaultMotion;
    }
    
    private static final class SingletonHolder
    {
        private static final BladeMotionManager instance;
        
        static {
            instance = new BladeMotionManager(null);
        }
    }
}
