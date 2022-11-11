//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd;

import jp.nyatla.nymmd.core.*;
import jp.nyatla.nymmd.types.*;
import java.io.*;
import com.google.common.collect.*;
import jp.nyatla.nymmd.struct.*;
import java.util.stream.*;
import java.util.*;
import jp.nyatla.nymmd.struct.pmd.*;
import net.minecraft.util.*;

public abstract class MmdPmdModel_BasicClass
{
    private String _name;
    private int _number_of_vertex;
    private PmdFace[] m_pFaceArray;
    private PmdBone[] m_pBoneArray;
    private Map<String, PmdBone> boneMap;
    private PmdIK[] m_pIKArray;
    private MmdVector3[] _position_array;
    private MmdVector3[] _normal_array;
    private MmdTexUV[] _texture_uv;
    private PmdSkinInfo[] _skin_info_array;
    private PmdMaterial[] _materials;
    private IResourceProvider _res_provider;
    
    public MmdPmdModel_BasicClass(final InputStream i_stream, final IResourceProvider i_provider) throws MmdException {
        this.boneMap = (Map<String, PmdBone>)Maps.newHashMap();
        this.initialize(i_stream);
        this._res_provider = i_provider;
    }
    
    public int getNumberOfVertex() {
        return this._number_of_vertex;
    }
    
    public PmdMaterial[] getMaterials() {
        return this._materials;
    }
    
    public MmdTexUV[] getUvArray() {
        return this._texture_uv;
    }
    
    public MmdVector3[] getPositionArray() {
        return this._position_array;
    }
    
    public MmdVector3[] getNormatArray() {
        return this._normal_array;
    }
    
    public PmdSkinInfo[] getSkinInfoArray() {
        return this._skin_info_array;
    }
    
    public PmdFace[] getFaceArray() {
        return this.m_pFaceArray;
    }
    
    public PmdBone[] getBoneArray() {
        return this.m_pBoneArray;
    }
    
    public PmdIK[] getIKArray() {
        return this.m_pIKArray;
    }
    
    public PmdBone getBoneByName(final String i_name) {
        return this.boneMap.get(i_name);
    }
    
    public PmdFace getFaceByName(final String i_name) {
        final PmdFace[] face_array = this.m_pFaceArray;
        for (int i = 0; i < face_array.length; ++i) {
            final PmdFace face = face_array[i];
            if (face.getName().equals(i_name)) {
                return face;
            }
        }
        return null;
    }
    
