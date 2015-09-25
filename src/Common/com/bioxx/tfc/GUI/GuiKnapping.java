package com.bioxx.tfc.GUI;

import java.lang.reflect.Field;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.relauncher.ReflectionHelper;

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
	private final Field _selectedButton = ReflectionHelper.findField(GuiScreen.class, "selectedButton", "field_146290_a");

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

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
	{
		if (clickedMouseButton == 0)
		{
			for (int i = 0; i < this.buttonList.size(); i++)
			{
				if (this.buttonList.get(i) instanceof GuiButton)
				{
					GuiButton guiButton = (GuiButton) this.buttonList.get(i);

					if (guiButton.mousePressed(this.mc, mouseX, mouseY))
					{
						try
						{
							ActionPerformedEvent.Pre event = new ActionPerformedEvent.Pre(this, guiButton, this.buttonList);
							if (MinecraftForge.EVENT_BUS.post(event))
								break;
							else if (_selectedButton.get(this) == event.button)
								continue;
							else
								this.mouseMovedOrUp(mouseX, mouseY, 0);

							_selectedButton.set(this, event.button);
							event.button.func_146113_a(this.mc.getSoundHandler()); // Play Press Sound
							this.actionPerformed(event.button);
							if (this.equals(this.mc.currentScreen))
								MinecraftForge.EVENT_BUS.post(new ActionPerformedEvent.Post(this, event.button, this.buttonList));
						} catch (Exception e)
						{
							throw new RuntimeException(e);
						}
					}

				}
			}
		}
	}
}
