//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.client.renderer.model.obj;

import java.util.regex.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.resources.*;
import java.io.*;
import com.mojang.blaze3d.vertex.*;
import java.util.*;
import net.minecraftforge.api.distmarker.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.vertex.*;

public class WavefrontObject
{
    public static VertexFormat POSITION_TEX_LMAP_COL_NORMAL;
    private static Pattern vertexPattern;
    private static Pattern vertexNormalPattern;
    private static Pattern textureCoordinatePattern;
    private static Pattern face_V_VT_VN_Pattern;
    private static Pattern face_V_VT_Pattern;
    private static Pattern face_V_VN_Pattern;
    private static Pattern face_V_Pattern;
    private static Pattern groupObjectPattern;
    private static Matcher vertexMatcher;
    private static Matcher vertexNormalMatcher;
    private static Matcher textureCoordinateMatcher;
    private static Matcher face_V_VT_VN_Matcher;
    private static Matcher face_V_VT_Matcher;
    private static Matcher face_V_VN_Matcher;
    private static Matcher face_V_Matcher;
    private static Matcher groupObjectMatcher;
    public ArrayList<Vertex> vertices;
    public ArrayList<Vertex> vertexNormals;
    public ArrayList<TextureCoordinate> textureCoordinates;
    public ArrayList<GroupObject> groupObjects;
    private GroupObject currentGroupObject;
    private String fileName;
    
    public WavefrontObject(final ResourceLocation resource) throws ModelFormatException {
        this.vertices = new ArrayList<Vertex>();
        this.vertexNormals = new ArrayList<Vertex>();
        this.textureCoordinates = new ArrayList<TextureCoordinate>();
        this.groupObjects = new ArrayList<GroupObject>();
        this.fileName = resource.toString();
        try {
            final IResource res = Minecraft.getInstance().getResourceManager().getResource(resource);
            this.loadObjModel(res.getInputStream());
        }
        catch (IOException e) {
            throw new ModelFormatException("IO Exception reading model format", (Throwable)e);
        }
    }
    
    public WavefrontObject(final String filename, final InputStream inputStream) throws ModelFormatException {
        this.vertices = new ArrayList<Vertex>();
        this.vertexNormals = new ArrayList<Vertex>();
        this.textureCoordinates = new ArrayList<TextureCoordinate>();
        this.groupObjects = new ArrayList<GroupObject>();
        this.fileName = filename;
        this.loadObjModel(inputStream);
    }
    
