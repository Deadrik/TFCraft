package TFC.GUI;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import TFC.Containers.ContainerFoodPrep;
import TFC.Containers.ContainerTerraLogPile;
import TFC.TileEntities.TileEntityFoodPrep;
import TFC.TileEntities.TileEntityTerraLogPile;
import net.minecraft.src.*;

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
        int k = mc.renderEngine.getTexture("/bioxx/foodprepgui.png");
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
