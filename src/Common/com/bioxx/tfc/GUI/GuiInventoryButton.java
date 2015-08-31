package com.bioxx.tfc.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;

public class GuiInventoryButton extends GuiButton 
{
	private static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID+":textures/gui/inventory.png");
	public IIcon icon;
	public int bX;
	public int bY;
	public int bW;
	public int bH;

	public GuiInventoryButton(int index, int xPos, int yPos, int width, int height, 
			int buttonX, int buttonY, int buttonW, int buttonH, String s, IIcon ico)
	{
		super(index, xPos, yPos, width, height, s);
		bX = buttonX;
		bY = buttonY;
		bW = buttonW;
		bH = buttonH;
		icon = ico;
	}


	@Override
	public void drawButton(Minecraft mc, int x, int y)
	{
		if (this.visible)
		{
			TFC_Core.bindTexture(texture);
			this.drawTexturedModalRect(this.xPosition, this.yPosition, this.bX, this.bY, this.bW, this.bH);

			this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			if(icon != null) 
			{
				TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
				this.drawTexturedModelRectFromIcon(this.xPosition+4, this.yPosition+2, icon, 16, 16);
			}

			this.mouseDragged(mc, x, y);

			/*if(field_82253_i)
			{
				drawTooltip(x, y, this.displayString);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}*/
		}
	}

	/*private boolean isPointInRegion(int mouseX, int mouseY)
	{
		int k1 = 0;//screen.getGuiLeft();
		int l1 = 0;//screen.getGuiTop();
		mouseX -= k1;
		mouseY -= l1;
		return mouseX >= xPosition - 1 && mouseX < xPosition + width + 1 && mouseY >= yPosition - 1 && mouseY < yPosition + height + 1;
	}*/

	/*public void drawTooltip(int mx, int my, String text) {
		List list = new ArrayList();
		list.add(text);
		FontRenderer fontrenderer = Minecraft.getMinecraft().fontRenderer;
		screen.drawHoveringText(list, mx, my+15, fontrenderer);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
	}*/
}