    private void initialize(final InputStream i_stream) throws MmdException {
        final DataReader reader = new DataReader(i_stream);
        final PMD_Header pPMDHeader = new PMD_Header();
        pPMDHeader.read(reader);
        if (!pPMDHeader.szMagic.equalsIgnoreCase("PMD")) {
            throw new MmdException();
        }
        this._name = pPMDHeader.szName;
        this._number_of_vertex = reader.readInt();
        if (this._number_of_vertex < 0) {
            throw new MmdException();
        }
        this._position_array = MmdVector3.createArray(this._number_of_vertex);
        this._normal_array = MmdVector3.createArray(this._number_of_vertex);
        this._texture_uv = MmdTexUV.createArray(this._number_of_vertex);
        this._skin_info_array = new PmdSkinInfo[this._number_of_vertex];
        final PMD_Vertex tmp_pmd_vertex = new PMD_Vertex();
        for (int i = 0; i < this._number_of_vertex; ++i) {
            tmp_pmd_vertex.read(reader);
            this._position_array[i].setValue(tmp_pmd_vertex.vec3Pos);
            this._normal_array[i].setValue(tmp_pmd_vertex.vec3Normal);
            this._texture_uv[i].setValue(tmp_pmd_vertex.uvTex);
            this._skin_info_array[i] = new PmdSkinInfo();
            this._skin_info_array[i].fWeight = tmp_pmd_vertex.cbWeight / 100.0f;
            this._skin_info_array[i].unBoneNo_0 = tmp_pmd_vertex.unBoneNo[0];
            this._skin_info_array[i].unBoneNo_1 = tmp_pmd_vertex.unBoneNo[1];
        }
        final short[] indices_array = createIndicesArray(reader);
        final int number_of_materials = reader.readInt();
        this._materials = new PmdMaterial[number_of_materials];
        final PMD_Material tmp_pmd_material = new PMD_Material();
        int indices_ptr = 0;
        for (int j = 0; j < number_of_materials; ++j) {
            tmp_pmd_material.read(reader);
            final PmdMaterial pmdm = new PmdMaterial();
            pmdm.unknown = tmp_pmd_material.unknown;
            final int num_of_indices = tmp_pmd_material.ulNumIndices;
            System.arraycopy(indices_array, indices_ptr, pmdm.indices = new short[num_of_indices], 0, num_of_indices);
            indices_ptr += num_of_indices;
            pmdm.col4Diffuse.setValue(tmp_pmd_material.col4Diffuse);
            pmdm.col4Specular.r = tmp_pmd_material.col3Specular.r;
            pmdm.col4Specular.g = tmp_pmd_material.col3Specular.g;
            pmdm.col4Specular.b = tmp_pmd_material.col3Specular.b;
            pmdm.col4Specular.a = 1.0f;
            pmdm.col4Ambient.r = tmp_pmd_material.col3Ambient.r;
            pmdm.col4Ambient.g = tmp_pmd_material.col3Ambient.g;
            pmdm.col4Ambient.b = tmp_pmd_material.col3Ambient.b;
            pmdm.col4Ambient.a = 1.0f;
            pmdm.fShininess = tmp_pmd_material.fShininess;
            pmdm.texture_name = tmp_pmd_material.szTextureFileName;
            if (pmdm.texture_name.length() < 1) {
                pmdm.texture_name = null;
            }
            this._materials[j] = pmdm;
        }
        this.m_pBoneArray = createBoneArray(reader);
        this.boneMap.clear();
        Stream.of(this.m_pBoneArray).forEach(bone -> this.boneMap.put(bone.getName(), bone));
        this.m_pIKArray = createIKArray(reader, this.m_pBoneArray);
        this.m_pFaceArray = createFaceArray(reader);
        final PmdFace[] face_array = this.m_pFaceArray;
        if (face_array != null && 0 < face_array.length) {
            face_array[0].setFace(this._position_array);
        }
    }
    
    private static short[] createIndicesArray(final DataReader i_reader) throws MmdException {
        final int num_of_indeces = i_reader.readInt();
        short[] result = new short[num_of_indeces];
        result = new short[num_of_indeces];
        for (int i = 0; i < num_of_indeces; ++i) {
            result[i] = i_reader.readShort();
        }
        return result;
    }
    
    private static PmdBone[] createBoneArray(final DataReader i_reader) throws MmdException {
        final int number_of_bone = i_reader.readShort();
        final PMD_Bone tmp_pmd_bone = new PMD_Bone();
        final PmdBone[] result = new PmdBone[number_of_bone];
        for (int i = 0; i < number_of_bone; ++i) {
            tmp_pmd_bone.read(i_reader);
            result[i] = new PmdBone(tmp_pmd_bone, result);
        }
        for (int i = 0; i < number_of_bone; ++i) {
            result[i].recalcOffset();
        }
        return result;
    }
    
    private static PmdIK[] createIKArray(final DataReader i_reader, final PmdBone[] i_ref_bone_array) throws MmdException {
        final int number_of_ik = i_reader.readShort();
        final PMD_IK tmp_pmd_ik = new PMD_IK();
        final PmdIK[] result = new PmdIK[number_of_ik];
        if (number_of_ik > 0) {
            for (int i = 0; i < number_of_ik; ++i) {
                tmp_pmd_ik.read(i_reader);
                result[i] = new PmdIK(tmp_pmd_ik, i_ref_bone_array);
            }
            Arrays.sort(result, (Comparator<? super PmdIK>)new DataComparator());
        }
        return result;
    }
    
    private static PmdFace[] createFaceArray(final DataReader i_reader) throws MmdException {
        final int number_of_face = i_reader.readShort();
        final PMD_FACE tmp_pmd_face = new PMD_FACE();
        final PmdFace[] result = new PmdFace[number_of_face];
        if (number_of_face > 0) {
            for (int i = 0; i < number_of_face; ++i) {
                tmp_pmd_face.read(i_reader);
                result[i] = new PmdFace(tmp_pmd_face, result[0]);
            }
        }
        return result;
    }
    
    public String getModelName() {
        return this._name;
    }
    
    public IResourceProvider getResourceProvider() {
        return this._res_provider;
    }
    
    public interface IResourceProvider
    {
        ResourceLocation getTextureStream(final String p0) throws MmdException;
    }
}
