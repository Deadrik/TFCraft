package com.bioxx.tfc.GUI;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerFirepit;
import com.bioxx.tfc.TileEntities.TEFirepit;

public class GuiFirepit extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_firepit.png");

	private TEFirepit firepitTE;

	public GuiFirepit(InventoryPlayer inventoryplayer, TEFirepit te, World world, int x, int y, int z)
	{
		super(new ContainerFirepit(inventoryplayer, te, world, x, y, z), 176, 85);
		firepitTE = te;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(texture);
	}

	@Override
	protected void drawForeground(int guiLeft, int guiTop)
	{
		if (firepitTE != null) // Fixes OpenEye-reported NPE
		{
			int scale = firepitTE.getTemperatureScaled(49);
			drawTexturedModalRect(guiLeft + 30, guiTop + 65 - scale, 185, 31, 15, 6);
		}
	}
}
