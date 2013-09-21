package TFC.GUI;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import TFC.Core.TFC_Core;
import TFC.Core.Player.PlayerInventory;

public class GuiContainerTFC extends GuiContainer
{
	protected int guiLeft = 0;
	protected int guiTop = 0;
	public GuiContainerTFC(Container par1Container, int xsize, int ysize)
	{
		super(par1Container);
		xSize = xsize;
		ySize = ysize+PlayerInventory.invYSize;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		drawGui(null);
	}

	protected void drawGui(ResourceLocation rl)
	{
		if(rl != null)
		{
			bindTexture(rl);
			guiLeft = (width - xSize) / 2;
			guiTop = (height - ySize) / 2;
			drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		}
		PlayerInventory.drawInventory(this, width, height, ySize-PlayerInventory.invYSize);
	}

	protected void bindTexture(ResourceLocation rl)
	{
		TFC_Core.bindTexture(rl);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
