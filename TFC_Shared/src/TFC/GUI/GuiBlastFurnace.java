package TFC.GUI;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.API.TFCOptions;
import TFC.Containers.ContainerBlastFurnace;
import TFC.Core.TFC_Core;
import TFC.Core.Util.StringUtil;
import TFC.TileEntities.TEBlastFurnace;


public class GuiBlastFurnace extends GuiContainer
{
	private TEBlastFurnace bloomery;


	public GuiBlastFurnace(InventoryPlayer inventoryplayer, TEBlastFurnace tileentityforge, World world, int x, int y, int z)
	{
		super(new ContainerBlastFurnace(inventoryplayer,tileentityforge, world, x, y, z) );
		bloomery = tileentityforge;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		TFC_Core.bindTexture(new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_bloomery.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		int w = (width - xSize) / 2;
		int h = (height - ySize) / 2;
		drawTexturedModalRect(w, h, 0, 0, xSize, ySize);

		int scale = 0;

		scale = bloomery.getTemperatureScaled(49);
		drawTexturedModalRect(w + 8, h + 65 - scale, 185, 31, 15, 6);

		scale = bloomery.getOreCountScaled(80);
		drawTexturedModalRect(w + 40, h + 25, 176, 0, scale+1, 8);

		scale = bloomery.getCharcoalCountScaled(80);
		drawTexturedModalRect(w + 40, h + 43, 176, 0, scale+1, 8);

		/*scale = bloomery.getOutCountScaled(80);
        drawTexturedModalRect(w + 40, h + 61, 176, 0, scale+1, 8);
        drawTexturedModalRect(w + 40, h + 61, 176, 8, scale+1, 8);*/
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		//drawCenteredString(this.fontRenderer,StringUtil.localize("item.Ore." + bloomery.OreType.replace(" ", "")),88,7,0x555555);
		this.fontRenderer.drawString(StringUtil.localize("gui.Bloomery.Ore") ,40, 17, 0x000000);
		this.fontRenderer.drawString(StringUtil.localize("gui.Bloomery.Charcoal") ,40, 35, 0x000000);
		this.fontRenderer.drawString(StringUtil.localize("gui.Bloomery.Output"),40, 53, 0x000000);
		if(TFCOptions.enableDebugMode)
		{
			this.fontRenderer.drawString("Temp : " + bloomery.fireTemperature ,40, 71, 0x000000);
		}
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}


}
