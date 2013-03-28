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


public class GuiTerraBloomery extends GuiContainer
{
	private TileEntityBloomery bloomery;


	public GuiTerraBloomery(InventoryPlayer inventoryplayer, TileEntityBloomery tileentityforge, World world, int x, int y, int z)
	{
		super(new ContainerTerraBloomery(inventoryplayer,tileentityforge, world, x, y, z) );
		bloomery = tileentityforge;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.mc.renderEngine.bindTexture("/bioxx/gui_bloomery.png");
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
	
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
    {
        fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
    }


}
