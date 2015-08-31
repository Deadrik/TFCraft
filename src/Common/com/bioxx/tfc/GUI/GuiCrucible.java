package com.bioxx.tfc.GUI;

import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerCrucible;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TECrucible;
import com.bioxx.tfc.api.TFCOptions;

public class GuiCrucible extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_crucible.png");

	private TECrucible crucibleTE;

	public GuiCrucible(InventoryPlayer inventoryplayer, TECrucible te, World world, int x, int y, int z)
	{
		super(new ContainerCrucible(inventoryplayer, te, world, x, y, z), 176, 113);
		crucibleTE = te;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(texture);
	}

	@Override
	protected void drawForeground(int guiLeft, int guiTop)
	{
		int scale = 0;

		scale = crucibleTE.getTemperatureScaled(49);
		drawTexturedModalRect(guiLeft + 153, guiTop + 80 - scale, 185, 0, 15, 6);

		scale = crucibleTE.getOutCountScaled(100);
		drawTexturedModalRect(guiLeft + 129, guiTop + 106 - scale, 177, 6, 8, scale);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		if (TFCOptions.enableDebugMode)
		{
			this.fontRendererObj.drawString("Temp: " + crucibleTE.temperature, 178, 8, 0xffffff);
		}

		if (crucibleTE.currentAlloy != null)
		{
			if (crucibleTE.currentAlloy.outputAmount == 0)
			{
				this.fontRendererObj.drawString(EnumChatFormatting.UNDERLINE + TFC_Core.translate("gui.empty"), 7, 7, 0x000000);
				return;
			}
			else if (crucibleTE.currentAlloy.outputType != null)
			{
				this.fontRendererObj.drawString(EnumChatFormatting.UNDERLINE + TFC_Core.translate("gui.metal." + crucibleTE.currentAlloy.outputType.name.replace(" ", "")), 7, 7, 0x000000);
			}
			else
			{
				this.fontRendererObj.drawString(EnumChatFormatting.UNDERLINE + TFC_Core.translate("gui.metal.Unknown"), 7, 7, 0x000000);
			}

			for (int c = 0; c < crucibleTE.currentAlloy.alloyIngred.size(); c++)
			{
				double m = crucibleTE.currentAlloy.alloyIngred.get(c).metal;
				m = Math.round(m * 100d) / 100d;
				if (crucibleTE.currentAlloy.alloyIngred.get(c).metalType != null)
				{
					this.fontRendererObj.drawString(EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.metal." + crucibleTE.currentAlloy.alloyIngred.get(c).metalType.name.replace(" ", "")) + ": " + EnumChatFormatting.DARK_GREEN + m + "%", 7, 18 + 10 * (c), 0x000000);
				}
			}
		}
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		if (crucibleTE.currentAlloy != null)
		{
			int w = (this.width - this.xSize) / 2;
			int h = (this.height - this.ySize) / 2;
			if (par1 >= 129 + w && par2 >= 6 + h && par1 <= 137 + w && par2 <= 106 + h)
			{
				String[] text =
				{ String.format("%2.0f", crucibleTE.currentAlloy.outputAmount) };
				List<String> temp = Arrays.asList(text);
				drawHoveringText(temp, par1, par2, fontRendererObj);
			}
		}
	}
}
