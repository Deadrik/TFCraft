package com.bioxx.tfc.GUI;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Containers.ContainerSpecialCrafting;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Handlers.Network.KnappingUpdatePacket;

public class GuiKnapping extends GuiContainer
{
	public GuiKnapping(InventoryPlayer inventoryplayer, ItemStack is, World world, int x, int y, int z)
	{
		super(new ContainerSpecialCrafting(inventoryplayer, is, world, x, y, z));
		this.xSize = 176;
		this.ySize = 103 + PlayerInventory.invYSize;
	}

	@Override
	public void onGuiClosed()
	{
		PlayerManagerTFC.getInstance().getClientPlayer().knappingInterface = new boolean[25];
		super.onGuiClosed();
	}

	@Override
	public void initGui()
	{
		super.initGui();
		boolean[] knappingInterface = PlayerManagerTFC.getInstance().getClientPlayer().knappingInterface;

		buttonList.clear();

		for (int y = 0; y < 5; y++)
		{
			for (int x = 0; x < 5; x++)
			{
				buttonList.add(new GuiKnappingButton(x + (y * 5), guiLeft + (x * 16) + 10, guiTop + (y * 16) + 12, 16, 16));
				if(PlayerManagerTFC.getInstance().getClientPlayer().knappingInterface[(y * 5) + x])
				{
					resetButton((y * 5) + x);
				}
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		resetButton(guibutton.id);
		TerraFirmaCraft.packetPipeline.sendToServer(new KnappingUpdatePacket(guibutton.id));
	}

	public void resetButton(int id)
	{
		if(PlayerManagerTFC.getInstance().getClientPlayer().specialCraftingTypeAlternate == null)
		{
			((GuiKnappingButton) this.buttonList.get(id)).visible = false;
		}
		PlayerManagerTFC.getInstance().getClientPlayer().knappingInterface[id] = true;
		((GuiKnappingButton) this.buttonList.get(id)).enabled = false;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int p, int j)
	{
		TFC_Core.bindTexture(new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_knapping.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		int w = (width - xSize) / 2;
		int h = (height - ySize) / 2;
		drawTexturedModalRect(w, h, 0, 0, xSize, ySize);

		PlayerInventory.drawInventory(this, width, height, ySize - PlayerInventory.invYSize);
	}
}
