//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.capability.slashblade;

import java.util.*;
import net.minecraft.item.*;
import mods.flammpfeil.slashblade.client.renderer.*;
import java.awt.*;
import net.minecraft.util.math.vector.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.registries.*;
import javax.annotation.*;
import mods.flammpfeil.slashblade.capability.slashblade.combo.*;

public class SlashBladeState implements ISlashBladeState
{
    protected long lastActionTime;
    protected int targetEntityId;
    protected boolean _onClick;
    protected float fallDecreaseRate;
    protected boolean isCharged;
    protected float attackAmplifier;
    protected ComboState comboSeq;
    protected String lastPosHash;
    protected boolean _hasShield;
    protected boolean isBroken;
    protected boolean isNoScabbard;
    protected boolean isSealed;
    protected float baseAttackModifier;
    protected int killCount;
    protected int refine;
    protected UUID owner;
    protected UUID uniqueId;
    protected String translationKey;
    protected RangeAttack rangeAttackType;
    protected String slashArtsKey;
    protected boolean isDestructable;
    protected boolean isDefaultBewitched;
    protected Optional<Rarity> rarity;
    protected String comboRootName;
    protected String comboRootAirName;
    protected Optional<CarryType> carryType;
    protected Optional<Color> effectColor;
    protected boolean effectColorInverse;
    protected Optional<Vector3d> adjust;
    protected Optional<ResourceLocation> texture;
    protected Optional<ResourceLocation> model;
    private CompoundNBT shareTag;
    LazyOptional<ComboState> rootCombo;
    LazyOptional<ComboState> rootComboAir;
    private float damage;
    boolean isChangedActiveState;
    
    public SlashBladeState() {
        this.uniqueId = UUID.randomUUID();
        this.translationKey = "";
        this.rarity = Optional.empty();
        this.carryType = Optional.empty();
        this.effectColor = Optional.empty();
        this.adjust = Optional.empty();
        this.texture = Optional.empty();
        this.model = Optional.empty();
        this.shareTag = null;
        this.rootCombo = this.instantiateRootComboHolder();
        this.rootComboAir = this.instantiateRootComboAirHolder();
        this.damage = 0.0f;
        this.isChangedActiveState = false;
    }
    
    public long getLastActionTime() {
        return this.lastActionTime;
    }
    
    public void setLastActionTime(final long lastActionTime) {
        this.lastActionTime = lastActionTime;
        this.setHasChangedActiveState(true);
    }
    
    public boolean onClick() {
        return this._onClick;
    }
    
    public void setOnClick(final boolean onClick) {
        this._onClick = onClick;
        this.setHasChangedActiveState(true);
    }
    
    public float getFallDecreaseRate() {
        return this.fallDecreaseRate;
    }
    
    public void setFallDecreaseRate(final float fallDecreaseRate) {
        this.fallDecreaseRate = fallDecreaseRate;
        this.setHasChangedActiveState(true);
    }
    
    public boolean isCharged() {
        return this.isCharged;
    }
    
    public void setCharged(final boolean charged) {
        this.isCharged = charged;
        this.setHasChangedActiveState(true);
    }
    
    public float getAttackAmplifier() {
        return this.attackAmplifier;
    }
    
    public void setAttackAmplifier(final float attackAmplifier) {
        this.attackAmplifier = attackAmplifier;
        this.setHasChangedActiveState(true);
    }
    
    @Nonnull
    public ComboState getComboSeq() {
        return (ComboState)ComboState.NONE.orNone((IForgeRegistryEntry)this.comboSeq);
    }
    
    public void setComboSeq(final ComboState comboSeq) {
        this.comboSeq = comboSeq;
        this.setHasChangedActiveState(true);
    }
    
    public String getLastPosHash() {
        return (this.lastPosHash != null) ? this.lastPosHash : "";
    }
    
    public void setLastPosHash(final String lastPosHash) {
        this.lastPosHash = lastPosHash;
        this.setHasChangedActiveState(true);
    }
    
    public boolean hasShield() {
        return this._hasShield;
    }
    
    public void setHasShield(final boolean hasShield) {
        this._hasShield = hasShield;
        this.setHasChangedActiveState(true);
    }
    
    public boolean isBroken() {
        return this.isBroken;
    }
    
    public void setBroken(final boolean broken) {
        this.isBroken = broken;
        this.setHasChangedActiveState(true);
    }
    
    public boolean isNoScabbard() {
        return this.isNoScabbard;
    }
    
    public void setNoScabbard(final boolean noScabbard) {
        this.isNoScabbard = noScabbard;
    }
    
    public boolean isSealed() {
        return this.isSealed;
    }
    
    public void setSealed(final boolean sealed) {
        this.isSealed = sealed;
    }
    
    public float getBaseAttackModifier() {
        return this.baseAttackModifier;
    }
    
    public void setBaseAttackModifier(final float baseAttackModifier) {
        this.baseAttackModifier = baseAttackModifier;
    }
    
    public int getKillCount() {
        return this.killCount;
    }
    
    public void setKillCount(final int killCount) {
        this.killCount = killCount;
        this.setHasChangedActiveState(true);
    }
    
    public int getRefine() {
        return this.refine;
    }
    
