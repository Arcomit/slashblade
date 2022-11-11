//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.types;

import java.nio.*;
import net.minecraft.client.renderer.*;

public class MmdMatrix
{
    public double m00;
    public double m01;
    public double m02;
    public double m03;
    public double m10;
    public double m11;
    public double m12;
    public double m13;
    public double m20;
    public double m21;
    public double m22;
    public double m23;
    public double m30;
    public double m31;
    public double m32;
    public double m33;
    private static final DoubleBuffer matrixBuffer;
    
    public static MmdMatrix[] createArray(final int i_number) {
        final MmdMatrix[] ret = new MmdMatrix[i_number];
        for (int i = 0; i < i_number; ++i) {
            ret[i] = new MmdMatrix();
        }
        return ret;
    }
    
    public void setValue(final double[] i_value) {
        this.m00 = i_value[0];
        this.m01 = i_value[1];
        this.m02 = i_value[2];
        this.m03 = i_value[3];
        this.m10 = i_value[4];
        this.m11 = i_value[5];
        this.m12 = i_value[6];
        this.m13 = i_value[7];
        this.m20 = i_value[8];
        this.m21 = i_value[9];
        this.m22 = i_value[10];
        this.m23 = i_value[11];
        this.m30 = i_value[12];
        this.m31 = i_value[13];
        this.m32 = i_value[14];
        this.m33 = i_value[15];
    }
    
    public void setValue(final MmdMatrix i_value) {
        this.m00 = i_value.m00;
        this.m01 = i_value.m01;
        this.m02 = i_value.m02;
        this.m03 = i_value.m03;
        this.m10 = i_value.m10;
        this.m11 = i_value.m11;
        this.m12 = i_value.m12;
        this.m13 = i_value.m13;
        this.m20 = i_value.m20;
        this.m21 = i_value.m21;
        this.m22 = i_value.m22;
        this.m23 = i_value.m23;
        this.m30 = i_value.m30;
        this.m31 = i_value.m31;
        this.m32 = i_value.m32;
        this.m33 = i_value.m33;
    }
    
    public void getValue(final double[] o_value) {
        o_value[0] = this.m00;
        o_value[1] = this.m01;
        o_value[2] = this.m02;
        o_value[3] = this.m03;
        o_value[4] = this.m10;
        o_value[5] = this.m11;
        o_value[6] = this.m12;
        o_value[7] = this.m13;
        o_value[8] = this.m20;
        o_value[9] = this.m21;
        o_value[10] = this.m22;
        o_value[11] = this.m23;
        o_value[12] = this.m30;
        o_value[13] = this.m31;
        o_value[14] = this.m32;
        o_value[15] = this.m33;
    }
    
    public void getValue(final float[] o_value) {
        o_value[0] = (float)this.m00;
        o_value[1] = (float)this.m01;
        o_value[2] = (float)this.m02;
        o_value[3] = (float)this.m03;
        o_value[4] = (float)this.m10;
        o_value[5] = (float)this.m11;
        o_value[6] = (float)this.m12;
        o_value[7] = (float)this.m13;
        o_value[8] = (float)this.m20;
        o_value[9] = (float)this.m21;
        o_value[10] = (float)this.m22;
        o_value[11] = (float)this.m23;
        o_value[12] = (float)this.m30;
        o_value[13] = (float)this.m31;
        o_value[14] = (float)this.m32;
        o_value[15] = (float)this.m33;
    }
    
    public void getValueT(final double[] o_value) {
        o_value[0] = this.m00;
        o_value[1] = this.m10;
        o_value[2] = this.m20;
        o_value[3] = this.m30;
        o_value[4] = this.m01;
        o_value[5] = this.m11;
        o_value[6] = this.m21;
        o_value[7] = this.m31;
        o_value[8] = this.m02;
        o_value[9] = this.m12;
        o_value[10] = this.m22;
        o_value[11] = this.m32;
        o_value[12] = this.m03;
        o_value[13] = this.m13;
        o_value[14] = this.m23;
        o_value[15] = this.m33;
    }
    
