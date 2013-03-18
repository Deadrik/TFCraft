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


public class GuiTerraScribe extends GuiContainer
{
	private TileEntityTerraScribe FirepitEntity;


	public GuiTerraScribe(InventoryPlayer inventoryplayer, TileEntityTerraScribe tileentityfirepit, World world, int x, int y, int z)
	{
		super(new ContainerTerraScribe(inventoryplayer,tileentityfirepit, world, x, y, z) );
		FirepitEntity = tileentityfirepit;
		((ContainerTerraScribe)inventorySlots).setGUI(this);
	}

	public void initGui()
	{
		super.initGui();
		//guiLeft = (width - 208) / 2;
		//guiTop = (height - 198) / 2;

		controlList.clear();
		if(FirepitEntity.scribeItemStacks[1]!=null){
			if(FirepitEntity.scribeItemStacks[1].getItem() == TFCItems.writabeBookTFC){
				controlList.add(new GuiButton(0, guiLeft+118, guiTop + 60, 36, 20, "write"));
			}
		}
	}

	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0)
		{
			ItemStack temp = FirepitEntity.scribeItemStacks[1];
			FirepitEntity.scribeItemStacks[1] = null;
			((ContainerTerraScribe)inventorySlots).openBook(temp);
		}

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		int r = mc.renderEngine.getTexture("/bioxx/gui_scribe.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		mc.renderEngine.bindTexture(r);
		int w = (width - 176) / 2;
		int h = (height - 184) / 2;
		drawTexturedModalRect(w, h, 0, 0, 175, 183);
		//        if(sluiceInventory.waterInput && sluiceInventory.waterOutput)
		//        {
		//            int l = 12;//sluiceInventory.getProcessScaled(12); 
		//            drawTexturedModalRect(s + 62, (t + 36 + 12) - l, 176, 12 - l, 14, l + 2);
		//        }
		//        int i1 = sluiceInventory.getProcessScaled(24);
		//       drawTexturedModalRect(s + 79, t + 34, 176, 14, i1+1, 16);
		int i1;// = FirepitEntity.getMaterialScaled(50);
		//(guiX,guiY,sourceMinX,sourceMinY,sourceMaxX,sourceMaxY
		//drawTexturedModalRect(w + 34, h + 16 + i1, 176, 31+i1, 9, 50);
		//i1 = FirepitEntity.getTemperatureScaled(49);
		//drawTexturedModalRect(w + 125, h + 63 - i1, 185, 31, 15, 6);

	}

	protected void drawGuiContainerForegroundLayer()
	{        

	}

	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}


}
