package com.bioxx.tfc.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;

public class GuiKnappingButton extends GuiButton 
{
	public GuiKnappingButton(int index, int xPos, int yPos, int width, int height)
	{
		super(index, xPos, yPos, width, height, "");
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int xPos, int yPos)
	{
		if (this.visible)
		{
			IIcon icon = PlayerManagerTFC.getInstance().getClientPlayer().specialCraftingType.getIconIndex();
			if(!this.enabled && PlayerManagerTFC.getInstance().getClientPlayer().specialCraftingTypeAlternate != null)
				icon = PlayerManagerTFC.getInstance().getClientPlayer().specialCraftingTypeAlternate.getIconIndex();
			TFC_Core.bindTexture(TextureMap.locationItemsTexture);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.field_146123_n = xPos >= this.xPosition && yPos >= this.yPosition && xPos < this.xPosition + this.width && yPos < this.yPosition + this.height;
			int k = this.getHoverState(this.field_146123_n);
			this.drawTexturedModelRectFromIcon(this.xPosition, this.yPosition, icon, this.width, this.height);
			//this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
			this.mouseDragged(par1Minecraft, xPos, yPos);
			int l = 14737632;

			if (!this.enabled)
				l = -6250336;
			else if (this.field_146123_n)
				l = 16777120;
		}
	}

}
