package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import TFC.Containers.Slots.SlotArmorTFC;
import TFC.Containers.Slots.SlotCraftingTFC;
import TFC.Core.Player.PlayerInventory;
import TFC.Items.ItemTFCArmor;
import cpw.mods.fml.common.registry.GameRegistry;

public class ContainerPlayerTFC extends ContainerPlayer 
{
	public ContainerPlayerTFC(InventoryPlayer playerInv,
			boolean par2, EntityPlayer player) 
	{
		super(playerInv, par2, player);
		craftMatrix = new InventoryCrafting(this, 3, 3);
		inventorySlots.clear();
		inventoryItemStacks.clear();

		this.addSlotToContainer(new SlotCraftingTFC(player, craftMatrix, craftResult, 0, 152, 36));
		int x;
		int y;

		for (x = 0; x < 2; ++x)
			for (y = 0; y < 2; ++y)
				this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18, 18 + x * 18));
		int indexForBack = -1;
		for (x = 0; x < playerInv.armorInventory.length; ++x)
		{
			int index = playerInv.getSizeInventory() - 1 - x;
			if(x == 4){
				//this.addSlotToContainer(new SlotArmorTFC(this, playerInv, index, 8, 8 + x * 18, x));
				indexForBack = index;
			}
			else{
				this.addSlotToContainer(new SlotArmorTFC(this, playerInv, index, 8, 8 + x * 18, x));
			}
		}
		PlayerInventory.buildInventoryLayout(this, playerInv, 8, 90, false, true);


		//Manually built the remaining crafting slots because of an order issue. These have to be created after the default slots
		if(player.getEntityData().hasKey("craftingTable") || !player.worldObj.isRemote)
		{
			x = 2; y = 0; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18, 18 + x * 18));
			x = 2; y = 1; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18, 18 + x * 18));
			x = 0; y = 2; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18, 18 + x * 18));
			x = 1; y = 2; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18, 18 + x * 18));
			x = 2; y = 2; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18, 18 + x * 18));
		}
		else
		{
			//Have to create some dummy slots
			x = 2; y = 0; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18-50000, 18 + x * 18));
			x = 2; y = 1; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18-50000, 18 + x * 18));
			x = 0; y = 2; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18-50000, 18 + x * 18));
			x = 1; y = 2; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18-50000, 18 + x * 18));
			x = 2; y = 2; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18-50000, 18 + x * 18));
		}

		this.onCraftMatrixChanged(this.craftMatrix);

		this.addSlotToContainer(new SlotArmorTFC(this, playerInv, indexForBack, 8 + 18, 8 + 18, 4));
	}

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);

		for (int i = 0; i < 9; ++i)
		{
			ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);

			if (itemstack != null)
				par1EntityPlayer.dropPlayerItem(itemstack);
		}

		this.craftResult.setInventorySlotContents(0, (ItemStack)null);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack(); 
			/*if(itemstack1.hasTagCompound())
				slot.onPickupFromSlot(thePlayer, itemstack1);*/

			itemstack = itemstack1.copy();

			if (par2 == 0)
			{
				GameRegistry.onItemCrafted(player, itemstack1, craftMatrix);
				((SlotCraftingTFC)slot).onCrafting(itemstack1);
				if (!this.mergeItemStack(itemstack1, 9, 45, true))
					return null;

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if ((par2 >= 1 && par2 < 5) || (player.getEntityData().hasKey("craftingTable") && (par2 >= 45 && par2 < 50)))
			{
				if (!this.mergeItemStack(itemstack1, 9, 45, false))
					return null;
			}
			else if (par2 >= 5 && par2 < 9 || par2 == 50)
			{
				if (!this.mergeItemStack(itemstack1, 9, 45, false))
					return null;
			}
			else if (itemstack.getItem() instanceof ItemArmor){
				if(itemstack.getItem() instanceof ItemTFCArmor && ((!((Slot)this.inventorySlots.get(5 + ((ItemTFCArmor)itemstack.getItem()).getUnadjustedArmorType())).getHasStack() && ((ItemTFCArmor)itemstack.getItem()).getUnadjustedArmorType() != 4)||
						(!((Slot)this.inventorySlots.get(50)).getHasStack()))){
					int j = ((ItemTFCArmor)itemstack.getItem()).getUnadjustedArmorType() != 4 ? 5 + ((ItemTFCArmor)itemstack.getItem()).getUnadjustedArmorType() : 50;

					if (!this.mergeItemStack(itemstack1, j, j + 1, false))
						return null;
				}
				else if(((!((Slot)this.inventorySlots.get(5 + ((ItemArmor)itemstack.getItem()).armorType)).getHasStack() && ((ItemArmor)itemstack.getItem()).armorType != 4)||
						(!((Slot)this.inventorySlots.get(50)).getHasStack())))
				{
					int j = ((ItemArmor)itemstack.getItem()).armorType != 4 ? 5 + ((ItemArmor)itemstack.getItem()).armorType : 50;

					if (!this.mergeItemStack(itemstack1, j, j + 1, false))
						return null;
				}
			}
			else if (par2 >= 9 && par2 < 36)
			{
				if (!this.mergeItemStack(itemstack1, 36, 45, false))
					return null;
			}
			else if (par2 >= 36 && par2 < 45)
			{
				if (!this.mergeItemStack(itemstack1, 9, 36, false))
					return null;
			}
			else if (!this.mergeItemStack(itemstack1, 9, 45, false))
				return null;

			if (itemstack1.stackSize == 0)
				slot.putStack((ItemStack)null);
			else
				slot.onSlotChanged();

			if (itemstack1.stackSize == itemstack.stackSize)
				return null;

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}

	public EntityPlayer getPlayer()
	{
		return this.thePlayer;
	}

}
