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


public class GuiBarrel extends GuiContainer
{
	private TileEntityBarrel barrel;
	private EntityPlayer player;

	public GuiBarrel(InventoryPlayer inventoryplayer, TileEntityBarrel tileentitybarrel, World world, int x, int y, int z)
	{
		super(new ContainerTerraBarrel(inventoryplayer,tileentitybarrel, world, x, y, z) );
		barrel = tileentitybarrel;
		player = inventoryplayer.player;
		guiLeft = (width - 208) / 2;
		guiTop = (height - 198) / 2;

		//controlList.clear();

		//controlList.add(new GuiButton(0, guiLeft+86, guiTop + 74, 36, 20, "\2474Seal"));
	}

	public void initGui()
	{
		super.initGui();
		//guiLeft = (width - 208) / 2;
		//guiTop = (height - 198) / 2;

		buttonList.clear();

		buttonList.add(new GuiButton(0, guiLeft+52, guiTop + 50, 36, 20, "Seal"));
		buttonList.add(new GuiButton(1, guiLeft+88, guiTop + 50, 36, 20, "Empty"));
	}

	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0)
		{
			if(barrel.actionSeal()){
				player.closeScreen();
			}
		}
		if (guibutton.id == 1)
		{
			barrel.actionEmpty();
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.mc.renderEngine.func_98187_b("/bioxx/gui_barrel.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		int w = (width - xSize) / 2;
		int h = (height - ySize) / 2;
		drawTexturedModalRect(w, h, 0, 0, xSize, ySize);

		int scale = 0;
		if(barrel!=null){
			scale = barrel.getLiquidScaled(49);
			drawTexturedModalRect(w + 8, h + 65 - scale, 185, 31, 15, 6);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		drawCenteredString(this.fontRenderer,barrel.getType(),88,7,0x555555);
	}

	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}


}
