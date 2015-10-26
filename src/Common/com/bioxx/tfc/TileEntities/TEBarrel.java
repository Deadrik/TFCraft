package com.bioxx.tfc.TileEntities;

import java.util.Random;
import java.util.Stack;

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
import net.minecraftforge.fluids.IFluidContainerItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.WeatherManager;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.*;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Crafting.*;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IFood;

public class TEBarrel extends NetworkTileEntity implements IInventory
{
	public FluidStack fluid;
	public byte rotation;
	public int barrelType;
	public int mode;
	public ItemStack[] storage;
	private boolean sealed;
	public int sealtime;
	public int unsealtime;
	private int processTimer;

	public static final int MODE_IN = 0;
	public static final int MODE_OUT = 1;
	public static final int INPUT_SLOT = 0;
	public BarrelRecipe recipe;
	//temporary field. No need to save
	public boolean shouldDropItem = true;

	public TEBarrel()
	{
		storage = new ItemStack[12];
	}

	public boolean getSealed()
	{
		return sealed;
	}

	public int getTechLevel()
	{
		return 1;
	}

	public void clearInventory()
	{
		storage = new ItemStack[12];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}

	public void setSealed()
	{
		sealed = true;
	}

	public void setUnsealed(String reason)
	{
		if ("killing fuse".equals(reason))
			sealed = false;
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
		if (storage[INPUT_SLOT] != null && count == 1)
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

	public boolean canAcceptLiquids()
	{
		return this.getInvCount() == 0;
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
				processItems();
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
		return storage[INPUT_SLOT];
	}

	public FluidStack getFluidStack()
	{
		return this.fluid;
	}

	public int getMaxLiquid()
	{
		return 10000;
	}

	public boolean addLiquid(FluidStack inFS)
	{
		if (inFS != null)
		{
			//We dont want very hot liquids stored here so if they are much hotter than boiling water, we prevent it. 
			if (inFS.getFluid() != null && inFS.getFluid().getTemperature(inFS) > 385)
				return false;

			if (fluid == null)
			{
				fluid = inFS.copy();
				if (fluid.amount > this.getMaxLiquid())
				{
					fluid.amount = getMaxLiquid();
					inFS.amount = inFS.amount - this.getMaxLiquid();

				}
				else
					inFS.amount = 0;
			}
			else
			{
				//check if the barrel is full or if the fluid being added does not match the barrel liquid
				if (fluid.amount == getMaxLiquid() || !fluid.isFluidEqual(inFS))
					return false;

				int a = fluid.amount + inFS.amount - getMaxLiquid();
				fluid.amount = Math.min(fluid.amount + inFS.amount, getMaxLiquid());
				if (a > 0)
					inFS.amount = a;
				else
					inFS.amount = 0;
			}
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			return true;
		}

		return false;
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
		else if(is.getItem() instanceof IFluidContainerItem)
		{
			FluidStack isfs = ((IFluidContainerItem) is.getItem()).getFluid(is);
			if(isfs != null && addLiquid(isfs))
			{
				((IFluidContainerItem) is.getItem()).drain(is, is.getMaxDamage(), true);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
		else if(fluid != null && is.getItem() instanceof IFluidContainerItem)
		{
			FluidStack isfs = ((IFluidContainerItem) is.getItem()).getFluid(is);
			if(isfs == null || fluid.isFluidEqual(isfs))
			{
				fluid.amount -= ((IFluidContainerItem) is.getItem()).fill(is, fluid, true);
				if(fluid.amount == 0)
					fluid = null;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
		nbt.setString("player", player.getCommandSenderName());
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
		nbt.setString("player", player.getCommandSenderName());
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
		nbt.setString("player", player.getCommandSenderName());
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
		sealtime = nbt.getInteger("SealTime");
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
		this.sealtime = nbt.getInteger("SealTime");
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
		nbt.setInteger("SealTime", sealtime);
		nbt.setInteger("fluid", fluid != null ? fluid.getFluidID() : -1);
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

				// Broadcast the seal time to update the client
				NBTTagCompound timeTag = new NBTTagCompound();
				timeTag.setInteger("SealTime", sealtime);
				this.broadcastPacketInRange(this.createDataPacket(timeTag));

				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}

			if(nbt.hasKey("tab"))
			{
				int tab = nbt.getByte("tab");
				switchTab(worldObj.getPlayerEntityByName(nbt.getString("player")), tab);
			}
		}
		else
		{
			// Get the seal time for the client display
			if (nbt.hasKey("SealTime"))
				sealtime = nbt.getInteger("SealTime");
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
			ItemStack itemstack = storage[INPUT_SLOT];
			BarrelPreservativeRecipe preservative = BarrelManager.getInstance().findMatchingPreservativeRepice(this, itemstack, fluid, sealed);
			if (itemstack != null && fluid != null && fluid.getFluid() == TFCFluids.FRESHWATER)
			{
				if(TFC_ItemHeat.hasTemp(itemstack))
				{
					float temp = TFC_ItemHeat.getTemp(itemstack);
					if(fluid.amount >= 1 && temp > 1)
					{
						temp -= 50;
						fluid.amount -= 1;
						TFC_ItemHeat.setTemp(itemstack, temp);
						TFC_ItemHeat.handleItemHeat(itemstack);
					}
				}
			}
			if(fluid != null && itemstack != null && itemstack.getItem() instanceof IFood)
			{
				float w = Food.getWeight(itemstack);
				if(fluid.getFluid() == TFCFluids.VINEGAR)
				{
					//If the food is brined then we attempt to pickle it
					if(Food.isBrined(itemstack) && !Food.isPickled(itemstack) && w/fluid.amount <= Global.FOOD_MAX_WEIGHT/this.getMaxLiquid() && this.getSealed() &&
							sealtime != 0 && TFC_Time.getTotalHours() - sealtime >= 4)
					{
						fluid.amount -= 1 * w;
						Food.setPickled(itemstack, true);
					}
				}
			}

			if(preservative == null)
			{
				// No preservative was matched - decay normally
				TFC_Core.handleItemTicking(this, this.worldObj, xCoord, yCoord, zCoord);
			}
			else
			{
				float env = preservative.getEnvironmentalDecayFactor();
				float base = preservative.getBaseDecayModifier();
				if(Float.isNaN(env) || env < 0.0)
				{
					TFC_Core.handleItemTicking(this, this.worldObj, xCoord, yCoord, zCoord);
				}
				else if(Float.isNaN(base) || base < 0.0)
				{
					TFC_Core.handleItemTicking(this, this.worldObj, xCoord, yCoord, zCoord, env);
				}
				else
				{
					TFC_Core.handleItemTicking(this, this.worldObj, xCoord, yCoord, zCoord, env, base);
				}
			}

			//Check the general isRaining function. Supports weather2 localized rainstorms.
			//Fill the barrel when its raining.
			if(!this.getSealed() &&  WeatherManager.isRainingOnCoord(this.worldObj, xCoord, yCoord + 1, zCoord))
			{
				int count = getInvCount();	
				if (count == 0 || count == 1 && this.getInputStack() != null)
				{
					if(this.fluid == null)
						fluid = new FluidStack(TFCFluids.FRESHWATER, 1);
					else if(this.fluid != null && fluid.getFluid() == TFCFluids.FRESHWATER)
						fluid.amount = Math.min(fluid.amount+1, getMaxLiquid());
				}
			}


			//We only want to bother ticking food once per 5 seconds to keep overhead low.
			processTimer++;
			if(processTimer > 100)
			{
				processItems();
				processTimer = 0;
			}

			//Here we handle item stacks that are too big for MC to handle such as when making mortar.
			//If the stack is > its own max stack size then we split it and add it to the invisible solid storage area or 
			//spawn the item in the world if there is no room left.
			if (this.getFluidLevel() > 0 && getInputStack() != null)
			{
				int count = 1;
				while(this.getInputStack().stackSize > getInputStack().getMaxStackSize())
				{
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

			//Move any items in the solid storage slots to the main slot if they exist and the barrel has liquid.
			else if(this.getFluidLevel() > 0 && getInputStack() == null && this.getInvCount() > 0)
			{
				for(int i = 0; i < storage.length; i++)
				{
					if(storage[i] != null)
					{
						storage[INPUT_SLOT] = storage[i].copy();
						storage[i] = null;
						break;
					}

				}
			}

			//Reset our fluid if all of the liquid is gone.
			if(fluid != null && fluid.amount == 0)
				fluid = null;

			//Handle adding fluids to the barrel if the barrel is currently in input mode.
			if(mode == MODE_IN)
			{
				ItemStack container = getInputStack();
				FluidStack inLiquid = FluidContainerRegistry.getFluidForFilledItem(container);

				if(container != null && container.getItem() instanceof IFluidContainerItem)
				{
					FluidStack isfs = ((IFluidContainerItem)container.getItem()).getFluid(container);
					if(isfs != null && addLiquid(isfs))
					{
						((IFluidContainerItem) container.getItem()).drain(container, ((IFluidContainerItem)container.getItem()).getCapacity(container), true);
					}
				}
				else if (inLiquid != null && container != null && container.stackSize == 1)
				{
					if(addLiquid(inLiquid))
					{
						this.setInventorySlotContents(0, FluidContainerRegistry.drainFluidContainer(container));
					}
				}
			}
			//Drain liquid from the barrel to a container if the barrel is in output mode.
			else if(mode == MODE_OUT)
			{
				ItemStack container = getInputStack();

				if(container != null && fluid != null && container.getItem() instanceof IFluidContainerItem)
				{
					FluidStack isfs = ((IFluidContainerItem)container.getItem()).getFluid(container);
					if(isfs == null || fluid.isFluidEqual(isfs))
					{
						fluid.amount -= ((IFluidContainerItem) container.getItem()).fill(container, fluid, true);
						if(fluid.amount == 0)
							fluid = null;
					}
				}
				else if(FluidContainerRegistry.isEmptyContainer(container))
				{
					this.setInventorySlotContents(0, this.removeLiquid(getInputStack()));
				}
			}
		}
	}

	public void processItems()
	{
		if(this.getInvCount() == 0)
		{
			//Before we handle standard barrel processing we have to see if we are handling cheese and run that code first 
			//since it has to be handled specially.
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
					if(fluid.isFluidEqual(recipe.getResultFluid(origIS, origFS, time)) && recipe.removesLiquid)
					{
						if (fluid.getFluid() == TFCFluids.BRINE && origIS != null && origIS.getItem() instanceof IFood)
							fluid.amount -= recipe.getResultFluid(origIS, origFS, time).amount * Food.getWeight(origIS);
						else
							fluid.amount -= recipe.getResultFluid(origIS, origFS, time).amount;
					}
					else
					{
						this.fluid = recipe.getResultFluid(origIS, origFS, time);
						if (fluid != null && !(recipe instanceof BarrelLiquidToLiquidRecipe) && origFS != null)
							this.fluid.amount = origFS.amount;
					}

					if (origFS != null && origFS.getFluid() != TFCFluids.MILKCURDLED && this.fluid.getFluid() == TFCFluids.MILKCURDLED)
						this.sealtime = (int) TFC_Time.getTotalHours();

					Stack<ItemStack> resultStacks = recipe.getResult(origIS, origFS, time);
					if (!resultStacks.isEmpty())
					{
						ItemStack result = resultStacks.pop();
						if (fluid != null && fluid.getFluid() == TFCFluids.BRINE)
						{
							if (result == null && origIS != null)
								result = origIS.copy();
							if (result != null && result.getItem() instanceof IFood && (result.getItem() == TFCItems.cheese || ((IFood) result.getItem()).getFoodGroup() != EnumFoodGroup.Grain))
							{
								if (!Food.isBrined(result))
									Food.setBrined(result, true);
							}
						}

						storage[INPUT_SLOT] = result;

						for (int i = 1; i < storage.length; i++)
						{
							if (storage[i] == null && !resultStacks.isEmpty())
								this.setInventorySlotContents(i, resultStacks.pop());
						}

						while (!resultStacks.isEmpty())
							worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord, zCoord, resultStacks.pop()));

						this.setInventorySlotContents(0, result);
					}
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
		ItemStack inIS = this.getInputStack();
		if(this.getSealed() && this.fluid != null && this.fluid.getFluid() == TFCFluids.MILKCURDLED && 
				(inIS == null || inIS.getItem() instanceof IFood && ((IFood)inIS.getItem()).getFoodGroup() != EnumFoodGroup.Dairy && 
				((IFood)inIS.getItem()).isEdible(inIS) && Food.getWeight(inIS) <= 20.0f))
		{
			recipe = new BarrelRecipe(null,new FluidStack(TFCFluids.MILKCURDLED, 10000), ItemFoodTFC.createTag(new ItemStack(TFCItems.cheese, 1), 160), null).setMinTechLevel(0);
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

				ItemStack is = ItemFoodTFC.createTag(new ItemStack(TFCItems.cheese), w);

				if(inIS != null && inIS.getItem() instanceof IFood)
				{
					int[] profile = Food.getFoodTasteProfile(inIS);
					float ratio = Math.min((Food.getWeight(getInputStack())-Food.getDecay(inIS))/(Global.FOOD_MAX_WEIGHT/8), 1.0f);
					Food.setSweetMod(is, (int) Math.floor(profile[INPUT_SLOT] * ratio));
					Food.setSourMod(is, (int)Math.floor(profile[1]*ratio));
					Food.setSaltyMod(is, (int)Math.floor(profile[2]*ratio));
					Food.setBitterMod(is, (int)Math.floor(profile[3]*ratio));
					Food.setSavoryMod(is, (int)Math.floor(profile[4]*ratio));
					Food.setInfusion(is, inIS.getItem().getUnlocalizedName());
					this.storage[INPUT_SLOT] = null;
				}

				this.actionEmpty();
				this.setInventorySlotContents(0, is);
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
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.potato), 160), new FluidStack(TFCFluids.FRESHWATER, 10000), null, new FluidStack(TFCFluids.VODKA, 10000)));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.redApple), 160), new FluidStack(TFCFluids.FRESHWATER, 10000), null, new FluidStack(TFCFluids.CIDER, 10000)));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.greenApple), 160), new FluidStack(TFCFluids.FRESHWATER, 10000), null, new FluidStack(TFCFluids.CIDER, 10000)));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.wheatGround), 160), new FluidStack(TFCFluids.FRESHWATER, 10000), null, new FluidStack(TFCFluids.WHISKEY, 10000)));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.ryeGround), 160), new FluidStack(TFCFluids.FRESHWATER, 10000), null, new FluidStack(TFCFluids.RYEWHISKEY, 10000)));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.barleyGround), 160), new FluidStack(TFCFluids.FRESHWATER, 10000), null, new FluidStack(TFCFluids.BEER, 10000)));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.riceGround), 160), new FluidStack(TFCFluids.FRESHWATER, 10000), null, new FluidStack(TFCFluids.SAKE, 10000)));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.sugar), 160), new FluidStack(TFCFluids.FRESHWATER, 10000), null, new FluidStack(TFCFluids.RUM, 10000)));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.cornmealGround), 160), new FluidStack(TFCFluids.FRESHWATER, 10000), null, new FluidStack(TFCFluids.CORNWHISKEY, 10000)));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(null, new FluidStack(TFCFluids.MILKVINEGAR, 10000), null, new FluidStack(TFCFluids.MILKCURDLED, 10000)).setMinTechLevel(0).setRemovesLiquid(false));
		//BarrelManager.getInstance().addRecipe(new BarrelRecipeNoItem(new FluidStack(TFCFluid.MILKCURDLED, 10000), ItemFoodTFC.createTag(new ItemStack(TFCItems.Cheese), 160), null).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.logs, 1, 0), new FluidStack(TFCFluids.FRESHWATER, 1000), null, new FluidStack(TFCFluids.TANNIN, 1000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.logs, 1, 2), new FluidStack(TFCFluids.FRESHWATER, 1000), null, new FluidStack(TFCFluids.TANNIN, 1000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.logs, 1, 3), new FluidStack(TFCFluids.FRESHWATER, 1000), null, new FluidStack(TFCFluids.TANNIN, 1000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.logs, 1, 4), new FluidStack(TFCFluids.FRESHWATER, 1000), null, new FluidStack(TFCFluids.TANNIN, 1000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.logs, 1, 5), new FluidStack(TFCFluids.FRESHWATER, 1000), null, new FluidStack(TFCFluids.TANNIN, 1000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.logs, 1, 6), new FluidStack(TFCFluids.FRESHWATER, 1000), null, new FluidStack(TFCFluids.TANNIN, 1000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.logs, 1, 9), new FluidStack(TFCFluids.FRESHWATER, 1000), null, new FluidStack(TFCFluids.TANNIN, 1000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.powder, 1, 0), new FluidStack(TFCFluids.FRESHWATER, 500), null, new FluidStack(TFCFluids.LIMEWATER, 500), 0).setMinTechLevel(0).setSealedRecipe(false).setRemovesLiquid(false).setAllowAnyStack(false));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.scrapedHide, 1, 0), new FluidStack(TFCFluids.FRESHWATER, 300), new ItemStack(TFCItems.prepHide, 1, 0), new FluidStack(TFCFluids.FRESHWATER, 300)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.scrapedHide, 1, 1), new FluidStack(TFCFluids.FRESHWATER, 400), new ItemStack(TFCItems.prepHide, 1, 1), new FluidStack(TFCFluids.FRESHWATER, 400)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.scrapedHide, 1, 2), new FluidStack(TFCFluids.FRESHWATER, 500), new ItemStack(TFCItems.prepHide, 1, 2), new FluidStack(TFCFluids.FRESHWATER, 500)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.hide, 1, 0), new FluidStack(TFCFluids.LIMEWATER, 300), new ItemStack(TFCItems.soakedHide, 1, 0), new FluidStack(TFCFluids.LIMEWATER, 300)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.hide, 1, 1), new FluidStack(TFCFluids.LIMEWATER, 400), new ItemStack(TFCItems.soakedHide, 1, 1), new FluidStack(TFCFluids.LIMEWATER, 400)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.hide, 1, 2), new FluidStack(TFCFluids.LIMEWATER, 500), new ItemStack(TFCItems.soakedHide, 1, 2), new FluidStack(TFCFluids.LIMEWATER, 500)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.prepHide, 1, 0), new FluidStack(TFCFluids.TANNIN, 300), new ItemStack(TFCItems.leather, 1), new FluidStack(TFCFluids.TANNIN, 300)).setKeepStackSize(false).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.prepHide, 1, 1), new FluidStack(TFCFluids.TANNIN, 400), new ItemStack(TFCItems.leather, 2), new FluidStack(TFCFluids.TANNIN, 400)).setKeepStackSize(false).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.prepHide, 1, 2), new FluidStack(TFCFluids.TANNIN, 500), new ItemStack(TFCItems.leather, 3), new FluidStack(TFCFluids.TANNIN, 500)).setKeepStackSize(false).setMinTechLevel(0));		
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCBlocks.sand, 1, 32767), new FluidStack(TFCFluids.LIMEWATER, 100), new ItemStack(TFCItems.mortar, 16), new FluidStack(TFCFluids.LIMEWATER, 100)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCBlocks.sand2, 1, 32767), new FluidStack(TFCFluids.LIMEWATER, 100), new ItemStack(TFCItems.mortar, 16), new FluidStack(TFCFluids.LIMEWATER, 100)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.VODKA, 100), new FluidStack(TFCFluids.VINEGAR, 100)));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.CIDER, 100), new FluidStack(TFCFluids.VINEGAR, 100)));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.WHISKEY, 100), new FluidStack(TFCFluids.VINEGAR, 100)));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.RYEWHISKEY, 100), new FluidStack(TFCFluids.VINEGAR, 100)));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.BEER, 100), new FluidStack(TFCFluids.VINEGAR, 100)));	
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.SAKE, 100), new FluidStack(TFCFluids.VINEGAR, 100)));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.RUM, 100), new FluidStack(TFCFluids.VINEGAR, 100)));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.CORNWHISKEY, 100), new FluidStack(TFCFluids.VINEGAR, 100)));
		BarrelManager.getInstance().addRecipe(new BarrelLiquidToLiquidRecipe(new FluidStack(TFCFluids.SALTWATER, 9000), new FluidStack(TFCFluids.VINEGAR, 1000), new FluidStack(TFCFluids.BRINE, 10000)).setSealedRecipe(false).setMinTechLevel(0).setRemovesLiquid(false));
		BarrelManager.getInstance().addRecipe(new BarrelBriningRecipe(new FluidStack(TFCFluids.BRINE, 60), new FluidStack(TFCFluids.BRINE, 60)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.sugarcane), 1), new FluidStack(TFCFluids.FRESHWATER, 60), ItemFoodTFC.createTag(new ItemStack(TFCItems.sugar), 0.1f), new FluidStack(TFCFluids.FRESHWATER, 60)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.jute, 1, 0), new FluidStack(TFCFluids.FRESHWATER, 200), new ItemStack(TFCItems.juteFiber, 1, 0), new FluidStack(TFCFluids.FRESHWATER, 200)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelLiquidToLiquidRecipe(new FluidStack(TFCFluids.MILK, 9000), new FluidStack(TFCFluids.VINEGAR, 1000), new FluidStack(TFCFluids.MILKVINEGAR, 10000)).setSealedRecipe(false).setMinTechLevel(0).setRemovesLiquid(false));
		BarrelManager.getInstance().addRecipe(new BarrelLiquidToLiquidRecipe(new FluidStack(TFCFluids.MILKVINEGAR, 9000), new FluidStack(TFCFluids.MILK, 1000), new FluidStack(TFCFluids.MILKVINEGAR, 10000)).setSealedRecipe(false).setMinTechLevel(0).setRemovesLiquid(false));
		// 5000mb / 160oz = 31.25
		// 10000mb / 160oz = 62.5
		// FluidStack naturally only takes int so I rounded down
		BarrelPreservativeRecipe picklePreservative = new BarrelPreservativeRecipe(new FluidStack(TFCFluids.VINEGAR,31),"gui.barrel.preserving").setAllowGrains(false).setRequiresPickled(true).setEnvironmentalDecayFactor(0.25f).setBaseDecayModifier(0.1f).setRequiresSealed(true);
		BarrelPreservativeRecipe brineInBrinePreservative = new BarrelPreservativeRecipe(new FluidStack(TFCFluids.BRINE,62),"gui.barrel.preserving").setAllowGrains(false).setRequiresBrined(true).setEnvironmentalDecayFactor(0.75f).setRequiresSealed(true);
		BarrelPreservativeRecipe brineInVinegarPreservative = new BarrelPreservativeRecipe(new FluidStack(TFCFluids.VINEGAR,62),"gui.barrel.preserving").setAllowGrains(false).setRequiresBrined(true).setEnvironmentalDecayFactor(0.75f).setRequiresSealed(true);
		BarrelManager.getInstance().addPreservative(picklePreservative);
		BarrelManager.getInstance().addPreservative(brineInBrinePreservative);
		BarrelManager.getInstance().addPreservative(brineInVinegarPreservative);
	}
}
