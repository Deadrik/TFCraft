package TFC.GUI;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.Containers.ContainerMold;
import TFC.Core.TFC_Core;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;

public class GuiMold extends GuiContainer
{
	EntityPlayer player;
	public GuiMold(InventoryPlayer inventoryplayer, World world, int i, int j, int k)
	{
		super(new ContainerMold(inventoryplayer, world, i, j, k));
		this.xSize = 175;
		this.ySize = 130;
		player = inventoryplayer.player;
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		TFC_Core.bindTexture(new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_mold.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int w = (width - xSize) / 2;
		int h = (height - ySize) / 2;
		drawTexturedModalRect(w, h, 0, 0, xSize, ySize);

		drawTexturedModalRect(w+85, h+17, 176, 0, drawArrowScaled(21)+1, 15);
	}

	private int drawArrowScaled(int scale)
	{
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
		return (pi.moldTransferTimer == 1000 ? 0 : pi.moldTransferTimer * scale)/100;
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}
}
