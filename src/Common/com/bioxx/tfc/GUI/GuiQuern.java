package com.bioxx.tfc.GUI;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerQuern;
import com.bioxx.tfc.TileEntities.TEQuern;

public class GuiQuern extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_quern.png");

	public GuiQuern(InventoryPlayer inventoryplayer, TEQuern te, World world, int x, int y, int z)
	{
		super(new ContainerQuern(inventoryplayer, te, world, x, y, z), 176, 85);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(texture);
	}
}
