//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.item;

import mods.flammpfeil.slashblade.*;
import mods.flammpfeil.slashblade.entity.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.item.*;
import net.minecraft.nbt.*;

public class BladeStandItem extends HangingEntityItem
{
    private boolean isWallType;
    
    public BladeStandItem(final Item.Properties builder) {
        this(builder, false);
    }
    
    public BladeStandItem(final Item.Properties builder, final boolean isWallType) {
        super((EntityType)SlashBlade.RegistryEvents.BladeStand, builder);
        this.isWallType = isWallType;
    }
    
    public ActionResultType useOn(final ItemUseContext context) {
        final BlockPos blockpos = context.getClickedPos();
        final Direction direction = context.getClickedFace();
        final BlockPos blockpos2 = blockpos.relative(direction);
        final PlayerEntity playerentity = context.getPlayer();
        final ItemStack itemstack = context.getItemInHand();
        if (playerentity != null && !this.mayPlace(playerentity, direction, itemstack, blockpos2)) {
            return ActionResultType.FAIL;
        }
        final World world = context.getLevel();
        final HangingEntity hangingentity = (HangingEntity)BladeStandEntity.createInstanceFromPos(world, blockpos2, direction, (Item)this);
        final CompoundNBT compoundnbt = itemstack.getTag();
        if (compoundnbt != null) {
            EntityType.updateCustomEntityTag(world, playerentity, (Entity)hangingentity, compoundnbt);
        }
        if (hangingentity.survives()) {
            if (!world.isClientSide) {
                hangingentity.playPlacementSound();
                world.addFreshEntity((Entity)hangingentity);
            }
            itemstack.shrink(1);
            return ActionResultType.sidedSuccess(world.isClientSide);
        }
        return ActionResultType.CONSUME;
    }
    
    protected boolean mayPlace(final PlayerEntity player, final Direction dir, final ItemStack stack, final BlockPos pos) {
        if (this.isWallType) {
            return !dir.getAxis().isVertical() && !World.isOutsideBuildHeight(pos) && player.mayUseItemAt(pos, dir, stack);
        }
        return dir == Direction.UP && !World.isOutsideBuildHeight(pos) && player.mayUseItemAt(pos, dir, stack);
    }
}
