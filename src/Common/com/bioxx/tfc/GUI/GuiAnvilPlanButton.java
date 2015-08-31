package com.bioxx.tfc.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCItems;

public class GuiAnvilPlanButton extends GuiButton 
{
	private GuiAnvil screen;
	protected static final RenderItem ITEM_RENDERER = new RenderItem();

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
			//TFC_Core.bindTexture(TextureMap.locationItemsTexture);

			if (screen.anvilTE != null && !screen.anvilTE.craftingPlan.equals("") && screen.anvilTE.workRecipe != null)
				renderInventorySlot(screen.anvilTE.workRecipe.getCraftingResult(),this.xPosition+1, this.yPosition+1);
			else
				renderInventorySlot(new ItemStack(TFCItems.blueprint),this.xPosition+1, this.yPosition+1);

			this.zLevel = 0;
			this.mouseDragged(mc, x, y);

			if(field_146123_n)
			{
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
}
