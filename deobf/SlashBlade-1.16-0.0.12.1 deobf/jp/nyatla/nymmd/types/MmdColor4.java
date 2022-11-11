//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.types;

public class MmdColor4
{
    public float r;
    public float g;
    public float b;
    public float a;
    
    public void setValue(final MmdColor4 v) {
        this.r = v.r;
        this.g = v.g;
        this.b = v.b;
        this.a = v.a;
    }
    
    public void getValue(final float[] v, final int i_st) {
        v[i_st + 0] = this.r;
        v[i_st + 1] = this.g;
        v[i_st + 2] = this.b;
        v[i_st + 3] = this.a;
    }
}
