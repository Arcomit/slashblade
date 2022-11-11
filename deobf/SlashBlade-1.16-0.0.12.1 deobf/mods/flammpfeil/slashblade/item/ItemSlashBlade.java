//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.item;

import net.minecraftforge.common.capabilities.*;
import mods.flammpfeil.slashblade.capability.inputstate.*;
import net.minecraft.inventory.*;
import com.google.common.collect.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraftforge.common.util.*;
import net.minecraft.entity.player.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;
import mods.flammpfeil.slashblade.init.*;
import net.minecraft.entity.item.*;
import mods.flammpfeil.slashblade.*;
import mods.flammpfeil.slashblade.entity.*;
import net.minecraft.entity.*;
import net.minecraft.world.server.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraft.client.world.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.server.*;
import net.minecraft.server.*;
import net.minecraftforge.fml.*;
import net.minecraft.util.*;
import java.util.stream.*;
import java.util.*;
import net.minecraft.tags.*;
import net.minecraft.client.util.*;
import net.minecraft.util.text.*;
import net.minecraft.item.crafting.*;
import java.util.function.*;
import net.minecraft.particles.*;
import net.minecraft.util.math.vector.*;
import net.minecraft.nbt.*;
import mods.flammpfeil.slashblade.util.*;
import net.minecraftforge.common.*;

public class ItemSlashBlade extends SwordItem
{
    protected static final UUID ATTACK_DAMAGE_AMPLIFIER;
    protected static final UUID PLAYER_REACH_AMPLIFIER;
    @CapabilityInject(ISlashBladeState.class)
    public static Capability<ISlashBladeState> BLADESTATE;
    @CapabilityInject(IInputState.class)
    public static Capability<IInputState> INPUT_STATE;
    public static final String BREAK_ACTION_TIMEOUT = "BreakActionTimeout";
    public static final String ICON_TAG_KEY = "SlashBladeIcon";
    RangeMap refineColor;
    
