package TFC.GUI;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

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

		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		// TODO Auto-generated method stub

	}

	protected void drawGui(ResourceLocation rl)
	{
		bindTexture(rl);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		PlayerInventory.drawInventory(this, width, height, ySize-PlayerInventory.invYSize);
	}

	protected void bindTexture(ResourceLocation rl)
	{
		this.mc.func_110434_K().func_110577_a(rl);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
