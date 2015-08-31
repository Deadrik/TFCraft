package com.bioxx.tfc.GUI;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerNestBox;
import com.bioxx.tfc.TileEntities.TENestBox;

public class GuiNestBox extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_nestbox.png");

	public GuiNestBox(InventoryPlayer inventoryplayer, TENestBox te, World world, int i, int j, int k)
	{
		super(new ContainerNestBox(inventoryplayer, te, world, i, j, k), 176, 85);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(texture);
	}
}
