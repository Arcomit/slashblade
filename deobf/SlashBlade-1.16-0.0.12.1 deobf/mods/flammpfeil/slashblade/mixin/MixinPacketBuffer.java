//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.mixin;

import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import org.spongepowered.asm.mixin.injection.*;
import io.netty.buffer.*;
import org.spongepowered.asm.mixin.*;
import javax.annotation.*;
import net.minecraft.util.*;

@Mixin({ PacketBuffer.class })
public class MixinPacketBuffer
{
    @Inject(at = { @At("HEAD") }, method = { "writeItemStack(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/network/PacketBuffer;" }, cancellable = true, remap = false)
    public void writeItemStack(final ItemStack stack, final boolean limitedTag, final CallbackInfoReturnable<PacketBuffer> callback) {
        if (stack.isEmpty()) {
            this.writeBoolean(false);
        }
        else {
            this.writeBoolean(true);
            final Item item = stack.getItem();
            this.writeVarInt(Item.getId(item));
            this.writeByte(stack.getCount());
            CompoundNBT compoundnbt = null;
            if (item.isDamageable(stack) || item.shouldOverrideMultiplayerNbt()) {
                compoundnbt = (limitedTag ? stack.getShareTag() : stack.getTag());
            }
            this.writeNbt(compoundnbt);
            final CompoundNBT completeNbt = new CompoundNBT();
            stack.save(completeNbt);
            final CompoundNBT caps = completeNbt.contains("ForgeCaps") ? completeNbt.getCompound("ForgeCaps") : null;
            this.writeNbt(caps);
        }
        callback.setReturnValue((Object)PacketBuffer.class.cast(this));
        callback.cancel();
    }
    
    @Shadow
    public ByteBuf writeBoolean(final boolean b) {
        throw new IllegalStateException("Mixin failed to shadow getItem()");
    }
    
    @Shadow
    public PacketBuffer writeVarInt(final int i) {
        throw new IllegalStateException("Mixin failed to shadow getItem()");
    }
    
    @Shadow
    public ByteBuf writeByte(final int i) {
        throw new IllegalStateException("Mixin failed to shadow getItem()");
    }
    
    @Shadow
    public PacketBuffer writeNbt(@Nullable final CompoundNBT nbt) {
        throw new IllegalStateException("Mixin failed to shadow getItem()");
    }
    
    @Inject(at = { @At("HEAD") }, method = { "readItemStack()Lnet/minecraft/item/ItemStack;" }, cancellable = true)
    public void readItemStack(final CallbackInfoReturnable<ItemStack> callback) {
        ItemStack result;
        if (!this.readBoolean()) {
            result = ItemStack.EMPTY;
        }
        else {
            final int i = this.readVarInt();
            final int j = this.readByte();
            final CompoundNBT shareTag = this.readNbt();
            final CompoundNBT capsTag = this.readNbt();
            result = new ItemStack((IItemProvider)Item.byId(i), j, capsTag);
            result.readShareTag(shareTag);
        }
        callback.setReturnValue((Object)result);
        callback.cancel();
    }
    
    @Shadow
    public boolean readBoolean() {
        throw new IllegalStateException("Mixin failed to shadow getItem()");
    }
    
    @Shadow
    public int readVarInt() {
        throw new IllegalStateException("Mixin failed to shadow getItem()");
    }
    
    @Shadow
    public byte readByte() {
        throw new IllegalStateException("Mixin failed to shadow getItem()");
    }
    
    @Shadow
    public CompoundNBT readNbt() {
        throw new IllegalStateException("Mixin failed to shadow getItem()");
    }
}
