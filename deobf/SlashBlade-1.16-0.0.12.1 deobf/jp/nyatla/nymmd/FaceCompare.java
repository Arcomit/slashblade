//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd;

import java.util.*;
import jp.nyatla.nymmd.types.*;

class FaceCompare implements Comparator<FaceKeyFrame>
{
    @Override
    public int compare(final FaceKeyFrame o1, final FaceKeyFrame o2) {
        return (int)(o1.fFrameNo - o2.fFrameNo);
    }
}
