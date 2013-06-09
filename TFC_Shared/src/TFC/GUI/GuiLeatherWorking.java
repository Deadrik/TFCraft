package TFC.GUI;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.Containers.ContainerLeatherWorking;

public class GuiLeatherWorking extends GuiContainer
{
    public GuiLeatherWorking(InventoryPlayer inventoryplayer,ItemStack is, World world, int x, int y, int z)
    {
        super(new ContainerLeatherWorking(inventoryplayer, is, world, x, y, z));
    }

    @Override
	public void onGuiClosed()
    {
        super.onGuiClosed();
    }

    protected void drawGuiContainerForegroundLayer()
    {

    }

    @Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
    	this.mc.renderEngine.bindTexture(Reference.AssetPathGui + "gui_knapping.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
        int w = (width - 176) / 2;
        int h = (height - 184) / 2;
        drawTexturedModalRect(w, h, 0, 0, 175, 183);
    }
}
