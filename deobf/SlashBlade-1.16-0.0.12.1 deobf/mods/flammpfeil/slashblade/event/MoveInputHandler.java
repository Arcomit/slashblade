//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.event;

import mods.flammpfeil.slashblade.capability.inputstate.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.event.*;
import net.minecraft.client.entity.player.*;
import net.minecraft.client.*;
import net.minecraftforge.client.settings.*;
import net.minecraft.nbt.*;
import mods.flammpfeil.slashblade.item.*;
import mods.flammpfeil.slashblade.util.*;
import mods.flammpfeil.slashblade.network.*;
import net.minecraft.item.*;
import com.google.gson.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.eventbus.api.*;
import java.util.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;

public class MoveInputHandler
{
    @CapabilityInject(IInputState.class)
    public static Capability<IInputState> INPUT_STATE;
    
    public static boolean checkFlag(final int data, final int flags) {
        return (data & flags) == flags;
    }
    
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onPlayerPostTick(final TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (!(event.player instanceof ClientPlayerEntity)) {
            return;
        }
        final ClientPlayerEntity player = (ClientPlayerEntity)event.player;
        final EnumSet<InputCommand> commands = EnumSet.noneOf(InputCommand.class);
        if (player.input.up) {
            commands.add(InputCommand.FORWARD);
        }
        if (player.input.down) {
            commands.add(InputCommand.BACK);
        }
        if (player.input.left) {
            commands.add(InputCommand.LEFT);
        }
        if (player.input.right) {
            commands.add(InputCommand.RIGHT);
        }
        if (player.input.shiftKeyDown) {
            commands.add(InputCommand.SNEAK);
        }
        if (Minecraft.getInstance().options.keySprint.isDown()) {
            commands.add(InputCommand.SPRINT);
        }
        if (Minecraft.getInstance().options.keyUse.isDown()) {
            commands.add(InputCommand.R_DOWN);
        }
        if (Minecraft.getInstance().options.keyAttack.isDown()) {
            commands.add(InputCommand.L_DOWN);
        }
        if (Minecraft.getInstance().options.keyPickItem.isDown()) {
            commands.add(InputCommand.M_DOWN);
        }
        if (Minecraft.getInstance().options.keySaveHotbarActivator.isDown()) {
            commands.add(InputCommand.SAVE_TOOLBAR);
        }
        final EnumSet<InputCommand> old = player.getCapability((Capability)MoveInputHandler.INPUT_STATE).map(state -> state.getCommands()).orElseGet(() -> EnumSet.noneOf(InputCommand.class));
        final long currentTime = player.getCommandSenderWorld().getGameTime();
        final boolean doCopy = player.isCreative();
        if (doCopy && old.contains(InputCommand.SAVE_TOOLBAR) && !commands.contains(InputCommand.SAVE_TOOLBAR)) {
            final ItemStack stack = player.getMainHandItem();
            final JsonObject ret = new JsonObject();
            String str = "";
            if (KeyModifier.SHIFT.isActive((IKeyConflictContext)KeyConflictContext.UNIVERSAL)) {
                str = AdvancementBuilder.getAdvancementJsonStr(stack);
            }
            else {
                ret.addProperty("item", stack.getItem().getRegistryName().toString());
                if (stack.getCount() != 1) {
                    ret.addProperty("count", (Number)stack.getCount());
                }
                final CompoundNBT tag = new CompoundNBT();
                stack.save(tag);
                final CompoundNBT nbt = stack.getOrCreateTag().copy();
                if (tag.contains("ForgeCaps")) {
                    nbt.put("ForgeCaps", tag.get("ForgeCaps"));
                }
                if (KeyModifier.ALT.isActive((IKeyConflictContext)KeyConflictContext.UNIVERSAL)) {
                    final AnvilCraftingRecipe acr = new AnvilCraftingRecipe();
                    final ItemStack result = player.getOffhandItem();
                    acr.setResult(result);
                    nbt.put("RequiredBlade", acr.writeNBT());
                }
                if (KeyModifier.CONTROL.isActive((IKeyConflictContext)KeyConflictContext.UNIVERSAL)) {
                    final ItemStack result2 = player.getMainHandItem();
                    final CompoundNBT iconNbt = result2.save(new CompoundNBT());
                    ret.addProperty("iconStr_nbt", iconNbt.toString());
                    final JsonObject criteriaitem = new JsonObject();
                    criteriaitem.addProperty("item", result2.getItem().getRegistryName().toString());
                    final CompoundNBT checktarget = new CompoundNBT();
                    final NBTHelper.NBTCoupler nbtc = NBTHelper.getNBTCoupler(checktarget).getChild("ForgeCaps").getChild("slashblade:bladestate").getChild("State");
                    result2.getCapability((Capability)ItemSlashBlade.BLADESTATE).ifPresent(s -> {
                        if (s.isBroken()) {
                            nbtc.put("isBroken", s.isBroken());
                        }
                        if (s.getTranslationKey() != null || !s.getTranslationKey().isEmpty()) {
                            nbtc.put("translationKey", s.getTranslationKey());
                        }
                    });
                    criteriaitem.addProperty("nbt", checktarget.toString());
                    ret.add("CriteriaItem", (JsonElement)criteriaitem);
                }
                JsonElement element = null;
                element = new JsonParser().parse(JSONUtil.NBTtoJsonString(nbt));
                if (stack.getTag() != null && element != null) {
                    ret.add("nbt", element);
                }
                final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
                GSON.toJson((JsonElement)ret);
            }
            Minecraft.getInstance().keyboardHandler.setClipboard(str);
        }
        if (!old.equals(commands)) {
            player.getCapability((Capability)MoveInputHandler.INPUT_STATE).ifPresent(state -> {
                state.getCommands().clear();
                state.getCommands().addAll(commands);
            });
            final MoveCommandMessage msg = new MoveCommandMessage();
            msg.command = EnumSetConverter.convertToInt(commands);
            NetworkManager.INSTANCE.sendToServer((Object)msg);
        }
    }
    
    static {
        MoveInputHandler.INPUT_STATE = null;
    }
}
