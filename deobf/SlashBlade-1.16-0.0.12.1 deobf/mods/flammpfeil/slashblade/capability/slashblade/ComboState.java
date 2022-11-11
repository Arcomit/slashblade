//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.capability.slashblade;

import mods.flammpfeil.slashblade.capability.inputstate.*;
import net.minecraftforge.common.capabilities.*;
import java.util.function.*;
import javax.annotation.*;
import net.minecraft.enchantment.*;
import mods.flammpfeil.slashblade.item.*;
import net.minecraftforge.registries.*;
import mods.flammpfeil.slashblade.event.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import mods.flammpfeil.slashblade.util.*;
import net.minecraft.util.math.vector.*;
import mods.flammpfeil.slashblade.ability.*;
import mods.flammpfeil.slashblade.event.client.*;
import net.minecraft.entity.*;
import mods.flammpfeil.slashblade.init.*;
import java.util.*;
import java.util.stream.*;
import mods.flammpfeil.slashblade.specialattack.*;
import mods.flammpfeil.slashblade.capability.slashblade.combo.*;
import com.google.common.collect.*;

public class ComboState extends RegistryBase<ComboState>
{
    @CapabilityInject(IInputState.class)
    public static Capability<IInputState> INPUT_STATE;
    public static final ComboState NONE;
    static List<Map.Entry<EnumSet<InputCommand>, Supplier<ComboState>>> standbyMap;
    public static final ComboState STANDBY;
    public static final ComboState COMBO_A1;
    public static final ComboState COMBO_A2;
    public static final ComboState COMBO_A3;
    public static final ComboState COMBO_A3_F;
    private static final EnumSet<InputCommand> combo_b1_alt;
    public static final ComboState COMBO_B1;
    public static final ComboState COMBO_B1_F;
    public static final ComboState COMBO_B2;
    public static final ComboState COMBO_B2_F;
    public static final ComboState COMBO_AA1;
    public static final ComboState COMBO_AA1_F;
    public static final ComboState COMBO_AA2;
    public static final ComboState COMBO_AA2_F;
    public static final ComboState ARTS_RAPID_SLASH;
    public static final ComboState ARTS_RAPID_SLASH_F;
    public static final ComboState ARTS_RISING_STAR;
    public static final ComboState ARTS_HELM_BREAKER;
    public static final ComboState ARTS_HELM_BREAKER_F;
    static final EnumSet<InputCommand> jc_cycle_input;
    static final RangeMap<Long, SlashArts.ArtsType> jc_cycle_accept;
    public static final ComboState SLASH_ARTS_JC;
    public static ComboState ExtraStandBy;
    private ResourceLocation motionLoc;
    private Supplier<Integer> start;
    private Supplier<Integer> end;
    private Supplier<Float> speed;
    private Supplier<Boolean> roop;
    public Supplier<Integer> timeout;
    private Function<LivingEntity, ComboState> next;
    private Supplier<ComboState> nextOfTimeout;
    private Consumer<LivingEntity> holdAction;
    private Consumer<LivingEntity> tickAction;
    private BiConsumer<LivingEntity, LivingEntity> hitEffect;
    private Consumer<LivingEntity> clickAction;
    private BiFunction<LivingEntity, Integer, SlashArts.ArtsType> releaseAction;
    private boolean isAerial;
    private int priority;
    
    public ResourceLocation getMotionLoc() {
        return this.motionLoc;
    }
    
    public int getStartFrame() {
        return this.start.get();
    }
    
    public int getEndFrame() {
        return this.end.get();
    }
    
    public float getSpeed() {
        return this.speed.get();
    }
    
    public boolean getRoop() {
        return this.roop.get();
    }
    
    public int getTimeoutMS() {
        return (int)(TimeValueHelper.getMSecFromFrames((float)Math.abs(this.getEndFrame() - this.getStartFrame())) / this.getSpeed()) + this.timeout.get();
    }
    
    public void holdAction(final LivingEntity user) {
        this.holdAction.accept(user);
    }
    
    public ComboState addHoldAction(final Consumer<LivingEntity> holdAction) {
        this.holdAction = this.holdAction.andThen(holdAction);
        return this;
    }
    
    public void tickAction(final LivingEntity user) {
        this.tickAction.accept(user);
    }
    
