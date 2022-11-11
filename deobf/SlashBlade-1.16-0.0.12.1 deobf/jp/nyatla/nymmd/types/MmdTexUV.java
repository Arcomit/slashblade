//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.types;

public class MmdTexUV
{
    public float u;
    public float v;
    
    public static MmdTexUV[] createArray(final int i_length) {
        final MmdTexUV[] ret = new MmdTexUV[i_length];
        for (int i = 0; i < i_length; ++i) {
            ret[i] = new MmdTexUV();
        }
        return ret;
    }
    
    public void setValue(final MmdTexUV v) {
        this.u = v.u;
        this.v = v.v;
    }
}
