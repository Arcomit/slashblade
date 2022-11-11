//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd;

import java.util.*;
import com.google.common.eventbus.*;
import com.google.common.collect.*;
import java.util.stream.*;
import jp.nyatla.nymmd.types.*;
import jp.nyatla.nymmd.core.*;

public abstract class MmdMotionPlayer
{
    protected MmdPmdModel_BasicClass _ref_pmd_model;
    protected MmdVmdMotion_BasicClass _ref_vmd_motion;
    private PmdBone[] m_ppBoneList;
    private PmdFace[] m_ppFaceList;
    public MmdMatrix[] _skinning_mat;
    public Map<String, Integer> boneNameToIndex;
    private PmdBone m_pNeckBone;
    public final EventBus eventBus;
    private MmdVector3 _looktarget;
    private boolean _lookme_enabled;
    
    public int getBoneIndexByName(final String name) {
        final Integer result = this.boneNameToIndex.get(name);
        return (result == null) ? -1 : result;
    }
    
    public PmdBone getBoneByName(final String name) {
        final int idx = this.getBoneIndexByName(name);
        if (idx < 0) {
            return null;
        }
        return this._ref_pmd_model.getBoneArray()[idx];
    }
    
    public MmdMotionPlayer() {
        this.boneNameToIndex = (Map<String, Integer>)Maps.newHashMap();
        this.eventBus = new EventBus();
        this._looktarget = new MmdVector3();
        this._lookme_enabled = false;
    }
    
    public void setPmd(final MmdPmdModel_BasicClass i_pmd_model) throws MmdException {
        this._ref_pmd_model = i_pmd_model;
        final PmdBone[] bone_array = i_pmd_model.getBoneArray();
        this._skinning_mat = MmdMatrix.createArray(bone_array.length);
        this.boneNameToIndex.clear();
        final Integer n;
        IntStream.range(0, bone_array.length).forEach(value -> n = this.boneNameToIndex.put(bone_array[value].getName(), value));
        this.m_pNeckBone = null;
        final Integer headIdx = this.boneNameToIndex.get("\u982d");
        if (headIdx != null) {
            this.m_pNeckBone = bone_array[headIdx];
        }
        if (this._ref_vmd_motion != null) {
            this.makeBoneFaceList();
        }
    }
    
    public void setVmd(final MmdVmdMotion_BasicClass i_vmd_model) throws MmdException {
        if (this._ref_vmd_motion == i_vmd_model) {
            return;
        }
        this._ref_vmd_motion = i_vmd_model;
        final MotionData[] pMotionDataList = i_vmd_model.refMotionDataArray();
        this.m_ppBoneList = new PmdBone[pMotionDataList.length];
        final FaceData[] pFaceDataList = i_vmd_model.refFaceDataArray();
        this.m_ppFaceList = new PmdFace[pFaceDataList.length];
        if (this._ref_pmd_model != null) {
            this.makeBoneFaceList();
        }
    }
    
    private void makeBoneFaceList() {
        final MmdPmdModel_BasicClass pmd_model = this._ref_pmd_model;
        final MmdVmdMotion_BasicClass vmd_model = this._ref_vmd_motion;
        final MotionData[] pMotionDataList = vmd_model.refMotionDataArray();
        this.m_ppBoneList = new PmdBone[pMotionDataList.length];
        for (int i = 0; i < pMotionDataList.length; ++i) {
            this.m_ppBoneList[i] = pmd_model.getBoneByName(pMotionDataList[i].szBoneName);
        }
        final FaceData[] pFaceDataList = vmd_model.refFaceDataArray();
        this.m_ppFaceList = new PmdFace[pFaceDataList.length];
        for (int j = 0; j < pFaceDataList.length; ++j) {
            this.m_ppFaceList[j] = pmd_model.getFaceByName(pFaceDataList[j].szFaceName);
        }
    }
    
    public float getTimeLength() {
        return (float)(this._ref_vmd_motion.getMaxFrame() * 33.333333333333336);
    }
    
    public void updateMotion(final float i_position_in_msec) throws MmdException {
        final PmdIK[] ik_array = this._ref_pmd_model.getIKArray();
        final PmdBone[] bone_array = this._ref_pmd_model.getBoneArray();
        assert i_position_in_msec >= 0.0f;
        float frame = (float)(i_position_in_msec / 33.333333333333336);
        if (frame > this._ref_vmd_motion.getMaxFrame()) {
            frame = this._ref_vmd_motion.getMaxFrame();
        }
        this.updateFace(frame);
        for (final PmdBone bone : bone_array) {
            bone.reset();
        }
        this.updateBone(frame);
        this.eventBus.post((Object)new UpdateBoneEvent.Pre(this._ref_pmd_model.getBoneArray(), this));
        for (int i = 0; i < bone_array.length; ++i) {
            bone_array[i].updateMatrix();
        }
        for (int i = 0; i < ik_array.length; ++i) {
            ik_array[i].update();
        }
        this.eventBus.post((Object)new UpdateBoneEvent.Pre(this._ref_pmd_model.getBoneArray(), this));
        for (int i = 0; i < bone_array.length; ++i) {
            bone_array[i].updateMatrix();
        }
        if (this._lookme_enabled) {
            this.updateNeckBone();
        }
        for (int i = 0; i < bone_array.length; ++i) {
            bone_array[i].updateSkinningMat(this._skinning_mat[i]);
        }
        this.onUpdateSkinningMatrix(this._skinning_mat);
    }
    
