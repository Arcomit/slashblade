//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.struct;

import java.io.*;
import java.nio.*;
import jp.nyatla.nymmd.*;

public class DataReader
{
    private ByteBuffer _buf;
    
    public DataReader(final InputStream i_stream) throws MmdException {
        try {
            int file_len = i_stream.available();
            if (file_len < 1) {
                file_len = 2097152;
            }
            final byte[] buf = new byte[file_len];
            final int buf_len = i_stream.read(buf, 0, file_len);
            (this._buf = ByteBuffer.wrap(buf, 0, buf_len)).order(ByteOrder.LITTLE_ENDIAN);
        }
        catch (Exception e) {
            throw new MmdException();
        }
    }
    
    public int readByte() {
        return this._buf.get();
    }
    
    public int read() {
        final int v = this._buf.get();
        return (v >= 0) ? v : (255 + v);
    }
    
    public short readShort() {
        return this._buf.getShort();
    }
    
    public int readUnsignedShort() {
        final int v = this._buf.getShort();
        return (v >= 0) ? v : (65535 + v);
    }
    
    public int readInt() {
        return this._buf.getInt();
    }
    
    public float readFloat() {
        return this._buf.getFloat();
    }
    
    public double readDouble() {
        return this._buf.getDouble();
    }
    
    public String readAscii(final int i_length) throws MmdException {
        try {
            String ret = "";
            int len = 0;
            final byte[] tmp = new byte[i_length];
            int i;
            for (i = 0; i < i_length; ++i) {
                final byte b = this._buf.get();
                if (b == 0) {
                    ++i;
                    break;
                }
                tmp[i] = b;
                ++len;
            }
            ret = new String(tmp, 0, len, "Shift_JIS");
            while (i < i_length) {
                this._buf.get();
                ++i;
            }
            return ret;
        }
        catch (Exception e) {
            throw new MmdException();
        }
    }
}
