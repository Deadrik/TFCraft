package com.bioxx.tfc.TileEntities;

import java.util.*;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Metal.*;
import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.api.*;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Interfaces.ISmeltable;

public class TECrucible extends NetworkTileEntity implements IInventory
{
	public Map<String, MetalPair> metals = new HashMap<String, MetalPair>();
	public Alloy currentAlloy;
	public int temperature;
	public ItemStack[] storage;
	public byte inputTick;
	public byte outputTick;
	public byte tempTick;
	private int cookDelay;
	public static final int MAX_UNITS = 3000;

	public TECrucible()
	{
		storage = new ItemStack[2];
		this.broadcastRange = 5;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		nbt.setInteger("temp", temperature);

		NBTTagList nbttaglist = new NBTTagList();
		Iterator<MetalPair> iter = metals.values().iterator();
		while(iter.hasNext())
		{
			MetalPair m = iter.next();
			if(m != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setInteger("ID", Item.getIdFromItem(m.type.ingot));
				nbttagcompound1.setFloat("AmountF", m.amount);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Metals", nbttaglist);

		nbttaglist = new NBTTagList();
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
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		readFromItemNBT(nbt);
	}

	public void readFromItemNBT(NBTTagCompound nbt)
	{
		temperature = nbt.getInteger("temp");
		NBTTagList nbttaglist = nbt.getTagList("Metals", 10);

		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int id = nbttagcompound1.getInteger("ID");
			float amount = nbttagcompound1.getShort("Amount");
			//Added so that hopefully old worlds that stored metal as shorts wont break
			float amountF = amount + nbttagcompound1.getFloat("AmountF");
			addMetal(MetalRegistry.instance.getMetalFromItem(Item.getItemById(id)), amountF);
		}

		nbttaglist = nbt.getTagList("Items", 10);
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
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			inputTick++;
			outputTick++;
			tempTick++;

			if (cookDelay > 0)
				cookDelay--;

			/*Heat the crucible based on the Forge beneath it*/
			if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) == TFCBlocks.forge)
			{
				TEForge te = (TEForge) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
				if (te.fireTemp >= 1 && TFCOptions.enableDebugMode)
					temperature = 2000;
				else if (te.fireTemp > temperature)
					temperature++;
			}
			if(tempTick > 22)
			{
				tempTick = 0;
				if(temperature > TFC_Climate.getHeightAdjustedTemp(worldObj, xCoord, yCoord, zCoord))
					temperature--;
			}

			ItemStack stackToSmelt = storage[0];
			if(stackToSmelt != null)
			{
				Item itemToSmelt = stackToSmelt.getItem();
				int newDamage = stackToSmelt.getItemDamage() + 1;
				int maxDamage = stackToSmelt.getMaxDamage() - 1; // Subtract one so we never have an unshaped metal with 0/100 units

				if(itemToSmelt instanceof ItemMeltedMetal && TFC_ItemHeat.getIsLiquid(storage[0]))
				{
					if(inputTick > 10)
					{
						Metal inputMetal = MetalRegistry.instance.getMetalFromItem(itemToSmelt);

						if (currentAlloy != null && currentAlloy.outputType != null && itemToSmelt == currentAlloy.outputType.meltedItem)
						{
							this.addMetal(inputMetal, (short) 1);
							if (newDamage >= maxDamage)
							{
								storage[0] = new ItemStack(TFCItems.ceramicMold, 1, 1);
							}
							else
							{
								stackToSmelt.setItemDamage(newDamage);
							}
						}
						else
						{
							this.addMetal(inputMetal, (short) 1);
							if (newDamage >= maxDamage)
							{
								storage[0] = new ItemStack(TFCItems.ceramicMold, 1, 1);
							}
							else
							{
								stackToSmelt.setItemDamage(newDamage);
							}
						}
						inputTick = 0;
						updateGui((byte) 0);
					}
				}
				else if(itemToSmelt instanceof ISmeltable && (
						(ISmeltable)itemToSmelt).isSmeltable(stackToSmelt) &&
						!TFC_Core.isOreIron(stackToSmelt) &&
						temperature >= TFC_ItemHeat.isCookable(stackToSmelt) && cookDelay == 0)
				{
					Metal mType =((ISmeltable)itemToSmelt).getMetalType(stackToSmelt);
					if(addMetal(mType, ((ISmeltable)itemToSmelt).getMetalReturnAmount(stackToSmelt)))
					{
						temperature *= 0.9f;
						cookDelay = 40;
						if(stackToSmelt.stackSize <= 1)
							storage[0] = null;
						else
							storage[0].stackSize--;
						updateGui((byte) 0);
					}
				}
			}
			//Metal Output handling
			if(currentAlloy != null &&
					storage[1] != null &&
					currentAlloy.outputType != null &&
					outputTick >= 2 &&
					temperature >= TFC_ItemHeat.isCookable(currentAlloy.outputType))
			{
				if(storage[1].getItem() == TFCItems.ceramicMold)
				{
					storage[1] = new ItemStack(currentAlloy.outputType.meltedItem, 1, 99);
					TFC_ItemHeat.setTemp(storage[1], temperature);
					//currentAlloy.outputAmount--;
					drainOutput(1.0f);
					updateGui((byte) 1);
				}
				else if(storage[1].getItem() == currentAlloy.outputType.meltedItem && storage[1].getItemDamage() > 0)
				{
					storage[1].setItemDamage(storage[1].getItemDamage()-1);
					float inTemp = TFC_ItemHeat.getTemp(storage[1]);
					float temp = (temperature - inTemp) / 2;
					TFC_ItemHeat.setTemp(storage[1], inTemp+temp);
					//TerraFirmaCraft.log.info(temperature +", "+inTemp+", "+temp);
					drainOutput(1.0f);
					storage[1].stackSize = 1;
					updateGui((byte) 1);
				}
				outputTick = 0;
			}

