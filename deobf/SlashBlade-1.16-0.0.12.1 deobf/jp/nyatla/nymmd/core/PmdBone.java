//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.core;

import jp.nyatla.nymmd.types.*;
import jp.nyatla.nymmd.struct.pmd.*;

public class PmdBone
{
    private String _name;
    public final MmdVector3 _pmd_bone_position;
    public final MmdVector3 m_vec3Offset;
    public final MmdMatrix m_matInvTransform;
    public PmdBone _parent_bone;
    public PmdBone m_pChildBone;
    public final MmdMatrix m_matLocal;
    public final MmdVector3 m_vec3Position;
    public final MmdVector4 m_vec4Rotate;
    public boolean m_bIKLimitAngle;
    private final MmdMatrix _mat_tmp1;
    private final MmdMatrix _mat_tmp2;
    private final MmdMatrix _mat_tmp3;
    private final MmdVector3 _lookAt_vec3LocalTgtPosZY;
    private final MmdVector3 _lookAt_vec3LocalTgtPosXZ;
    private final MmdVector3 _lookAt_vec3Angle;
    
    public String getName() {
        return this._name;
    }
    
    public PmdBone(final PMD_Bone pPMDBoneData, final PmdBone[] pBoneArray) {
        this._pmd_bone_position = new MmdVector3();
        this.m_vec3Offset = new MmdVector3();
        this.m_matInvTransform = new MmdMatrix();
        this.m_matLocal = new MmdMatrix();
        this.m_vec3Position = new MmdVector3();
        this.m_vec4Rotate = new MmdVector4();
        this._mat_tmp1 = new MmdMatrix();
        this._mat_tmp2 = new MmdMatrix();
        this._mat_tmp3 = new MmdMatrix();
        this._lookAt_vec3LocalTgtPosZY = new MmdVector3();
        this._lookAt_vec3LocalTgtPosXZ = new MmdVector3();
        this._lookAt_vec3Angle = new MmdVector3();
        this._name = pPMDBoneData.szName;
        this._pmd_bone_position.setValue(pPMDBoneData.vec3Position);
        if (pPMDBoneData.nParentNo != -1) {
            this._parent_bone = pBoneArray[pPMDBoneData.nParentNo];
            if (this._parent_bone != null) {
                this.m_vec3Offset.Vector3Sub(this._pmd_bone_position, this._parent_bone._pmd_bone_position);
            }
            else {
                this.m_vec3Offset.Vector3Sub(this._pmd_bone_position, new MmdVector3(0.0f, 1.0f, 0.0f));
            }
        }
        else {
            this._parent_bone = null;
            this.m_vec3Offset.setValue(this._pmd_bone_position);
        }
        if (pPMDBoneData.nChildNo != -1) {
            this.m_pChildBone = pBoneArray[pPMDBoneData.nChildNo];
        }
        this.m_matInvTransform.identity();
        this.m_matInvTransform.m30 = -this._pmd_bone_position.x;
        this.m_matInvTransform.m31 = -this._pmd_bone_position.y;
        this.m_matInvTransform.m32 = -this._pmd_bone_position.z;
        this.m_bIKLimitAngle = false;
        this.reset();
    }
    
    public void recalcOffset() {
        if (this._parent_bone != null) {
            this.m_vec3Offset.Vector3Sub(this._pmd_bone_position, this._parent_bone._pmd_bone_position);
        }
    }
    
    public void reset() {
        final MmdVector3 vec3Position = this.m_vec3Position;
        final MmdVector3 vec3Position2 = this.m_vec3Position;
        final MmdVector3 vec3Position3 = this.m_vec3Position;
        final float x = 0.0f;
        vec3Position3.z = x;
        vec3Position2.y = x;
        vec3Position.x = x;
        final MmdVector4 vec4Rotate = this.m_vec4Rotate;
        final MmdVector4 vec4Rotate2 = this.m_vec4Rotate;
        final MmdVector4 vec4Rotate3 = this.m_vec4Rotate;
        final double x2 = 0.0;
        vec4Rotate3.z = x2;
        vec4Rotate2.y = x2;
        vec4Rotate.x = x2;
        this.m_vec4Rotate.w = 1.0;
        this.m_matLocal.identity();
        this.m_matLocal.m30 = this._pmd_bone_position.x;
        this.m_matLocal.m31 = this._pmd_bone_position.y;
        this.m_matLocal.m32 = this._pmd_bone_position.z;
    }
    
