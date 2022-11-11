//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd;

public class MmdException extends Exception
{
    private static final long serialVersionUID = 1L;
    
    public MmdException() {
    }
    
    public MmdException(final Exception e) {
        super(e);
    }
    
    public MmdException(final String m) {
        super(m);
    }
    
    public static void trap(final String m) throws MmdException {
        throw new MmdException("\u30c8\u30e9\u30c3\u30d7:" + m);
    }
    
    public static void notImplement() throws MmdException {
        throw new MmdException("Not Implement!");
    }
}
