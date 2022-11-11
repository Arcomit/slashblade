//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.types;

public class MmdVector3
{
    public float x;
    public float y;
    public float z;
    
    public MmdVector3() {
    }
    
    public MmdVector3(final float ix, final float iy, final float iz) {
        this.x = ix;
        this.y = iy;
        this.z = iz;
    }
    
    public static MmdVector3[] createArray(final int i_length) {
        final MmdVector3[] ret = new MmdVector3[i_length];
        for (int i = 0; i < i_length; ++i) {
            ret[i] = new MmdVector3();
        }
        return ret;
    }
    
    public void setValue(final MmdVector3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }
    
    public void Vector3Add(final MmdVector3 pvec3Add1, final MmdVector3 pvec3Add2) {
        this.x = pvec3Add1.x + pvec3Add2.x;
        this.y = pvec3Add1.y + pvec3Add2.y;
        this.z = pvec3Add1.z + pvec3Add2.z;
    }
    
    public void Vector3Sub(final MmdVector3 pvec3Sub1, final MmdVector3 pvec3Sub2) {
        this.x = pvec3Sub1.x - pvec3Sub2.x;
        this.y = pvec3Sub1.y - pvec3Sub2.y;
        this.z = pvec3Sub1.z - pvec3Sub2.z;
    }
    
    public void Vector3MulAdd(final MmdVector3 pvec3Add1, final MmdVector3 pvec3Add2, final float fRate) {
        this.x = pvec3Add1.x + pvec3Add2.x * fRate;
        this.y = pvec3Add1.y + pvec3Add2.y * fRate;
        this.z = pvec3Add1.z + pvec3Add2.z * fRate;
    }
    
    public void Vector3Normalize(final MmdVector3 pvec3Src) {
        final double fSqr = 1.0 / Math.sqrt(pvec3Src.x * pvec3Src.x + pvec3Src.y * pvec3Src.y + pvec3Src.z * pvec3Src.z);
        this.x = (float)(pvec3Src.x * fSqr);
        this.y = (float)(pvec3Src.y * fSqr);
        this.z = (float)(pvec3Src.z * fSqr);
    }
    
    public double Vector3DotProduct(final MmdVector3 pvec3Src2) {
        return this.x * pvec3Src2.x + this.y * pvec3Src2.y + this.z * pvec3Src2.z;
    }
    
    public void Vector3CrossProduct(final MmdVector3 pvec3Src1, final MmdVector3 pvec3Src2) {
        final float vx1 = pvec3Src1.x;
        final float vy1 = pvec3Src1.y;
        final float vz1 = pvec3Src1.z;
        final float vx2 = pvec3Src2.x;
        final float vy2 = pvec3Src2.y;
        final float vz2 = pvec3Src2.z;
        this.x = vy1 * vz2 - vz1 * vy2;
        this.y = vz1 * vx2 - vx1 * vz2;
        this.z = vx1 * vy2 - vy1 * vx2;
    }
    
    public void Vector3Lerp(final MmdVector3 pvec3Src1, final MmdVector3 pvec3Src2, final float fLerpValue) {
        final float t0 = 1.0f - fLerpValue;
        this.x = pvec3Src1.x * t0 + pvec3Src2.x * fLerpValue;
        this.y = pvec3Src1.y * t0 + pvec3Src2.y * fLerpValue;
        this.z = pvec3Src1.z * t0 + pvec3Src2.z * fLerpValue;
    }
    
    public void Vector3Transform(final MmdVector3 pVec3In, final MmdMatrix matTransform) {
        final double vx = pVec3In.x;
        final double vy = pVec3In.y;
        final double vz = pVec3In.z;
        this.x = (float)(vx * matTransform.m00 + vy * matTransform.m10 + vz * matTransform.m20 + matTransform.m30);
        this.y = (float)(vx * matTransform.m01 + vy * matTransform.m11 + vz * matTransform.m21 + matTransform.m31);
        this.z = (float)(vx * matTransform.m02 + vy * matTransform.m12 + vz * matTransform.m22 + matTransform.m32);
    }
    
    public void Vector3Transform(final MmdMatrix i_posmat, final MmdMatrix matTransform) {
        final double vx = i_posmat.m30;
        final double vy = i_posmat.m31;
        final double vz = i_posmat.m32;
        this.x = (float)(vx * matTransform.m00 + vy * matTransform.m10 + vz * matTransform.m20 + matTransform.m30);
        this.y = (float)(vx * matTransform.m01 + vy * matTransform.m11 + vz * matTransform.m21 + matTransform.m31);
        this.z = (float)(vx * matTransform.m02 + vy * matTransform.m12 + vz * matTransform.m22 + matTransform.m32);
    }
    
    public void Vector3Rotate(final MmdVector3 pVec3In, final MmdMatrix matRotate) {
        final double vx = pVec3In.x;
        final double vy = pVec3In.y;
        final double vz = pVec3In.z;
        this.x = (float)(vx * matRotate.m00 + vy * matRotate.m10 + vz * matRotate.m20);
        this.y = (float)(vx * matRotate.m01 + vy * matRotate.m11 + vz * matRotate.m21);
        this.z = (float)(vx * matRotate.m02 + vy * matRotate.m12 + vz * matRotate.m22);
    }
    
    public void QuaternionToEuler(final MmdVector4 pvec4Quat) {
        final double x2 = pvec4Quat.x + pvec4Quat.x;
        final double y2 = pvec4Quat.y + pvec4Quat.y;
        final double z2 = pvec4Quat.z + pvec4Quat.z;
        final double xz2 = pvec4Quat.x * z2;
        final double wy2 = pvec4Quat.w * y2;
        double temp = -(xz2 - wy2);
        if (temp >= 1.0) {
            temp = 1.0;
        }
        else if (temp <= -1.0) {
            temp = -1.0;
        }
        final double yRadian = Math.sin(temp);
        final double xx2 = pvec4Quat.x * x2;
        final double xy2 = pvec4Quat.x * y2;
        final double zz2 = pvec4Quat.z * z2;
        final double wz2 = pvec4Quat.w * z2;
        if (yRadian < 1.570796251296997) {
            if (yRadian > -1.570796251296997) {
                final double yz2 = pvec4Quat.y * z2;
                final double wx2 = pvec4Quat.w * x2;
                final double yy2 = pvec4Quat.y * y2;
                this.x = (float)Math.atan2(yz2 + wx2, 1.0 - (xx2 + yy2));
                this.y = (float)yRadian;
                this.z = (float)Math.atan2(xy2 + wz2, 1.0 - (yy2 + zz2));
            }
            else {
                this.x = (float)(-Math.atan2(xy2 - wz2, 1.0 - (xx2 + zz2)));
                this.y = (float)yRadian;
                this.z = 0.0f;
            }
        }
        else {
            this.x = (float)Math.atan2(xy2 - wz2, 1.0 - (xx2 + zz2));
            this.y = (float)yRadian;
            this.z = 0.0f;
        }
    }
}
