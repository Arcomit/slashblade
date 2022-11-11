//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.core;

import jp.nyatla.nymmd.types.*;
import jp.nyatla.nymmd.struct.pmd.*;

public class PmdIK
{
    private PmdBone m_pTargetBone;
    private PmdBone m_pEffBone;
    private int m_unCount;
    private double _fact;
    private int m_nSortVal;
    private PmdBone[] m_ppBoneList;
    private final MmdVector3[] _work_vector3;
    private final MmdVector4 _work_vector4;
    private final MmdMatrix __update_matInvBone;
    
    public PmdIK(final PMD_IK pPMDIKData, final PmdBone[] i_ref_bone_array) {
        this._work_vector3 = MmdVector3.createArray(4);
        this._work_vector4 = new MmdVector4();
        this.__update_matInvBone = new MmdMatrix();
        this.m_pTargetBone = i_ref_bone_array[pPMDIKData.nTargetNo];
        this.m_pEffBone = i_ref_bone_array[pPMDIKData.nEffNo];
        this.m_unCount = pPMDIKData.unCount;
        this._fact = pPMDIKData.fFact * 3.141592653589793;
        this.m_nSortVal = pPMDIKData.punLinkNo[0];
        final int number_of_ik_link = pPMDIKData.cbNumLink;
        this.m_ppBoneList = new PmdBone[number_of_ik_link];
        for (int i = 0; i < number_of_ik_link; ++i) {
            this.m_ppBoneList[i] = i_ref_bone_array[pPMDIKData.punLinkNo[i]];
            if (this.m_ppBoneList[i].getName().equals("\u5de6\u3072\u3056") || this.m_ppBoneList[i].getName().equals("\u53f3\u3072\u3056")) {
                this.m_ppBoneList[i].setIKLimitAngle(true);
            }
        }
    }
    
    private void limitAngle(final MmdVector4 pvec4Out, final MmdVector4 pvec4Src) {
        final MmdVector3 vec3Angle = this._work_vector3[0];
        vec3Angle.QuaternionToEuler(pvec4Src);
        if (vec3Angle.x < -3.141592653589793) {
            vec3Angle.x = -3.1415927f;
        }
        if (-0.002f < vec3Angle.x) {
            vec3Angle.x = -0.002f;
        }
        vec3Angle.y = 0.0f;
        vec3Angle.z = 0.0f;
        pvec4Out.QuaternionCreateEuler(vec3Angle);
    }
    
    public int getSortVal() {
        return this.m_nSortVal;
    }
    
    public void update() {
        final MmdMatrix matInvBone = this.__update_matInvBone;
        final MmdVector3 vec3EffPos = this._work_vector3[0];
        final MmdVector3 vec3TargetPos = this._work_vector3[1];
        final MmdVector3 vec3Diff = this._work_vector3[2];
        final MmdVector3 vec3RotAxis = this._work_vector3[3];
        final MmdVector4 vec4RotQuat = this._work_vector4;
        for (int i = this.m_ppBoneList.length - 1; i >= 0; --i) {
            this.m_ppBoneList[i].updateMatrix();
        }
        this.m_pEffBone.updateMatrix();
        for (int it = this.m_unCount - 1; it >= 0; --it) {
            for (int j = 0; j < this.m_ppBoneList.length; ++j) {
                matInvBone.inverse(this.m_ppBoneList[j].m_matLocal);
                vec3EffPos.Vector3Transform(this.m_pEffBone.m_matLocal, matInvBone);
                vec3TargetPos.Vector3Transform(this.m_pTargetBone.m_matLocal, matInvBone);
                vec3Diff.Vector3Sub(vec3EffPos, vec3TargetPos);
                if (vec3Diff.Vector3DotProduct(vec3Diff) < 1.0000000116860974E-7) {
                    return;
                }
                vec3EffPos.Vector3Normalize(vec3EffPos);
                vec3TargetPos.Vector3Normalize(vec3TargetPos);
                double fRotAngle = Math.acos(vec3EffPos.Vector3DotProduct(vec3TargetPos));
                if (1.0E-8 < Math.abs(fRotAngle)) {
                    if (fRotAngle < -this._fact) {
                        fRotAngle = -this._fact;
                    }
                    else if (this._fact < fRotAngle) {
                        fRotAngle = this._fact;
                    }
                    vec3RotAxis.Vector3CrossProduct(vec3EffPos, vec3TargetPos);
                    if (vec3RotAxis.Vector3DotProduct(vec3RotAxis) >= 1.0E-7) {
                        vec3RotAxis.Vector3Normalize(vec3RotAxis);
                        vec4RotQuat.QuaternionCreateAxis(vec3RotAxis, fRotAngle);
                        if (this.m_ppBoneList[j].m_bIKLimitAngle) {
                            this.limitAngle(vec4RotQuat, vec4RotQuat);
                        }
                        vec4RotQuat.QuaternionNormalize(vec4RotQuat);
                        this.m_ppBoneList[j].m_vec4Rotate.QuaternionMultiply(this.m_ppBoneList[j].m_vec4Rotate, vec4RotQuat);
                        this.m_ppBoneList[j].m_vec4Rotate.QuaternionNormalize(this.m_ppBoneList[j].m_vec4Rotate);
                        for (int k = j; k >= 0; --k) {
                            this.m_ppBoneList[k].updateMatrix();
                        }
                        this.m_pEffBone.updateMatrix();
                    }
                }
            }
        }
    }
}
