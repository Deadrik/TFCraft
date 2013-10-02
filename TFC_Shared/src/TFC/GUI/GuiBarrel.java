package TFC.GUI;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.Containers.ContainerBarrel;
import TFC.Core.TFC_Core;
import TFC.Core.Util.StringUtil;
import TFC.TileEntities.TileEntityBarrel;

public class GuiBarrel extends GuiContainer
{
	private TileEntityBarrel barrel;
	private EntityPlayer player;

	public GuiBarrel(InventoryPlayer inventoryplayer, TileEntityBarrel tileentitybarrel, World world, int x, int y, int z)
	{
		super(new ContainerBarrel(inventoryplayer,tileentitybarrel, world, x, y, z) );
		barrel = tileentitybarrel;
		player = inventoryplayer.player;
		guiLeft = (width - 208) / 2;
		guiTop = (height - 198) / 2;

		//controlList.clear();

		//controlList.add(new GuiButton(0, guiLeft+86, guiTop + 74, 36, 20, "\2474Seal"));
	}

	@Override
	public void initGui()
	{
		super.initGui();
		//guiLeft = (width - 208) / 2;
		//guiTop = (height - 198) / 2;

		buttonList.clear();

		buttonList.add(new GuiButton(0, guiLeft+38, guiTop + 50, 50, 20, StringUtil.localize("gui.Barrel.Seal")));
		buttonList.add(new GuiButton(1, guiLeft+88, guiTop + 50, 50, 20, StringUtil.localize("gui.Barrel.Empty")));
	}

	@Override
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
		TFC_Core.bindTexture(new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_barrel.png"));
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

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}


}