    public ComboState addTickAction(final Consumer<LivingEntity> tickAction) {
        this.tickAction = this.tickAction.andThen(tickAction);
        return this;
    }
    
    public void hitEffect(final LivingEntity target, final LivingEntity attacker) {
        this.hitEffect.accept(target, attacker);
    }
    
    public ComboState addHitEffect(final BiConsumer<LivingEntity, LivingEntity> hitEffect) {
        this.hitEffect = this.hitEffect.andThen(hitEffect);
        return this;
    }
    
    public void clickAction(final LivingEntity user) {
        this.clickAction.accept(user);
    }
    
    public ComboState setClickAction(final Consumer<LivingEntity> clickAction) {
        this.clickAction = clickAction;
        return this;
    }
    
    public SlashArts.ArtsType releaseAction(final LivingEntity user, final int elapsed) {
        return this.releaseAction.apply(user, elapsed);
    }
    
    public ComboState setReleaseAction(final BiFunction<LivingEntity, Integer, SlashArts.ArtsType> clickAction) {
        this.releaseAction = clickAction;
        return this;
    }
    
    public ComboState(final String name, final int priority, final Supplier<Integer> start, final Supplier<Integer> end, final Supplier<Float> speed, final Supplier<Boolean> roop, final Supplier<Integer> timeout, final ResourceLocation motionLoc, final Function<LivingEntity, ComboState> next, final Supplier<ComboState> nextOfTimeout) {
        super(name);
        this.start = start;
        this.end = end;
        this.speed = speed;
        this.timeout = timeout;
        this.roop = roop;
        this.motionLoc = motionLoc;
        this.next = next;
        this.nextOfTimeout = nextOfTimeout;
        this.holdAction = (a -> {});
        this.tickAction = ArrowReflector::doTicks;
        this.hitEffect = ((a, b) -> {});
        this.clickAction = (user -> {});
        this.releaseAction = (BiFunction<LivingEntity, Integer, SlashArts.ArtsType>)((u, e) -> SlashArts.ArtsType.Fail);
        this.isAerial = false;
        this.priority = priority;
    }
    
    @Override
    public String getPath() {
        return "combostate";
    }
    
    @Override
    public ComboState getNone() {
        return ComboState.NONE;
    }
    
    public ComboState getNext(final LivingEntity living) {
        return this.next.apply(living);
    }
    
    public ComboState getNextOfTimeout() {
        return this.nextOfTimeout.get();
    }
    
    @Nonnull
    public ComboState checkTimeOut(final float msec) {
        return (this.getTimeoutMS() < msec) ? this.nextOfTimeout.get() : this;
    }
    
    public boolean isAerial() {
        return this.isAerial;
    }
    
    public ComboState setIsAerial() {
        this.isAerial = true;
        return this;
    }
    
    public int getPriority() {
        return this.priority;
    }
    
