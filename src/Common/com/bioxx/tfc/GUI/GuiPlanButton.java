package com.bioxx.tfc.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;

public class GuiPlanButton extends GuiButton 
{
	public ItemStack item;
	private GuiPlanSelection screen;
	protected static final RenderItem ITEM_RENDERER = new RenderItem();

	public GuiPlanButton(int index, int xPos, int yPos, int width, int height, ItemStack ico, GuiPlanSelection gui, String s)
	{
		super(index, xPos, yPos, width, height, s);
		item = ico;
		screen = gui;
	}


	@Override
	public void drawButton(Minecraft mc, int x, int y)
	{
		if (this.visible)
		{
			int k = this.getHoverState(this.field_146123_n)-1;

			TFC_Core.bindTexture(GuiPlanSelection.texture);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.drawTexturedModalRect(this.xPosition, this.yPosition, 176, k*18, 18, 18);
			this.field_146123_n = isPointInRegion(x, y);//x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;

			if(item != null) 
			{
				renderInventorySlot(item, this.xPosition+1, this.yPosition+1);
			}

			this.mouseDragged(mc, x, y);

			if(field_146123_n)
			{
				//FontRenderer fontrenderer = Minecraft.getMinecraft().fontRenderer;
				screen.drawTooltip(x, y, this.displayString);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}
		}
	}

	protected void renderInventorySlot(ItemStack is, int x, int y)
	{
		if (is != null)
		{
			ITEM_RENDERER.renderItemAndEffectIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), is, x, y);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
		}
	}

	private boolean isPointInRegion(int mouseX, int mouseY)
	{
		int k1 = 0;//screen.getGuiLeft();
		int l1 = 0;//screen.getGuiTop();
		mouseX -= k1;
		mouseY -= l1;
		return mouseX >= xPosition - 1 && mouseX < xPosition + width + 1 && mouseY >= yPosition - 1 && mouseY < yPosition + height + 1;
	}
}
