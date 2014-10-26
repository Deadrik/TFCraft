package com.bioxx.tfc.TileEntities;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFCFluid;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Crafting.BarrelAlcoholRecipe;
import com.bioxx.tfc.api.Crafting.BarrelBriningRecipe;
import com.bioxx.tfc.api.Crafting.BarrelLiquidToLiquidRecipe;
import com.bioxx.tfc.api.Crafting.BarrelManager;
import com.bioxx.tfc.api.Crafting.BarrelMultiItemRecipe;
import com.bioxx.tfc.api.Crafting.BarrelRecipe;
import com.bioxx.tfc.api.Crafting.BarrelVinegarRecipe;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IFood;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TEBarrel extends NetworkTileEntity implements IInventory
{
	public FluidStack fluid = null;
	public byte rotation = 0;
	public int barrelType;
	public int mode = 0;
	public ItemStack[] storage;
	private boolean sealed = false;
	public int sealtime = 0;
	public int unsealtime = 0;
	private int processTimer = 0;
	private boolean primed = false;

	public static final int MODE_IN = 0;
	public static final int MODE_OUT = 1;
	public BarrelRecipe recipe;

	public TEBarrel()
	{
		storage = new ItemStack[12];
	}

	public boolean getSealed()
	{
		return sealed;
	}

	protected int getTechLevel()
	{
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord +1, yCoord + 1, zCoord + 1);
		return bb;
	}

	public void setSealed()
	{
		sealed = true;
	}

	public void setUnsealed(String reason)
	{
		if(reason.equals("killing fuse"))
			sealed = false;
	}

	/**
	 * Is the barrel primed, if true the fuse counting down
	 */
	public boolean isPrimed()
	{
		return primed;
	}
	
	/**
	 * Set the barrels primed status, set to true if the barrels fuse is
	 * running --THIS DOES NOT START OR STOP THE FUSE--
	 */
	public void setPrimed(boolean bPrimed)
	{
		primed = bPrimed;
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
		return "Barrel";
	}

	@Override
	public int getSizeInventory()
	{
		return 12;
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

	public int getGunPowderCount()
	{
		int count = 0;
		for(ItemStack is : storage)
		{
			if(is != null && is.getItem() == Items.gunpowder)
				count+=is.stackSize;
		}
		return count;
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
		if(!ItemStack.areItemStacksEqual(storage[i], is))
		{
			storage[i] = is;
			if(i == 0)
			{
				if(storage[0] == null)
					System.out.println("Setting input to null" + " : " + worldObj.isRemote);
				else
					System.out.println("Setting input to "+storage[0].getDisplayName() + " : " + worldObj.isRemote);
				ProcessItems();
				if(!getSealed())
					this.unsealtime = (int)TFC_Time.getTotalHours();
			}
		}
	}

	public int getFluidLevel()
	{
		if(fluid != null)
			return fluid.amount;
		return 0;
	}

	public ItemStack getInputStack()
	{
		return storage[0];
	}

	public FluidStack getFluidStack()
	{
		return this.fluid;
	}

	public int getMaxLiquid()
	{
		return 10000;
	}

	public boolean addLiquid(int amount)
	{
		if(fluid.amount == getMaxLiquid())
			return false;

		fluid.amount = Math.min(fluid.amount+amount, getMaxLiquid());
		return true;
	}

	public boolean addLiquid(FluidStack inFS)
	{
		if(fluid == null)
		{
			fluid = inFS.copy();
			if(fluid.amount > this.getMaxLiquid())
			{
				fluid.amount = getMaxLiquid();
				inFS.amount = inFS.amount - this.getMaxLiquid();

			}
			else inFS.amount = 0;
		}
		else
		{
			if(fluid.amount == getMaxLiquid() || !fluid.isFluidEqual(inFS))
				return false;

			int a = (fluid.amount+inFS.amount) - getMaxLiquid();
			fluid.amount = Math.min(fluid.amount+inFS.amount, getMaxLiquid());
			if(a > 0) inFS.amount = a;
			else inFS.amount = 0;
		}
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return true;
	}

	public ItemStack addLiquid(ItemStack is)
	{
		if(is == null || is.stackSize > 1)
			return is;
		if(FluidContainerRegistry.isFilledContainer(is))
		{
			FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(is);
			if(addLiquid(fs))
			{
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				return FluidContainerRegistry.drainFluidContainer(is);
			}
		}
		return is;
	}

	/**
	 * This attempts to remove a portion of the water in this container and put it into a valid Container Item
	 */
	public ItemStack removeLiquid(ItemStack is)
	{
		if(is == null || is.stackSize > 1)
			return is;
		if(FluidContainerRegistry.isEmptyContainer(is))
		{
			ItemStack out = FluidContainerRegistry.fillFluidContainer(fluid, is);
			if(out != null)
			{
				FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(out);
				fluid.amount -= fs.amount;
				is = null;
				if(fluid.amount == 0)
				{
					fluid = null;
				}
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				return out;
			}
		}
		return is;
	}

	/**
	 * This removes a specified amount of liquid from the container and updates the block.
	 */
	public void drainLiquid(int amount)
	{
		if(!getSealed() && this.getFluidStack() != null)
		{
			this.getFluidStack().amount -= amount;
			if(this.getFluidStack().amount <= 0)
				this.actionEmpty();
			else
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	public int getLiquidScaled(int i)
	{
		if(fluid != null)
			return fluid.amount * i/getMaxLiquid();
		return 0;
	}

	public boolean actionSeal(int tab, EntityPlayer player)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("seal", true);
		nbt.setByte("tab", (byte)tab);
		nbt.setString("player", player.getDisplayName());
		this.broadcastPacketInRange(this.createDataPacket(nbt));
		sealed = true;
		this.worldObj.func_147479_m(xCoord, yCoord, zCoord);
		return true;
	}

	public boolean actionUnSeal(int tab, EntityPlayer player)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("seal", false);
		nbt.setByte("tab", (byte)tab);
		nbt.setString("player", player.getDisplayName());
		this.broadcastPacketInRange(this.createDataPacket(nbt));
		sealed = false;
		this.worldObj.func_147479_m(xCoord, yCoord, zCoord);
		return true;
	}

	public void actionEmpty()
	{
		fluid = null;
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("fluidID", (byte)-1);
		this.broadcastPacketInRange(this.createDataPacket(nbt));
	}

	public void actionMode()
	{
		mode = mode == 0 ? 1 : 0;
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("mode", (byte)mode);
		this.broadcastPacketInRange(this.createDataPacket(nbt));
	}

	public void actionSwitchTab(int tab, EntityPlayer player)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("tab", (byte)tab);
		nbt.setString("player", player.getDisplayName());
		this.broadcastPacketInRange(this.createDataPacket(nbt));
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
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setBoolean("Sealed", sealed);
		nbt.setInteger("SealTime", sealtime);
		nbt.setInteger("barrelType", barrelType);
		//nbt.setInteger("mode", mode);
		NBTTagCompound fluidNBT = new NBTTagCompound();
		if(fluid != null)
			fluid.writeToNBT(fluidNBT);
		nbt.setTag("fluidNBT", fluidNBT);
		nbt.setByte("rotation", rotation);
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
		fluid = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("fluidNBT"));
		sealed = nbt.getBoolean("Sealed");
		sealtime = nbt.getInteger("SealTime");
		barrelType = nbt.getInteger("barrelType");
		//mode = nbt.getInteger("mode");
		rotation = nbt.getByte("rotation");
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
		barrelType = nbt.getInteger("barrelType");
		fluid = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("fluidNBT"));
		sealed = nbt.getBoolean("Sealed");
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbt1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbt1.getByte("Slot");
			if(byte0 >= 0 && byte0 < storage.length)
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
		this.sealed = nbt.getBoolean("sealed");
		barrelType = nbt.getInteger("barrelType");
		if(nbt.getInteger("fluid") != -1)
		{
			if(fluid != null)
				fluid.amount = nbt.getInteger("fluidAmount");
			else
				fluid = new FluidStack(nbt.getInteger("fluid"), nbt.getInteger("fluidAmount"));
		}
		else
		{
			fluid = null;
		}
		this.worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) 
	{
		nbt.setByte("rotation", rotation);
		nbt.setBoolean("sealed", sealed);
		nbt.setInteger("fluid", fluid != null ? fluid.fluidID : -1);
		nbt.setInteger("fluidAmount", fluid != null ? fluid.amount : 0);
		nbt.setInteger("barrelType", barrelType);
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt)
	{
		if(nbt.hasKey("fluidID"))
		{
			if(nbt.getByte("fluidID") == -1)
				fluid = null;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		if(!worldObj.isRemote)
		{	
			if(nbt.hasKey("mode"))
			{
				mode = nbt.getByte("mode");
			}
			else if(nbt.hasKey("seal"))
			{
				sealed = nbt.getBoolean("seal");
				if(!sealed)
				{
					unsealtime = (int) TFC_Time.getTotalHours();
					sealtime = 0;
				}
				else
				{
					sealtime = (int) TFC_Time.getTotalHours();
					unsealtime = 0;
				}
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}

			if(nbt.hasKey("tab"))
			{
				int tab = nbt.getByte("tab");
				switchTab(worldObj.getPlayerEntityByName(nbt.getString("player")), tab);
			}
		}
	}

	protected void switchTab(EntityPlayer player, int tab)
	{
		if(player != null)
			if(tab == 0)
				player.openGui(TerraFirmaCraft.instance, 35, worldObj, xCoord, yCoord, zCoord);
			else if(tab == 1)
				player.openGui(TerraFirmaCraft.instance, 36, worldObj, xCoord, yCoord, zCoord);
	}

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			boolean shouldStandardTick = false;
			if(fluid == null)
				shouldStandardTick = true;
			else
			{
				ItemStack itemstack = storage[0]; 
				if(itemstack != null)
				{
					if(TFC_ItemHeat.HasTemp(itemstack))
					{
						float temp = TFC_ItemHeat.GetTemp(itemstack);
						if(fluid.amount >= 1 && temp > 1)
						{
							temp -= 50;
							fluid.amount -= 1;
							TFC_ItemHeat.SetTemp(itemstack, temp);
							TFC_ItemHeat.HandleItemHeat(itemstack);
						}
					}
				}
				if(itemstack != null && itemstack.getItem() instanceof IFood)
				{
					float w = ((IFood)itemstack.getItem()).getFoodWeight(itemstack);
					if(fluid.getFluid() == TFCFluid.BRINE)
					{
						if(Food.isBrined(itemstack))
						{
							if(w/fluid.amount <= 0.016)
								TFC_Core.handleItemTicking(this, this.worldObj, xCoord, yCoord, zCoord, 0.75f);
							else
								shouldStandardTick = true;
						}
					}
					else if(fluid.getFluid() == TFCFluid.VINEGAR)
					{
						if(Food.isBrined(itemstack))
						{
							if(!Food.isPickled(itemstack) && w/fluid.amount <= Global.FOOD_MAX_WEIGHT/this.getMaxLiquid() && this.getSealed() &&
									sealtime != 0 && TFC_Time.getTotalHours() - sealtime >= 4)
							{
								fluid.amount -= 1 * w;
								Food.setPickled(itemstack, true);
							}

							if(Food.isPickled(itemstack) && w/fluid.amount <= Global.FOOD_MAX_WEIGHT/this.getMaxLiquid()*2)
								TFC_Core.handleItemTicking(this, this.worldObj, xCoord, yCoord, zCoord, 0.25f, 0.1f);
							else
								shouldStandardTick = true;
						}
					}
				}
			}
			if(shouldStandardTick)
			{
				TFC_Core.handleItemTicking(this, this.worldObj, xCoord, yCoord, zCoord);
			}

			if(!this.getSealed() && worldObj.canLightningStrikeAt(xCoord, yCoord+1, zCoord))
			{
				int count = getInvCount();	
				if(count == 0 ||(count == 1 && this.getInputStack() != null))
				{
					if(this.fluid == null)
						fluid = new FluidStack(TFCFluid.FRESHWATER, 1);
					else if(this.fluid != null && fluid.getFluid() == TFCFluid.FRESHWATER)
						fluid.amount = Math.min(fluid.amount+1, getMaxLiquid());
				}
			}

			processTimer++;

			if(processTimer > 100)
			{
				ProcessItems();
				processTimer = 0;
			}

			if(this.getFluidLevel() > 0 && getInputStack() != null)
			{
				int count = 1;
				while(this.getInputStack().stackSize > getInputStack().getMaxStackSize())
				{
					int ss = getInputStack().stackSize;
					ItemStack is = getInputStack().splitStack(getInputStack().getMaxStackSize());
					if(count < this.storage.length && this.getStackInSlot(count) == null)
					{
						this.setInventorySlotContents(count, is);
					}
					else
					{
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord, zCoord, is));
					}
					count++;
				}
			}
			else if(this.getFluidLevel() > 0 && getInputStack() == null && this.getInvCount() > 0)
			{
				for(int i = 0; i < storage.length; i++)
				{
					if(storage[i] != null)
					{
						storage[0] = storage[i].copy();
						storage[i] = null;
						break;
					}

				}
			}

			if(fluid != null && fluid.amount == 0)
				fluid = null;

			if(mode == MODE_IN)
			{
				ItemStack container = getInputStack();
				FluidStack inLiquid = FluidContainerRegistry.getFluidForFilledItem(container);
				if(inLiquid != null && container.stackSize == 1)
				{
					if(this.fluid == null)
					{
						this.fluid = inLiquid.copy();
						this.setInventorySlotContents(0, FluidContainerRegistry.drainFluidContainer(container));
					}
					else if(inLiquid.isFluidEqual(this.fluid))
					{
						if(addLiquid(inLiquid.amount))
						{
							this.setInventorySlotContents(0, FluidContainerRegistry.drainFluidContainer(container));
						}
					}
				}
			}
			else if(mode == MODE_OUT)
			{
				if(FluidContainerRegistry.isEmptyContainer(getInputStack()))
				{
					this.setInventorySlotContents(0, this.removeLiquid(getInputStack()));
				}
			}
		}
	}

	public void ProcessItems()
	{
		if(this.getInvCount() == 0)
		{
			boolean isCheese = handleCheese();
			if(getFluidStack() != null && !isCheese)
			{
				recipe = BarrelManager.getInstance().findMatchingRecipe(getInputStack(), getFluidStack(), this.sealed, getTechLevel());
				if(recipe != null && !worldObj.isRemote)
				{
					int time = 0;
					if(sealtime > 0)
						time = (int)TFC_Time.getTotalHours() - sealtime;
					else if(unsealtime > 0)
						time = (int)TFC_Time.getTotalHours() - unsealtime;

					//Make sure that the recipe meets the time requirements
					if(recipe.isSealedRecipe() && time < recipe.sealTime)
						return;

					ItemStack origIS = getInputStack() != null ? getInputStack().copy() : null;
					FluidStack origFS = getFluidStack() != null ? getFluidStack().copy() : null;
					if(fluid.isFluidEqual(recipe.getResultFluid(origIS, origFS, time)))
					{
						fluid.amount -= recipe.getResultFluid(origIS, origFS, time).amount;
					}
					else
					{
						this.fluid = recipe.getResultFluid(origIS, origFS, time);
						if(fluid != null && !(recipe instanceof BarrelLiquidToLiquidRecipe))
							this.fluid.amount = origFS.amount;
					}
					ItemStack result = recipe.getResult(origIS, origFS, time);
					if(fluid != null && fluid.getFluid() == TFCFluid.BRINE)
					{
						if(result == null)
							result = origIS.copy();
						if(result != null && result.getItem() instanceof IFood && 
								(((IFood)result.getItem()).getFoodGroup() == EnumFoodGroup.Vegetable || 
								((IFood)result.getItem()).getFoodGroup() == EnumFoodGroup.Fruit ||
								((IFood)result.getItem()).getFoodGroup() == EnumFoodGroup.Protein))
						{
							if(!Food.isBrined(result))
								Food.setBrined(result, true);
						}
					}

					this.setInventorySlotContents(0, result);
				}
			}
			else if(getFluidStack() == null && !isCheese)recipe = null;
		}
	}

	/**
	 * We have to handle cheese by itself because the barrel recipe manager doesnt take kindly to null input items.
	 */
	private boolean handleCheese()
	{
		if(this.getSealed() && this.fluid != null && this.fluid.getFluid() == TFCFluid.MILKCURDLED && this.getInputStack() == null)
		{
			recipe = new BarrelRecipe(null,new FluidStack(TFCFluid.MILKCURDLED, 10000), ItemFoodTFC.createTag(new ItemStack(TFCItems.Cheese, 1), 160), null).setMinTechLevel(0);
			if(!worldObj.isRemote)
			{
				int time = 0;
				if(sealtime > 0)
					time = (int)TFC_Time.getTotalHours() - sealtime;
				else if(unsealtime > 0)
					time = (int)TFC_Time.getTotalHours() - unsealtime;

				//Make sure that the recipe meets the time requirements
				if(time < recipe.sealTime)
					return true;
				float w = this.fluid.amount/62.5f;
				this.setInventorySlotContents(0, ItemFoodTFC.createTag(new ItemStack(TFCItems.Cheese), w));
				this.actionEmpty();
			}
			return true;
		}
		return false;
	}

	public static ItemStack createFullBarrel(FluidStack f, ItemStack is)
	{
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());

		is.getTagCompound().setBoolean("Sealed", true);
		//nbt.setInteger("mode", mode);
		NBTTagCompound fluidNBT = new NBTTagCompound();
		if(f != null)
			f.writeToNBT(fluidNBT);
		is.getTagCompound().setTag("fluidNBT", fluidNBT);

		return is;
	}

	public static void registerRecipes()
	{
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.Potato), 160), new FluidStack(TFCFluid.FRESHWATER, 10000), null, new FluidStack(TFCFluid.VODKA, 10000)));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.RedApple), 160), new FluidStack(TFCFluid.FRESHWATER, 10000), null, new FluidStack(TFCFluid.CIDER, 10000)));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.GreenApple), 160), new FluidStack(TFCFluid.FRESHWATER, 10000), null, new FluidStack(TFCFluid.CIDER, 10000)));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.WheatGround), 160), new FluidStack(TFCFluid.FRESHWATER, 10000), null, new FluidStack(TFCFluid.WHISKEY, 10000)));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.RyeGround), 160), new FluidStack(TFCFluid.FRESHWATER, 10000), null, new FluidStack(TFCFluid.RYEWHISKEY, 10000)));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.BarleyGround), 160), new FluidStack(TFCFluid.FRESHWATER, 10000), null, new FluidStack(TFCFluid.BEER, 10000)));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.RiceGround), 160), new FluidStack(TFCFluid.FRESHWATER, 10000), null, new FluidStack(TFCFluid.SAKE, 10000)));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.Sugar), 160), new FluidStack(TFCFluid.FRESHWATER, 10000), null, new FluidStack(TFCFluid.RUM, 10000)));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.CornmealGround), 160), new FluidStack(TFCFluid.FRESHWATER, 10000), null, new FluidStack(TFCFluid.CORNWHISKEY, 10000)));
		BarrelManager.getInstance().addRecipe(new BarrelLiquidToLiquidRecipe(new FluidStack(TFCFluid.MILK, 9000), new FluidStack(TFCFluid.VINEGAR, 1000), new FluidStack(TFCFluid.MILKCURDLED, 10000)).setMinTechLevel(0));
		//BarrelManager.getInstance().addRecipe(new BarrelRecipeNoItem(new FluidStack(TFCFluid.MILKCURDLED, 10000), ItemFoodTFC.createTag(new ItemStack(TFCItems.Cheese), 160), null).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.Logs, 1, 0), new FluidStack(TFCFluid.FRESHWATER, 1000), null, new FluidStack(TFCFluid.TANNIN, 1000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.Logs, 1, 2), new FluidStack(TFCFluid.FRESHWATER, 1000), null, new FluidStack(TFCFluid.TANNIN, 1000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.Logs, 1, 3), new FluidStack(TFCFluid.FRESHWATER, 1000), null, new FluidStack(TFCFluid.TANNIN, 1000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.Logs, 1, 4), new FluidStack(TFCFluid.FRESHWATER, 1000), null, new FluidStack(TFCFluid.TANNIN, 1000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.Logs, 1, 5), new FluidStack(TFCFluid.FRESHWATER, 1000), null, new FluidStack(TFCFluid.TANNIN, 1000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.Logs, 1, 6), new FluidStack(TFCFluid.FRESHWATER, 1000), null, new FluidStack(TFCFluid.TANNIN, 1000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.Logs, 1, 9), new FluidStack(TFCFluid.FRESHWATER, 1000), null, new FluidStack(TFCFluid.TANNIN, 1000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.Powder, 1, 0), new FluidStack(TFCFluid.FRESHWATER, 500), null, new FluidStack(TFCFluid.LIMEWATER, 500), 0).setMinTechLevel(0).setSealedRecipe(false).setRemovesLiquid(false).setAllowAnyStack(false));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.ScrapedHide, 1, 0), new FluidStack(TFCFluid.FRESHWATER, 300), new ItemStack(TFCItems.PrepHide, 1, 0), new FluidStack(TFCFluid.FRESHWATER, 300)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.ScrapedHide, 1, 1), new FluidStack(TFCFluid.FRESHWATER, 400), new ItemStack(TFCItems.PrepHide, 1, 1), new FluidStack(TFCFluid.FRESHWATER, 400)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.ScrapedHide, 1, 2), new FluidStack(TFCFluid.FRESHWATER, 500), new ItemStack(TFCItems.PrepHide, 1, 2), new FluidStack(TFCFluid.FRESHWATER, 500)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.Hide, 1, 0), new FluidStack(TFCFluid.LIMEWATER, 300), new ItemStack(TFCItems.SoakedHide, 1, 0), new FluidStack(TFCFluid.LIMEWATER, 300)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.Hide, 1, 1), new FluidStack(TFCFluid.LIMEWATER, 400), new ItemStack(TFCItems.SoakedHide, 1, 1), new FluidStack(TFCFluid.LIMEWATER, 400)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.Hide, 1, 2), new FluidStack(TFCFluid.LIMEWATER, 500), new ItemStack(TFCItems.SoakedHide, 1, 2), new FluidStack(TFCFluid.LIMEWATER, 500)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.PrepHide, 1, 0), new FluidStack(TFCFluid.TANNIN, 300), new ItemStack(TFCItems.Leather, 1), new FluidStack(TFCFluid.TANNIN, 300)).setKeepStackSize(false).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.PrepHide, 1, 1), new FluidStack(TFCFluid.TANNIN, 400), new ItemStack(TFCItems.Leather, 2), new FluidStack(TFCFluid.TANNIN, 400)).setKeepStackSize(false).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.PrepHide, 1, 2), new FluidStack(TFCFluid.TANNIN, 500), new ItemStack(TFCItems.Leather, 3), new FluidStack(TFCFluid.TANNIN, 500)).setKeepStackSize(false).setMinTechLevel(0));		
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCBlocks.Sand, 1, 32767), new FluidStack(TFCFluid.LIMEWATER, 100), new ItemStack(TFCItems.Mortar, 16), new FluidStack(TFCFluid.LIMEWATER, 100)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCBlocks.Sand2, 1, 32767), new FluidStack(TFCFluid.LIMEWATER, 100), new ItemStack(TFCItems.Mortar, 16), new FluidStack(TFCFluid.LIMEWATER, 100)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluid.VODKA, 100), new FluidStack(TFCFluid.VINEGAR, 100)));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluid.CIDER, 100), new FluidStack(TFCFluid.VINEGAR, 100)));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluid.WHISKEY, 100), new FluidStack(TFCFluid.VINEGAR, 100)));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluid.RYEWHISKEY, 100), new FluidStack(TFCFluid.VINEGAR, 100)));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluid.BEER, 100), new FluidStack(TFCFluid.VINEGAR, 100)));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluid.SAKE, 100), new FluidStack(TFCFluid.VINEGAR, 100)));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluid.RUM, 100), new FluidStack(TFCFluid.VINEGAR, 100)));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluid.CORNWHISKEY, 100), new FluidStack(TFCFluid.VINEGAR, 100)));
		BarrelManager.getInstance().addRecipe(new BarrelLiquidToLiquidRecipe(new FluidStack(TFCFluid.SALTWATER, 9000), new FluidStack(TFCFluid.VINEGAR, 1000), new FluidStack(TFCFluid.BRINE, 10000)).setSealedRecipe(false).setMinTechLevel(0).setRemovesLiquid(false));
		BarrelManager.getInstance().addRecipe(new BarrelBriningRecipe(new FluidStack(TFCFluid.BRINE, 60), new FluidStack(TFCFluid.BRINE, 60)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.Sugarcane), 1), new FluidStack(TFCFluid.FRESHWATER, 60), ItemFoodTFC.createTag(new ItemStack(TFCItems.Sugar), 0.1f), new FluidStack(TFCFluid.FRESHWATER, 60)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.Jute, 1, 0), new FluidStack(TFCFluid.FRESHWATER, 200), new ItemStack(TFCItems.JuteFibre, 1, 0), new FluidStack(TFCFluid.FRESHWATER, 200)).setMinTechLevel(0));
	}
}
