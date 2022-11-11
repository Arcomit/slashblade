//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.struct.pmd;

import jp.nyatla.nymmd.struct.*;
import jp.nyatla.nymmd.*;

public class PMD_FACE implements StructType
{
    public String szName;
    public int ulNumVertices;
    public int cbType;
    public PMD_FACE_VTX[] pVertices;
    
    public PMD_FACE() {
        this.pVertices = PMD_FACE_VTX.createArray(64);
    }
    
    @Override
    public void read(final DataReader i_reader) throws MmdException {
        this.szName = i_reader.readAscii(20);
        this.ulNumVertices = i_reader.readInt();
        this.cbType = i_reader.read();
        if (this.ulNumVertices > this.pVertices.length) {
            this.pVertices = PMD_FACE_VTX.createArray(this.ulNumVertices);
        }
        for (int i = 0; i < this.ulNumVertices; ++i) {
            this.pVertices[i].read(i_reader);
        }
    }
}
