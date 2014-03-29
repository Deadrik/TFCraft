package TFC.TileEntities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.API.ISmeltable;
import TFC.API.Metal;
import TFC.API.Constant.Global;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.Metal.Alloy;
import TFC.Core.Metal.AlloyManager;
import TFC.Core.Metal.AlloyMetal;
import TFC.Core.Metal.MetalPair;
import TFC.Core.Metal.MetalRegistry;
import TFC.Items.ItemMeltedMetal;

public class TECrucible extends TileEntity implements IInventory
{
	public HashMap metals = new HashMap();
	public Alloy currentAlloy;
	public int temperature = 0;
	public ItemStack[] storage;
	public byte inputTick = 0;
	public byte outputTick = 0;
	public byte tempTick = 0;

	public TECrucible()
	{
		storage = new ItemStack[2];
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		nbt.setInteger("temp", temperature);

		NBTTagList nbttaglist = new NBTTagList();
		Iterator iter = metals.values().iterator();
		while(iter.hasNext())
		{
			MetalPair m = (MetalPair) iter.next();
			if(m != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setInteger("ID", Item.getIdFromItem(m.type.Ingot));
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
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.func_148857_g());
	}

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			inputTick++;
			outputTick++;
			tempTick++;
			/*Heat the crucible based on the Forge beneath it*/
			if(worldObj.getBlock(xCoord,yCoord-1,zCoord) == TFCBlocks.Forge)
			{
				TileEntityForge te = (TileEntityForge) worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
				if(te.fireTemperature > temperature)
					temperature++;
			}
			if(tempTick > 22)
			{
				tempTick = 0;
				if(temperature > TFC_Climate.getHeightAdjustedTemp(xCoord, yCoord, zCoord))
					temperature--;
			}

			ItemStack stackToSmelt = storage[0];
			if(stackToSmelt != null)
			{
				Item itemToSmelt = stackToSmelt.getItem();
				if(itemToSmelt instanceof ItemMeltedMetal && TFC_ItemHeat.getIsLiquid(storage[0]))
				{
					if(inputTick > 5)
					{
						if(currentAlloy != null && currentAlloy.outputType != null && itemToSmelt == currentAlloy.outputType.MeltedItem)
						{
							this.addMetal(MetalRegistry.instance.getMetalFromItem(itemToSmelt), (short) 1);
							if(stackToSmelt.getItemDamage()+1 >= storage[0].getMaxDamage())
								storage[0] = new ItemStack(TFCItems.CeramicMold,1,1);
							else
								stackToSmelt.setItemDamage(stackToSmelt.getItemDamage() + 1);
						}
						else
						{
							this.addMetal(MetalRegistry.instance.getMetalFromItem(itemToSmelt), (short) 1);
							if(stackToSmelt.getItemDamage()+1 >= stackToSmelt.getMaxDamage())
								storage[0] = new ItemStack(TFCItems.CeramicMold,1,1);
							else
								stackToSmelt.setItemDamage(stackToSmelt.getItemDamage() + 1);
						}
						inputTick = 0;
						updateGui((byte) 0); //TODO
					}
				}
				else if(itemToSmelt instanceof ISmeltable && (
						(ISmeltable)itemToSmelt).isSmeltable(stackToSmelt) &&
						!TFC_Core.isOreIron(stackToSmelt) &&
						temperature >= TFC_ItemHeat.getMeltingPoint(stackToSmelt))
				{
					Metal mType =((ISmeltable)itemToSmelt).GetMetalType(stackToSmelt);
					if(addMetal(mType, ((ISmeltable)itemToSmelt).GetMetalReturnAmount(stackToSmelt)))
					{
						temperature *= 0.9f;
						if(stackToSmelt.stackSize <= 1)
							storage[0] = null;
						updateGui((byte) 0); //TODO
					}
				}
			}
			//Metal Output handling
			if(currentAlloy != null &&
					storage[1] != null &&
					currentAlloy.outputType != null &&
					outputTick >= 3 &&
					temperature >= TFC_ItemHeat.getMeltingPoint(currentAlloy.outputType))
			{
				if(storage[1].getItem() == TFCItems.CeramicMold)
				{
					storage[1] = new ItemStack(currentAlloy.outputType.MeltedItem, 1, 99);
					TFC_ItemHeat.SetTemperature(storage[1], temperature);
					//currentAlloy.outputAmount--;
					drainOutput(1.0f);
					updateGui((byte) 1); //TODO
				}
				else if(storage[1].getItem() == currentAlloy.outputType.MeltedItem && storage[1].getItemDamage() > 0)
				{
					storage[1].setItemDamage(storage[1].getItemDamage()-1);
					float inTemp =TFC_ItemHeat.GetTemperature(storage[1]);
					float temp = (temperature - inTemp) / 2;
					TFC_ItemHeat.SetTemperature(storage[1], inTemp+temp);
					//System.out.println(temperature +", "+inTemp+", "+temp);
					drainOutput(1.0f);
					storage[1].stackSize = 1;
					updateGui((byte) 1); //TODO
				}
				outputTick = 0;
			}

			if(currentAlloy != null && this.getTotalMetal() < 1)
			{
				metals = new HashMap();
				updateCurrentAlloy();
				this.updateGui((byte) 2); //TODO
				currentAlloy = null;
			}

			if(storage[1] != null && storage[1].stackSize <= 0)
				storage[1].stackSize = 1;
			if(inputTick > 5)
				inputTick = 0;
			if(outputTick >= 3)
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
		if(getTotalMetal()+amt <= 3000 && m.Name != "Unknown")
		{
			if(metals.containsKey(m.Name))
				((MetalPair)metals.get(m.Name)).amount += amt;
			else
				metals.put(m.Name, new MetalPair(m, amt));

			updateCurrentAlloy();
			return true;
		}
		return false;
	}

