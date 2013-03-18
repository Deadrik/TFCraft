package TFC.GUI;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.src.ModLoader;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import org.lwjgl.opengl.GL11;

import TFC.*;
import TFC.Containers.*;
import TFC.TileEntities.*;


public class GuiTerraForge extends GuiContainer
{
	private TileEntityTerraForge ForgeEntity;


	public GuiTerraForge(InventoryPlayer inventoryplayer, TileEntityTerraForge tileentityforge, World world, int x, int y, int z)
	{
		super(new ContainerTerraForge(inventoryplayer,tileentityforge, world, x, y, z) );
		ForgeEntity = tileentityforge;
		
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		int r = mc.renderEngine.getTexture("/bioxx/gui_forge.png");
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
        int i1 = 0;
        //(guiX,guiY,sourceMinX,sourceMinY,sourceMaxX,sourceMaxY
        //drawTexturedModalRect(w + 26, h+66, 176, 0, i1, 14);
        i1 = ForgeEntity.getTemperatureScaled(49);
        drawTexturedModalRect(w + 8, h + 65 - i1, 185, 31, 15, 6);
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
