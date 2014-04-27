package com.bioxx.tfc.GUI;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerNestBox;
import com.bioxx.tfc.TileEntities.TENestBox;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiNestBox extends GuiContainerTFC
{
	public static ResourceLocation GuiTex = new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_nestbox.png");

	public GuiNestBox(InventoryPlayer inventoryplayer, TENestBox te, World world, int i, int j, int k)
	{
		super(new ContainerNestBox(inventoryplayer, te, world, i, j, k), 176, 49);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(GuiTex);
	}
}