    public boolean inverse(final MmdMatrix i_src) {
        final double a11 = i_src.m00;
        final double a12 = i_src.m01;
        final double a13 = i_src.m02;
        final double a14 = i_src.m03;
        final double a15 = i_src.m10;
        final double a16 = i_src.m11;
        final double a17 = i_src.m12;
        final double a18 = i_src.m13;
        final double a19 = i_src.m20;
        final double a20 = i_src.m21;
        final double a21 = i_src.m22;
        final double a22 = i_src.m23;
        final double a23 = i_src.m30;
        final double a24 = i_src.m31;
        final double a25 = i_src.m32;
        final double a26 = i_src.m33;
        double t1 = a21 * a26 - a22 * a25;
        double t2 = a22 * a24 - a20 * a26;
        double t3 = a20 * a25 - a21 * a24;
        double t4 = a22 * a23 - a19 * a26;
        double t5 = a19 * a25 - a21 * a23;
        double t6 = a19 * a24 - a20 * a23;
        final double b11 = a16 * t1 + a17 * t2 + a18 * t3;
        final double b12 = -(a17 * t4 + a18 * t5 + a15 * t1);
        final double b13 = a18 * t6 - a15 * t2 + a16 * t4;
        final double b14 = -(a15 * t3 - a16 * t5 + a17 * t6);
        t1 = a25 * a14 - a26 * a13;
        t2 = a26 * a12 - a24 * a14;
        t3 = a24 * a13 - a25 * a12;
        t4 = a26 * a11 - a23 * a14;
        t5 = a23 * a13 - a25 * a11;
        t6 = a23 * a12 - a24 * a11;
        final double b15 = -(a20 * t1 + a21 * t2 + a22 * t3);
        final double b16 = a21 * t4 + a22 * t5 + a19 * t1;
        final double b17 = -(a22 * t6 - a19 * t2 + a20 * t4);
        final double b18 = a19 * t3 - a20 * t5 + a21 * t6;
        t1 = a13 * a18 - a14 * a17;
        t2 = a14 * a16 - a12 * a18;
        t3 = a12 * a17 - a13 * a16;
        t4 = a14 * a15 - a11 * a18;
        t5 = a11 * a17 - a13 * a15;
        t6 = a11 * a16 - a12 * a15;
        final double b19 = a24 * t1 + a25 * t2 + a26 * t3;
        final double b20 = -(a25 * t4 + a26 * t5 + a23 * t1);
        final double b21 = a26 * t6 - a23 * t2 + a24 * t4;
        final double b22 = -(a23 * t3 - a24 * t5 + a25 * t6);
        t1 = a17 * a22 - a18 * a21;
        t2 = a18 * a20 - a16 * a22;
        t3 = a16 * a21 - a17 * a20;
        t4 = a18 * a19 - a15 * a22;
        t5 = a15 * a21 - a17 * a19;
        t6 = a15 * a20 - a16 * a19;
        final double b23 = -(a12 * t1 + a13 * t2 + a14 * t3);
        final double b24 = a13 * t4 + a14 * t5 + a11 * t1;
        final double b25 = -(a14 * t6 - a11 * t2 + a12 * t4);
        final double b26 = a11 * t3 - a12 * t5 + a13 * t6;
        double det_1 = a11 * b11 + a15 * b15 + a19 * b19 + a23 * b23;
        if (det_1 == 0.0) {
            return false;
        }
        det_1 = 1.0 / det_1;
        this.m00 = b11 * det_1;
        this.m01 = b15 * det_1;
        this.m02 = b19 * det_1;
        this.m03 = b23 * det_1;
        this.m10 = b12 * det_1;
        this.m11 = b16 * det_1;
        this.m12 = b20 * det_1;
        this.m13 = b24 * det_1;
        this.m20 = b13 * det_1;
        this.m21 = b17 * det_1;
        this.m22 = b21 * det_1;
        this.m23 = b25 * det_1;
        this.m30 = b14 * det_1;
        this.m31 = b18 * det_1;
        this.m32 = b22 * det_1;
        this.m33 = b26 * det_1;
        return true;
    }
    
