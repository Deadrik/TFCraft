package com.bioxx.tfc.TileEntities;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFCFluid;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Render.Models.ModelLoom;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Crafting.BarrelAlcoholRecipe;
import com.bioxx.tfc.api.Crafting.BarrelLiquidToLiquidRecipe;
import com.bioxx.tfc.api.Crafting.BarrelManager;
import com.bioxx.tfc.api.Crafting.BarrelMultiItemRecipe;
import com.bioxx.tfc.api.Crafting.BarrelRecipe;
import com.bioxx.tfc.api.Crafting.BarrelVinegarRecipe;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IFood;

public class TELoom extends NetworkTileEntity implements IInventory
{
	public byte rotation = 0;
	public int loomType;
	private int stringType;
	public ItemStack[] storage;
	private int stringCount;

	private int numStrings;
	private boolean weaving;
	private boolean finished;

	private ModelLoom model = null;
	
	private int cloth;

	public TELoom()
	{
		storage = new ItemStack[12];
	}

	@Override
	public void closeInventory()
	{
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(storage[i] != null)
		{
			if(storage[i] .stackSize <= j)
			{
				ItemStack is = storage[i];
				storage[i] = null;
				return is;
			}
			ItemStack isSplit = storage[i].splitStack(j);
			if(storage[i].stackSize == 0)
				storage[i] = null;
			return isSplit;
		}
		else
		{
			return null;
		}
	}

	public boolean isFinished(){
		return finished;
	}

	public ItemStack addString(ItemStack i){
		if(!finished && i != null && this.stringCount < 16 && !worldObj.isRemote){
			int type = -1;
			boolean update = false;
			if(i.getItem().equals(TFCItems.WoolYarn) && (this.stringType == 0 || this.stringCount == 0)){
				type = 0;
				update = true;
			}

			if(update){
				this.stringType = type;
				this.stringCount++;
				i.stackSize--;
				updateLoom();
			}
		}
		return i;
	}

	public ItemStack takeFinishedCloth(){
		if(finished){
			this.stringCount = 0;
			this.finished = false;
			this.cloth = 0;
			updateLoom();
			switch(stringType){
			case 0: return new ItemStack(TFCItems.WoolCloth,1,0);
			default:return new ItemStack(TFCItems.WoolCloth,1,0);
			}
		}
		return null;
	}

