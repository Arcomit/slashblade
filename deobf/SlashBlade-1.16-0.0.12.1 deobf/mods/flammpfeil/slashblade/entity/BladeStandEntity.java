//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.entity;

import net.minecraftforge.fml.common.registry.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraftforge.registries.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import mods.flammpfeil.slashblade.*;
import net.minecraftforge.fml.network.*;
import net.minecraft.entity.item.*;
import net.minecraft.item.*;
import javax.annotation.*;
import net.minecraft.entity.player.*;
import mods.flammpfeil.slashblade.item.*;
import net.minecraft.util.*;

public class BladeStandEntity extends ItemFrameEntity implements IEntityAdditionalSpawnData
{
    public Item currentType;
    public ItemStack currentTypeStack;
    
    public BladeStandEntity(final EntityType<? extends BladeStandEntity> p_i50224_1_, final World p_i50224_2_) {
        super((EntityType)p_i50224_1_, p_i50224_2_);
        this.currentType = null;
        this.currentTypeStack = ItemStack.EMPTY;
    }
    
    public IPacket<?> getAddEntityPacket() {
        return (IPacket<?>)NetworkHooks.getEntitySpawningPacket((Entity)this);
    }
    
    public void addAdditionalSaveData(final CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        String standTypeStr;
        if (this.currentType != null) {
            standTypeStr = this.currentType.getRegistryName().toString();
        }
        else {
            standTypeStr = "";
        }
        compound.putString("StandType", standTypeStr);
        compound.putByte("Pose", (byte)this.getPose().ordinal());
    }
    
    public void readAdditionalSaveData(final CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.currentType = (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation(compound.getString("StandType")));
        this.setPose(Pose.values()[compound.getByte("Pose") % Pose.values().length]);
    }
    
    public void writeSpawnData(final PacketBuffer buffer) {
        final CompoundNBT tag = new CompoundNBT();
        this.addAdditionalSaveData(tag);
        buffer.writeNbt(tag);
    }
    
    public void readSpawnData(final PacketBuffer additionalData) {
        final CompoundNBT tag = additionalData.readNbt();
        this.readAdditionalSaveData(tag);
    }
    
    public static BladeStandEntity createInstanceFromPos(final World worldIn, final BlockPos placePos, final Direction dir, final Item type) {
        final BladeStandEntity e = new BladeStandEntity(SlashBlade.RegistryEvents.BladeStand, worldIn);
        e.pos = placePos;
        e.setDirection(dir);
        e.currentType = type;
        return e;
    }
    
    public static BladeStandEntity createInstance(final FMLPlayMessages.SpawnEntity spawnEntity, final World world) {
        return new BladeStandEntity(SlashBlade.RegistryEvents.BladeStand, world);
    }
    
    @Nullable
    public ItemEntity spawnAtLocation(IItemProvider iip) {
        if (iip == Items.ITEM_FRAME) {
            if (this.currentType == null || this.currentType == Items.AIR) {
                return null;
            }
            iip = (IItemProvider)this.currentType;
        }
        return super.spawnAtLocation(iip);
    }
    
    public ActionResultType interact(final PlayerEntity player, final Hand hand) {
        ActionResultType result = ActionResultType.PASS;
        if (!this.level.isClientSide) {
            final ItemStack itemstack = player.getItemInHand(hand);
            if (player.isShiftKeyDown() && !this.getItem().isEmpty()) {
                final Pose current = this.getPose();
                final int newIndex = (current.ordinal() + 1) % Pose.values().length;
                this.setPose(Pose.values()[newIndex]);
                result = ActionResultType.SUCCESS;
            }
            else if ((!itemstack.isEmpty() && itemstack.getItem() instanceof ItemSlashBlade) || (itemstack.isEmpty() && !this.getItem().isEmpty())) {
                if (this.getItem().isEmpty()) {
                    result = super.interact(player, hand);
                }
                else {
                    final ItemStack displayed = this.getItem().copy();
                    this.setItem(ItemStack.EMPTY);
                    result = super.interact(player, hand);
                    player.setItemInHand(hand, displayed);
                }
            }
            else {
                this.playSound(SoundEvents.ITEM_FRAME_ROTATE_ITEM, 1.0f, 1.0f);
                this.setRotation(this.getRotation() + 1);
                result = ActionResultType.SUCCESS;
            }
        }
        return result;
    }
}
