//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd;

import java.io.*;
import jp.nyatla.nymmd.struct.*;
import jp.nyatla.nymmd.types.*;
import java.util.*;
import jp.nyatla.nymmd.struct.vmd.*;

public class MmdVmdMotion_BasicClass
{
    private MotionData[] _motion_data_array;
    private FaceData[] _face_data_array;
    private float _fMaxFrame;
    
    public MmdVmdMotion_BasicClass(final InputStream i_stream) throws MmdException {
        this.initialize(i_stream);
    }
    
    public MotionData[] refMotionDataArray() {
        return this._motion_data_array;
    }
    
    public FaceData[] refFaceDataArray() {
        return this._face_data_array;
    }
    
    public float getMaxFrame() {
        return this._fMaxFrame;
    }
    
    private boolean initialize(final InputStream i_st) throws MmdException {
        if (i_st == null) {
            final VMD_Header tmp_vmd_header = new VMD_Header();
            final float[] max_frame = { 0.0f };
            (this._motion_data_array = new MotionData[2])[0] = new MotionData();
            this._motion_data_array[0].szBoneName = "\u3059\u3079\u3066\u306e\u89aa";
            this._motion_data_array[0].ulNumKeyFrames = 1;
            (this._motion_data_array[0].pKeyFrames = new BoneKeyFrame[1])[0] = new BoneKeyFrame();
            this._motion_data_array[0].pKeyFrames[0].fFrameNo = 1.0f;
            this._motion_data_array[0].pKeyFrames[0].vec3Position.setValue(new MmdVector3(0.0f, 0.0f, 0.0f));
            this._motion_data_array[0].pKeyFrames[0].vec4Rotate.setValue(new MmdVector4() {
                {
                    this.x = 0.0;
                    this.y = 0.0;
                    this.z = 0.0;
                    this.w = 1.0;
                }
            });
            this._motion_data_array[1] = new MotionData();
            this._motion_data_array[1].szBoneName = "\u53f3\u8db3\uff29\uff2b";
            this._motion_data_array[1].ulNumKeyFrames = 3;
            (this._motion_data_array[1].pKeyFrames = new BoneKeyFrame[3])[0] = new BoneKeyFrame();
            this._motion_data_array[1].pKeyFrames[0].fFrameNo = 0.0f;
            this._motion_data_array[1].pKeyFrames[0].vec3Position.setValue(new MmdVector3(0.0f, 5.0f, 5.0f));
            this._motion_data_array[1].pKeyFrames[0].vec4Rotate.setValue(new MmdVector4() {
                {
                    this.x = 0.0;
                    this.y = 0.0;
                    this.z = 0.0;
                    this.w = 1.0;
                }
            });
            this._motion_data_array[1].pKeyFrames[1] = new BoneKeyFrame();
            this._motion_data_array[1].pKeyFrames[1].fFrameNo = 29.0f;
            this._motion_data_array[1].pKeyFrames[1].vec3Position.setValue(new MmdVector3(0.0f, -5.0f, 0.0f));
            this._motion_data_array[1].pKeyFrames[1].vec4Rotate.setValue(new MmdVector4() {
                {
                    this.x = 0.0;
                    this.y = 0.0;
                    this.z = 0.0;
                    this.w = 1.0;
                }
            });
            this._motion_data_array[1].pKeyFrames[2] = new BoneKeyFrame();
            this._motion_data_array[1].pKeyFrames[2].fFrameNo = 59.0f;
            this._motion_data_array[1].pKeyFrames[2].vec3Position.setValue(new MmdVector3(0.0f, 5.0f, -5.0f));
            this._motion_data_array[1].pKeyFrames[2].vec4Rotate.setValue(new MmdVector4() {
                {
                    this.x = 0.0;
                    this.y = 0.0;
                    this.z = 0.0;
                    this.w = 1.0;
                }
            });
            this._face_data_array = new FaceData[0];
            this._fMaxFrame = 60.0f;
            return true;
        }
        final DataReader reader = new DataReader(i_st);
        final VMD_Header tmp_vmd_header2 = new VMD_Header();
        tmp_vmd_header2.read(reader);
        if (!tmp_vmd_header2.szHeader.equalsIgnoreCase("Vocaloid Motion Data 0002")) {
            throw new MmdException();
        }
        final float[] max_frame2 = { 0.0f };
        this._motion_data_array = createMotionDataList(reader, max_frame2);
        this._fMaxFrame = max_frame2[0];
        this._face_data_array = createFaceDataList(reader, max_frame2);
        this._fMaxFrame = ((this._fMaxFrame > max_frame2[0]) ? this._fMaxFrame : max_frame2[0]);
        return true;
    }
    
