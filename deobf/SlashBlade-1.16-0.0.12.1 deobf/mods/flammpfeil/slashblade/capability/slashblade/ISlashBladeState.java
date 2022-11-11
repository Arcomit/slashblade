//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.capability.slashblade;

import mods.flammpfeil.slashblade.specialattack.*;
import mods.flammpfeil.slashblade.client.renderer.*;
import java.awt.*;
import net.minecraft.util.math.vector.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import javax.annotation.*;
import com.google.common.collect.*;
import java.util.*;
import mods.flammpfeil.slashblade.capability.slashblade.combo.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import java.util.function.*;
import net.minecraft.advancements.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import mods.flammpfeil.slashblade.network.*;
import net.minecraftforge.fml.network.*;
import mods.flammpfeil.slashblade.util.*;

public interface ISlashBladeState
{
    long getLastActionTime();
    
    void setLastActionTime(final long p0);
    
    default long getElapsedTime(final LivingEntity user) {
        long ticks = Math.max(0L, user.level.getGameTime() - this.getLastActionTime());
        if (user.level.isClientSide) {
            ticks = Math.max(0L, ticks + 1L);
        }
        return ticks;
    }
    
    boolean onClick();
    
    void setOnClick(final boolean p0);
    
    float getFallDecreaseRate();
    
    void setFallDecreaseRate(final float p0);
    
    boolean isCharged();
    
    void setCharged(final boolean p0);
    
    float getAttackAmplifier();
    
    void setAttackAmplifier(final float p0);
    
    @Nonnull
    ComboState getComboSeq();
    
    void setComboSeq(final ComboState p0);
    
    String getLastPosHash();
    
    void setLastPosHash(final String p0);
    
    boolean hasShield();
    
    void setHasShield(final boolean p0);
    
    boolean isBroken();
    
    void setBroken(final boolean p0);
    
    boolean isNoScabbard();
    
    void setNoScabbard(final boolean p0);
    
    boolean isSealed();
    
    void setSealed(final boolean p0);
    
    float getBaseAttackModifier();
    
    void setBaseAttackModifier(final float p0);
    
    int getKillCount();
    
    void setKillCount(final int p0);
    
    int getRefine();
    
    void setRefine(final int p0);
    
    UUID getOwner();
    
    void setOwner(final UUID p0);
    
    UUID getUniqueId();
    
    void setUniqueId(final UUID p0);
    
    @Nonnull
    RangeAttack getRangeAttackType();
    
    void setRangeAttackType(final RangeAttack p0);
    
    @Nonnull
    default SlashArts getSlashArts() {
        final String key = this.getSlashArtsKey();
        SlashArts result = null;
        if (key != null) {
            result = SlashArts.NONE.valueOf(key);
        }
        if (result == SlashArts.NONE) {
            result = null;
        }
        return (result != null) ? result : SlashArts.JUDGEMENT_CUT;
    }
    
    void setSlashArtsKey(final String p0);
    
    String getSlashArtsKey();
    
    boolean isDestructable();
    
    void setDestructable(final boolean p0);
    
    boolean isDefaultBewitched();
    
    void setDefaultBewitched(final boolean p0);
    
    @Nonnull
    Rarity getRarity();
    
    void setRarity(final Rarity p0);
    
    @Nonnull
    String getTranslationKey();
    
    void setTranslationKey(final String p0);
    
    @Nonnull
    CarryType getCarryType();
    
    void setCarryType(final CarryType p0);
    
    @Nonnull
    Color getEffectColor();
    
    void setEffectColor(final Color p0);
    
    boolean isEffectColorInverse();
    
    void setEffectColorInverse(final boolean p0);
    
    default void setColorCode(final int colorCode) {
        this.setEffectColor(new Color(colorCode));
    }
    
    default int getColorCode() {
        return this.getEffectColor().getRGB();
    }
    
    @Nonnull
    Vector3d getAdjust();
    
    void setAdjust(final Vector3d p0);
    
    @Nonnull
    Optional<ResourceLocation> getTexture();
    
    void setTexture(final ResourceLocation p0);
    
    @Nonnull
    Optional<ResourceLocation> getModel();
    
    void setModel(final ResourceLocation p0);
    
    int getTargetEntityId();
    
    void setTargetEntityId(final int p0);
    
    @Nullable
    default Entity getTargetEntity(final World world) {
        final int id = this.getTargetEntityId();
        if (id < 0) {
            return null;
        }
        return world.getEntity(id);
    }
    
