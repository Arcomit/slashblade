//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.capability.concentrationrank;

import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;
import net.minecraft.util.*;
import javax.annotation.*;
import net.minecraft.nbt.*;

public class ConcentrationRankCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT>
{
    @CapabilityInject(IConcentrationRank.class)
    public static Capability<IConcentrationRank> RANK_POINT;
    protected LazyOptional<IConcentrationRank> state;
    static final String tagState = "rawPoint";
    
    public ConcentrationRankCapabilityProvider() {
        this.state = (LazyOptional<IConcentrationRank>)LazyOptional.of(ConcentrationRankCapabilityProvider.RANK_POINT::getDefaultInstance);
    }
    
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side) {
        return (LazyOptional<T>)ConcentrationRankCapabilityProvider.RANK_POINT.orEmpty((Capability)cap, (LazyOptional)this.state);
    }
    
    public CompoundNBT serializeNBT() {
        final CompoundNBT baseTag = new CompoundNBT();
        this.state.ifPresent(state -> baseTag.put("rawPoint", ConcentrationRankCapabilityProvider.RANK_POINT.writeNBT((Object)state, (Direction)null)));
        return baseTag;
    }
    
    public void deserializeNBT(final CompoundNBT baseTag) {
        this.state.ifPresent(state -> ConcentrationRankCapabilityProvider.RANK_POINT.readNBT((Object)state, (Direction)null, (INBT)baseTag.getCompound("rawPoint")));
    }
    
    static {
        ConcentrationRankCapabilityProvider.RANK_POINT = null;
    }
}
