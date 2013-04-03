package TFC.GUI;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.TFCItems;
import TFC.Containers.ContainerTerraScribe;
import TFC.TileEntities.TileEntityTerraScribe;


public class GuiTerraScribe extends GuiContainer
{
	private TileEntityTerraScribe FirepitEntity;


	public GuiTerraScribe(InventoryPlayer inventoryplayer, TileEntityTerraScribe tileentityfirepit, World world, int x, int y, int z)
	{
		super(new ContainerTerraScribe(inventoryplayer,tileentityfirepit, world, x, y, z) );
		FirepitEntity = tileentityfirepit;
		((ContainerTerraScribe)inventorySlots).setGUI(this);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		//guiLeft = (width - 208) / 2;
		//guiTop = (height - 198) / 2;

		buttonList.clear();
		if(FirepitEntity.scribeItemStacks[1]!=null){
			if(FirepitEntity.scribeItemStacks[1].getItem() == TFCItems.writabeBookTFC){
				buttonList.add(new GuiButton(0, guiLeft+118, guiTop + 60, 36, 20, "write"));
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		/*if (guibutton.id == 0)
		{
			ItemStack temp = FirepitEntity.scribeItemStacks[1];
			FirepitEntity.scribeItemStacks[1] = null;
			((ContainerTerraScribe)inventorySlots).openBook(temp);
		}*/

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.mc.renderEngine.bindTexture("/bioxx/gui_scribe.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
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

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}


}
