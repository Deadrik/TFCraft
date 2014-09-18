package com.bioxx.tfc.TileEntities;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
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
import com.bioxx.tfc.api.Crafting.LoomManager;
import com.bioxx.tfc.api.Crafting.LoomRecipe;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IFood;

public class TELoom extends NetworkTileEntity implements IInventory
{
	public byte rotation = 0;
	public int loomType;
	public ItemStack[] storage;

	private int numStrings;
	private boolean weaving;
	private boolean finished;

	private ModelLoom model = null;

	private int clothCompletionCount;

	public LoomRecipe recipe;
	private ResourceLocation defaultTexture = new ResourceLocation(Reference.ModID, "textures/blocks/String.png");

	public TELoom()
	{
		storage = new ItemStack[2];
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
		if(!isFinished() &&  i != null && !this.worldObj.isRemote){
			recipe =LoomManager.getInstance().findPotentialRecipes(this.getStackInSlot(0));
			if(this.getStackInSlot(0) != null){
				if(LoomManager.getInstance().findPotentialRecipes(i).equals(LoomManager.getInstance().findPotentialRecipes(this.storage[0]))){
					if(this.getStringCount() < recipe.getReqSize()){
						i.stackSize--;
						this.storage[0].stackSize++;
						updateLoom();
					}
				}
			}
			else if(LoomManager.getInstance().hasPotentialRecipes(i)){
				i.stackSize--;
				ItemStack is = i.copy();
				is.stackSize = 1;
				this.setInventorySlotContents(0, is);
			}
		}
		return i;
	}

	public ItemStack takeFinishedCloth(){
		recipe = LoomManager.getInstance().findMatchingRecipe(getStackInSlot(0));
		if(finished && recipe != null){
			this.setString(null);
			this.finished = false;
			this.clothCompletionCount = 0;
			updateLoom();
			ItemStack is = storage[1].copy();
			storage[1] = null;
			return is;
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
		return this.getRequiredStringCount();
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
			model.cloth = this.clothCompletionCount;
		}
	}

	@Override
	public int getSizeInventory()
	{
		return 2;
	}

	public ResourceLocation getWoodResource(){
		return new ResourceLocation(Reference.ModID, "textures/blocks/wood/WoodSheet/"+Global.WOOD_ALL[loomType]+".png");
	}

	public ResourceLocation getStringResource(){
		recipe = LoomManager.getInstance().findPotentialRecipes(getStackInSlot(0));
		ResourceLocation rl = LoomManager.getInstance().findMatchingTexture(recipe);
		return (recipe != null && rl != null)?rl:this.defaultTexture;
	}

	public Item getStringType(){
		return this.storage[0] != null? this.storage[0].getItem(): null ;
	}

	public int getStringCount(){
		return this.storage[0] != null? this.storage[0].stackSize: 0;
	}

	public void setString(ItemStack is){
		this.storage[0] = is;
		if(!worldObj.isRemote){
			this.updateLoom();
		}
	}

	public void setStringCount(int count){
		if(this.storage[0] != null)this.storage[0].stackSize = count;
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
		recipe = LoomManager.getInstance().findMatchingRecipe(storage[0]);
		return recipe != null && !finished;
	}

	public void setIsWeaving(boolean isWeaving){
		if(canWeave()){
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

	public int getRequiredStringCount(){
		if(storage[0] != null){
			recipe = LoomManager.getInstance().findPotentialRecipes(storage[0]);
			if(recipe != null){
				return recipe.getReqSize();
			}
		}
		return 16;
	}

	public void finishCloth(){
		if(!this.finished){
			NBTTagCompound nbt = new NBTTagCompound();
			this.weaving = false;
			this.finished = true;
			//nbt.setBoolean("weaving", this.weaving);
			//nbt.setBoolean("finished", this.finished);
			recipe = LoomManager.getInstance().findMatchingRecipe(storage[0]);
			this.storage[1] = recipe.getResult(storage[0]);
			this.writeToNBT(nbt);
			this.broadcastPacketInRange(this.createDataPacket(nbt));
		}
	}

	public void dropItem(){
		if(!worldObj.isRemote){
			this.ejectContents();
		}
	}

	public void finishWeaveCycle(){
		NBTTagCompound nbt = new NBTTagCompound();
		this.weaving = false;
		this.clothCompletionCount++;
		//nbt.setBoolean("weaving", false);
		//nbt.setInteger("cloth", this.cloth);
		this.writeToNBT(nbt);
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
		return clothCompletionCount;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("loomType", loomType);
		nbt.setByte("rotation", rotation);
		nbt.setBoolean("weaving", weaving);
		nbt.setBoolean("finished", finished);
		nbt.setInteger("cloth", clothCompletionCount);
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
		weaving = nbt.getBoolean("weaving");
		rotation = nbt.getByte("rotation");
		finished = nbt.getBoolean("finished");
		clothCompletionCount = nbt.getInteger("cloth");
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
		/*
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbt1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbt1.getByte("Slot");
			if(byte0 >= 0 && byte0 < 2)
				setInventorySlotContents(byte0,ItemStack.loadItemStackFromNBT(nbt1));
		}
		*/
	}

	public void updateGui()
	{
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		//validate();
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) 
	{
		this.readFromNBT(nbt);
		/*
		this.rotation = nbt.getByte("rotation");
		this.loomType = nbt.getInteger("loomType");
		this.stringType = nbt.getInteger("stringType");
		this.stringCount = nbt.getInteger("stringCount");
		this.finished = nbt.getBoolean("finished");
		this.cloth = nbt.getInteger("cloth");
		this.worldObj.func_147479_m(xCoord, yCoord, zCoord);
		 */
	}


	@Override
	public void handleDataPacket(NBTTagCompound nbt)
	{
		/*
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
		 */
		this.readFromNBT(nbt);
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) 
	{
		/*
		nbt.setByte("rotation", rotation);
		nbt.setInteger("loomType", loomType);
		nbt.setInteger("stringType", stringType);
		nbt.setInteger("stringCount", stringCount);
		nbt.setBoolean("finished",finished);
		nbt.setInteger("cloth", cloth);*/
		this.writeToNBT(nbt);
	}


	public static void registerRecipes()
	{
		LoomManager.getInstance().addRecipe(new LoomRecipe(new ItemStack(TFCItems.WoolYarn,16), new ItemStack(TFCItems.WoolCloth,1)),new ResourceLocation(Reference.ModID, "textures/blocks/String.png"));
		LoomManager.getInstance().addRecipe(new LoomRecipe(new ItemStack(Items.string,24), new ItemStack(TFCItems.SilkCloth,1)),new ResourceLocation(Reference.ModID, "textures/blocks/Silk.png"));
		LoomManager.getInstance().addRecipe(new LoomRecipe(new ItemStack(TFCItems.JuteFibre,12), new ItemStack(TFCItems.BurlapCloth,1)),new ResourceLocation(Reference.ModID, "textures/blocks/Rope.png"));
	}
}
