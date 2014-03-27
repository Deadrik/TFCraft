package TFC.GUI;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.TFCItems;
import TFC.Containers.ContainerScribe;
import TFC.Core.TFC_Core;
import TFC.Core.Util.StringUtil;
import TFC.TileEntities.TileEntityScribe;


public class GuiScribe extends GuiContainer
{
	private TileEntityScribe FirepitEntity;


	public GuiScribe(InventoryPlayer inventoryplayer, TileEntityScribe tileentityfirepit, World world, int x, int y, int z)
	{
		super(new ContainerScribe(inventoryplayer,tileentityfirepit, world, x, y, z) );
		FirepitEntity = tileentityfirepit;
		((ContainerScribe)inventorySlots).setGUI(this);
		this.xSize = 176;
		this.ySize = 184;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		buttonList.clear();
		if(FirepitEntity.scribeItemStacks[1]!=null){
			if(FirepitEntity.scribeItemStacks[1].getItem() == TFCItems.writabeBookTFC){
				buttonList.add(new GuiButton(0, guiLeft+118, guiTop + 60, 36, 20, StringUtil.localize("gui.Scribe.Write")));
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
		TFC_Core.bindTexture(new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_scribe.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		int w = (width - xSize) / 2;
		int h = (height - ySize) / 2;
		drawTexturedModalRect(w, h, 0, 0, 175, 183);

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
