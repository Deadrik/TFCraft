package TFC.GUI;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.World;

import org.lwjgl.opengl.GL11;

import TFC.*;
import TFC.Containers.ContainerTerraBloomery;
import TFC.TileEntities.TileEntityBloomery;


public class GuiTerraBloomery extends GuiContainer
{
	private TileEntityBloomery ForgeEntity;


	public GuiTerraBloomery(InventoryPlayer inventoryplayer, TileEntityBloomery tileentityforge, World world, int x, int y, int z)
	{
		super(new ContainerTerraBloomery(inventoryplayer,tileentityforge, world, x, y, z) );
		ForgeEntity = tileentityforge;
		
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		int r = mc.renderEngine.getTexture("/bioxx/Bloomerygui.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
        mc.renderEngine.bindTexture(r);
        int w = (width - xSize) / 2;
        int h = (height - ySize) / 2;
        drawTexturedModalRect(w, h, 0, 0, xSize, ySize);

        int i1 = 0;

        i1 = ForgeEntity.getTemperatureScaled(49);
        drawTexturedModalRect(w + 8, h + 65 - i1, 185, 31, 15, 6);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
    {
		drawCenteredString(this.fontRenderer,"Type:" + ForgeEntity.OreType,88,7,0x000000);
		this.fontRenderer.drawString("Ore:" + ForgeEntity.oreCount*4.5 + "kg",104, 32, 0x000000);
		this.fontRenderer.drawString("Coal:" + ForgeEntity.charcoalCount*4.5 + "kg",104, 44, 0x000000);
		this.fontRenderer.drawString("Output:" + ((ForgeEntity.outCount/100)*2) + "kg",104, 56, 0x000000);
    }
	
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
    {
        fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
    }


}