    public void setRefine(final int refine) {
        this.refine = refine;
    }
    
    public UUID getOwner() {
        return this.owner;
    }
    
    public void setOwner(final UUID owner) {
        this.owner = owner;
    }
    
    @Nonnull
    public RangeAttack getRangeAttackType() {
        return (RangeAttack)RangeAttack.NONE.orNone((IForgeRegistryEntry)this.rangeAttackType);
    }
    
    public void setRangeAttackType(final RangeAttack rangeAttackType) {
        this.rangeAttackType = rangeAttackType;
    }
    
    public String getSlashArtsKey() {
        return this.slashArtsKey;
    }
    
    public void setSlashArtsKey(final String key) {
        this.slashArtsKey = key;
    }
    
    public boolean isDestructable() {
        return this.isDestructable;
    }
    
    public void setDestructable(final boolean destructable) {
        this.isDestructable = destructable;
    }
    
    public boolean isDefaultBewitched() {
        return this.isDefaultBewitched;
    }
    
    public void setDefaultBewitched(final boolean defaultBewitched) {
        this.isDefaultBewitched = defaultBewitched;
    }
    
    @Nonnull
    public Rarity getRarity() {
        return this.rarity.orElse(Rarity.COMMON);
    }
    
    public void setRarity(final Rarity rarity) {
        this.rarity = Optional.ofNullable(rarity);
    }
    
    public String getTranslationKey() {
        return this.translationKey;
    }
    
    public void setTranslationKey(final String translationKey) {
        this.translationKey = Optional.ofNullable(translationKey).orElse("");
    }
    
    @Nonnull
    public CarryType getCarryType() {
        return this.carryType.orElse(CarryType.NONE);
    }
    
    public void setCarryType(final CarryType carryType) {
        this.carryType = Optional.ofNullable(carryType);
    }
    
    public Color getEffectColor() {
        return this.effectColor.orElseGet(() -> new Color(3355647));
    }
    
    public void setEffectColor(final Color effectColor) {
        this.effectColor = Optional.ofNullable(effectColor);
    }
    
    public boolean isEffectColorInverse() {
        return this.effectColorInverse;
    }
    
    public void setEffectColorInverse(final boolean effectColorInverse) {
        this.effectColorInverse = effectColorInverse;
    }
    
    public Vector3d getAdjust() {
        return this.adjust.orElseGet(() -> Vector3d.ZERO);
    }
    
    public void setAdjust(final Vector3d adjust) {
        this.adjust = Optional.ofNullable(adjust);
    }
    
    public Optional<ResourceLocation> getTexture() {
        return this.texture;
    }
    
    public void setTexture(final ResourceLocation texture) {
        this.texture = Optional.ofNullable(texture);
    }
    
    public Optional<ResourceLocation> getModel() {
        return this.model;
    }
    
    public void setModel(final ResourceLocation model) {
        this.model = Optional.ofNullable(model);
    }
    
    public int getTargetEntityId() {
        return this.targetEntityId;
    }
    
    public void setTargetEntityId(final int id) {
        this.targetEntityId = id;
        this.setHasChangedActiveState(true);
    }
    
    public String getComboRootName() {
        return this.comboRootName;
    }
    
    public void setComboRootName(final String comboRootName) {
        this.comboRootName = comboRootName;
        this.rootCombo = this.instantiateRootComboHolder();
    }
    
    private LazyOptional<ComboState> instantiateRootComboHolder() {
        return (LazyOptional<ComboState>)LazyOptional.of(() -> {
            if (ComboState.NONE.valueOf(this.getComboRootName()) == null) {
                return Extra.STANDBY_EX;
            }
            return (ComboState)ComboState.NONE.valueOf(this.getComboRootName());
        });
    }
    
    public String getComboRootAirName() {
        return this.comboRootAirName;
    }
    
    public void setComboRootAirName(final String comboRootName) {
        this.comboRootName = comboRootName;
        this.rootComboAir = this.instantiateRootComboAirHolder();
    }
    
    private LazyOptional<ComboState> instantiateRootComboAirHolder() {
        return (LazyOptional<ComboState>)LazyOptional.of(() -> {
            if (ComboState.NONE.valueOf(this.getComboRootName()) == null) {
                return Extra.STANDBY_EX;
            }
            return (ComboState)ComboState.NONE.valueOf(this.getComboRootAirName());
        });
    }
    
    public CompoundNBT getShareTag() {
        return this.shareTag;
    }
    
    public void setShareTag(final CompoundNBT shareTag) {
        this.shareTag = shareTag;
    }
    
    public float getDamage() {
        return this.damage;
    }
    
    public void setDamage(final float damage) {
        if (!this.isSealed() && damage <= 0.0f) {
            this.setBroken(false);
        }
        this.damage = Math.max(0.0f, Math.min(damage, 1.0f));
        this.setHasChangedActiveState(true);
    }
    
    public boolean hasChangedActiveState() {
        return this.isChangedActiveState;
    }
    
    public void setHasChangedActiveState(final boolean isChanged) {
        this.isChangedActiveState = isChanged;
    }
    
    public UUID getUniqueId() {
        return this.uniqueId;
    }
    
    public void setUniqueId(final UUID uniqueId) {
        this.uniqueId = uniqueId;
    }
}
