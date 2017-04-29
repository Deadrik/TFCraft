package com.bioxx.tfc.GUI;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerQuiver;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiQuiver extends GuiContainerTFC
{
	public static ResourceLocation GuiTex = new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_quiver.png");

	public GuiQuiver(InventoryPlayer inventoryplayer, World world, int i, int j, int k)
	{
		super(new ContainerQuiver(inventoryplayer, world, i, j, k), 176, 85);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(GuiTex);
	}
}
