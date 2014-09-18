package com.bioxx.tfc.Render.Models;

import net.minecraft.client.model.ModelBase;

public class ModelCookingPot extends ModelBase
{
	public ModelRendererTFC renderer;
	public ModelCookingPot(){
		renderer = new ModelRendererTFC(this,0,0);
		float x = 8;
		float y = 8;
		float z = 8;
		int baseHeight = 4;
		int baseWidth = 7;
		int baseDepth = 15;
		Object[] basicVesselData = new Object[]{
				new float[]{0.5F + x, y, z + 0.5f,8,0+0.01f,8,8},
				new float[]{0.5F + x, y, z + 0.5f,8,2,8,18},
				new float[]{0.5F + x, y, z + 0.5f,8,4,8,20},
				new float[]{0.5F + x, y, z + 0.5f,8,7,8,20},
				new float[]{0.5F + x, y, z + 0.5f,8,10,8,18},
				new float[]{0.5F + x, y, z + 0.5f,8,12,8,14},
				new float[]{0.5F + x, y, z + 0.5f,8,14,8,14},
				new float[]{0.5F + x, y, z + 0.5f,8,14,8,12}
		};
		renderer.cubeList.add(
				new ModelPotteryBase(renderer,renderer.textureOffsetX, renderer.textureOffsetY, 5.5F + x, y, z + 5.5f, baseWidth, baseHeight, baseDepth, 0f,basicVesselData,false));
	}
	public void renderPot()
	{
		renderer.render(0.0625F / 2F);
	}
}