    public void setIKLimitAngle(final boolean i_value) {
        this.m_bIKLimitAngle = i_value;
    }
    
    public void updateSkinningMat(final MmdMatrix o_matrix) {
        o_matrix.mul(this.m_matInvTransform, this.m_matLocal);
    }
    
    public void updateMatrix() {
        if (this._parent_bone != null) {
            this._mat_tmp1.QuaternionToMatrix(this.m_vec4Rotate);
            this._mat_tmp1.m30 = this.m_vec3Position.x + this.m_vec3Offset.x;
            this._mat_tmp1.m31 = this.m_vec3Position.y + this.m_vec3Offset.y;
            this._mat_tmp1.m32 = this.m_vec3Position.z + this.m_vec3Offset.z;
            this.m_matLocal.mul(this._mat_tmp1, this._parent_bone.m_matLocal);
        }
        else {
            this.m_matLocal.QuaternionToMatrix(this.m_vec4Rotate);
            this.m_matLocal.m30 = this.m_vec3Position.x + this.m_vec3Offset.x;
            this.m_matLocal.m31 = this.m_vec3Position.y + this.m_vec3Offset.y;
            this.m_matLocal.m32 = this.m_vec3Position.z + this.m_vec3Offset.z;
        }
    }
    
    public void lookAt(final MmdVector3 pvecTargetPos) {
        final MmdMatrix mat_tmp1 = this._mat_tmp1;
        final MmdMatrix mat_tmp2 = this._mat_tmp3;
        final MmdVector3 vec3LocalTgtPosZY = this._lookAt_vec3LocalTgtPosZY;
        final MmdVector3 vec3LocalTgtPosXZ = this._lookAt_vec3LocalTgtPosXZ;
        mat_tmp1.identity();
        mat_tmp1.m30 = this.m_vec3Position.x + this.m_vec3Offset.x;
        mat_tmp1.m31 = this.m_vec3Position.y + this.m_vec3Offset.y;
        mat_tmp1.m32 = this.m_vec3Position.z + this.m_vec3Offset.z;
        if (this._parent_bone != null) {
            mat_tmp2.inverse(this._parent_bone.m_matLocal);
            this._mat_tmp2.mul(mat_tmp1, mat_tmp2);
            mat_tmp2.inverse(this._mat_tmp2);
            vec3LocalTgtPosZY.Vector3Transform(pvecTargetPos, mat_tmp2);
        }
        else {
            mat_tmp1.inverse(mat_tmp1);
            vec3LocalTgtPosZY.Vector3Transform(pvecTargetPos, mat_tmp1);
        }
        vec3LocalTgtPosXZ.setValue(vec3LocalTgtPosZY);
        vec3LocalTgtPosXZ.y = 0.0f;
        vec3LocalTgtPosXZ.Vector3Normalize(vec3LocalTgtPosXZ);
        vec3LocalTgtPosZY.x = 0.0f;
        vec3LocalTgtPosZY.Vector3Normalize(vec3LocalTgtPosZY);
        final MmdVector3 lookAt_vec3Angle;
        final MmdVector3 mmdVector4;
        final MmdVector3 mmdVector3;
        final MmdVector3 vec3Angle = mmdVector3 = (mmdVector4 = (lookAt_vec3Angle = this._lookAt_vec3Angle));
        final float x = 0.0f;
        mmdVector3.z = x;
        mmdVector4.y = x;
        lookAt_vec3Angle.x = x;
        if (vec3LocalTgtPosZY.z > 0.0f) {
            vec3Angle.x = (float)(Math.asin(vec3LocalTgtPosZY.y) - 0.3490658503988659);
        }
        if (vec3LocalTgtPosXZ.x < 0.0f) {
            vec3Angle.y = (float)Math.acos(vec3LocalTgtPosXZ.z);
        }
        else {
            vec3Angle.y = (float)(-Math.acos(vec3LocalTgtPosXZ.z));
        }
        if (vec3Angle.x < -0.4363323129985824) {
            vec3Angle.x = -0.43633232f;
        }
        if (0.7853981633974483 < vec3Angle.x) {
            vec3Angle.x = 0.7853982f;
        }
        if (vec3Angle.y < -1.3962634015954636) {
            vec3Angle.y = -1.3962634f;
        }
        if (1.3962634015954636 < vec3Angle.y) {
            vec3Angle.y = 1.3962634f;
        }
        this.m_vec4Rotate.QuaternionCreateEuler(vec3Angle);
    }
}
