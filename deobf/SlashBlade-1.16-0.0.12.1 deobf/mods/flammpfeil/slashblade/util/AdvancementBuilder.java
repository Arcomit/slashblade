//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.util;

import net.minecraft.item.*;
import mods.flammpfeil.slashblade.item.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;
import net.minecraft.nbt.*;
import com.google.gson.*;

public class AdvancementBuilder
{
    public static String getAdvancementJsonStr(final ItemStack inItemStack) {
        final ItemStack iconItem = inItemStack.copy();
        final JsonObject ret = new JsonObject();
        final String recipeid = "[recipeid]";
        ret.addProperty("\u7f6e\u63db\u7528\u30d2\u30f3\u30c8ResourceLocation", "[recipeid]");
        iconItem.getCapability(ItemSlashBlade.BLADESTATE).ifPresent(state -> {
            final JsonObject display = new JsonObject();
            ret.add("display", (JsonElement)display);
            final JsonObject title = new JsonObject();
            display.add("title", (JsonElement)title);
            title.addProperty("translate", state.getTranslationKey());
            final JsonObject description = new JsonObject();
            display.add("description", (JsonElement)description);
            description.addProperty("translate", state.getTranslationKey() + ".desc");
            final JsonObject icon = new JsonObject();
            display.add("icon", (JsonElement)icon);
            icon.addProperty("item", iconItem.getItem().getRegistryName().toString());
            iconItem.getOrCreateTag().putString("Crafting", "[recipeid]");
            final CompoundNBT iconNbt = iconItem.save(new CompoundNBT());
            icon.addProperty("nbt", "{SlashBladeIcon:" + iconNbt.toString() + "}");
            display.addProperty("frame", "task");
            ret.addProperty("parent", "slashblade:blade/ex/");
            final JsonObject criteria = new JsonObject();
            ret.add("criteria", (JsonElement)criteria);
            final JsonObject crafting = new JsonObject();
            criteria.add("crafting", (JsonElement)crafting);
            crafting.addProperty("trigger", "inventory_changed");
            final JsonObject conditions = new JsonObject();
            crafting.add("conditions", (JsonElement)conditions);
            final JsonArray items = new JsonArray();
            conditions.add("items", (JsonElement)items);
            final JsonObject item = new JsonObject();
            item.addProperty("item", iconItem.getItem().getRegistryName().toString());
            item.addProperty("nbt", "{ShareTag:{translationKey:\"" + state.getTranslationKey() + "\",isBroken:\"false\"}}");
            items.add((JsonElement)item);
        });
        final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        return GSON.toJson((JsonElement)ret);
    }
}
