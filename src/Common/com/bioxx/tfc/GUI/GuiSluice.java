package com.bioxx.tfc.GUI;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerSluice;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TESluice;

public class GuiSluice extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_sluice.png");

	private TESluice sluiceTE;

	public GuiSluice(InventoryPlayer inventoryplayer, TESluice te, World world, int x, int y, int z)
	{
		super(new ContainerSluice(inventoryplayer, te, world, x, y, z), 176, 85);
		sluiceTE = te;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(texture);
	}

	@Override
	protected void drawForeground(int guiLeft, int guiTop)
	{
		if (sluiceTE.waterInput && sluiceTE.waterOutput)
		{
			int l = 12;//sluiceInventory.getProcessScaled(12); 
			drawTexturedModalRect(guiLeft + 80, guiTop + 36 + 12 - 19 - l, 176, 12 - l, 14, l + 2);
		}
		int scale = sluiceTE.getProcessScaled(24);
		drawTexturedModalRect(guiLeft + 76, guiTop + 34, 176, 14, scale + 1, 16);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		if (sluiceTE.soilAmount != -1)
			fontRendererObj.drawString(TFC_Core.translate("gui.Sluice.Soil") + ": " + sluiceTE.soilAmount + "/50", 15, 39, 0x404040);
		else
			fontRendererObj.drawString(TFC_Core.translate("gui.Sluice.Overworked"), 10, 39, 0x404040);
	}
}
