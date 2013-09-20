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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.AxisAlignedBB;
import TFC.TerraFirmaCraft;
import TFC.API.Entities.IAnimal.GenderEnum;
import TFC.Core.TFC_Time;
import TFC.Entities.Mobs.EntityChickenTFC;
import TFC.Handlers.PacketHandler;

public class TENestBox extends NetworkTileEntity implements IInventory
{
	public ItemStack[] inventory = new ItemStack[4];

	public TENestBox()
	{		
		shouldSendInitData = true;
	}

	public boolean hasBird(){
		return getBird() != null;	
	}

	public EntityAnimal getBird(){
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
		if(list.size()!=0){
			for(Object e : list)
			{
				if(((EntityChickenTFC)e).getGender() == GenderEnum.FEMALE && ((EntityChickenTFC)e).isAdult())
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
		if(list.size() != 0)
		{
			for(Object e : list)
			{
				if(((EntityChickenTFC)e).getGender() == GenderEnum.MALE && ((EntityChickenTFC)e).isAdult())
				{
					return (EntityChickenTFC)e;
				}
			}
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
			if(inventory != null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, 
						inventory[i]);
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
		// TODO Auto-generated method stub
		return inventory[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openChest() {

	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		this.inventory[i] = itemstack;
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
			EntityAnimal bird = getBird();
			if(bird!=null)
			{
				ItemStack item = ((EntityChickenTFC)bird).getEggs();
				EntityChickenTFC father = (EntityChickenTFC) getRooster();
				for(int i = 0; item != null && i < this.getSizeInventory();i++)
				{
					if(inventory[i] == null)
					{
						if(father != null)
						{
							NBTTagCompound nbt = new NBTTagCompound();
							nbt.setLong("Fertilized", TFC_Time.getTotalTicks()+(long)(TFC_Time.ticksInMonth*0.75f));
							nbt.setTag("Genes", this.createGenes((EntityChickenTFC) bird, father));
							item.setTagCompound(nbt);
						}
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
						long _time = inventory[i].getTagCompound().getLong("Fertilized");
						if(_time <= TFC_Time.getTotalTicks())
						{
							EntityChickenTFC chick = new EntityChickenTFC(worldObj,xCoord+0.5,yCoord+1,zCoord+0.5, 
									(NBTTagCompound) inventory[i].getTagCompound().getTag("Genes"));
							Random rand = new Random();
							chick.setLocationAndAngles (xCoord+(rand.nextFloat()-0.5F)*2F,yCoord,zCoord+(rand.nextFloat()-0.5F)*2F, 0.0F, 0.0F);
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

	public NBTTagCompound createGenes(EntityChickenTFC mother, EntityChickenTFC father)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setFloat("m_size", mother.getSize());
		nbt.setFloat("f_size", father.getSize());

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
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		inventory = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < inventory.length)
			{
				inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
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
