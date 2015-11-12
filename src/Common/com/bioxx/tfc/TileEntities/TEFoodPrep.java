package com.bioxx.tfc.TileEntities;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;
import com.bioxx.tfc.Handlers.Network.CreateMealPacket;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.Interfaces.IItemFoodBlock;

public class TEFoodPrep extends NetworkTileEntity implements IInventory
{
	public ItemStack[] storage = new ItemStack[11];
	public int lastTab;
	private final float[] sandwichWeights = new float[]{2,3,2,2,1};
	private final float[] saladWeights = new float[]{10,4,4,2};

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

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
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
			if(lastTab == 0)
				createSandwich(player);
			else if(lastTab == 1)
				createSalad(player);
		}
		else
		{
			// Send a network packet to call this method on the Server side
			// and create a meal, also adds 1 to the players cooking skill.
			AbstractPacket pkt = new CreateMealPacket(0, this);
			broadcastPacketInRange(pkt);
		}
	}

	private void createSandwich(EntityPlayer player) 
	{
		if(validateSandwich())//Bread
		{
			ItemStack is = new ItemStack(TFCItems.sandwich, 1);

			float w = 0;
			for (int i = 0; i < 5; i++)
			{
				ItemStack f = getStackInSlot(i);
				if (f != null && Food.getWeight(f) >= sandwichWeights[i])
					w += sandwichWeights[i];
			}

			ItemFoodTFC.createTag(is, w);
			Food.setDecayRate(is, 2.0F);

			int[] foodGroups = new int[]{-1,-1,-1,-1,-1};
			if(getStackInSlot(0) != null) foodGroups[0] = ((IFood)(getStackInSlot(0).getItem())).getFoodID();
			if(getStackInSlot(1) != null) foodGroups[1] = ((IFood)(getStackInSlot(1).getItem())).getFoodID();
			if(getStackInSlot(2) != null) foodGroups[2] = ((IFood)(getStackInSlot(2).getItem())).getFoodID();
			if(getStackInSlot(3) != null) foodGroups[3] = ((IFood)(getStackInSlot(3).getItem())).getFoodID();
			if(getStackInSlot(4) != null) foodGroups[4] = ((IFood)(getStackInSlot(4).getItem())).getFoodID();

			Food.setFoodGroups(is, foodGroups);
			setSandwichIcon(is);

			combineTastes(is.getTagCompound(), sandwichWeights, getStackInSlot(0), getStackInSlot(1), getStackInSlot(2), getStackInSlot(3), getStackInSlot(4));

			Food.setMealSkill(is, TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_COOKING).ordinal());
			this.setInventorySlotContents(6, is);

			consumeFoodWeight(sandwichWeights, getStackInSlot(0), getStackInSlot(1), getStackInSlot(2), 
					getStackInSlot(3), getStackInSlot(4));
		}
	}

	private void setSandwichIcon(ItemStack is)
	{
		if(getStackInSlot(0).getItem() == TFCItems.wheatBread)
			is.setItemDamage(0);
		else if(getStackInSlot(0).getItem() == TFCItems.oatBread)
			is.setItemDamage(1);
		else if(getStackInSlot(0).getItem() == TFCItems.barleyBread)
			is.setItemDamage(2);
		else if(getStackInSlot(0).getItem() == TFCItems.ryeBread)
			is.setItemDamage(3);
		else if(getStackInSlot(0).getItem() == TFCItems.cornBread)
			is.setItemDamage(4);
		else if(getStackInSlot(0).getItem() == TFCItems.riceBread)
			is.setItemDamage(5);
	}

	private void createSalad(EntityPlayer player) 
	{
		if(validateSalad())//Bread
		{
			ItemStack is = new ItemStack(TFCItems.salad, 1);

			float w = 0;
			for(int i = 0; i < 4; i++)
			{
				ItemStack f = getStackInSlot(i+1);
				if (f != null && Food.getWeight(f) >= saladWeights[i])
					w += saladWeights[i];
			}
			
			ItemFoodTFC.createTag(is, w);
			Food.setDecayRate(is, 2.0F);

			int[] foodGroups = new int[]{-1,-1,-1,-1};
			if(getStackInSlot(1) != null) foodGroups[0] = ((IFood)(getStackInSlot(1).getItem())).getFoodID();
			if(getStackInSlot(2) != null) foodGroups[1] = ((IFood)(getStackInSlot(2).getItem())).getFoodID();
			if(getStackInSlot(3) != null) foodGroups[2] = ((IFood)(getStackInSlot(3).getItem())).getFoodID();
			if(getStackInSlot(4) != null) foodGroups[3] = ((IFood)(getStackInSlot(4).getItem())).getFoodID();

			Food.setFoodGroups(is, foodGroups);

			is.setItemDamage(new Random(getIconSeed()).nextInt(((ItemTerra)TFCItems.salad).metaIcons.length));
			
			combineTastes(is.getTagCompound(), saladWeights, getStackInSlot(1), getStackInSlot(2), getStackInSlot(3), getStackInSlot(4));

			Food.setMealSkill(is, TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_COOKING).ordinal());
			this.setInventorySlotContents(6, is);

			consumeFoodWeight(saladWeights, getStackInSlot(1), getStackInSlot(2), getStackInSlot(3), getStackInSlot(4));

			TFC_Core.getItemInInventory(TFCItems.potteryBowl, this).stackSize--;
		}
	}

	public boolean validateSandwich()
	{
		if(lastTab == 0)
		{
			if(storage[0] == null || storage[6] != null)//Bread
				return false;
			/*int count = 0;
			if(storage[1] != null) count++;
			if(storage[2] != null) count++;
			if(storage[3] != null) count++;
			if(storage[4] != null) count++;*/

			if(!validateIngreds(storage[1],storage[2],storage[3],storage[4]))
				return false;

			float weight = 0;
			for(int i = 0; i < 5; i++)
			{
				ItemStack f = getStackInSlot(i);
				if (f != null && f.getItem() instanceof IFood && Food.getWeight(f) - Food.getDecay(f) >= sandwichWeights[i])
				{
					weight += sandwichWeights[i];
				}
			}

			if(weight < 7)
				return false;
		}
		return true;
	}

	public boolean validateSalad()
	{
		if(lastTab == 1)
		{
			if(storage[6] != null)//Bread
				return false;

			/*int count = 0;
			if(storage[1] != null) {count++;}
			if(storage[2] != null) {count++;}
			if(storage[3] != null) {count++;}
			if(storage[4] != null) {count++;}*/


			if(!validateIngreds(storage[1],storage[2],storage[3],storage[4]))
				return false;

			float weight = 0;
			for(int i = 0; i < 4; i++)
			{
				ItemStack f = getStackInSlot(i+1);
				if (f != null && Food.getWeight(f) - Food.getDecay(f) >= saladWeights[i])
				{
					weight += saladWeights[i];
				}
			}

			if(weight < 14)
				return false;

			ItemStack bowlStack = TFC_Core.getItemInInventory(TFCItems.potteryBowl, this);
			if(bowlStack == null || bowlStack.getItemDamage() != 1)
			{
				return false;
			}
		}
		return true;
	}

	public boolean validateIngreds(ItemStack... is)
	{
		for(int i = 0; i < is.length; i++)
		{
			if(is[i] != null && !((IFood)is[i].getItem()).isUsable(is[i]))
				return false;
			for(int j = 0; j < is.length; j++)
			{

				if(j != i && !compareIngred(is[i], is[j]))
					return false;
			}
		}
		return true;
	}

	private boolean compareIngred(ItemStack is1, ItemStack is2)
	{
		return !(is1 != null && is2 != null && is1.getItem() == is2.getItem());
	}

	private void combineTastes(NBTTagCompound nbt, float[] weights, ItemStack... isArray)
	{
		int tasteSweet = 0;
		int tasteSour = 0;
		int tasteSalty = 0;
		int tasteBitter = 0;
		int tasteUmami = 0;

		for (int i = 0; i < isArray.length; i++)
		{
			float weightMult = 1f;//weights[i] / totalW * 2;
			if(isArray[i] != null)
			{
				tasteSweet += ((IFood)isArray[i].getItem()).getTasteSweet(isArray[i]) * weightMult;
				tasteSour += ((IFood)isArray[i].getItem()).getTasteSour(isArray[i]) * weightMult;
				tasteSalty += ((IFood)isArray[i].getItem()).getTasteSalty(isArray[i]) * weightMult;
				tasteBitter += ((IFood)isArray[i].getItem()).getTasteBitter(isArray[i]) * weightMult;
				tasteUmami += ((IFood)isArray[i].getItem()).getTasteSavory(isArray[i]) * weightMult;
			}
		}
		nbt.setInteger("tasteSweet", tasteSweet);
		nbt.setInteger("tasteSour", tasteSour);
		nbt.setInteger("tasteSalty", tasteSalty);
		nbt.setInteger("tasteBitter", tasteBitter);
		nbt.setInteger("tasteUmami", tasteUmami);
	}

	public void openGui(EntityPlayer player)
	{
		if(lastTab == 0)
			player.openGui(TerraFirmaCraft.instance, 44, worldObj, xCoord, yCoord, zCoord);
		else if (lastTab == 1)
			player.openGui(TerraFirmaCraft.instance, 45, worldObj, xCoord, yCoord, zCoord);
	}

	/*private long getFoodSeed()
	{
		int seed = 1;

		for(int i = 0; i < 4; i++)
		{
			ItemStack is = getStackInSlot(i);
			if(is != null)
				seed *= ((ItemFoodTFC) is.getItem()).getFoodID();
		}
		return seed + worldObj.getSeed();
	}*/

	private long getIconSeed()
	{
		int seed = 0;
		for(int i = 1; i < 5; i++)
		{
			ItemStack is = getStackInSlot(i);
			if(is != null)
				seed += ((ItemFoodTFC)is.getItem()).getFoodID();
		}
		return seed + worldObj.getSeed();
	}

	public void consumeFoodWeight(float[] weights, ItemStack... isArray)
	{
		for(int i = 0; i < isArray.length; i++)
		{
			ItemStack is = isArray[i];
			if(is != null)
			{
				float oldW = Food.getWeight(is);
				Food.setWeight(is, oldW - weights[i]);
				float newW = Food.getWeight(is);
				if (newW <= 0 || newW <= Food.getDecay(is))
					is.stackSize = 0;
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
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
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
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
		nbt.setTag("Items", nbttaglist);
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
		if(!TFC_Core.areItemsEqual(storage[i], itemstack))
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		storage[i] = itemstack;
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
		{
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		/*else if(storage[0] == null && storage[1] == null && storage[2] == null && storage[3] == null && storage[4] == null && storage[5] == null 
				&& storage[6] == null && storage[7] == null && storage[8] == null && storage[9] == null && storage[10] == null)
		{
			this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}*/
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

	public void actionSwitchTab(int tab, EntityPlayer player)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("tab", (byte)tab);
		nbt.setString("player", player.getCommandSenderName());
		this.broadcastPacketInRange(this.createDataPacket(nbt));
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) {
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
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
	public void createInitNBT(NBTTagCompound nbt) {
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
		nbt.setTag("Items", nbttaglist);
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt)
	{
		if(!worldObj.isRemote)
		{
			if(nbt.hasKey("tab"))
			{
				int tab = nbt.getByte("tab");
				EntityPlayer player = worldObj.getPlayerEntityByName(nbt.getString("player"));
				if(player != null && tab == 0)
					player.openGui(TerraFirmaCraft.instance, 44, worldObj, xCoord, yCoord, zCoord);
				else if(player != null && tab == 1)
					player.openGui(TerraFirmaCraft.instance, 45, worldObj, xCoord, yCoord, zCoord);
			}
		}
	}

}
