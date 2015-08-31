package com.bioxx.tfc.GUI;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerBlastFurnace;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEBlastFurnace;
import com.bioxx.tfc.api.TFCOptions;

public class GuiBlastFurnace extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_blastfurnace.png");

	private TEBlastFurnace blastFurnaceTE;

	public GuiBlastFurnace(InventoryPlayer inventoryplayer, TEBlastFurnace te, World world, int x, int y, int z)
	{
		super(new ContainerBlastFurnace(inventoryplayer, te, world, x, y, z), 176, 85);
		blastFurnaceTE = te;
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

		scale = blastFurnaceTE.getTemperatureScaled(49);
		drawTexturedModalRect(guiLeft + 8, guiTop + 65 - scale, 185, 31, 15, 6);

		scale = blastFurnaceTE.getOreCountScaled(80);
		drawTexturedModalRect(guiLeft + 40, guiTop + 25, 176, 0, scale + 1, 8);

		scale = blastFurnaceTE.getCharcoalCountScaled(80);
		drawTexturedModalRect(guiLeft + 40, guiTop + 43, 176, 0, scale + 1, 8);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		this.fontRendererObj.drawString(TFC_Core.translate("gui.Bloomery.Ore"), 40, 17, 0x000000);
		this.fontRendererObj.drawString(TFC_Core.translate("gui.Bloomery.Charcoal"), 40, 35, 0x000000);

		if (TFCOptions.enableDebugMode)
		{
			this.fontRendererObj.drawString("Temp : " + blastFurnaceTE.fireTemp, 40, 71, 0x000000);
		}
	}
}
