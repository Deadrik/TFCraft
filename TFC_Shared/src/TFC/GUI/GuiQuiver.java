package TFC.GUI;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.Containers.ContainerQuiver;
import TFC.Core.Player.PlayerInventory;

public class GuiQuiver extends GuiContainer
{
	public GuiQuiver(InventoryPlayer inventoryplayer, World world, int i, int j, int k)
	{
		super(new ContainerQuiver(inventoryplayer, world, i, j, k));
		xSize = 175;
		ySize = 49+PlayerInventory.invYSize;
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.mc.func_110434_K().func_110577_a(new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_quiver.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (width - xSize) / 2;
		int y = ((height - ySize) / 2);
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		PlayerInventory.drawInventory(this, width, height, ySize-PlayerInventory.invYSize);
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}
}
