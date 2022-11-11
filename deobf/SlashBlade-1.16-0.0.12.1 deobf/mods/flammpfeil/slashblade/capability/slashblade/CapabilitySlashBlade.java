//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.capability.slashblade;

import net.minecraft.util.math.vector.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraft.nbt.*;
import mods.flammpfeil.slashblade.capability.slashblade.combo.*;
import java.util.*;
import net.minecraft.item.*;
import mods.flammpfeil.slashblade.client.renderer.*;
import net.minecraft.util.*;

public class CapabilitySlashBlade
{
    @CapabilityInject(ISlashBladeState.class)
    public static Capability<ISlashBladeState> BLADESTATE;
    
    protected static Vector3d readVector3dFrom(final CompoundNBT tag, final String key) {
        final ListNBT list = tag.getList(key, 6);
        return new Vector3d(list.getDouble(0), list.getDouble(1), list.getDouble(2));
    }
    
    protected static ListNBT newDoubleNBTList(final Vector3d vec) {
        return newDoubleNBTList(vec.x, vec.y, vec.z);
    }
    
    protected static ListNBT newDoubleNBTList(final double... numbers) {
        final ListNBT listnbt = new ListNBT();
        for (final double d0 : numbers) {
            listnbt.add((Object)DoubleNBT.valueOf(d0));
        }
        return listnbt;
    }
    
