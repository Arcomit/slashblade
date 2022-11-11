//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.struct.pmd;

import jp.nyatla.nymmd.struct.*;
import jp.nyatla.nymmd.*;

public class PMD_IK implements StructType
{
    public int nTargetNo;
    public int nEffNo;
    public int cbNumLink;
    public int unCount;
    public float fFact;
    public int[] punLinkNo;
    
    @Override
    public void read(final DataReader i_reader) throws MmdException {
        this.nTargetNo = i_reader.readShort();
        this.nEffNo = i_reader.readShort();
        this.cbNumLink = i_reader.read();
        this.unCount = i_reader.readUnsignedShort();
        this.fFact = i_reader.readFloat();
        this.punLinkNo = new int[this.cbNumLink];
        for (int i = 0; i < this.cbNumLink; ++i) {
            this.punLinkNo[i] = i_reader.readUnsignedShort();
        }
    }
}
