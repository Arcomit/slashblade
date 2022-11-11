//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.client.renderer.model;

import net.minecraft.entity.*;
import net.minecraftforge.client.model.*;
import com.google.common.collect.*;
import java.util.function.*;
import net.minecraft.item.*;
import net.minecraft.client.world.*;
import javax.annotation.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.*;

public class BladeModel implements IBakedModel
{
    IBakedModel original;
    ItemOverrideList overrides;
    public static LivingEntity user;
    
    public BladeModel(final IBakedModel original, final ModelLoader loader) {
        this.original = original;
        this.overrides = new ItemOverrideList(loader, null, null, ImmutableList.of()) {
            public IBakedModel resolve(final IBakedModel originalModel, final ItemStack stack, @Nullable final ClientWorld world, @Nullable final LivingEntity entity) {
                BladeModel.user = entity;
                return super.resolve(originalModel, stack, world, entity);
            }
        };
    }
    
    public ItemOverrideList getOverrides() {
        return this.overrides;
    }
    
    public List<BakedQuad> getQuads(@Nullable final BlockState state, @Nullable final Direction side, final Random rand) {
        return (List<BakedQuad>)this.original.getQuads(state, side, rand);
    }
    
    public boolean useAmbientOcclusion() {
        return this.original.useAmbientOcclusion();
    }
    
    public boolean isGui3d() {
        return this.original.isGui3d();
    }
    
    public boolean usesBlockLight() {
        return false;
    }
    
    public boolean isCustomRenderer() {
        return true;
    }
    
    public TextureAtlasSprite getParticleIcon() {
        return this.original.getParticleIcon();
    }
    
    static {
        BladeModel.user = null;
    }
}
