package TFC.Containers;

import TFC.*;
import TFC.TileEntities.TileEntityQuern;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;

public class ContainerQuern extends Container
{
	private World world;
	private int posX;
	private int posY;
	private int posZ;
	private TileEntityQuern te;
	private EntityPlayer player;

	public ContainerQuern(InventoryPlayer playerinv, TileEntityQuern pile, World world, int x, int y, int z)
	{
		this.player = playerinv.player;
		this.te = pile;
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		pile.openChest();

		layoutContainer(playerinv, pile, 0, 0);
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	@Override
	public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
	{
		super.onCraftGuiClosed(par1EntityPlayer);

		if(!world.isRemote)
		{
			te.closeChest();
		}
		else
		{
			te.validate();
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize) 
	{
		this.addSlotToContainer(new SlotQuernGrain(chestInventory, 0, 66, 47));
		this.addSlotToContainer(new SlotBlocked(chestInventory, 1, 93, 47));
		this.addSlotToContainer(new SlotQuern(chestInventory, 2, 93, 20));

		int row;
		int col;

		for (row = 0; row < 9; ++row)
		{
			this.addSlotToContainer(new Slot(playerInventory, row, 8 + row * 18, 142));
		}

		for (row = 0; row < 3; ++row)
		{
			for (col = 0; col < 9; ++col)
			{
				this.addSlotToContainer(new Slot(playerInventory, col + row * 9+9, 8 + col * 18, 84 + row * 18));
			}
		}


	}

	public EntityPlayer getPlayer() {
		return player;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int clickedIndex)
	{
		ItemStack returnedStack = null;
		Slot clickedSlot = (Slot)this.inventorySlots.get(clickedIndex);

		if (clickedSlot != null && clickedSlot.getHasStack())
		{
			ItemStack clickedStack = clickedSlot.getStack();
			returnedStack = clickedStack.copy();

			if (clickedIndex < 3)
			{
				if (!this.mergeItemStack(clickedStack, 3, 28, true))
				{
					return null;
				}

			}
			else if (clickedIndex >= 3 && clickedIndex < 28 && clickedStack.getItem() == TFCItems.WheatGrain || clickedStack.getItem() == TFCItems.BarleyGrain || 
					clickedStack.getItem() == TFCItems.RyeGrain || clickedStack.getItem() == TFCItems.OatGrain || 
					clickedStack.getItem() == TFCItems.RiceGrain || clickedStack.getItem() == TFCItems.MaizeEar)
			{
				if (!this.mergeItemStack(clickedStack, 0, 1, false))
				{
					return null;
				}
			}
			else if (clickedIndex >= 3 && clickedIndex < 28 && clickedStack.getItem() == TFCItems.Quern)
			{
				if (!this.mergeItemStack(clickedStack, 2, 3, false))
				{
					return null;
				}
			}
			else if (clickedIndex >= 3 && clickedIndex < 28)
			{
				return null;
			}

			if (clickedStack.stackSize == 0)
			{
				clickedSlot.putStack((ItemStack)null);
			}
			else
			{
				clickedSlot.onSlotChanged();
			}

			if (clickedStack.stackSize == returnedStack.stackSize)
			{
				return null;
			}

			clickedSlot.onPickupFromSlot(player, clickedStack);
		}

		return returnedStack;
	}
}
