//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd.core;

import jp.nyatla.nymmd.struct.pmd.*;
import jp.nyatla.nymmd.types.*;
import jp.nyatla.nymmd.*;

public class PmdFace
{
    private String _name;
    private PMD_FACE_VTX[] _face_vertex;
    
    public PmdFace(final PMD_FACE pPMDFaceData, final PmdFace pPMDFaceBase) {
        this._name = pPMDFaceData.szName;
        final int number_of_vertex = pPMDFaceData.ulNumVertices;
        this._face_vertex = PMD_FACE_VTX.createArray(number_of_vertex);
        for (int i = 0; i < this._face_vertex.length; ++i) {
            this._face_vertex[i].setValue(pPMDFaceData.pVertices[i]);
        }
        if (pPMDFaceBase != null) {
            final PMD_FACE_VTX[] vertex_array = this._face_vertex;
            for (int j = this._face_vertex.length - 1; j >= 0; --j) {
                final PMD_FACE_VTX vertex = vertex_array[j];
                vertex.vec3Pos.Vector3Add(pPMDFaceBase._face_vertex[vertex.ulIndex].vec3Pos, vertex.vec3Pos);
                vertex.ulIndex = pPMDFaceBase._face_vertex[vertex.ulIndex].ulIndex;
            }
        }
    }
    
    public void setFace(final MmdVector3[] pvec3Vertices) throws MmdException {
        if (this._face_vertex == null) {
            throw new MmdException();
        }
        final PMD_FACE_VTX[] vertex_array = this._face_vertex;
        for (int i = vertex_array.length - 1; i >= 0; --i) {
            final PMD_FACE_VTX vertex = vertex_array[i];
            pvec3Vertices[vertex.ulIndex].setValue(vertex.vec3Pos);
        }
    }
    
    public void blendFace(final MmdVector3[] pvec3Vertices, final float fRate) throws MmdException {
        if (this._face_vertex == null) {
            throw new MmdException();
        }
        final PMD_FACE_VTX[] vertex_array = this._face_vertex;
        for (int i = vertex_array.length - 1; i >= 0; --i) {
            final PMD_FACE_VTX vertex = vertex_array[i];
            final MmdVector3 vec = pvec3Vertices[vertex.ulIndex];
            vec.Vector3Lerp(vec, vertex.vec3Pos, fRate);
        }
    }
    
    public String getName() {
        return this._name;
    }
}
