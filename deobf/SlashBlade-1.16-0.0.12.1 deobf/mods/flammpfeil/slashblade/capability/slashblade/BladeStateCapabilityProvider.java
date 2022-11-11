//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.capability.slashblade;

import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;
import net.minecraft.util.*;
import javax.annotation.*;
import net.minecraft.nbt.*;
import net.minecraftforge.energy.*;

public class BladeStateCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT>
{
    @CapabilityInject(ISlashBladeState.class)
    public static Capability<ISlashBladeState> CAP;
    @CapabilityInject(IEnergyStorage.class)
    public static Capability<IEnergyStorage> ENERGY;
    protected LazyOptional<ISlashBladeState> state;
    protected LazyOptional<IEnergyStorage> storage;
    static final int defaultCapacity = 1000000;
    private final String tagState = "State";
    private final String tagEnergy = "Energy";
    
    public BladeStateCapabilityProvider() {
        this.state = (LazyOptional<ISlashBladeState>)LazyOptional.of(BladeStateCapabilityProvider.CAP::getDefaultInstance);
        this.storage = (LazyOptional<IEnergyStorage>)LazyOptional.of(() -> new EnergyStorage(1000000));
    }
    
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side) {
        if (cap == BladeStateCapabilityProvider.ENERGY) {
            return (LazyOptional<T>)(this.state.filter(iSlashBladeState -> iSlashBladeState.hasEnergy()).isPresent() ? this.storage.cast() : LazyOptional.empty());
        }
        return (LazyOptional<T>)BladeStateCapabilityProvider.CAP.orEmpty((Capability)cap, (LazyOptional)this.state);
    }
    
    public CompoundNBT serializeNBT() {
        final CompoundNBT baseTag = new CompoundNBT();
        this.state.ifPresent(iSlashBladeState -> baseTag.put("State", BladeStateCapabilityProvider.CAP.writeNBT((Object)iSlashBladeState, (Direction)null)));
        this.storage.ifPresent(iEnergyStorage -> baseTag.put("Energy", BladeStateCapabilityProvider.ENERGY.writeNBT((Object)iEnergyStorage, (Direction)null)));
        return baseTag;
    }
    
    public void deserializeNBT(final CompoundNBT baseTag) {
        this.state.ifPresent(iSlashBladeState -> BladeStateCapabilityProvider.CAP.readNBT((Object)iSlashBladeState, (Direction)null, baseTag.get("State")));
        this.storage.ifPresent(iEnergyStorage -> BladeStateCapabilityProvider.ENERGY.readNBT((Object)iEnergyStorage, (Direction)null, baseTag.get("Energy")));
    }
    
    static {
        BladeStateCapabilityProvider.CAP = null;
        BladeStateCapabilityProvider.ENERGY = null;
    }
}
