//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.client.renderer.model.obj;

public class TextureCoordinate
{
    public float u;
    public float v;
    public float w;
    
    public TextureCoordinate(final float u, final float v) {
        this(u, v, 0.0f);
    }
    
    public TextureCoordinate(final float u, final float v, final float w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }
}
