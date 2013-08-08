package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.AxisAlignedBB;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.API.ISmeltable;
import TFC.API.Metal;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.Metal.Alloy;
import TFC.Core.Metal.AlloyManager;
import TFC.Core.Metal.AlloyMetal;
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
	public TECrucible()
	{
		storage = new ItemStack[2];
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);

		NBTTagList nbttaglist = new NBTTagList();
		Iterator iter = metals.values().iterator();
		while(iter.hasNext())
		{
			MetalPair m = (MetalPair) iter.next();
			if(m != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setInteger("ID", m.type.IngotID);
				nbttagcompound1.setShort("Amount", m.amount);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Metals", nbttaglist);

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

		nbttagcompound.setTag("Items", nbttaglist);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);

		NBTTagList nbttaglist = nbttagcompound.getTagList("Metals");

		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
			int id = nbttagcompound1.getInteger("ID");
			short amount = nbttagcompound1.getShort("Amount");

			Metal m = MetalRegistry.instance.getMetalFromItem(Item.itemsList[id]);
			addMetal(MetalRegistry.instance.getMetalFromItem(Item.itemsList[id]), amount);
		}

		nbttaglist = nbttagcompound.getTagList("Items");
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
	}

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			inputTick++;
			outputTick++;
			
			/*Heat the crucible based on the Forge beneath it*/
			if(worldObj.getBlockId(xCoord,yCoord-1,zCoord) == TFCBlocks.Forge.blockID)
			{
				TileEntityForge te = (TileEntityForge) worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord);
				if(te.fireTemperature > temperature)
					temperature++;
			}
			
			/*Create a list of all the items that are falling into the stack */
			List list = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(
					xCoord+0.0625, yCoord, zCoord+0.0625, xCoord+0.9375, yCoord+1, zCoord+0.9375));

			if (list != null && !list.isEmpty())
			{
				/*Iterate through the list and check for charcoal, coke, and ore*/
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					EntityItem entity = (EntityItem)iterator.next();
					if(entity.getEntityItem().getItem() instanceof ISmeltable)
					{
						ISmeltable obj = (ISmeltable)entity.getEntityItem().getItem();
						for(int c = 0; c < entity.getEntityItem().stackSize; c++)
						{

							if(addMetal(obj.GetMetalType(entity.getEntityItem()), obj.GetMetalReturnAmount(entity.getEntityItem())))
							{
								entity.getEntityItem().stackSize--;
							}
							else break;
						}

						if(entity.getEntityItem().stackSize == 0) 
						{
							entity.setDead();
						}
					}
				}
			}

			if(storage[0] != null && storage[0].getItem() instanceof ItemMeltedMetal && TFC_ItemHeat.getIsLiquid(storage[0]))
			{
				if(inputTick > 5)
				{
					if(currentAlloy.outputType != null && storage[0].getItem().itemID == currentAlloy.outputType.MeltedItemID)
					{
						currentAlloy.outputAmount++;
						if(storage[0].getItemDamage()+1 >= storage[0].getMaxDamage())
							storage[0] = new ItemStack(TFCItems.CeramicMold,1,1);
						else
							storage[0].setItemDamage(storage[0].getItemDamage() + 1);
					}
					else
					{
						if(storage[0].getItemDamage()+1 >= storage[0].getMaxDamage())
							storage[0] = new ItemStack(TFCItems.CeramicMold,1,1);
						else
							storage[0].setItemDamage(storage[0].getItemDamage() + 1);
						this.addMetal(MetalRegistry.instance.getMetalFromItem(storage[0].getItem()), (short) 1);
					}
					inputTick = 0;
					updateGui((byte) 0);
				}
			}
			else if(storage[0] != null && storage[0].getItem() instanceof ISmeltable && !TFC_Core.isOreIron(storage[0]))
			{
				Metal mType =((ISmeltable)storage[0].getItem()).GetMetalType(storage[0]);
				if(addMetal(mType, ((ISmeltable)storage[0].getItem()).GetMetalReturnAmount(storage[0])))
				{
					temperature *= 0.9f;
					
					if(storage[0].stackSize <= 1)
						storage[0] = null;
					else
						storage[0].stackSize--;
					
					updateGui((byte) 0);
				}
			}

			//Metal Output handling
			if(currentAlloy != null && storage[1] != null && currentAlloy.outputType != null && outputTick >= 3 && temperature >= TFC_ItemHeat.getMeltingPoint(currentAlloy.outputType))
			{
				if(storage[1].itemID == TFCItems.CeramicMold.itemID)
				{
					storage[1] = new ItemStack(Item.itemsList[currentAlloy.outputType.MeltedItemID], 1, 99);
					currentAlloy.outputAmount--;
					updateGui((byte) 1);
				}
				else if(storage[1].itemID == currentAlloy.outputType.MeltedItemID)
				{
					storage[1].setItemDamage(storage[1].getItemDamage()-1);
					currentAlloy.outputAmount--;
					updateGui((byte) 1);
				}
				outputTick = 0;
			}
		}
	}

	private boolean addMetal(Metal m, short amt)
	{
		if(getTotalMetal()+amt <= 3000)
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
				a.add(new AlloyMetal(m.type, ((float)m.amount/(float)totalAmount) * 100f));
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
			currentAlloy = new Alloy(null, totalAmount);
			currentAlloy.AlloyIngred = a;
		}
	}

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException 
	{
		byte id = inStream.readByte();
		if(id == 0 && inStream.available() > 0)
			this.currentAlloy = new Alloy().fromPacket(inStream);
		else if(id == 1)
		{
			currentAlloy.outputAmount = inStream.readInt();
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

	private class MetalPair
	{
		public Metal type;
		public short amount;
		public MetalPair(Metal t, short amnt)
		{
			type = t;
			amount = amnt;
		}
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
			storage[i].stackSize -= j;
		return storage[i];
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
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
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
				dos.writeInt(currentAlloy.outputAmount);
			}
		} 
		catch (IOException e) {
		}
		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}

	public int getOutCountScaled(int length)
	{
		if(currentAlloy != null)
			return (this.currentAlloy.outputAmount * length)/3000;
		else return 0;
	}

	public int getTemperatureScaled(int s)
	{
		return (temperature * s) / 2500;
	}

	public void updateGui(byte id)
	{
		if(!worldObj.isRemote)
			TerraFirmaCraft.proxy.sendCustomPacketToPlayersInRange(xCoord, yCoord, zCoord, createUpdatePacket(id), 5);
	}
}
