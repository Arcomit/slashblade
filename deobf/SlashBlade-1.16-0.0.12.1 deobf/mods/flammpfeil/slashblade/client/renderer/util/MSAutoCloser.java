//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.client.renderer.util;

import com.mojang.blaze3d.matrix.*;

public class MSAutoCloser implements AutoCloseable
{
    MatrixStack ms;
    
    public static MSAutoCloser pushMatrix(final MatrixStack ms) {
        return new MSAutoCloser(ms);
    }
    
    MSAutoCloser(final MatrixStack ms) {
        (this.ms = ms).pushPose();
    }
    
    @Override
    public void close() {
        this.ms.popPose();
    }
}
