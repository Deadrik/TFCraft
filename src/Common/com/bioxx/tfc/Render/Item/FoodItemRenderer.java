package com.bioxx.tfc.Render.Item;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Food.Food;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.Interfaces.IFood;

public class FoodItemRenderer implements IItemRenderer
{

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) 
	{
		if(type == ItemRenderType.INVENTORY)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack is, Object... data) 
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_BLEND);
		renderIcon(0, 0, is.getItem().getIconIndex(is), 16, 16);
		if(is.getItem() instanceof IFood && is.hasTagCompound())
		{
			float decayPerc = Math.max(((IFood)is.getItem()).getFoodDecay(is) / ((IFood)is.getItem()).getFoodWeight(is), 0);
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
				if(((IFood)is.getItem()).renderDecay())
				{
					if(decayPerc < 0.10)
					{
						decayTop = (decayTop*10);
						renderQuad(1, 13, 13-decayTop, 1, 0x00ff00);
					}
					else
						renderQuad(1, 13, 13-decayTop, 1, 0xff0000);
				}
				if(((IFood)is.getItem()).renderWeight())
				{
					renderQuad(1, 14, 13, 1, 0);
					float weightPerc = ((IFood)is.getItem()).getFoodWeight(is) / ((IFood)is.getItem()).getFoodMaxWeight(is);
					float weightTop = weightPerc * 13.0F;

					renderQuad(1, 14, weightTop, 1, 0xffffff);
				}
			}
		}
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public static void renderIcon(int x, int y, IIcon icon, int sizeX, int sizeY)
	{
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double)(x + 0), (double)(y + sizeY), (double)200, (double)icon.getMinU(), (double)icon.getMaxV());
		tessellator.addVertexWithUV((double)(x + sizeX), (double)(y + sizeY), (double)200, (double)icon.getMaxU(), (double)icon.getMaxV());
		tessellator.addVertexWithUV((double)(x + sizeX), (double)(y + 0), (double)200, (double)icon.getMaxU(), (double)icon.getMinV());
		tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)200, (double)icon.getMinU(), (double)icon.getMinV());
		tessellator.draw();
	}

	private static void renderQuad(double x, double y, double sizeX, double sizeY, int color)
	{
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setColorOpaque_I(color);
		tess.addVertex((double)(x + 0), (double)(y + 0), 200.0D);
		tess.addVertex((double)(x + 0), (double)(y + sizeY), 200.0D);
		tess.addVertex((double)(x + sizeX), (double)(y + sizeY), 200.0D);
		tess.addVertex((double)(x + sizeX), (double)(y + 0), 200.0D);
		tess.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

}
