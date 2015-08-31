package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.Slots.SlotForShowOnly;
import com.bioxx.tfc.Containers.Slots.SlotLiquidVessel;
import com.bioxx.tfc.Core.TFC_Achievements;
import com.bioxx.tfc.Core.Metal.MetalRegistry;
import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.Items.Pottery.ItemPotteryMold;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFC_ItemHeat;

public class ContainerLiquidVessel extends ContainerTFC 
{
	private World world;
	//private int posX;
	//private int posY;
	//private int posZ;
	private EntityPlayer player;
	public InventoryCrafting containerInv = new InventoryCrafting(this, 1, 1);

	public int bagsSlotNum;
	public int metalAmount;

	public ContainerLiquidVessel(InventoryPlayer playerinv, World world, int x, int y, int z)
	{
		this.player = playerinv.player;
		this.world = world;
		//this.posX = x;
		//this.posY = y;
		//this.posZ = z;
		bagsSlotNum = player.inventory.currentItem;
		layoutContainer(playerinv);
	}

	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		if (!this.world.isRemote)
		{
			ItemStack itemstack2 = this.containerInv.getStackInSlotOnClosing(0);

			if (itemstack2 != null)
				player.entityDropItem(itemstack2, 0);
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		return true;
	}

	private void layoutContainer(IInventory playerInventory)
	{
		this.addSlotToContainer(new SlotLiquidVessel(containerInv, 0, 80, 34));

		int row;
		int col;

		for (row = 0; row < 9; ++row)
		{
			if(row == bagsSlotNum)
				this.addSlotToContainer(new SlotForShowOnly(playerInventory, row, 8 + row * 18, 148));
			else
				this.addSlotToContainer(new Slot(playerInventory, row, 8 + row * 18, 148));
		}

		for (row = 0; row < 3; ++row)
		{
			for (col = 0; col < 9; ++col) 
				this.addSlotToContainer(new Slot(playerInventory, col + row * 9+9, 8 + col * 18, 90 + row * 18));
		}
	}