	public float getTotalMetal()
	{
		Iterator iter = metals.values().iterator();
		float totalAmount = 0;
		while(iter.hasNext())
		{
			MetalPair m = (MetalPair) iter.next();
			if(m != null)
				totalAmount += m.amount;
		}
		return totalAmount;
	}

	private void updateCurrentAlloy()
	{
		List<AlloyMetal> a = new ArrayList<AlloyMetal>();
		Iterator iter = metals.values().iterator();
		float totalAmount = getTotalMetal();
		iter = metals.values().iterator();
		while(iter.hasNext())
		{
			MetalPair m = (MetalPair) iter.next();
			if(m != null)
				a.add(new AlloyMetal(m.type, (m.amount/totalAmount) * 100f));
		}

		Metal match = AlloyManager.instance.matchesAlloy(a, Alloy.EnumTier.TierV);
		if(match != null)
		{
			currentAlloy = new Alloy(match, totalAmount); 
			currentAlloy.AlloyIngred = a;
		}
		else
		{
			currentAlloy = new Alloy(Global.UNKNOWN, totalAmount);
			currentAlloy.AlloyIngred = a;
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
		return 1;
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
			return ((int)this.currentAlloy.outputAmount * length)/3000;
		else
			return 0;
	}

	public int getTemperatureScaled(int s)
	{
		return (temperature * s) / 2500;
	}

	//////////////////////////////////////////////////////////////////////////
	//TODO Udate packet
	public void updateGui(byte id)
	{
//		if(!worldObj.isRemote)
//			TerraFirmaCraft.proxy.sendCustomPacketToPlayersInRange(xCoord, yCoord, zCoord, createUpdatePacket(id), 5);
	}
//	public void handleDataPacket(DataInputStream inStream) throws IOException 
//	{
//		byte id = inStream.readByte();
//		if(id == 0 && inStream.available() > 0)
//			this.currentAlloy = new Alloy().fromPacket(inStream);
//		else if(id == 1)
//			currentAlloy.outputAmount = inStream.readFloat();
//		else if(id == 2)
//			currentAlloy = null;
//	}
//	public Packet createUpdatePacket(byte id)
//	{
//		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
//		DataOutputStream dos=new DataOutputStream(bos);	
//		try
//		{
//			dos.writeByte(PacketHandler.Packet_Data_Block_Client);
//			dos.writeInt(xCoord);
//			dos.writeInt(yCoord);
//			dos.writeInt(zCoord);
//			if(id == 0 && currentAlloy != null)
//			{
//				dos.writeByte(0);
//				currentAlloy.toPacket(dos);
//			}
//			else if(id == 1 && currentAlloy != null)
//			{
//				dos.writeByte(1);
//				dos.writeFloat(this.getTotalMetal());
//			}
//			else if(id == 2)
//			{
//				dos.writeByte(2);
//			}
//		}
//		catch (IOException e)
//		{
//		}
//		return null;// this.setupCustomPacketData(bos.toByteArray(), bos.size());
//	}

}
