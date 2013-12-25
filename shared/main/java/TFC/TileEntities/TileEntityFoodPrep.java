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
import TFC.TerraFirmaCraft;
import TFC.Core.TFC_ItemHeat;
import TFC.Food.ItemTerraFood;
import TFC.Handlers.PacketHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityFoodPrep extends NetworkTileEntity implements IInventory
{
	public ItemStack[] storage = new ItemStack[6];

	@Override
	public void updateEntity()
	{
		TFC_ItemHeat.HandleContainerHeat(this.worldObj,storage, xCoord,yCoord,zCoord);
	}

	public void actionCreate()
	{
		if(!worldObj.isRemote)
		{
			if(storage[4] == null && storage[5].getItem().itemID == Item.bowlEmpty.itemID)
			{
				int count = (storage[0] != null ? 1 : 0) + 
						(storage[1] != null ? 1 : 0) + 
						(storage[2] != null ? 1 : 0) + 
						(storage[3] != null ? 1 : 0);
				int id1 = storage[0] != null ? ((ItemTerraFood)storage[0].getItem()).foodID : 1;
				int id2 = storage[1] != null ? ((ItemTerraFood)storage[1].getItem()).foodID : 1;
				int id3 = storage[2] != null ? ((ItemTerraFood)storage[2].getItem()).foodID : 1;
				int id4 = storage[3] != null ? ((ItemTerraFood)storage[3].getItem()).foodID : 1;
				
				if((id1 == id2 || id1 == id3 || id1 == id4) || (id2 == id3 || id2 == id4) || id3 == id4)
				{
					return;
				}

				int seed = id1 * id2 * id3 * id4;

				int fill1 = storage[0] != null ? ((ItemTerraFood)storage[0].getItem()).getHealAmount() : 1;
				int fill2 = storage[1] != null ? ((ItemTerraFood)storage[1].getItem()).getHealAmount() : 1;
				int fill3 = storage[2] != null ? ((ItemTerraFood)storage[2].getItem()).getHealAmount() : 1;
				int fill4 = storage[3] != null ? ((ItemTerraFood)storage[3].getItem()).getHealAmount() : 1;

				int filling = Math.min(fill1 + fill2 + fill3 + fill4, 100);

				if(count >= 2 && filling > 40)
				{
					decrStackSize(0,1);
					decrStackSize(1,1);
					decrStackSize(2,1);
					decrStackSize(3,1);
					decrStackSize(5,1);
					Random R = new Random(this.worldObj.getSeed()+seed);
					if(R.nextInt(5) == 0)
					{
						byte power = (byte)R.nextInt(25*count);
						
						storage[4] = new ItemStack(TFCItems.Meals[R.nextInt(TFCItems.Meals.length)], 1);
						NBTTagCompound nbt = new NBTTagCompound();
						nbt.setByte("effectpower", power);
						nbt.setByte("energy", (byte) R.nextInt(100));
						nbt.setByte("filling", (byte) filling);
						storage[4].setTagCompound(nbt);
					}
					else
					{
						storage[4] = new ItemStack(TFCItems.MealGeneric, 1);
						NBTTagCompound nbt = new NBTTagCompound();
						nbt.setByte("energy", (byte) R.nextInt(100));
						nbt.setByte("filling", (byte) filling);
						storage[4].setTagCompound(nbt);
					}
				}
			}
		}
		else
		{
			TerraFirmaCraft.proxy.sendCustomPacket(createMealPacket());
		}
	}

	public Packet createMealPacket()
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
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);

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
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
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
	public void handleDataPacketServer(DataInputStream inStream) throws IOException 
	{
		actionCreate();
	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException 
	{
		outStream.writeInt(storage[0] != null ? storage[0].itemID : -1);
		outStream.writeInt(storage[1] != null ? storage[1].itemID : -1);
		outStream.writeInt(storage[2] != null ? storage[2].itemID : -1);
		outStream.writeInt(storage[3] != null ? storage[3].itemID : -1);
		outStream.writeInt(storage[5] != null ? storage[5].itemID : -1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleInitPacket(DataInputStream inStream) throws IOException {
		int s1 = inStream.readInt();
		int s2 = inStream.readInt();
		int s3 = inStream.readInt();
		int s4 = inStream.readInt();
		int s5 = inStream.readInt();
		storage[0] = s1 != -1 ? new ItemStack(Item.itemsList[s1]) : null;
		storage[1] = s2 != -1 ? new ItemStack(Item.itemsList[s2]) : null;
		storage[2] = s3 != -1 ? new ItemStack(Item.itemsList[s3]) : null;
		storage[3] = s4 != -1 ? new ItemStack(Item.itemsList[s4]) : null;
		storage[5] = s5 != -1 ? new ItemStack(Item.itemsList[s5]) : null;
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

			dos.writeInt(storage[0] != null ? storage[0].itemID : -1);
			dos.writeInt(storage[1] != null ? storage[1].itemID : -1);
			dos.writeInt(storage[2] != null ? storage[2].itemID : -1);
			dos.writeInt(storage[3] != null ? storage[3].itemID : -1);
			dos.writeInt(storage[5] != null ? storage[5].itemID : -1);
		} catch (IOException e) {
		}

		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
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
			entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, 
					storage[index]);
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
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
		{
			itemstack.stackSize = getInventoryStackLimit();
		}
		TerraFirmaCraft.proxy.sendCustomPacket(createUpdatePacket());
	}

	@Override
	public String getInvName() {
		// TODO Auto-generated method stub
		return "FoodPrep";
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeChest() 
	{
		if(worldObj.isRemote)
			worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
		else
		{
			if(storage[0] == null && storage[1] == null && storage[2] == null && storage[3] == null && storage[5] == null)
			{
				if(storage[4] != null)
					this.ejectItem(4);
				
				this.worldObj.setBlock(xCoord, yCoord, zCoord, 0);
			}
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
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
