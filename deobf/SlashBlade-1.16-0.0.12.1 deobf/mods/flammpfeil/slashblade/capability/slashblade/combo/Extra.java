//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.capability.slashblade.combo;

import mods.flammpfeil.slashblade.capability.inputstate.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraft.util.math.vector.*;
import mods.flammpfeil.slashblade.event.client.*;
import mods.flammpfeil.slashblade.*;
import mods.flammpfeil.slashblade.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import mods.flammpfeil.slashblade.item.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import net.minecraftforge.common.*;
import net.minecraft.entity.ai.attributes.*;
import mods.flammpfeil.slashblade.ability.*;
import mods.flammpfeil.slashblade.event.*;
import mods.flammpfeil.slashblade.util.*;
import java.util.*;
import java.util.stream.*;
import mods.flammpfeil.slashblade.init.*;
import java.util.function.*;
import mods.flammpfeil.slashblade.specialattack.*;

public class Extra
{
    @CapabilityInject(IInputState.class)
    public static Capability<IInputState> INPUT_STATE;
    static List<Map.Entry<EnumSet<InputCommand>, Supplier<ComboState>>> ex_standbyMap;
    public static final ComboState STANDBY_EX;
    public static final ComboState STANDBY_INAIR;
    public static final ComboState EX_COMBO_A1;
    public static final ComboState EX_COMBO_A1_END;
    public static final ComboState EX_COMBO_A1_END2;
    public static final ComboState EX_COMBO_A2;
    public static final ComboState EX_COMBO_A2_END;
    public static final ComboState EX_COMBO_A2_END2;
    public static final ComboState EX_COMBO_C;
    public static final ComboState EX_COMBO_C_END;
    public static final ComboState EX_COMBO_A3;
    public static final ComboState EX_COMBO_A3_END;
    public static final ComboState EX_COMBO_A3_END2;
    public static final ComboState EX_COMBO_A3_END3;
    public static final ComboState EX_COMBO_A4;
    public static final ComboState EX_COMBO_A4_END;
    public static final ComboState EX_COMBO_A4EX;
    public static final ComboState EX_COMBO_A4EX_END;
    public static final ComboState EX_COMBO_A4EX_END2;
    public static final ComboState EX_COMBO_A5EX;
    public static final ComboState EX_COMBO_A5EX_END;
    private static final float rushDamageBase = 0.1f;
    public static final ComboState EX_COMBO_B1;
    public static final ComboState EX_COMBO_B1_END;
    public static final ComboState EX_COMBO_B1_END2;
    public static final ComboState EX_COMBO_B1_END3;
    public static final ComboState EX_COMBO_B2;
    public static final ComboState EX_COMBO_B3;
    public static final ComboState EX_COMBO_B4;
    public static final ComboState EX_COMBO_B5;
    public static final ComboState EX_COMBO_B6;
    public static final ComboState EX_COMBO_B7;
    public static final ComboState EX_COMBO_B7_END;
    public static final ComboState EX_COMBO_B_END;
    public static final ComboState EX_COMBO_B_END2;
    public static final ComboState EX_COMBO_B_END3;
    public static final ComboState EX_AERIAL_RAVE_A1;
    public static final ComboState EX_AERIAL_RAVE_A1_END;
    public static final ComboState EX_AERIAL_RAVE_A2;
    public static final ComboState EX_AERIAL_RAVE_A2_END;
    public static final ComboState EX_AERIAL_RAVE_A2_END2;
    public static final ComboState EX_AERIAL_RAVE_A3;
    public static final ComboState EX_AERIAL_RAVE_A3_END;
    public static final ComboState EX_AERIAL_RAVE_B3;
    public static final ComboState EX_AERIAL_RAVE_B3_END;
    public static final ComboState EX_AERIAL_RAVE_B4;
    public static final ComboState EX_AERIAL_RAVE_B4_END;
    private static final EnumSet<InputCommand> ex_upperslash_command;
    public static final ComboState EX_UPPERSLASH;
    public static final ComboState EX_UPPERSLASH_END;
    public static final ComboState EX_UPPERSLASH_JUMP;
    public static final ComboState EX_UPPERSLASH_JUMP_END;
    public static final ComboState EX_AERIAL_CLEAVE;
    public static final ComboState EX_AERIAL_CLEAVE_LOOP;
    public static final ComboState EX_AERIAL_CLEAVE_LANDING;
    public static final ComboState EX_AERIAL_CLEAVE_END;
    public static final ComboState EX_RAPID_SLASH;
    public static final ComboState EX_RAPID_SLASH_QUICK;
    public static final ComboState EX_RAPID_SLASH_END;
    public static final ComboState EX_RAPID_SLASH_END2;
    public static final ComboState EX_RISING_STAR;
    public static final ComboState EX_RISING_STAR_END;
    public static final ComboState EX_JUDGEMENT_CUT;
    public static final ComboState EX_JUDGEMENT_CUT_SLASH;
    public static final ComboState EX_JUDGEMENT_CUT_SHEATH;
    public static final ComboState EX_JUDGEMENT_CUT_SLASH_AIR;
    public static final ComboState EX_JUDGEMENT_CUT_SHEATH_AIR;
    public static final ComboState EX_JUDGEMENT_CUT_SLASH_JUST;
    public static final ComboState EX_JUDGEMENT_CUT_SLASH_JUST2;
    public static final ComboState EX_JUDGEMENT_CUT_SLASH_JUST_SHEATH;
    public static final ComboState EX_VOID_SLASH;
    public static final ComboState EX_VOID_SLASH_SHEATH;
    
    public static void playQuickSheathSoundAction(final LivingEntity e) {
        e.playSound(SoundEvents.CHAIN_HIT, 1.0f, 1.0f);
    }
    
    public static Vector3d genRushOffset(final LivingEntity entityIn) {
        return new Vector3d((double)(entityIn.getRandom().nextFloat() - 0.5f), (double)(entityIn.getRandom().nextFloat() - 0.5f), 0.0).scale(2.0);
    }
    
