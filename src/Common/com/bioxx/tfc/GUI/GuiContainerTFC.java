package com.bioxx.tfc.GUI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.PlayerInventory;

public class GuiContainerTFC extends GuiContainer
{
	protected boolean drawInventory = true;
	protected Slot activeSlot;

	public GuiContainerTFC(Container container, int xsize, int ysize)
	{
		super(container);
		xSize = xsize;
		ySize = ysize + PlayerInventory.invYSize;
	}

	protected void setDrawInventory(boolean b)
	{
		if (!drawInventory && b)
			ySize += PlayerInventory.invYSize;
		else if (drawInventory && !b)
			ySize -= PlayerInventory.invYSize;
		drawInventory = b;
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		for (int j1 = 0; j1 < this.inventorySlots.inventorySlots.size(); ++j1)
		{
			Slot slot = (Slot) this.inventorySlots.inventorySlots.get(j1);
			if (this.isMouseOverSlot(slot, par1, par2) && slot.func_111238_b())
				this.activeSlot = slot;
		}
	}

	protected boolean isMouseOverSlot(Slot par1Slot, int par2, int par3)
	{
		return this.func_146978_c/*isPointInRegion*/(par1Slot.xDisplayPosition, par1Slot.yDisplayPosition, 16, 16, par2, par3);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		drawGui(null);
	}

	protected void drawGui(ResourceLocation rl)
	{
		if (rl != null)
		{
			bindTexture(rl);
			guiLeft = (width - xSize) / 2;
			guiTop = (height - ySize) / 2;
			int height = drawInventory ? this.getShiftedYSize() : ySize;

			drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, height);

			drawForeground(guiLeft, guiTop);
		}
		if (drawInventory)
			PlayerInventory.drawInventory(this, width, height, this.getShiftedYSize());
	}

	/*
	 * Draws extra pieces on a GUI such as moving gauges and arrows.
	 * Must be called before PlayerInventory.drawInventory() to avoid extra binding of textures.
	 */
	protected void drawForeground(int guiLeft, int guiTop)
	{
		// Intentionally blank.
	}

	protected boolean mouseInRegion(int x, int y, int width, int height, int mouseX, int mouseY)
	{
		mouseX -= guiLeft;
		mouseY -= guiTop;
		return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
	}

	protected void bindTexture(ResourceLocation rl)
	{
		TFC_Core.bindTexture(rl);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public void drawTooltip(int mx, int my, String text)
	{
		List<String> list = new ArrayList<String>();
		list.add(text);
		this.drawHoveringText(list, mx, my + 15, this.fontRendererObj);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		//GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	protected void drawHoveringTextZLevel(List par1List, int par2, int par3, FontRenderer font, float z)
	{
		if (!par1List.isEmpty())
		{
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			//GL11.glDisable(GL11.GL_DEPTH_TEST);
			int k = 0;
			Iterator iterator = par1List.iterator();

			while (iterator.hasNext())
			{
				String s = (String) iterator.next();
				int l = font.getStringWidth(s);
				if (l > k)
					k = l;
			}

			int i1 = par2 + 12;
			int j1 = par3 - 12;
			int k1 = 8;

			if (par1List.size() > 1)
				k1 += 2 + (par1List.size() - 1) * 10;

			if (i1 + k > this.width)
				i1 -= 28 + k;

			if (j1 + k1 + 6 > this.height)
				j1 = this.height - k1 - 6;

			this.zLevel = z;
			itemRender.zLevel = 300.0F;
			int l1 = -267386864;
			this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
			this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
			this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
			this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
			this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
			int i2 = 1347420415;
			int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
			this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
			this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
			this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
			this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

			for (int k2 = 0; k2 < par1List.size(); ++k2)
			{
				String s1 = (String) par1List.get(k2);
				font.drawStringWithShadow(s1, i1, j1, -1);

				if (k2 == 0)
					j1 += 2;
				j1 += 10;
			}

			this.zLevel = 0.0F;
			itemRender.zLevel = 0.0F;
			GL11.glEnable(GL11.GL_LIGHTING);
			RenderHelper.enableStandardItemLighting();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		}
	}

	protected int getShiftedYSize()
	{
		return this.ySize - PlayerInventory.invYSize;
	}

}