			if(currentAlloy != null && this.getTotalMetal() < 1)
			{
				metals = new HashMap<String, MetalPair>();
				updateCurrentAlloy();
				this.updateGui((byte) 2);
				currentAlloy = null;
			}

			if(storage[1] != null && storage[1].stackSize <= 0)
				storage[1].stackSize = 1;
			if (inputTick > 10)
				inputTick = 0;
			if (outputTick >= 2)
				outputTick = 0;
		}
	}

	public boolean drainOutput(float amount)
	{
		if(metals != null && metals.values().size() > 0)
		{
			for(Object am : metals.values())
			{
				float percent = currentAlloy.getPercentForMetal(((MetalPair)am).type) / 100;
				((MetalPair)am).amount -= amount*percent;
			}
			updateCurrentAlloy();
		}
		return true;
	}

	public boolean addMetal(Metal m, float amt)
	{
		if (getTotalMetal() + amt <= MAX_UNITS && m.name != null && !"Unknown".equals(m.name))
		{
			if(metals.containsKey(m.name))
				metals.get(m.name).amount += amt;
			else
				metals.put(m.name, new MetalPair(m, amt));

			updateCurrentAlloy();
			return true;
		}
		return false;
	}

	public float getTotalMetal()
	{
		Iterator<MetalPair> iter = metals.values().iterator();
		float totalAmount = 0;
		while(iter.hasNext())
		{
			MetalPair m = iter.next();
			if(m != null)
				totalAmount += m.amount;
		}
		return totalAmount;
	}

	private void updateCurrentAlloy()
	{
		List<AlloyMetal> a = new ArrayList<AlloyMetal>();
		Iterator<MetalPair> iter = metals.values().iterator();
		float totalAmount = getTotalMetal();
		iter = metals.values().iterator();
		while(iter.hasNext())
		{
			MetalPair m = iter.next();
			if(m != null)
				a.add(new AlloyMetal(m.type, (m.amount / totalAmount) * 100f));
		}

		Metal match = AlloyManager.INSTANCE.matchesAlloy(a, Alloy.EnumTier.TierV);
		if(match != null)
		{
			currentAlloy = new Alloy(match, totalAmount); 
			currentAlloy.alloyIngred = a;
		}
		else
		{
			currentAlloy = new Alloy(Global.UNKNOWN, totalAmount);
			currentAlloy.alloyIngred = a;
		}
	}

	@Override
	public int getSizeInventory()
	{
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return storage[i];
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
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		return storage[i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		storage[i] = itemstack;
	}

	@Override
	public String getInventoryName()
	{
		return "Crucible";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
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
	public void closeInventory()
	{
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return true;
	}

	public int getOutCountScaled(int length)
	{
		if(currentAlloy != null)
			return ((int) this.currentAlloy.outputAmount * length) / MAX_UNITS;
		else
			return 0;
	}

	public int getTemperatureScaled(int s)
	{
		return (temperature * s) / 2500;
	}

	public void updateGui(byte action)
	{
		if(!worldObj.isRemote)
		{
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setByte("action", action);
			if (currentAlloy != null) {
				if (action == 0) {
					currentAlloy.toNBT(nbt);
				}
				else if (action == 1 && currentAlloy != null) {
					nbt.setFloat("outputAmount", currentAlloy.outputAmount);
				}
			}
			this.broadcastPacketInRange(this.createDataPacket(nbt));
		}
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt)
	{
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt) {
		byte action = nbt.getByte("action");
		if(action == 0)
			this.currentAlloy = new Alloy().fromNBT(nbt);
		else if(action == 1 && currentAlloy != null)
		{
			currentAlloy.outputAmount = nbt.getFloat("outputAmount");
		}
		else if(action == 2)
			currentAlloy = null;

	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) 
	{

	}

}
