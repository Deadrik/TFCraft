package TFC.GUI;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.Containers.ContainerForge;
import TFC.Core.TFC_Core;
import TFC.TileEntities.TileEntityForge;


public class GuiForge extends GuiContainer
{
	private TileEntityForge ForgeEntity;


	public GuiForge(InventoryPlayer inventoryplayer, TileEntityForge tileentityforge, World world, int x, int y, int z)
	{
		super(new ContainerForge(inventoryplayer,tileentityforge, world, x, y, z) );
		ForgeEntity = tileentityforge;

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		TFC_Core.bindTexture(new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_forge.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		int w = (width - xSize) / 2;
		int h = (height - ySize) / 2;
		drawTexturedModalRect(w, h, 0, 0, xSize, ySize);

		int i1 = ForgeEntity.getTemperatureScaled(49);
		drawTexturedModalRect(w + 8, h + 65 - i1, 185, 31, 15, 6);
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}
}
