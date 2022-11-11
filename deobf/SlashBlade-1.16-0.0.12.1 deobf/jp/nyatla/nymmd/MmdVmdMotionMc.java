//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd;

import net.minecraft.util.*;
import net.minecraft.client.*;
import java.io.*;

public class MmdVmdMotionMc extends MmdVmdMotion_BasicClass
{
    private static InputStream getStream(final ResourceLocation loc) throws IOException {
        return new BufferedInputStream(Minecraft.getInstance().getResourceManager().getResource(loc).getInputStream());
    }
    
    public MmdVmdMotionMc(final ResourceLocation loc) throws IOException, MmdException {
        super(getStream(loc));
    }
}
