package com.bioxx.tfc.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.TextureMap;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;

public class GuiAnvilPlanButton extends GuiButton 
{
	GuiAnvil screen;

	public GuiAnvilPlanButton(int index, int xPos, int yPos, int width, int height, GuiAnvil gui, String s)
	{
		super(index, xPos, yPos, width, height, s);
		screen = gui;
	}

	@Override
	public void drawButton(Minecraft mc, int x, int y)
	{
		if (this.visible)
		{
			int k = this.getHoverState(this.field_146123_n)-1;

			TFC_Core.bindTexture(GuiAnvil.texture);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.zLevel = 300f;
			this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 205 + k*18, 18, 18);
			this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			if(!screen.AnvilEntity.craftingPlan.equals("") && screen.AnvilEntity.workRecipe != null) 
			{
				TFC_Core.bindTexture(TextureMap.locationItemsTexture);
				this.drawTexturedModelRectFromIcon(this.xPosition+1, this.yPosition+1, screen.AnvilEntity.workRecipe.getCraftingResult().getIconIndex(), this.width-2, this.height-2);
			}
			this.zLevel = 0;
			this.mouseDragged(mc, x, y);

			if(field_146123_n)
			{
				FontRenderer fontrenderer = Minecraft.getMinecraft().fontRenderer;
				screen.drawTooltip(x, y, this.displayString);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}
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