    public static SlashArts.ArtsType releaseActionQuickCharge(final LivingEntity user, final Integer elapsed) {
        final int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.SOUL_SPEED, user);
        if (elapsed <= 3 + level) {
            return SlashArts.ArtsType.Jackpot;
        }
        return SlashArts.ArtsType.Fail;
    }
    
    public static long getElapsed(final LivingEntity livingEntity) {
        return livingEntity.getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).map(state -> state.getElapsedTime(livingEntity)).orElseGet(() -> 0L);
    }
    
    static {
        ComboState.INPUT_STATE = null;
        NONE = new ComboState(ComboState.BaseInstanceName, 1000, () -> 0, () -> 1, () -> 1.0f, () -> true, () -> 0, DefaultResources.ExMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(e -> UserPoseOverrider.resetRot(e));
        ComboState.standbyMap = new HashMap<EnumSet<InputCommand>, Supplier<ComboState>>() {
            {
                this.put(EnumSet.of(InputCommand.ON_GROUND, InputCommand.SNEAK, InputCommand.FORWARD, InputCommand.R_CLICK), () -> ComboState.ARTS_RAPID_SLASH);
                this.put(EnumSet.of(InputCommand.ON_GROUND, InputCommand.L_CLICK), () -> ComboState.COMBO_B1);
                this.put(EnumSet.of(InputCommand.ON_GROUND, InputCommand.BACK, InputCommand.SNEAK, InputCommand.R_CLICK), () -> ComboState.COMBO_B1);
                this.put(EnumSet.of(InputCommand.ON_GROUND, InputCommand.R_CLICK), () -> ComboState.COMBO_A1);
                this.put(EnumSet.of(InputCommand.ON_AIR, InputCommand.SNEAK, InputCommand.FORWARD, InputCommand.R_CLICK), () -> ComboState.ARTS_HELM_BREAKER);
                this.put(EnumSet.of(InputCommand.ON_AIR), () -> ComboState.COMBO_AA1);
            }
        }.entrySet().stream().collect((Collector<? super Object, ?, List<Map.Entry<EnumSet<InputCommand>, Supplier<ComboState>>>>)Collectors.toList());
        final EnumSet<InputCommand> commands;
        STANDBY = new ComboState("standby_old", 10, () -> 30, () -> 31, () -> 1.0f, () -> true, () -> 1000, DefaultResources.BaseMotionLocation, a -> {
            commands = a.getCapability((Capability)ComboState.INPUT_STATE).map(state -> state.getCommands(a)).orElseGet(() -> EnumSet.noneOf(InputCommand.class));
            return (ComboState)ComboState.standbyMap.stream().filter(entry -> commands.containsAll(entry.getKey())).min(Comparator.comparingInt(entry -> entry.getValue().get().getPriority())).map(entry -> entry.getValue().get()).orElseGet(() -> ComboState.NONE);
        }, () -> ComboState.NONE);
        COMBO_A1 = new ComboState("combo_a1", 100, () -> 60, () -> 70, () -> 1.5f, () -> false, () -> 1000, DefaultResources.BaseMotionLocation, a -> ComboState.COMBO_A2, () -> ComboState.NONE).setClickAction(e -> AttackManager.areaAttack(e, KnockBackHandler::setCancel)).addHitEffect(StunManager::setStun);
        COMBO_A2 = new ComboState("combo_a2", 100, () -> 70, () -> 80, () -> 1.5f, () -> false, () -> 1000, DefaultResources.BaseMotionLocation, a -> ComboState.COMBO_A3, () -> ComboState.NONE).setClickAction(e -> AttackManager.areaAttack(e, KnockBackHandler::setCancel)).addHitEffect(StunManager::setStun);
        COMBO_A3 = new ComboState("combo_a3", 100, () -> 80, () -> 90, () -> 1.75f, () -> false, () -> 600, DefaultResources.BaseMotionLocation, a -> ComboState.NONE, () -> ComboState.COMBO_A3_F).setClickAction(e -> AttackManager.areaAttack(e, ee -> KnockBackHandler.setSmash(ee, 1.5)));
        COMBO_A3_F = new ComboState("combo_a3_f", 100, () -> 90, () -> 120, () -> 1.5f, () -> false, () -> 2000, DefaultResources.BaseMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE);
        combo_b1_alt = EnumSet.of(InputCommand.BACK, InputCommand.R_DOWN);
        final int elapsed;
        final EnumSet<InputCommand> commands2;
        Vector3d motion;
        COMBO_B1 = new ComboState("combo_b1", 90, () -> 150, () -> 160, () -> 1.0f, () -> false, () -> 1000, DefaultResources.BaseMotionLocation, a -> ComboState.COMBO_B2, () -> ComboState.COMBO_B1_F).setClickAction(e -> AttackManager.areaAttack(e, ee -> KnockBackHandler.setVertical(ee, 0.5))).addHoldAction(player -> {
            elapsed = player.getTicksUsingItem();
            commands2 = player.getCapability((Capability)ComboState.INPUT_STATE).map(state -> state.getCommands(player)).orElseGet(() -> EnumSet.noneOf(InputCommand.class));
            if (5 == elapsed && commands2.containsAll(ComboState.combo_b1_alt)) {
                motion = player.getDeltaMovement();
                player.setDeltaMovement(motion.x, motion.y + 0.7, motion.z);
                player.setOnGround(false);
                player.hasImpulse = true;
            }
            return;
        }).addHitEffect((e, a) -> StunManager.setStun(e, 15L)).addTickAction(playerIn -> FallHandler.fallDecrease(playerIn));
        COMBO_B1_F = new ComboState("combo_b1_f", 100, () -> 165, () -> 185, () -> 1.0f, () -> false, () -> 1000, DefaultResources.BaseMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(playerIn -> FallHandler.fallDecrease(playerIn));
        COMBO_B2 = new ComboState("combo_b2", 90, () -> 200, () -> 215, () -> 1.0f, () -> false, () -> 1000, DefaultResources.BaseMotionLocation, a -> ComboState.NONE, () -> ComboState.COMBO_B2_F).addHitEffect(StunManager::setStun).setClickAction(e -> AttackManager.areaAttack(e, ee -> KnockBackHandler.setVertical(ee, -5.0)));
        COMBO_B2_F = new ComboState("combo_b2_f", 100, () -> 215, () -> 240, () -> 1.0f, () -> false, () -> 1000, DefaultResources.BaseMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE);
        COMBO_AA1 = new ComboState("combo_aa1", 80, () -> 245, () -> 270, () -> 1.0f, () -> false, () -> 500, DefaultResources.BaseMotionLocation, a -> ComboState.COMBO_AA2, () -> ComboState.COMBO_AA1_F).setClickAction(e -> AttackManager.areaAttack(e, KnockBackHandler::setCancel)).setIsAerial().addTickAction(playerIn -> {
            FallHandler.fallDecrease(playerIn);
            playerIn.getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(state -> {
                final long elapsed = state.getElapsedTime(playerIn);
                if (elapsed == 4L) {
                    AttackManager.areaAttack(playerIn, KnockBackHandler::setCancel);
                }
            });
            return;
        }).addHitEffect(StunManager::setStun).setIsAerial();
        COMBO_AA1_F = new ComboState("combo_aa1_f", 80, () -> 269, () -> 270, () -> 20.0f, () -> true, () -> 1000, DefaultResources.BaseMotionLocation, a -> ComboState.COMBO_AA2, () -> ComboState.NONE);
        COMBO_AA2 = new ComboState("combo_aa2", 80, () -> 270, () -> 295, () -> 1.0f, () -> false, () -> 1000, DefaultResources.BaseMotionLocation, a -> ComboState.NONE, () -> ComboState.COMBO_AA2_F).setClickAction(e -> AttackManager.areaAttack(e, ee -> KnockBackHandler.setSmash(ee, 1.5))).setIsAerial().addTickAction(playerIn -> FallHandler.fallDecrease(playerIn));
        COMBO_AA2_F = new ComboState("combo_aa2_f", 100, () -> 295, () -> 300, () -> 1.0f, () -> false, () -> 400, DefaultResources.BaseMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE).addTickAction(playerIn -> FallHandler.fallDecrease(playerIn));
        final int elapsed2;
        ARTS_RAPID_SLASH = new ComboState("arts_rapid_slash", 70, () -> 80, () -> 90, () -> 1.0f, () -> false, () -> 1000, DefaultResources.BaseMotionLocation, a -> ComboState.ARTS_RAPID_SLASH_F, () -> ComboState.ARTS_RAPID_SLASH_F).setClickAction(e -> AttackManager.areaAttack(e, KnockBackHandler::setCancel)).addHitEffect(StunManager::setStun).addHoldAction(playerIn -> {
            elapsed2 = playerIn.getTicksUsingItem();
            if (elapsed2 < 6) {
                playerIn.getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(state -> AttackManager.areaAttack(playerIn, KnockBackHandler::setCancel, 1.0f, false, false, true));
                if (elapsed2 % 3 == 1) {
                    playerIn.level.playSound((PlayerEntity)null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 0.5f, 0.4f / (playerIn.getRandom().nextFloat() * 0.4f + 0.8f));
                }
            }
            if (elapsed2 <= 3 && playerIn.isOnGround()) {
                playerIn.moveRelative(playerIn.isInWater() ? 0.35f : 0.8f, new Vector3d(0.0, 0.0, 1.0));
            }
            if (elapsed2 == 10 && (!playerIn.level.isClientSide || playerIn.isOnGround())) {
                playerIn.getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(state -> {
                    final ComboState combo = ComboState.ARTS_RISING_STAR;
                    state.setComboSeq(combo);
                    state.setLastActionTime(playerIn.level.getGameTime());
                    combo.clickAction(playerIn);
                });
            }
            return;
        });
        ARTS_RAPID_SLASH_F = new ComboState("arts_rapid_slash_f", 70, () -> 90, () -> 120, () -> 1.0f, () -> false, () -> 1000, DefaultResources.BaseMotionLocation, a -> ComboState.NONE, () -> ComboState.NONE);
        final Vector3d motion2;
        final int elapsed3;
        ARTS_RISING_STAR = new ComboState("arts_rising_star", 100, () -> 250, () -> 255, () -> 0.75f, () -> false, () -> 1000, DefaultResources.BaseMotionLocation, a -> ComboState.NONE, () -> ComboState.COMBO_A3_F).addHitEffect(StunManager::setStun).setIsAerial().setClickAction(playerIn -> {
            AttackManager.areaAttack(playerIn, ee -> KnockBackHandler.setVertical(ee, 0.5), 1.0f, true, false, false);
            motion2 = playerIn.getDeltaMovement();
            playerIn.setDeltaMovement(0.0, motion2.y + 0.7, 0.0);
            playerIn.setOnGround(false);
            playerIn.hasImpulse = true;
            return;
        }).addHoldAction(playerIn -> {
            elapsed3 = playerIn.getTicksUsingItem();
            if (elapsed3 < 6) {
                playerIn.getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(state -> AttackManager.areaAttack(playerIn, ee -> KnockBackHandler.setVertical(ee, 0.5), 1.0f, false, false, true));
                if (elapsed3 % 2 == 1) {
                    playerIn.level.playSound((PlayerEntity)null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 0.5f, 0.4f / (playerIn.getRandom().nextFloat() * 0.4f + 0.8f));
                }
            }
            return;
        }).addTickAction(playerIn -> FallHandler.fallDecrease(playerIn));
        final Vector3d motion3;
        final int elapsed4;
        ARTS_HELM_BREAKER = new ComboState("arts_helm_breaker", 70, () -> 200, () -> 215, () -> 1.0f, () -> false, () -> 1000, DefaultResources.BaseMotionLocation, a -> ComboState.NONE, () -> ComboState.ARTS_HELM_BREAKER_F).addHitEffect(StunManager::setStun).setClickAction(playerIn -> {
            AttackManager.areaAttack(playerIn, KnockBacks.meteor.action, 1.0f, true, false, false);
            motion3 = playerIn.getDeltaMovement();
            playerIn.setDeltaMovement(motion3.x, motion3.y - 0.7, motion3.z);
            return;
        }).addHoldAction(playerIn -> {
            elapsed4 = playerIn.getTicksUsingItem();
            if (!playerIn.isOnGround()) {
                playerIn.getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(state -> AttackManager.areaAttack(playerIn, ee -> KnockBackHandler.setVertical(ee, -5.0), 1.0f, false, false, true));
                if (elapsed4 % 2 == 1) {
                    playerIn.level.playSound((PlayerEntity)null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 0.5f, 0.4f / (playerIn.getRandom().nextFloat() * 0.4f + 0.8f));
                }
            }
            return;
        }).addTickAction(playerIn -> {
            if (!playerIn.isOnGround()) {
                playerIn.fallDistance = 1.0f;
            }
            else {
                playerIn.getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(state -> {
                    AttackManager.areaAttack(playerIn, ee -> KnockBackHandler.setVertical(ee, -5.0), 1.3f, true, true, true);
                    state.setComboSeq(ComboState.ARTS_HELM_BREAKER_F);
                    state.setLastActionTime(playerIn.level.getGameTime());
                    FallHandler.spawnLandingParticle(playerIn, 20.0f);
                });
            }
            return;
        });
        ARTS_HELM_BREAKER_F = new ComboState("arts_helm_breaker_f", 70, () -> 214, () -> 215, () -> 20.0f, () -> true, () -> 600, DefaultResources.BaseMotionLocation, a -> ComboState.NONE, () -> ComboState.COMBO_B2_F);
        jc_cycle_input = EnumSet.of(InputCommand.L_DOWN, InputCommand.R_CLICK);
        jc_cycle_accept = (RangeMap)ImmutableRangeMap.builder().put(Range.lessThan((Comparable)7L), (Object)SlashArts.ArtsType.Fail).put(Range.closedOpen((Comparable)7L, (Comparable)8L), (Object)SlashArts.ArtsType.Jackpot).put(Range.closed((Comparable)8L, (Comparable)9L), (Object)SlashArts.ArtsType.Success).put(Range.greaterThan((Comparable)9L), (Object)SlashArts.ArtsType.Fail).build();
        final EnumSet<InputCommand> commands3;
        SLASH_ARTS_JC = new ComboState("slash_arts_jc", 50, () -> 115, () -> 120, () -> 0.5f, () -> false, () -> 600, DefaultResources.BaseMotionLocation, a -> {
            commands3 = a.getCapability((Capability)ComboState.INPUT_STATE).map(state -> state.getCommands(a)).orElseGet(() -> EnumSet.noneOf(InputCommand.class));
            if (commands3.containsAll(ComboState.jc_cycle_input)) {
                return (ComboState)a.getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).map(s -> {
                    final long time = a.level.getGameTime();
                    final long lastAction = s.getLastActionTime();
                    final long count = time - lastAction;
                    final SlashArts.ArtsType type = (SlashArts.ArtsType)ComboState.jc_cycle_accept.get((Comparable)count);
                    return s.getSlashArts().doArts(type, a);
                }).orElse(ComboState.NONE);
            }
            else {
                return ComboState.NONE;
            }
        }, () -> ComboState.NONE).addTickAction(playerIn -> FallHandler.fallResist(playerIn)).addTickAction(TimeLineTickAction.getBuilder().put(0, JudgementCut::doJudgementCut).build());
        ComboState.ExtraStandBy = Extra.STANDBY_EX;
    }
    
    public static class TimeoutNext implements Function<LivingEntity, ComboState>
    {
        long timeout;
        Function<LivingEntity, ComboState> next;
        
        public static TimeoutNext buildFromFrame(final int timeoutFrame, final Function<LivingEntity, ComboState> next) {
            return new TimeoutNext((int)TimeValueHelper.getTicksFromFrames((float)timeoutFrame), next);
        }
        
        public TimeoutNext(final long timeout, final Function<LivingEntity, ComboState> next) {
            this.timeout = timeout;
            this.next = next;
        }
        
        @Override
        public ComboState apply(final LivingEntity livingEntity) {
            final long elapsed = ComboState.getElapsed(livingEntity);
            if (this.timeout <= elapsed) {
                return this.next.apply(livingEntity);
            }
            return livingEntity.getMainHandItem().getCapability((Capability)ItemSlashBlade.BLADESTATE).map(state -> state.getComboSeq()).orElseGet(() -> ComboState.NONE);
        }
    }
    
    public static class TimeLineTickAction implements Consumer<LivingEntity>
    {
        long offset;
        Map<Integer, Consumer<LivingEntity>> timeLine;
        
        public static TimeLineTickActionBuilder getBuilder() {
            return new TimeLineTickActionBuilder();
        }
        
        TimeLineTickAction(final Map<Integer, Consumer<LivingEntity>> timeLine) {
            this.offset = -1L;
            (this.timeLine = (Map<Integer, Consumer<LivingEntity>>)Maps.newHashMap()).putAll(timeLine);
        }
        
        @Override
        public void accept(final LivingEntity livingEntity) {
            long elapsed = ComboState.getElapsed(livingEntity);
            if (this.offset < 0L) {
                this.offset = elapsed;
            }
            elapsed -= this.offset;
            final Consumer<LivingEntity> action = this.timeLine.getOrDefault((int)elapsed, this::defaultConsumer);
            action.accept(livingEntity);
        }
        
        void defaultConsumer(final LivingEntity entityIn) {
        }
        
        public static class TimeLineTickActionBuilder
        {
            Map<Integer, Consumer<LivingEntity>> timeLine;
            
            public TimeLineTickActionBuilder() {
                this.timeLine = (Map<Integer, Consumer<LivingEntity>>)Maps.newHashMap();
            }
            
            public TimeLineTickActionBuilder put(final int ticks, final Consumer<LivingEntity> action) {
                this.timeLine.put(ticks, action);
                return this;
            }
            
            public TimeLineTickAction build() {
                return new TimeLineTickAction(this.timeLine);
            }
        }
    }
}
