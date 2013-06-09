package TFC.GUI;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.Containers.ContainerBloomery;
import TFC.TileEntities.TileEntityBloomery;


public class GuiBloomery extends GuiContainer
{
	private TileEntityBloomery bloomery;


	public GuiBloomery(InventoryPlayer inventoryplayer, TileEntityBloomery tileentityforge, World world, int x, int y, int z)
	{
		super(new ContainerBloomery(inventoryplayer,tileentityforge, world, x, y, z) );
		bloomery = tileentityforge;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.mc.renderEngine.bindTexture(Reference.AssetPathGui + "gui_bloomery.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
        int w = (width - xSize) / 2;
        int h = (height - ySize) / 2;
        drawTexturedModalRect(w, h, 0, 0, xSize, ySize);

        int scale = 0;

        scale = bloomery.getTemperatureScaled(49);
        drawTexturedModalRect(w + 8, h + 65 - scale, 185, 31, 15, 6);
        
        scale = bloomery.getOreCountScaled(80);
        drawTexturedModalRect(w + 40, h + 25, 176, 0, scale+1, 8);
        
        scale = bloomery.getCharcoalCountScaled(80);
        drawTexturedModalRect(w + 40, h + 43, 176, 0, scale+1, 8);
        
        scale = bloomery.getOutCountScaled(80);
        drawTexturedModalRect(w + 40, h + 61, 176, 0, scale+1, 8);
        drawTexturedModalRect(w + 40, h + 61, 176, 8, scale+1, 8);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
    {
		drawCenteredString(this.fontRenderer,bloomery.OreType,88,7,0x555555);
		this.fontRenderer.drawString("Ore" ,40, 17, 0x000000);
		this.fontRenderer.drawString("Charcoal" ,40, 35, 0x000000);
		this.fontRenderer.drawString("Output",40, 53, 0x000000);
    }
	
	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
    {
        fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
    }


}
