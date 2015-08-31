package com.bioxx.tfc.GUI;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerLiquidVessel;
import com.bioxx.tfc.Core.TFC_Core;

public class GuiVesselLiquid extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_vessel_liquid.png");

	private EntityPlayer player;
	private int bagsSlotNum;

	public GuiVesselLiquid(InventoryPlayer inventoryplayer, World world, int i, int j, int k)
	{
		super(new ContainerLiquidVessel(inventoryplayer, world, i, j, k), 176, 85);
		player = inventoryplayer.player;
		bagsSlotNum = player.inventory.currentItem;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(texture);
	}

	@Override
	protected void drawForeground(int guiLeft, int guiTop)
	{
		ItemStack stack = player.inventory.mainInventory[this.bagsSlotNum];
		NBTTagCompound tags = stack != null && stack.hasTagCompound() ? stack.getTagCompound() : null;
		if (tags != null && tags.hasKey("MetalType"))
		{
			drawCenteredString(this.fontRendererObj, tags.getString("MetalType"), guiLeft + 87, guiTop + 13, 0);
		}
		if (tags != null && tags.hasKey("MetalAmount"))
		{
			drawCenteredString(this.fontRendererObj, tags.getInteger("MetalAmount") + " " + TFC_Core.translate("gui.units"), guiLeft + 87, guiTop + 23, 0);
		}
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}
}
