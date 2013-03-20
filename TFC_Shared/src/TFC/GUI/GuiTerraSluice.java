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


public class GuiTerraSluice extends GuiContainer
{
	private TileEntitySluice sluiceInventory;


	public GuiTerraSluice(InventoryPlayer inventoryplayer, TileEntitySluice tileEntitySluice, World world, int x, int y, int z)
	{
		super(new ContainerTerraSluice(inventoryplayer,tileEntitySluice, world, x, y, z) );
		sluiceInventory = tileEntitySluice;
		
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.mc.renderEngine.func_98187_b("/bioxx/gui_sluice.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
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
