package com.bioxx.tfc.Render.Models;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.Tessellator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModelCrossedSquare extends ModelBox 
{
	/**
     * The (x,y,z) vertex positions and (u,v) texture coordinates for each of the 8 points on a cube
     */
	//private PositionTextureVertex[] vertexPositions;

    /** An array of 6 TexturedQuads, one for each face of a cube */
    private TexturedQuad[] quadList;
	
	public ModelCrossedSquare(ModelRenderer renderer, int textureOffsetX, int textureOffsetY,
			float originX, float originY, float originZ, int width, int height, int depth,
			float scale) {
		super(renderer, textureOffsetX, textureOffsetY, originX, originY, originZ, width, height, depth, scale);
		
		//this.vertexPositions = new PositionTextureVertex[8];
        this.quadList = new TexturedQuad[4];
		float maxX = originX + width;
		float maxY = originY + height;
		float maxZ = originZ + depth;
        originX -= scale;
        originY -= scale;
        originZ -= scale;
        maxX += scale;
        maxY += scale;
        maxZ += scale;
        
        PositionTextureVertex vert0 = new PositionTextureVertex(originX, originY, originZ, 0.0F, 0.0F);
        PositionTextureVertex vert1 = new PositionTextureVertex(maxX, originY, originZ, 0.0F, 8.0F);
        PositionTextureVertex vert2 = new PositionTextureVertex(maxX, maxY, originZ, 8.0F, 8.0F);
        PositionTextureVertex vert3 = new PositionTextureVertex(originX, maxY, originZ, 8.0F, 0.0F);
        PositionTextureVertex vert4 = new PositionTextureVertex(originX, originY, maxZ, 0.0F, 0.0F);
        PositionTextureVertex vert5 = new PositionTextureVertex(maxX, originY, maxZ, 0.0F, 8.0F);
        PositionTextureVertex vert6 = new PositionTextureVertex(maxX, maxY, maxZ, 8.0F, 8.0F);
        PositionTextureVertex vert7 = new PositionTextureVertex(originX, maxY, maxZ, 8.0F, 0.0F);
		/*this.vertexPositions[0] = vert0;
		this.vertexPositions[1] = vert1;
		this.vertexPositions[2] = vert2;
		this.vertexPositions[3] = vert3;
		this.vertexPositions[4] = vert4;
		this.vertexPositions[5] = vert5;
		this.vertexPositions[6] = vert6;
		this.vertexPositions[7] = vert7;*/
        
        
        this.quadList[0] = new TexturedQuad(new PositionTextureVertex[] {vert3, vert6, vert5, vert0}, 
        		textureOffsetX + depth, textureOffsetY + depth, textureOffsetX + depth + width, textureOffsetY + depth + height, renderer.textureWidth, renderer.textureHeight);
        this.quadList[1] = new TexturedQuad(new PositionTextureVertex[] {vert2, vert7, vert4, vert1}, 
        		textureOffsetX + depth + width + depth, textureOffsetY + depth, textureOffsetX + depth + width + depth + width, textureOffsetY + depth + height, renderer.textureWidth, renderer.textureHeight);
        this.quadList[2] = new TexturedQuad(new PositionTextureVertex[] {vert6, vert3, vert0, vert5}, 
        		textureOffsetX + depth, textureOffsetY + depth, textureOffsetX + depth + width, textureOffsetY + depth + height, renderer.textureWidth, renderer.textureHeight);
        this.quadList[3] = new TexturedQuad(new PositionTextureVertex[] {vert7, vert2, vert1, vert4}, 
        		textureOffsetX + depth + width + depth, textureOffsetY + depth, textureOffsetX + depth + width + depth + width, textureOffsetY + depth + height, renderer.textureWidth, renderer.textureHeight);
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
