package TFC.TileEntities;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import TFC.TFCItems;
import TFC.API.IFood;
import TFC.API.IItemFoodBlock;
import TFC.API.Constant.Global;
import TFC.API.Util.Helper;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Food.ItemFoodTFC;

public class TileEntityFoodPrep extends TileEntity implements IInventory
{
	public ItemStack[] storage = new ItemStack[6];
	private float[] weights = new float[]{10,4,4,2};

	@Override
	public void updateEntity()
	{
		TFC_Core.handleItemTicking(this, worldObj, xCoord, yCoord, zCoord);
	}
	
	public int getFoodIdFromItemStack(ItemStack is)
	{
		if(is != null)
		{
			if(is.getItem() instanceof IFood)
				return ((IFood)is.getItem()).getFoodID();
			else if(is.getItem() instanceof IItemFoodBlock)
				return ((IItemFoodBlock)is.getItem()).getFoodId(is);
		}
		return 1;
	}
	
	public int getHealAmountFromItemStack(ItemStack is)
	{
		if(is != null)
		{
			if(is.getItem() instanceof IFood)
				return ((IFood)is.getItem()).getFoodID();
			else if(is.getItem() instanceof IItemFoodBlock)
				return ((IItemFoodBlock)is.getItem()).getHealAmount(is);
		}
		return 1;
	}

	public void actionCreate(EntityPlayer player)
	{
		if(!worldObj.isRemote)
		{
			if(storage[4] == null && storage[5].getItem() == Items.bowl)
			{
				if(getMealWeight() >= 14)
				{
					NBTTagCompound nbt = new NBTTagCompound();
					ItemStack is = new ItemStack(TFCItems.MealGeneric, 1);
					Random R = new Random(getFoodSeed());
					Random Ri = new Random(getIconSeed());

					int count = -2;
					if(getStackInSlot(0) != null) 
					{
						count++;
						nbt.setString("FG0", getStackInSlot(0).getItem().getUnlocalizedName(getStackInSlot(0))+":"+((ItemFoodTFC)getStackInSlot(0).getItem()).getFoodGroup().ordinal());
					}
					if(getStackInSlot(1) != null) 
					{
						count++;
						nbt.setString("FG1", getStackInSlot(1).getItem().getUnlocalizedName(getStackInSlot(1))+":"+((ItemFoodTFC)getStackInSlot(1).getItem()).getFoodGroup().ordinal());
					}
					if(getStackInSlot(2) != null) 
					{
						count++;
						nbt.setString("FG2", getStackInSlot(2).getItem().getUnlocalizedName(getStackInSlot(2))+":"+((ItemFoodTFC)getStackInSlot(2).getItem()).getFoodGroup().ordinal());
					}
					if(getStackInSlot(3) != null) 
					{
						count++;
						nbt.setString("FG3", getStackInSlot(3).getItem().getUnlocalizedName(getStackInSlot(3))+":"+((ItemFoodTFC)getStackInSlot(3).getItem()).getFoodGroup().ordinal());
					}

					float mult = 0.15f + 0.12f * count;

					//set the icon for this meal
					is.setItemDamage(Ri.nextInt(11));
					if(R.nextFloat() < mult)
					{
						float s = R.nextFloat()*0.25f+(TFC_Core.getSkillStats(player).getSkillMultiplier(Global.SKILL_COOKING)*0.5f);
						nbt.setFloat("satisfaction", s);
					}
					nbt.setFloat("foodWeight", Helper.roundNumber(getMealWeight(), 10));
					nbt.setFloat("foodDecay", -24);
					nbt.setFloat("decayRate", 2.0f);
					nbt.setInteger("decayTimer", (int)TFC_Time.getTotalHours()+1);

					is.setTagCompound(nbt);

					this.setInventorySlotContents(4, is);
					this.getStackInSlot(5).stackSize--;

					consumeFoodWeight();
				}
			}
			else
			{
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				//TODO TerraFirmaCraft.proxy.sendCustomPacket(createMealPacket());
			}
		}
	}

