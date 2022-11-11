//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.struct;

import jp.nyatla.nymmd.*;
import jp.nyatla.nymmd.types.*;

public class StructReader
{
    public static void read(final MmdColor4 i_dest, final DataReader i_reader) throws MmdException {
        i_dest.r = i_reader.readFloat();
        i_dest.g = i_reader.readFloat();
        i_dest.b = i_reader.readFloat();
        i_dest.a = i_reader.readFloat();
    }
    
    public static void read(final MmdColor3 i_dest, final DataReader i_reader) throws MmdException {
        i_dest.r = i_reader.readFloat();
        i_dest.g = i_reader.readFloat();
        i_dest.b = i_reader.readFloat();
    }
    
    public static void read(final MmdTexUV i_dest, final DataReader i_reader) throws MmdException {
        i_dest.u = i_reader.readFloat();
        i_dest.v = i_reader.readFloat();
    }
    
    public static void read(final MmdVector3 i_dest, final DataReader i_reader) throws MmdException {
        i_dest.x = i_reader.readFloat();
        i_dest.y = i_reader.readFloat();
        i_dest.z = i_reader.readFloat();
    }
    
    public static void read(final MmdVector4 i_dest, final DataReader i_reader) throws MmdException {
        i_dest.x = i_reader.readFloat();
        i_dest.y = i_reader.readFloat();
        i_dest.z = i_reader.readFloat();
        i_dest.w = i_reader.readFloat();
    }
}
