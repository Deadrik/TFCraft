package net.minecraft.src.TFC_Game;

import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.TFCHeat;

public class ContainerTerraMetallurgy extends Container
{
	private TileEntityTerraMetallurgy terrametallurgy;
	public InventoryCrafting craftMatrix;
	public IInventory craftResult;
	private World worldObj;

	public ContainerTerraMetallurgy(InventoryPlayer inventoryplayer, TileEntityTerraMetallurgy scribe, World world)
	{
		terrametallurgy = scribe;
		craftMatrix = new InventoryCrafting(this, 5, 5);
		craftResult = new InventoryCraftResult();
		worldObj = world;
		//output
		addSlot(new SlotCraftingMetal(inventoryplayer.player, craftMatrix, craftResult,0, 128, 35));

		for (int l = 0; l < 5; l++)
		{
			for (int k1 = 0; k1 < 5; k1++)
			{
				addSlot(new SlotMetal(craftMatrix, k1 + l * 5, 8 + k1 * 18, l * 18 - 1));
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
		float temp = terrametallurgy.checkTemps(craftMatrix);
		if(temp >= 0)
		{
			ItemStack stack = CraftingManagerTFC.getInstance().findMatchingRecipe(craftMatrix);
			if (stack != null)
			{
				if(!stack.hasTagCompound()) {
					stack.setTagCompound(new NBTTagCompound());
				}

				stack.stackTagCompound.setFloat("temperature", temp);
				if(stack.stackSize <= 0) {
					stack.stackSize = 1;
				}
			}
			craftResult.setInventorySlotContents(0, stack);
		}
	}

	public ItemStack transferStackInSlot(int i)
	{
		Slot slot = (Slot)inventorySlots.get(i);
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
			else if(i <= 25)
			{
//				if(!ModLoader.getMinecraftInstance().thePlayer.inventory.addItemStackToInventory(itemstack1.copy()))
//				{
//					return null;
//				}
				slot.putStack(null);
			}
			else if(itemstack1.getItem() instanceof ItemTerraMeltedMetal)
			{
				int j = 0;
				while(j < 25)
				{
					if(((Slot)inventorySlots.get(j + 1)).getHasStack())
					{
						j++;
					}
					else
					{
						ItemStack stack = itemstack1.copy();
						stack.stackSize = 1;
						((Slot)inventorySlots.get(j + 1)).putStack(stack);
						itemstack1.stackSize--;
						break;
					}
				}
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