    public final void mul(final MmdMatrix i_mat_l, final MmdMatrix i_mat_r) {
        assert this != i_mat_l;
        assert this != i_mat_r;
        this.m00 = i_mat_l.m00 * i_mat_r.m00 + i_mat_l.m01 * i_mat_r.m10 + i_mat_l.m02 * i_mat_r.m20 + i_mat_l.m03 * i_mat_r.m30;
        this.m01 = i_mat_l.m00 * i_mat_r.m01 + i_mat_l.m01 * i_mat_r.m11 + i_mat_l.m02 * i_mat_r.m21 + i_mat_l.m03 * i_mat_r.m31;
        this.m02 = i_mat_l.m00 * i_mat_r.m02 + i_mat_l.m01 * i_mat_r.m12 + i_mat_l.m02 * i_mat_r.m22 + i_mat_l.m03 * i_mat_r.m32;
        this.m03 = i_mat_l.m00 * i_mat_r.m03 + i_mat_l.m01 * i_mat_r.m13 + i_mat_l.m02 * i_mat_r.m23 + i_mat_l.m03 * i_mat_r.m33;
        this.m10 = i_mat_l.m10 * i_mat_r.m00 + i_mat_l.m11 * i_mat_r.m10 + i_mat_l.m12 * i_mat_r.m20 + i_mat_l.m13 * i_mat_r.m30;
        this.m11 = i_mat_l.m10 * i_mat_r.m01 + i_mat_l.m11 * i_mat_r.m11 + i_mat_l.m12 * i_mat_r.m21 + i_mat_l.m13 * i_mat_r.m31;
        this.m12 = i_mat_l.m10 * i_mat_r.m02 + i_mat_l.m11 * i_mat_r.m12 + i_mat_l.m12 * i_mat_r.m22 + i_mat_l.m13 * i_mat_r.m32;
        this.m13 = i_mat_l.m10 * i_mat_r.m03 + i_mat_l.m11 * i_mat_r.m13 + i_mat_l.m12 * i_mat_r.m23 + i_mat_l.m13 * i_mat_r.m33;
        this.m20 = i_mat_l.m20 * i_mat_r.m00 + i_mat_l.m21 * i_mat_r.m10 + i_mat_l.m22 * i_mat_r.m20 + i_mat_l.m23 * i_mat_r.m30;
        this.m21 = i_mat_l.m20 * i_mat_r.m01 + i_mat_l.m21 * i_mat_r.m11 + i_mat_l.m22 * i_mat_r.m21 + i_mat_l.m23 * i_mat_r.m31;
        this.m22 = i_mat_l.m20 * i_mat_r.m02 + i_mat_l.m21 * i_mat_r.m12 + i_mat_l.m22 * i_mat_r.m22 + i_mat_l.m23 * i_mat_r.m32;
        this.m23 = i_mat_l.m20 * i_mat_r.m03 + i_mat_l.m21 * i_mat_r.m13 + i_mat_l.m22 * i_mat_r.m23 + i_mat_l.m23 * i_mat_r.m33;
        this.m30 = i_mat_l.m30 * i_mat_r.m00 + i_mat_l.m31 * i_mat_r.m10 + i_mat_l.m32 * i_mat_r.m20 + i_mat_l.m33 * i_mat_r.m30;
        this.m31 = i_mat_l.m30 * i_mat_r.m01 + i_mat_l.m31 * i_mat_r.m11 + i_mat_l.m32 * i_mat_r.m21 + i_mat_l.m33 * i_mat_r.m31;
        this.m32 = i_mat_l.m30 * i_mat_r.m02 + i_mat_l.m31 * i_mat_r.m12 + i_mat_l.m32 * i_mat_r.m22 + i_mat_l.m33 * i_mat_r.m32;
        this.m33 = i_mat_l.m30 * i_mat_r.m03 + i_mat_l.m31 * i_mat_r.m13 + i_mat_l.m32 * i_mat_r.m23 + i_mat_l.m33 * i_mat_r.m33;
    }
    
    public final void identity() {
        final double n = 1.0;
        this.m33 = n;
        this.m22 = n;
        this.m11 = n;
        this.m00 = n;
        final double n2 = 0.0;
        this.m32 = n2;
        this.m31 = n2;
        this.m30 = n2;
        this.m23 = n2;
        this.m21 = n2;
        this.m20 = n2;
        this.m13 = n2;
        this.m12 = n2;
        this.m10 = n2;
        this.m03 = n2;
        this.m02 = n2;
        this.m01 = n2;
    }
    
