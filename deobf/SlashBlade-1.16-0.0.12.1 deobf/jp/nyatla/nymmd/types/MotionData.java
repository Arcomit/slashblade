//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.types;

import jp.nyatla.nymmd.core.*;

public class MotionData
{
    public String szBoneName;
    public int ulNumKeyFrames;
    public BoneKeyFrame[] pKeyFrames;
    
    public void getMotionPosRot(float fFrame, final PmdBone i_pmd_bone) {
        final int ulNumKeyFrame = this.ulNumKeyFrames;
        final BoneKeyFrame[] bone_key_frame = this.pKeyFrames;
        if (fFrame > bone_key_frame[ulNumKeyFrame - 1].fFrameNo) {
            fFrame = bone_key_frame[ulNumKeyFrame - 1].fFrameNo;
        }
        int lKey0 = findByBinarySearch(bone_key_frame, fFrame, 0, ulNumKeyFrame - 1) - 1;
        int lKey2 = lKey0 + 1;
        if (lKey2 == ulNumKeyFrame) {
            lKey2 = ulNumKeyFrame - 1;
        }
        if (lKey0 < 0) {
            lKey0 = 0;
        }
        final float fTime0 = bone_key_frame[lKey0].fFrameNo;
        final float fTime2 = bone_key_frame[lKey2].fFrameNo;
        final MmdVector3 pvec3Pos = i_pmd_bone.m_vec3Position;
        final MmdVector4 pvec4Rot = i_pmd_bone.m_vec4Rotate;
        if (lKey0 != lKey2) {
            final float fLerpValue = (fFrame - fTime0) / (fTime2 - fTime0);
            pvec3Pos.Vector3Lerp(bone_key_frame[lKey0].vec3Position, bone_key_frame[lKey2].vec3Position, fLerpValue);
            pvec4Rot.QuaternionSlerp(bone_key_frame[lKey0].vec4Rotate, bone_key_frame[lKey2].vec4Rotate, (double)fLerpValue);
            pvec4Rot.QuaternionNormalize(pvec4Rot);
        }
        else {
            pvec3Pos.setValue(bone_key_frame[lKey0].vec3Position);
            pvec4Rot.setValue(bone_key_frame[lKey0].vec4Rotate);
        }
    }
    
    private static int findByBinarySearch(final BoneKeyFrame[] pKeyFrames, final float fFrame, final int start, final int end) {
        final int diff = end - start;
        if (diff < 8) {
            for (int i = start; i < end; ++i) {
                if (fFrame < pKeyFrames[i].fFrameNo) {
                    return i;
                }
            }
            return end;
        }
        final int mid = (start + end) / 2;
        if (fFrame < pKeyFrames[mid].fFrameNo) {
            return findByBinarySearch(pKeyFrames, fFrame, start, mid);
        }
        return findByBinarySearch(pKeyFrames, fFrame, mid, end);
    }
}
