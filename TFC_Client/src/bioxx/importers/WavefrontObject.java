package bioxx.importers;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.src.ModLoader;
import net.minecraft.src.Tessellator;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class WavefrontObject
{
    public String objectName;
    public ArrayList vertices;
    public ArrayList normals;
    public ArrayList textureCoords;

    public ArrayList Groups;

    public WavefrontObject()
    {
        Groups = new ArrayList<FaceGroup>();
        vertices = new ArrayList<Vector3f>();
        normals = new ArrayList<Vector3f>();
        textureCoords = new ArrayList<Vector3f>();
    }

    public boolean draw(double x, double y, double z)
    {
        drawUV(x,y,z,0,0,16,16);
        return true;
    }
    public boolean drawUV(double x, double y, double z, double uMin, double vMin, double uMax, double vMax)
    {    
        Tessellator tessellator = Tessellator.instance;
        try
        {
            for(Iterator<FaceGroup> i = Groups.iterator(); i.hasNext();)
            {
                FaceGroup FG = i.next();
                for(Iterator<Face> g = FG.Faces.iterator(); g.hasNext();)
                {
                    Face F = g.next();

                    Vector3f v1 = (Vector3f) vertices.get(F.vertices[0][0]);
                    Vector3f vt1 = (Vector3f) textureCoords.get(F.vertices[0][1]);
                    Vector3f vn1 = (Vector3f) normals.get(F.vertices[0][2]);

                    Vector3f v2 = (Vector3f) vertices.get(F.vertices[1][0]);
                    Vector3f vt2 = (Vector3f) textureCoords.get(F.vertices[1][1]);
                    Vector3f vn2 = (Vector3f) normals.get(F.vertices[1][2]);

                    Vector3f v3 = (Vector3f) vertices.get(F.vertices[2][0]);
                    Vector3f vt3 = (Vector3f) textureCoords.get(F.vertices[2][1]);
                    Vector3f vn3 = (Vector3f) normals.get(F.vertices[2][2]);

//                    tessellator.addVertexWithUV(x+v1.x, y+v1.y, z+v1.z, uMin+(vt1.x*(uMax-uMin)), vMin+(vt1.y*(vMax-vMin)));
//
//                    tessellator.addVertexWithUV(x+v2.x, y+v2.y, z+v2.z, uMin+(vt2.x*(uMax-uMin)), vMin+(vt2.y*(vMax-vMin)));
//
//                    tessellator.addVertexWithUV(x+v3.x, y+v3.y, z+v3.z, uMin+(vt3.x*(uMax-uMin)), vMin+(vt3.y*(vMax-vMin)));
//
//                    if(F.vertCount == 4)
//                    {
//                        Vector3f v4 = (Vector3f) vertices.get(F.vertices[3][0]);
//                        Vector3f vt4 = (Vector3f) textureCoords.get(F.vertices[3][1]);
//                        Vector3f vn4 = (Vector3f) normals.get(F.vertices[3][2]);
//                        tessellator.addVertexWithUV(x+v4.x, y+v4.y, z+v4.z, uMin+(vt4.x*(uMax-uMin)), vMin+(vt4.y*(vMax-vMin)));
//
//                    }
//                    else
//                    {
//                        tessellator.addVertexWithUV(x+v3.x, y+v3.y, z+v3.z, uMin+(vt3.x*(uMax-uMin)), vMin+(vt3.y*(vMax-vMin)));
//
//                    }
                    tessellator.addVertexWithUV(x+v1.x, y+v1.y, z+v1.z, uMin+vt1.x, vMin+vt1.y);

                    tessellator.addVertexWithUV(x+v2.x, y+v2.y, z+v2.z, uMin+vt2.x, vMin+vt2.y);

                    tessellator.addVertexWithUV(x+v3.x, y+v3.y, z+v3.z, uMin+vt3.x, vMin+vt3.y);

                    if(F.vertCount == 4)
                    {
                        Vector3f v4 = (Vector3f) vertices.get(F.vertices[3][0]);
                        Vector3f vt4 = (Vector3f) textureCoords.get(F.vertices[3][1]);
                        Vector3f vn4 = (Vector3f) normals.get(F.vertices[3][2]);
                        tessellator.addVertexWithUV(x+v4.x, y+v4.y, z+v4.z, uMin+vt4.x, vMin+vt4.y);

                    }
                    else
                    {
                        tessellator.addVertexWithUV(x+v3.x, y+v3.y, z+v3.z, uMin+vt3.x, vMin+vt3.y);

                    }
                }
            }
        }
        catch(Exception Ex)
        {
            System.out.println(Ex.getMessage());
        }
        return true;
    }

    public WavefrontObject parse(String file)
    {
        try
        {
            //FileInputStream fstream = new FileInputStream(ModLoader.getMinecraftInstance().getMinecraftDir()+file);
            InputStream is = ModLoader.getMinecraftInstance().renderEngine.texturePack.selectedTexturePack.getResourceAsStream(file);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(is);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            FaceGroup grp = null;

            while ((strLine = br.readLine()) != null)   
            {
                strLine = strLine.replace("  ", " ");
                String[] line = strLine.split(" ");
                if(line[0].equalsIgnoreCase("v"))
                {
                    vertices.add(new Vector3f(Float.valueOf(line[1]),Float.valueOf(line[2]),Float.valueOf(line[3])));
                }
                else if(line[0].equalsIgnoreCase("vn"))
                {
                    normals.add(new Vector3f(Float.valueOf(line[1]),Float.valueOf(line[2]),Float.valueOf(line[3])));
                }
                else if(line[0].equalsIgnoreCase("vt"))
                {
                    float f1 = Float.valueOf(line[1]);if(f1 > 1 || f1 < 0) f1 = 0;
                    float f2 = Float.valueOf(line[2]);if(f2 > 1 || f2 < 0) f2 = 0;
                    float f3 = Float.valueOf(line[3]);if(f3 > 1 || f3 < 0) f3 = 0;
                    textureCoords.add(new Vector3f(f1,f2,f3));
                }
                else if(line[0].equalsIgnoreCase("g"))
                {
                    if(grp != null && grp.Faces.size() > 0)
                        Groups.add(grp);

                    grp = new FaceGroup(line[1]);
                }
                else if(line[0].equalsIgnoreCase("f"))
                {
                    String[] sections = line[1].split("/");
                    int[] v1 = new int[]{Integer.valueOf(sections[0])-1,Integer.valueOf(sections[1])-1,Integer.valueOf(sections[2])-1};
                    sections = line[2].split("/");
                    int[] v2 = new int[]{Integer.valueOf(sections[0])-1,Integer.valueOf(sections[1])-1,Integer.valueOf(sections[2])-1};
                    sections = line[3].split("/");
                    int[] v3 = new int[]{Integer.valueOf(sections[0])-1,Integer.valueOf(sections[1])-1,Integer.valueOf(sections[2])-1};
                    if(line.length > 4)
                    {
                        sections = line[4].split("/");
                        int[] v4 = new int[]{Integer.valueOf(sections[0])-1,Integer.valueOf(sections[1])-1,Integer.valueOf(sections[2])-1};
                        grp.Faces.add(new Face(v1,v2,v3,v4));
                    }
                    else
                    {
                        grp.Faces.add(new Face(v1,v2,v3));
                    }


                }
            }
            Groups.add(grp);
            in.close();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return this;
    }
}