    default void setTargetEntityId(final Entity target) {
        if (target != null) {
            this.setTargetEntityId(target.getId());
        }
        else {
            this.setTargetEntityId(-1);
        }
    }
    
    default int getFullChargeTicks(final LivingEntity user) {
        return 9;
    }
    
    default boolean isCharged(final LivingEntity user) {
        final int elapsed = user.getTicksUsingItem();
        return this.getFullChargeTicks(user) < elapsed;
    }
    
    default ComboState progressCombo(final LivingEntity user) {
        final ComboState current = this.resolvCurrentComboState(user);
        final ComboState next = current.getNext(user);
        if (next != ComboState.NONE && next == current) {
            return ComboState.NONE;
        }
        final ComboState rootNext = this.getComboRoot().getNext(user);
        final ComboState resolved = (next.getPriority() <= rootNext.getPriority()) ? next : rootNext;
        this.setComboSeq(resolved);
        return resolved;
    }
    
    default ComboState doChargeAction(final LivingEntity user, final int elapsed) {
        final Map.Entry<Integer, ComboState> current = this.resolvCurrentComboStateTicks(user);
        if (elapsed <= 2) {
            return ComboState.NONE;
        }
        if (current.getValue() != ComboState.NONE && current.getValue().getNext(user) == current.getValue()) {
            return ComboState.NONE;
        }
        final int fullChargeTicks = this.getFullChargeTicks(user);
        final int justReceptionSpan = SlashArts.getJustReceptionSpan(user);
        final int justChargePeriod = fullChargeTicks + justReceptionSpan;
        final RangeMap<Integer, SlashArts.ArtsType> charge_accept = (RangeMap<Integer, SlashArts.ArtsType>)ImmutableRangeMap.builder().put(Range.lessThan((Comparable)fullChargeTicks), (Object)SlashArts.ArtsType.Fail).put(Range.closedOpen((Comparable)fullChargeTicks, (Comparable)justChargePeriod), (Object)SlashArts.ArtsType.Jackpot).put(Range.atLeast((Comparable)justChargePeriod), (Object)SlashArts.ArtsType.Success).build();
        SlashArts.ArtsType type = (SlashArts.ArtsType)charge_accept.get((Comparable)elapsed);
        if (type != SlashArts.ArtsType.Jackpot) {
            final SlashArts.ArtsType result = current.getValue().releaseAction(user, (int)current.getKey());
            if (result != SlashArts.ArtsType.Fail) {
                type = result;
            }
        }
        final ComboState cs = this.getSlashArts().doArts(type, user);
        if (current.getValue() != cs && cs != ComboState.NONE && current.getValue().getPriority() > cs.getPriority()) {
            this.updateComboSeq(user, cs);
        }
        return cs;
    }
    
    default ComboState doBrokenAction(final LivingEntity user) {
        final Map.Entry<Integer, ComboState> current = this.resolvCurrentComboStateTicks(user);
        if (current.getValue() != ComboState.NONE && current.getValue().getNext(user) == current.getValue()) {
            return ComboState.NONE;
        }
        final SlashArts.ArtsType type = SlashArts.ArtsType.Broken;
        final ComboState cs = this.getSlashArts().doArts(type, user);
        if (current.getValue() != cs && cs != ComboState.NONE && current.getValue().getPriority() > cs.getPriority()) {
            this.updateComboSeq(user, cs);
        }
        return cs;
    }
    
    default void updateComboSeq(final LivingEntity entity, final ComboState cs) {
        this.setComboSeq(cs);
        this.setLastActionTime(entity.level.getGameTime());
        cs.clickAction(entity);
    }
    
    default ComboState resolvCurrentComboState(final LivingEntity user) {
        return this.resolvCurrentComboStateTicks(user).getValue();
    }
    
    default Map.Entry<Integer, ComboState> resolvCurrentComboStateTicks(final LivingEntity user) {
        ComboState current;
        int time;
        for (current = this.getComboSeq(), time = (int)TimeValueHelper.getMSecFromTicks((float)this.getElapsedTime(user)); current != ComboState.NONE && current.getTimeoutMS() < time; time -= current.getTimeoutMS(), current = current.getNextOfTimeout()) {}
        final int ticks = (int)TimeValueHelper.getTicksFromMSec((float)time);
        return new AbstractMap.SimpleImmutableEntry<Integer, ComboState>(ticks, current);
    }
    
    default boolean hasEnergy() {
        return true;
    }
    
