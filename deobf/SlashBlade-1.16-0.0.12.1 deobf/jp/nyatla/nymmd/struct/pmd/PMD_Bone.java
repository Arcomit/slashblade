//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.struct.pmd;

import jp.nyatla.nymmd.types.*;
import jp.nyatla.nymmd.struct.*;
import jp.nyatla.nymmd.*;

public class PMD_Bone implements StructType
{
    public String szName;
    public int nParentNo;
    public int nChildNo;
    public int cbKind;
    public int unIKTarget;
    public final MmdVector3 vec3Position;
    
    public PMD_Bone() {
        this.vec3Position = new MmdVector3();
    }
    
    @Override
    public void read(final DataReader i_reader) throws MmdException {
        this.szName = i_reader.readAscii(20);
        this.nParentNo = i_reader.readShort();
        this.nChildNo = i_reader.readShort();
        this.cbKind = i_reader.readByte();
        this.unIKTarget = i_reader.readShort();
        StructReader.read(this.vec3Position, i_reader);
    }
}
