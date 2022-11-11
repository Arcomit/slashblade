//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.network;

import net.minecraft.network.*;
import java.util.function.*;
import net.minecraftforge.fml.network.*;
import net.minecraft.util.*;
import mods.flammpfeil.slashblade.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import mods.flammpfeil.slashblade.capability.inputstate.*;
import mods.flammpfeil.slashblade.util.*;
import mods.flammpfeil.slashblade.event.*;
import java.util.*;

public class MoveCommandMessage
{
    public int command;
    
    public static MoveCommandMessage decode(final PacketBuffer buf) {
        final MoveCommandMessage msg = new MoveCommandMessage();
        msg.command = buf.readInt();
        return msg;
    }
    
    public static void encode(final MoveCommandMessage msg, final PacketBuffer buf) {
        buf.writeInt(msg.command);
    }
    
    public static void handle(final MoveCommandMessage msg, final Supplier<NetworkEvent.Context> ctx) {
        final ServerPlayerEntity sender;
        final ItemStack stack;
        ctx.get().enqueueWork(() -> {
            sender = ctx.get().getSender();
            stack = sender.getItemInHand(Hand.MAIN_HAND);
            if (stack.isEmpty()) {
                return;
            }
            else if (!(stack.getItem() instanceof ItemSlashBlade)) {
                return;
            }
            else {
                sender.getCapability(CapabilityInputState.INPUT_STATE).ifPresent(state -> {
                    final EnumSet<InputCommand> old = state.getCommands().clone();
                    state.getCommands().clear();
                    state.getCommands().addAll(EnumSetConverter.convertToEnumSet(InputCommand.class, InputCommand.values(), msg.command));
                    final EnumSet<InputCommand> current = state.getCommands().clone();
                    InputCommandEvent.onInputChange(sender, (EnumSet)old, (EnumSet)current);
                });
                return;
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
