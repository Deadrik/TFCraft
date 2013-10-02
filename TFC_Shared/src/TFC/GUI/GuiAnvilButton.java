package TFC.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.API.Enums.CraftingRuleEnum;
import TFC.Core.TFC_Core;
import TFC.Core.Util.StringUtil;

public class GuiAnvilButton extends GuiButton 
{
	private static ResourceLocation texture = new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "anvilicons.png");
	public Icon icon;
	public int bX = 0;
	public int bY = 0;
	public int bW = 0;
	public int bH = 0;
	public int ruleIndex = 0;
	GuiAnvil screen;
	byte red = (byte)255;
	byte blue = (byte)255;
	byte green = (byte)255;

	public GuiAnvilButton(int index, int xPos, int yPos, int width, int height, Icon ico, 
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
		if (this.drawButton)
		{


			int k = this.getHoverState(this.field_82253_i)-1;
			if(icon == null)
			{
				k = 0;
				if(screen.AnvilEntity != null && screen.AnvilEntity.workRecipe != null)
				{
					CraftingRuleEnum[] Rules = screen.AnvilEntity.workRecipe.getRules();
					int[] ItemRules = screen.AnvilEntity.getItemRules();
					this.displayString = StringUtil.localize(Rules[ruleIndex].Name);
				}
			}

			TFC_Core.bindTexture(GuiAnvil.texture);
			GL11.glColor4ub(red, green, blue, (byte)255);
			this.drawTexturedModalRect(this.xPosition, this.yPosition, this.bX + k * 16, this.bY + (ruleIndex * 22), this.bW, this.bH);

			this.field_82253_i = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			if(icon != null) 
			{
				TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
				this.drawTexturedModelRectFromIcon(this.xPosition, this.yPosition, icon, this.width, this.height);
			}

			this.mouseDragged(mc, x, y);

			if(field_82253_i)
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
