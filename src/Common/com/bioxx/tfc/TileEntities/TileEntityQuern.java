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

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Entities.Mobs.EntityCowTFC;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.IFood;
import com.bioxx.tfc.api.Crafting.QuernManager;
import com.bioxx.tfc.api.Crafting.QuernRecipe;

public class TileEntityQuern extends NetworkTileEntity implements IInventory
{
	public ItemStack[] storage = new ItemStack[3];
	public int rotation = 0;
	public boolean shouldRotate = false;
	public int rotatetimer = 0;
	public boolean hasQuern = false;

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

	public boolean processItem()
	{
		if(storage[0] != null)
		{
			QuernRecipe qr = QuernManager.getInstance().findMatchingRecipe(storage[0].getItem(), storage[0].getItemDamage());
			if(qr == null)
			{
				System.out.println("QUERN RECIPE NOT FOUND! This is a BUG! -- " + storage[0].getItem().getUnlocalizedName());
				return false; // If this happens, it's a bug!
			}

			// If the output item can not be combined or is different from what is being made, eject and make room for the new output
			if(storage[1] != null && !(storage[1].getItem() == qr.getOutItem() && storage[1].getItemDamage() == qr.getOutItemDmg()))
			{
				ejectItem(new ItemStack(storage[1].getItem(), storage[1].stackSize, storage[1].getItemDamage()));
				storage[1] = null;
			}

			if(storage[1] == null || (storage[1].getItem() == qr.getOutItem() && storage[1].getItemDamage() == qr.getOutItemDmg()))
			{
				if (qr.getInItem() instanceof IFood)
				{
					if(storage[0].hasTagCompound() && storage[0].getTagCompound().hasKey("foodWeight") &&
							storage[0].getTagCompound().hasKey("foodDecay") && storage[1] == null)
					{
						storage [1] = new ItemStack(qr.getOutItem(), qr.getOutStackSize(), qr.getOutItemDmg());
						NBTTagCompound grainNBT = storage[0].getTagCompound();
						float flourWeight = grainNBT.getFloat("foodWeight");
						float flourDecay = grainNBT.getFloat("foodDecay");
						ItemFoodTFC.createTag(storage[1], flourWeight, flourDecay);
						storage[0] = null;
						return true;
					}
				}
				else
				{
					if(storage[0].stackSize == 1)
						storage[0] = null;
					else
						storage[0].stackSize--;

					if(storage[1] == null)
						storage[1] = new ItemStack(qr.getOutItem(), qr.getOutStackSize(), qr.getOutItemDmg());
					else if(storage[1].stackSize < storage[1].getMaxStackSize())
					{
						if((qr.getOutStackSize() + storage[1].stackSize) > storage[1].getMaxStackSize())
						{
							int amountleft = qr.getOutStackSize() - (storage[1].getMaxStackSize() - storage[1].stackSize);
							ejectItem(new ItemStack(qr.getOutItem(), storage[1].getMaxStackSize(), qr.getOutItemDmg()));
							storage[1] = new ItemStack(qr.getOutItem(), amountleft, qr.getOutItemDmg());
						}
						else
							storage[1].stackSize += qr.getOutStackSize();
					}
					else
					{
						ejectItem(new ItemStack(qr.getOutItem(), storage[1].stackSize, qr.getOutItemDmg()));
						storage[1] = new ItemStack(qr.getOutItem(), qr.getOutStackSize(), qr.getOutItemDmg());
					}

					return true;
				}
			}
		}
		return false;
	}

	public void damageStackInSlot(int i)
	{
		if(storage[i] != null)
		{
			storage[i].damageItem(i, new EntityCowTFC(this.worldObj));
			if(storage[i].stackSize == 0 || storage[i].getItemDamage() == storage[i].getMaxDamage())
			{
				setInventorySlotContents(i, null);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(storage[i] != null)
		{
			if(storage[i].stackSize <= j)
			{
				ItemStack itemstack = storage[i];
				setInventorySlotContents(i, null);
				return itemstack;
			}
			ItemStack itemstack1 = storage[i].splitStack(j);
			if(storage[i].stackSize == 0)
				setInventorySlotContents(i, null);
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
			if(storage[i]!= null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, storage[i]);
				entityitem.motionX = (float)rand.nextGaussian() * f3;
				entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float)rand.nextGaussian() * f3;
				worldObj.spawnEntityInWorld(entityitem);
			}
		}
	}

	public void ejectItem(ItemStack item)
	{
		float f3 = 0.05F;
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 2.0F + 0.4F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;

		if(storage[1]!= null)
		{
			entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, item);
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
	public ItemStack getStackInSlot(int i)
	{
		return storage[i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		storage[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
			itemstack.stackSize = getInventoryStackLimit();
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
	public boolean isUseableByPlayer(EntityPlayer var1)
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
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		return null;
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
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
	public void handleInitPacket(NBTTagCompound nbt) {
		this.hasQuern = nbt.getBoolean("hasQuern");
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createDataNBT(NBTTagCompound nbt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) {
		nbt.setBoolean("hasQuern", hasQuern);
	}
}
