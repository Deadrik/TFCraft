package TFC.GUI;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import TFC.*;
import TFC.Containers.*;
import TFC.TileEntities.*;
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

public class GuiFoodPrep extends GuiContainer
{
	TileEntityFoodPrep table;
	
    public GuiFoodPrep(InventoryPlayer inventoryplayer, TileEntityFoodPrep wb, World world, int i, int j, int k)
    {
        super(new ContainerFoodPrep(inventoryplayer, wb,world, i, j, k));
        table = wb;
    }

    public void onGuiClosed()
    {
        super.onGuiClosed();
    }

    protected void drawGuiContainerForegroundLayer()
    {
    	//drawCenteredString(fontRenderer,"Log Pile", 87, 6, 0x000000);
        //fontRenderer.drawString("Log Pile", 28, 6, 0x404040);
        fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        int k = mc.renderEngine.getTexture("/bioxx/gui_foodprep.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(k);
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
    }
    
    public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
    {
        fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
    }
    
    public void initGui()
	{
		super.initGui();
		controlList.clear();

		controlList.add(new GuiButton(0, guiLeft+105, guiTop + 52, 66, 20, "Create Meal"));

	}

	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0)
		{
			table.actionCreate();
		}
	}
}
