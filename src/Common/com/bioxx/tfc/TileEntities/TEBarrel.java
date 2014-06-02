package com.bioxx.tfc.TileEntities;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFCFluid;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Crafting.BarrelAlcoholRecipe;
import com.bioxx.tfc.api.Crafting.BarrelManager;
import com.bioxx.tfc.api.Crafting.BarrelRecipe;

public class TEBarrel extends NetworkTileEntity implements IInventory
{
	public FluidStack fluid = null;
	public byte rotation = 0;
	public int barrelType;
	public int mode;
	public ItemStack[] storage;
	public ItemStack output;
	private boolean sealed;
	public int sealtimecounter;
	public final int SEALTIME = TFCOptions.enableDebugMode ? 0 : (int)((TFC_Time.hourLength * 12) / 100);//default 80
	public static final int MODE_IN = 0;
	public static final int MODE_OUT = 1;
	private boolean	processUnseal = false;

	public TEBarrel()
	{
		sealed = false;
		//itemstack = new ItemStack(1,0,0);
		sealtimecounter = 0;
		storage = new ItemStack[12];
	}

	public void careForInventorySlot()
	{
		/*if(Type == 1 && itemstack != null && itemstack.getItem() instanceof ItemTerra)
		{
			if(TFC_ItemHeat.HasTemp(itemstack))
			{
				float temp = TFC_ItemHeat.GetTemp(itemstack);
				if(liquidLevel >= 1 && temp > 1)
				{
					temp -= 100;
					liquidLevel -= 1;
					TFC_ItemHeat.SetTemp(itemstack, temp);
					TFC_ItemHeat.HandleItemHeat(itemstack);
				}
			}
		}*/
	}

	public boolean getSealed()
	{
		return sealed;
	}

	private void ProcessItems()
	{
		if(this.getInvCount() == 0)
		{
			if(getInputStack() != null)
			{
				BarrelRecipe recipe = BarrelManager.getInstance().findMatchingRecipe(getInputStack(), getFluidStack());
				if(recipe != null)
				{
					int sealedTime = (int)TFC_Time.getTotalHours() - sealtimecounter;
					ItemStack origIS = getInputStack().copy();
					FluidStack origFS = getFluidStack().copy();
					if(fluid.isFluidEqual(recipe.getResultFluid(origIS, origFS, sealedTime)))
					{
						fluid.amount -= recipe.getResultFluid(origIS, origFS, sealedTime).amount;
					}
					else
					{
						this.fluid = recipe.getResultFluid(origIS, origFS, sealedTime);
					}
					storage[0] = recipe.getResult(origIS, origFS, sealedTime);

				}
			}
		}
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

	public static String getType(int type)
	{
		switch (type)
		{
		case 0: return StatCollector.translateToLocal("gui.Barrel.Empty");
		case 1: return StatCollector.translateToLocal("gui.Barrel.Water");
		case 2: return StatCollector.translateToLocal("gui.Barrel.Limewater");
		case 3: return StatCollector.translateToLocal("gui.Barrel.Tannin");
		case 4: return StatCollector.translateToLocal("gui.Barrel.Gunpowder");
		case 5: return StatCollector.translateToLocal("gui.Barrel.Beer");
		case 6: return StatCollector.translateToLocal("gui.Barrel.Cider");
		case 7: return StatCollector.translateToLocal("gui.Barrel.Vodka");
		case 8: return StatCollector.translateToLocal("gui.Barrel.Whiskey");
		case 9: return StatCollector.translateToLocal("gui.Barrel.RyeWhiskey");
		case 10: return StatCollector.translateToLocal("gui.Barrel.Sake");
		case 11: return StatCollector.translateToLocal("gui.Barrel.Rum");
		case 12: return StatCollector.translateToLocal("gui.Barrel.Vinegar");
		case 13: return StatCollector.translateToLocal("gui.Barrel.Milk");
		case 14: return StatCollector.translateToLocal("gui.Barrel.CurdledMilk");
		case 15: return StatCollector.translateToLocal("gui.Barrel.SaltWater");
		default: return "";
		}
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
		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 2.0F + 0.4F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;

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

	//FIXME This needs to use blocks and not ids from now on
	public boolean checkValidAddition(int i)
	{
		/*if((i == Type || Type == 0 || (Type == 13 && i == 12)) && !sealed && liquidLevel < 256)
		{
			liquidLevel = Math.min(liquidLevel + 32, 256);
			if(Type == 0 || Type == 13)
			{
				Type = (i == 12 && Type == 13) ? 14 : i;
			}
			updateGui();
			return true;
		}*/
		updateGui();
		return false;
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

	public boolean addLiquid(int amount)
	{
		if(fluid.amount == 10000)
			return false;

		fluid.amount = Math.min(fluid.amount+amount, 10000);
		return true;
	}

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			careForInventorySlot();
			if(sealed)
			{
				if(sealtimecounter == 0)
					sealtimecounter = (int) TFC_Time.getTotalHours();
			}

			if(processUnseal)
			{
				ProcessItems();
				this.processUnseal = false;
			}

			if(fluid != null && fluid.amount == 0)
				fluid = null;

			if(mode == MODE_IN)
			{
				FluidStack inLiquid = FluidContainerRegistry.getFluidForFilledItem(getInputStack());
				if(inLiquid != null)
				{
					if(this.fluid == null)
					{
						this.fluid = inLiquid.copy();
						this.setInventorySlotContents(0, getInputStack().getItem().getContainerItem(getInputStack()));
					}
					else if(inLiquid.isFluidEqual(this.fluid))
					{
						if(addLiquid(inLiquid.amount))
						{
							this.setInventorySlotContents(0, getInputStack().getItem().getContainerItem(getInputStack()));
						}
					}
				}
			}
			else if(mode == MODE_OUT)
			{
				ItemStack out = FluidContainerRegistry.fillFluidContainer(fluid, getInputStack());
				if(out != null && fluid.amount >= 1000)
				{
					this.setInventorySlotContents(0, out);
					this.fluid.amount-=1000;
				}
			}
		}
	}

	public int getLiquidScaled(int i)
	{
		if(fluid != null)
			return fluid.amount * i/10000;
		return 0;
	}

	public boolean actionSeal(EntityPlayer player)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("seal", true);
		nbt.setString("player", player.getDisplayName());
		this.broadcastPacketInRange(this.createDataPacket(nbt));
		sealed = true;
		this.worldObj.func_147479_m(xCoord, yCoord, zCoord);
		return true;
	}