	@Override
	public void detectAndSendChanges()
	{
		//Load the metal info from the liquid container
		ItemStack stack = player.inventory.getStackInSlot(bagsSlotNum);

		NBTTagCompound nbt = stack != null && stack.hasTagCompound() ? stack.getTagCompound() : null;

		if (nbt != null)
		{
			ItemStack input = containerInv.getStackInSlot(0);

			// Trigger the copper age achievement simply when there is a full tool mold in the input slot.
			if (input != null && input.getItem() instanceof ItemPotteryMold && input.getItemDamage() > 1 /*Ceramic*/ && input.getItemDamage() <= 5 /*Full of Metal*/)
				player.triggerAchievement(TFC_Achievements.achCopperAge);

			Metal m = MetalRegistry.instance.getMetalFromString(nbt.getString("MetalType"));
			metalAmount = nbt.getInteger("MetalAmount");

			if (!world.isRemote && m != null && stack != null)
			{
				if (input != null && input.getItem() == TFCItems.ceramicMold && input.getItemDamage() == 1 && input.stackSize == 1 && metalAmount > 0)
				{
					int amt = 99;
					ItemStack is = new ItemStack(m.meltedItem, 1, amt);
					TFC_ItemHeat.setTemp(is, (short) (HeatRegistry.getInstance().getMeltingPoint(is) * 1.5f));
					containerInv.setInventorySlotContents(0, is);
					if (metalAmount - 1 <= 0)
					{
						nbt.removeTag("MetalType");
						nbt.removeTag("MetalAmount");
						nbt.removeTag("TempTimer");
						stack.setItemDamage(1);
					}
					else
					{
						nbt.setInteger("MetalAmount", metalAmount - 2);
					}

					stack.setTagCompound(nbt);
				}
				else if (input != null && input.getItem() == m.meltedItem && input.getItemDamage() > 0)
				{
					input.setItemDamage(input.getItemDamage() - 1);
					TFC_ItemHeat.setTemp(input, (short) (HeatRegistry.getInstance().getMeltingPoint(input) * 1.5f));
					if (metalAmount - 1 <= 0)
					{
						nbt.removeTag("MetalType");
						nbt.removeTag("MetalAmount");
						nbt.removeTag("TempTimer");
						stack.setItemDamage(1);
					}
					else
					{
						nbt.setInteger("MetalAmount", metalAmount - 1);
					}
				}
				else if (input != null &&input.getItem() instanceof ItemPotteryMold && input.getItemDamage() == 1 && input.stackSize == 1 && metalAmount > 0 &&
							("Copper".equals(m.name) || "Bronze".equals(m.name) || "Bismuth Bronze".equals(m.name) || "Black Bronze".equals(m.name)))
				{
					int amt = -1;
					if ("Copper".equals(m.name))
						amt = 398;
					else if ("Bronze".equals(m.name))
						amt = 399;
					else if ("Bismuth Bronze".equals(m.name))
						amt = 400;
					else if ("Black Bronze".equals(m.name))
						amt = 401;

					ItemStack is = new ItemStack(input.getItem(), 1, amt);
					TFC_ItemHeat.setTemp(is, (short) (HeatRegistry.getInstance().getMeltingPoint(is) * 1.5f));
					containerInv.setInventorySlotContents(0, is);
					if (metalAmount - 1 <= 0)
					{
						nbt.removeTag("MetalType");
						nbt.removeTag("MetalAmount");
						nbt.removeTag("TempTimer");
						stack.setItemDamage(1);
					}
					else
					{
						nbt.setInteger("MetalAmount", metalAmount - 2);
					}

					stack.setTagCompound(nbt);
				}
				else if (input != null && input.getItem() instanceof ItemPotteryMold && input.getItemDamage() > 1)
				{
					boolean correctMetalFlag = false;
					if ("Copper".equals(m.name) && (input.getItemDamage() - 2) % 4 == 0)
						correctMetalFlag = true;
					else if ("Bronze".equals(m.name) && (input.getItemDamage() - 2) % 4 == 1)
						correctMetalFlag = true;
					else if ("Bismuth Bronze".equals(m.name) && (input.getItemDamage() - 2) % 4 == 2)
						correctMetalFlag = true;
					else if ("Black Bronze".equals(m.name) && (input.getItemDamage() - 2) % 4 == 3)
						correctMetalFlag = true;

					if (correctMetalFlag)
					{
						if (input.getItemDamage() > 5)
						{
							input.setItemDamage(input.getItemDamage() - 4);
							TFC_ItemHeat.setTemp(input, (short) (HeatRegistry.getInstance().getMeltingPoint(input) * 1.5f));
							if (metalAmount - 1 <= 0)
							{
								nbt.removeTag("MetalType");
								nbt.removeTag("MetalAmount");
								nbt.removeTag("TempTimer");
								stack.setItemDamage(1);
							}
							else
							{
								nbt.setInteger("MetalAmount", metalAmount - 1);
							}
						}
					}
				}
			}
		}
		super.detectAndSendChanges();
	}

	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer player, int slotNum)
	{
		ItemStack origStack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotNum);
		Slot outputSlot = (Slot)inventorySlots.get(0);

		if (slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();

			// From vessel to inventory
			if (slotNum < 1)
			{
				if (!this.mergeItemStack(slotStack, 1, inventorySlots.size(), true))
					return null;
			}
			else if (!outputSlot.getHasStack() &&
					(slotStack.getItem() == TFCItems.ceramicMold && slotStack.getItemDamage() == 1 ||
					slotStack.getItem() instanceof ItemMeltedMetal && slotStack.getItemDamage() > 1 ||
					slotStack.getItem() instanceof ItemPotteryMold && slotStack.getItemDamage() > 0))
			{
				ItemStack stack = slotStack.copy();
				stack.stackSize = 1;
				outputSlot.putStack(stack);
				slotStack.stackSize--;
			}

			if (slotStack.stackSize <= 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();

			if (slotStack.stackSize == origStack.stackSize)
				return null;

			slot.onPickupFromSlot(player, slotStack);
		}

		return origStack;
	}
}
