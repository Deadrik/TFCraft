package com.bioxx.tfc.GUI;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerLogPile;
import com.bioxx.tfc.TileEntities.TELogPile;

public class GuiLogPile extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_logpile.png");

	public GuiLogPile(InventoryPlayer inventoryplayer, TELogPile te, World world, int x, int y, int z)
	{
		super(new ContainerLogPile(inventoryplayer, te, world, x, y, z), 176, 85);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(texture);
	}
}
