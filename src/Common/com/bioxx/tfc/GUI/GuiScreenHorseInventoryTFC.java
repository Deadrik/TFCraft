package com.bioxx.tfc.GUI;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerHorseInventoryTFC;
import com.bioxx.tfc.Entities.Mobs.EntityHorseTFC;

@SideOnly(Side.CLIENT)
public class GuiScreenHorseInventoryTFC extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_horse.png");
	//private IInventory invHorse;
	private EntityHorseTFC horse;
	private float xSize;
	private float ySize;

	public GuiScreenHorseInventoryTFC(InventoryPlayer playerInv, IInventory horseInv, EntityHorseTFC entityHorse)
	{
		super(new ContainerHorseInventoryTFC(playerInv, horseInv, entityHorse), 176, 85);
		//this.invHorse = horseInv;
		this.horse = entityHorse;
		this.allowUserInput = false; // Not sure why this is here
	}
	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		// Draw name at the top of the inventory for named horses.
		if (this.horse.hasCustomNameTag())
			this.fontRendererObj.drawString(this.horse.getCustomNameTag(), 8, 6, 4210752);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		this.drawGui(texture);
	}

	@Override
	protected void drawForeground(int guiLeft, int guiTop)
	{
		if (this.horse.isChested()) // Draw chest slots for chested donkeys
		{
			this.drawTexturedModalRect(guiLeft + 79, guiTop + 17, 0, this.getShiftedYSize() + 1, 90, 54);
		}

		if (this.horse.func_110259_cr()) // Draw armor slot for standard horses
		{
			this.drawTexturedModalRect(guiLeft + 7, guiTop + 35, 0, this.getShiftedYSize() + 55, 18, 18);
		}

		// Draw the horse in the box.
		GuiInventory.func_147046_a(guiLeft + 51, guiTop + 60, 17, guiLeft + 51 - this.xSize, guiTop + 75 - 50 - this.ySize, this.horse);
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int par1, int par2, float par3) // Don't know what this does, but not removing it
	{
		this.xSize = par1;
		this.ySize = par2;
		super.drawScreen(par1, par2, par3);
	}
}
