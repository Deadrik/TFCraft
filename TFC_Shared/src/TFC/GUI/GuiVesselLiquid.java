package TFC.GUI;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.Containers.ContainerLiquidVessel;
import TFC.Core.Util.StringUtil;

public class GuiVesselLiquid extends GuiContainer
{
	EntityPlayer player;
	private int bagsSlotNum;
	
    public GuiVesselLiquid(InventoryPlayer inventoryplayer, World world, int i, int j, int k)
    {
        super(new ContainerLiquidVessel(inventoryplayer, world, i, j, k));
        player = inventoryplayer.player;
        bagsSlotNum = player.inventory.currentItem;
    }

    @Override
	public void onGuiClosed()
    {
        super.onGuiClosed();
    }

    protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString(StringUtil.localize("gui.Inventory"), 8, (ySize - 96) + 2, 0x404040);
    }

    @Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
    	this.mc.func_110434_K().func_110577_a(new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_vessel_liquid.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
        if(player.inventory.mainInventory[this.bagsSlotNum].getTagCompound().hasKey("MetalType"))
        	drawCenteredString(this.fontRenderer, player.inventory.mainInventory[this.bagsSlotNum].getTagCompound().getString("MetalType"), l+87, i1+13, 0);
        if(player.inventory.mainInventory[this.bagsSlotNum].getTagCompound().hasKey("MetalAmount"))
        	drawCenteredString(this.fontRenderer, player.inventory.mainInventory[this.bagsSlotNum].getTagCompound().getInteger("MetalAmount")+" Units", l+87, i1+23, 0);
    }
    
    @Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
    {
        fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
    }
}
