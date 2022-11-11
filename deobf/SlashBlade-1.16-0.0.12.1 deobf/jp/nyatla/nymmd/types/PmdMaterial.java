//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.types;

public class PmdMaterial
{
    public final MmdColor4 col4Diffuse;
    public final MmdColor4 col4Specular;
    public final MmdColor4 col4Ambient;
    public float fShininess;
    public String texture_name;
    public short[] indices;
    public int unknown;
    
    public PmdMaterial() {
        this.col4Diffuse = new MmdColor4();
        this.col4Specular = new MmdColor4();
        this.col4Ambient = new MmdColor4();
    }
}