    String getComboRootName();
    
    void setComboRootName(final String p0);
    
    default ComboState getComboRoot() {
        return (ComboState)Optional.ofNullable(ComboState.NONE.valueOf(this.getComboRootName())).orElseGet(() -> Extra.STANDBY_EX);
    }
    
    String getComboRootAirName();
    
    void setComboRootAirName(final String p0);
    
    default ComboState getComboRootAir() {
        return (ComboState)Optional.ofNullable(ComboState.NONE.valueOf(this.getComboRootAirName())).orElseGet(() -> Extra.STANDBY_INAIR);
    }
    
    CompoundNBT getShareTag();
    
    void setShareTag(final CompoundNBT p0);
    
    float getDamage();
    
    void setDamage(final float p0);
    
    default <T extends LivingEntity> void damageBlade(final ItemStack stack, final int amount, final T entityIn, final Consumer<T> onBroken) {
        if (amount <= 0) {
            return;
        }
        final boolean current = this.isBroken();
        stack.hurtAndBreak(1, (LivingEntity)entityIn, s -> {});
        if (1.0f <= this.getDamage()) {
            this.setBroken(true);
        }
        if (current != this.isBroken()) {
            onBroken.accept(entityIn);
            if (entityIn instanceof ServerPlayerEntity) {
                stack.getShareTag();
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)entityIn, stack);
            }
            if (entityIn instanceof PlayerEntity) {
                ((PlayerEntity)entityIn).awardStat(Stats.ITEM_BROKEN.get((Object)stack.getItem()));
            }
        }
        if (this.isBroken() && this.isDestructable()) {
            stack.shrink(1);
        }
    }
    
    default float getDurabilityForDisplay() {
        return Math.max(0.0f, Math.min(this.getDamage(), 1.0f));
    }
    
    boolean hasChangedActiveState();
    
    void setHasChangedActiveState(final boolean p0);
    
    default void sendChanges(final Entity entityIn) {
        if (!entityIn.level.isClientSide && this.hasChangedActiveState()) {
            final ActiveStateSyncMessage msg = new ActiveStateSyncMessage();
            msg.activeTag = this.getActiveState();
            msg.id = entityIn.getId();
            NetworkManager.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entityIn), (Object)msg);
            this.setHasChangedActiveState(false);
        }
    }
    
    default void removeActiveState(final CompoundNBT tag) {
        NBTHelper.getNBTCoupler(tag).remove("lastActionTime").remove("TargetEntity").remove("_onClick").remove("fallDecreaseRate").remove("isCharged").remove("AttackAmplifier").remove("currentCombo").remove("lastPosHash").remove("HasShield").remove("killCount").remove("Damage");
    }
    
    default CompoundNBT getActiveState() {
        final CompoundNBT tag = new CompoundNBT();
        NBTHelper.getNBTCoupler(tag).put("BladeUniqueId", this.getUniqueId()).put("lastActionTime", this.getLastActionTime()).put("TargetEntity", this.getTargetEntityId()).put("_onClick", this.onClick()).put("fallDecreaseRate", this.getFallDecreaseRate()).put("isCharged", this.isCharged()).put("AttackAmplifier", this.getAttackAmplifier()).put("currentCombo", this.getComboSeq().getName()).put("lastPosHash", this.getLastPosHash()).put("HasShield", this.hasShield()).put("killCount", this.getKillCount()).put("Damage", this.getDamage()).put("isBroken", this.isBroken());
        return tag;
    }
    
    default void setActiveState(final CompoundNBT tag) {
        NBTHelper.getNBTCoupler(tag).get("lastActionTime", this::setLastActionTime, new Long[0]).get("TargetEntity", id -> this.setTargetEntityId(id), new Integer[0]).get("_onClick", this::setOnClick, new Boolean[0]).get("fallDecreaseRate", this::setFallDecreaseRate, new Float[0]).get("isCharged", this::setCharged, new Boolean[0]).get("AttackAmplifier", this::setAttackAmplifier, new Float[0]).get("currentCombo", s -> this.setComboSeq((ComboState)ComboState.NONE.valueOf(s)), new String[0]).get("lastPosHash", this::setLastPosHash, new String[0]).get("HasShield", this::setHasShield, new Boolean[0]).get("killCount", this::setKillCount, new Integer[0]).get("Damage", this::setDamage, new Float[0]).get("isBroken", this::setBroken, new Boolean[0]);
        this.setHasChangedActiveState(false);
    }
}
