package com.bioxx.tfc.TileEntities;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import net.minecraftforge.fluids.FluidStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Blocks.Devices.BlockChestTFC;
import com.bioxx.tfc.Blocks.Terrain.*;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.TFCItems;

public class TEHopper extends NetworkTileEntity implements IHopper
{
	private ItemStack[] storage = new ItemStack[5];
	private String customName;
	private int cooldown = -1;
	public int pressCooldown;
	public ItemStack pressBlock;

	public TEHopper()
	{

	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		this.storage = new ItemStack[this.getSizeInventory()];

		if (nbt.hasKey("CustomName", 8))
		{
			this.customName = nbt.getString("CustomName");
		}

		this.cooldown = nbt.getInteger("TransferCooldown");

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < this.storage.length)
			{
				this.storage[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		this.pressCooldown = nbt.getInteger("pressCooldown");
		this.pressBlock = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("pressBlock"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.storage.length; ++i)
		{
			if (this.storage[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				this.storage[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbt.setTag("Items", nbttaglist);
		nbt.setInteger("TransferCooldown", this.cooldown);

		if (this.hasCustomInventoryName())
		{
			nbt.setString("CustomName", this.customName);
		}

		nbt.setInteger("pressCooldown", this.pressCooldown);

		if(pressBlock != null)
		{
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			pressBlock.writeToNBT(nbttagcompound1);
			nbt.setTag("pressBlock", nbttagcompound1);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 2, zCoord + 1);
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory()
	{
		return this.storage.length;
	}

	/**
	 * Returns the stack in slot i
	 */
	@Override
	public ItemStack getStackInSlot(int i)
	{
		return this.storage[i];
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
	 * new stack.
	 */
	@Override
	public ItemStack decrStackSize(int i, int amount)
	{
		if (this.storage[i] != null)
		{
			ItemStack itemstack;

			if (this.storage[i].stackSize <= amount)
			{
				itemstack = this.storage[i];
				this.storage[i] = null;
				return itemstack;
			}
			else
			{
				itemstack = this.storage[i].splitStack(amount);

				if (this.storage[i].stackSize == 0)
				{
					this.storage[i] = null;
				}

				return itemstack;
			}
		}
		else
		{
			return null;
		}
	}

	/**
	 * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
	 * like when you close a workbench GUI.
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		if (this.storage[i] != null)
		{
			ItemStack itemstack = this.storage[i];
			this.storage[i] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int i, ItemStack is)
	{
		this.storage[i] = is;

		if (is != null && is.stackSize > this.getInventoryStackLimit())
		{
			is.stackSize = this.getInventoryStackLimit();
		}
	}

	/**
	 * Returns the name of the inventory
	 */
	@Override
	public String getInventoryName()
	{
		return this.hasCustomInventoryName() ? this.customName : "container.hopper";
	}

	/**
	 * Returns if the inventory is named
	 */
	@Override
	public boolean hasCustomInventoryName()
	{
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomName(String s)
	{
		this.customName = s;
	}

	/**
	 * Returns the maximum stack size for a inventory slot.
	 */
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes with Container
	 */
	@Override
	public boolean isUseableByPlayer(EntityPlayer p)
	{
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : p.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
	 */
	@Override
	public boolean isItemValidForSlot(int i, ItemStack is)
	{
		return true;
	}

	@Override
	public void updateEntity()
	{
		if (this.worldObj.isRemote)
		{
			if(pressCooldown > 0)
				--this.pressCooldown;
			else
				this.pressBlock = null;
		}
		else if (this.worldObj != null && !this.worldObj.isRemote)
		{
			--this.cooldown;

			TFC_Core.handleItemTicking(this, this.worldObj, xCoord, yCoord, zCoord);

			if(pressCooldown > 0)
			{
				--this.pressCooldown;
				if(pressCooldown % 20 == 0)
					press();
			}
			else if(pressCooldown == 0 && pressBlock != null)
			{
				for(int i = 0; i < storage.length; i++)
				{
					if (storage[i] == null || ItemStack.areItemStacksEqual(storage[i], pressBlock) && storage[i].stackSize < storage[i].getMaxStackSize())
					{
						if(storage[i] == null)
							storage[i] = pressBlock;
						else
							storage[i].stackSize++;
						this.pressBlock = null;
						break;
					}
				}
			}

			if (!this.isCoolingDown())
			{
				this.setCooldown(0);
				//this.feed();
			}
			Block blockAbove = worldObj.getBlock(xCoord, yCoord+1, zCoord);
			if(blockAbove != null && this.hasPressableItem() > 0)
			{
				if (pressBlock != null && !(blockAbove instanceof BlockCobble || blockAbove instanceof BlockGravel || blockAbove instanceof BlockSand || blockAbove instanceof BlockDirt))
				{
					TFC_Core.setBlockToAirWithDrops(worldObj, xCoord, yCoord+1, zCoord);
				}
				else if (blockAbove instanceof BlockSmooth)
				{
					pressBlock = new ItemStack(blockAbove, 1, worldObj.getBlockMetadata(xCoord, yCoord+1, zCoord));
					worldObj.setBlockToAir(xCoord, yCoord+1, zCoord);
					sendPressPacket();
					beginPressing();
				}
			}
		}
	}

	private void press()
	{
		TEBarrel barrel = null;
		if(worldObj.getTileEntity(xCoord, yCoord-1, zCoord) instanceof TEBarrel)
			barrel = (TEBarrel) worldObj.getTileEntity(xCoord, yCoord-1, zCoord);

		ItemStack item = getPressableItem();
		if(item != null)
		{
			if(item.stackSize > 0)
				Food.setWeight(item, Food.getWeight(item) - 0.64f);//0.64 per cycle leads to 250mB per stack of olives

			if(barrel != null && barrel.canAcceptLiquids() && !barrel.getSealed())
			{
				barrel.addLiquid(new FluidStack(TFCFluids.OLIVEOIL, 1));
			}
		}
	}

	private void beginPressing()
	{
		int pressWeight = hasPressableItem();
		if(pressWeight > 0)
		{
			this.pressCooldown += pressWeight/0.64f * 20;
			sendCooldownPacket();	
		}
	}

	public int hasPressableItem()
	{
		int amount = 0;
		for(int i = 0; i < storage.length; i++)
		{
			if(storage[i] != null && storage[i].getItem() == TFCItems.olive)
			{
				amount += Math.floor(Food.getWeight(storage[i]));
			}
		}
		return amount;
	}

	public ItemStack getPressableItem()
	{
		for(int i = 0; i < storage.length; i++)
		{
			if(storage[i] != null && storage[i].getItem() == TFCItems.olive)
			{
				return storage[i];
			}
		}
		return null;
	}

	/*public boolean feed()
	{
		if (this.worldObj != null && !this.worldObj.isRemote)
		{
			if (!this.isCoolingDown() && BlockHopper.func_149917_c(this.getBlockMetadata()))
			{
				boolean invChanged = false;
	
				if (!this.isHopperEmpty())
				{
					invChanged = this.tryToFeedContents();
				}
	
				if (!this.isHopperFull())
				{
					invChanged = FeedHopper(this) || invChanged;
				}
	
				if (invChanged)
				{
					this.setCooldown(8);
					this.markDirty();
					return true;
				}
			}
	
			return false;
		}
		else
		{
			return false;
		}
	}*/

	public void setCooldown(int time)
	{
		this.cooldown = time;
	}

	public boolean isCoolingDown()
	{
		return this.cooldown > 0;
	}

	/*private boolean isHopperEmpty()
	{
		ItemStack[] aitemstack = this.storage;
		int i = aitemstack.length;
	
		for (int j = 0; j < i; ++j)
		{
			ItemStack itemstack = aitemstack[j];
	
			if (itemstack != null)
			{
				return false;
			}
		}
	
		return true;
	}*/

	/*private boolean isHopperFull()
	{
		ItemStack[] aitemstack = this.storage;
		int i = aitemstack.length;
	
		for (int j = 0; j < i; ++j)
		{
			ItemStack itemstack = aitemstack[j];
	
			if (itemstack == null || itemstack.stackSize != itemstack.getMaxStackSize())
			{
				return false;
			}
		}
	
		return true;
	}*/

	/*private boolean tryToFeedContents()
	{
		IInventory iinventory = this.getOutputInventory();
	
		if (iinventory == null)
		{
			return false;
		}
		else
		{
			int i = Facing.oppositeSide[BlockHopper.getDirectionFromMetadata(this.getBlockMetadata())];
	
			if (this.func_152102_a(iinventory, i))
			{
				return false;
			}
			else
			{
				for (int j = 0; j < this.getSizeInventory(); ++j)
				{
					if (this.getStackInSlot(j) != null)
					{
						ItemStack itemstack = this.getStackInSlot(j).copy();
						ItemStack itemstack1 = func_145889_a(iinventory, this.decrStackSize(j, 1), i);
	
						if (itemstack1 == null || itemstack1.stackSize == 0)
						{
							iinventory.markDirty();
							return true;
						}
	
						this.setInventorySlotContents(j, itemstack);
					}
				}
	
				return false;
			}
		}
	}*/

	/*private boolean func_152102_a(IInventory inv, int side)
	{
		if (inv instanceof ISidedInventory && side > -1)
		{
			ISidedInventory isidedinventory = (ISidedInventory)inv;
			int[] aint = isidedinventory.getAccessibleSlotsFromSide(side);
	
			for (int l = 0; l < aint.length; ++l)
			{
				ItemStack itemstack1 = isidedinventory.getStackInSlot(aint[l]);
	
				if (itemstack1 == null || itemstack1.stackSize != itemstack1.getMaxStackSize())
				{
					return false;
				}
			}
		}
		else
		{
			int j = inv.getSizeInventory();
	
			for (int k = 0; k < j; ++k)
			{
				ItemStack itemstack = inv.getStackInSlot(k);
	
				if (itemstack == null || itemstack.stackSize != itemstack.getMaxStackSize())
				{
					return false;
				}
			}
		}
	
		return true;
	}*/

	/*private static boolean isSidedInvFull(IInventory inv, int side)
	{
		if (inv instanceof ISidedInventory && side > -1)
		{
			ISidedInventory isidedinventory = (ISidedInventory)inv;
			int[] aint = isidedinventory.getAccessibleSlotsFromSide(side);
	
			for (int l = 0; l < aint.length; ++l)
			{
				if (isidedinventory.getStackInSlot(aint[l]) != null)
				{
					return false;
				}
			}
		}
		else
		{
			int j = inv.getSizeInventory();
	
			for (int k = 0; k < j; ++k)
			{
				if (inv.getStackInSlot(k) != null)
				{
					return false;
				}
			}
		}
	
		return true;
	}*/

	/*public static boolean FeedHopper(IHopper hopper)
	{
		IInventory inputInv = getInputInventory(hopper);
	
		if (inputInv != null)
		{
			byte b0 = 0;
	
			if (isSidedInvFull(inputInv, b0))
			{
				return false;
			}
	
			if (inputInv instanceof ISidedInventory && b0 > -1)
			{
				ISidedInventory isidedinventory = (ISidedInventory)inputInv;
				int[] aint = isidedinventory.getAccessibleSlotsFromSide(b0);
	
				for (int k = 0; k < aint.length; ++k)
				{
					if (feedHopper(hopper, inputInv, aint[k], b0))
					{
						return true;
					}
				}
			}
			else
			{
				int i = inputInv.getSizeInventory();
	
				for (int j = 0; j < i; ++j)
				{
					if (feedHopper(hopper, inputInv, j, b0))
					{
						return true;
					}
				}
			}
		}
		else
		{
			EntityItem entityitem = searchForLooseInput(hopper.getWorldObj(), hopper.getXPos(), hopper.getYPos() + 1.0D, hopper.getZPos());
	
			if (entityitem != null)
			{
				return FeedLooseItem(hopper, entityitem);
			}
		}
	
		return false;
	}*/

	/*private static boolean feedHopper(IHopper hopper, IInventory input, int p_145892_2_, int p_145892_3_)
	{
		ItemStack itemstack = input.getStackInSlot(p_145892_2_);
	
		if (itemstack != null && canExtractItemStack(input, itemstack, p_145892_2_, p_145892_3_))
		{
			ItemStack itemstack1 = itemstack.copy();
			ItemStack itemstack2 = func_145889_a(hopper, input.decrStackSize(p_145892_2_, 1), -1);
	
			if (itemstack2 == null || itemstack2.stackSize == 0)
			{
				input.markDirty();
				return true;
			}
	
			input.setInventorySlotContents(p_145892_2_, itemstack1);
		}
	
		return false;
	}*/

	/*public static boolean feedLooseItem(IInventory hopper, EntityItem item)
	{
		boolean flag = false;
	
		if (item == null)
		{
			return false;
		}
		else
		{
			ItemStack itemstack = item.getEntityItem().copy();
			ItemStack itemstack1 = func_145889_a(hopper, itemstack, -1);
	
			if (itemstack1 != null && itemstack1.stackSize != 0)
			{
				item.setEntityItemStack(itemstack1);
			}
			else
			{
				flag = true;
				item.setDead();
			}
	
			return flag;
		}
	}*/

	/*public static ItemStack func_145889_a(IInventory output, ItemStack is, int side)
	{
		if (output instanceof ISidedInventory && side > -1)
		{
			ISidedInventory isidedinventory = (ISidedInventory)output;
			int[] aint = isidedinventory.getAccessibleSlotsFromSide(side);
	
			for (int l = 0; l < aint.length && is != null && is.stackSize > 0; ++l)
			{
				is = feedItem(output, is, aint[l], side);
			}
		}
		else
		{
			int j = output.getSizeInventory();
	
			for (int k = 0; k < j && is != null && is.stackSize > 0; ++k)
			{
				is = feedItem(output, is, k, side);
			}
		}
	
		if (is != null && is.stackSize == 0)
		{
			is = null;
		}
	
		return is;
	}*/

	/*private static boolean canFeedItemStack(IInventory inv, ItemStack is, int slot, int side)
	{
		return !inv.isItemValidForSlot(slot, is) ? false : !(inv instanceof ISidedInventory) || ((ISidedInventory)inv).canInsertItem(slot, is, side);
	}*/

	/*private static boolean canExtractItemStack(IInventory inv, ItemStack is, int slot, int side)
	{
		return !(inv instanceof ISidedInventory) || ((ISidedInventory)inv).canExtractItem(slot, is, side);
	}*/

	/*private static ItemStack feedItem(IInventory inv, ItemStack is, int slot, int side)
	{
		ItemStack itemstack1 = inv.getStackInSlot(slot);
	
		if (canFeedItemStack(inv, is, slot, side))
		{
			boolean changed = false;
	
			if (itemstack1 == null)
			{
				//Forge: BUGFIX: Again, make things respect max stack sizes.
				int max = Math.min(is.getMaxStackSize(), inv.getInventoryStackLimit());
				if (max >= is.stackSize)
				{
					inv.setInventorySlotContents(slot, is);
					is = null;
				}
				else
				{
					inv.setInventorySlotContents(slot, is.splitStack(max));
				}
				changed = true;
			}
			else if (canMergeStacks(itemstack1, is))
			{
				//Forge: BUGFIX: Again, make things respect max stack sizes.
				int max = Math.min(is.getMaxStackSize(), inv.getInventoryStackLimit());
				if (max > itemstack1.stackSize)
				{
					int size = Math.min(is.stackSize, max - itemstack1.stackSize);
					is.stackSize -= size;
					itemstack1.stackSize += size;
					changed = size > 0;
				}
			}
	
			if (changed)
			{
				if (inv instanceof TEHopper)
				{
					((TEHopper)inv).setCooldown(8);
					inv.markDirty();
				}
	
				inv.markDirty();
			}
		}
	
		return is;
	}*/

	/*private IInventory getOutputInventory()
	{
		int i = BlockHopper.getDirectionFromMetadata(this.getBlockMetadata());
		return searchForOutputInventory(this.getWorldObj(), this.xCoord + Facing.offsetsXForSide[i], this.yCoord + Facing.offsetsYForSide[i], this.zCoord + Facing.offsetsZForSide[i]);
	}*/

	public static IInventory getInputInventory(IHopper hopper)
	{
		return searchForOutputInventory(hopper.getWorldObj(), hopper.getXPos(), hopper.getYPos() + 1.0D, hopper.getZPos());
	}

	public static EntityItem searchForLooseInput(World world, double x, double y, double z)
	{
		List list = world.selectEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), IEntitySelector.selectAnything);
		return !list.isEmpty() ? (EntityItem) list.get(0) : null;
	}

	public static IInventory searchForOutputInventory(World world, double x, double y, double z)
	{
		IInventory iinventory = null;
		int i = MathHelper.floor_double(x);
		int j = MathHelper.floor_double(y);
		int k = MathHelper.floor_double(z);
		TileEntity tileentity = world.getTileEntity(i, j, k);

		//First we look for a block inventory
		if (tileentity instanceof IInventory)
		{
			iinventory = (IInventory)tileentity;

			if (iinventory instanceof TEChest)
			{
				Block block = world.getBlock(i, j, k);

				if (block instanceof BlockChestTFC)
				{
					iinventory = ((BlockChestTFC)block).getInventory(world, i, j, k);
				}
			}
		}
		//If no block inventory is found then we look for entities that have an inventory
		if (iinventory == null)
		{
			List list = world.getEntitiesWithinAABBExcludingEntity((Entity)null, AxisAlignedBB.getBoundingBox(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), IEntitySelector.selectInventories);

			if (list != null && !list.isEmpty())
			{
				iinventory = (IInventory)list.get(world.rand.nextInt(list.size()));
			}
		}

		return iinventory;
	}

	/*private static boolean canMergeStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack1.getItem() != stack2.getItem() ? false : stack1.getItemDamage() != stack2.getItemDamage() ? false
				: stack1.stackSize > stack1.getMaxStackSize() ? false : ItemStack.areItemStackTagsEqual(stack1, stack2);
	}*/

	@Override
	public void handleInitPacket(NBTTagCompound nbt) {
		if(nbt.hasKey("pressBlock"))
		{
			this.pressBlock = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("pressBlock"));
		}
		this.pressCooldown = nbt.getInteger("pressCooldown");
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) 
	{
		if(pressBlock != null)
		{
			NBTTagCompound pb = new NBTTagCompound();
			pressBlock.writeToNBT(pb);
			nbt.setTag("pressBlock", pb);
		}
		nbt.setInteger("pressCooldown", pressCooldown);
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt)
	{
		if(nbt.hasKey("pressBlock"))
		{
			this.pressBlock = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("pressBlock"));
		}
		if(nbt.hasKey("pressCooldown"))
		{
			this.pressCooldown = nbt.getInteger("pressCooldown");
		}
	}

	private void sendPressPacket() 
	{
		NBTTagCompound nbt = new NBTTagCompound();
		if(pressBlock != null)
		{
			NBTTagCompound pb = new NBTTagCompound();
			pressBlock.writeToNBT(pb);
			nbt.setTag("pressBlock", pb);
		}
		this.broadcastPacketInRange(this.createDataPacket(nbt));
	}

	private void sendCooldownPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("pressCooldown", pressCooldown);
		this.broadcastPacketInRange(this.createDataPacket(nbt));
	}

	@Override
	public double getXPos() {
		return this.xCoord;
	}

	@Override
	public double getYPos() {
		return this.yCoord;
	}

	@Override
	public double getZPos() {
		return this.zCoord;
	}

}
