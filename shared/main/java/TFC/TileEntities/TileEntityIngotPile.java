package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import TFC.TFCItems;
import TFC.Core.TFC_ItemHeat;
import TFC.Handlers.PacketHandler;

public class TileEntityIngotPile extends NetworkTileEntity implements IInventory
{
	public ItemStack[] storage;
	public String type;
	public static int[] INGOTS;
	
	public TileEntityIngotPile()
	{
		storage = new ItemStack[1];
		type = "Copper";
		INGOTS = new int[]{(TFCItems.BismuthIngot.itemID),(TFCItems.BismuthBronzeIngot.itemID),(TFCItems.BlackBronzeIngot.itemID),
				(TFCItems.BlackSteelIngot.itemID),(TFCItems.BlueSteelIngot.itemID),(TFCItems.BrassIngot.itemID),(TFCItems.BronzeIngot.itemID),
				(TFCItems.CopperIngot.itemID),(TFCItems.GoldIngot.itemID),(TFCItems.WroughtIronIngot.itemID),(TFCItems.LeadIngot.itemID),
				(TFCItems.NickelIngot.itemID),(TFCItems.PigIronIngot.itemID),(TFCItems.PlatinumIngot.itemID),(TFCItems.RedSteelIngot.itemID),
				(TFCItems.RoseGoldIngot.itemID),(TFCItems.SilverIngot.itemID),(TFCItems.SteelIngot.itemID),(TFCItems.SterlingSilverIngot.itemID),
				(TFCItems.TinIngot.itemID),(TFCItems.ZincIngot.itemID), TFCItems.UnknownIngot.itemID};
	}
	public static int[] getIngots(){
		return INGOTS;
	}
	
	public void setType(String i)
	{
		type = i;
	}
	
	public int getStack(){
		return storage[0].stackSize;
	}

	public String getType(){
		return this.type;
	}

	public void addContents(int index, ItemStack is)
	{
		if(storage[index] == null) {
			storage[index] = is;
		}
	}

	public void clearContents()
	{
		storage[0] = null;
	}

	@Override
	public void closeChest() {

	}

	public boolean contentsMatch(int index, ItemStack is)
	{
		if(storage[index] != null){
			if(storage[index].stackSize == 0){
				return true;
			}
		}
		if(storage[index].getItem() == is.getItem() && storage[index].getItem().itemID == is.getItem().itemID &&
				/*storage[index].stackSize < storage[index].getMaxStackSize() &&*/ storage[index].stackSize+1 <= this.getInventoryStackLimit())
		{
			return true;
		}


		return false;
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
			{
				storage[i] = null;
			}
			return itemstack1;
		} else
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
			if(storage[i]!= null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, 
						storage[i]);
				entityitem.motionX = (float)rand.nextGaussian() * f3;
				entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float)rand.nextGaussian() * f3;
				worldObj.spawnEntityInWorld(entityitem);
				storage[i] = null;
			}
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public String getInvName()
	{
		return "Ingot Pile";
	}

	@Override
	public int getSizeInventory()
	{
		return storage.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return this.storage[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	public void injectContents(int index, int count)
	{
		if(storage[index] != null) {
			if(storage[index].stackSize > 0){
				storage[index] = 
						new ItemStack(storage[index].getItem(),
								storage[index].stackSize+count,
								storage[index].getItemDamage());
				
			}
		}
	}


	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openChest() {

	}
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		//nbttagcompound.setInteger("type", type);
		type = nbttagcompound.getString("type");
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
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
	public void setInventorySlotContents(int i, ItemStack itemstack) 
	{
		storage[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
		{
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public void updateEntity()
	{
		TFC_ItemHeat.HandleContainerHeat(this.worldObj,storage, xCoord,yCoord,zCoord);
	}
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setString("type", type);
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
	}

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException 
	{
		handleInitPacket(inStream);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream)throws IOException 
	{
		
		
	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException {
		outStream.writeUTF(type);
		outStream.writeInt(storage[0] != null ? storage[0].itemID : -1);
		outStream.writeInt(storage[0] != null ? storage[0].stackSize : 0);
	}

	@Override
	public void handleInitPacket(DataInputStream inStream) throws IOException 
	{
		type = inStream.readUTF();
		int s1 = inStream.readInt();
		int size = inStream.readInt();
		storage[0] = s1 != -1 ? new ItemStack(Item.itemsList[s1],size) : null;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	public Packet createUpdatePacket()
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);
		try {
			dos.writeByte(PacketHandler.Packet_Data_Block_Client);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeUTF(type);
			dos.writeInt(storage[0] != null ? storage[0].itemID : -1);
			dos.writeInt(storage[0] != null ? storage[0].stackSize : 0);
		} catch (IOException e) {
		}

		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}
	@Override
	public boolean isInvNameLocalized() 
	{
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) 
	{
		return false;
	}
}