	public boolean actionUnSeal(EntityPlayer player)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("seal", false);
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
		nbt.setInteger("SealTime", sealtimecounter);
		nbt.setInteger("mode", mode);
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
		sealtimecounter = nbt.getInteger("SealTime");
		mode = nbt.getInteger("mode");
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
		fluid = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("fluidNBT"));
		sealed = nbt.getBoolean("sealed");
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
		this.sealed = nbt.getBoolean("sealed");
		this.worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) 
	{
		nbt.setByte("rotation", rotation);
		nbt.setBoolean("sealed", sealed);
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt)
	{
		if(!worldObj.isRemote)
		{
			if(nbt.hasKey("tab"))
			{
				int tab = nbt.getByte("tab");
				if(tab == 0)
					worldObj.getPlayerEntityByName(nbt.getString("player")).openGui(TerraFirmaCraft.instance, 35, worldObj, xCoord, yCoord, zCoord);
				else if(tab == 1)
					worldObj.getPlayerEntityByName(nbt.getString("player")).openGui(TerraFirmaCraft.instance, 36, worldObj, xCoord, yCoord, zCoord);
			}
			else if(nbt.hasKey("fluidID"))
			{
				if(nbt.getByte("fluidID") == -1)
					fluid = null;
			}
			else if(nbt.hasKey("mode"))
			{
				mode = nbt.getByte("mode");
			}
			else if(nbt.hasKey("seal"))
			{
				sealed = nbt.getBoolean("seal");
				if(!sealed)
					processUnseal = true;
				this.sealtimecounter = 0;
				worldObj.getPlayerEntityByName(nbt.getString("player")).openGui(TerraFirmaCraft.instance, 35, worldObj, xCoord, yCoord, zCoord);
			}
		}
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
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.Sugarcane), 160), new FluidStack(TFCFluid.FRESHWATER, 10000), null, new FluidStack(TFCFluid.RUM, 10000)));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(null, new FluidStack(TFCFluid.MILK, 10000), null, new FluidStack(TFCFluid.MILKCURDLED, 10000)));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(null, new FluidStack(TFCFluid.MILKCURDLED, 10000), ItemFoodTFC.createTag(new ItemStack(TFCItems.Cheese), 160), null));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.Logs, 0, 1), new FluidStack(TFCFluid.FRESHWATER, 10000), null, new FluidStack(TFCFluid.TANNIN, 10000)));
	}
}
