//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.types;

public class FaceKeyFrame
{
    public float fFrameNo;
    public float fRate;
    
    public static FaceKeyFrame[] createArray(final int i_length) {
        final FaceKeyFrame[] ret = new FaceKeyFrame[i_length];
        for (int i = 0; i < i_length; ++i) {
            ret[i] = new FaceKeyFrame();
        }
        return ret;
    }
}