    public ItemSlashBlade(final IItemTier tier, final int attackDamageIn, final float attackSpeedIn, final Item.Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.refineColor = (RangeMap)ImmutableRangeMap.builder().put(Range.lessThan((Comparable)10), (Object)TextFormatting.WHITE).put(Range.closedOpen((Comparable)10, (Comparable)50), (Object)TextFormatting.YELLOW).put(Range.closedOpen((Comparable)50, (Comparable)100), (Object)TextFormatting.GREEN).put(Range.closedOpen((Comparable)100, (Comparable)150), (Object)TextFormatting.AQUA).put(Range.closedOpen((Comparable)150, (Comparable)200), (Object)TextFormatting.BLUE).put(Range.atLeast((Comparable)200), (Object)TextFormatting.LIGHT_PURPLE).build();
    }
    
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(final EquipmentSlotType slot, final ItemStack stack) {
        final Multimap<Attribute, AttributeModifier> def = (Multimap<Attribute, AttributeModifier>)super.getAttributeModifiers(slot, stack);
        final Multimap<Attribute, AttributeModifier> result = (Multimap<Attribute, AttributeModifier>)ArrayListMultimap.create();
        result.putAll((Object)Attributes.ATTACK_DAMAGE, (Iterable)def.get((Object)Attributes.ATTACK_DAMAGE));
        result.putAll((Object)Attributes.ATTACK_SPEED, (Iterable)def.get((Object)Attributes.ATTACK_SPEED));
        if (slot == EquipmentSlotType.MAINHAND) {
            final LazyOptional<ISlashBladeState> state = (LazyOptional<ISlashBladeState>)stack.getCapability((Capability)ItemSlashBlade.BLADESTATE);
            state.ifPresent(s -> {
                final float baseAttackModifier = s.getBaseAttackModifier();
                final AttributeModifier base = new AttributeModifier(ItemSlashBlade.BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)baseAttackModifier, AttributeModifier.Operation.ADDITION);
                result.remove((Object)Attributes.ATTACK_DAMAGE, (Object)base);
                result.put((Object)Attributes.ATTACK_DAMAGE, (Object)base);
                final float rankAttackAmplifier = s.getAttackAmplifier();
                result.put((Object)Attributes.ATTACK_DAMAGE, (Object)new AttributeModifier(ItemSlashBlade.ATTACK_DAMAGE_AMPLIFIER, "Weapon amplifier", (double)rankAttackAmplifier, AttributeModifier.Operation.ADDITION));
                result.put((Object)ForgeMod.REACH_DISTANCE.get(), (Object)new AttributeModifier(ItemSlashBlade.PLAYER_REACH_AMPLIFIER, "Reach amplifer", s.isBroken() ? 0.0 : 1.5, AttributeModifier.Operation.ADDITION));
            });
        }
        return result;
    }
    
    public Rarity getRarity(final ItemStack stack) {
        final LazyOptional<ISlashBladeState> state = (LazyOptional<ISlashBladeState>)stack.getCapability((Capability)ItemSlashBlade.BLADESTATE);
        return state.filter(s -> s.getRarity() != Rarity.COMMON).map(s -> s.getRarity()).orElseGet(() -> super.getRarity(stack));
    }
    
    public int getUseDuration(final ItemStack stack) {
        return 72000;
    }
    
    public ActionResult<ItemStack> use(final World worldIn, final PlayerEntity playerIn, final Hand handIn) {
        final ItemStack itemstack = playerIn.getItemInHand(handIn);
        final boolean result = itemstack.getCapability((Capability)ItemSlashBlade.BLADESTATE).map(state -> {
            playerIn.getCapability((Capability)ItemSlashBlade.INPUT_STATE).ifPresent(s -> s.getCommands().add(InputCommand.R_CLICK));
            final ComboState combo = state.progressCombo((LivingEntity)playerIn);
            if (combo != ComboState.NONE) {
                state.setLastActionTime(worldIn.getGameTime());
            }
            combo.clickAction((LivingEntity)playerIn);
            playerIn.getCapability((Capability)ItemSlashBlade.INPUT_STATE).ifPresent(s -> s.getCommands().remove(InputCommand.R_CLICK));
            if (combo != ComboState.NONE) {
                playerIn.swing(handIn);
            }
            return true;
        }).orElse(false);
        playerIn.startUsingItem(handIn);
        return (ActionResult<ItemStack>)new ActionResult(ActionResultType.SUCCESS, (Object)itemstack);
    }
    
    public boolean onLeftClickEntity(final ItemStack itemstack, final PlayerEntity playerIn, final Entity entity) {
        final World worldIn = playerIn.level;
        final Optional<ISlashBladeState> stateHolder = (Optional<ISlashBladeState>)itemstack.getCapability((Capability)ItemSlashBlade.BLADESTATE).filter(state -> !state.onClick());
        final ComboState combo;
        final World world;
        stateHolder.ifPresent(state -> {
            playerIn.getCapability((Capability)ItemSlashBlade.INPUT_STATE).ifPresent(s -> s.getCommands().add(InputCommand.L_CLICK));
            combo = state.progressCombo((LivingEntity)playerIn);
            state.setLastActionTime(world.getGameTime());
            combo.clickAction((LivingEntity)playerIn);
            playerIn.getCapability((Capability)ItemSlashBlade.INPUT_STATE).ifPresent(s -> s.getCommands().remove(InputCommand.L_CLICK));
            return;
        });
        return stateHolder.isPresent();
    }
    
    private Consumer<LivingEntity> getOnBroken(final ItemStack stack) {
        final ItemStack soul;
        final CompoundNBT blade;
        final ItemEntity itementity;
        final BladeItemEntity e;
        return user -> {
            user.broadcastBreakEvent(user.getUsedItemHand());
            soul = new ItemStack((IItemProvider)SBItems.proudsoul);
            blade = stack.save(new CompoundNBT());
            soul.addTagElement("BladeData", (INBT)blade);
            stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(s -> {
                s.getTexture().ifPresent(r -> soul.addTagElement("Texture", (INBT)StringNBT.valueOf(r.toString())));
                s.getModel().ifPresent(r -> soul.addTagElement("Model", (INBT)StringNBT.valueOf(r.toString())));
            });
            itementity = new ItemEntity(user.level, user.getX(), user.getY(), user.getZ(), soul);
            e = new BladeItemEntity(SlashBlade.RegistryEvents.BladeItem, user.level) {
                static final String isReleased = "isReleased";
                
                public boolean causeFallDamage(final float distance, final float damageMultiplier) {
                    final CompoundNBT tag = this.getPersistentData();
                    if (!tag.getBoolean("isReleased")) {
                        this.getPersistentData().putBoolean("isReleased", true);
                        if (this.level instanceof ServerWorld) {
                            final Entity thrower = ((ServerWorld)this.level).getEntity(this.getThrower());
                            if (thrower != null) {
                                thrower.getPersistentData().remove("BreakActionTimeout");
                            }
                        }
                    }
                    return super.causeFallDamage(distance, damageMultiplier);
                }
            };
            e.restoreFrom((Entity)itementity);
            e.init();
            e.push(0.0, 0.4, 0.0);
            e.setPickUpDelay(40);
            e.setGlowing(true);
            e.setAirSupply(-1);
            e.setThrower(user.getUUID());
            user.level.addFreshEntity((Entity)e);
            user.getPersistentData().putLong("BreakActionTimeout", user.level.getGameTime() + 100L);
            stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(state -> {
                if (0 < state.getRefine()) {
                    state.setRefine(state.getRefine() - 1);
                    state.doBrokenAction(user);
                }
            });
        };
    }
    
    public boolean hurtEnemy(final ItemStack stackF, final LivingEntity target, final LivingEntity attacker) {
        final ItemStack stack = attacker.getMainHandItem();
        stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(state -> {
            state.resolvCurrentComboState(attacker).hitEffect(target, attacker);
            state.damageBlade(stack, 1, attacker, (Consumer)this.getOnBroken(stack));
        });
        return true;
    }
    
    public boolean mineBlock(final ItemStack stack, final World worldIn, final BlockState state, final BlockPos pos, final LivingEntity entityLiving) {
        if (state.getDestroySpeed((IBlockReader)worldIn, pos) != 0.0f) {
            stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(s -> s.damageBlade(stack, 1, entityLiving, (Consumer)this.getOnBroken(stack)));
        }
        return true;
    }
    
    public void releaseUsing(final ItemStack stack, final World worldIn, final LivingEntity entityLiving, final int timeLeft) {
        final int elapsed = this.getUseDuration(stack) - timeLeft;
        if (!worldIn.isClientSide) {
            stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(state -> {
                final ComboState sa = state.doChargeAction(entityLiving, elapsed);
                if (sa != ComboState.NONE) {
                    state.damageBlade(stack, 1, entityLiving, (Consumer)this.getOnBroken(stack));
                    entityLiving.swing(Hand.MAIN_HAND);
                }
            });
        }
    }
    
    public void onUsingTick(final ItemStack stack, final LivingEntity player, final int count) {
        stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(state -> {
            state.getComboSeq().holdAction(player);
            if (!player.level.isClientSide) {
                final int ticks = player.getTicksUsingItem();
                if (0 < ticks && ticks == 20) {
                    final Vector3d pos = player.getEyePosition(1.0f).add(player.getLookAngle());
                    ((ServerWorld)player.level).sendParticles((IParticleData)ParticleTypes.PORTAL, pos.x, pos.y, pos.z, 7, 0.7, 0.7, 0.7, 0.02);
                }
            }
        });
    }
    
    public void inventoryTick(final ItemStack stack, final World worldIn, final Entity entityIn, final int itemSlot, final boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if (!isSelected) {
            return;
        }
        if (stack == null) {
            return;
        }
        if (entityIn == null) {
            return;
        }
        stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(state -> {
            if (entityIn instanceof LivingEntity) {
                state.resolvCurrentComboState((LivingEntity)entityIn).tickAction((LivingEntity)entityIn);
                state.sendChanges(entityIn);
            }
        });
    }
    
    @Nullable
    public CompoundNBT getShareTag(final ItemStack stack) {
        final CompoundNBT tag;
        final CompoundNBT tag2;
        return stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).filter(s -> s.getShareTag() != null).map(s -> {
            tag = s.getShareTag();
            tag.putString("translationKey", s.getTranslationKey());
            if (tag.getBoolean("isBroken") != s.isBroken()) {
                tag.putString("isBroken", Boolean.toString(s.isBroken()));
            }
            stack.addTagElement("ShareTag", (INBT)tag);
            return stack.getTag();
        }).orElseGet(() -> {
            tag2 = stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).map(s -> NBTHelper.getNBTCoupler(stack.getOrCreateTag()).getChild("ShareTag").put("translationKey", s.getTranslationKey()).put("isBroken", Boolean.toString(s.isBroken())).put("isNoScabbard", Boolean.toString(s.isNoScabbard())).getRawCompound()).orElseGet(() -> new CompoundNBT());
            stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(s -> s.setShareTag(tag2));
            return stack.getTag();
        });
    }
    
    public void readShareTag(final ItemStack stack, @Nullable final CompoundNBT nbt) {
        super.readShareTag(stack, nbt);
        if (nbt == null) {
            return;
        }
        if (nbt.contains("SlashBladeIcon")) {
            stack.deserializeNBT(nbt.getCompound("SlashBladeIcon"));
        }
    }
    
    int getHalfMaxdamage() {
        return this.getMaxDamage() / 2;
    }
    
    public int getDamage(final ItemStack stack) {
        return this.getHalfMaxdamage();
    }
    
    public void setDamage(final ItemStack stack, final int damage) {
        if (damage == this.getHalfMaxdamage()) {
            return;
        }
        if (damage > stack.getMaxDamage()) {
            stack.setCount(2);
        }
        stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(s -> {
            final float amount = (damage - this.getHalfMaxdamage()) / (float)this.getMaxDamage();
            s.setDamage(s.getDamage() + amount);
        });
    }
    
    public boolean isDamaged(final ItemStack stack) {
        return stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).map(s -> 0.0f < s.getDamage()).orElse(false);
    }
    
    public <T extends LivingEntity> int damageItem(final ItemStack stack, final int amount, final T entity, final Consumer<T> onBroken) {
        return Math.min(amount, this.getHalfMaxdamage() / 2);
    }
    
    public boolean showDurabilityBar(final ItemStack stack) {
        return Minecraft.getInstance().player.getMainHandItem() == stack;
    }
    
    public double getDurabilityForDisplay(final ItemStack stack) {
        return stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).map(s -> s.getDamage()).orElse(0.0f);
    }
    
    public int getRGBDurabilityForDisplay(final ItemStack stack) {
        final boolean isBroken = stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).filter(s -> s.isBroken()).isPresent();
        return isBroken ? 16737966 : 188654;
    }
    
    public String getDescriptionId(final ItemStack stack) {
        return stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).filter(s -> !s.getTranslationKey().isEmpty()).map(state -> state.getTranslationKey()).orElseGet(() -> super.getDescriptionId(stack));
    }
    
    protected boolean allowdedIn(final ItemGroup group) {
        return group == SlashBlade.SLASHBLADE || super.allowdedIn(group);
    }
    
    @OnlyIn(Dist.CLIENT)
    public static RecipeManager getClientRM() {
        final ClientWorld cw = Minecraft.getInstance().level;
        if (cw != null) {
            return cw.getRecipeManager();
        }
        return null;
    }
    
    public static RecipeManager getServerRM() {
        final MinecraftServer sw = ServerLifecycleHooks.getCurrentServer();
        if (sw != null) {
            return sw.getRecipeManager();
        }
        return null;
    }
    
    public void fillItemCategory(final ItemGroup group, final NonNullList<ItemStack> items) {
        super.fillItemCategory(group, (NonNullList)items);
        if (group == SlashBlade.SLASHBLADE) {
            final RecipeManager rm = (RecipeManager)DistExecutor.runForDist(() -> ItemSlashBlade::getClientRM, () -> ItemSlashBlade::getServerRM);
            if (rm == null) {
                return;
            }
            final Set<ResourceLocation> keys = rm.getRecipeIds().filter(loc -> loc.getNamespace().equals("slashblade") && !loc.getPath().startsWith("material") && !loc.getPath().startsWith("bladestand") && !loc.getPath().startsWith("simple_slashblade")).collect((Collector<? super Object, ?, Set<ResourceLocation>>)Collectors.toSet());
            final ItemStack stack;
            final List<ItemStack> allItems = keys.stream().map(key -> rm.byKey(key).map(r -> {
                stack = r.getResultItem().copy();
                stack.readShareTag(stack.getShareTag());
                return stack;
            }).orElseGet(() -> ItemStack.EMPTY)).sorted(Comparator.comparing(s -> s.getDescriptionId()).reversed()).collect((Collector<? super Object, ?, List<ItemStack>>)Collectors.toList());
            items.addAll((Collection)allItems);
        }
    }
    
    public boolean isValidRepairItem(final ItemStack toRepair, final ItemStack repair) {
        return ItemTags.STONE_TOOL_MATERIALS.contains((Object)repair.getItem()) || super.isValidRepairItem(toRepair, repair);
    }
    
    public void appendHoverText(final ItemStack stack, @Nullable final World worldIn, final List<ITextComponent> tooltip, final ITooltipFlag flagIn) {
        stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(s -> {
            if (0 < s.getKillCount()) {
                tooltip.add(new TranslationTextComponent("slashblade.tooltip.killcount", new Object[] { s.getKillCount() }));
            }
            if (0 < s.getRefine()) {
                tooltip.add(new TranslationTextComponent("slashblade.tooltip.refine", new Object[] { s.getRefine() }).withStyle((TextFormatting)this.refineColor.get((Comparable)s.getRefine())));
            }
        });
        super.appendHoverText(stack, worldIn, (List)tooltip, flagIn);
    }
    
    public boolean onEntitySwing(final ItemStack stack, final LivingEntity entity) {
        return !stack.getCapability((Capability)ItemSlashBlade.BLADESTATE).filter(s -> s.getLastActionTime() == entity.level.getGameTime()).isPresent();
    }
    
    public boolean hasCustomEntity(final ItemStack stack) {
        return true;
    }
    
    @Nullable
    public Entity createEntity(final World world, final Entity location, final ItemStack itemstack) {
        final BladeItemEntity e = new BladeItemEntity((EntityType)SlashBlade.RegistryEvents.BladeItem, world);
        e.restoreFrom(location);
        e.init();
        return (Entity)e;
    }
    
    public int getEntityLifespan(final ItemStack itemStack, final World world) {
        return super.getEntityLifespan(itemStack, world);
    }
    
    static {
        ATTACK_DAMAGE_AMPLIFIER = UUID.fromString("2D988C13-595B-4E58-B254-39BB6FA077FD");
        PLAYER_REACH_AMPLIFIER = UUID.fromString("2D988C13-595B-4E58-B254-39BB6FA077FD");
        ItemSlashBlade.BLADESTATE = null;
        ItemSlashBlade.INPUT_STATE = null;
    }
}
