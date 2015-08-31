package com.bioxx.tfc.GUI;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerChestTFC;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.TileEntities.TEChest;

public class GuiChestTFC extends GuiContainer
{
	private IInventory upperChestInventory;
	private IInventory lowerChestInventory;

	/**
	 * window height is calculated with this values, the more rows, the heigher
	 */
	private int inventoryRows;

	public GuiChestTFC(IInventory par1IInventory, IInventory chestInv, World world, int x, int y, int z)
	{
		super(new ContainerChestTFC(par1IInventory, chestInv, world, x, y, z));

		TEChest chest = (TEChest)chestInv;

		this.upperChestInventory = par1IInventory;
		this.lowerChestInventory = chestInv;

		if (chest.adjacentChestXNeg != null)
		{
			lowerChestInventory = new InventoryLargeChest("Large chest", chest.adjacentChestXNeg, chestInv);
		}

		if (chest.adjacentChestXPos != null)
		{
			lowerChestInventory = new InventoryLargeChest("Large chest", chestInv, chest.adjacentChestXPos);
		}

		if (chest.adjacentChestZNeg != null)
		{
			lowerChestInventory = new InventoryLargeChest("Large chest", chest.adjacentChestZNeg, chestInv);
		}

		if (chest.adjacentChestZPos != null)
		{
			lowerChestInventory = new InventoryLargeChest("Large chest", chestInv, chest.adjacentChestZPos);
		}

		this.allowUserInput = false;
		short var3 = 222;
		int var4 = var3 - 108;
		this.inventoryRows = lowerChestInventory.getSizeInventory() / 9;
		this.ySize = var4 + this.inventoryRows * 18;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everythin in front of the items)
	 */
	protected void drawGuiContainerForegroundLayer()
	{
		this.fontRendererObj.drawString(TFC_Core.translate(this.lowerChestInventory.getInventoryName()), 8, 6, 4210752);
		this.fontRendererObj.drawString(TFC_Core.translate(this.upperChestInventory.getInventoryName()), 8, this.ySize - 96 + 2, 4210752);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		TFC_Core.bindTexture(new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_chest.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
		this.drawTexturedModalRect(var5, var6 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
		
		PlayerInventory.drawInventory(this, width, height, ySize-PlayerInventory.invYSize + 10);
	}
}
