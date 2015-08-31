package com.bioxx.tfc.Render.Models;

import net.minecraft.client.model.ModelBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelCrossedSquares extends ModelBase
{
	public ModelRendererTFC renderer;

	public ModelCrossedSquares(int texOffx, int texOffy, int texWidth, int texHeight,int sqWidth,int sqHeight,int sqDepth)
	{
		this.renderer = new ModelRendererTFC(this,0,0);
		this.renderer.textureHeight = texHeight;
		this.renderer.textureWidth = texWidth;
		float x = 0;
		float y = 0;
		float z = 0;

		int squareHeight = sqWidth;
		int squareWidth = sqWidth;
		int squareDepth = sqDepth;
		renderer.cubeList.add(
				new ModelCrossedSquare(renderer,renderer.textureOffsetX, renderer.textureOffsetY,  x-(sqWidth-16)/2, y, z-(sqDepth-16)/2, squareWidth, squareHeight, squareDepth, 0f));				

	}

	public void renderSquares()
	{
		renderer.render(0.0625F / 1F);
	}
}
