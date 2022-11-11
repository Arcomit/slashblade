//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package jp.nyatla.nymmd;

import java.util.*;
import jp.nyatla.nymmd.types.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import com.mojang.blaze3d.platform.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import java.nio.*;
import net.minecraft.util.*;

public class MmdMotionPlayerGL2 extends MmdMotionPlayer
{
    private final MmdMatrix __tmp_matrix;
    private Material[] _materials;
    private float[] _fbuf;
    private MmdTexUV[] _tex_array;
    
    public MmdMotionPlayerGL2() {
        this.__tmp_matrix = new MmdMatrix();
    }
    
    public void setPmd(final MmdPmdModel_BasicClass i_pmd_model) throws MmdException {
        super.setPmd(i_pmd_model);
        final int number_of_vertex = i_pmd_model.getNumberOfVertex();
        this._fbuf = new float[number_of_vertex * 3 * 2];
        final MmdPmdModel_BasicClass.IResourceProvider tp = i_pmd_model.getResourceProvider();
        final PmdMaterial[] m = i_pmd_model.getMaterials();
        final Vector<Material> materials = new Vector<Material>();
        for (int i = 0; i < m.length; ++i) {
            final Material new_material = new Material();
            new_material.unknown = m[i].unknown;
            final float[] color = new float[12];
            m[i].col4Diffuse.getValue(color, 0);
            m[i].col4Ambient.getValue(color, 4);
            m[i].col4Specular.getValue(color, 8);
            new_material.color = color;
            new_material.fShininess = m[i].fShininess;
            if (m[i].texture_name != null && !m[i].texture_name.isEmpty()) {
                new_material.texture_id = tp.getTextureStream(m[i].texture_name);
            }
            else {
                new_material.texture_id = null;
            }
            new_material.indices = m[i].indices;
            new_material.ulNumIndices = m[i].indices.length;
            materials.add(new_material);
        }
        this._materials = materials.toArray(new Material[materials.size()]);
        this._tex_array = this._ref_pmd_model.getUvArray();
    }
    
    public void setVmd(final MmdVmdMotion_BasicClass i_vmd_model) throws MmdException {
        super.setVmd(i_vmd_model);
    }
    
    protected void onUpdateSkinningMatrix(final MmdMatrix[] i_skinning_mat) throws MmdException {
        final MmdVector3[] org_pos_array = this._ref_pmd_model.getPositionArray();
        final MmdVector3[] org_normal_array = this._ref_pmd_model.getNormatArray();
        final PmdSkinInfo[] org_skin_info = this._ref_pmd_model.getSkinInfoArray();
        final int number_of_vertex = this._ref_pmd_model.getNumberOfVertex();
        final float[] ft = this._fbuf;
        int p1 = 0;
        int p2 = number_of_vertex * 3;
        for (int i = 0; i < this._ref_pmd_model.getNumberOfVertex(); ++i) {
            final PmdSkinInfo info_ptr = org_skin_info[i];
            MmdMatrix mat;
            if (info_ptr.fWeight == 0.0f) {
                mat = i_skinning_mat[info_ptr.unBoneNo_1];
            }
            else if (info_ptr.fWeight >= 0.9999f) {
                mat = i_skinning_mat[info_ptr.unBoneNo_0];
            }
            else {
                final MmdMatrix mat2 = i_skinning_mat[info_ptr.unBoneNo_0];
                final MmdMatrix mat3 = i_skinning_mat[info_ptr.unBoneNo_1];
                mat = this.__tmp_matrix;
                mat.MatrixLerp(mat2, mat3, info_ptr.fWeight);
            }
            MmdVector3 vp = org_pos_array[i];
            ft[p1++] = (float)(vp.x * mat.m00 + vp.y * mat.m10 + vp.z * mat.m20 + mat.m30);
            ft[p1++] = (float)(vp.x * mat.m01 + vp.y * mat.m11 + vp.z * mat.m21 + mat.m31);
            ft[p1++] = (float)(vp.x * mat.m02 + vp.y * mat.m12 + vp.z * mat.m22 + mat.m32);
            vp = org_normal_array[i];
            ft[p2++] = (float)(vp.x * mat.m00 + vp.y * mat.m10 + vp.z * mat.m20);
            ft[p2++] = (float)(vp.x * mat.m01 + vp.y * mat.m11 + vp.z * mat.m21);
            ft[p2++] = (float)(vp.x * mat.m02 + vp.y * mat.m12 + vp.z * mat.m22);
        }
    }
    
    public void render() {
        GL11.glPushAttrib(1048575);
        GL11.glPushClientAttrib(-1);
        GL11.glEnable(2884);
        GL11.glCullFace(1028);
        GL11.glEnable(2977);
        GL11.glShadeModel(7425);
        final BufferBuilder wr = Tessellator.getInstance().getBuilder();
        final int number_of_vertex = this._ref_pmd_model.getNumberOfVertex();
        for (int i = this._materials.length - 1; i >= 0; --i) {
            wr.begin(4, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
            final Material mt_ptr = this._materials[i];
            for (final int pos : mt_ptr.indices) {
                int npos = number_of_vertex * 3 + pos * 3;
                int vpos = pos * 3;
                wr.vertex((double)this._fbuf[vpos++], (double)this._fbuf[vpos++], (double)(-this._fbuf[vpos++])).uv(this._tex_array[pos].u, this._tex_array[pos].v).normal(this._fbuf[npos++], this._fbuf[npos++], this._fbuf[npos++]).color(1, 1, 1, 1).endVertex();
            }
            GL11.glEnable(2903);
            GL11.glColorMaterial(1032, 5634);
            GlStateManager._color4f(mt_ptr.color[0], mt_ptr.color[1], mt_ptr.color[2], mt_ptr.color[3]);
            if ((0x100 & mt_ptr.unknown) == 0x100) {
                GL11.glDisable(2884);
            }
            else {
                GL11.glEnable(2884);
            }
            if (mt_ptr.texture_id != null) {
                GL11.glEnable(3553);
                Minecraft.getInstance().getEntityRenderDispatcher().textureManager.bind(mt_ptr.texture_id);
            }
            else {
                GL11.glDisable(3553);
            }
            Tessellator.getInstance().end();
        }
        GL11.glPopClientAttrib();
        GL11.glPopAttrib();
    }
    
    private static FloatBuffer makeFloatBuffer(final int i_size) {
        final ByteBuffer bb = ByteBuffer.allocateDirect(i_size * 4);
        bb.order(ByteOrder.nativeOrder());
        final FloatBuffer fb = bb.asFloatBuffer();
        return fb;
    }
    
    private class Material
    {
        public float[] color;
        public float fShininess;
        public short[] indices;
        public int ulNumIndices;
        public ResourceLocation texture_id;
        public int unknown;
    }
}
