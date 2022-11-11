//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.struct.pmd;

import jp.nyatla.nymmd.struct.*;
import jp.nyatla.nymmd.*;

public class PMD_Header implements StructType
{
    public static final int SIZE_OF_STRUCT = 283;
    public String szMagic;
    public float fVersion;
    public String szName;
    public String szComment;
    
    @Override
    public void read(final DataReader i_reader) throws MmdException {
        this.szMagic = i_reader.readAscii(3);
        this.fVersion = i_reader.readFloat();
        this.szName = i_reader.readAscii(20);
        this.szComment = i_reader.readAscii(256);
    }
}
