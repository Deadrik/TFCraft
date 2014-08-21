package com.bioxx.tfc.TileEntities;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;
import com.bioxx.tfc.Handlers.Network.CreateMealPacket;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.Interfaces.IItemFoodBlock;
import com.bioxx.tfc.api.Util.Helper;

public class TEFoodPrep extends NetworkTileEntity implements IInventory
{
	public ItemStack[] storage = new ItemStack[11];
	public int lastTab = 0;
	private float[] sandwichWeights = new float[]{1,3,2,1,1,1};

	@Override
	public void updateEntity()
	{
		TFC_Core.handleItemTicking(this, worldObj, xCoord, yCoord, zCoord);
	}

	public int getFoodIdFromItemStack(ItemStack is)
	{
		if(is != null)
		{
			if(is.getItem() instanceof IFood)
				return ((IFood)is.getItem()).getFoodID();
			else if(is.getItem() instanceof IItemFoodBlock)
				return ((IItemFoodBlock)is.getItem()).getFoodId(is);
		}
		return 1;
	}

	public int getHealAmountFromItemStack(ItemStack is)
	{
		if(is != null)
		{
			if(is.getItem() instanceof IFood)
				return ((IFood)is.getItem()).getFoodID();
			else if(is.getItem() instanceof IItemFoodBlock)
				return ((IItemFoodBlock)is.getItem()).getHealAmount(is);
		}
		return 1;
	}

	public void actionCreate(EntityPlayer player)
	{
		if(!worldObj.isRemote)
		{
			if(lastTab == 0)
				createSandwich(player);
			else if(lastTab == 1)
				createSalad(player);
		}
		else
		{
			// Send a network packet to call this method on the Server side
			// and create a meal, also adds 1 to the players cooking skill.
			AbstractPacket pkt = new CreateMealPacket(0, this);
			broadcastPacketInRange(pkt);
		}
	}

	private void createSandwich(EntityPlayer player) 
	{
		if(storage[0] != null && storage[5] != null)//Bread
		{
			NBTTagCompound nbt = new NBTTagCompound();
			ItemStack is = new ItemStack(TFCItems.Sandwich, 1);
			Random R = new Random(getFoodSeed());
			int[] FG = new int[]{-1,-1,-1,-1,-1,-1};
			if(getStackInSlot(0) != null) FG[0] = ((IFood)(getStackInSlot(0).getItem())).getFoodID();
			if(getStackInSlot(1) != null) FG[1] = ((IFood)(getStackInSlot(1).getItem())).getFoodID();
			if(getStackInSlot(2) != null) FG[2] = ((IFood)(getStackInSlot(2).getItem())).getFoodID();
			if(getStackInSlot(3) != null) FG[3] = ((IFood)(getStackInSlot(3).getItem())).getFoodID();
			if(getStackInSlot(4) != null) FG[4] = ((IFood)(getStackInSlot(4).getItem())).getFoodID();
			if(getStackInSlot(5) != null) FG[5] = ((IFood)(getStackInSlot(5).getItem())).getFoodID();

			nbt.setIntArray("FG", FG);

			is.setItemDamage(new Random(getIconSeed()).nextInt(((ItemTerra)TFCItems.Sandwich).MetaIcons.length));

			nbt.setFloat("foodWeight", Helper.roundNumber(getSandwichWeight(sandwichWeights), 10));
			nbt.setFloat("foodDecay", -24);
			nbt.setFloat("decayRate", 2.0f);
			nbt.setInteger("decayTimer", (int)TFC_Time.getTotalHours() + 1);
			combineTastes(nbt, sandwichWeights);
			is.setTagCompound(nbt);

			this.setInventorySlotContents(6, is);

			consumeFoodWeight(sandwichWeights);
		}
	}

	private void createSalad(EntityPlayer player) 
	{

	}

