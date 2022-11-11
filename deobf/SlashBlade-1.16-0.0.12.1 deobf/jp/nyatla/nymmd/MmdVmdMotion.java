//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd;

import java.io.*;

public class MmdVmdMotion extends MmdVmdMotion_BasicClass
{
    public MmdVmdMotion(final String i_vmd_file_path) throws FileNotFoundException, MmdException {
        super(new FileInputStream(i_vmd_file_path));
    }
    
    public MmdVmdMotion(final InputStream i_stream) throws MmdException {
        super(i_stream);
    }
}
