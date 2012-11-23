package TFC.GUI;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.World;

import org.lwjgl.opengl.GL11;

import TFC.*;
import TFC.Containers.ContainerTerraSluice;
import TFC.TileEntities.TileEntityTerraSluice;


public class GuiTerraSluice extends GuiContainer
{
	private TileEntityTerraSluice sluiceInventory;


	public GuiTerraSluice(InventoryPlayer inventoryplayer, TileEntityTerraSluice tileEntitySluice, World world, int x, int y, int z)
	{
		super(new ContainerTerraSluice(inventoryplayer,tileEntitySluice, world, x, y, z) );
		sluiceInventory = tileEntitySluice;
		
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		int r = mc.renderEngine.getTexture("/bioxx/gui_sluice.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
        mc.renderEngine.bindTexture(r);
        int s = (width - xSize) / 2;
        int t = (height - ySize) / 2;
        drawTexturedModalRect(s, t, 0, 0, xSize, ySize);
        if(sluiceInventory.waterInput && sluiceInventory.waterOutput)
        {
            int l = 12;//sluiceInventory.getProcessScaled(12); 
            drawTexturedModalRect(s + 62, (t + 36 + 12) - l, 176, 12 - l, 14, l + 2);
        }
        int i1 = sluiceInventory.getProcessScaled(24);
       drawTexturedModalRect(s + 79, t + 34, 176, 14, i1+1, 16);
		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.drawCenteredString(fontRenderer, "Sluice", 89, 6, 0x404040);
        fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
        fontRenderer.drawString("Soil: " + sluiceInventory.soilAmount + "/50", 8, 20, 0x404040);
    }
	
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
    {
        fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
    }


}
