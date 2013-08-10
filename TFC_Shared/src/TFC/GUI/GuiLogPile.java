package TFC.GUI;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.Containers.ContainerLogPile;
import TFC.Core.Util.StringUtil;
import TFC.TileEntities.TileEntityLogPile;

public class GuiLogPile extends GuiContainer
{
    public GuiLogPile(InventoryPlayer inventoryplayer, TileEntityLogPile wb, World world, int i, int j, int k)
    {
        super(new ContainerLogPile(inventoryplayer, wb,world, i, j, k));
    }

    @Override
	public void onGuiClosed()
    {
        super.onGuiClosed();
    }

    protected void drawGuiContainerForegroundLayer()
    {
    	drawCenteredString(fontRenderer,StringUtil.localize("gui.LogPile"), 87, 6, 0x000000);
        //fontRenderer.drawString("Log Pile", 28, 6, 0x404040);
        fontRenderer.drawString(StringUtil.localize("gui.Inventory"), 8, (ySize - 96) + 2, 0x404040);
    }

    @Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
    	this.mc.renderEngine.bindTexture(Reference.AssetPathGui + "gui_logpile.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
    }
    
    @Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
    {
        fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
    }
}
