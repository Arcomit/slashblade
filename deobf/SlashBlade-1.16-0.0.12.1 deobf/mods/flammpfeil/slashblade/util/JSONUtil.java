//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.util;

import java.util.regex.*;
import net.minecraft.nbt.*;
import java.util.*;

public class JSONUtil
{
    private static final Pattern SIMPLE_VALUE;
    
    static String handleEscape(final String str) {
        return JSONUtil.SIMPLE_VALUE.matcher(str).matches() ? str : StringNBT.quoteAndEscape(str);
    }
    
    public static String NBTtoJsonString(final INBT childTag) {
        final int i = childTag.getId();
        String str;
        if (childTag instanceof CompoundNBT) {
            str = NBTtoJsonString((CompoundNBT)childTag);
        }
        else if (childTag instanceof ListNBT) {
            str = NBTtoJsonString((ListNBT)childTag);
        }
        else if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6) {
            str = childTag.toString();
            str = str.replaceAll("[fdbsL]", "");
        }
        else {
            str = childTag.toString();
        }
        return str;
    }
    
    public static String NBTtoJsonString(final ListNBT list) {
        final StringBuilder stringbuilder = new StringBuilder("[");
        for (int i = 0; i < list.size(); ++i) {
            if (i != 0) {
                stringbuilder.append(',');
            }
            stringbuilder.append(NBTtoJsonString(list.get(i)));
        }
        return stringbuilder.append(']').toString();
    }
    
    public static String NBTtoJsonString(final CompoundNBT tag) {
        final StringBuilder stringbuilder = new StringBuilder("{");
        final Collection<String> collection = (Collection<String>)tag.getAllKeys();
        for (final String s : collection) {
            if (stringbuilder.length() != 1) {
                stringbuilder.append(',');
            }
            stringbuilder.append(handleEscape(s)).append(':').append(NBTtoJsonString(tag.get(s)));
        }
        return stringbuilder.append('}').toString();
    }
    
    static {
        SIMPLE_VALUE = Pattern.compile("[A-Za-z0-9._+-]+");
    }
}
