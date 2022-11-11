//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.client.renderer.model.obj;

import com.mojang.blaze3d.vertex.*;
import java.util.*;
import net.minecraftforge.api.distmarker.*;

public class GroupObject
{
    public String name;
    public ArrayList<Face> faces;
    public int glDrawingMode;
    
    public GroupObject() {
        this("");
    }
    
    public GroupObject(final String name) {
        this(name, -1);
    }
    
    public GroupObject(final String name, final int glDrawingMode) {
        this.faces = new ArrayList<Face>();
        this.name = name;
        this.glDrawingMode = glDrawingMode;
    }
    
    @OnlyIn(Dist.CLIENT)
    public void render(final IVertexBuilder tessellator) {
        if (this.faces.size() > 0) {
            for (final Face face : this.faces) {
                face.addFaceForRender(tessellator);
            }
        }
    }
}
