//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.struct.pmd;

import jp.nyatla.nymmd.types.*;
import jp.nyatla.nymmd.struct.*;
import jp.nyatla.nymmd.*;

public class PMD_FACE_VTX implements StructType
{
    public int ulIndex;
    public MmdVector3 vec3Pos;
    
    public PMD_FACE_VTX() {
        this.vec3Pos = new MmdVector3();
    }
    
    @Override
    public void read(final DataReader i_reader) throws MmdException {
        this.ulIndex = i_reader.readInt();
        StructReader.read(this.vec3Pos, i_reader);
    }
    
    public static PMD_FACE_VTX[] createArray(final int i_length) {
        final PMD_FACE_VTX[] ret = new PMD_FACE_VTX[i_length];
        for (int i = 0; i < i_length; ++i) {
            ret[i] = new PMD_FACE_VTX();
        }
        return ret;
    }
    
    public void setValue(final PMD_FACE_VTX i_value) {
        this.ulIndex = i_value.ulIndex;
        this.vec3Pos.setValue(i_value.vec3Pos);
    }
}
