package TFC.GUI;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import TFC.Containers.ContainerTerraWorkbench;
import TFC.TileEntities.TileEntityTerraWorkbench;
import net.minecraft.src.*;

public class GuiTerraWorkbench extends GuiContainerTFC
{
    public GuiTerraWorkbench(InventoryPlayer inventoryplayer, TileEntityTerraWorkbench wb, World world, int i, int j, int k)
    {
        super(new ContainerTerraWorkbench(inventoryplayer, wb,world, i, j, k));
    }

    public void onGuiClosed()
    {
        super.onGuiClosed();
    }

    protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString("Crafting", 28, 6, 0x404040);
        fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        int k = mc.renderEngine.getTexture("/gui/crafting.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(k);
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
    }
}