    private static FaceData[] createFaceDataList(final DataReader i_reader, final float[] o_max_frame) throws MmdException {
        final Vector<FaceData> result = new Vector<FaceData>();
        final int ulNumFaceKeyFrames = i_reader.readInt();
        final VMD_Face[] tmp_vmd_face = new VMD_Face[ulNumFaceKeyFrames];
        for (int i = 0; i < ulNumFaceKeyFrames; ++i) {
            (tmp_vmd_face[i] = new VMD_Face()).read(i_reader);
        }
        float max_frame = 0.0f;
        for (int j = 0; j < ulNumFaceKeyFrames; ++j) {
            if (max_frame < tmp_vmd_face[j].ulFrameNo) {
                max_frame = (float)tmp_vmd_face[j].ulFrameNo;
            }
            boolean is_found = false;
            for (int i2 = 0; i2 < result.size(); ++i2) {
                final FaceData pFaceTemp = result.get(i2);
                if (pFaceTemp.szFaceName.equals(tmp_vmd_face[j].szFaceName)) {
                    final FaceData faceData = pFaceTemp;
                    ++faceData.ulNumKeyFrames;
                    is_found = true;
                    break;
                }
            }
            if (!is_found) {
                final FaceData pNew = new FaceData();
                pNew.szFaceName = tmp_vmd_face[j].szFaceName;
                pNew.ulNumKeyFrames = 1;
                result.add(pNew);
            }
        }
        for (int j = 0; j < result.size(); ++j) {
            final FaceData pFaceTemp2 = result.get(j);
            pFaceTemp2.pKeyFrames = FaceKeyFrame.createArray(pFaceTemp2.ulNumKeyFrames);
            pFaceTemp2.ulNumKeyFrames = 0;
        }
        for (int j = 0; j < ulNumFaceKeyFrames; ++j) {
            for (int i3 = 0; i3 < result.size(); ++i3) {
                final FaceData pFaceTemp3 = result.get(i3);
                if (pFaceTemp3.szFaceName.equals(tmp_vmd_face[j].szFaceName)) {
                    final FaceKeyFrame pKeyFrame = pFaceTemp3.pKeyFrames[pFaceTemp3.ulNumKeyFrames];
                    pKeyFrame.fFrameNo = (float)tmp_vmd_face[j].ulFrameNo;
                    pKeyFrame.fRate = tmp_vmd_face[j].fFactor;
                    final FaceData faceData2 = pFaceTemp3;
                    ++faceData2.ulNumKeyFrames;
                    break;
                }
            }
        }
        for (int j = 0; j < result.size(); ++j) {
            final FaceData pFaceTemp2 = result.get(j);
            Arrays.sort(pFaceTemp2.pKeyFrames, (Comparator<? super FaceKeyFrame>)new FaceCompare());
        }
        o_max_frame[0] = max_frame;
        return result.toArray(new FaceData[result.size()]);
    }
    
    private static MotionData[] createMotionDataList(final DataReader i_reader, final float[] o_max_frame) throws MmdException {
        final Vector<MotionData> result = new Vector<MotionData>();
        final int ulNumBoneKeyFrames = i_reader.readInt();
        final VMD_Motion[] tmp_vmd_motion = new VMD_Motion[ulNumBoneKeyFrames];
        for (int i = 0; i < ulNumBoneKeyFrames; ++i) {
            (tmp_vmd_motion[i] = new VMD_Motion()).read(i_reader);
        }
        float max_frame = 0.0f;
        for (int j = 0; j < ulNumBoneKeyFrames; ++j) {
            if (max_frame < tmp_vmd_motion[j].ulFrameNo) {
                max_frame = (float)tmp_vmd_motion[j].ulFrameNo;
            }
            boolean is_found = false;
            for (int i2 = 0; i2 < result.size(); ++i2) {
                final MotionData pMotTemp = result.get(i2);
                if (pMotTemp.szBoneName.equals(tmp_vmd_motion[j].szBoneName)) {
                    final MotionData motionData = pMotTemp;
                    ++motionData.ulNumKeyFrames;
                    is_found = true;
                    break;
                }
            }
            if (!is_found) {
                final MotionData pNew = new MotionData();
                pNew.szBoneName = tmp_vmd_motion[j].szBoneName;
                pNew.ulNumKeyFrames = 1;
                result.add(pNew);
            }
        }
        for (int j = 0; j < result.size(); ++j) {
            final MotionData pMotTemp2 = result.get(j);
            pMotTemp2.pKeyFrames = BoneKeyFrame.createArray(pMotTemp2.ulNumKeyFrames);
            pMotTemp2.ulNumKeyFrames = 0;
        }
        for (int j = 0; j < ulNumBoneKeyFrames; ++j) {
            for (int i3 = 0; i3 < result.size(); ++i3) {
                final MotionData pMotTemp3 = result.get(i3);
                if (pMotTemp3.szBoneName.equals(tmp_vmd_motion[j].szBoneName)) {
                    final BoneKeyFrame pKeyFrame = pMotTemp3.pKeyFrames[pMotTemp3.ulNumKeyFrames];
                    pKeyFrame.fFrameNo = (float)tmp_vmd_motion[j].ulFrameNo;
                    pKeyFrame.vec3Position.setValue(tmp_vmd_motion[j].vec3Position);
                    pKeyFrame.vec4Rotate.QuaternionNormalize(tmp_vmd_motion[j].vec4Rotate);
                    final MotionData motionData2 = pMotTemp3;
                    ++motionData2.ulNumKeyFrames;
                    break;
                }
            }
        }
        for (int j = 0; j < result.size(); ++j) {
            final MotionData pMotTemp2 = result.get(j);
            Arrays.sort(pMotTemp2.pKeyFrames, (Comparator<? super BoneKeyFrame>)new BoneCompare());
        }
        o_max_frame[0] = max_frame;
        return result.toArray(new MotionData[result.size()]);
    }
}
