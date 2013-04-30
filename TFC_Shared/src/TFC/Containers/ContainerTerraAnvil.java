package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.Items.Tools.ItemHammer;
import TFC.TileEntities.TileEntityAnvil;

public class ContainerTerraAnvil extends ContainerTFC
{
	private TileEntityAnvil anvil;
	private int greenIndicator;
	private int redIndicator;
	private int tier = -1;

	public ContainerTerraAnvil(InventoryPlayer inventoryplayer, TileEntityAnvil anvil, World world, int x, int y, int z)
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

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i)
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
				if(itemstack1.itemID == TFCItems.Flux.itemID)
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
				else if(itemstack1.itemID == TFCItems.HammerHeadPlan.itemID || itemstack1.itemID == TFCItems.SawBladePlan.itemID ||
						itemstack1.itemID == TFCItems.PickaxeHeadPlan.itemID || itemstack1.itemID == TFCItems.ProPickHeadPlan.itemID ||
						itemstack1.itemID == TFCItems.ChiselHeadPlan.itemID || itemstack1.itemID == TFCItems.AxeHeadPlan.itemID ||
						itemstack1.itemID == TFCItems.SwordBladePlan.itemID || itemstack1.itemID == TFCItems.HoeHeadPlan.itemID ||
						itemstack1.itemID == TFCItems.ShovelHeadPlan.itemID || itemstack1.itemID == TFCItems.MaceHeadPlan.itemID)
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
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int var1 = 0; var1 < this.crafters.size(); ++var1)
		{
			ICrafting var2 = (ICrafting)this.crafters.get(var1);
			int cv = anvil.getCraftingValue();
			int icv = anvil.getItemCraftingValueNoSet(1);
			int t = this.anvil.AnvilTier;

			if (this.redIndicator != cv)
			{
				var2.sendProgressBarUpdate(this, 0, cv);
			}
			if (this.greenIndicator != icv)
			{
				var2.sendProgressBarUpdate(this, 1, icv);
			}
			if (this.tier != t)
			{
				var2.sendProgressBarUpdate(this, 2, t);
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
	@Override
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
	@Override
	public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
	{
		super.onCraftGuiClosed(par1EntityPlayer);
		anvil.closeChest();
	}
}
