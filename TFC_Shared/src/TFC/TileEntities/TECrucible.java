package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
import net.minecraft.network.packet.Packet;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
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
import TFC.Handlers.PacketHandler;
import TFC.Items.ItemMeltedMetal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TECrucible extends NetworkTileEntity implements IInventory
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
				nbttagcompound1.setInteger("ID", m.type.IngotID);
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

		if(currentAlloy != null)
		{
			nbt.setInteger("outputAmount", currentAlloy.outputAmount);
		}
		else
		{
			nbt.setInteger("outputAmount", 0);
		}
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

		NBTTagList nbttaglist = nbt.getTagList("Metals");

		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
			int id = nbttagcompound1.getInteger("ID");
			float amount = nbttagcompound1.getShort("Amount");
			//Added so that hopefully old worlds that stored metal as shorts wont break
			float amountF = amount + nbttagcompound1.getFloat("AmountF");
			Metal m = MetalRegistry.instance.getMetalFromItem(Item.itemsList[id]);
			addMetal(MetalRegistry.instance.getMetalFromItem(Item.itemsList[id]), amount);
		}

		nbttaglist = nbt.getTagList("Items");
		storage = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < storage.length)
			{
				storage[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		if(currentAlloy != null)
		{
			currentAlloy.outputAmount = nbt.getInteger("outputAmount");
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
			/*Heat the crucible based on the Forge beneath it*/
			if(worldObj.getBlockId(xCoord,yCoord-1,zCoord) == TFCBlocks.Forge.blockID)
			{
				TileEntityForge te = (TileEntityForge) worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord);
				if(te.fireTemperature > temperature) {
					temperature++;
				}
			}
			if(tempTick > 22)
			{
				tempTick = 0;
				if(temperature > TFC_Climate.getHeightAdjustedTemp(xCoord, yCoord, zCoord)) {
					temperature--;
				}
			}

			ItemStack stackToSmelt = storage[0];
			if(stackToSmelt != null)
			{
				Item itemToSmelt = stackToSmelt.getItem();

				if(itemToSmelt instanceof ItemMeltedMetal && TFC_ItemHeat.getIsLiquid(storage[0]))
				{
					if(inputTick > 5)
					{
						if(currentAlloy != null && currentAlloy.outputType != null && itemToSmelt.itemID == currentAlloy.outputType.MeltedItemID)
						{
							this.addMetal(MetalRegistry.instance.getMetalFromItem(itemToSmelt), (short) 1);
							if(stackToSmelt.getItemDamage()+1 >= storage[0].getMaxDamage()) {
								storage[0] = new ItemStack(TFCItems.CeramicMold,1,1);
							} else {
								stackToSmelt.setItemDamage(stackToSmelt.getItemDamage() + 1);
							}
						}
						else
						{
							this.addMetal(MetalRegistry.instance.getMetalFromItem(itemToSmelt), (short) 1);
							if(stackToSmelt.getItemDamage()+1 >= stackToSmelt.getMaxDamage()) {
								storage[0] = new ItemStack(TFCItems.CeramicMold,1,1);
							} else {
								stackToSmelt.setItemDamage(stackToSmelt.getItemDamage() + 1);
							}
						}
						inputTick = 0;
						updateGui((byte) 0);
					}
				}
				else if(itemToSmelt instanceof ISmeltable && ((ISmeltable)itemToSmelt).isSmeltable(stackToSmelt) && !TFC_Core.isOreIron(stackToSmelt) 
						&& temperature >= TFC_ItemHeat.getMeltingPoint(stackToSmelt))
				{
					Metal mType =((ISmeltable)itemToSmelt).GetMetalType(stackToSmelt);
					if(addMetal(mType, ((ISmeltable)itemToSmelt).GetMetalReturnAmount(stackToSmelt)))
					{
						temperature *= 0.9f;

						if(stackToSmelt.stackSize <= 1) {
							storage[0] = null;
						}

						updateGui((byte) 0);
					}
				}
			}
			//Metal Output handling
			if(currentAlloy != null && storage[1] != null && currentAlloy.outputType != null && outputTick >= 3 && temperature >= TFC_ItemHeat.getMeltingPoint(currentAlloy.outputType))
			{
				if(storage[1].itemID == TFCItems.CeramicMold.itemID)
				{
					storage[1] = new ItemStack(Item.itemsList[currentAlloy.outputType.MeltedItemID], 1, 99);
					TFC_ItemHeat.SetTemperature(storage[1], temperature);
					//currentAlloy.outputAmount--;
					drainOutput(1.0f);
					updateGui((byte) 1);
				}
				else if(storage[1].itemID == currentAlloy.outputType.MeltedItemID && storage[1].getItemDamage() > 0)
				{
					storage[1].setItemDamage(storage[1].getItemDamage()-1);
					float inTemp =TFC_ItemHeat.GetTemperature(storage[1]);
					float temp = (temperature - inTemp) / 2;
					TFC_ItemHeat.SetTemperature(storage[1], inTemp+temp);
					//System.out.println(temperature +", "+inTemp+", "+temp);
					//currentAlloy.outputAmount--;
					drainOutput(1.0f);
					storage[1].stackSize = 1;
					updateGui((byte) 1);
				}
				outputTick = 0;
			}

			if(currentAlloy != null && currentAlloy.outputAmount <= 0) {
				metals = new HashMap();
				updateCurrentAlloy();
				this.updateGui((byte) 2);
				currentAlloy = null;
			}

			if(storage[1] != null && storage[1].stackSize <= 0) {
				storage[1].stackSize = 1;
			}
			if(inputTick > 5) {
				inputTick = 0;
			}
			if(outputTick >= 3) {
				outputTick = 0;
			}
		}
	}

	public boolean drainOutput(float amount)
	{
		if(metals != null && metals.values().size() > 0) 
		{
			int numMetals = metals.values().size();
			amount /= numMetals;
			for(Object am : metals.values())
			{

				((MetalPair)am).amount -= amount;
			}

			updateCurrentAlloy();
		}
		return true;
	}

	public boolean addMetal(Metal m, float amt)
	{
		if(getTotalMetal()+amt <= 3000 && m.Name != "Unknown")
		{
			if(metals.containsKey(m.Name)) {
				((MetalPair)metals.get(m.Name)).amount += amt;
			} else {
				metals.put(m.Name, new MetalPair(m, amt));
			}

			updateCurrentAlloy();

			return true;
		}
		return false;
	}

	public int getTotalMetal()
	{
		Iterator iter = metals.values().iterator();
		int totalAmount = 0;
		while(iter.hasNext())
		{
			MetalPair m = (MetalPair) iter.next();
			if(m != null)
			{
				totalAmount += m.amount;
			}
		}
		return totalAmount;
	}

	private void updateCurrentAlloy()
	{
		List<AlloyMetal> a = new ArrayList<AlloyMetal>();

		Iterator iter = metals.values().iterator();
		int totalAmount = getTotalMetal();

		iter = metals.values().iterator();
		while(iter.hasNext())
		{
			MetalPair m = (MetalPair) iter.next();
			if(m != null)
			{
				a.add(new AlloyMetal(m.type, (m.amount/totalAmount) * 100f));
			}
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
	public void handleDataPacket(DataInputStream inStream) throws IOException 
	{
		byte id = inStream.readByte();
		if(id == 0 && inStream.available() > 0) 
		{
			this.currentAlloy = new Alloy().fromPacket(inStream);
		} 
		else if(id == 1)
		{
			currentAlloy.outputAmount = inStream.readInt();
		}
		else if(id == 2)
		{
			currentAlloy = null;
		}
	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleInitPacket(DataInputStream inStream) throws IOException {
		// TODO Auto-generated method stub

	}


	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return storage[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
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
			{
				storage[i] = null;
			}
			return itemstack1;
		} else
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		// TODO Auto-generated method stub
		return storage[i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) 
	{
		storage[i] = itemstack;
	}

	@Override
	public String getInvName() {
		return "Crucible";
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return true;
	}

	public Packet createUpdatePacket(byte id)
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);	
		try
		{
			dos.writeByte(PacketHandler.Packet_Data_Block_Client);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			if(id == 0 && currentAlloy != null)
			{
				dos.writeByte(0);
				currentAlloy.toPacket(dos);
			}
			else if(id == 1 && currentAlloy != null)
			{
				dos.writeByte(1);
				dos.writeInt(this.getTotalMetal());
			}
			else if(id == 2)
			{
				dos.writeByte(2);
			}
		} 
		catch (IOException e) {
		}
		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}

	public int getOutCountScaled(int length)
	{
		if(currentAlloy != null) {
			return (this.currentAlloy.outputAmount * length)/3000;
		} else {
			return 0;
		}
	}

	public int getTemperatureScaled(int s)
	{
		return (temperature * s) / 2500;
	}

	public void updateGui(byte id)
	{
		if(!worldObj.isRemote) {
			TerraFirmaCraft.proxy.sendCustomPacketToPlayersInRange(xCoord, yCoord, zCoord, createUpdatePacket(id), 5);
		}
	}
}
