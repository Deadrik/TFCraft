package com.bioxx.tfc.GUI;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerGrill;
import com.bioxx.tfc.TileEntities.TEGrill;
import com.bioxx.tfc.api.TileEntities.TEFireEntity;

public class GuiGrill extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_grill.png");

	//private TEGrill grillTE;
	private TEFireEntity fireTE;

	public GuiGrill(InventoryPlayer inventoryplayer, TEGrill te, World world, int x, int y, int z)
	{
		super(new ContainerGrill(inventoryplayer, te, world, x, y, z), 176, 85);
		//grillTE = te;
		if (world.getTileEntity(x, y - 1, z) instanceof TEFireEntity)
			fireTE = (TEFireEntity) world.getTileEntity(x, y - 1, z);
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
		if (fireTE != null)
			scale = fireTE.getTemperatureScaled(49);

		drawTexturedModalRect(guiLeft + 7, guiTop + 65 - scale, 0, 86, 15, 6);
	}
}