    static {
        Extra.INPUT_STATE = null;
        Extra.ex_standbyMap = new HashMap<EnumSet<InputCommand>, Supplier<ComboState>>() {
            {
                this.put(EnumSet.of(InputCommand.ON_GROUND, InputCommand.SNEAK, InputCommand.FORWARD, InputCommand.R_CLICK), () -> Extra.EX_RAPID_SLASH);
                this.put(EnumSet.of(InputCommand.ON_GROUND, InputCommand.L_CLICK), () -> Extra.EX_COMBO_A1);
                this.put(EnumSet.of(InputCommand.ON_GROUND, InputCommand.BACK, InputCommand.SNEAK, InputCommand.R_CLICK), () -> Extra.EX_UPPERSLASH);
                this.put(EnumSet.of(InputCommand.ON_GROUND, InputCommand.R_CLICK), () -> Extra.EX_COMBO_A1);
                this.put(EnumSet.of(InputCommand.ON_AIR, InputCommand.SNEAK, InputCommand.BACK, InputCommand.R_CLICK), () -> Extra.EX_AERIAL_CLEAVE);
                this.put(EnumSet.of(InputCommand.ON_AIR), () -> Extra.EX_AERIAL_RAVE_A1);
            }
        }.entrySet().stream().collect((Collector<? super Object, ?, List<Map.Entry<EnumSet<InputCommand>, Supplier<ComboState>>>>)Collectors.toList());
        final EnumSet<InputCommand> commands;
        STANDBY_EX = new ComboState("standby", 10, () -> 0, () -> 1, () -> 1.0f, () -> true, () -> 1000, DefaultResources.ExMotionLocation, a -> {
            commands = a.getCapability((Capability)ComboState.INPUT_STATE).map(state -> state.getCommands(a)).orElseGet(() -> EnumSet.noneOf(InputCommand.class));
            return (ComboState)Extra.ex_standbyMap.stream().filter(entry -> commands.containsAll(entry.getKey())).min(Comparator.comparingInt(entry -> entry.getValue().get().getPriority())).map(entry -> entry.getValue().get()).orElseGet(() -> ComboState.NONE);
        }, () -> ComboState.NONE);
        STANDBY_INAIR = new ComboState("standby_inair", 10, () -> 0, () -> 1, () -> 1.0f, () -> true, () -> 400, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(FallHandler::fallDecrease);
        EX_COMBO_A1 = new ComboState("ex_combo_a1", 100, () -> 1, () -> 10, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(5, a -> Extra.EX_COMBO_A2), () -> Extra.EX_COMBO_A1_END).setClickAction(e -> AttackManager.doSlash(e, -10.0f, true)).addTickAction(entityIn -> UserPoseOverrider.resetRot(entityIn)).addHitEffect(StunManager::setStun);
        EX_COMBO_A1_END = new ComboState("ex_combo_a1_end", 100, () -> 10, () -> 21, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> Extra.EX_COMBO_A2, () -> Extra.EX_COMBO_A1_END2).setReleaseAction(ComboState::releaseActionQuickCharge);
        EX_COMBO_A1_END2 = new ComboState("ex_combo_a1_end2", 100, () -> 21, () -> 41, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE);
        EX_COMBO_A2 = new ComboState("ex_combo_a2", 100, () -> 100, () -> 115, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(5, a -> Extra.EX_COMBO_A3), () -> Extra.EX_COMBO_A2_END).setClickAction(e -> AttackManager.doSlash(e, 170.0f, true)).addHitEffect(StunManager::setStun);
        EX_COMBO_A2_END = new ComboState("ex_combo_a2_end", 100, () -> 115, () -> 132, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> Extra.EX_COMBO_C, () -> Extra.EX_COMBO_A2_END2).setReleaseAction(ComboState::releaseActionQuickCharge);
        EX_COMBO_A2_END2 = new ComboState("ex_combo_a2_end2", 100, () -> 132, () -> 151, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE);
        EX_COMBO_C = new ComboState("ex_combo_c", 100, () -> 400, () -> 459, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(15, a -> ComboState.NONE), () -> Extra.EX_COMBO_C_END).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(2, entityIn -> AttackManager.doSlash(entityIn, -30.0f)).put(3, entityIn -> AttackManager.doSlash(entityIn, -35.0f, true)).build()).addHitEffect(StunManager::setStun);
        EX_COMBO_C_END = new ComboState("ex_combo_c_end", 100, () -> 459, () -> 488, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build()).setReleaseAction(ComboState::releaseActionQuickCharge);
        EX_COMBO_A3 = new ComboState("ex_combo_a3", 100, () -> 200, () -> 218, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(9, a -> a.hasEffect(Effects.DAMAGE_BOOST) ? Extra.EX_COMBO_A4EX : Extra.EX_COMBO_A4), () -> Extra.EX_COMBO_A3_END).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(2, entityIn -> AttackManager.doSlash(entityIn, -61.0f)).put(6, entityIn -> AttackManager.doSlash(entityIn, 138.0f)).build()).addHitEffect(StunManager::setStun);
        EX_COMBO_A3_END = new ComboState("ex_combo_a3_end", 100, () -> 218, () -> 230, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> Extra.EX_COMBO_B1, () -> Extra.EX_COMBO_A3_END2);
        EX_COMBO_A3_END2 = new ComboState("ex_combo_a3_end2", 100, () -> 230, () -> 281, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> Extra.EX_COMBO_A3_END3);
        EX_COMBO_A3_END3 = new ComboState("ex_combo_a3_end3", 100, () -> 281, () -> 314, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build()).setReleaseAction(ComboState::releaseActionQuickCharge);
        EX_COMBO_A4 = new ComboState("ex_combo_a4", 100, () -> 500, () -> 576, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(21, a -> ComboState.NONE), () -> Extra.EX_COMBO_A4_END).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(8, entityIn -> AttackManager.doSlash(entityIn, 45.0f)).put(9, entityIn -> AttackManager.doSlash(entityIn, 50.0f, true)).build()).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(8, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(9, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(10, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(11, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(12, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(13, entityIn -> UserPoseOverrider.resetRot(entityIn)).build()).addHitEffect(StunManager::setStun);
        EX_COMBO_A4_END = new ComboState("ex_combo_a4_end", 100, () -> 576, () -> 608, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build()).setReleaseAction(ComboState::releaseActionQuickCharge);
        EX_COMBO_A4EX = new ComboState("ex_combo_a4ex", 100, () -> 800, () -> 839, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(22, a -> Extra.EX_COMBO_A5EX), () -> Extra.EX_COMBO_A4EX_END).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(7, entityIn -> AttackManager.doSlash(entityIn, 70.0f)).put(14, entityIn -> AttackManager.doSlash(entityIn, 255.0f)).build()).addHitEffect(StunManager::setStun);
        EX_COMBO_A4EX_END = new ComboState("ex_combo_a4ex_end", 100, () -> 839, () -> 877, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> Extra.EX_COMBO_A4EX_END2);
        EX_COMBO_A4EX_END2 = new ComboState("ex_combo_a4ex_end2", 100, () -> 877, () -> 894, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build()).setReleaseAction(ComboState::releaseActionQuickCharge);
        EX_COMBO_A5EX = new ComboState("ex_combo_a5ex", 100, () -> 900, () -> 1013, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(33, a -> ComboState.NONE), () -> Extra.EX_COMBO_A5EX_END).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(15, entityIn -> AttackManager.doSlash(entityIn, 35.0f, false, true)).put(17, entityIn -> AttackManager.doSlash(entityIn, 40.0f, true, true)).put(19, entityIn -> AttackManager.doSlash(entityIn, 30.0f, true, true)).build()).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(13, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(14, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(15, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(16, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(17, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(18, entityIn -> UserPoseOverrider.resetRot(entityIn)).build()).addHitEffect(StunManager::setStun);
        EX_COMBO_A5EX_END = new ComboState("ex_combo_a5ex_end", 100, () -> 1013, () -> 1061, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build()).setReleaseAction(ComboState::releaseActionQuickCharge);
        EX_COMBO_B1 = new ComboState("ex_combo_b1", 100, () -> 700, () -> 720, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(13, a -> Extra.EX_COMBO_B2), () -> Extra.EX_COMBO_B1_END).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(6, entityIn -> {
            AttackManager.doSlash(entityIn, -30.0f, false, false, 0.25);
            AttackManager.doSlash(entityIn, 145.0f, true, false, 0.25);
            return;
        }).put(7, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(8, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(9, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(10, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(11, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(12, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(13, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(14, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).build()).addHitEffect(StunManager::setStun);
        EX_COMBO_B1_END = new ComboState("ex_combo_b1_end", 100, () -> 720, () -> 743, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> Extra.EX_COMBO_B1_END, () -> Extra.EX_COMBO_B1_END2).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(9, entityIn -> AttackManager.doSlash(entityIn, 0.0f, new Vector3d((double)(entityIn.getRandom().nextFloat() - 0.5f), 0.800000011920929, 0.0), false, true, 1.0)).put(10, entityIn -> AttackManager.doSlash(entityIn, 5.0f, new Vector3d((double)(entityIn.getRandom().nextFloat() - 0.5f), 0.800000011920929, 0.0), true, false, 1.0)).build()).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(9, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(10, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(11, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(12, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(13, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(14, entityIn -> UserPoseOverrider.resetRot(entityIn)).build()).addHitEffect(StunManager::setStun);
        EX_COMBO_B1_END2 = new ComboState("ex_combo_b1_end2", 100, () -> 743, () -> 764, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> Extra.EX_COMBO_B1_END3);
        EX_COMBO_B1_END3 = new ComboState("ex_combo_b1_end3", 100, () -> 764, () -> 787, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build());
        EX_COMBO_B2 = new ComboState("ex_combo_b2", 100, () -> 710, () -> 720, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(6, a -> Extra.EX_COMBO_B3), () -> Extra.EX_COMBO_B_END).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(1, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(2, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(3, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(4, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(5, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(6, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).build()).addHitEffect(StunManager::setStun);
        EX_COMBO_B3 = new ComboState("ex_combo_b3", 100, () -> 710, () -> 720, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(6, a -> Extra.EX_COMBO_B4), () -> Extra.EX_COMBO_B_END).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(1, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(2, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(3, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(4, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(5, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(6, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).build()).addHitEffect(StunManager::setStun);
        EX_COMBO_B4 = new ComboState("ex_combo_b4", 100, () -> 710, () -> 720, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(6, a -> Extra.EX_COMBO_B5), () -> Extra.EX_COMBO_B_END).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(1, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(2, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(3, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(4, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(5, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(6, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).build()).addHitEffect(StunManager::setStun);
        EX_COMBO_B5 = new ComboState("ex_combo_b5", 100, () -> 710, () -> 720, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(6, a -> Extra.EX_COMBO_B6), () -> Extra.EX_COMBO_B_END).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(1, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(2, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(3, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(4, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(5, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(6, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).build()).addHitEffect(StunManager::setStun);
        EX_COMBO_B6 = new ComboState("ex_combo_b6", 100, () -> 710, () -> 720, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(6, a -> Extra.EX_COMBO_B7), () -> Extra.EX_COMBO_B_END).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(1, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(2, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(3, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(4, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(5, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(6, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).build()).addHitEffect(StunManager::setStun);
        EX_COMBO_B7 = new ComboState("ex_combo_b7", 100, () -> 710, () -> 764, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(33, a -> ComboState.NONE), () -> Extra.EX_COMBO_B7_END).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(1, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(2, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(3, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(4, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(5, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(6, entityIn -> AttackManager.doSlash(entityIn, -90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), false, false, 0.10000000149011612)).put(7, entityIn -> AttackManager.doSlash(entityIn, 90.0f + 180.0f * entityIn.getRandom().nextFloat(), genRushOffset(entityIn), true, false, 0.10000000149011612)).put(12, entityIn -> AttackManager.doSlash(entityIn, 0.0f, new Vector3d((double)(entityIn.getRandom().nextFloat() - 0.5f), 0.800000011920929, 0.0), false, true, 1.0)).put(13, entityIn -> AttackManager.doSlash(entityIn, 5.0f, new Vector3d((double)(entityIn.getRandom().nextFloat() - 0.5f), 0.800000011920929, 0.0), true, false, 1.0)).build()).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(12, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(13, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(14, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(15, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(16, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(17, entityIn -> UserPoseOverrider.resetRot(entityIn)).build()).addHitEffect(StunManager::setStun);
        EX_COMBO_B7_END = new ComboState("ex_combo_b7_end", 100, () -> 764, () -> 787, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build()).setReleaseAction(ComboState::releaseActionQuickCharge);
        EX_COMBO_B_END = new ComboState("ex_combo_b_end", 100, () -> 720, () -> 743, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> Extra.EX_COMBO_B_END, () -> Extra.EX_COMBO_B_END2).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(9, entityIn -> AttackManager.doSlash(entityIn, 0.0f, new Vector3d((double)(entityIn.getRandom().nextFloat() - 0.5f), 0.800000011920929, 0.0), false, true, 1.0)).put(10, entityIn -> AttackManager.doSlash(entityIn, 5.0f, new Vector3d((double)(entityIn.getRandom().nextFloat() - 0.5f), 0.800000011920929, 0.0), true, false, 1.0)).build()).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(9, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(10, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(11, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(12, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(13, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(14, entityIn -> UserPoseOverrider.resetRot(entityIn)).build()).addHitEffect(StunManager::setStun);
        EX_COMBO_B_END2 = new ComboState("ex_combo_b_end2", 100, () -> 743, () -> 764, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> Extra.EX_COMBO_B_END3);
        EX_COMBO_B_END3 = new ComboState("ex_combo_b_end3", 100, () -> 764, () -> 787, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build()).setReleaseAction(ComboState::releaseActionQuickCharge);
        EX_AERIAL_RAVE_A1 = new ComboState("ex_aerial_rave_a1", 80, () -> 1100, () -> 1122, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(5, a -> Extra.EX_AERIAL_RAVE_A2), () -> Extra.EX_AERIAL_RAVE_A1_END).addTickAction(ComboState.TimeLineTickAction.getBuilder().put((int)TimeValueHelper.getTicksFromFrames(3.0f) + 0, entityIn -> AttackManager.doSlash(entityIn, -20.0f)).build().andThen(FallHandler::fallDecrease)).addHitEffect(StunManager::setStun).addTickAction(entityIn -> UserPoseOverrider.resetRot(entityIn)).setIsAerial();
        EX_AERIAL_RAVE_A1_END = new ComboState("ex_aerial_rave_a1_end", 80, () -> 1122, () -> 1132, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build()).addTickAction(FallHandler::fallDecrease).setReleaseAction(ComboState::releaseActionQuickCharge);
        EX_AERIAL_RAVE_A2 = new ComboState("ex_aerial_rave_a2", 80, () -> 1200, () -> 1210, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(5, a -> Extra.EX_AERIAL_RAVE_A3), () -> Extra.EX_AERIAL_RAVE_A2_END).addTickAction(ComboState.TimeLineTickAction.getBuilder().put((int)TimeValueHelper.getTicksFromFrames(3.0f) + 0, entityIn -> AttackManager.doSlash(entityIn, 150.0f)).build()).addTickAction(FallHandler::fallDecrease).addHitEffect(StunManager::setStun).setIsAerial();
        EX_AERIAL_RAVE_A2_END = new ComboState("ex_aerial_rave_a2_end", 80, () -> 1210, () -> 1231, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> Extra.EX_AERIAL_RAVE_B3, () -> Extra.EX_AERIAL_RAVE_A2_END2).addTickAction(FallHandler::fallDecrease).setIsAerial();
        EX_AERIAL_RAVE_A2_END2 = new ComboState("ex_aerial_rave_a2_end2", 80, () -> 1231, () -> 1241, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build()).addTickAction(FallHandler::fallDecrease).setReleaseAction(ComboState::releaseActionQuickCharge);
        EX_AERIAL_RAVE_A3 = new ComboState("ex_aerial_rave_a3", 80, () -> 1300, () -> 1328, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(9, a -> ComboState.NONE), () -> Extra.EX_AERIAL_RAVE_A3_END).addTickAction(ComboState.TimeLineTickAction.getBuilder().put((int)TimeValueHelper.getTicksFromFrames(4.0f) + 0, entityIn -> AttackManager.doSlash(entityIn, 0.0f, Vector3d.ZERO, false, false, 1.0, KnockBacks.smash)).put((int)TimeValueHelper.getTicksFromFrames(4.0f) + 1, entityIn -> AttackManager.doSlash(entityIn, -3.0f, Vector3d.ZERO, true, true, 1.0, KnockBacks.smash)).build()).addTickAction(FallHandler::fallDecrease).addHitEffect(StunManager::setStun).setIsAerial();
        EX_AERIAL_RAVE_A3_END = new ComboState("ex_aerial_rave_a3_end", 80, () -> 1328, () -> 1338, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build()).addTickAction(FallHandler::fallDecrease).setReleaseAction(ComboState::releaseActionQuickCharge);
        final Vector3d motion;
        EX_AERIAL_RAVE_B3 = new ComboState("ex_aerial_rave_b3", 80, () -> 1400, () -> 1437, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(13, a -> Extra.EX_AERIAL_RAVE_B4), () -> Extra.EX_AERIAL_RAVE_B3_END).setClickAction(entityIn -> {
            motion = entityIn.getDeltaMovement();
            entityIn.setDeltaMovement(motion.x, 0.6, motion.z);
            return;
        }).addTickAction(ComboState.TimeLineTickAction.getBuilder().put((int)TimeValueHelper.getTicksFromFrames(5.0f), entityIn -> AttackManager.doSlash(entityIn, 237.0f, Vector3d.ZERO, false, false, 1.0, KnockBacks.toss)).put((int)TimeValueHelper.getTicksFromFrames(10.0f), entityIn -> AttackManager.doSlash(entityIn, 237.0f, Vector3d.ZERO, false, false, 1.0, KnockBacks.toss)).build()).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, entityIn -> UserPoseOverrider.setRot(entityIn, -90.0f, true)).put(1, entityIn -> UserPoseOverrider.setRot(entityIn, -90.0f, true)).put(2, entityIn -> UserPoseOverrider.setRot(entityIn, -90.0f, true)).put(3, entityIn -> UserPoseOverrider.setRot(entityIn, -90.0f, true)).put(4, entityIn -> UserPoseOverrider.setRot(entityIn, -120.0f, true)).put(5, entityIn -> UserPoseOverrider.setRot(entityIn, -120.0f, true)).put(6, entityIn -> UserPoseOverrider.setRot(entityIn, -120.0f, true)).put(7, entityIn -> UserPoseOverrider.resetRot(entityIn)).build()).addTickAction(FallHandler::fallDecrease).addHitEffect(StunManager::setStun).setIsAerial();
        EX_AERIAL_RAVE_B3_END = new ComboState("ex_aerial_rave_b3_end", 80, () -> 1437, () -> 1443, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build()).addTickAction(FallHandler::fallDecrease).setReleaseAction(ComboState::releaseActionQuickCharge);
        EX_AERIAL_RAVE_B4 = new ComboState("ex_aerial_rave_b4", 80, () -> 1500, () -> 1537, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(15, a -> ComboState.NONE), () -> Extra.EX_AERIAL_RAVE_B4_END).addTickAction(ComboState.TimeLineTickAction.getBuilder().put((int)TimeValueHelper.getTicksFromFrames(10.0f) + 0, entityIn -> AttackManager.doSlash(entityIn, 45.0f, Vector3d.ZERO, false, false, 1.0, KnockBacks.meteor)).put((int)TimeValueHelper.getTicksFromFrames(10.0f) + 1, entityIn -> AttackManager.doSlash(entityIn, 50.0f, Vector3d.ZERO, true, true, 1.0, KnockBacks.meteor)).build()).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(5, entityIn -> UserPoseOverrider.setRot(entityIn, 90.0f, true)).put(6, entityIn -> UserPoseOverrider.setRot(entityIn, 90.0f, true)).put(7, entityIn -> UserPoseOverrider.setRot(entityIn, 90.0f, true)).put(8, entityIn -> UserPoseOverrider.setRot(entityIn, 90.0f, true)).put(9, entityIn -> UserPoseOverrider.resetRot(entityIn)).build()).addTickAction(FallHandler::fallDecrease).addHitEffect(StunManager::setStun).setIsAerial();
        EX_AERIAL_RAVE_B4_END = new ComboState("ex_aerial_rave_b4_end", 80, () -> 1537, () -> 1547, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build()).addTickAction(FallHandler::fallDecrease).setReleaseAction(ComboState::releaseActionQuickCharge);
        ex_upperslash_command = EnumSet.of(InputCommand.BACK, InputCommand.R_DOWN);
        final int elapsed;
        final int fireTime;
        EnumSet<InputCommand> commands2;
        EX_UPPERSLASH = new ComboState("ex_upperslash", 90, () -> 1600, () -> 1659, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(11, a -> ComboState.NONE), () -> Extra.EX_UPPERSLASH_END).addHoldAction(player -> {
            elapsed = player.getTicksUsingItem();
            fireTime = (int)TimeValueHelper.getTicksFromFrames(9.0f);
            if (fireTime != elapsed) {
                return;
            }
            else {
                commands2 = player.getCapability((Capability)Extra.INPUT_STATE).map(state -> state.getCommands(player)).orElseGet(() -> EnumSet.noneOf(InputCommand.class));
                if (!commands2.containsAll(Extra.ex_upperslash_command)) {
                    return;
                }
                else {
                    player.getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(state -> state.updateComboSeq(player, Extra.EX_UPPERSLASH_JUMP));
                    return;
                }
            }
        }).addTickAction(ComboState.TimeLineTickAction.getBuilder().put((int)TimeValueHelper.getTicksFromFrames(7.0f), entityIn -> AttackManager.doSlash(entityIn, -80.0f, Vector3d.ZERO, false, false, 1.0, KnockBacks.toss)).build()).addHitEffect((t, a) -> StunManager.setStun(t, 15L));
        EX_UPPERSLASH_END = new ComboState("ex_upperslash_end", 90, () -> 1659, () -> 1693, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build()).setReleaseAction(ComboState::releaseActionQuickCharge);
        final Vector3d motion2;
        EX_UPPERSLASH_JUMP = new ComboState("ex_upperslash_jump", 90, () -> 1700, () -> 1713, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(7, a -> ComboState.NONE), () -> Extra.EX_UPPERSLASH_JUMP_END).setClickAction(entityIn -> {
            motion2 = entityIn.getDeltaMovement();
            entityIn.setDeltaMovement(motion2.x, 0.6000000238418579, motion2.z);
            entityIn.setOnGround(false);
            entityIn.hasImpulse = true;
            return;
        }).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, entityIn -> UserPoseOverrider.setRot(entityIn, 90.0f, true)).put(1, entityIn -> UserPoseOverrider.setRot(entityIn, 90.0f, true)).put(2, entityIn -> UserPoseOverrider.setRot(entityIn, 90.0f, true)).put(3, entityIn -> UserPoseOverrider.setRot(entityIn, 90.0f, true)).put(4, entityIn -> UserPoseOverrider.resetRot(entityIn)).build()).addTickAction(FallHandler::fallDecrease).addHitEffect(StunManager::setStun).setIsAerial();
        EX_UPPERSLASH_JUMP_END = new ComboState("ex_upperslash_jump_end", 90, () -> 1713, () -> 1717, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build().andThen(FallHandler::fallDecrease));
        final Vector3d motion3;
        final long elapsed2;
        Vector3d motion4;
        EX_AERIAL_CLEAVE = new ComboState("ex_aerial_cleave", 70, () -> 1800, () -> 1812, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> Extra.EX_AERIAL_CLEAVE, () -> Extra.EX_AERIAL_CLEAVE_LOOP).setClickAction(e -> {
            motion3 = e.getDeltaMovement();
            e.setDeltaMovement(motion3.x, 0.1, motion3.z);
            return;
        }).addTickAction(e -> {
            e.fallDistance = 1.0f;
            elapsed2 = ComboState.getElapsed(e);
            if (elapsed2 == 2L) {
                e.level.playSound((PlayerEntity)null, e.getX(), e.getY(), e.getZ(), SoundEvents.PLAYER_ATTACK_STRONG, SoundCategory.PLAYERS, 0.75f, 1.0f);
            }
            if (2L < elapsed2) {
                motion4 = e.getDeltaMovement();
                e.setDeltaMovement(motion4.x, motion4.y - 3.0, motion4.z);
            }
            if (elapsed2 % 2L == 0L) {
                AttackManager.areaAttack(e, KnockBacks.meteor.action, 0.1f, true, false, true);
            }
            if (e.isOnGround()) {
                AttackManager.doSlash(e, 55.0f, Vector3d.ZERO, true, true, 1.0, KnockBacks.meteor);
                e.getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(state -> {
                    state.updateComboSeq(e, Extra.EX_AERIAL_CLEAVE_LANDING);
                    FallHandler.spawnLandingParticle(e, 20.0f);
                });
            }
            return;
        }).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, entityIn -> UserPoseOverrider.setRot(entityIn, 90.0f, true)).put(1, entityIn -> UserPoseOverrider.setRot(entityIn, 90.0f, true)).put(2, entityIn -> UserPoseOverrider.setRot(entityIn, 90.0f, true)).put(3, entityIn -> UserPoseOverrider.setRot(entityIn, 90.0f, true)).put(4, entityIn -> UserPoseOverrider.resetRot(entityIn)).build());
        final Vector3d motion5;
        final long elapsed3;
        EX_AERIAL_CLEAVE_LOOP = new ComboState("ex_aerial_cleave_loop", 70, () -> 1812, () -> 1817, () -> 1.0f, () -> true, () -> 1000, DefaultResources.ExMotionLocation, a -> Extra.EX_AERIAL_CLEAVE_LOOP, () -> ComboState.NONE).addTickAction(e -> {
            e.fallDistance = 1.0f;
            motion5 = e.getDeltaMovement();
            e.setDeltaMovement(motion5.x, motion5.y - 3.0, motion5.z);
            elapsed3 = ComboState.getElapsed(e);
            if (elapsed3 % 2L == 0L) {
                AttackManager.areaAttack(e, KnockBacks.meteor.action, 0.1f, true, false, true);
            }
            if (e.isOnGround()) {
                AttackManager.doSlash(e, 55.0f, Vector3d.ZERO, true, true, 1.0, KnockBacks.meteor);
                e.getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(state -> {
                    state.updateComboSeq(e, Extra.EX_AERIAL_CLEAVE_LANDING);
                    FallHandler.spawnLandingParticle(e, 20.0f);
                });
            }
            return;
        }).addHitEffect((t, a) -> StunManager.setStun(t, 15L));
        EX_AERIAL_CLEAVE_LANDING = new ComboState("ex_aerial_cleave_landing", 70, () -> 1816, () -> 1859, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(6, a -> ComboState.NONE), () -> Extra.EX_AERIAL_CLEAVE_END).setClickAction(entityIn -> AttackManager.doSlash(entityIn, 60.0f, Vector3d.ZERO, false, false, 1.0, KnockBacks.meteor)).addTickAction(entityIn -> UserPoseOverrider.resetRot(entityIn));
        EX_AERIAL_CLEAVE_END = new ComboState("ex_aerial_cleave_end", 70, () -> 1859, () -> 1886, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build()).addTickAction(entityIn -> UserPoseOverrider.resetRot(entityIn)).setReleaseAction(ComboState::releaseActionQuickCharge);
        final AttributeModifier am;
        final ModifiableAttributeInstance mai;
        final boolean isRightDown;
        final long elapsed4;
        float roll;
        boolean critical;
        EX_RAPID_SLASH = new ComboState("ex_rapid_slash", 70, () -> 2000, () -> 2019, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> a.hasEffect(Effects.DAMAGE_BOOST) ? Extra.EX_RAPID_SLASH_QUICK : Extra.EX_RAPID_SLASH, () -> Extra.EX_RAPID_SLASH_END).addHoldAction(e -> {
            am = new AttributeModifier("SweepingDamageRatio", -3.0, AttributeModifier.Operation.ADDITION);
            mai = e.getAttribute((Attribute)ForgeMod.REACH_DISTANCE.get());
            mai.addTransientModifier(am);
            AttackManager.areaAttack(e, t -> {
                isRightDown = e.getCapability((Capability)Extra.INPUT_STATE).map(state -> state.getCommands().contains(InputCommand.R_DOWN)).orElse(false);
                if (isRightDown) {
                    e.getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(state -> {
                        if (state.getComboSeq() == Extra.EX_RAPID_SLASH) {
                            final List<Entity> hits = AttackManager.areaAttack(e, KnockBacks.toss.action, 0.01f, true, true, true);
                            if (!hits.isEmpty()) {
                                state.updateComboSeq(e, Extra.EX_RISING_STAR);
                            }
                        }
                    });
                }
                return;
            }, 0.001f, true, false, true);
            mai.removeModifier(am);
            return;
        }).addTickAction(e -> {
            elapsed4 = ComboState.getElapsed(e);
            if (elapsed4 == 0L) {
                e.level.playSound((PlayerEntity)null, e.getX(), e.getY(), e.getZ(), SoundEvents.ARMOR_EQUIP_IRON, SoundCategory.PLAYERS, 1.0f, 1.0f);
            }
            if (elapsed4 <= 3L && e.isOnGround()) {
                e.moveRelative(e.isInWater() ? 0.35f : 0.8f, new Vector3d(0.0, 0.0, 1.0));
            }
            if (2L <= elapsed4 && elapsed4 < 6L) {
                roll = -45.0f + 90.0f * e.getRandom().nextFloat();
                if (elapsed4 % 2L == 0L) {
                    roll += 180.0f;
                }
                critical = e.hasEffect(Effects.DAMAGE_BOOST);
                AttackManager.doSlash(e, roll, genRushOffset(e), false, critical, 0.10000000149011612);
            }
            if (elapsed4 == 7L) {
                AttackManager.doSlash(e, -30.0f, genRushOffset(e), false, true, 0.10000000149011612);
            }
            if (7L <= elapsed4 && elapsed4 <= 10L) {
                UserPoseOverrider.setRot((Entity)e, 90.0f, true);
            }
            if (10L < elapsed4) {
                UserPoseOverrider.setRot((Entity)e, 0.0f, false);
            }
            return;
        }).addHitEffect(StunManager::setStun);
        EX_RAPID_SLASH_QUICK = new ComboState("ex_rapid_slash_quick", 70, () -> 2000, () -> 2001, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> Extra.EX_RAPID_SLASH_QUICK, () -> Extra.EX_RAPID_SLASH);
        EX_RAPID_SLASH_END = new ComboState("ex_rapid_slash_end", 70, () -> 2019, () -> 2054, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> Extra.EX_RAPID_SLASH_END2);
        EX_RAPID_SLASH_END2 = new ComboState("ex_rapid_slash_end2", 70, () -> 2054, () -> 2073, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build()).setReleaseAction(ComboState::releaseActionQuickCharge);
        final long elapsed5;
        Vector3d motion6;
        double yMotion;
        EX_RISING_STAR = new ComboState("ex_rising_star", 80, () -> 2100, () -> 2137, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, ComboState.TimeoutNext.buildFromFrame(18, a -> ComboState.NONE), () -> Extra.EX_RISING_STAR_END).setClickAction(entityIn -> {
            entityIn.setDeltaMovement(0.0, 0.6, 0.0);
            entityIn.setOnGround(false);
            entityIn.hasImpulse = true;
            AttackManager.doSlash(entityIn, -57.0f, Vector3d.ZERO, false, false, 1.0, KnockBacks.toss);
            return;
        }).addTickAction(ComboState.TimeLineTickAction.getBuilder().put((int)TimeValueHelper.getTicksFromFrames(9.0f), entityIn -> AttackManager.doSlash(entityIn, -57.0f, Vector3d.ZERO, false, false, 1.0, KnockBacks.toss)).build()).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(1, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(2, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(3, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(4, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(5, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(6, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(7, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(8, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(9, entityIn -> UserPoseOverrider.setRot(entityIn, 72.0f, true)).put(10, entityIn -> UserPoseOverrider.resetRot(entityIn)).build()).addTickAction(entityIn -> {
            elapsed5 = ComboState.getElapsed(entityIn);
            if (elapsed5 < 3L) {
                motion6 = entityIn.getDeltaMovement();
                yMotion = motion6.y;
                if (yMotion <= 0.0) {
                    yMotion = 0.6;
                    entityIn.setOnGround(false);
                    entityIn.hasImpulse = true;
                }
                entityIn.setDeltaMovement(0.0, yMotion, 0.0);
            }
            return;
        }).addTickAction(FallHandler::fallDecrease).addHitEffect(StunManager::setStun).setIsAerial();
        EX_RISING_STAR_END = new ComboState("ex_rising_star_end", 80, () -> 2137, () -> 2147, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build()).addTickAction(FallHandler::fallDecrease).setReleaseAction(ComboState::releaseActionQuickCharge);
        final long elapsed6;
        Vector3d vec;
        double d0;
        double d2;
        double d3;
        Vector3d vec2;
        EX_JUDGEMENT_CUT = new ComboState("ex_judgement_cut", 50, () -> 1900, () -> 1923, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> Extra.EX_JUDGEMENT_CUT, () -> Extra.EX_JUDGEMENT_CUT_SLASH).addTickAction(e -> {
            elapsed6 = ComboState.getElapsed(e);
            if (elapsed6 == 0L) {
                e.playSound(SoundEvents.TRIDENT_THROW, 0.8f, 0.625f + 0.1f * e.getRandom().nextFloat());
            }
            if (elapsed6 <= 3L) {
                e.moveRelative(-0.3f, new Vector3d(0.0, 0.0, 1.0));
                vec = e.getDeltaMovement();
                d0 = vec.x;
                d2 = vec.z;
                d3 = 0.05;
                while (d0 != 0.0 && e.level.noCollision((Entity)e, e.getBoundingBox().move(d0, (double)(-e.maxUpStep), 0.0))) {
                    if (d0 < 0.05 && d0 >= -0.05) {
                        d0 = 0.0;
                    }
                    else if (d0 > 0.0) {
                        d0 -= 0.05;
                    }
                    else {
                        d0 += 0.05;
                    }
                }
                while (d2 != 0.0 && e.level.noCollision((Entity)e, e.getBoundingBox().move(0.0, (double)(-e.maxUpStep), d2))) {
                    if (d2 < 0.05 && d2 >= -0.05) {
                        d2 = 0.0;
                    }
                    else if (d2 > 0.0) {
                        d2 -= 0.05;
                    }
                    else {
                        d2 += 0.05;
                    }
                }
                while (d0 != 0.0 && d2 != 0.0 && e.level.noCollision((Entity)e, e.getBoundingBox().move(d0, (double)(-e.maxUpStep), d2))) {
                    if (d0 < 0.05 && d0 >= -0.05) {
                        d0 = 0.0;
                    }
                    else if (d0 > 0.0) {
                        d0 -= 0.05;
                    }
                    else {
                        d0 += 0.05;
                    }
                    if (d2 < 0.05 && d2 >= -0.05) {
                        d2 = 0.0;
                    }
                    else if (d2 > 0.0) {
                        d2 -= 0.05;
                    }
                    else {
                        d2 += 0.05;
                    }
                }
                vec2 = new Vector3d(d0, vec.y, d2);
                e.move(MoverType.SELF, vec2);
            }
            e.setDeltaMovement(e.getDeltaMovement().multiply(0.0, 1.0, 0.0));
            return;
        }).addTickAction(FallHandler::fallDecrease).addTickAction(entityIn -> UserPoseOverrider.resetRot(entityIn));
        EX_JUDGEMENT_CUT_SLASH = new ComboState("ex_judgement_cut_slash", 50, () -> 1923, () -> 1928, () -> 0.4f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> Extra.EX_JUDGEMENT_CUT_SLASH, () -> Extra.EX_JUDGEMENT_CUT_SHEATH).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, JudgementCut::doJudgementCut).build()).addTickAction(FallHandler::fallDecrease).addHitEffect(StunManager::setStun);
        EX_JUDGEMENT_CUT_SHEATH = new ComboState("ex_judgement_cut_sheath", 50, () -> 1928, () -> 1963, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(FallHandler::fallDecrease).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build()).setReleaseAction(ComboState::releaseActionQuickCharge);
        EX_JUDGEMENT_CUT_SLASH_AIR = new ComboState("ex_judgement_cut_slash_air", 50, () -> 1923, () -> 1928, () -> 0.5f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> Extra.EX_JUDGEMENT_CUT_SLASH_AIR, () -> Extra.EX_JUDGEMENT_CUT_SHEATH_AIR).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, JudgementCut::doJudgementCut).build()).addTickAction(FallHandler::fallResist).addTickAction(entityIn -> UserPoseOverrider.resetRot(entityIn)).addHitEffect(StunManager::setStun);
        EX_JUDGEMENT_CUT_SHEATH_AIR = new ComboState("ex_judgement_cut_sheath_air", 50, () -> 1928, () -> 1963, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(FallHandler::fallDecrease).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build()).setReleaseAction(ComboState::releaseActionQuickCharge);
        EX_JUDGEMENT_CUT_SLASH_JUST = new ComboState("ex_judgement_cut_slash_just2", 45, () -> 1923, () -> 1928, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> Extra.EX_JUDGEMENT_CUT_SLASH_JUST, () -> Extra.EX_JUDGEMENT_CUT_SLASH_JUST2).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, JudgementCut::doJudgementCutJust).build()).addTickAction(entityIn -> UserPoseOverrider.resetRot(entityIn)).addTickAction(FallHandler::fallResist).addHitEffect(StunManager::setStun);
        EX_JUDGEMENT_CUT_SLASH_JUST2 = new ComboState("ex_judgement_cut_slash_just2", 50, () -> 1923, () -> 1928, () -> 0.75f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> Extra.EX_JUDGEMENT_CUT_SLASH_JUST2, () -> Extra.EX_JUDGEMENT_CUT_SLASH_JUST_SHEATH).addTickAction(entityIn -> UserPoseOverrider.resetRot(entityIn)).addTickAction(FallHandler::fallResist);
        EX_JUDGEMENT_CUT_SLASH_JUST_SHEATH = new ComboState("ex_judgement_cut_slash_just_sheath", 50, () -> 1928, () -> 1963, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(entityIn -> UserPoseOverrider.resetRot(entityIn)).addTickAction(FallHandler::fallDecrease).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build()).setReleaseAction(ComboState::releaseActionQuickCharge);
        Vector3d pos;
        EntitySlashEffect jc;
        int colorCode;
        EX_VOID_SLASH = new ComboState("ex_void_slash", 45, () -> 2200, () -> 2277, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> Extra.EX_VOID_SLASH, () -> Extra.EX_VOID_SLASH_SHEATH).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(28, living -> {
            if (living.level.isClientSide) {
                pos = living.position().add(0.0, living.getEyeHeight() * 0.75, 0.0).add(living.getLookAngle().scale(0.30000001192092896));
                jc = new EntitySlashEffect(SlashBlade.RegistryEvents.SlashEffect, living.level) {
                    @Override
                    protected void tryDespawn() {
                        if (this.getShooter() != null) {
                            final long timeout = this.getShooter().getPersistentData().getLong("BreakActionTimeout");
                            if (timeout <= this.level.getGameTime() || timeout == 0L) {
                                this.level.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.GLASS_BREAK, SoundCategory.PLAYERS, 0.8f, 0.625f + 0.1f * this.random.nextFloat());
                                this.remove();
                            }
                        }
                        super.tryDespawn();
                    }
                };
                jc.setPos(pos.x, pos.y, pos.z);
                jc.setOwner((Entity)living);
                jc.setRotationRoll(0.0f);
                jc.yRot = living.yRot;
                jc.xRot = 0.0f;
                colorCode = living.getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).map(state -> state.getColorCode()).orElseGet(() -> 16777215);
                jc.setColor(colorCode);
                jc.setMute(true);
                jc.setIsCritical(true);
                jc.setDamage(living.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2.0);
                jc.setKnockBack(KnockBacks.cancel);
                jc.setBaseSize(20.0f);
                jc.setLifetime(100);
                living.level.addFreshEntity((Entity)jc);
            }
            return;
        }).build()).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(28, entityIn -> UserPoseOverrider.setRot(entityIn, -36.0f, true)).put(29, entityIn -> UserPoseOverrider.setRot(entityIn, -36.0f, true)).put(30, entityIn -> UserPoseOverrider.setRot(entityIn, -36.0f, true)).put(31, entityIn -> UserPoseOverrider.setRot(entityIn, -36.0f, true)).put(32, entityIn -> UserPoseOverrider.setRot(entityIn, -36.0f, true)).put(33, entityIn -> UserPoseOverrider.setRot(entityIn, 0.0f, true)).put(79, entityIn -> UserPoseOverrider.setRot(entityIn, 18.0f, true)).put(80, entityIn -> UserPoseOverrider.setRot(entityIn, 18.0f, true)).put(81, entityIn -> UserPoseOverrider.setRot(entityIn, 18.0f, true)).put(82, entityIn -> UserPoseOverrider.setRot(entityIn, 18.0f, true)).put(83, entityIn -> UserPoseOverrider.setRot(entityIn, 18.0f, true)).put(84, entityIn -> UserPoseOverrider.setRot(entityIn, 18.0f, true)).put(85, entityIn -> UserPoseOverrider.setRot(entityIn, 18.0f, true)).put(86, entityIn -> UserPoseOverrider.setRot(entityIn, 18.0f, true)).put(87, entityIn -> UserPoseOverrider.setRot(entityIn, 18.0f, true)).put(88, entityIn -> UserPoseOverrider.setRot(entityIn, 18.0f, true)).put(89, entityIn -> UserPoseOverrider.setRot(entityIn, 0.0f, true)).build()).addTickAction(FallHandler::fallResist).addHitEffect(StunManager::setStun);
        EX_VOID_SLASH_SHEATH = new ComboState("ex_void_slash_sheath", 50, () -> 2278, () -> 2299, () -> 1.0f, () -> false, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(entityIn -> UserPoseOverrider.resetRot(entityIn)).addTickAction(FallHandler::fallDecrease).addTickAction(ComboState.TimeLineTickAction.getBuilder().put(0, Extra::playQuickSheathSoundAction).build()).setReleaseAction(ComboState::releaseActionQuickCharge);
    }
}