	public boolean areComponentsCorrect()
	{
		if(lastTab == 0)
		{
			if(storage[0] == null || storage[5] == null)//Bread
				return false;
			int count = 0;
			if(storage[1] != null) count++;
			else return false;
			if(storage[2] != null) count++;
			if(storage[3] != null) count++;
			if(storage[4] != null) count++;

			if(count < 2)
				return false;

			if(!validateSameIngreds(storage[1],storage[2],storage[3],storage[4]))
				return false;

			if(getSandwichWeight(this.sandwichWeights) == -1)
				return false;
		}
		return true;
	}

	public boolean validateSameIngreds(ItemStack is1, ItemStack is2, ItemStack is3, ItemStack is4)
	{
		if((is1 != null && is2 != null && is1.getItem() == is2.getItem()) || (is1 != null && is3 != null && is1.getItem() == is3.getItem())
				|| (is1 != null && is4 != null && is1.getItem() == is4.getItem()))
			return false;
		if((is2 != null && is3 != null && is2.getItem() == is3.getItem()) || (is2 != null && is4 != null && is2.getItem() == is4.getItem()))
			return false;
		if((is3 != null && is4 != null && is3.getItem() == is4.getItem()))
			return false;
		return true;
	}

	public void actionCreateOLD(EntityPlayer player)
	{
		if(!worldObj.isRemote)
		{
			if(storage[4] == null && storage[5].getItem() == Items.bowl)
			{
				if(getMealWeight(new float[]{0,0,0,0}) >= 14)
				{
					NBTTagCompound nbt = new NBTTagCompound();
					ItemStack is = new ItemStack(TFCItems.MealGeneric, 1);
					Random R = new Random(getFoodSeed());
					Random Ri = new Random(getIconSeed());

					int count = -2;
					if(getStackInSlot(0) != null)
					{
						count++;
						nbt.setInteger("FG0", ((IFood)(getStackInSlot(0).getItem())).getFoodID());
					}
					if(getStackInSlot(1) != null)
					{
						count++;
						nbt.setInteger("FG1", ((IFood)(getStackInSlot(1).getItem())).getFoodID());
					}
					if(getStackInSlot(2) != null)
					{
						count++;
						nbt.setInteger("FG2", ((IFood)(getStackInSlot(2).getItem())).getFoodID());
					}
					if(getStackInSlot(3) != null)
					{
						count++;
						nbt.setInteger("FG3", ((IFood)(getStackInSlot(3).getItem())).getFoodID());
					}

					float mult = 0.15f + 0.12f * count;

					//set the icon for this meal
					is.setItemDamage(Ri.nextInt(11));
					if(R.nextFloat() < mult)
					{
						float s = R.nextFloat() * 0.25f + (TFC_Core.getSkillStats(player).getSkillMultiplier(Global.SKILL_COOKING) * 0.5f);
						nbt.setFloat("satisfaction", s);
					}
					nbt.setFloat("foodWeight", Helper.roundNumber(getMealWeight(new float[]{0,0,0,0}), 10));
					nbt.setFloat("foodDecay", -24);
					nbt.setFloat("decayRate", 2.0f);
					nbt.setInteger("decayTimer", (int)TFC_Time.getTotalHours() + 1);
					combineTastes(nbt, new float[]{0,0,0,0});
					is.setTagCompound(nbt);

					this.setInventorySlotContents(4, is);
					this.getStackInSlot(5).stackSize--;
					if(getStackInSlot(5).stackSize == 0)
						setInventorySlotContents(5, null);

					consumeFoodWeight(new float[]{0,0,0,0});
				}
			}
		}
		else
		{
			// Send a network packet to call this method on the Server side
			// and create a meal, also adds 1 to the players cooking skill.
			AbstractPacket pkt = new CreateMealPacket(0, this);
			broadcastPacketInRange(pkt);
		}
	}

