package com.bioxx.tfc.GUI;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerForge;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.TileEntities.TEForge;

public class GuiForge extends GuiContainer
{
	private TEForge ForgeEntity;


	public GuiForge(InventoryPlayer inventoryplayer, TEForge tileentityforge, World world, int x, int y, int z)
	{
		super(new ContainerForge(inventoryplayer,tileentityforge, world, x, y, z) );
		ForgeEntity = tileentityforge;
		xSize = 176;
		ySize = 85 + PlayerInventory.invYSize;

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

		PlayerInventory.drawInventory(this, width, height, ySize-PlayerInventory.invYSize);
	}

	@Override
	protected boolean checkHotbarKeys(int keycode)
	{
		//Disabled to prevent players placing stacks into the forge
		// return super.checkHotbarKeys(keycode);
		return false;
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}
}
