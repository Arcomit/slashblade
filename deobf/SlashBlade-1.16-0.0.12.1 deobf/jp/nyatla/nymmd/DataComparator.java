//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd;

import java.util.*;
import jp.nyatla.nymmd.core.*;

class DataComparator implements Comparator<PmdIK>
{
    @Override
    public int compare(final PmdIK o1, final PmdIK o2) {
        return o1.getSortVal() - o2.getSortVal();
    }
}
