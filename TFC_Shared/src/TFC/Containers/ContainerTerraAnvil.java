package TFC.Containers;

import TFC.Core.AnvilReq;
import TFC.Core.TFCItems;
import TFC.Core.TFC_Core;
import TFC.Items.ItemHammer;
import TFC.TileEntities.TileEntityTerraAnvil;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.TFCWorldChunkManager;
import net.minecraft.src.*;

public class ContainerTerraAnvil extends Container
{
	private TileEntityTerraAnvil anvil;
	private int greenIndicator;
	private int redIndicator;

	public ContainerTerraAnvil(InventoryPlayer inventoryplayer, TileEntityTerraAnvil anvil, World world, int x, int y, int z)
	{
		this.anvil = anvil;

		redIndicator = -1000;
		greenIndicator = -1000;

		//Hammer slot
		addSlotToContainer(new SlotAnvilHammer(inventoryplayer.player, anvil, 0, 6, 95));
		//input item slot
		addSlotToContainer(new Slot(anvil, 1, 87, 6));

		//Weld slots
		addSlotToContainer(new Slot(anvil,  2, 87, 33));
		addSlotToContainer(new Slot(anvil,  3, 105, 33));
		addSlotToContainer(new SlotAnvilWeldOut(inventoryplayer.player, anvil, 4, 96, 55));
		//blueprint slot
		addSlotToContainer(new Slot(anvil, 5, 105, 6));
		//flux slot
		addSlotToContainer(new SlotAnvilFlux(inventoryplayer.player, anvil, 6, 186, 95));

		for(int i = 0; i < 3; i++)
		{
			for(int k = 0; k < 9; k++)
			{
				int m = k + i * 9 + 9;
				addSlotToContainer(new Slot(inventoryplayer, m, 24 + k * 18, 116 + i * 18));
			}

		}

		for(int j = 0; j < 9; j++)
		{
			addSlotToContainer(new Slot(inventoryplayer, j, 24 + j * 18, 174));
		}

	}

	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}


	@Override
	public ItemStack slotClick(int i, int j, boolean flag, EntityPlayer entityplayer)
	{
		ItemStack itemstack = null;
		if (j > 1)
		{
			return null;
		}
		if (j == 0 || j == 1)
		{
			InventoryPlayer inventoryplayer = entityplayer.inventory;
			if (i == -999)
			{
				if (inventoryplayer.getItemStack() != null && i == -999)
				{
					if (j == 0)
					{
						entityplayer.dropPlayerItem(inventoryplayer.getItemStack());
						inventoryplayer.setItemStack(null);
					}
					if (j == 1)
					{
						entityplayer.dropPlayerItem(inventoryplayer.getItemStack().splitStack(1));
						if (inventoryplayer.getItemStack().stackSize == 0)
						{
							inventoryplayer.setItemStack(null);
						}
					}
				}
			}
			else if (flag)
			{
				ItemStack itemstack1 = playerTransferStackInSlot(i, entityplayer);
				if (itemstack1 != null)
				{
					int k = itemstack1.itemID;
					itemstack = itemstack1.copy();
					Slot slot1 = (Slot)inventorySlots.get(i);
					if (slot1 != null && slot1.getStack() != null && slot1.getStack().itemID == k)
					{
						retrySlotClick(i, j, flag, entityplayer);
					}
				}
			}
			else
			{
				if (i < 0)
				{
					return null;
				}
				Slot slot = (Slot)inventorySlots.get(i);
				if (slot != null)
				{
					slot.onSlotChanged();
					ItemStack itemstack2 = slot.getStack();
					ItemStack itemstack3 = inventoryplayer.getItemStack();
					if (itemstack2 != null)
					{
						itemstack = itemstack2.copy();
					}
					if (itemstack2 == null)
					{
						if (itemstack3 != null && slot.isItemValid(itemstack3))
						{
							int l = j != 0 ? 1 : itemstack3.stackSize;
							if (l > slot.getSlotStackLimit())
							{
								l = slot.getSlotStackLimit();
							}
							slot.putStack(itemstack3.splitStack(l));
							if (itemstack3.stackSize == 0)
							{
								inventoryplayer.setItemStack(null);
							}
						}
					}
					else if (itemstack3 == null)
					{
						int i1 = j != 0 ? (itemstack2.stackSize + 1) / 2 : itemstack2.stackSize;
						ItemStack itemstack5 = slot.decrStackSize(i1);
						inventoryplayer.setItemStack(itemstack5);
						if (itemstack2.stackSize == 0)
						{
							slot.putStack(null);
						}
						slot.onPickupFromSlot(inventoryplayer.getItemStack());
					}
					else if (slot.isItemValid(itemstack3))
					{
						if (itemstack2.itemID != itemstack3.itemID || itemstack2.getHasSubtypes() && itemstack2.getItemDamage() != itemstack3.getItemDamage() || !ItemStack.areItemStacksEqual(itemstack2, itemstack3))
						{
							if (itemstack3.stackSize <= slot.getSlotStackLimit())
							{
								ItemStack itemstack4 = itemstack2;
								slot.putStack(itemstack3);
								inventoryplayer.setItemStack(itemstack4);
							}
						}
						else
						{
							int j1 = j != 0 ? 1 : itemstack3.stackSize;
							if (j1 > slot.getSlotStackLimit() - itemstack2.stackSize)
							{
								j1 = slot.getSlotStackLimit() - itemstack2.stackSize;
							}
							if (j1 > itemstack3.getMaxStackSize() - itemstack2.stackSize)
							{
								j1 = itemstack3.getMaxStackSize() - itemstack2.stackSize;
							}
							itemstack3.splitStack(j1);
							if (itemstack3.stackSize == 0)
							{
								inventoryplayer.setItemStack(null);
							}
							itemstack2.stackSize += j1;
						}
					}
					else if (itemstack2.itemID == itemstack3.itemID && itemstack3.getMaxStackSize() > 1 && (!itemstack2.getHasSubtypes() || itemstack2.getItemDamage() == itemstack3.getItemDamage()) && ItemStack.areItemStacksEqual(itemstack2, itemstack3))
					{
						int k1 = itemstack2.stackSize;
						if (k1 > 0 && k1 + itemstack3.stackSize <= itemstack3.getMaxStackSize())
						{
							itemstack3.stackSize += k1;
							itemstack2.splitStack(k1);
							if (itemstack2.stackSize == 0)
							{
								slot.putStack(null);
							}
							slot.onPickupFromSlot(inventoryplayer.getItemStack());
						}
					}
				}
			}
		}
		this.updateCraftingResults();
		return itemstack;
	}

	public ItemStack playerTransferStackInSlot(int i, EntityPlayer entityplayer)
	{
		Slot slot = (Slot)inventorySlots.get(i);
		Slot slothammer = (Slot)inventorySlots.get(0);
		Slot[] slotinput = {(Slot)inventorySlots.get(1), (Slot)inventorySlots.get(3), (Slot)inventorySlots.get(4)};
		Slot slotblueprint = (Slot)inventorySlots.get(2);
		Slot slotflux = (Slot)inventorySlots.get(6);
		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			if(i <= 6)
			{
				if(!entityplayer.inventory.addItemStackToInventory(itemstack1.copy()))
				{
					return null;
				}
				slot.putStack(null);
			}
			else
			{
				if(itemstack1.itemID == TFCItems.Flux.shiftedIndex)
				{
					if(slotflux.getHasStack())
					{
						return null;
					}
					ItemStack stack = itemstack1.copy();
					stack.stackSize = 1;
					slotflux.putStack(stack);
					itemstack1.stackSize--;
				}
				else if(itemstack1.getItem() instanceof ItemHammer)
				{
					if(slothammer.getHasStack())
					{
						return null;
					}
					ItemStack stack = itemstack1.copy();
					stack.stackSize = 1;
					slothammer.putStack(stack);
					itemstack1.stackSize--;
				}
				else if(itemstack1.itemID == TFCItems.HammerHeadPlan.shiftedIndex || itemstack1.itemID == TFCItems.SawBladePlan.shiftedIndex ||
						itemstack1.itemID == TFCItems.PickaxeHeadPlan.shiftedIndex || itemstack1.itemID == TFCItems.ProPickHeadPlan.shiftedIndex ||
						itemstack1.itemID == TFCItems.ChiselHeadPlan.shiftedIndex || itemstack1.itemID == TFCItems.AxeHeadPlan.shiftedIndex ||
						itemstack1.itemID == TFCItems.SwordBladePlan.shiftedIndex || itemstack1.itemID == TFCItems.HoeHeadPlan.shiftedIndex ||
						itemstack1.itemID == TFCItems.ShovelHeadPlan.shiftedIndex || itemstack1.itemID == TFCItems.MaceHeadPlan.shiftedIndex)
				{
					if(slotblueprint.getHasStack())
					{
						return null;
					}
					ItemStack stack = itemstack1.copy();
					stack.stackSize = 1;
					slotblueprint.putStack(stack);
					itemstack1.stackSize--;
				}
				else
				{
					int j = 0;
					while(j < 3)
					{
						if(slotinput[j].getHasStack())
						{
							j++;
						}
						else
						{
							ItemStack stack = itemstack1.copy();
							stack.stackSize = 1;
							slotinput[j].putStack(stack);
							itemstack1.stackSize--;
							break;
						}
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

	private int tier = -1;

	public void updateCraftingResults()
	{
		super.updateCraftingResults();

		for (int var1 = 0; var1 < this.crafters.size(); ++var1)
		{
			ICrafting var2 = (ICrafting)this.crafters.get(var1);
			int cv = anvil.getCraftingValue();
			int icv = anvil.getItemCraftingValueNoSet(1);
			int t = this.anvil.AnvilTier;

			if (this.redIndicator != cv)
			{
				var2.updateCraftingInventoryInfo(this, 0, cv);
			}
			if (this.greenIndicator != icv)
			{
				var2.updateCraftingInventoryInfo(this, 1, icv);
			}
			if (this.tier != t)
			{
				var2.updateCraftingInventoryInfo(this, 2, t);
			}
		}

		redIndicator = anvil.craftingValue;
		greenIndicator = anvil.itemCraftingValue;
		this.tier = this.anvil.AnvilTier;
	}

	/**
	 * This is needed to make sure that something is done when 
	 * the client receives the updated progress bar
	 * */
	 public void updateProgressBar(int par1, int par2)
	{
		if(anvil != null)
		{
			if (par1 == 0)
			{
				this.anvil.craftingValue = par2;
			}
			else if (par1 == 1)
			{
				this.anvil.itemCraftingValue = par2;
			}
			else if (par1 == 2)
			{
				this.anvil.AnvilTier = par2;
			}
		}

	}
	public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
	{
		if(anvil.AnvilTier == AnvilReq.STONE.Tier)
		{
			//anvil.ejectContents();            
			DataLayer rockLayer1 = ((TFCWorldChunkManager)anvil.worldObj.getWorldChunkManager()).getRockLayerAt(anvil.xCoord, anvil.zCoord, TFC_Core.getRockLayerFromHeight(anvil.yCoord));

			anvil.worldObj.setBlockAndMetadata(anvil.xCoord, anvil.yCoord, anvil.zCoord, rockLayer1.data1, rockLayer1.data2);

		}
		super.onCraftGuiClosed(par1EntityPlayer);
	}

}
