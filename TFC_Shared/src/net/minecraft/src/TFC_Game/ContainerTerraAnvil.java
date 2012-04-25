package net.minecraft.src.TFC_Game;

import net.minecraft.src.*;

public class ContainerTerraAnvil extends Container
{
	private TileEntityTerraAnvil terraanvil;

	public ContainerTerraAnvil(InventoryPlayer inventoryplayer, TileEntityTerraAnvil anvil)
	{
		terraanvil = anvil;

		//Hammer slot
		addSlot(new SlotAnvilHammer(inventoryplayer.player, anvil, 0, 6, 95));
		//input item slot
		addSlot(new Slot(anvil, 1, 87, 6));
		//blueprint slot
		addSlot(new Slot(anvil, 5, 105, 6));
		//Weld slots
		addSlot(new Slot(anvil,  2, 87, 33));
		addSlot(new Slot(anvil,  3, 105, 33));
		addSlot(new SlotAnvilWeldOut(inventoryplayer.player, anvil, 4, 96, 55));
		addSlot(new SlotAnvilFlux(inventoryplayer.player, anvil, 7, 186, 95));



		for(int i = 0; i < 3; i++)
		{
			for(int k = 0; k < 9; k++)
			{
				int m = k + i * 9 + 9;
				addSlot(new Slot(inventoryplayer, m, 24 + k * 18, 116 + i * 18));
			}

		}

		for(int j = 0; j < 9; j++)
		{
			addSlot(new Slot(inventoryplayer, j, 24 + j * 18, 174));
		}

	}

	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}


	public ItemStack transferStackInSlot(int i)
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
				//commented out in order to get everything to compile
//				if(!ModLoader.getMinecraftInstance().thePlayer.inventory.addItemStackToInventory(itemstack1.copy()))
//				{
//					return null;
//				}
				slot.putStack(null);
			}
			else
			{
				if(itemstack1.itemID == mod_TFC_Game.Flux.shiftedIndex)
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
				else if(itemstack1.itemID == mod_TFC_Game.HammerHeadPlan.shiftedIndex || itemstack1.itemID == mod_TFC_Game.SawBladePlan.shiftedIndex ||
						itemstack1.itemID == mod_TFC_Game.PickaxeHeadPlan.shiftedIndex || itemstack1.itemID == mod_TFC_Game.ProPickBladePlan.shiftedIndex ||
						itemstack1.itemID == mod_TFC_Game.ChiselHeadPlan.shiftedIndex || itemstack1.itemID == mod_TFC_Game.AxeHeadPlan.shiftedIndex ||
						itemstack1.itemID == mod_TFC_Game.SwordBladePlan.shiftedIndex || itemstack1.itemID == mod_TFC_Game.HoeHeadPlan.shiftedIndex ||
						itemstack1.itemID == mod_TFC_Game.ShovelHeadPlan.shiftedIndex || itemstack1.itemID == mod_TFC_Game.MaceHeadPlan.shiftedIndex)
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



}
