package com.bioxx.tfc.GUI;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerGrill;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.TileEntities.TEGrill;
import com.bioxx.tfc.api.TileEntities.TEFireEntity;


public class GuiGrill extends GuiContainer
{
	private TEGrill grill;
	private TEFireEntity fire;

	public GuiGrill(InventoryPlayer inventoryplayer, TEGrill te, World world, int x, int y, int z)
	{
		super(new ContainerGrill(inventoryplayer,te, world, x, y, z));
		grill = te;
		if(world.getTileEntity(x, y-1, z) instanceof TEFireEntity)
			fire = (TEFireEntity) world.getTileEntity(x, y-1, z);
		xSize = 176;
		ySize = 85+PlayerInventory.invYSize;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		TFC_Core.bindTexture(new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_grill.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		int w = (width - xSize) / 2;
		int h = (height - ySize) / 2;
		drawTexturedModalRect(w, h, 0, 0, xSize, ySize);
		int tempScaled = 0;
		if(fire != null)
			tempScaled = fire.getTemperatureScaled(49);
		drawTexturedModalRect(w + 7, h + 65 - tempScaled, 0, 86, 15, 6);

		PlayerInventory.drawInventory(this, width, height, ySize-PlayerInventory.invYSize);
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
