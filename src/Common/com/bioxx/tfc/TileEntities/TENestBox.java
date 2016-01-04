package com.bioxx.tfc.TileEntities;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.Mobs.EntityChickenTFC;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Entities.IAnimal.GenderEnum;
import com.bioxx.tfc.api.Util.Helper;

public class TENestBox extends TileEntity implements IInventory
{
	public ItemStack[] inventory = new ItemStack[4];

	public TENestBox()
	{
	}

	public boolean hasBird()
	{
		return getBird() != null;
	}

	public EntityAnimal getBird()
	{
		List list = worldObj.getEntitiesWithinAABB(EntityChickenTFC.class, AxisAlignedBB.getBoundingBox(
				xCoord+0.1, yCoord, zCoord+0.1, 
				xCoord+0.9, yCoord+1.1, zCoord+0.9));
		//Code that returns the closest chicken instead of the first one found, which afaik would be the one found first when the AABB was checked,
		//which might be left to right? either way, takes longer and doesn't neccesarilly produce better results. Chickens do swap nests humourously though.
		/*
		if(list.size()!=0){
			EntityAnimal ea = (EntityAnimal)list.get(0);
			for(Object entity : list){
				ea = ((EntityAnimal)entity).getDistanceSq(xCoord+0.5,yCoord,zCoord+0.5) < ea.getDistanceSq(xCoord+0.5,yCoord,zCoord+0.5)?(EntityAnimal)entity:ea;
			}
			return ea;//(EntityAnimal)list.get(0);
		}*/

		if (!list.isEmpty())
		{
			for(Object e : list)
			{
				if(((EntityChickenTFC)e).getGender() == GenderEnum.FEMALE && ((EntityChickenTFC)e).isAdult()
						&& ((EntityChickenTFC)e).getAnimalTypeID() == Helper.stringToInt("chicken"))
				{
					return (EntityChickenTFC)e;
				}
			}
		}

		return null;
	}

	public EntityAnimal getRooster()
	{
		List list = worldObj.getEntitiesWithinAABB(EntityChickenTFC.class, AxisAlignedBB.getBoundingBox(
				xCoord-5, yCoord, zCoord-5, 
				xCoord+5, yCoord+2, zCoord+5));

		if (!list.isEmpty())
		{
			for(Object e : list)
			{
				if(((EntityChickenTFC)e).getGender() == GenderEnum.MALE && ((EntityChickenTFC)e).isAdult())
					return (EntityChickenTFC)e;
			}
		}
		return null;
	}

	@Override
	public void closeInventory()
	{
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(inventory[i] != null)
		{
			if(inventory[i].stackSize <= j)
			{
				ItemStack itemstack = inventory[i];
				inventory[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = inventory[i].splitStack(j);
			if(inventory[i].stackSize == 0)
				inventory[i] = null;
			return itemstack1;
		}
		else
		{
			return null;
		}
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
			if(inventory != null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, inventory[i]);
				entityitem.motionX = (float)rand.nextGaussian() * f3;
				entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float)rand.nextGaussian() * f3;
				worldObj.spawnEntityInWorld(entityitem);
			}
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public String getInventoryName()
	{
		return "NestBox";
	}

	@Override
	public int getSizeInventory()
	{
		return 4;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return inventory[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		return null;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public void openInventory()
	{
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		this.inventory[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
			itemstack.stackSize = getInventoryStackLimit();
	}

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			EntityAnimal bird = getBird();
			if(bird!=null)
			{
				ItemStack item = ((EntityChickenTFC)bird).getEggs();

				EntityChickenTFC father = (EntityChickenTFC) getRooster();
				for(int i = 0; item != null && i < this.getSizeInventory();i++)
				{
					if(inventory[i] == null)
					{
						ItemFoodTFC.createTag(item, 2.0f);
						if(father != null)
						{
							NBTTagCompound nbt = item.getTagCompound();
							nbt.setLong("Fertilized", TFC_Time.getTotalTicks() + (long) (TFCOptions.animalTimeMultiplier * TFC_Time.ticksInMonth * 0.75f));
							nbt.setTag("Genes", this.createGenes((EntityChickenTFC) bird, father));
							item.setTagCompound(nbt);
						}
						worldObj.playSoundAtEntity(bird,"mob.chicken.plop", 1.0F, (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F + 1.0F);
						setInventorySlotContents(i,item);
						break;
					}
				}
			}

			//Care for the eggs in the hatchery
			for(int i = 0;i < this.getSizeInventory();i++)
			{
				if(inventory[i] != null)
				{
					if(inventory[i].getTagCompound() != null && inventory[i].getTagCompound().hasKey("Fertilized"))
					{
						long time = inventory[i].getTagCompound().getLong("Fertilized");
						if(time <= TFC_Time.getTotalTicks())
						{
							EntityChickenTFC chick = new EntityChickenTFC(worldObj, xCoord + 0.5, yCoord + 1, zCoord + 0.5, 
									(NBTTagCompound) inventory[i].getTagCompound().getTag("Genes"));
							chick.setLocationAndAngles(xCoord + 0.5, yCoord + 1, zCoord + 0.5, 0.0F, 0.0F);
							chick.rotationYawHead = chick.rotationYaw;
							chick.renderYawOffset = chick.rotationYaw;
							worldObj.spawnEntityInWorld(chick);
							inventory[i] = null;
						}
					}
				}
			}
		}
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

	public NBTTagCompound createGenes(EntityChickenTFC mother, EntityChickenTFC father)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setFloat("m_size", mother.getSizeMod());
		nbt.setFloat("f_size", father.getSizeMod());
		return nbt;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < inventory.length; i++)
		{
			if(inventory[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Items", nbttaglist);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
		inventory = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < inventory.length)
				inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}
}
