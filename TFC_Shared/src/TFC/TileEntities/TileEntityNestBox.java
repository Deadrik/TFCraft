package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
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
import TFC.API.Util.StringUtil;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Time;
import TFC.Entities.Mobs.EntityChickenTFC;
import TFC.Handlers.PacketHandler;
import TFC.Items.ItemTerra;

public class TileEntityNestBox extends NetworkTileEntity implements IInventory
{

	private ItemStack[] itemstack = new ItemStack[4];
	private ItemStack output;

	public TileEntityNestBox()
	{		
		shouldSendInitData = true;
	}

	public void careForInventorySlot()
	{
		
	}

	private void ProcessItems()
	{
		
		
	}

	public boolean hasBird(){
		List list = worldObj.getEntitiesWithinAABB(EntityChickenTFC.class, AxisAlignedBB.getBoundingBox(
				xCoord, yCoord, zCoord, 
				xCoord+1, yCoord+1.1, zCoord+1));
		if(list.size()!=0){
			return true;
		}
		return false;	
	}
	
	public EntityAnimal getBird(){
		List list = worldObj.getEntitiesWithinAABB(EntityChickenTFC.class, AxisAlignedBB.getBoundingBox(
				xCoord, yCoord, zCoord, 
				xCoord+0.5, yCoord+1.1, zCoord+0.5));
		if(list.size()!=0){
			return (EntityAnimal)list.get(0);
		}
		return null;
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub

	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
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
			if(itemstack != null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, 
						itemstack[i]);
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
		return 64;
	}

	@Override
	public String getInvName()
	{
		return "Barrel";
	}

	@Override
	public int getSizeInventory()
	{
		return 4;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		// TODO Auto-generated method stub
		return itemstack[i];
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
		this.itemstack[i] = (ItemStack) itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
		{
			itemstack.stackSize = getInventoryStackLimit();
		}


	}


	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			careForInventorySlot();
			EntityAnimal bird = getBird();
			if(bird!=null){
				ItemStack item = ((EntityChickenTFC)bird).getEggs();
				if(item!=null){
					for(int i = 0; i< this.getSizeInventory();i++){
						if(itemstack[i]==null){
							setInventorySlotContents(i,item);
							break;
						}
					}
				}
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		NBTTagList nbttaglist = new NBTTagList();

		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
		nbttagcompound1.setByte("Slot0", (byte)1);
		if(itemstack[0] != null){
			itemstack[0].writeToNBT(nbttagcompound1);
		}
		nbttagcompound1.setByte("Slot1", (byte)1);
		if(itemstack[1] != null){
			itemstack[1].writeToNBT(nbttagcompound1);
		}
		nbttagcompound1.setByte("Slot2", (byte)1);
		if(itemstack[2] != null){
			itemstack[2].writeToNBT(nbttagcompound1);
		}
		nbttagcompound1.setByte("Slot3", (byte)1);
		if(itemstack[3] != null){
			itemstack[3].writeToNBT(nbttagcompound1);
		}
		nbttaglist.appendTag(nbttagcompound1);

		nbttagcompound.setTag("Items", nbttaglist);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");

		NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(0);
		byte byte0 = nbttagcompound1.getByte("Slot0");
		itemstack[0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		byte byte1 = nbttagcompound1.getByte("Slot1");
		itemstack[1] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		byte byte2 = nbttagcompound1.getByte("Slot2");
		itemstack[2] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		byte byte3 = nbttagcompound1.getByte("Slot3");
		itemstack[3] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
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
	public void createInitPacket(DataOutputStream outStream) throws IOException {
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
