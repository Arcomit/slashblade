//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.util;

import net.minecraft.util.math.vector.*;
import net.minecraft.util.math.*;

public class VectorHelper
{
    public static Vector3d getVectorForRotation(final float pitch, final float yaw) {
        final float f = pitch * 0.017453292f;
        final float f2 = -yaw * 0.017453292f;
        final float f3 = MathHelper.cos(f2);
        final float f4 = MathHelper.sin(f2);
        final float f5 = MathHelper.cos(f);
        final float f6 = MathHelper.sin(f);
        return new Vector3d((double)(f4 * f5), (double)(-f6), (double)(f3 * f5));
    }
}
