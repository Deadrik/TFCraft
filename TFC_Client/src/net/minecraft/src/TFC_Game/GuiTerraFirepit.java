package net.minecraft.src.TFC_Game;

import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.GuiContainerTFC;
import net.minecraft.src.TFC_Game.*;

import org.lwjgl.opengl.GL11;


public class GuiTerraFirepit extends GuiContainerTFC
{
	private TileEntityTerraFirepit FirepitEntity;


	public GuiTerraFirepit(InventoryPlayer inventoryplayer, TileEntityTerraFirepit tileentityfirepit)
	{
		super(new ContainerTerraFirepit(inventoryplayer,tileentityfirepit) );
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
//        if(sluiceInventory.waterInput && sluiceInventory.waterOutput)
//        {
//            int l = 12;//sluiceInventory.getProcessScaled(12); 
//            drawTexturedModalRect(s + 62, (t + 36 + 12) - l, 176, 12 - l, 14, l + 2);
//        }
//        int i1 = sluiceInventory.getProcessScaled(24);
//       drawTexturedModalRect(s + 79, t + 34, 176, 14, i1+1, 16);
        int i1 = (FirepitEntity.fuelBuildup*14)/3;
        //(guiX,guiY,sourceMinX,sourceMinY,sourceMaxX,sourceMaxY
        drawTexturedModalRect(w + 26, h+66, 176, 0, i1, 14);
        i1 = FirepitEntity.getTemperatureScaled(49);
        drawTexturedModalRect(w + 30, h + 65 - i1, 185, 31, 15, 6);
	}
	
	protected void drawGuiContainerForegroundLayer()
    {
        //this.drawCenteredString(fontRenderer, "Fire Pit", 89, 6, 0x404040);
//        fontRenderer.drawString(""+(int)FirepitEntity.getInputTemp()+"\u2103", 91, 17, 0xeeeeee);
//        if(FirepitEntity.getOutput1Temp() != -1000)
//        	fontRenderer.drawString(""+(int)FirepitEntity.getOutput1Temp()+"\u2103", 64, 63, 0xeeeeee);
//        if(FirepitEntity.getOutput2Temp() != -1000)
//        	fontRenderer.drawString(""+(int)FirepitEntity.getOutput2Temp()+"\u2103", 64, 74, 0xeeeeee);
        
        //int i1 = FirepitEntity.getTemperatureScaled(49);
		//fontRenderer.drawString(""+(int)FirepitEntity.fireTemperature+"\u2103", 141, 63 - i1, 0xffffff);
    }
	
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
    {
        fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
    }


}
