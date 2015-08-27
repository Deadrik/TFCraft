package com.bioxx.tfc.Render.Models;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.Tessellator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModelIngot extends ModelBox 
{
	/**
     * The (x,y,z) vertex positions and (u,v) texture coordinates for each of the 8 points on a cube
     */
	//private PositionTextureVertex[] vertexPositions;

    /** An array of 6 TexturedQuads, one for each face of a cube */
    private TexturedQuad[] quadList;
	
	public ModelIngot(ModelRenderer renderer, int textureOffsetX, int textureOffsetY) {
		super(renderer, textureOffsetX, textureOffsetY, 0.5F, 0, 0.5F, 15, 4, 7, 0);
		
		float originX = .5f;
		float originY = 0;
		float originZ = .5f;
		
		//this.vertexPositions = new PositionTextureVertex[8];
        this.quadList = new TexturedQuad[6];
        float maxX = originX + 15;
        float maxY = originY + 4;
        float maxZ = originZ + 7;
        
        PositionTextureVertex vert0 = new PositionTextureVertex(originX, originY, originZ, 0.0F, 0.0F);
        PositionTextureVertex vert1 = new PositionTextureVertex(maxX, originY, originZ, 0.0F, 8.0F);
        PositionTextureVertex vert2 = new PositionTextureVertex(maxX-1, maxY, originZ+1, 8.0F, 8.0F);
        PositionTextureVertex vert3 = new PositionTextureVertex(originX+1, maxY, originZ+1, 8.0F, 0.0F);
        PositionTextureVertex vert4 = new PositionTextureVertex(originX, originY, maxZ, 0.0F, 0.0F);
        PositionTextureVertex vert5 = new PositionTextureVertex(maxX, originY, maxZ, 0.0F, 8.0F);
        PositionTextureVertex vert6 = new PositionTextureVertex(maxX-1, maxY, maxZ-1, 8.0F, 8.0F);
        PositionTextureVertex vert7 = new PositionTextureVertex(originX+1, maxY, maxZ-1, 8.0F, 0.0F);
		/*this.vertexPositions[0] = vert0;
		this.vertexPositions[1] = vert1;
		this.vertexPositions[2] = vert2;
		this.vertexPositions[3] = vert3;
		this.vertexPositions[4] = vert4;
		this.vertexPositions[5] = vert5;
		this.vertexPositions[6] = vert6;
		this.vertexPositions[7] = vert7;*/
        
        int x1 = textureOffsetX + 4;
        int x2 = textureOffsetX + 20;
        int x3 = textureOffsetX + 44;
        int x4 = textureOffsetX + 60;
            
        int y1 = textureOffsetY + 4;
        int y2 = textureOffsetY + 8;
        int y3 = textureOffsetY + 16;
        int y4 = textureOffsetY + 20;
        int y5 = textureOffsetY + 28;
        
        this.quadList[0] = new TexturedQuad(new PositionTextureVertex[] {vert5, vert1, vert2, vert6}, 
        		x3, y1, x4, y2, renderer.textureWidth, renderer.textureHeight); // petit
        this.quadList[1] = new TexturedQuad(new PositionTextureVertex[] {vert0, vert4, vert7, vert3}, 
        		x1, y1, x2, y2, renderer.textureWidth, renderer.textureHeight); // petit
        this.quadList[2] = new TexturedQuad(new PositionTextureVertex[] {vert5, vert4, vert0, vert1}, 
        		x2, y4, x3, y5, renderer.textureWidth, renderer.textureHeight); // bottom
        this.quadList[3] = new TexturedQuad(new PositionTextureVertex[] {vert2, vert3, vert7, vert6}, 
        		x2, y2, x3, y3, renderer.textureWidth, renderer.textureHeight); // top
        this.quadList[4] = new TexturedQuad(new PositionTextureVertex[] {vert1, vert0, vert3, vert2}, 
        		x2, y1, x3, y2, renderer.textureWidth, renderer.textureHeight); // long
        this.quadList[5] = new TexturedQuad(new PositionTextureVertex[] {vert4, vert5, vert6, vert7}, 
        		x3, y4, x2, y3, renderer.textureWidth, renderer.textureHeight); // long
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void render(Tessellator par1Tessellator, float par2)
    {
        for (int var3 = 0; var3 < this.quadList.length; ++var3)
        {
            this.quadList[var3].draw(par1Tessellator, par2);
        }
    }

}
