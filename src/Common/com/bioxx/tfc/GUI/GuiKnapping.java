package com.bioxx.tfc.GUI;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Containers.ContainerSpecialCrafting;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;
import com.bioxx.tfc.Handlers.Network.KnappingUpdatePacket;
import com.bioxx.tfc.api.TFCItems;

public class GuiKnapping extends GuiContainerTFC
{
	private boolean previouslyLoaded;
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_knapping.png");

	public GuiKnapping(InventoryPlayer inventoryplayer, ItemStack is, World world, int x, int y, int z)
	{
		super(new ContainerSpecialCrafting(inventoryplayer, is, world, x, y, z), 176, 103);
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

		buttonList.clear();
		((ContainerSpecialCrafting) this.inventorySlots).setDecreasedStack(false);

		for (int y = 0; y < 5; y++)
		{
			for (int x = 0; x < 5; x++)
			{
				buttonList.add(new GuiKnappingButton(x + (y * 5), guiLeft + (x * 16) + 10, guiTop + (y * 16) + 12, 16, 16));
				// Normal Behavior
				if (!previouslyLoaded)
				{
					if (PlayerManagerTFC.getInstance().getClientPlayer().knappingInterface[y * 5 + x])
					{
						resetButton(y * 5 + x);
					}
				}
				// GUI has been reloaded, usually caused by looking up a recipe in NEI while having the interface open.
				else
				{
					/*
					 * For whatever reason all my attempts at implementing this for all crafting types just wouldn't work for the clay ones.
					 * Types that completely remove pieces (rocks, leather) work properly to save states when reloaded with this.
					 */
					if (PlayerManagerTFC.getInstance().getClientPlayer().specialCraftingType.getItem() != TFCItems.flatClay && 
						((ContainerSpecialCrafting) this.inventorySlots).craftMatrix.getStackInSlot(y * 5 + x) == null)
					{
						resetButton(y * 5 + x);
					}
				}
			}
		}

		previouslyLoaded = true;
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		resetButton(guibutton.id);
		AbstractPacket pkt = new KnappingUpdatePacket(guibutton.id);
		TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);
	}

	public void resetButton(int id)
	{
		if(PlayerManagerTFC.getInstance().getClientPlayer().specialCraftingTypeAlternate == null)
		{
			((GuiKnappingButton) this.buttonList.get(id)).visible = false;
		}
		PlayerManagerTFC.getInstance().getClientPlayer().knappingInterface[id] = true;
		((GuiKnappingButton) this.buttonList.get(id)).enabled = false;
		((ContainerSpecialCrafting) this.inventorySlots).craftMatrix.setInventorySlotContents(id, null);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int p, int j)
	{
		drawGui(texture);
	}

	/**
	 * This function is what controls the hotbar shortcut check when you press a
	 * number key when hovering a stack.
	 */
	@Override
	protected boolean checkHotbarKeys(int par1)
	{
		if (this.mc.thePlayer.inventory.currentItem != par1 - 2)
		{
			super.checkHotbarKeys(par1);
			return true;
		}
		else
			return false;
	}
}
