//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.struct.vmd;

import jp.nyatla.nymmd.struct.*;
import jp.nyatla.nymmd.*;

public class VMD_Face implements StructType
{
    public String szFaceName;
    public long ulFrameNo;
    public float fFactor;
    
    public void read(final DataReader i_reader) throws MmdException {
        this.szFaceName = i_reader.readAscii(15);
        this.ulFrameNo = i_reader.readInt();
        this.fFactor = i_reader.readFloat();
    }
}
