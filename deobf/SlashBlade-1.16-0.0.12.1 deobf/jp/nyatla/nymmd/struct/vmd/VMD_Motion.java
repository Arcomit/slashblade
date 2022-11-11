//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.struct.vmd;

import jp.nyatla.nymmd.types.*;
import jp.nyatla.nymmd.struct.*;
import jp.nyatla.nymmd.*;

public class VMD_Motion implements StructType
{
    public String szBoneName;
    public long ulFrameNo;
    public final MmdVector3 vec3Position;
    public final MmdVector4 vec4Rotate;
    public final int[] cInterpolation1;
    public final int[] cInterpolation2;
    public final int[] cInterpolation3;
    public final int[] cInterpolation4;
    
    public VMD_Motion() {
        this.vec3Position = new MmdVector3();
        this.vec4Rotate = new MmdVector4();
        this.cInterpolation1 = new int[16];
        this.cInterpolation2 = new int[16];
        this.cInterpolation3 = new int[16];
        this.cInterpolation4 = new int[16];
    }
    
    public void read(final DataReader i_reader) throws MmdException {
        this.szBoneName = i_reader.readAscii(15);
        this.ulFrameNo = i_reader.readInt();
        StructReader.read(this.vec3Position, i_reader);
        StructReader.read(this.vec4Rotate, i_reader);
        for (int i = 0; i < 16; ++i) {
            this.cInterpolation1[i] = i_reader.readByte();
        }
        for (int i = 0; i < 16; ++i) {
            this.cInterpolation2[i] = i_reader.readByte();
        }
        for (int i = 0; i < 16; ++i) {
            this.cInterpolation3[i] = i_reader.readByte();
        }
        for (int i = 0; i < 16; ++i) {
            this.cInterpolation4[i] = i_reader.readByte();
        }
    }
}