	public void ejectContents()
	{
		float f3 = 0.05F;
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.3F + 0.1F;
		float f1 = rand.nextFloat() * 2.0F + 0.4F;
		float f2 = rand.nextFloat() * 0.3F + 0.1F;

		for (int i = 0; i < getSizeInventory(); i++)
		{
			if(storage[i] != null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, storage[i]);
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
		return 64;
	}

	@Override
	public String getInventoryName()
	{
		return "Loom";
	}

	public ModelLoom getModel(){
		if(worldObj.isRemote){
			return model;
		}
		return null;
	}
	
	public void setModel(ModelLoom loomModel){
		if(worldObj.isRemote){
			model = loomModel;
			model.cloth = this.cloth;
		}
	}
	
	@Override
	public int getSizeInventory()
	{
		return 12;
	}

	public ResourceLocation getWoodResource(){
		return new ResourceLocation(Reference.ModID, "textures/blocks/wood/WoodSheet/"+Global.WOOD_ALL[loomType]+".png");
	}

	public ResourceLocation getStringResource(){
		switch(this.stringType){
		case 0: return new ResourceLocation("textures/blocks/wool_colored_white.png");
		default: return new ResourceLocation("textures/blocks/wool_colored_white.png");
		}
	}

	public int getStringType(){
		return this.stringType;
	}

	public int getStringCount(){
		return this.stringCount;
	}

	public void setStringType(int type){
		this.stringType = type;
		if(!worldObj.isRemote){
			this.updateLoom();
		}
	}

	public void setStringCount(int count){
		this.stringCount = count;
		if(!worldObj.isRemote){
			this.updateLoom();
		}
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return storage[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		return storage[i];
	}

	public int getInvCount()
	{
		int count = 0;
		for(ItemStack is : storage)
		{
			if(is != null)
				count++;
		}
		if(storage[0] != null && count == 1)
			return 0;
		return count;
	}

	public boolean canWeave(){
		return stringCount == 16 && !finished;
	}

	public void setIsWeaving(boolean isWeaving){
		if(!finished && stringCount == 16){
			this.weaving = isWeaving;
			if(!worldObj.isRemote){
				this.updateLoom();
			}
		}
	}

	public boolean getIsWeaving(){
		return this.weaving;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return false;
	}

	@Override
	public void openInventory()
	{
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack is)
	{
		storage[i] = is;
	}

	public ItemStack getInputStack()
	{
		return storage[0];
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


	public void finishCloth(){
		if(!this.finished){
			NBTTagCompound nbt = new NBTTagCompound();
			this.weaving = false;
			this.finished = true;
			nbt.setBoolean("weaving", this.weaving);
			nbt.setBoolean("finished", this.finished);
			this.broadcastPacketInRange(this.createDataPacket(nbt));
		}
	}

	public void dropItem(){
		if(!worldObj.isRemote){
			ItemStack item = null;
			if(!finished){
				if(stringCount > 0){
					if(stringType == 0){
						item = new ItemStack(TFCItems.WoolYarn,stringCount,0);
					}
				}
			}
			else{
				if(stringType == 0){
					item = new ItemStack(TFCItems.WoolCloth,1,0);
				}
			}
			if(item != null){
				EntityItem ei = new EntityItem(worldObj,xCoord,yCoord,zCoord,item);
				worldObj.spawnEntityInWorld(ei);
			}
		}
	}

	public void finishWeaveCycle(){
		NBTTagCompound nbt = new NBTTagCompound();
		this.weaving = false;
		this.cloth++;
		nbt.setBoolean("weaving", false);
		nbt.setInteger("cloth", this.cloth);
		this.broadcastPacketInRange(this.createDataPacket(nbt));
	}

	public void updateLoom(){
		NBTTagCompound nbt = new NBTTagCompound();
		//nbt.setInteger("stringCount", this.stringCount);
		//nbt.setBoolean("weaving", weaving);
		//nbt.setInteger("stringType", stringType);
		writeToNBT(nbt);
		this.broadcastPacketInRange(this.createDataPacket(nbt));
	}


	public int getCloth(){
		return cloth;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("loomType", loomType);
		nbt.setInteger("stringType", stringType);
		nbt.setInteger("stringCount",stringCount);
		nbt.setByte("rotation", rotation);
		nbt.setBoolean("weaving", weaving);
		nbt.setBoolean("finished", finished);
		nbt.setInteger("cloth", cloth);
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
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		loomType = nbt.getInteger("loomType");
		stringType = nbt.getInteger("stringType");
		stringCount = nbt.getInteger("stringCount");
		weaving = nbt.getBoolean("weaving");
		rotation = nbt.getByte("rotation");
		finished = nbt.getBoolean("finished");
		cloth = nbt.getInteger("cloth");
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

	public void readFromItemNBT(NBTTagCompound nbt)
	{
		loomType = nbt.getInteger("loomType");
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbt1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbt1.getByte("Slot");
			if(byte0 >= 0 && byte0 < 2)
				setInventorySlotContents(byte0,ItemStack.loadItemStackFromNBT(nbt1));
		}
	}

	public void updateGui()
	{
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		//validate();
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) 
	{
		this.rotation = nbt.getByte("rotation");
		this.loomType = nbt.getInteger("loomType");
		this.stringType = nbt.getInteger("stringType");
		this.stringCount = nbt.getInteger("stringCount");
		this.finished = nbt.getBoolean("finished");
		this.cloth = nbt.getInteger("cloth");
		this.worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}


	@Override
	public void handleDataPacket(NBTTagCompound nbt)
	{
		if(worldObj.isRemote){
			if(nbt.hasKey("weaving")){
				this.setIsWeaving(nbt.getBoolean("weaving"));
			}
			if(nbt.hasKey("stringCount")){
				this.stringCount = nbt.getInteger("stringCount");
			}
			if(nbt.hasKey("stringType")){
				this.stringType = nbt.getInteger("stringType");
			}
			if(nbt.hasKey("loomType")){
				this.loomType = nbt.getInteger("loomType");
			}
			if(nbt.hasKey("finished")){
				this.finished = nbt.getBoolean("finished");
			}
			if(nbt.hasKey("cloth")){
				this.cloth = nbt.getInteger("cloth");
			}
		}
		else{
			if(nbt.hasKey("weaving")){
				this.setIsWeaving(nbt.getBoolean("weaving"));
			}
			if(nbt.hasKey("finished")){
				this.finished = nbt.getBoolean("finished");
			}
			if(nbt.hasKey("cloth")){
				this.cloth = nbt.getInteger("cloth");
			}
		}
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) 
	{
		nbt.setByte("rotation", rotation);
		nbt.setInteger("loomType", loomType);
		nbt.setInteger("stringType", stringType);
		nbt.setInteger("stringCount", stringCount);
		nbt.setBoolean("finished",finished);
		nbt.setInteger("cloth", cloth);
	}
}
