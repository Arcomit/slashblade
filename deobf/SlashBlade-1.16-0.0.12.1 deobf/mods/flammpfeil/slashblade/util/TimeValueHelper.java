//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.util;

public class TimeValueHelper
{
    static final double TicksToMSec = 50.0;
    static final double FramesToMSec = 33.333333333333336;
    static final double TicksToFrames = 1.5;
    static final double MSecToFrames = 0.03;
    static final double MSecToTicks = 0.02;
    static final double FramesToTicks = 0.6666666666666666;
    
    public static double getMSecFromTicks(final float ticks) {
        return ticks * 50.0;
    }
    
    public static double getMSecFromFrames(final float frames) {
        return frames * 33.333333333333336;
    }
    
    public static double getFramesFromTicks(final float ticks) {
        return ticks * 1.5;
    }
    
    public static double getFramesFromMSec(final float msec) {
        return msec * 0.03;
    }
    
    public static double getTicksFromMSec(final float msec) {
        return msec * 0.02;
    }
    
    public static double getTicksFromFrames(final float frames) {
        return frames * 0.6666666666666666;
    }
}