	private void combineTastes(NBTTagCompound nbt, float[] weights)
	{
		int tasteSweet = 0;
		int tasteSour = 0;
		int tasteSalty = 0;
		int tasteBitter = 0;
		int tasteUmami = 0;
		for (int i = 0; i < 6; i++)
		{
			float weightMult = ((weights[i]*2)/100f)*5f;
			if(storage[i] != null)
			{
				tasteSweet += ((IFood)storage[i].getItem()).getTasteSweet(storage[i]) * weightMult;
				tasteSour += ((IFood)storage[i].getItem()).getTasteSour(storage[i]) * weightMult;
				tasteSalty += ((IFood)storage[i].getItem()).getTasteSalty(storage[i]) * weightMult;
				tasteBitter += ((IFood)storage[i].getItem()).getTasteBitter(storage[i]) * weightMult;
				tasteUmami += ((IFood)storage[i].getItem()).getTasteSavory(storage[i]) * weightMult;
			}
		}
		nbt.setInteger("tasteSweet", tasteSweet);
		nbt.setInteger("tasteSour", tasteSour);
		nbt.setInteger("tasteSalty", tasteSalty);
		nbt.setInteger("tasteBitter", tasteBitter);
		nbt.setInteger("tasteUmami", tasteUmami);
	}

	public void openGui(EntityPlayer player)
	{
		if(lastTab == 0)
			player.openGui(TerraFirmaCraft.instance, 44, worldObj, xCoord, yCoord, zCoord);
		else if (lastTab == 1)
			player.openGui(TerraFirmaCraft.instance, 45, worldObj, xCoord, yCoord, zCoord);
	}

	private long getFoodSeed()
	{
		int seed = 1;

		for(int i = 0; i < 4; i++)
		{
			ItemStack is = getStackInSlot(i);
			if(is != null)
				seed *= ((ItemFoodTFC) is.getItem()).getFoodID();
		}
		return seed + worldObj.getSeed();
	}

	private long getIconSeed()
	{
		int seed = 0;
		for(int i = 1; i < 5; i++)
		{
			ItemStack is = getStackInSlot(i);
			if(is != null)
				seed += ((ItemFoodTFC)is.getItem()).getFoodID();
		}
		return seed + worldObj.getSeed();
	}

	public float getMealWeight(float[] weights)
	{
		float w = 0;
		for(int i = 0; i < 6; i++)
		{
			ItemStack is = getStackInSlot(i);
			if(is != null && ((IFood)is.getItem()).getFoodWeight(is) >= weights[i])
				w += weights[i];
			else return -1;
		}
		return w;
	}

	public float getSandwichWeight(float[] weights)
	{
		float w = 0;
		for(int i = 0; i < 6; i++)
		{
			ItemStack is = getStackInSlot(i);
			if(is != null && ((IFood)is.getItem()).getFoodWeight(is) >= weights[i])
				w += weights[i];
			else return -1;
		}
		return w;
	}

	public float consumeFoodWeight(float[] weights)
	{
		float w = 0;
		for(int i = 0; i < 6; i++)
		{
			ItemStack is = getStackInSlot(i);
			if(is != null)
			{
				is.getTagCompound().setFloat("foodWeight", ((ItemFoodTFC)is.getItem()).getFoodWeight(is) - weights[i]);
				if(((ItemFoodTFC)is.getItem()).getFoodWeight(is) <= 0)
					is.stackSize = 0;
			}
		}
		return w;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		storage = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < storage.length)
				storage[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
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
		nbt.setTag("Items", nbttaglist);
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(storage[i] != null)
		{
			if(storage[i].stackSize <= j)
			{
				ItemStack itemstack = storage[i];
				storage[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = storage[i].splitStack(j);
			if(storage[i].stackSize == 0)
				storage[i] = null;
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
				storage[i] = null;
			}
		}
	}

	public void ejectItem(int index)
	{
		float f3 = 0.05F;
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 2.0F + 0.4F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;

		if(storage[index]!= null)
		{
			entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, storage[index]);
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
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public String getInventoryName()
	{
		return "FoodPrep";
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
		if(worldObj.isRemote)
		{
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		else if(storage[0] == null && storage[1] == null && storage[2] == null && storage[3] == null && storage[4] == null && storage[5] == null 
				&& storage[6] == null && storage[7] == null && storage[8] == null && storage[9] == null && storage[10] == null)
		{
			this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
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
	public void handleInitPacket(NBTTagCompound nbt) {
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		storage = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < storage.length)
				storage[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) {
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
		nbt.setTag("Items", nbttaglist);
	}

}
