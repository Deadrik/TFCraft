package TFC.Core.Player;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.Containers.Slots.SlotForShowOnly;
import TFC.Core.TFC_Core;

public class PlayerInventory
{
	public static int invXSize = 176;
	public static int invYSize = 87;
	private static ResourceLocation invTexture = new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_inventory_lower.png");

	public static void buildInventoryLayout(Container container, InventoryPlayer inventory, int x, int y, boolean freezeSlot, boolean toolBarAfterMainInv)
	{
		if(!toolBarAfterMainInv)
			addToolbarSlots(container, inventory, x, y, freezeSlot);

		for(int i = 0; i < 3; ++i)
		{
			for(int k = 0; k < 9; ++k)
			{
				int index =  k + (i+1) * 9;
				addSlotToContainer(container, new Slot(inventory, index, x + k * 18, y + i * 18));
			}
		}

		if(toolBarAfterMainInv)
			addToolbarSlots(container, inventory, x, y, freezeSlot);
	}

	private static void addToolbarSlots(Container container, InventoryPlayer inventory, int x, int y, boolean freezeSlot) 
	{
		for(int j = 0; j < 9; ++j)
		{
			if(freezeSlot && j == inventory.currentItem)
				addSlotToContainer(container, new SlotForShowOnly(inventory, j, x + j * 18, y+58));
			else
				addSlotToContainer(container, new Slot(inventory, j, x + j * 18, y+58));
		}
	}

	public static void buildInventoryLayout(Container container, InventoryPlayer inventory, int x, int y, boolean freezeSlot)
	{
		buildInventoryLayout(container, inventory, x, y, false, false);
	}

	public static void buildInventoryLayout(Container container, InventoryPlayer inventory, int x, int y)
	{
		buildInventoryLayout(container, inventory, x, y, false);
	}

	protected static Slot addSlotToContainer(Container container, Slot par1Slot)
	{
		par1Slot.slotNumber = container.inventorySlots.size();
		container.inventorySlots.add(par1Slot);
		container.inventoryItemStacks.add((Object)null);
		return par1Slot;
	}

	public static void drawInventory(GuiContainer container, int screenWidth, int screenHeight, int upperGuiHeight)
	{
		TFC_Core.bindTexture(invTexture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int l = (screenWidth - invXSize) / 2;
		int i1 = (screenHeight - (upperGuiHeight+invYSize)) / 2 + upperGuiHeight;
		container.drawTexturedModalRect(l, i1, 0, 0, invXSize, invYSize);
	}

	public static void upgradePlayerCrafting(EntityPlayer player)
	{
		if(player.getEntityData().hasKey("craftingTable"))
		{
			player.inventoryContainer.getSlot(45).xDisplayPosition += 50000;
			player.inventoryContainer.getSlot(46).xDisplayPosition += 50000;
			player.inventoryContainer.getSlot(47).xDisplayPosition += 50000;
			player.inventoryContainer.getSlot(48).xDisplayPosition += 50000;
			player.inventoryContainer.getSlot(49).xDisplayPosition += 50000;
		}
	}
}
