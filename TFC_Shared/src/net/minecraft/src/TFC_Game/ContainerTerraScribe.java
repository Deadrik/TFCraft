package net.minecraft.src.TFC_Game;

import net.minecraft.src.*;

public class ContainerTerraScribe extends Container
{
	private TileEntityTerraScribe terraScribe;
	public InventoryCrafting craftMatrix;
	public IInventory craftResult;
	private World worldObj;

	public ContainerTerraScribe(InventoryPlayer inventoryplayer, TileEntityTerraScribe scribe, World world)
	{
		terraScribe = scribe;
		craftMatrix = new InventoryCrafting(this, 5, 5);
		craftResult = new InventoryCraftResult();
		worldObj = world;
		//output
		addSlot(new SlotCraftingScribe(inventoryplayer.player, craftMatrix, craftResult,terraScribe, 0, 128, 35));
		//paper
		addSlot(new SlotScribePaper(inventoryplayer.player,scribe, 1, 128, -1));

		for (int l = 0; l < 5; l++)
		{
			for (int k1 = 0; k1 < 5; k1++)
			{
				addSlot(new SlotScribeCrafting(inventoryplayer.player,craftMatrix, k1 + l * 5, 8 + k1 * 18, l * 18 - 1));
			}
		}

		for(int i = 0; i < 3; i++)
		{
			for(int k = 0; k < 9; k++)
			{
				addSlot(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 93 + i * 18));
			}

		}

		for(int j = 0; j < 9; j++)
		{
			addSlot(new Slot(inventoryplayer, j, 8 + j * 18, 151));
		}


		onCraftMatrixChanged(craftMatrix);
	}

	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	public void onCraftGuiClosed(EntityPlayer entityplayer)
	{
		super.onCraftGuiClosed(entityplayer);
		if (worldObj.isRemote)
		{
			return;
		}
		for (int i = 0; i < 25; i++)
		{
			ItemStack itemstack = craftMatrix.getStackInSlot(i);
			if (itemstack != null)
			{
				entityplayer.dropPlayerItem(itemstack);
			}
		}
	}

	public void onCraftMatrixChanged(IInventory iinventory)
	{
		if(terraScribe.scribeItemStacks[1] != null && 
				terraScribe.scribeItemStacks[1].getItem() == Item.paper) {
			craftResult.setInventorySlotContents(0, CraftingManagerTFC.getInstance().findMatchingRecipe(craftMatrix));
		}
	}

	public ItemStack transferStackInSlot(int i)
	{
		Slot slot = (Slot)inventorySlots.get(i);
		Slot slotpaper = (Slot)inventorySlots.get(1);
		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			if(i == 0)
			{
//				if(!ModLoader.getMinecraftInstance().thePlayer.inventory.addItemStackToInventory(itemstack1.copy()))
//				{
//					return null;
//				}
				slot.putStack(null);
				for (int j = 1; j <= 25; j++)
				{
					((Slot)inventorySlots.get(j)).putStack(null);
				}
			}
			if(i <= 26)
			{
//				if(!ModLoader.getMinecraftInstance().thePlayer.inventory.addItemStackToInventory(itemstack1.copy()))
//				{
//					return null;
//				}
				slot.putStack(null);
			}
			else if(itemstack1.itemID == Item.paper.shiftedIndex)
			{
				if(slotpaper.getHasStack())
				{
					return null;
				}
				ItemStack stack = itemstack1.copy();
				stack.stackSize = 1;
				slotpaper.putStack(stack);
				itemstack1.stackSize--;
			}
			if(itemstack1.stackSize == 0)
			{
				slot.putStack(null);
			} else
			{
				slot.onSlotChanged();
			}
		}
		return null;
	}

}