    public static void register() {
        CapabilityManager.INSTANCE.register((Class)ISlashBladeState.class, (Capability.IStorage)new Capability.IStorage<ISlashBladeState>() {
            public INBT writeNBT(final Capability<ISlashBladeState> capability, final ISlashBladeState instance, final Direction side) {
                final CompoundNBT tag = new CompoundNBT();
                tag.putLong("lastActionTime", instance.getLastActionTime());
                tag.putInt("TargetEntity", instance.getTargetEntityId());
                tag.putBoolean("_onClick", instance.onClick());
                tag.putFloat("fallDecreaseRate", instance.getFallDecreaseRate());
                tag.putBoolean("isCharged", instance.isCharged());
                tag.putFloat("AttackAmplifier", instance.getAttackAmplifier());
                tag.putString("currentCombo", instance.getComboSeq().getName());
                tag.putString("lastPosHash", instance.getLastPosHash());
                tag.putBoolean("HasShield", instance.hasShield());
                tag.putFloat("Damage", instance.getDamage());
                tag.putBoolean("isBroken", instance.isBroken());
                tag.putBoolean("isNoScabbard", instance.isNoScabbard());
                tag.putBoolean("isSealed", instance.isSealed());
                tag.putFloat("baseAttackModifier", instance.getBaseAttackModifier());
                tag.putInt("killCount", instance.getKillCount());
                tag.putInt("RepairCounter", instance.getRefine());
                final UUID id = instance.getOwner();
                if (id != null) {
                    tag.putUUID("Owner", id);
                }
                final UUID bladeId = instance.getUniqueId();
                tag.putUUID("BladeUniqueId", bladeId);
                tag.putString("RangeAttackType", instance.getRangeAttackType().getName());
                tag.putString("SpecialAttackType", (String)Optional.ofNullable(instance.getSlashArtsKey()).orElse("none"));
                tag.putBoolean("isDestructable", instance.isDestructable());
                tag.putBoolean("isDefaultBewitched", instance.isDefaultBewitched());
                tag.putByte("rarityType", (byte)instance.getRarity().ordinal());
                tag.putString("translationKey", instance.getTranslationKey());
                tag.putByte("StandbyRenderType", (byte)instance.getCarryType().ordinal());
                tag.putInt("SummonedSwordColor", instance.getColorCode());
                tag.putBoolean("SummonedSwordColorInverse", instance.isEffectColorInverse());
                tag.put("adjustXYZ", (INBT)CapabilitySlashBlade.newDoubleNBTList(instance.getAdjust()));
                instance.getTexture().ifPresent(loc -> tag.putString("TextureName", loc.toString()));
                instance.getModel().ifPresent(loc -> tag.putString("ModelName", loc.toString()));
                tag.putString("ComboRoot", (String)Optional.ofNullable(instance.getComboRoot()).map(c -> c.getName()).orElseGet(() -> Extra.STANDBY_EX.getName()));
                tag.putString("ComboRootAir", (String)Optional.ofNullable(instance.getComboRoot()).map(c -> c.getName()).orElseGet(() -> Extra.STANDBY_INAIR.getName()));
                return (INBT)tag;
            }
            
            private <T extends Enum> T fromOrdinal(final T[] values, final int ordinal, final T def) {
                if (0 <= ordinal && ordinal < values.length) {
                    return values[ordinal];
                }
                return def;
            }
            
            public void readNBT(final Capability<ISlashBladeState> capability, final ISlashBladeState instance, final Direction side, final INBT nbt) {
                final CompoundNBT tag = (CompoundNBT)nbt;
                if (!(instance instanceof SlashBladeState)) {
                    throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
                }
                instance.setLastActionTime(tag.getLong("lastActionTime"));
                instance.setTargetEntityId(tag.getInt("TargetEntity"));
                instance.setOnClick(tag.getBoolean("_onClick"));
                instance.setFallDecreaseRate(tag.getFloat("fallDecreaseRate"));
                instance.setCharged(tag.getBoolean("isCharged"));
                instance.setAttackAmplifier(tag.getFloat("AttackAmplifier"));
                instance.setComboSeq(ComboState.NONE.valueOf(tag.getString("currentCombo")));
                instance.setLastPosHash(tag.getString("lastPosHash"));
                instance.setHasShield(tag.getBoolean("HasShield"));
                instance.setDamage(tag.getFloat("Damage"));
                instance.setBroken(tag.getBoolean("isBroken"));
                instance.setHasChangedActiveState(true);
                instance.setNoScabbard(tag.getBoolean("isNoScabbard"));
                instance.setSealed(tag.getBoolean("isSealed"));
                instance.setBaseAttackModifier(tag.getFloat("baseAttackModifier"));
                instance.setKillCount(tag.getInt("killCount"));
                instance.setRefine(tag.getInt("RepairCounter"));
                instance.setOwner(tag.hasUUID("Owner") ? tag.getUUID("Owner") : null);
                instance.setUniqueId(tag.hasUUID("BladeUniqueId") ? tag.getUUID("BladeUniqueId") : UUID.randomUUID());
                instance.setRangeAttackType(RangeAttack.NONE.valueOf(tag.getString("RangeAttackType")));
                instance.setSlashArtsKey(tag.getString("SpecialAttackType"));
                instance.setDestructable(tag.getBoolean("isDestructable"));
                instance.setDefaultBewitched(tag.getBoolean("isDefaultBewitched"));
                instance.setRarity(this.fromOrdinal(Rarity.values(), tag.getByte("rarityType"), Rarity.COMMON));
                instance.setTranslationKey(tag.getString("translationKey"));
                instance.setCarryType(this.fromOrdinal(CarryType.values(), tag.getByte("StandbyRenderType"), CarryType.DEFAULT));
                instance.setColorCode(tag.getInt("SummonedSwordColor"));
                instance.setEffectColorInverse(tag.getBoolean("SummonedSwordColorInverse"));
                instance.setAdjust(CapabilitySlashBlade.readVector3dFrom(tag, "adjustXYZ"));
                if (tag.contains("TextureName")) {
                    instance.setTexture(new ResourceLocation(tag.getString("TextureName")));
                }
                else {
                    instance.setTexture(null);
                }
                if (tag.contains("ModelName")) {
                    instance.setModel(new ResourceLocation(tag.getString("ModelName")));
                }
                else {
                    instance.setModel(null);
                }
                instance.setComboRootName(tag.getString("ComboRoot"));
                instance.setComboRootAirName(tag.getString("ComboRootAir"));
            }
        }, () -> new SlashBladeState());
    }
    
    static {
        CapabilitySlashBlade.BLADESTATE = null;
    }
}
