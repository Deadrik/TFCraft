package com.bioxx.tfc.GUI;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerQuiver;

public class GuiQuiver extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_quiver.png");

	public GuiQuiver(InventoryPlayer inventoryplayer, World world, int i, int j, int k)
	{
		super(new ContainerQuiver(inventoryplayer, world, i, j, k), 176, 49);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(texture);
	}

	/**
	 * This function is what controls the hotbar shortcut check when you press a
	 * number key when hovering a stack.
	 */
	@Override
	protected boolean checkHotbarKeys(int par1)
	{
		if (this.mc.thePlayer.inventory.currentItem != par1 - 2)
		{
			super.checkHotbarKeys(par1);
			return true;
		}
		else
			return false;
	}
}
