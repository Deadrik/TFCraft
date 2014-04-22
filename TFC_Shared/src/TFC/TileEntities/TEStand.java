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
import TFC.TerraFirmaCraft;
import TFC.Entities.EntityStand;
import TFC.Handlers.PacketHandler;

public class TEStand extends NetworkTileEntity implements IInventory
{

	public ItemStack[] items;
	public EntityStand entity;
	public float yaw = 0;
	public boolean isTop = false;
	private boolean hasEntity = false;
	public int highlightedSlot;

	public TEStand()
	{
		items = new ItemStack[5];
		highlightedSlot = -1;
		//items[4] = new ItemStack(TFCItems.CopperHelmet,1,0);
		//items[3] = new ItemStack(TFCItems.CopperChestplate,1,0);
		//items[2] = new ItemStack(TFCItems.CopperGreaves,1,0);
		//items[1] = new ItemStack(TFCItems.CopperBoots,1,0);
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub

	}

	public void destroy(){
		if(!isTop && entity!= null)
			entity.setDead();
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(items[i] != null && !isTop)
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
		if(!isTop){
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
		if(!isTop)
			return items[i];
		return null;
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
		if(!isTop){
			if(items[i]==null || itemstack == null){
				items[i] = itemstack;
			}
			if(items[i] != null && items[i].stackSize > getInventoryStackLimit())
			{
				items[i].stackSize = getInventoryStackLimit();
			}
		}
	}

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote && !isTop)
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
		nbttagcompound.setFloat("yaw", yaw);
		nbttagcompound.setBoolean("isTop",isTop);
		for(int i = 0; i < items.length; i++)
		{
			if(items[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				items[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Items", nbttaglist);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		hasEntity = nbttagcompound.getBoolean("hasEntity");
		yaw = nbttagcompound.getFloat("yaw");
		isTop = nbttagcompound.getBoolean("isTop");
		items = new ItemStack[getSizeInventory()];
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < items.length)
			{
				items[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}

	public void updateGui()
	{
		if(!worldObj.isRemote) {
			TerraFirmaCraft.proxy.sendCustomPacketToPlayersInRange(xCoord, yCoord, zCoord, createUpdatePacket(), 5);
		}
	}


	public Packet createHighlightPacket(int i)
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);
		this.highlightedSlot = i;
		try {
			dos.writeByte(PacketHandler.Packet_Data_Block_Server);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeInt(i);
		} catch (IOException e) {

		}
		//worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException {
		this.yaw = inStream.readFloat();
		this.isTop = inStream.readBoolean();
		highlightedSlot = inStream.readInt();
		int item;
		for(int i = 0; i < items.length;i++){
			item = inStream.readInt();
			if(Item.itemsList[item] != null){
				items[i] = new ItemStack(Item.itemsList[item]);
			}
		}
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public Packet createUpdatePacket() {
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);
		try {
			dos.writeByte(PacketHandler.Packet_Data_Block_Client);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeFloat(yaw);
			dos.writeBoolean(isTop);
			for(int j = 0; j < items.length;j++){
				if(items[j]!=null){
					dos.writeInt(items[j].itemID);
				}
				else{
					dos.writeInt(0);
				}
			}
		} catch (IOException e) {
		}
		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream) throws IOException {
		highlightedSlot = inStream.readInt();
		//worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException {
		outStream.writeFloat(yaw);
		outStream.writeBoolean(isTop);
		outStream.writeInt(highlightedSlot);
		for(int i = 0; i < items.length;i++){
			if(items[i]!=null){
				outStream.writeInt(items[i].itemID);
			}
			else{
				outStream.writeInt(0);
			}
		}
	}

	@Override
	public void handleInitPacket(DataInputStream inStream) throws IOException {
		yaw = inStream.readFloat();
		isTop = inStream.readBoolean();
		highlightedSlot = inStream.readInt();
		int item;
		for(int i = 0; i < items.length;i++){
			item = inStream.readInt();
			if(Item.itemsList[item] != null){
				items[i] = new ItemStack(Item.itemsList[item]);
			}
			else{
				items[i] = null;
			}
		}
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
}
