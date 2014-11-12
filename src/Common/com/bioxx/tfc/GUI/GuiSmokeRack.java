package com.bioxx.tfc.GUI;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockSmokeRack;
import com.bioxx.tfc.Containers.ContainerSmokeRack;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.TileEntities.TESmokeRack;

public class GuiSmokeRack extends GuiContainer
{
	private InventoryPlayer inventoryplayer;
	
	public GuiSmokeRack(InventoryPlayer inventoryplayer, TESmokeRack rack, World world, int i, int j, int k)
	{
		super(new ContainerSmokeRack(inventoryplayer, rack, world, i, j, k));
		this.inventoryplayer = inventoryplayer;
		xSize = 176;
		ySize = 85+PlayerInventory.invYSize;
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
	}

	protected void drawGuiContainerForegroundLayer()
	{
		drawCenteredString(fontRendererObj,StatCollector.translateToLocal("gui.SmokeRack"), 87, 6, 0x000000);
		fontRendererObj.drawString(StatCollector.translateToLocal("gui.Inventory"), 8, (ySize - 96) + 2, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		TFC_Core.bindTexture(new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_smoke_rack.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);

		ItemStack hangItem = TFC_Core.getItemInInventory(BlockSmokeRack.getCreatedWithItem(), inventoryplayer);
		if (hangItem == null)
		{
			fontRendererObj.drawString(StatCollector.translateToLocal("gui.SmokeRack.NoHangItem"), l+5, i1+60, 0xff0000);
			fontRendererObj.drawString(StatCollector.translateToLocal(BlockSmokeRack.getCreatedWithItem().getUnlocalizedName()+".name"), l+5, i1+70, 0xff0000);
		}
		
		PlayerInventory.drawInventory(this, width, height, ySize-PlayerInventory.invYSize);
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}
}