	public boolean areComponentsCorrect()
	{
		int f0 = -1;
		int f1 = -1;
		int f2 = -1;
		int f3 = -1;

		//First we want to test the foodgroups to see if they match
		if(getStackInSlot(0) != null)
			f0 = ((ItemFoodTFC)getStackInSlot(0).getItem()).getFoodGroup().ordinal();
		if(getStackInSlot(1) != null)
			f1 = ((ItemFoodTFC)getStackInSlot(1).getItem()).getFoodGroup().ordinal();
		if(getStackInSlot(2) != null)
			f2 = ((ItemFoodTFC)getStackInSlot(2).getItem()).getFoodGroup().ordinal();
		if(getStackInSlot(3) != null)
			f3 = ((ItemFoodTFC)getStackInSlot(3).getItem()).getFoodGroup().ordinal();

		if(f0 == -1 || f0==f1 || f0==f2 || f0==f3)
			return false;
		else if(f1 == -1 || f1==f3)
			return false;
		else if(f2 != -1 && f2==f3)
			return false;

		//Now we test the food types to make sure that they are different
		if(getStackInSlot(0) != null)
			f0 = ((ItemFoodTFC)getStackInSlot(0).getItem()).getFoodID();
		if(getStackInSlot(1) != null)
			f1 = ((ItemFoodTFC)getStackInSlot(1).getItem()).getFoodID();
		if(getStackInSlot(2) != null)
			f2 = ((ItemFoodTFC)getStackInSlot(2).getItem()).getFoodID();
		if(getStackInSlot(3) != null)
			f3 = ((ItemFoodTFC)getStackInSlot(3).getItem()).getFoodID();

		if(f0 == -1 || f0==f1 || f0==f2 || f0==f3)
			return false;
		else if(f1 == -1 || f1==f2 || f1==f3)
			return false;
		else if(f2 != -1 && f2==f3)
			return false;

		//Next we make sure that each slot has enough food material
		if(getStackInSlot(0) == null || ((ItemFoodTFC)getStackInSlot(0).getItem()).getFoodWeight(getStackInSlot(0)) < 10)
			return false;
		if(getStackInSlot(1) == null || ((ItemFoodTFC)getStackInSlot(1).getItem()).getFoodWeight(getStackInSlot(1)) < 4)
			return false;
		if(getStackInSlot(2) != null && ((ItemFoodTFC)getStackInSlot(2).getItem()).getFoodWeight(getStackInSlot(2)) < 4)
			return false;
		if(getStackInSlot(3) != null && ((ItemFoodTFC)getStackInSlot(3).getItem()).getFoodWeight(getStackInSlot(3)) < 2)
			return false;
		if(storage[4] != null || (storage[5] == null || storage[5].getItem() != Items.bowl))
			return false;
		return true;
	}

	private long getFoodSeed()
	{
		int seed = 0;
		for(int i = 0; i < 4; i++)
		{
			ItemStack is = getStackInSlot(i);
			if(is != null)
				seed += ((ItemFoodTFC)is.getItem()).getFoodID();
		}
		return seed + worldObj.getSeed();
	}

	private long getIconSeed()
	{
		int seed = 0;
		for(int i = 0; i < 2; i++)
		{
			ItemStack is = getStackInSlot(i);
			if(is != null)
				seed += ((ItemFoodTFC)is.getItem()).getFoodID();
		}
		return seed + worldObj.getSeed();
	}

	public float getMealWeight()
	{
		float w = 0;
		for(int i = 0; i < 4; i++)
		{
			ItemStack is = getStackInSlot(i);
			if(is != null && ((ItemFoodTFC)is.getItem()).getFoodWeight(is) >= weights[i])
				w += weights[i];
		}
		return w;
	}

	public float consumeFoodWeight()
	{
		float w = 0;
		for(int i = 0; i < 4; i++)
		{
			ItemStack is = getStackInSlot(i);
			if(is != null)
			{
				is.getTagCompound().setFloat("foodWeight", ((ItemFoodTFC)is.getItem()).getFoodWeight(is) - weights[i]);
				if(((ItemFoodTFC)is.getItem()).getFoodWeight(is) <= 0)
					is.stackSize = 0;
			}
		}
		return w;
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
			if(storage[i]!= null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, storage[i]);
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
			entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, storage[index]);
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
			itemstack.stackSize = getInventoryStackLimit();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public String getInventoryName()
	{
		return "FoodPrep";
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1)
	{
		return false;
	}

