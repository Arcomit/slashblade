//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.client.renderer.model.obj;

import java.util.function.*;
import java.awt.*;
import net.minecraft.util.*;
import com.mojang.blaze3d.matrix.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraft.util.math.vector.*;

public class Face
{
    public static boolean isSmoothShade;
    public static int lightmap;
    public static final BiFunction<Vector4f, Integer, Integer> alphaNoOverride;
    public static final BiFunction<Vector4f, Integer, Integer> alphaOverrideYZZ;
    public static BiFunction<Vector4f, Integer, Integer> alphaOverride;
    public static final Vector4f uvDefaultOperator;
    public static Vector4f uvOperator;
    public static Color col;
    private static final LazyValue<Matrix4f> defaultTransform;
    public static MatrixStack matrix;
    public Vertex[] vertices;
    public Vertex[] vertexNormals;
    public Vertex faceNormal;
    public TextureCoordinate[] textureCoordinates;
    
    public static void setLightMap(final int value) {
        Face.lightmap = value;
    }
    
    public static void resetLightMap() {
        Face.lightmap = 15;
    }
    
    public static void setAlphaOverride(final BiFunction<Vector4f, Integer, Integer> alphaOverride) {
        Face.alphaOverride = alphaOverride;
    }
    
    public static void resetAlphaOverride() {
        Face.alphaOverride = Face.alphaNoOverride;
    }
    
    public static void setUvOperator(final float uScale, final float vScale, final float uOffset, final float vOffset) {
        Face.uvOperator = new Vector4f(uScale, vScale, uOffset, vOffset);
    }
    
    public static void resetUvOperator() {
        Face.uvOperator = Face.uvDefaultOperator;
    }
    
    public static void setCol(final Color col) {
        Face.col = col;
    }
    
    public static void resetCol() {
        Face.col = Color.white;
    }
    
    public static void setMatrix(final MatrixStack ms) {
        Face.matrix = ms;
    }
    
    public static void resetMatrix() {
        Face.matrix = null;
    }
    
    @OnlyIn(Dist.CLIENT)
    public void addFaceForRender(final IVertexBuilder tessellator) {
        this.addFaceForRender(tessellator, 5.0E-4f);
    }
    
    @OnlyIn(Dist.CLIENT)
    public void addFaceForRender(final IVertexBuilder tessellator, final float textureOffset) {
        if (this.faceNormal == null) {
            this.faceNormal = this.calculateFaceNormal();
        }
        float averageU = 0.0f;
        float averageV = 0.0f;
        if (this.textureCoordinates != null && this.textureCoordinates.length > 0) {
            for (int i = 0; i < this.textureCoordinates.length; ++i) {
                averageU += this.textureCoordinates[i].u * Face.uvOperator.x() + Face.uvOperator.z();
                averageV += this.textureCoordinates[i].v * Face.uvOperator.y() + Face.uvOperator.w();
            }
            averageU /= this.textureCoordinates.length;
            averageV /= this.textureCoordinates.length;
        }
        final IVertexBuilder wr = tessellator;
        Matrix4f transform;
        if (Face.matrix != null) {
            final MatrixStack.Entry me = Face.matrix.last();
            transform = me.pose();
        }
        else {
            transform = (Matrix4f)Face.defaultTransform.get();
        }
        for (int j = 0; j < this.vertices.length; ++j) {
            final Vector4f vector4f = new Vector4f(this.vertices[j].x, this.vertices[j].y, this.vertices[j].z, 1.0f);
            vector4f.transform(transform);
            wr.vertex((double)vector4f.x(), (double)vector4f.y(), (double)vector4f.z());
            if (this.textureCoordinates != null && this.textureCoordinates.length > 0) {
                float offsetU = textureOffset;
                float offsetV = textureOffset;
                final float textureU = this.textureCoordinates[j].u * Face.uvOperator.x() + Face.uvOperator.z();
                final float textureV = this.textureCoordinates[j].v * Face.uvOperator.y() + Face.uvOperator.w();
                if (textureU > averageU) {
                    offsetU = -offsetU;
                }
                if (textureV > averageV) {
                    offsetV = -offsetV;
                }
                wr.uv(textureU + offsetU, textureV + offsetV);
            }
            else {
                wr.uv(0.0f, 0.0f);
            }
            wr.uv2(Face.lightmap);
            wr.color(Face.col.getRed(), Face.col.getGreen(), Face.col.getBlue(), (int)Face.alphaOverride.apply(new Vector4f(this.vertices[j].x, this.vertices[j].y, this.vertices[j].z, 1.0f), Face.col.getAlpha()));
            Vector3f vector3f;
            if (Face.isSmoothShade && this.vertexNormals != null) {
                final Vertex normal = this.vertexNormals[j];
                final Vector3d nol = new Vector3d((double)normal.x, (double)normal.y, (double)normal.z);
                vector3f = new Vector3f((float)nol.x, (float)nol.y, (float)nol.z);
            }
            else {
                vector3f = new Vector3f(this.faceNormal.x, this.faceNormal.y, this.faceNormal.z);
            }
            vector3f.transform(new Matrix3f(transform));
            vector3f.normalize();
            wr.normal(vector3f.x(), vector3f.y(), vector3f.z());
            wr.endVertex();
        }
    }
    
    public Vertex calculateFaceNormal() {
        final Vector3d v1 = new Vector3d((double)(this.vertices[1].x - this.vertices[0].x), (double)(this.vertices[1].y - this.vertices[0].y), (double)(this.vertices[1].z - this.vertices[0].z));
        final Vector3d v2 = new Vector3d((double)(this.vertices[2].x - this.vertices[0].x), (double)(this.vertices[2].y - this.vertices[0].y), (double)(this.vertices[2].z - this.vertices[0].z));
        Vector3d normalVector = null;
        normalVector = v1.cross(v2).normalize();
        return new Vertex((float)normalVector.x, (float)normalVector.y, (float)normalVector.z);
    }
    
    static {
        Face.isSmoothShade = true;
        Face.lightmap = 15;
        alphaNoOverride = ((v, a) -> a);
        alphaOverrideYZZ = ((v, a) -> (v.y() == 0.0f) ? 0 : a);
        Face.alphaOverride = Face.alphaNoOverride;
        uvDefaultOperator = new Vector4f(1.0f, 1.0f, 0.0f, 0.0f);
        Face.uvOperator = Face.uvDefaultOperator;
        final Matrix4f m;
        defaultTransform = new LazyValue(() -> {
            m = new Matrix4f();
            m.setIdentity();
            return m;
        });
        Face.matrix = null;
    }
}
