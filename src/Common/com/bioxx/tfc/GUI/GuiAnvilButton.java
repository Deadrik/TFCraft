package com.bioxx.tfc.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Crafting.PlanRecipe;
import com.bioxx.tfc.api.Enums.RuleEnum;

public class GuiAnvilButton extends GuiButton 
{
	//private static ResourceLocation texture = new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "anvilicons.png");
	public IIcon icon;
	public int bX;
	public int bY;
	public int bW;
	public int bH;
	public int ruleIndex;
	private GuiAnvil screen;
	private byte red = (byte) 255;
	private byte blue = (byte) 255;
	private byte green = (byte) 255;

	public GuiAnvilButton(int index, int xPos, int yPos, int width, int height, IIcon ico, 
			int buttonX, int buttonY, int buttonW, int buttonH, GuiAnvil gui, String s)
	{
		super(index, xPos, yPos, width, height, s);
		icon = ico;
		bX = buttonX;
		bY = buttonY;
		bW = buttonW;
		bH = buttonH;
		screen = gui;
	}

	public GuiAnvilButton(int index, int xPos, int yPos, int width, int height, 
			int buttonX, int buttonY, int buttonW, int buttonH, GuiAnvil gui, int i, byte r, byte g, byte b)
	{
		super(index, xPos, yPos, width, height, "");
		bX = buttonX;
		bY = buttonY;
		bW = buttonW;
		bH = buttonH;
		screen = gui;
		ruleIndex = i;
		red = r;
		green = g;
		blue = b;
	}


	@Override
	public void drawButton(Minecraft mc, int x, int y)
	{
		if (this.visible)
		{
			int k = this.getHoverState(this.field_146123_n)-1;
			if(icon == null)
			{
				k = 0;
				if(screen.anvilTE != null && screen.anvilTE.workRecipe != null)
				{
					PlanRecipe p = AnvilManager.getInstance().getPlan(screen.anvilTE.craftingPlan);
					if(p == null) return;
					RuleEnum[] rules = p.rules;
					//int[] ItemRules = screen.anvilTE.getItemRules();
					this.displayString = TFC_Core.translate(rules[ruleIndex].Name);
				}
			}

			TFC_Core.bindTexture(GuiAnvil.texture);
			GL11.glColor4ub(red, green, blue, (byte)255);
			this.drawTexturedModalRect(this.xPosition, this.yPosition, this.bX + k * 16, this.bY + (ruleIndex * 22), this.bW, this.bH);

			this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			if(icon != null) 
			{
				TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
				this.drawTexturedModelRectFromIcon(this.xPosition, this.yPosition, icon, this.width, this.height);
			}

			this.mouseDragged(mc, x, y);

			if(field_146123_n)
			{
				screen.drawTooltip(x, y, this.displayString);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}
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