	@Override
	public void openInventory()
	{
	}

	@Override
	public void closeInventory()
	{
		if(worldObj.isRemote)
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		else
		{
			if(storage[0] == null && storage[1] == null && storage[2] == null && storage[3] == null && storage[5] == null)
			{
				if(storage[4] != null)
					this.ejectItem(4);
				this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			}
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		return null;
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
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
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.func_148857_g());
	}






//TODO
//	public Packet createUpdatePacket()
//	{
//		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
//		DataOutputStream dos=new DataOutputStream(bos);
//
//		try {
//			dos.writeByte(PacketHandler.Packet_Data_Block_Client);
//			dos.writeInt(xCoord);
//			dos.writeInt(yCoord);
//			dos.writeInt(zCoord);
//
//			dos.writeInt(storage[0] != null ? Item.getIdFromItem(storage[0].getItem()) : -1);
//			dos.writeInt(storage[1] != null ? Item.getIdFromItem(storage[1].getItem()) : -1);
//			dos.writeInt(storage[2] != null ? Item.getIdFromItem(storage[2].getItem()) : -1);
//			dos.writeInt(storage[3] != null ? Item.getIdFromItem(storage[3].getItem()) : -1);
//			dos.writeInt(storage[5] != null ? Item.getIdFromItem(storage[5].getItem()) : -1);
//		} catch (IOException e) {
//		}
//
//		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
//	}
//
//	public Packet createMealPacket()
//	{
//		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
//		DataOutputStream dos=new DataOutputStream(bos);
//
//		try {
//			dos.writeByte(PacketHandler.Packet_Data_Block_Server);
//			dos.writeInt(xCoord);
//			dos.writeInt(yCoord);
//			dos.writeInt(zCoord);
//			dos.writeUTF(Minecraft.getMinecraft().thePlayer.username);
//		} catch (IOException e) {
//		}
//
//		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
//	}
//	@Override
//	public void handleDataPacket(DataInputStream inStream) throws IOException 
//	{
//		String user = inStream.readUTF();
//		EntityPlayer player = worldObj.getPlayerEntityByName(user);
//		TFC_Core.getSkillStats(player).increaseSkill(Global.SKILL_COOKING, 1);
//		actionCreate(player);
//	}
//
//	@Override
//	public void handleDataPacketServer(DataInputStream inStream) throws IOException 
//	{
//		actionCreate();
//	}
//
//	@Override
//	public void createInitPacket(DataOutputStream outStream) throws IOException 
//	{
//		outStream.writeInt(storage[0] != null ? Item.getIdFromItem(storage[0].getItem()) : -1);
//		outStream.writeInt(storage[1] != null ? Item.getIdFromItem(storage[1].getItem()) : -1);
//		outStream.writeInt(storage[2] != null ? Item.getIdFromItem(storage[2].getItem()) : -1);
//		outStream.writeInt(storage[3] != null ? Item.getIdFromItem(storage[3].getItem()) : -1);
//		outStream.writeInt(storage[5] != null ? Item.getIdFromItem(storage[5].getItem()) : -1);
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void handleInitPacket(DataInputStream inStream) throws IOException {
//		int s1 = inStream.readInt();
//		int s2 = inStream.readInt();
//		int s3 = inStream.readInt();
//		int s4 = inStream.readInt();
//		int s5 = inStream.readInt();
//		storage[0] = s1 != -1 ? new ItemStack(Item.getItemById(s1)) : null;
//		storage[1] = s2 != -1 ? new ItemStack(Item.getItemById(s2)) : null;
//		storage[2] = s3 != -1 ? new ItemStack(Item.getItemById(s3)) : null;
//		storage[3] = s4 != -1 ? new ItemStack(Item.getItemById(s4)) : null;
//		storage[5] = s5 != -1 ? new ItemStack(Item.getItemById(s5)) : null;
//		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
//
//	}
}
