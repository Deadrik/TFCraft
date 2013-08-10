package TFC.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;

import TFC.Core.Player.PlayerManagerTFC;

public class GuiKnappingButton extends GuiButton 
{

	public GuiKnappingButton(int index, int xPos, int yPos, int width, int height)
	{
		super(index, xPos, yPos, width, height, "");
	}
	
	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.drawButton)
        {
        	Icon icon = PlayerManagerTFC.getInstance().getClientPlayer().specialCraftingType.getIconIndex();
        	if(!this.enabled && PlayerManagerTFC.getInstance().getClientPlayer().specialCraftingTypeAlternate != null)
        		icon = PlayerManagerTFC.getInstance().getClientPlayer().specialCraftingTypeAlternate.getIconIndex();
            par1Minecraft.renderEngine.bindTexture("/gui/items.png");
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int k = this.getHoverState(this.field_82253_i);
            this.drawTexturedModelRectFromIcon(this.xPosition, this.yPosition, icon, this.width, this.height);
            //this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
            this.mouseDragged(par1Minecraft, par2, par3);
            int l = 14737632;

            if (!this.enabled)
            {
                l = -6250336;
            }
            else if (this.field_82253_i)
            {
                l = 16777120;
            }
        }
    }

}
