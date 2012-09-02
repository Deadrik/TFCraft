package TFC.GUI;

import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;

import TFC.Containers.ContainerTerraFirepit;
import TFC.TileEntities.TileEntityTerraFirepit;


public class GuiTerraFirepit extends GuiContainerTFC
{
	private TileEntityTerraFirepit FirepitEntity;


	public GuiTerraFirepit(InventoryPlayer inventoryplayer, TileEntityTerraFirepit tileentityfirepit, World world, int x, int y, int z)
	{
		super(new ContainerTerraFirepit(inventoryplayer,tileentityfirepit, world, x, y, z) );
		FirepitEntity = tileentityfirepit;
		
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		int r = mc.renderEngine.getTexture("/bioxx/Firepitgui.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
        mc.renderEngine.bindTexture(r);
        int w = (width - xSize) / 2;
        int h = (height - ySize) / 2;
        drawTexturedModalRect(w, h, 0, 0, xSize, ySize);
        int i1 = FirepitEntity.getTemperatureScaled(49);
        drawTexturedModalRect(w + 30, h + 65 - i1, 185, 31, 15, 6);
	}
	
	protected void drawGuiContainerForegroundLayer()
    {
	    
    }
	
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
    {
        fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
    }


}
