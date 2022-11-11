//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.specialattack;

import mods.flammpfeil.slashblade.util.*;
import java.util.function.*;
import net.minecraft.entity.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;
import net.minecraft.enchantment.*;
import net.minecraftforge.registries.*;
import mods.flammpfeil.slashblade.capability.slashblade.combo.*;

public class SlashArts extends RegistryBase<SlashArts>
{
    public static final int ChargeTicks = 9;
    public static final int ChargeJustTicks = 3;
    public static final int ChargeJustTicksMax = 5;
    public static final SlashArts NONE;
    public static final SlashArts JUDGEMENT_CUT;
    private Function<LivingEntity, ComboState> comboState;
    private Function<LivingEntity, ComboState> comboStateJust;
    private Function<LivingEntity, ComboState> comboStateBroken;
    
    public static int getJustReceptionSpan(final LivingEntity user) {
        return Math.min(5, 3 + EnchantmentHelper.getEnchantmentLevel(Enchantments.SOUL_SPEED, user));
    }
    
    public ComboState doArts(final ArtsType type, final LivingEntity user) {
        switch (type) {
            case Jackpot: {
                return this.getComboStateJust(user);
            }
            case Success: {
                return this.getComboState(user);
            }
            case Broken: {
                return this.getComboStateBroken(user);
            }
            default: {
                return ComboState.NONE;
            }
        }
    }
    
    public SlashArts(final String name, final Function<LivingEntity, ComboState> state) {
        super(name);
        this.comboState = state;
        this.comboStateJust = state;
        this.comboStateBroken = state;
    }
    
    @Override
    public String getPath() {
        return "slasharts";
    }
    
    @Override
    public SlashArts getNone() {
        return SlashArts.NONE;
    }
    
    public ComboState getComboState(final LivingEntity user) {
        return this.comboState.apply(user);
    }
    
    public ComboState getComboStateJust(final LivingEntity user) {
        return this.comboStateJust.apply(user);
    }
    
    public SlashArts setComboStateJust(final Function<LivingEntity, ComboState> state) {
        this.comboStateJust = state;
        return this;
    }
    
    public ComboState getComboStateBroken(final LivingEntity user) {
        return this.comboStateBroken.apply(user);
    }
    
    public SlashArts setComboStateBroken(final Function<LivingEntity, ComboState> state) {
        this.comboStateBroken = state;
        return this;
    }
    
    static {
        NONE = new SlashArts(SlashArts.BaseInstanceName, e -> ComboState.NONE);
        JUDGEMENT_CUT = new SlashArts("judgement_cut", e -> e.isOnGround() ? Extra.EX_JUDGEMENT_CUT : Extra.EX_JUDGEMENT_CUT_SLASH_AIR).setComboStateJust(e -> Extra.EX_JUDGEMENT_CUT_SLASH_JUST).setComboStateBroken(e -> Extra.EX_VOID_SLASH);
    }
    
    public enum ArtsType
    {
        Fail, 
        Success, 
        Jackpot, 
        Broken;
    }
}
