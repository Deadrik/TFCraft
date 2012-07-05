package net.minecraft.src.TFC_Core.GUI;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.TileEntityTerraWorkbench;
import net.minecraft.src.TFC_Core.Containers.ContainerKnapping;
import net.minecraft.src.TFC_Core.Containers.ContainerTerraWorkbench;

public class GuiKnapping extends GuiContainerTFC
{
    public GuiKnapping(InventoryPlayer inventoryplayer,ItemStack is, World world)
    {
        super(new ContainerKnapping(inventoryplayer, is, world));
    }

    public void onGuiClosed()
    {
        super.onGuiClosed();
    }

    protected void drawGuiContainerForegroundLayer()
    {

    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        int r = mc.renderEngine.getTexture("/bioxx/knappinggui.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
        mc.renderEngine.bindTexture(r);
        int w = (width - 176) / 2;
        int h = (height - 184) / 2;
        drawTexturedModalRect(w, h, 0, 0, 175, 183);
    }
}
