//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.types;

public class BoneKeyFrame
{
    public float fFrameNo;
    public final MmdVector3 vec3Position;
    public final MmdVector4 vec4Rotate;
    
    public BoneKeyFrame() {
        this.vec3Position = new MmdVector3();
        this.vec4Rotate = new MmdVector4();
    }
    
    public static BoneKeyFrame[] createArray(final int i_length) {
        final BoneKeyFrame[] ret = new BoneKeyFrame[i_length];
        for (int i = 0; i < i_length; ++i) {
            ret[i] = new BoneKeyFrame();
        }
        return ret;
    }
}
