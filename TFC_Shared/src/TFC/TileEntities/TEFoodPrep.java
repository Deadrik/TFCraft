package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import net.minecraft.client.Minecraft;
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
import TFC.API.IFood;
import TFC.API.IItemFoodBlock;
import TFC.API.Constant.Global;
import TFC.API.Util.Helper;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Food.ItemFoodTFC;
import TFC.Handlers.PacketHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TEFoodPrep extends NetworkTileEntity implements IInventory
{
	public ItemStack[] storage = new ItemStack[6];
	private float[] weights = new float[]{10,4,4,2};
	@Override
	public void updateEntity()
	{
		TFC_Core.handleItemTicking(this, worldObj, xCoord, yCoord, zCoord);
	}

	public int getFoodIdFromItemStack(ItemStack is){
		if(is != null)
			if(is.getItem() instanceof IFood)
				return ((IFood)is.getItem()).getFoodID();
			else if(is.getItem() instanceof IItemFoodBlock)
				return ((IItemFoodBlock)is.getItem()).getFoodId(is);
		return 1;
	}

	public int getHealAmountFromItemStack(ItemStack is){
		if(is != null)
			if(is.getItem() instanceof IFood)
				return ((IFood)is.getItem()).getFoodID();
			else if(is.getItem() instanceof IItemFoodBlock)
				return ((IItemFoodBlock)is.getItem()).getHealAmount(is);
		return 1;
	}

	public void actionCreate(EntityPlayer player)
	{
		if(!worldObj.isRemote)
		{
			if(storage[4] == null && storage[5].getItem().itemID == Item.bowlEmpty.itemID)
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
					if(getStackInSlot(5).stackSize == 0)
						setInventorySlotContents(5, null);

					consumeFoodWeight();
				}
		} else
			TerraFirmaCraft.proxy.sendCustomPacket(createMealPacket());
	}

	public boolean areComponentsCorrect()
	{
		int f0 = -1;
		int f1 = -2;
		int f2 = -2;
		int f3 = -3;
		Item[] food = new Item[]
				{
				(storage[0] != null ? storage[0].getItem() : null),
				(storage[1] != null ? storage[1].getItem() : null),
				(storage[2] != null ? storage[2].getItem() : null),
				(storage[3] != null ? storage[3].getItem() : null)
				};

		//First we want to test the foodgroups to see if they match
		if(getStackInSlot(0) != null)
			f0 = ((ItemFoodTFC)getStackInSlot(0).getItem()).getFoodGroup().ordinal();
		if(getStackInSlot(1) != null)
			f1 = ((ItemFoodTFC)getStackInSlot(1).getItem()).getFoodGroup().ordinal();
		if(getStackInSlot(2) != null)
			f2 = ((ItemFoodTFC)getStackInSlot(2).getItem()).getFoodGroup().ordinal();
		if(getStackInSlot(3) != null)
			f3 = ((ItemFoodTFC)getStackInSlot(3).getItem()).getFoodGroup().ordinal();

		if(f0==f1 || f0==f2 || f0==f3)//food0 can't be the same foodgroup as the other food types. If it is then we return false
			return false;
		else if(f1==f3)//food1 can be the same as food2 so we dont compare those. It can't be the same as food3 however.
			return false;
		else if(f2==f3)//food2 can be the same as food1 so we dont compare those. It can't be the same as food3 however.
			return false;

		f0 = -1;
		f1 = -1;
		f2 = -1;
		f3 = -1;

		//Now we test the food types to make sure that they are different
		if(getStackInSlot(0) != null && ((IFood)storage[0].getItem()).isUsable())
			f0 = ((IFood)getStackInSlot(0).getItem()).getFoodID();
		if(getStackInSlot(1) != null && ((IFood)storage[1].getItem()).isUsable())
			f1 = ((IFood)getStackInSlot(1).getItem()).getFoodID();
		if(getStackInSlot(2) != null && ((IFood)storage[2].getItem()).isUsable())
			f2 = ((IFood)getStackInSlot(2).getItem()).getFoodID();
		if(getStackInSlot(3) != null && ((IFood)storage[3].getItem()).isUsable())
			f3 = ((IFood)getStackInSlot(3).getItem()).getFoodID();

		//Now we check the usability of the specific item
		if(getStackInSlot(0) != null && (f0==f1 || f0==f2 || f0==f3))
			return false;
		else if(getStackInSlot(1) != null && (f1==f2 || f1==f3))
			return false;
		else if(getStackInSlot(2) != null && f2==f3)
			return false;
		else if(getStackInSlot(3) != null && f3 == -1)
			return false;

		//Next we make sure that each slot has enough food material
		if(getStackInSlot(0) != null && ((IFood)getStackInSlot(0).getItem()).getFoodWeight(getStackInSlot(0)) < 10)
			return false;
		if(getStackInSlot(1) != null && ((IFood)getStackInSlot(1).getItem()).getFoodWeight(getStackInSlot(1)) < 4)
			return false;
		if(getStackInSlot(2) != null && ((IFood)getStackInSlot(2).getItem()).getFoodWeight(getStackInSlot(2)) < 4)
			return false;
		if(getStackInSlot(3) != null && ((IFood)getStackInSlot(3).getItem()).getFoodWeight(getStackInSlot(3)) < 2)
			return false;

		if(storage[4] != null || (storage[5] == null || storage[5].getItem().itemID != Item.bowlEmpty.itemID))
			return false;
		return true;
	}

	private long getFoodSeed()
	{
		int seed = 1;

		for(int i = 0; i < 4; i++)
		{
			ItemStack is = getStackInSlot(i);
			if(is != null)
				seed *= ((ItemFoodTFC) is.getItem()).getFoodID();
		}

		return seed + worldObj.getSeed();
	}

	private long getIconSeed()
	{
		int seed = 0;

		for(int i = 0; i < 3; i++)
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
			if(is != null && ((IFood)is.getItem()).getFoodWeight(is) >= weights[i])
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

	public Packet createMealPacket()
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);

		try {
			dos.writeByte(PacketHandler.Packet_Data_Block_Server);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeUTF(Minecraft.getMinecraft().thePlayer.username);
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
				storage[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < storage.length; i++)
			if(storage[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				storage[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
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
		String user = inStream.readUTF();
		EntityPlayer player = worldObj.getPlayerEntityByName(user);
		TFC_Core.getSkillStats(player).increaseSkill(Global.SKILL_COOKING, 1);
		actionCreate(player);
	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException 
	{
		writeItemToStream(outStream, 0);
		writeItemToStream(outStream, 1);
		writeItemToStream(outStream, 2);
		writeItemToStream(outStream, 3);
		outStream.writeInt(storage[5] != null ? storage[5].itemID : -1);
	}

	public void writeItemToStream(DataOutputStream outStream, int index) throws IOException 
	{
		if(storage[index] != null)
		{
			outStream.writeInt(storage[index].itemID);
			if(storage[index].hasTagCompound())
			{
				NBTTagCompound nbt = storage[index].getTagCompound();
				outStream.writeFloat(nbt.hasKey("foodWeight") ? nbt.getFloat("foodWeight") : -1);
				outStream.writeFloat(nbt.hasKey("foodDecay") ? nbt.getFloat("foodDecay") : -1);
			}
			else
			{
				outStream.writeFloat(-1);
				outStream.writeFloat(-1);
			}
		}
		else
		{
			outStream.writeInt(-1);
			outStream.writeFloat(-1);
			outStream.writeFloat(-1);
		}
	}

	public void readItemFromStream(DataInputStream inStream, int index) throws IOException 
	{
		int id = inStream.readInt();
		float weight = inStream.readFloat();
		float decay = inStream.readFloat();
		if(id != -1 && storage[index] == null || storage[index].getItem().itemID != id)
		{
			storage[index] = new ItemStack(Item.itemsList[id]);

			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setFloat("foodWeight", weight);
			nbt.setFloat("foodDecay", decay);
		}
		else if(id != -1 && storage[index] != null && storage[index].getItem().itemID == id)
		{
			storage[index].getTagCompound().setFloat("foodWeight", weight);
			storage[index].getTagCompound().setFloat("foodDecay", decay);
		}
		else if(id == -1)
		{
			storage[index] = null;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleInitPacket(DataInputStream inStream) throws IOException {
		readItemFromStream(inStream, 0);
		readItemFromStream(inStream, 1);
		readItemFromStream(inStream, 2);
		readItemFromStream(inStream, 3);
		int s5 = inStream.readInt();
		if(storage[5] == null)
		{storage[5] = s5 != -1 ? new ItemStack(Item.itemsList[s5]) : null;}
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

			writeItemToStream(dos, 0);
			writeItemToStream(dos, 1);
			writeItemToStream(dos, 2);
			writeItemToStream(dos, 3);
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
				storage[i] = null;
			return itemstack1;
		} else
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
			itemstack.stackSize = getInventoryStackLimit();
		if (!worldObj.isRemote)
		{
			TerraFirmaCraft.proxy.sendCustomPacket(createUpdatePacket());
		}
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
		else if(storage[0] == null && storage[1] == null && storage[2] == null && storage[3] == null && storage[5] == null)
		{
			if(storage[4] != null)
				this.ejectItem(4);

			this.worldObj.setBlock(xCoord, yCoord, zCoord, 0);
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
