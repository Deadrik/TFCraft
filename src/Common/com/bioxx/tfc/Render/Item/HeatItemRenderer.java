package com.bioxx.tfc.Render.Item;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemBloom;
import com.bioxx.tfc.Items.ItemIngot;
import com.bioxx.tfc.Items.ItemMetalSheet;
import com.bioxx.tfc.Items.ItemUnfinishedArmor;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Interfaces.IFood;

public class HeatItemRenderer implements IItemRenderer
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
		
		if(type == ItemRenderType.INVENTORY)
		{
			if(TFC_ItemHeat.HasTemp(is))
			{
				float meltTemp = TFC_ItemHeat.IsCookable(is);
				float temp = TFC_ItemHeat.GetTemp(is);
				if(temp > 0 && temp < meltTemp)
				{
					renderQuad(1, 1, 10, 1, 0);

					int color = 0;
					float tempValue = 0F;
					
					if(temp < 80)	// Warming
					{
						color = 0x400000;
						tempValue = 2;
						if(temp>(80 * 0.2))
							tempValue = 4;
						if(temp>(80 * 0.4))
							tempValue = 6;
						if(temp>(80 * 0.6))
							tempValue = 8;
						if(temp>(80 * 0.8))
							tempValue = 10;
					}
					else if(temp < 210)	// Hot
					{
						color = 0x600000;
						tempValue = 2;
						if(temp>80+((210-80) * 0.2))
							tempValue = 4;
						if(temp>80+((210-80) * 0.4))
							tempValue = 6;
						if(temp>80+((210-80) * 0.6))
							tempValue = 8;
						if(temp>80+((210-80) * 0.8))
							tempValue = 10;
					}
					else if(temp < 480)	// VeryHot
					{
						color = 0x800000;
						tempValue = 2;						
						if(temp>210+((480-210) * 0.2))
							tempValue = 4;
						if(temp>210+((480-210) * 0.4))
							tempValue = 6;
						if(temp>210+((480-210) * 0.6))
							tempValue = 8;
						if(temp>210+((480-210) * 0.8))
							tempValue = 10;
					}
					else if(temp < 580)	// FaintRed
					{
						color = 0xa00000;
						tempValue = 2;
						if(temp>480+((580-480) * 0.2))
							tempValue = 4;
						if(temp>480+((580-480) * 0.4))
							tempValue = 6;
						if(temp>480+((580-480) * 0.6))
							tempValue = 8;
						if(temp>480+((580-480) * 0.8))
							tempValue = 10;
					}
					else if(temp < 730)	// DarkRed
					{
						color = 0xc40000;
						tempValue = 2;
						if(temp>580+((730-580) * 0.2))
							tempValue = 4;
						if(temp>580+((730-580) * 0.4))
							tempValue = 6;
						if(temp>580+((730-580) * 0.6))
							tempValue = 8;
						if(temp>580+((730-580) * 0.8))
							tempValue = 10;
					}
					else if(temp < 930)	// BrightRed
					{
						color = 0xff5555;
						tempValue = 2;
						if(temp>730+((930-730) * 0.2))
							tempValue = 4;
						if(temp>730+((930-730) * 0.4))
							tempValue = 6;
						if(temp>730+((930-730) * 0.6))
							tempValue = 8;
						if(temp>730+((930-730) * 0.8))
							tempValue = 10;
					}
					else if(temp < 1100)	// Orange
					{
						color = 0xffaa00;
						tempValue = 2;
						if(temp>930+((1100-930) * 0.2))
							tempValue = 4;
						if(temp>930+((1100-930) * 0.4))
							tempValue = 6;
						if(temp>930+((1100-930) * 0.6))
							tempValue = 8;
						if(temp>930+((1100-930) * 0.8))
							tempValue = 10;
					}
					else if(temp < 1300)	// Yellow
					{
						color = 0xffff00;
						tempValue = 2;
						if(temp>1100+((1300-1100) * 0.2))
							tempValue = 4;
						if(temp>1100+((1300-1100) * 0.4))
							tempValue = 6;
						if(temp>1100+((1300-1100) * 0.6))
							tempValue = 8;
						if(temp>1100+((1300-1100) * 0.8))
							tempValue = 10;
					}
					else if(temp < 1400)	// YellowWhite
					{
						color = 0xffffc0;
						tempValue = 2;
						if(temp>1300+((1400-1300) * 0.2))
							tempValue = 4;
						if(temp>1300+((1400-1300) * 0.4))
							tempValue = 6;
						if(temp>1300+((1400-1300) * 0.6))
							tempValue = 8;
						if(temp>1300+((1400-1300) * 0.8))
							tempValue = 10;
					}
					else if(temp < 1500)	// White
					{
						color = 0xe5e5e5;
						tempValue = 2;
						if(temp>1400+((1500-1400) * 0.2))
							tempValue = 4;
						if(temp>1400+((1500-1400) * 0.4))
							tempValue = 6;
						if(temp>1400+((1500-1400) * 0.6))
							tempValue = 8;
						if(temp>1400+((1500-1400) * 0.8))
							tempValue = 10;
					}
					else // BrilliantWhite
					{
						color = 0xffffff;
						tempValue = 10;
					}

					if (tempValue < 0) tempValue = 3;
					if (tempValue > 10) tempValue = 7;
					renderQuad(1, 1, tempValue, 1, color);
				}
				else if (meltTemp > 0 && temp >= meltTemp)
				{
					renderQuad(1, 1, 10, 1, 0x0080ff);					
				}

				if(	is.getItem() instanceof ItemIngot || is.getItem() instanceof ItemMetalSheet ||
					is.getItem() instanceof ItemUnfinishedArmor || is.getItem() instanceof ItemBloom)
				{
					renderQuad(12, 1, 3, 1, 0);
					
					if(HeatRegistry.getInstance().isTemperatureWorkable(is))
					{
						renderQuad(12, 1, 1, 1, 0x00ff00);
					}
	
					if(HeatRegistry.getInstance().isTemperatureWeldable(is))
					{
						renderQuad(13, 1, 1, 1, 0xffaa00);
					}

					if(HeatRegistry.getInstance().isTemperatureDanger(is))
					{
						renderQuad(14, 1, 1, 1, 0xff0000);
					}
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
		tessellator.addVertexWithUV((double)(x + 0), (double)(y + sizeY), (double)0, (double)icon.getMinU(), (double)icon.getMaxV());
		tessellator.addVertexWithUV((double)(x + sizeX), (double)(y + sizeY), (double)0, (double)icon.getMaxU(), (double)icon.getMaxV());
		tessellator.addVertexWithUV((double)(x + sizeX), (double)(y + 0), (double)0, (double)icon.getMaxU(), (double)icon.getMinV());
		tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)0, (double)icon.getMinU(), (double)icon.getMinV());
		tessellator.draw();
	}

	private static void renderQuad(double x, double y, double sizeX, double sizeY, int color)
	{
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setColorOpaque_I(color);
		tess.addVertex((double)(x + 0), (double)(y + 0), 0.0D);
		tess.addVertex((double)(x + 0), (double)(y + sizeY), 0.0D);
		tess.addVertex((double)(x + sizeX), (double)(y + sizeY), 0.0D);
		tess.addVertex((double)(x + sizeX), (double)(y + 0), 0.0D);
		tess.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

}