    protected abstract void onUpdateSkinningMatrix(final MmdMatrix[] p0) throws MmdException;
    
    public void setLookVector(final float i_x, final float i_y, final float i_z) {
        this._looktarget.x = i_x;
        this._looktarget.y = i_y;
        this._looktarget.z = i_z;
    }
    
    public void lookMeEnable(final boolean i_enable) {
        this._lookme_enabled = i_enable;
    }
    
    private void updateNeckBone() {
        if (this.m_pNeckBone == null) {
            return;
        }
        this.m_pNeckBone.lookAt(this._looktarget);
        PmdBone[] bone_array;
        int i;
        for (bone_array = this._ref_pmd_model.getBoneArray(), i = 0; i < bone_array.length; ++i) {
            if (this.m_pNeckBone == bone_array[i]) {
                break;
            }
        }
        while (i < bone_array.length) {
            bone_array[i].updateMatrix();
            ++i;
        }
    }
    
    private void updateBone(final float i_frame) throws MmdException {
        final PmdBone[] ppBone = this.m_ppBoneList;
        final MotionData[] pMotionDataList = this._ref_vmd_motion.refMotionDataArray();
        for (int i = 0; i < pMotionDataList.length; ++i) {
            if (ppBone[i] != null) {
                pMotionDataList[i].getMotionPosRot(i_frame, ppBone[i]);
            }
        }
    }
    
    private void updateFace(final float i_frame) throws MmdException {
        final MmdVector3[] position_array = this._ref_pmd_model.getPositionArray();
        final PmdFace[] ppFace = this.m_ppFaceList;
        final FaceData[] pFaceDataList = this._ref_vmd_motion.refFaceDataArray();
        for (int i = 0; i < pFaceDataList.length; ++i) {
            final float fFaceRate = this.getFaceRate(pFaceDataList[i], i_frame);
            if (ppFace[i] != null) {
                if (fFaceRate == 1.0f) {
                    ppFace[i].setFace(position_array);
                }
                else if (0.001f < fFaceRate) {
                    ppFace[i].blendFace(position_array, fFaceRate);
                }
            }
        }
    }
    
    private float getFaceRate(final FaceData pFaceData, float fFrame) {
        final int ulNumKeyFrame = pFaceData.ulNumKeyFrames;
        if (fFrame > pFaceData.pKeyFrames[ulNumKeyFrame - 1].fFrameNo) {
            fFrame = pFaceData.pKeyFrames[ulNumKeyFrame - 1].fFrameNo;
        }
        int i;
        for (i = 0; i < ulNumKeyFrame && fFrame > pFaceData.pKeyFrames[i].fFrameNo; ++i) {}
        int lKey0 = i - 1;
        int lKey2 = i;
        if (lKey0 <= 0) {
            lKey0 = 0;
        }
        if (i == ulNumKeyFrame) {
            lKey2 = ulNumKeyFrame - 1;
        }
        final float fTime0 = pFaceData.pKeyFrames[lKey0].fFrameNo;
        final float fTime2 = pFaceData.pKeyFrames[lKey2].fFrameNo;
        if (lKey0 != lKey2) {
            final float fLerpValue = (fFrame - fTime0) / (fTime2 - fTime0);
            return pFaceData.pKeyFrames[lKey0].fRate * (1.0f - fLerpValue) + pFaceData.pKeyFrames[lKey2].fRate * fLerpValue;
        }
        return pFaceData.pKeyFrames[lKey0].fRate;
    }
    
    public static class UpdateBoneEvent
    {
        public final PmdBone[] bones;
        public final MmdMotionPlayer motionPlayer;
        
        public UpdateBoneEvent(final PmdBone[] bones, final MmdMotionPlayer motionPlayer) {
            this.bones = bones;
            this.motionPlayer = motionPlayer;
        }
        
        public static class Pre extends UpdateBoneEvent
        {
            public Pre(final PmdBone[] bones, final MmdMotionPlayer motionPlayer) {
                super(bones, motionPlayer);
            }
        }
        
        public static class Post extends UpdateBoneEvent
        {
            public Post(final PmdBone[] bones, final MmdMotionPlayer motionPlayer) {
                super(bones, motionPlayer);
            }
        }
    }
}
