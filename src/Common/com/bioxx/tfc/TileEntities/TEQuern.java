package com.bioxx.tfc.TileEntities;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.AxisAlignedBB;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Entities.Mobs.EntityCowTFC;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Crafting.QuernManager;
import com.bioxx.tfc.api.Crafting.QuernRecipe;
import com.bioxx.tfc.api.Interfaces.IFood;

public class TEQuern extends NetworkTileEntity implements IInventory
{
	public ItemStack[] storage = new ItemStack[3];
	public int rotation;
	public boolean shouldRotate;
	public int rotatetimer;
	public boolean hasQuern;

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
			TFC_Core.handleItemTicking(this, worldObj, xCoord, yCoord, zCoord);

		hasQuern = storage[2] != null;

		if(shouldRotate)
		{
			rotatetimer++;
			if(rotatetimer == 90) //This needs to be 73 if speed is * 1 in TESRQuern, use 90 for speed * 4
			{
				rotatetimer = 0;
				shouldRotate = false;
				if(!worldObj.isRemote)
				{
					if(processItem() && storage[2] != null)
						damageStackInSlot(2);
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}

	public boolean processItem()
	{
		if(storage[0] != null)
		{
			QuernRecipe qr = QuernManager.getInstance().findMatchingRecipe(storage[0]);
			if(qr == null)
			{
				TerraFirmaCraft.LOG.warn("QUERN RECIPE NOT FOUND! This is a BUG! -- " + storage[0].getItem().getUnlocalizedName());
				return false; // If this happens, it's a bug!
			}

			// If the output item can not be combined or is different from what is being made, eject and make room for the new output
			if(storage[1] != null && !(storage[1].getItem() == qr.getResult().getItem() && storage[1].getItemDamage() == qr.getResult().getItemDamage()))
			{
				ejectItem(storage[1]);
				storage[1] = null;
			}

			if (qr.getInItem().getItem() instanceof IFood)
			{
				if (storage[1] != null)
				{
					float slot0Weight = Food.getWeight(storage[0]);
					float slot1Weight = Food.getWeight(storage[1]);
					float newWeight = slot0Weight + slot1Weight;

					if(newWeight > Global.FOOD_MAX_WEIGHT)
					{
						Food.setWeight(storage[1], newWeight - Global.FOOD_MAX_WEIGHT);

						ItemStack tossStack = storage[1].copy();
						Food.setWeight(tossStack, Global.FOOD_MAX_WEIGHT);
						ejectItem(tossStack);
					}
					else
					{
						Food.setWeight(storage[1], newWeight);
					}
					storage[0] = null;
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					return true;
				}

				if (storage[1] == null)
				{
					storage[1] = qr.getResult().copy();
					float flourWeight = Food.getWeight(storage[0]);
					float flourDecay = Food.getDecay(storage[0]);
					ItemFoodTFC.createTag(storage[1], flourWeight, flourDecay);
					storage[0] = null;
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					return true;
				}
			}
			else
			{
				if(storage[0].stackSize == qr.getInItem().stackSize)
				{
					storage[0] = null;
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
				else
					storage[0].stackSize -= qr.getInItem().stackSize;

				if(storage[1] == null)
					storage[1] = qr.getResult().copy();
				else if(storage[1].stackSize < storage[1].getMaxStackSize())
				{
					if((qr.getResult().stackSize + storage[1].stackSize) > storage[1].getMaxStackSize())
					{
						int amountleft = qr.getResult().stackSize - (storage[1].getMaxStackSize() - storage[1].stackSize);
						ItemStack tossStack = qr.getResult().copy();
						tossStack.stackSize = tossStack.getMaxStackSize();
						ejectItem(tossStack);
						ItemStack remainStack = qr.getResult().copy();
						remainStack.stackSize = amountleft;
						storage[1] = remainStack;
					}
					else
						storage[1].stackSize += qr.getResult().stackSize;
				}
				else
				{
					ejectItem(storage[1]);
					storage[1] = qr.getResult().copy();
				}
				return true;
			}
		}
		return false;
	}

	public void damageStackInSlot(int slot)
	{
		if(storage[slot] != null)
		{
			// Use a non-player, non-null entity so the handstone is damaged even when the player is in creative mode
			storage[slot].damageItem(slot, new EntityCowTFC(this.worldObj));
			if(storage[slot].stackSize == 0 || storage[slot].getItemDamage() == storage[slot].getMaxDamage())
			{
				setInventorySlotContents(slot, null);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		if(storage[slot] != null)
		{
			if(storage[slot].stackSize <= amount)
			{
				ItemStack itemstack = storage[slot];
				setInventorySlotContents(slot, null);
				return itemstack;
			}
			ItemStack itemstack1 = storage[slot].splitStack(amount);
			if(storage[slot].stackSize == 0)
				setInventorySlotContents(slot, null);
			return itemstack1;
		}
		else
			return null;
	}

	public void ejectContents()
	{
		float f3 = 0.05F;
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 2.0F + 0.4F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;

		for (int i = 0; i < getSizeInventory(); i++)
		{
			if(storage[i] != null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, storage[i]);
				entityitem.motionX = (float)rand.nextGaussian() * f3;
				entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float)rand.nextGaussian() * f3;
				worldObj.spawnEntityInWorld(entityitem);
			}
		}
	}

	public void ejectItem(ItemStack is)
	{
		float f3 = 0.05F;
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 2.0F + 0.4F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;

		if(storage[1] != null)
		{
			entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, is);
			entityitem.motionX = (float)rand.nextGaussian() * f3;
			entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.05F;
			entityitem.motionZ = (float)rand.nextGaussian() * f3;
			worldObj.spawnEntityInWorld(entityitem);
		}
	}

	@Override
	public int getSizeInventory()
	{
		return storage.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return storage[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack is)
	{
		storage[slot] = is;
		if(is != null && is.stackSize > getInventoryStackLimit())
			is.stackSize = getInventoryStackLimit();
	}

	@Override
	public String getInventoryName()
	{
		return "Quern";
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return false;
	}

	@Override
	public void openInventory()
	{
	}

	@Override
	public void closeInventory()
	{
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return null;
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is)
	{
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
		storage = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < storage.length)
				storage[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
		hasQuern = nbttagcompound.getBoolean("hasQuern");
		shouldRotate = nbttagcompound.getBoolean("shouldRotate");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < storage.length; i++)
		{
			if(storage[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				storage[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Items", nbttaglist);
		nbttagcompound.setBoolean("hasQuern", hasQuern);
		nbttagcompound.setBoolean("shouldRotate", shouldRotate);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.func_148857_g());
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt)
	{
		this.hasQuern = nbt.getBoolean("hasQuern");
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void createDataNBT(NBTTagCompound nbt)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt)
	{
		nbt.setBoolean("hasQuern", hasQuern);
	}
}
