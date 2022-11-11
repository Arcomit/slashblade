//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd;

import net.minecraft.util.*;
import net.minecraft.client.*;
import java.io.*;

public class MmdPmdModelMc extends MmdPmdModel_BasicClass
{
    public MmdPmdModelMc(final ResourceLocation loc) throws IOException, MmdException {
        super(Minecraft.getInstance().getResourceManager().getResource(loc).getInputStream(), new FileResourceProvider());
    }
    
    public MmdPmdModelMc(final InputStream i_stream, final IResourceProvider i_res_provider) throws MmdException {
        super(i_stream, i_res_provider);
    }
    
    protected static class FileResourceProvider implements IResourceProvider
    {
        @Override
        public ResourceLocation getTextureStream(final String i_name) throws MmdException {
            try {
                return new ResourceLocation(i_name);
            }
            catch (Exception e) {
                throw new MmdException(e);
            }
        }
    }
}
