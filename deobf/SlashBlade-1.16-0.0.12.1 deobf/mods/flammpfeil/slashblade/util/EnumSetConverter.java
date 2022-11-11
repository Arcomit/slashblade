//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.util;

import java.util.*;
import java.util.stream.*;

public class EnumSetConverter
{
    public static <T extends Enum<T>> int convertToInt(final EnumSet<T> enumSet) {
        final int value;
        return enumSet.stream().mapToInt(e -> {
            value = 1 << e.ordinal();
            return value;
        }).sum();
    }
    
    public static <T extends Enum<T>> EnumSet<T> convertToEnumSet(final Class<T> tclass, final T[] values, final int ivalues) {
        final EnumSet<T> set = EnumSet.noneOf(tclass);
        convertToEnumSet(set, values, ivalues);
        return set;
    }
    
    public static <T extends Enum<T>> void convertToEnumSet(final EnumSet<T> set, final T[] values, final int ivalues) {
        set.clear();
        IntStream.range(0, Math.min(values.length, 32)).forEach(i -> {
            if ((ivalues & 1 << i) != 0x0) {
                set.add(values[i]);
            }
        });
    }
}
