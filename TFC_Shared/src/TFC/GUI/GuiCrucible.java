package TFC.GUI;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.API.TFCOptions;
import TFC.Containers.ContainerCrucible;
import TFC.Core.TFC_Core;
import TFC.Core.Util.StringUtil;
import TFC.TileEntities.TECrucible;


public class GuiCrucible extends GuiContainer
{
	private TECrucible te;


	public GuiCrucible(InventoryPlayer inventoryplayer, TECrucible tileEntity, World world, int x, int y, int z)
	{
		super(new ContainerCrucible(inventoryplayer,tileEntity, world, x, y, z) );
		te = tileEntity;
		this.xSize = 175;
		this.ySize = 193;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		TFC_Core.bindTexture(new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_crucible.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		int w = (width - xSize) / 2;
		int h = (height - ySize) / 2;
		drawTexturedModalRect(w, h, 0, 0, xSize, ySize);

		int scale = 0;

		scale = te.getTemperatureScaled(49);
		drawTexturedModalRect(w + 153, h + 82 - scale, 185, 0, 15, 6);

		scale = te.getOutCountScaled(100);
		drawTexturedModalRect(w + 129, h + 106 - scale, 177, 6, 8, scale);

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		if(te.currentAlloy != null)
		{
			if(te.currentAlloy.outputType != null)
			{
				this.fontRenderer.drawString(EnumChatFormatting.UNDERLINE+StringUtil.localize("gui.metal." + te.currentAlloy.outputType.Name.replace(" ", "")),7,7,0x000000);
			} else
			{
				this.fontRenderer.drawString(EnumChatFormatting.UNDERLINE+StringUtil.localize("gui.metal.Unknown"),7,7,0x000000);
			}

			for(int c = 0; c < te.currentAlloy.AlloyIngred.size(); c++)
			{
				double m = te.currentAlloy.AlloyIngred.get(c).metal;
				m = Math.round(m * 100d)/100d;
				if(te.currentAlloy.AlloyIngred.get(c).metalType != null)
				{
					this.fontRenderer.drawString(EnumChatFormatting.DARK_GRAY+StringUtil.localize("gui.metal." + te.currentAlloy.AlloyIngred.get(c).metalType.Name.replace(" ", "")) + 
							": "+ EnumChatFormatting.DARK_GREEN + m + "%", 7,18 + 10 * (c),0x000000);
				}
			}
		}

		if(TFCOptions.enableDebugMode)
		{
			this.fontRenderer.drawString("Temp: " + te.temperature ,178, 8, 0xffffff);
		}
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}


}
