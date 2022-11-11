//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.struct.pmd;

import jp.nyatla.nymmd.types.*;
import jp.nyatla.nymmd.struct.*;
import jp.nyatla.nymmd.*;

public class PMD_Material implements StructType
{
    public final MmdColor4 col4Diffuse;
    public float fShininess;
    public final MmdColor3 col3Specular;
    public final MmdColor3 col3Ambient;
    public int unknown;
    public int ulNumIndices;
    public String szTextureFileName;
    
    public PMD_Material() {
        this.col4Diffuse = new MmdColor4();
        this.col3Specular = new MmdColor3();
        this.col3Ambient = new MmdColor3();
    }
    
    @Override
    public void read(final DataReader i_reader) throws MmdException {
        StructReader.read(this.col4Diffuse, i_reader);
        this.fShininess = i_reader.readFloat();
        StructReader.read(this.col3Specular, i_reader);
        StructReader.read(this.col3Ambient, i_reader);
        this.unknown = i_reader.readUnsignedShort();
        this.ulNumIndices = i_reader.readInt();
        this.szTextureFileName = i_reader.readAscii(20);
    }
}
