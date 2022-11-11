//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.capability.concentrationrank;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import mods.flammpfeil.slashblade.network.*;
import net.minecraftforge.fml.network.*;
import net.minecraft.util.*;
import mods.flammpfeil.slashblade.item.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraft.item.*;
import java.util.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;
import com.google.common.collect.*;

public interface IConcentrationRank
{
    long getRawRankPoint();
    
    void setRawRankPoint(final long p0);
    
    long getLastUpdate();
    
    void setLastUpdte(final long p0);
    
    long getLastRankRise();
    
    void setLastRankRise(final long p0);
    
    long getUnitCapacity();
    
    default long getMaxCapacity() {
        return (long)(ConcentrationRanks.MAX_LEVEL * this.getUnitCapacity()) - 1L;
    }
    
    default ConcentrationRanks getRank(final long time) {
        return ConcentrationRanks.getRankFromLevel(this.getRankLevel(time));
    }
    
    default long reductionLimitter(final long reduction) {
        final long limit = this.getRawRankPoint() % this.getUnitCapacity();
        return Math.min(reduction, limit);
    }
    
    default float getRankLevel(final long currentTime) {
        return this.getRankPoint(currentTime) / (float)this.getUnitCapacity();
    }
    
    default float getRankProgress(final long currentTime) {
        final float level = this.getRankLevel(currentTime);
        final Range<Float> range = this.getRank(currentTime).pointRange;
        final double bottom = range.hasLowerBound() ? ((double)(float)range.lowerEndpoint()) : 0.0;
        final double top = range.hasUpperBound() ? ((double)(float)range.upperEndpoint()) : Math.floor(bottom + 1.0);
        final double len = top - bottom;
        return (float)((level - bottom) / len);
    }
    
    default long getRankPoint(final long time) {
        final long reduction = time - this.getLastUpdate();
        return this.getRawRankPoint() - this.reductionLimitter(reduction);
    }
    
    default void addRankPoint(final LivingEntity user, final long point) {
        final long time = user.level.getGameTime();
        final ConcentrationRanks oldRank = this.getRank(time);
        this.setRawRankPoint(Math.min(Math.max(0L, point + this.getRankPoint(time)), this.getMaxCapacity()));
        this.setLastUpdte(time);
        if (oldRank.level < this.getRank(time).level) {
            this.setLastRankRise(time);
        }
        if (user instanceof ServerPlayerEntity && !user.level.isClientSide) {
            if (((ServerPlayerEntity)user).connection == null) {
                return;
            }
            final RankSyncMessage msg = new RankSyncMessage();
            msg.rawPoint = this.getRawRankPoint();
            NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)user), (Object)msg);
        }
    }
    
    default void addRankPoint(final DamageSource src) {
        if (!(src.getEntity() instanceof LivingEntity)) {
            return;
        }
        final LivingEntity user = (LivingEntity)src.getEntity();
        final ItemStack stack = user.getMainHandItem();
        final Optional<ComboState> combo = (Optional<ComboState>)stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).map(s -> s.resolvCurrentComboState(user));
        final float modifier = combo.map(c -> this.getRankPointModifier(c)).orElse(this.getRankPointModifier(src));
        this.addRankPoint(user, (long)(modifier * this.getUnitCapacity()));
    }
    
    float getRankPointModifier(final DamageSource p0);
    
    float getRankPointModifier(final ComboState p0);
    
    public enum ConcentrationRanks
    {
        NONE(0, Range.lessThan((Comparable)1.0f)), 
        D(1, Range.closedOpen((Comparable)1.0f, (Comparable)2.0f)), 
        C(2, Range.closedOpen((Comparable)2.0f, (Comparable)3.0f)), 
        B(3, Range.closedOpen((Comparable)3.0f, (Comparable)4.0f)), 
        A(4, Range.closedOpen((Comparable)4.0f, (Comparable)5.0f)), 
        S(5, Range.closedOpen((Comparable)5.0f, (Comparable)5.25f)), 
        SS(6, Range.closedOpen((Comparable)5.25f, (Comparable)5.5f)), 
        SSS(7, Range.atLeast((Comparable)5.5f));
        
        public static float MAX_LEVEL;
        final Range<Float> pointRange;
        public final int level;
        private static RangeMap<Float, ConcentrationRanks> concentrationRanksMap;
        
        private ConcentrationRanks(final int level, final Range pointRange) {
            this.pointRange = (Range<Float>)pointRange;
            this.level = level;
        }
        
        public static ConcentrationRanks getRankFromLevel(final float point) {
            return (ConcentrationRanks)ConcentrationRanks.concentrationRanksMap.get((Comparable)point);
        }
        
        static {
            ConcentrationRanks.MAX_LEVEL = 6.0f;
            ConcentrationRanks.concentrationRanksMap = (RangeMap<Float, ConcentrationRanks>)ImmutableRangeMap.builder().put((Range)ConcentrationRanks.NONE.pointRange, (Object)ConcentrationRanks.NONE).put((Range)ConcentrationRanks.D.pointRange, (Object)ConcentrationRanks.D).put((Range)ConcentrationRanks.C.pointRange, (Object)ConcentrationRanks.C).put((Range)ConcentrationRanks.B.pointRange, (Object)ConcentrationRanks.B).put((Range)ConcentrationRanks.A.pointRange, (Object)ConcentrationRanks.A).put((Range)ConcentrationRanks.S.pointRange, (Object)ConcentrationRanks.S).put((Range)ConcentrationRanks.SS.pointRange, (Object)ConcentrationRanks.SS).put((Range)ConcentrationRanks.SSS.pointRange, (Object)ConcentrationRanks.SSS).build();
        }
    }
}
