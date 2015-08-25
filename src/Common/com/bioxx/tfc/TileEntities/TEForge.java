package com.bioxx.tfc.TileEntities;

import java.util.Random;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.ItemBloom;
import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.api.*;
import com.bioxx.tfc.api.Enums.EnumFuelMaterial;
import com.bioxx.tfc.api.Interfaces.ISmeltable;
import com.bioxx.tfc.api.TileEntities.TEFireEntity;

public class TEForge extends TEFireEntity implements IInventory
{
	public boolean isSmokeStackValid;
	public ItemStack fireItemStacks[];
	private int smokeTimer = 0;

	public TEForge()
	{
		super();
		fuelTimeLeft = 200;
		fuelBurnTemp = 200;
		fireTemp = 20;
		isSmokeStackValid = false;
		fireItemStacks = new ItemStack[14];
		maxFireTempScale = 2500;
	}

	private boolean validateSmokeStack()
	{

		if (directChimney(worldObj.getPrecipitationHeight(xCoord, zCoord) - 1))
			return true;
		else if(!worldObj.getBlock(xCoord + 1, yCoord + 1, zCoord).isOpaqueCube() && worldObj.canBlockSeeTheSky(xCoord + 1, yCoord + 1, zCoord))
			return true;
		else if(!worldObj.getBlock(xCoord - 1, yCoord + 1, zCoord).isOpaqueCube() && worldObj.canBlockSeeTheSky(xCoord - 1, yCoord + 1, zCoord))
			return true;
		else if(!worldObj.getBlock(xCoord, yCoord + 1, zCoord + 1).isOpaqueCube() && worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord + 1))
			return true;
		else if(!worldObj.getBlock(xCoord, yCoord + 1, zCoord - 1).isOpaqueCube() && worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord - 1))
			return true;
		else if(!worldObj.getBlock(xCoord + 1, yCoord + 1, zCoord).isOpaqueCube() && !worldObj.getBlock(xCoord+2, yCoord + 1, zCoord).isOpaqueCube() &&
				worldObj.canBlockSeeTheSky(xCoord+2, yCoord + 1, zCoord))
			return true;
		else if(!worldObj.getBlock(xCoord - 1, yCoord + 1, zCoord).isOpaqueCube() && !worldObj.getBlock(xCoord-2, yCoord + 1, zCoord).isOpaqueCube() &&
				worldObj.canBlockSeeTheSky(xCoord-2, yCoord + 1, zCoord))
			return true;
		else if(!worldObj.getBlock(xCoord, yCoord + 1, zCoord + 1).isOpaqueCube() && !worldObj.getBlock(xCoord, yCoord + 1, zCoord+2).isOpaqueCube() &&
				worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord+2))
			return true;
		else
			return !worldObj.getBlock(xCoord, yCoord + 1, zCoord - 1).isOpaqueCube() &&!worldObj.getBlock(xCoord, yCoord + 1, zCoord - 2).isOpaqueCube() &&
					worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord - 2);
	}

	private boolean directChimney(int highestY)
	{
		boolean isBlocked = false;
		if (worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord)) // Either no blocks, or transparent blocks above.
		{
			// Glass blocks, or blocks with a solid top or bottom block the chimney.
			if (worldObj.getBlock(xCoord, highestY, zCoord) instanceof BlockGlass
					|| worldObj.getBlock(xCoord, highestY, zCoord) instanceof BlockStainedGlass
					|| worldObj.isSideSolid(xCoord, highestY, zCoord, ForgeDirection.UP) 
					|| worldObj.isSideSolid(xCoord, highestY, zCoord, ForgeDirection.DOWN))
				isBlocked = true;
		}
		else // Can't see the sky, chimney is blocked
			isBlocked = true;

		return !worldObj.isRaining() && !isBlocked; // Direct chimney is valid when it is not blocked and it isn't raining.
	}

	private void genSmokeRoot(int x, int y, int z)
	{
		if(fuelTimeLeft >= 0)
		{
			if(worldObj.getBlock(x,y,z) != TFCBlocks.Smoke)
				worldObj.setBlock(x, y, z, TFCBlocks.Smoke);
		}
		else
		{
			worldObj.setBlockToAir(x, y, z);
		}
	}

	/*private class CoordDirection
	{
		int x; int y; int z; ForgeDirection dir;
		public CoordDirection(int x, int y, int z, ForgeDirection d)
		{
			this.x = x;this.y = y;this.z = z;this.dir = d;
		}
	}*/

	@Override
	public void closeInventory()
	{
	}

	public void combineMetals(ItemStack InputItem, ItemStack DestItem)
	{
		int D1 = 100 - InputItem.getItemDamage();
		int D2 = 100 - DestItem.getItemDamage();
		DestItem.setItemDamage(100 - (D1 + D2));
	}

	public void CookItem(int i)
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		Random R = new Random();
		if(fireItemStacks[i] != null)
		{
			HeatIndex index = manager.findMatchingIndex(fireItemStacks[i]);
			ItemStack inputCopy = fireItemStacks[i].copy();
			
			if(index != null && TFC_ItemHeat.getTemp(fireItemStacks[i]) > index.meltTemp)
			{
				float temperature = TFC_ItemHeat.getTemp(fireItemStacks[i]);
				//int dam = fireItemStacks[i].getItemDamage();

				// If not unshaped metal, morph the input to the output. If not an input with direct morph (sand, sticks, etc) this deletes the input item from the slot.
				if(!(fireItemStacks[i].getItem() instanceof ItemMeltedMetal))
					fireItemStacks[i] = index.getMorph();

				// Handle items that had a direct morph.
				if(fireItemStacks[i] != null)
				{
					HeatIndex morphIndex = manager.findMatchingIndex(fireItemStacks[i]);
					if(morphIndex != null)
					{
						// Apply old temperature to direct morphs that can continue to be heated.
						TFC_ItemHeat.setTemp(fireItemStacks[i], temperature);
					}
				}
				else if(index.hasOutput())
				{
					ItemStack output = index.getOutput(inputCopy, R);
					if (inputCopy.getItem() instanceof ISmeltable)
					{
						ISmeltable smelt = (ISmeltable)inputCopy.getItem();
						ItemStack meltedItem = new ItemStack(smelt.getMetalType(inputCopy).MeltedItem);
						TFC_ItemHeat.setTemp(meltedItem, temperature);

						int units = smelt.getMetalReturnAmount(inputCopy);
						// Raw/Refined Blooms give at max 100 units to force players to split using the anvil
						if (inputCopy.getItem() instanceof ItemBloom)
							units = Math.min(100, units);

						while(units > 0 && getMold() != null)
						{
							ItemStack moldIS = this.getMold();
							ItemStack outputCopy = meltedItem.copy();

							if (units > 100)
							{
								units-= 100;
								moldIS.stackSize--;
								if(!addToStorage(outputCopy.copy()))
								{
									EntityItem ei = new EntityItem(worldObj, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, outputCopy);
									ei.motionX = 0; ei.motionY = 0; ei.motionZ = 0;
									worldObj.spawnEntityInWorld(ei);
								}
							}
							else if (units > 0) // Put the last item in the forge cooking slot, replacing the input.
							{
								outputCopy.setItemDamage(100-units);
								units = 0;
								moldIS.stackSize--;
								fireItemStacks[i] = outputCopy.copy();
							}
						}
					}
					else
					{
						fireItemStacks[i] = output;
					}


					if(TFC_ItemHeat.isCookable(fireItemStacks[i]) > -1)
					{
						//if the input is a new item, then apply the old temperature to it
						TFC_ItemHeat.setTemp(fireItemStacks[i], temperature);
					}
				}
			}
		}
	}

	public boolean addToStorage(ItemStack is)
	{
		if(this.getStackInSlot(10) == null)
		{
			this.setInventorySlotContents(10, is);
			return true;
		}
		if(this.getStackInSlot(11) == null)
		{
			this.setInventorySlotContents(11, is);
			return true;
		}
		if(this.getStackInSlot(12) == null)
		{
			this.setInventorySlotContents(12, is);
			return true;
		}
		if(this.getStackInSlot(13) == null)
		{
			this.setInventorySlotContents(13, is);
			return true;
		}
		return false;
	}

	private ItemStack getMold()
	{
		if(fireItemStacks[10] != null && fireItemStacks[10].getItem() == TFCItems.CeramicMold && fireItemStacks[10].stackSize > 0)
		{
			return fireItemStacks[10];
		}
		else if(fireItemStacks[11] != null && fireItemStacks[11].getItem() == TFCItems.CeramicMold && fireItemStacks[11].stackSize > 0)
		{
			return fireItemStacks[11];
		}
		else if(fireItemStacks[12] != null && fireItemStacks[12].getItem() == TFCItems.CeramicMold && fireItemStacks[12].stackSize > 0)
		{
			return fireItemStacks[12];
		}
		else if(fireItemStacks[13] != null && fireItemStacks[13].getItem() == TFCItems.CeramicMold && fireItemStacks[13].stackSize > 0)
		{
			return fireItemStacks[13];
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(fireItemStacks[i] != null)
		{
			if(fireItemStacks[i].stackSize <= j)
			{
				ItemStack is = fireItemStacks[i];
				fireItemStacks[i] = null;
				return is;
			}

			ItemStack isSplit = fireItemStacks[i].splitStack(j);
			if(fireItemStacks[i].stackSize == 0)
				fireItemStacks[i] = null;
			return isSplit;
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
		float f1 = rand.nextFloat() * 0.8F + 0.4F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;

		for (int i = 0; i < getSizeInventory(); i++)
		{
			if(fireItemStacks[i]!= null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, fireItemStacks[i]);
				entityitem.motionX = (float)rand.nextGaussian() * f3;
				entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float)rand.nextGaussian() * f3;
				worldObj.spawnEntityInWorld(entityitem);
				fireItemStacks[i] = null;
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
		return "Forge";
	}

	public int getMoldIndex()
	{
		if(fireItemStacks[10] != null && fireItemStacks[10].getItem() == TFCItems.CeramicMold)
			return 10;
		if(fireItemStacks[11] != null && fireItemStacks[11].getItem() == TFCItems.CeramicMold)
			return 11;
		if(fireItemStacks[12] != null && fireItemStacks[12].getItem() == TFCItems.CeramicMold)
			return 12;
		if(fireItemStacks[13] != null && fireItemStacks[13].getItem() == TFCItems.CeramicMold)
			return 13;
		return -1;
	}

	@Override
	public int getSizeInventory()
	{
		return fireItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return fireItemStacks[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		return null;
	}

	public void HandleFuelStack()
	{
		Random random = new Random();
		if(fireItemStacks[7] == null)
		{
			if(random.nextBoolean() && fireItemStacks[6] != null)
			{
				fireItemStacks[7] = fireItemStacks[6];
				fireItemStacks[6] = null;
			}
			else
			{
				fireItemStacks[7] = fireItemStacks[8];
				fireItemStacks[8] = null;
			}
		}

		if(fireItemStacks[6] == null)
		{
			if(fireItemStacks[5] != null)
			{
				fireItemStacks[6] = fireItemStacks[5];
				fireItemStacks[5] = null;
			}
		}

		if(fireItemStacks[8] == null)
		{
			if(fireItemStacks[9] != null)
			{
				fireItemStacks[8] = fireItemStacks[9];
				fireItemStacks[9] = null;
			}
		}
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
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		isSmokeStackValid = nbt.getBoolean("isValid");

		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		fireItemStacks = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbt1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbt1.getByte("Slot");
			if(byte0 >= 0 && byte0 < fireItemStacks.length)
				fireItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbt1);
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		fireItemStacks[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
			itemstack.stackSize = getInventoryStackLimit();
	}

	@Override
	public void updateEntity()
	{
		//Here we make sure that the forge is valid
		isSmokeStackValid = validateSmokeStack();

		if(!worldObj.isRemote)
		{
			//Here we take care of the items that we are cooking in the fire
			careForInventorySlot(fireItemStacks[0]);
			careForInventorySlot(fireItemStacks[1]);
			careForInventorySlot(fireItemStacks[2]);
			careForInventorySlot(fireItemStacks[3]);
			careForInventorySlot(fireItemStacks[4]);

			ItemStack[] FuelStack = new ItemStack[9];
			FuelStack[0] = fireItemStacks[5];
			FuelStack[1] = fireItemStacks[6];
			FuelStack[2] = fireItemStacks[7];
			FuelStack[3] = fireItemStacks[8];
			FuelStack[4] = fireItemStacks[9];
			FuelStack[5] = fireItemStacks[10];
			FuelStack[6] = fireItemStacks[11];
			FuelStack[7] = fireItemStacks[12];
			FuelStack[8] = fireItemStacks[13];

			//Now we cook the input item
			CookItem(0);
			CookItem(1);
			CookItem(2);
			CookItem(3);
			CookItem(4);

			//push the input fuel down the stack
			HandleFuelStack();

			//Play the fire sound
			Random R = new Random();
			if(R.nextInt(10) == 0 && fireTemp > 20)
				worldObj.playSoundEffect(xCoord, yCoord, zCoord, "fire.fire", 0.4F + (R.nextFloat() / 2), 0.7F + R.nextFloat());

			if(fireTemp >= 20 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 1)
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
			else if(fireTemp < 20 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 0)
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 3);

			//If the fire is still burning and has fuel
			if(fuelTimeLeft > 0 && fireTemp >= 1 && (!worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord) || !worldObj.isRaining()))
			{
				float desiredTemp = handleTemp();
				handleTempFlux(desiredTemp);
				smokeTimer++;
				if(smokeTimer > 60)
				{
					smokeTimer = 0;
					createSmoke();
				}
				if(TFCOptions.enableDebugMode)
				{
					fireTemp = 2000;
					fuelTimeLeft = 9999;
				}

				TFC_Core.handleItemTicking(FuelStack, worldObj, xCoord, yCoord, zCoord);
			}
			else if(fuelTimeLeft <= 0 && fireTemp >= 1 && fireItemStacks[7] != null && isSmokeStackValid)
			{
				//here we set the temp and burn time based on the fuel in the bottom slot.
				EnumFuelMaterial m = TFC_Core.getFuelMaterial(fireItemStacks[7]);
				fuelTimeLeft = m.burnTimeMax;
				fuelBurnTemp = m.burnTempMax;
				fuelTasteProfile = m.ordinal();
				fireItemStacks[7] = null;
			}
			else
			{
				removeSmoke();

				handleTempFlux(0);
				TFC_Core.handleItemTicking(this, worldObj, xCoord, yCoord, zCoord);
			}

			//Here we handle the bellows
			handleAirReduction();

			//do a last minute check to verify stack size
			for(int c = 0; c < 5; c++)
			{
				if(fireItemStacks[c] != null)
				{
					if(fireItemStacks[c].stackSize <= 0)
						fireItemStacks[c].stackSize = 1;
				}
			}
		}
	}

	private void createSmoke()
	{
		if(!TFCOptions.generateSmoke)
			return;
		if(worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord) && !worldObj.isRaining())
			genSmokeRoot(xCoord, yCoord+1, zCoord);
		else if(!worldObj.getBlock(xCoord + 1, yCoord + 1, zCoord).isOpaqueCube() && worldObj.canBlockSeeTheSky(xCoord + 1, yCoord + 1, zCoord))
			genSmokeRoot(xCoord+1, yCoord+1, zCoord);
		else if(!worldObj.getBlock(xCoord - 1, yCoord + 1, zCoord).isOpaqueCube() && worldObj.canBlockSeeTheSky(xCoord - 1, yCoord + 1, zCoord))
			genSmokeRoot(xCoord-1, yCoord+1, zCoord);
		else if(!worldObj.getBlock(xCoord, yCoord + 1, zCoord + 1).isOpaqueCube() && worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord + 1))
			genSmokeRoot(xCoord, yCoord+1, zCoord+1);
		else if(!worldObj.getBlock(xCoord, yCoord + 1, zCoord - 1).isOpaqueCube() && worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord - 1))
			genSmokeRoot(xCoord, yCoord+1, zCoord-1);
		else if(!worldObj.getBlock(xCoord + 2, yCoord + 1, zCoord).isOpaqueCube() && !worldObj.getBlock(xCoord+2, yCoord + 1, zCoord).isOpaqueCube() &&
				worldObj.canBlockSeeTheSky(xCoord+2, yCoord + 1, zCoord))
			genSmokeRoot(xCoord+2, yCoord+1, zCoord);
		else if(!worldObj.getBlock(xCoord - 2, yCoord + 1, zCoord).isOpaqueCube() && !worldObj.getBlock(xCoord-2, yCoord + 1, zCoord).isOpaqueCube() &&
				worldObj.canBlockSeeTheSky(xCoord-2, yCoord + 1, zCoord))
			genSmokeRoot(xCoord-2, yCoord+1, zCoord);
		else if(!worldObj.getBlock(xCoord, yCoord + 1, zCoord + 2).isOpaqueCube() && !worldObj.getBlock(xCoord, yCoord + 1, zCoord+2).isOpaqueCube() &&
				worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord+2))
			genSmokeRoot(xCoord, yCoord+1, zCoord+2);
		else if(!worldObj.getBlock(xCoord, yCoord + 1, zCoord - 2).isOpaqueCube() && !worldObj.getBlock(xCoord, yCoord + 1, zCoord-2).isOpaqueCube() &&
				worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord-2))
			genSmokeRoot(xCoord, yCoord+1, zCoord-2);
	}

	public void removeSmoke() {
		if(worldObj.getBlock(xCoord, yCoord+1, zCoord) == TFCBlocks.Smoke)
			worldObj.setBlockToAir(xCoord, yCoord+1, zCoord);
		else if(worldObj.getBlock(xCoord+1, yCoord+1, zCoord) == TFCBlocks.Smoke)
			worldObj.setBlockToAir(xCoord+1, yCoord+1, zCoord);
		else if(worldObj.getBlock(xCoord-1, yCoord+1, zCoord) == TFCBlocks.Smoke)
			worldObj.setBlockToAir(xCoord-1, yCoord+1, zCoord);
		else if(worldObj.getBlock(xCoord, yCoord+1, zCoord+1) == TFCBlocks.Smoke)
			worldObj.setBlockToAir(xCoord, yCoord+1, zCoord+1);
		else if(worldObj.getBlock(xCoord, yCoord+1, zCoord-1) == TFCBlocks.Smoke)
			worldObj.setBlockToAir(xCoord, yCoord+1, zCoord-1);
		else if(worldObj.getBlock(xCoord+2, yCoord+1, zCoord) == TFCBlocks.Smoke)
			worldObj.setBlockToAir(xCoord+2, yCoord+1, zCoord);
		else if(worldObj.getBlock(xCoord-2, yCoord+1, zCoord) == TFCBlocks.Smoke)
			worldObj.setBlockToAir(xCoord-2, yCoord+1, zCoord);
		else if(worldObj.getBlock(xCoord, yCoord+1, zCoord+2) == TFCBlocks.Smoke)
			worldObj.setBlockToAir(xCoord, yCoord+1, zCoord+2);
		else if(worldObj.getBlock(xCoord, yCoord+1, zCoord-2) == TFCBlocks.Smoke)
			worldObj.setBlockToAir(xCoord, yCoord+1, zCoord-2);
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
		nbt.setBoolean("isValid", isSmokeStackValid);

		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < fireItemStacks.length; i++)
		{
			if(fireItemStacks[i] != null)
			{
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("Slot", (byte)i);
				fireItemStacks[i].writeToNBT(nbt1);
				nbttaglist.appendTag(nbt1);
			}
		}
		nbt.setTag("Items", nbttaglist);
	}
}