    private void loadObjModel(final InputStream inputStream) throws ModelFormatException {
        BufferedReader reader = null;
        String currentLine = null;
        int lineCount = 0;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((currentLine = reader.readLine()) != null) {
                ++lineCount;
                currentLine = currentLine.replaceAll("\\s+", " ").trim();
                if (!currentLine.startsWith("#")) {
                    if (currentLine.length() == 0) {
                        continue;
                    }
                    if (currentLine.startsWith("v ")) {
                        final Vertex vertex = this.parseVertex(currentLine, lineCount);
                        if (vertex == null) {
                            continue;
                        }
                        this.vertices.add(vertex);
                    }
                    else if (currentLine.startsWith("vn ")) {
                        final Vertex vertex = this.parseVertexNormal(currentLine, lineCount);
                        if (vertex == null) {
                            continue;
                        }
                        this.vertexNormals.add(vertex);
                    }
                    else if (currentLine.startsWith("vt ")) {
                        final TextureCoordinate textureCoordinate = this.parseTextureCoordinate(currentLine, lineCount);
                        if (textureCoordinate == null) {
                            continue;
                        }
                        this.textureCoordinates.add(textureCoordinate);
                    }
                    else if (currentLine.startsWith("f ")) {
                        if (this.currentGroupObject == null) {
                            this.currentGroupObject = new GroupObject("Default");
                        }
                        final Face face = this.parseFace(currentLine, lineCount);
                        if (face == null) {
                            continue;
                        }
                        this.currentGroupObject.faces.add(face);
                    }
                    else {
                        if (!(currentLine.startsWith("g ") | currentLine.startsWith("o "))) {
                            continue;
                        }
                        final GroupObject group = this.parseGroupObject(currentLine, lineCount);
                        if (group != null && this.currentGroupObject != null) {
                            this.groupObjects.add(this.currentGroupObject);
                        }
                        this.currentGroupObject = group;
                    }
                }
            }
            this.groupObjects.add(this.currentGroupObject);
        }
        catch (IOException e) {
            throw new ModelFormatException("IO Exception reading model format", (Throwable)e);
        }
        finally {
            try {
                reader.close();
            }
            catch (IOException ex) {}
            try {
                inputStream.close();
            }
            catch (IOException ex2) {}
        }
    }
    
    @OnlyIn(Dist.CLIENT)
    public void tessellateAll(final IVertexBuilder tessellator) {
        for (final GroupObject groupObject : this.groupObjects) {
            groupObject.render(tessellator);
        }
    }
    
    @OnlyIn(Dist.CLIENT)
    public void tessellateOnly(final IVertexBuilder tessellator, final String... groupNames) {
        for (final GroupObject groupObject : this.groupObjects) {
            for (final String groupName : groupNames) {
                if (groupName.equalsIgnoreCase(groupObject.name)) {
                    groupObject.render(tessellator);
                }
            }
        }
    }
    
    @OnlyIn(Dist.CLIENT)
    public void tessellatePart(final IVertexBuilder tessellator, final String partName) {
        for (final GroupObject groupObject : this.groupObjects) {
            if (partName.equalsIgnoreCase(groupObject.name)) {
                groupObject.render(tessellator);
            }
        }
    }
    
    @OnlyIn(Dist.CLIENT)
    public void tessellateAllExcept(final IVertexBuilder tessellator, final String... excludedGroupNames) {
        for (final GroupObject groupObject : this.groupObjects) {
            boolean exclude = false;
            for (final String excludedGroupName : excludedGroupNames) {
                if (excludedGroupName.equalsIgnoreCase(groupObject.name)) {
                    exclude = true;
                }
            }
            if (!exclude) {
                groupObject.render(tessellator);
            }
        }
    }
    
    private Vertex parseVertex(String line, final int lineCount) throws ModelFormatException {
        final Vertex vertex = null;
        if (isValidVertexLine(line)) {
            line = line.substring(line.indexOf(" ") + 1);
            final String[] tokens = line.split(" ");
            try {
                if (tokens.length == 2) {
                    return new Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]));
                }
                if (tokens.length == 3) {
                    return new Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
                }
            }
            catch (NumberFormatException e) {
                throw new ModelFormatException(String.format("Number formatting error at line %d", lineCount), (Throwable)e);
            }
            return vertex;
        }
        throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
    }
    
    private Vertex parseVertexNormal(String line, final int lineCount) throws ModelFormatException {
        final Vertex vertexNormal = null;
        if (isValidVertexNormalLine(line)) {
            line = line.substring(line.indexOf(" ") + 1);
            final String[] tokens = line.split(" ");
            try {
                if (tokens.length == 3) {
                    return new Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
                }
            }
            catch (NumberFormatException e) {
                throw new ModelFormatException(String.format("Number formatting error at line %d", lineCount), (Throwable)e);
            }
            return vertexNormal;
        }
        throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
    }
    
    private TextureCoordinate parseTextureCoordinate(String line, final int lineCount) throws ModelFormatException {
        final TextureCoordinate textureCoordinate = null;
        if (isValidTextureCoordinateLine(line)) {
            line = line.substring(line.indexOf(" ") + 1);
            final String[] tokens = line.split(" ");
            try {
                if (tokens.length == 2) {
                    return new TextureCoordinate(Float.parseFloat(tokens[0]), 1.0f - Float.parseFloat(tokens[1]));
                }
                if (tokens.length == 3) {
                    return new TextureCoordinate(Float.parseFloat(tokens[0]), 1.0f - Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
                }
            }
            catch (NumberFormatException e) {
                throw new ModelFormatException(String.format("Number formatting error at line %d", lineCount), (Throwable)e);
            }
            return textureCoordinate;
        }
        throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
    }
    
    private Face parseFace(final String line, final int lineCount) throws ModelFormatException {
        Face face = null;
        if (isValidFaceLine(line)) {
            face = new Face();
            final String trimmedLine = line.substring(line.indexOf(" ") + 1);
            final String[] tokens = trimmedLine.split(" ");
            String[] subTokens = null;
            if (tokens.length == 3) {
                if (this.currentGroupObject.glDrawingMode == -1) {
                    this.currentGroupObject.glDrawingMode = 4;
                }
                else if (this.currentGroupObject.glDrawingMode != 4) {
                    throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Invalid number of points for face (expected 4, found " + tokens.length + ")");
                }
            }
            else if (tokens.length == 4) {
                if (this.currentGroupObject.glDrawingMode == -1) {
                    this.currentGroupObject.glDrawingMode = 7;
                }
                else if (this.currentGroupObject.glDrawingMode != 7) {
                    throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Invalid number of points for face (expected 3, found " + tokens.length + ")");
                }
            }
            if (isValidFace_V_VT_VN_Line(line)) {
                face.vertices = new Vertex[tokens.length];
                face.textureCoordinates = new TextureCoordinate[tokens.length];
                face.vertexNormals = new Vertex[tokens.length];
                for (int i = 0; i < tokens.length; ++i) {
                    subTokens = tokens[i].split("/");
                    face.vertices[i] = this.vertices.get(Integer.parseInt(subTokens[0]) - 1);
                    face.textureCoordinates[i] = this.textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
                    face.vertexNormals[i] = this.vertexNormals.get(Integer.parseInt(subTokens[2]) - 1);
                }
                face.faceNormal = face.calculateFaceNormal();
            }
            else if (isValidFace_V_VT_Line(line)) {
                face.vertices = new Vertex[tokens.length];
                face.textureCoordinates = new TextureCoordinate[tokens.length];
                for (int i = 0; i < tokens.length; ++i) {
                    subTokens = tokens[i].split("/");
                    face.vertices[i] = this.vertices.get(Integer.parseInt(subTokens[0]) - 1);
                    face.textureCoordinates[i] = this.textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
                }
                face.faceNormal = face.calculateFaceNormal();
            }
            else if (isValidFace_V_VN_Line(line)) {
                face.vertices = new Vertex[tokens.length];
                face.vertexNormals = new Vertex[tokens.length];
                for (int i = 0; i < tokens.length; ++i) {
                    subTokens = tokens[i].split("//");
                    face.vertices[i] = this.vertices.get(Integer.parseInt(subTokens[0]) - 1);
                    face.vertexNormals[i] = this.vertexNormals.get(Integer.parseInt(subTokens[1]) - 1);
                }
                face.faceNormal = face.calculateFaceNormal();
            }
            else {
                if (!isValidFace_V_Line(line)) {
                    throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
                }
                face.vertices = new Vertex[tokens.length];
                for (int i = 0; i < tokens.length; ++i) {
                    face.vertices[i] = this.vertices.get(Integer.parseInt(tokens[i]) - 1);
                }
                face.faceNormal = face.calculateFaceNormal();
            }
            return face;
        }
        throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
    }
    
    private GroupObject parseGroupObject(final String line, final int lineCount) throws ModelFormatException {
        GroupObject group = null;
        if (isValidGroupObjectLine(line)) {
            final String trimmedLine = line.substring(line.indexOf(" ") + 1);
            if (trimmedLine.length() > 0) {
                group = new GroupObject(trimmedLine);
            }
            return group;
        }
        throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
    }
    
    private static boolean isValidVertexLine(final String line) {
        if (WavefrontObject.vertexMatcher != null) {
            WavefrontObject.vertexMatcher.reset();
        }
        WavefrontObject.vertexMatcher = WavefrontObject.vertexPattern.matcher(line);
        return WavefrontObject.vertexMatcher.matches();
    }
    
    private static boolean isValidVertexNormalLine(final String line) {
        if (WavefrontObject.vertexNormalMatcher != null) {
            WavefrontObject.vertexNormalMatcher.reset();
        }
        WavefrontObject.vertexNormalMatcher = WavefrontObject.vertexNormalPattern.matcher(line);
        return WavefrontObject.vertexNormalMatcher.matches();
    }
    
    private static boolean isValidTextureCoordinateLine(final String line) {
        if (WavefrontObject.textureCoordinateMatcher != null) {
            WavefrontObject.textureCoordinateMatcher.reset();
        }
        WavefrontObject.textureCoordinateMatcher = WavefrontObject.textureCoordinatePattern.matcher(line);
        return WavefrontObject.textureCoordinateMatcher.matches();
    }
    
    private static boolean isValidFace_V_VT_VN_Line(final String line) {
        if (WavefrontObject.face_V_VT_VN_Matcher != null) {
            WavefrontObject.face_V_VT_VN_Matcher.reset();
        }
        WavefrontObject.face_V_VT_VN_Matcher = WavefrontObject.face_V_VT_VN_Pattern.matcher(line);
        return WavefrontObject.face_V_VT_VN_Matcher.matches();
    }
    
    private static boolean isValidFace_V_VT_Line(final String line) {
        if (WavefrontObject.face_V_VT_Matcher != null) {
            WavefrontObject.face_V_VT_Matcher.reset();
        }
        WavefrontObject.face_V_VT_Matcher = WavefrontObject.face_V_VT_Pattern.matcher(line);
        return WavefrontObject.face_V_VT_Matcher.matches();
    }
    
    private static boolean isValidFace_V_VN_Line(final String line) {
        if (WavefrontObject.face_V_VN_Matcher != null) {
            WavefrontObject.face_V_VN_Matcher.reset();
        }
        WavefrontObject.face_V_VN_Matcher = WavefrontObject.face_V_VN_Pattern.matcher(line);
        return WavefrontObject.face_V_VN_Matcher.matches();
    }
    
    private static boolean isValidFace_V_Line(final String line) {
        if (WavefrontObject.face_V_Matcher != null) {
            WavefrontObject.face_V_Matcher.reset();
        }
        WavefrontObject.face_V_Matcher = WavefrontObject.face_V_Pattern.matcher(line);
        return WavefrontObject.face_V_Matcher.matches();
    }
    
    private static boolean isValidFaceLine(final String line) {
        return isValidFace_V_VT_VN_Line(line) || isValidFace_V_VT_Line(line) || isValidFace_V_VN_Line(line) || isValidFace_V_Line(line);
    }
    
    private static boolean isValidGroupObjectLine(final String line) {
        if (WavefrontObject.groupObjectMatcher != null) {
            WavefrontObject.groupObjectMatcher.reset();
        }
        WavefrontObject.groupObjectMatcher = WavefrontObject.groupObjectPattern.matcher(line);
        return WavefrontObject.groupObjectMatcher.matches();
    }
    
    public String getType() {
        return "obj";
    }
    
    static {
        WavefrontObject.POSITION_TEX_LMAP_COL_NORMAL = new VertexFormat(ImmutableList.builder().add((Object)DefaultVertexFormats.ELEMENT_POSITION).add((Object)DefaultVertexFormats.ELEMENT_UV0).add((Object)DefaultVertexFormats.ELEMENT_UV2).add((Object)DefaultVertexFormats.ELEMENT_COLOR).add((Object)DefaultVertexFormats.ELEMENT_NORMAL).add((Object)DefaultVertexFormats.ELEMENT_PADDING).build());
        WavefrontObject.vertexPattern = Pattern.compile("(v( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *\\n)|(v( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *$)");
        WavefrontObject.vertexNormalPattern = Pattern.compile("(vn( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *\\n)|(vn( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *$)");
        WavefrontObject.textureCoordinatePattern = Pattern.compile("(vt( (\\-){0,1}\\d+\\.\\d+){2,3} *\\n)|(vt( (\\-){0,1}\\d+(\\.\\d+)?){2,3} *$)");
        WavefrontObject.face_V_VT_VN_Pattern = Pattern.compile("(f( \\d+/\\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+/\\d+){3,4} *$)");
        WavefrontObject.face_V_VT_Pattern = Pattern.compile("(f( \\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+){3,4} *$)");
        WavefrontObject.face_V_VN_Pattern = Pattern.compile("(f( \\d+//\\d+){3,4} *\\n)|(f( \\d+//\\d+){3,4} *$)");
        WavefrontObject.face_V_Pattern = Pattern.compile("(f( \\d+){3,4} *\\n)|(f( \\d+){3,4} *$)");
        WavefrontObject.groupObjectPattern = Pattern.compile("([go]( [\\w\\d\\.]+) *\\n)|([go]( [\\w\\d\\.]+) *$)");
    }
}
