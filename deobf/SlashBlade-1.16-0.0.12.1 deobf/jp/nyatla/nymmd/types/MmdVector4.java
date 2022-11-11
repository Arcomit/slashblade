//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.types;

public class MmdVector4
{
    public double x;
    public double y;
    public double z;
    public double w;
    
    public void setValue(final MmdVector4 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }
    
    public void QuaternionSlerp(final MmdVector4 pvec4Src1, final MmdVector4 pvec4Src2, final double fLerpValue) {
        final double qr = pvec4Src1.x * pvec4Src2.x + pvec4Src1.y * pvec4Src2.y + pvec4Src1.z * pvec4Src2.z + pvec4Src1.w * pvec4Src2.w;
        final double t0 = 1.0 - fLerpValue;
        if (qr < 0.0) {
            this.x = pvec4Src1.x * t0 - pvec4Src2.x * fLerpValue;
            this.y = pvec4Src1.y * t0 - pvec4Src2.y * fLerpValue;
            this.z = pvec4Src1.z * t0 - pvec4Src2.z * fLerpValue;
            this.w = pvec4Src1.w * t0 - pvec4Src2.w * fLerpValue;
        }
        else {
            this.x = pvec4Src1.x * t0 + pvec4Src2.x * fLerpValue;
            this.y = pvec4Src1.y * t0 + pvec4Src2.y * fLerpValue;
            this.z = pvec4Src1.z * t0 + pvec4Src2.z * fLerpValue;
            this.w = pvec4Src1.w * t0 + pvec4Src2.w * fLerpValue;
        }
        this.QuaternionNormalize(this);
    }
    
    public void QuaternionNormalize(final MmdVector4 pvec4Src) {
        final double fSqr = 1.0 / Math.sqrt(pvec4Src.x * pvec4Src.x + pvec4Src.y * pvec4Src.y + pvec4Src.z * pvec4Src.z + pvec4Src.w * pvec4Src.w);
        this.x = pvec4Src.x * fSqr;
        this.y = pvec4Src.y * fSqr;
        this.z = pvec4Src.z * fSqr;
        this.w = pvec4Src.w * fSqr;
    }
    
    public void QuaternionCreateAxis(final MmdVector3 pvec3Axis, double fRotAngle) {
        if (Math.abs(fRotAngle) < 9.999999747378752E-5) {
            final double x = 0.0;
            this.z = x;
            this.y = x;
            this.x = x;
            this.w = 1.0;
        }
        else {
            fRotAngle *= 0.5;
            final double fTemp = Math.sin(fRotAngle);
            this.x = pvec3Axis.x * fTemp;
            this.y = pvec3Axis.y * fTemp;
            this.z = pvec3Axis.z * fTemp;
            this.w = Math.cos(fRotAngle);
        }
    }
    
    public void QuaternionMultiply(final MmdVector4 pvec4Src1, final MmdVector4 pvec4Src2) {
        final double px = pvec4Src1.x;
        final double py = pvec4Src1.y;
        final double pz = pvec4Src1.z;
        final double pw = pvec4Src1.w;
        final double qx = pvec4Src2.x;
        final double qy = pvec4Src2.y;
        final double qz = pvec4Src2.z;
        final double qw = pvec4Src2.w;
        this.x = pw * qx + px * qw + py * qz - pz * qy;
        this.y = pw * qy - px * qz + py * qw + pz * qx;
        this.z = pw * qz + px * qy - py * qx + pz * qw;
        this.w = pw * qw - px * qx - py * qy - pz * qz;
    }
    
    public void QuaternionCreateEuler(final MmdVector3 pvec3EulerAngle) {
        final double xRadian = pvec3EulerAngle.x * 0.5;
        final double yRadian = pvec3EulerAngle.y * 0.5;
        final double zRadian = pvec3EulerAngle.z * 0.5;
        final double sinX = Math.sin(xRadian);
        final double cosX = Math.cos(xRadian);
        final double sinY = Math.sin(yRadian);
        final double cosY = Math.cos(yRadian);
        final double sinZ = Math.sin(zRadian);
        final double cosZ = Math.cos(zRadian);
        this.x = sinX * cosY * cosZ - cosX * sinY * sinZ;
        this.y = cosX * sinY * cosZ + sinX * cosY * sinZ;
        this.z = cosX * cosY * sinZ - sinX * sinY * cosZ;
        this.w = cosX * cosY * cosZ + sinX * sinY * sinZ;
    }
}
