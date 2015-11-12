package com.bioxx.tfc.Render.Item;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Interfaces.IFood;

public class FoodItemRenderer implements IItemRenderer
{

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) 
	{
		return type == ItemRenderType.INVENTORY;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack is, Object... data) 
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		if(is.getItem() instanceof IFood && is.hasTagCompound())
		{			
			renderIcon(0, 0, is.getItem().getIconIndex(is), 16, 16);
			float decayPerc = Math.max(Food.getDecay(is) / Food.getWeight(is), 0);
			float cookPerc = Math.max(Math.min(Food.getCooked(is)/600f, 1), 0);
			if(is.getItem() instanceof ItemFoodTFC)
			{
				int color = Food.getCookedColorMultiplier(is);
				GL11.glColor4f(((color & 0xFF0000)>>16)/255f, ((color & 0x00ff00)>>8)/255f, (color & 0x0000ff)/255f, cookPerc);
				if(((ItemFoodTFC)is.getItem()).cookedIcon != null)
					renderIcon(0, 0,((ItemFoodTFC)is.getItem()).cookedIcon, 16, 16);
				else
					renderIcon(0, 0, is.getItem().getIconIndex(is), 16, 16);
			}
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			float decayTop = decayPerc * 13.0F;

			if(type == ItemRenderType.INVENTORY)
			{
				if(TFC_ItemHeat.hasTemp(is))
				{
					float meltTemp = TFC_ItemHeat.isCookable(is);
					float temp = TFC_ItemHeat.getTemp(is);
					if(temp > 0 && temp < meltTemp)
					{
						renderQuad(1, 1, 13, 1, 0);
						
						float tempValue = (13 / meltTemp) * temp;
						if (tempValue < 0) tempValue = 0;
						if (tempValue > 13) tempValue = 13;
						
						if (temp < meltTemp*0.1F)	// Cold
							renderQuad(1, 1, tempValue, 1, 0xffffff);						
						else if (temp < meltTemp*0.4F)	// Warm
							renderQuad(1, 1, tempValue, 1, 0xff8000);						
						else if (temp < meltTemp*0.8F)	// Hot
							renderQuad(1, 1, tempValue, 1, 0xff6000);						
						else	// VeryHot
							renderQuad(1, 1, tempValue, 1, 0xff0000);		
					}
				}
			
				float weightPerc = Food.getWeight(is) / ((IFood) is.getItem()).getFoodMaxWeight(is);

				if (weightPerc <= 1) // Only draw bars if the weight is within the max weight. Food created using the blank createTag (weight = 999) will not have the bars.
				{
					if (((IFood) is.getItem()).renderDecay())
					{
						if (decayPerc < 0.10)
						{
							decayTop = decayTop * 10;
							renderQuad(1, 13, 13 - decayTop, 1, 0x00ff00);
						}
						else
							renderQuad(1, 13, 13 - decayTop, 1, 0xff0000);
					}
					if (((IFood) is.getItem()).renderWeight())
					{
						renderQuad(1, 14, 13, 1, 0);
						float weightTop = weightPerc * 13.0F;

						renderQuad(1, 14, weightTop, 1, 0xffffff);
					}
				}
			}

		}
		else if(is.getItem() instanceof IFood)
		{
			renderIcon(0, 0, is.getItem().getIconIndex(is), 16, 16);
		}

		GL11.glPopAttrib();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public static void renderIcon(int x, int y, IIcon icon, int sizeX, int sizeY)
	{
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0, y + sizeY, 0, icon.getMinU(), icon.getMaxV());
		tessellator.addVertexWithUV(x + sizeX, y + sizeY, 0, icon.getMaxU(), icon.getMaxV());
		tessellator.addVertexWithUV(x + sizeX, y + 0, 0, icon.getMaxU(), icon.getMinV());
		tessellator.addVertexWithUV(x + 0, y + 0, 0, icon.getMinU(), icon.getMinV());
		tessellator.draw();
	}

	private static void renderQuad(double x, double y, double sizeX, double sizeY, int color)
	{
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setColorOpaque_I(color);
		tess.addVertex(x + 0, y + 0, 0.0D);
		tess.addVertex(x + 0, y + sizeY, 0.0D);
		tess.addVertex(x + sizeX, y + sizeY, 0.0D);
		tess.addVertex(x + sizeX, y + 0, 0.0D);
		tess.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

}