    public void MatrixLerp(final MmdMatrix sm1, final MmdMatrix sm2, final float fLerpValue) {
        final double fT = 1.0 - fLerpValue;
        this.m00 = sm1.m00 * fLerpValue + sm2.m00 * fT;
        this.m01 = sm1.m01 * fLerpValue + sm2.m01 * fT;
        this.m02 = sm1.m02 * fLerpValue + sm2.m02 * fT;
        this.m03 = sm1.m03 * fLerpValue + sm2.m03 * fT;
        this.m10 = sm1.m10 * fLerpValue + sm2.m10 * fT;
        this.m11 = sm1.m11 * fLerpValue + sm2.m11 * fT;
        this.m12 = sm1.m12 * fLerpValue + sm2.m12 * fT;
        this.m13 = sm1.m13 * fLerpValue + sm2.m13 * fT;
        this.m20 = sm1.m20 * fLerpValue + sm2.m20 * fT;
        this.m21 = sm1.m21 * fLerpValue + sm2.m21 * fT;
        this.m22 = sm1.m22 * fLerpValue + sm2.m22 * fT;
        this.m23 = sm1.m23 * fLerpValue + sm2.m23 * fT;
        this.m30 = sm1.m30 * fLerpValue + sm2.m30 * fT;
        this.m31 = sm1.m31 * fLerpValue + sm2.m31 * fT;
        this.m32 = sm1.m32 * fLerpValue + sm2.m32 * fT;
        this.m33 = sm1.m33 * fLerpValue + sm2.m33 * fT;
    }
    
    public void QuaternionToMatrix(final MmdVector4 pvec4Quat) {
        final double x2 = pvec4Quat.x * pvec4Quat.x * 2.0;
        final double y2 = pvec4Quat.y * pvec4Quat.y * 2.0;
        final double z2 = pvec4Quat.z * pvec4Quat.z * 2.0;
        final double xy = pvec4Quat.x * pvec4Quat.y * 2.0;
        final double yz = pvec4Quat.y * pvec4Quat.z * 2.0;
        final double zx = pvec4Quat.z * pvec4Quat.x * 2.0;
        final double xw = pvec4Quat.x * pvec4Quat.w * 2.0;
        final double yw = pvec4Quat.y * pvec4Quat.w * 2.0;
        final double zw = pvec4Quat.z * pvec4Quat.w * 2.0;
        this.m00 = 1.0 - y2 - z2;
        this.m01 = xy + zw;
        this.m02 = zx - yw;
        this.m10 = xy - zw;
        this.m11 = 1.0 - z2 - x2;
        this.m12 = yz + xw;
        this.m20 = zx + yw;
        this.m21 = yz - xw;
        this.m22 = 1.0 - x2 - y2;
        final double n = 0.0;
        this.m32 = n;
        this.m31 = n;
        this.m30 = n;
        this.m23 = n;
        this.m13 = n;
        this.m03 = n;
        this.m33 = 1.0;
    }
    
    public MmdVector3 getPos() {
        return new MmdVector3((float)this.m30, (float)this.m31, (float)this.m32);
    }
    
    public MmdVector3 getRotXYZ() {
        double b = (float)Math.asin(-this.m02);
        final double bb = Math.cos(b);
        double c;
        double a;
        if (Double.isNaN(bb) || Math.abs(bb) <= 1.0E-4) {
            c = Math.atan2(-this.m10, this.m11);
            a = 0.0;
        }
        else {
            c = (float)Math.atan2(this.m01, this.m00);
            a = (float)Math.asin(this.m12 / bb);
            if (this.m22 < 0.0) {
                a = 3.141592653589793 - a;
            }
        }
        if (Double.isNaN(a) || Double.isInfinite(a)) {
            a = 0.0;
        }
        if (Double.isNaN(b) || Double.isInfinite(b)) {
            b = 0.0;
        }
        if (Double.isNaN(c) || Double.isInfinite(c)) {
            c = 0.0;
        }
        return new MmdVector3((float)a, (float)b, (float)c);
    }
    
    public DoubleBuffer getMatrixBuffer() {
        final double[] buf = new double[16];
        this.getValue(buf);
        MmdMatrix.matrixBuffer.position(0);
        MmdMatrix.matrixBuffer.put(buf);
        MmdMatrix.matrixBuffer.position(0);
        return MmdMatrix.matrixBuffer;
    }
    
    static {
        matrixBuffer = GLAllocation.createByteBuffer(256).asDoubleBuffer();
    }
}
