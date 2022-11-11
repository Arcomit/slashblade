//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.struct.pmd;

import jp.nyatla.nymmd.types.*;
import jp.nyatla.nymmd.struct.*;
import jp.nyatla.nymmd.*;

public class PMD_Vertex implements StructType
{
    public MmdVector3 vec3Pos;
    public MmdVector3 vec3Normal;
    public MmdTexUV uvTex;
    public int[] unBoneNo;
    public int cbWeight;
    public int cbEdge;
    
    public PMD_Vertex() {
        this.vec3Pos = new MmdVector3();
        this.vec3Normal = new MmdVector3();
        this.uvTex = new MmdTexUV();
        this.unBoneNo = new int[2];
    }
    
    @Override
    public void read(final DataReader i_reader) throws MmdException {
        StructReader.read(this.vec3Pos, i_reader);
        StructReader.read(this.vec3Normal, i_reader);
        StructReader.read(this.uvTex, i_reader);
        this.unBoneNo[0] = i_reader.readUnsignedShort();
        this.unBoneNo[1] = i_reader.readUnsignedShort();
        this.cbWeight = i_reader.read();
        this.cbEdge = i_reader.read();
    }
}
