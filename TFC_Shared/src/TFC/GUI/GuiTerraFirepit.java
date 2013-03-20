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


public class GuiTerraFirepit extends GuiContainer
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
		this.mc.renderEngine.func_98187_b("/bioxx/gui_firepit.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
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
