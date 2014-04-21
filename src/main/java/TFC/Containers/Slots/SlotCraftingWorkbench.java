package TFC.Containers.Slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;

public class SlotCraftingWorkbench extends Slot
{
	/** The craft matrix inventory linked to this result slot. */
	private final IInventory craftMatrix;

	/** The player that is using the GUI where this slot resides. */
	private EntityPlayer thePlayer;
	private int field_48436_g;

	private final IInventory paperSlot;

	public SlotCraftingWorkbench(EntityPlayer par1EntityPlayer, IInventory par2IInventory, IInventory par3IInventory,IInventory iinventory2, int par4, int par5, int par6)
	{
		super(par3IInventory, par4, par5, par6);
		this.thePlayer = par1EntityPlayer;
		this.craftMatrix = par2IInventory;
		paperSlot = iinventory2;
	}

	/**
	 * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
	 * stack.
	 */
	public ItemStack decrStackSize(int par1)
	{
		if (this.getHasStack())
		{
			this.field_48436_g += Math.min(par1, this.getStack().stackSize);
		}

		return super.decrStackSize(par1);
	}

	protected void func_48434_c(ItemStack par1ItemStack)
	{
		par1ItemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.field_48436_g);
		this.field_48436_g = 0;

		if (par1ItemStack.getItem() == Item.getItemFromBlock(Blocks.crafting_table))
		{
			this.thePlayer.addStat(AchievementList.buildWorkBench, 1);
		}
		else if (par1ItemStack.getItem() == Items.wooden_pickaxe)
		{
			this.thePlayer.addStat(AchievementList.buildPickaxe, 1);
		}
		else if (par1ItemStack.getItem() == Item.getItemFromBlock(Blocks.furnace))
		{
			this.thePlayer.addStat(AchievementList.buildFurnace, 1);
		}
		else if (par1ItemStack.getItem() == Items.wooden_hoe)
		{
			this.thePlayer.addStat(AchievementList.buildHoe, 1);
		}
		else if (par1ItemStack.getItem() == Items.bread)
		{
			this.thePlayer.addStat(AchievementList.makeBread, 1);
		}
		else if (par1ItemStack.getItem() == Items.cake)
		{
			this.thePlayer.addStat(AchievementList.bakeCake, 1);
		}
		else if (par1ItemStack.getItem() == Items.stone_pickaxe)
		{
			this.thePlayer.addStat(AchievementList.buildBetterPickaxe, 1);
		}
		else if (par1ItemStack.getItem() == Items.wooden_sword)
		{
			this.thePlayer.addStat(AchievementList.buildSword, 1);
		}
		else if (par1ItemStack.getItem() == Item.getItemFromBlock(Blocks.enchanting_table))
		{
			this.thePlayer.addStat(AchievementList.enchantments, 1);
		}
		else if (par1ItemStack.getItem() == Item.getItemFromBlock(TFCBlocks.Bookshelf))
		{
			this.thePlayer.addStat(AchievementList.bookcase, 1);
		}

		TerraFirmaCraft.proxy.takenFromCrafting(thePlayer, par1ItemStack, craftMatrix);
		//ForgeHooks.onTakenFromCrafting(thePlayer, par1ItemStack, craftMatrix);
	}

	protected void func_48435_a(ItemStack par1ItemStack, int par2)
	{
		this.field_48436_g += par2;
		this.func_48434_c(par1ItemStack);
	}

	/**
	 * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
	 */
	public boolean isItemValid(ItemStack par1ItemStack)
	{
		return false;
	}

	/**
	 * Called when the player picks up an item from an inventory slot
	 */
	public void onPickupFromSlot(ItemStack par1ItemStack)
	{
		this.func_48434_c(par1ItemStack);

		for (int var2 = 0; var2 < this.craftMatrix.getSizeInventory(); ++var2)
		{
			ItemStack var3 = this.craftMatrix.getStackInSlot(var2);

			if (var3 != null)
			{
				this.craftMatrix.decrStackSize(var2, 1);

				if (var3.getItem().hasContainerItem())
				{
					ItemStack var4 = new ItemStack(var3.getItem().getContainerItem());

					if (!var3.getItem().doesContainerItemLeaveCraftingGrid(var3) || !this.thePlayer.inventory.addItemStackToInventory(var4))
					{
						if (this.craftMatrix.getStackInSlot(var2) == null)
						{
							this.craftMatrix.setInventorySlotContents(var2, var4);
						}
						else
						{
							this.thePlayer.dropItem(var4.getItem(), var4.stackSize);
						}
					}
				}
			}
		}
	}
}
