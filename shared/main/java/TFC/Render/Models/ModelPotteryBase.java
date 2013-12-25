package TFC.Render.Models;

import java.util.ArrayList;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.Tessellator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModelPotteryBase extends ModelBox 
{
	/**
	 * The (x,y,z) vertex positions and (u,v) texture coordinates for each of the 8 points on a cube
	 */
	private PositionTextureVertex[] vertexPositions;
	private Object[] rings;
	protected ArrayList<TexturedQuad> polygons;

	/** An array of 6 TexturedQuads, one for each face of a cube */
	private TexturedQuad[] quadList;

	public ModelPotteryBase(ModelRenderer renderer, int textureOffsetX, int textureOffsetY,
			float originX, float originY, float originZ, int width, int height, int depth,
			float scale) {
		super(renderer, textureOffsetX, textureOffsetY, originX, originY, originZ, width, height, depth, scale);
		/*
        this.vertexPositions = new PositionTextureVertex[8];
        this.quadList = new TexturedQuad[6];
        float maxX = originX + (float)width;
        float maxY = originY + (float)height;
        float maxZ = originZ + (float)depth;
        originX -= scale;
        originY -= scale;
        originZ -= scale;
        maxX += scale;
        maxY += scale;
        maxZ += scale;

        PositionTextureVertex vert0 = new PositionTextureVertex(originX, originY, originZ, 0.0F, 0.0F);
        PositionTextureVertex vert1 = new PositionTextureVertex(maxX, originY, originZ, 0.0F, 8.0F);
        PositionTextureVertex vert2 = new PositionTextureVertex(maxX-1, maxY, originZ+1, 8.0F, 8.0F);
        PositionTextureVertex vert3 = new PositionTextureVertex(originX+1, maxY, originZ+1, 8.0F, 0.0F);
        PositionTextureVertex vert4 = new PositionTextureVertex(originX, originY, maxZ, 0.0F, 0.0F);
        PositionTextureVertex vert5 = new PositionTextureVertex(maxX, originY, maxZ, 0.0F, 8.0F);
        PositionTextureVertex vert6 = new PositionTextureVertex(maxX-1, maxY, maxZ-1, 8.0F, 8.0F);
        PositionTextureVertex vert7 = new PositionTextureVertex(originX+1, maxY, maxZ-1, 8.0F, 0.0F);
        this.vertexPositions[0] = vert0;
        this.vertexPositions[1] = vert1;
        this.vertexPositions[2] = vert2;
        this.vertexPositions[3] = vert3;
        this.vertexPositions[4] = vert4;
        this.vertexPositions[5] = vert5;
        this.vertexPositions[6] = vert6;
        this.vertexPositions[7] = vert7;

        this.quadList[0] = new TexturedQuad(new PositionTextureVertex[] {vert5, vert1, vert2, vert6}, 
        		textureOffsetX + depth + width, textureOffsetY + depth, textureOffsetX + depth + width + depth, textureOffsetY + depth + height, renderer.textureWidth, renderer.textureHeight);
        this.quadList[1] = new TexturedQuad(new PositionTextureVertex[] {vert0, vert4, vert7, vert3}, 
        		textureOffsetX, textureOffsetY + depth, textureOffsetX + depth, textureOffsetY + depth + height, renderer.textureWidth, renderer.textureHeight);
        this.quadList[2] = new TexturedQuad(new PositionTextureVertex[] {vert5, vert4, vert0, vert1}, 
        		textureOffsetX + depth, textureOffsetY, textureOffsetX + depth + width, textureOffsetY + depth, renderer.textureWidth, renderer.textureHeight);
        this.quadList[3] = new TexturedQuad(new PositionTextureVertex[] {vert2, vert3, vert7, vert6}, 
        		textureOffsetX + depth + width, textureOffsetY + depth, textureOffsetX + depth + width + width, textureOffsetY, renderer.textureWidth, renderer.textureHeight);
        this.quadList[4] = new TexturedQuad(new PositionTextureVertex[] {vert1, vert0, vert3, vert2}, 
        		textureOffsetX + depth, textureOffsetY + depth, textureOffsetX + depth + width, textureOffsetY + depth + height, renderer.textureWidth, renderer.textureHeight);
        this.quadList[5] = new TexturedQuad(new PositionTextureVertex[] {vert4, vert5, vert6, vert7}, 
        		textureOffsetX + depth + width + depth, textureOffsetY + depth, textureOffsetX + depth + width + depth + width, textureOffsetY + depth + height, renderer.textureWidth, renderer.textureHeight);
		 */
		rings = new Object[7];
		
		rings[0] = newRing(originX,originY,originZ,8,0,8,8);
		rings[1] = newRing(originX,originY,originZ,8,3,8,12);
		rings[2] = newRing(originX,originY,originZ,8,6,8,14);
		rings[3] = newRing(originX,originY,originZ,8,9,8,14);
		rings[4] = newRing(originX,originY,originZ,8,12,8,12);
		rings[5] = newRing(originX,originY,originZ,8,14,8,6);
		rings[6] = newRing(originX,originY,originZ,8,16,8,6);

		polygons = buildSides(rings,renderer, textureOffsetX,  textureOffsetY,
			 originX,  originY, originZ,  width,  height,  depth,
			 scale);
		
	}

	public PositionTextureVertex [] newRing(float originX,float originY,float originZ,float offsetX,float offsetY,float offsetZ,float width){
		PositionTextureVertex[] vert = new PositionTextureVertex[8];
		vert[0] = new PositionTextureVertex(originX+offsetX-(width/4f),originY+offsetY,originZ+offsetZ-(width/2f),0,0);
		vert[1] = new PositionTextureVertex(originX+offsetX+(width/4f),originY+offsetY,originZ+offsetZ-(width/2f),0,0);
		vert[2] = new PositionTextureVertex(originX+offsetX+(width/2f),originY+offsetY,originZ+offsetZ-(width/4f),0,0);
		vert[3] = new PositionTextureVertex(originX+offsetX+(width/2f),originY+offsetY,originZ+offsetZ+(width/4f),0,0);
		vert[4] = new PositionTextureVertex(originX+offsetX+(width/4f),originY+offsetY,originZ+offsetZ+(width/2f),0,0);
		vert[5] = new PositionTextureVertex(originX+offsetX-(width/4f),originY+offsetY,originZ+offsetZ+(width/2f),0,0);
		vert[6] = new PositionTextureVertex(originX+offsetX-(width/2f),originY+offsetY,originZ+offsetZ+(width/4f),0,0);
		vert[7] = new PositionTextureVertex(originX+offsetX-(width/2f),originY+offsetY,originZ+offsetZ-(width/4f),0,0);
		return vert;
	}

	public ArrayList<TexturedQuad> buildSides(Object[] vertices,ModelRenderer renderer, int textureOffsetX, int textureOffsetY,
			float originX, float originY, float originZ, int width, int height, int depth,
			float scale){

		ArrayList<TexturedQuad> aList = new ArrayList();
		for(int i = 0; i < vertices.length-1;i++){
			for(int j = 0; j < 8; j++){
				aList.add(new TexturedQuad(new PositionTextureVertex[] {((PositionTextureVertex [])(vertices[i+1]))[j],((PositionTextureVertex [])(vertices[i+1]))[(j+1)%8], ((PositionTextureVertex [])(vertices[i]))[(j+1)%8], ((PositionTextureVertex [])(vertices[i]))[j]}, 
						textureOffsetX + depth + width, textureOffsetY + depth, textureOffsetX + depth + width + depth, textureOffsetY + depth + height, renderer.textureWidth, renderer.textureHeight));
			}
		}

		return aList;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void render(Tessellator par1Tessellator, float par2)
	{
		/*for (int var3 = 0; var3 < this.quadList.length; ++var3)
		{
			this.quadList[var3].draw(par1Tessellator, par2);
		}*/
		for (TexturedQuad quad : polygons){
			quad.draw(par1Tessellator, par2);
		}
	}

}
