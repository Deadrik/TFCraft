package com.bioxx.tfc.GUI;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Containers.ContainerWorkbench;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEWorkbench;

public class GuiWorkbench extends GuiContainer
{
	public GuiWorkbench(InventoryPlayer inventoryplayer, TEWorkbench wb, World world, int i, int j, int k)
	{
		super(new ContainerWorkbench(inventoryplayer, wb,world, i, j, k));
	}

	/*@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
	}*/

	protected void drawGuiContainerForegroundLayer()
	{
		fontRendererObj.drawString(TFC_Core.translate("gui.Workbench.Crafting"), 28, 6, 0x404040);
		fontRendererObj.drawString(TFC_Core.translate("gui.Inventory"), 8, ySize - 96 + 2, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		TFC_Core.bindTexture(new ResourceLocation("textures/gui/container/crafting_table.png"));

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
	}
}
