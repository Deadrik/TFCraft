package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.AxisAlignedBB;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.API.TFCOptions;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Time;
import TFC.Core.Util.StringUtil;
import TFC.Entities.EntityStand;
import TFC.Handlers.PacketHandler;
import TFC.Items.ItemTerra;

public class TEStand extends NetworkTileEntity implements IInventory
{

	public ItemStack[] items;
	public EntityStand entity;
	public float yaw = 0;
	private boolean hasEntity = false;

	public TEStand()
	{
		shouldSendInitData = true;
		items = new ItemStack[5];
		items[4] = new ItemStack(TFCItems.CopperHelmet,1,0);
		items[3] = new ItemStack(TFCItems.CopperChestplate,1,0);
		items[2] = new ItemStack(TFCItems.CopperGreaves,1,0);
		items[1] = new ItemStack(TFCItems.CopperBoots,1,0);
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub

	}
	
	public void destroy(){
		entity.setDead();
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(items[i] != null)
		{
			if(items[i].stackSize <= j)
			{
				ItemStack itemstack2 = items[i];
				items[i] = null;
				return itemstack2;
			}
			ItemStack itemstack1 = items[i].splitStack(j);
			if(items[i].stackSize == 0)
			{
				items[i] = null;
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
			if(items[i] != null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, 
						items[i]);
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
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String getInvName()
	{
		return "Stand";
	}

	@Override
	public int getSizeInventory()
	{
		return 5;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		// TODO Auto-generated method stub
		return items[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		items[i] = itemstack;
		if(items[i] != null && items[i].stackSize > getInventoryStackLimit())
		{
			items[i].stackSize = getInventoryStackLimit();
		}


	}

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			if(hasWorldObj() && !hasEntity){
				entity = new EntityStand(worldObj,this);
				entity.setLocationAndAngles(xCoord, yCoord, zCoord, yaw, 0);
				worldObj.spawnEntityInWorld(entity);
				entity.standTE = this;
				hasEntity = true;
			}
			if(hasEntity && entity == null){
				List list = worldObj.getEntitiesWithinAABB(EntityStand.class, AxisAlignedBB.getBoundingBox(
						xCoord, yCoord, zCoord, 
						xCoord+1, yCoord+2, zCoord+1));
				if(list.size() != 0){
					entity = (EntityStand)list.get(0);
				}
				else{
					hasEntity = false;
				}
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		NBTTagList nbttaglist = new NBTTagList();
		nbttagcompound.setBoolean("hasEntity", hasEntity);
		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
		nbttagcompound1.setByte("Slot", (byte)1);
		if(items[0] != null){
			items[0].writeToNBT(nbttagcompound1);
		}
		nbttagcompound1.setByte("Slot1", (byte)1);
		if(items[1] != null){
			items[1].writeToNBT(nbttagcompound1);
		}
		nbttagcompound1.setByte("Slot2", (byte)1);
		if(items[2] != null){
			items[2].writeToNBT(nbttagcompound1);
		}
		nbttagcompound1.setByte("Slot3", (byte)1);
		if(items[3] != null){
			items[3].writeToNBT(nbttagcompound1);
		}
		nbttagcompound1.setByte("Slot4", (byte)1);
		if(items[4] != null){
			items[4].writeToNBT(nbttagcompound1);
		}
		nbttaglist.appendTag(nbttagcompound1);
		nbttagcompound.setTag("Items", nbttaglist);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		hasEntity = nbttagcompound.getBoolean("hasEntity");
		NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(0);
		byte byte0 = nbttagcompound1.getByte("Slot");
		items[0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		byte0 = nbttagcompound1.getByte("Slot1");
		items[1] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		byte0 = nbttagcompound1.getByte("Slot2");
		items[2] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		byte0 = nbttagcompound1.getByte("Slot3");
		items[3] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		byte0 = nbttagcompound1.getByte("Slot4");
		items[4] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
	}

	public void updateGui()
	{
		if(!worldObj.isRemote) {
			TerraFirmaCraft.proxy.sendCustomPacketToPlayersInRange(xCoord, yCoord, zCoord, createUpdatePacket(), 5);
		}
	}

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException {
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
		} catch (IOException e) {
		}
		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException {;
	}

	public Packet createSealPacket()
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);

		try {
			dos.writeByte(PacketHandler.Packet_Data_Block_Server);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
		} catch (IOException e) {
		}

		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}

	@Override
	public void handleInitPacket(DataInputStream inStream) throws IOException {
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream)
			throws IOException {
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
