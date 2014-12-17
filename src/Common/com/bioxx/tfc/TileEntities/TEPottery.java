package com.bioxx.tfc.TileEntities;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Metal.Alloy;
import com.bioxx.tfc.Items.Pottery.ItemPotteryBase;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Crafting.KilnCraftingManager;
import com.bioxx.tfc.api.Crafting.KilnRecipe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TEPottery extends NetworkTileEntity implements IInventory
{
	public ItemStack inventory[];
	public boolean hasRack;
	public long burnStart = 0;
	public int straw = 0;
	public int wood = 0;

	public TEPottery()
	{
		inventory = new ItemStack[12];
		hasRack = false;
	}

	public boolean canAddItem(int slot)
	{
		if(inventory[0] != null && inventory[0].getItem() instanceof ItemBlock)
		{
			return false;
		}
		if(inventory[slot] != null)
			return false;
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord +1, yCoord + 1, zCoord + 1);
		return bb;
	}

	@Override
	public void updateEntity()
	{
		TFC_Core.handleItemTicking(this, worldObj, xCoord, yCoord, zCoord);
		//If there are no logs for burning then we dont need to tick at all

		if(!worldObj.isRemote && straw > 0 && wood < 8)
		{
			//If there is a fire nearby and the pitkiln is not valid
			//eject all items and remove the TiteEntity
			if(isFireNear())
			{
				ejectContents();
				worldObj.removeTileEntity(xCoord, yCoord, zCoord);
				worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}

		if(!worldObj.isRemote && wood == 8)
		{
			Block blockAbove = worldObj.getBlock(xCoord, yCoord + 1, zCoord);

			//If there is fire nearby, the pitkiln will also catch fire and start the burn countdown
			if(burnStart == 0 && isFireNear())
				StartPitFire();

			//Make sure to keep the fire going throughout the length of the burn
			if(blockAbove != Blocks.fire && TFC_Time.getTotalTicks() - burnStart < TFC_Time.hourLength * TFCOptions.pitKilnBurnTime)
			{
				if ((blockAbove.isAir(worldObj, xCoord, yCoord + 1, zCoord) || worldObj.getBlock(xCoord, yCoord + 1, zCoord).getMaterial().getCanBurn()) && isValid())
					worldObj.setBlock(xCoord, yCoord + 1, zCoord, Blocks.fire);
				else
				{
					wood = 0;
					inventory[4] = null;inventory[5] = null;inventory[6] = null;inventory[7] = null;
					inventory[8] = null;inventory[9] = null;inventory[10] = null;inventory[11] = null;
					straw = 0;
					burnStart = 0;
				}
			}

			//If the total time passes then we complete the burn and turn the clay into ceramic
			if(blockAbove == Blocks.fire && TFC_Time.getTotalTicks() > burnStart + (TFCOptions.pitKilnBurnTime * TFC_Time.hourLength))
			{
				worldObj.setBlockToAir(xCoord, yCoord + 1, zCoord);
				if(inventory[0] != null)
				{
					inventory[0] = KilnCraftingManager.getInstance().findCompleteRecipe(new KilnRecipe(inventory[0], 0)).copy();
					if(inventory[0].getItem() instanceof ItemPotteryBase)
						((ItemPotteryBase)inventory[0].getItem()).onDoneCooking(worldObj, inventory[0], Alloy.EnumTier.TierI);
				}
				if(inventory[1] != null)
				{
					inventory[1] = KilnCraftingManager.getInstance().findCompleteRecipe(new KilnRecipe(inventory[1], 0)).copy();
					if(inventory[1].getItem() instanceof ItemPotteryBase)
						((ItemPotteryBase)inventory[1].getItem()).onDoneCooking(worldObj, inventory[1], Alloy.EnumTier.TierI);
				}
				if(inventory[2] != null)
				{
					inventory[2] = KilnCraftingManager.getInstance().findCompleteRecipe(new KilnRecipe(inventory[2], 0)).copy();
					if(inventory[2].getItem() instanceof ItemPotteryBase)
						((ItemPotteryBase)inventory[2].getItem()).onDoneCooking(worldObj, inventory[2], Alloy.EnumTier.TierI);
				}
				if(inventory[3] != null)
				{
					inventory[3] = KilnCraftingManager.getInstance().findCompleteRecipe(new KilnRecipe(inventory[3], 0)).copy();
					if(inventory[3].getItem() instanceof ItemPotteryBase)
						((ItemPotteryBase)inventory[3].getItem()).onDoneCooking(worldObj, inventory[3], Alloy.EnumTier.TierI);
				}

				wood = 0;
				inventory[4] = null;inventory[5] = null;inventory[6] = null;inventory[7] = null;
				inventory[8] = null;inventory[9] = null;inventory[10] = null;inventory[11] = null;
				straw = 0;
				burnStart = 0;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
	}

	private Boolean isFireNear()
	{
		Boolean foundFire = false;

		for(int x = -1; x <= 1; x++)
		{
			for(int z = -1; z <= 1; z++)
			{
				if(worldObj.getBlock(xCoord + x, yCoord + 1, zCoord + z) == Blocks.fire)
					foundFire = true;
			}
		}

		return foundFire;
	}

	public void StartPitFire()
	{
		if(straw == 8 && wood == 8)
		{
			worldObj.setBlock(xCoord, yCoord + 1, zCoord, Blocks.fire);
			burnStart = TFC_Time.getTotalTicks();
		}
	}

	public void addLog(ItemStack is, EntityPlayer player)
	{
		if(wood < 8)
		{
			if (!player.capabilities.isCreativeMode)
			{
				for (int i = 4; i < 12; i++)
				{
					if (this.inventory[i] == null)
					{
						wood++;
						ItemStack _is = is.copy();
						is.stackSize--;
						_is.stackSize = 1;
						this.setInventorySlotContents(i, _is);
						worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
						break;
					}
				}
			}
			else
			{
				for (int i = 4; i < 12; i++)
				{
					if (this.inventory[i] == null)
					{
						wood++;
						ItemStack _is = is.copy();
						_is.stackSize = 1;
						this.setInventorySlotContents(i, _is);
					}
				}
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
	}

	public void addStraw(ItemStack is, EntityPlayer player)
	{
		if(straw < 8)
		{
			if (!player.capabilities.isCreativeMode)
			{
				straw++;
				is.stackSize--;
			}
			else
			{
				straw = 8;
			}
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	public boolean isValid()
	{
		boolean surroundSolids = TFC_Core.isNorthFaceSolid(worldObj, xCoord, yCoord, zCoord - 1) &&
				TFC_Core.isSouthFaceSolid(worldObj, xCoord, yCoord, zCoord + 1) &&
				TFC_Core.isEastFaceSolid(worldObj, xCoord - 1, yCoord, zCoord) &&
				TFC_Core.isWestFaceSolid(worldObj, xCoord + 1, yCoord, zCoord);
		return surroundSolids && worldObj.isSideSolid(xCoord, yCoord - 1, zCoord, ForgeDirection.UP);
	}

	public boolean isLit()
	{
		if (TFC_Time.getTotalTicks() > burnStart && TFC_Time.getTotalTicks() - burnStart < TFC_Time.hourLength * TFCOptions.pitKilnBurnTime)
			return true;
		else
			return false;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		inventory[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
			itemstack.stackSize = getInventoryStackLimit();
	}

	public void ejectContents()
	{
		float f3 = 0.05F;
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.8F;
		float f1 = rand.nextFloat() * 0.4F;
		float f2 = rand.nextFloat() * 0.8F;

		for (int i = 0; i < getSizeInventory(); i++)
		{
			if(inventory[i]!= null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, inventory[i]);
				entityitem.motionX = (float)rand.nextGaussian() * f3;
				entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float)rand.nextGaussian() * f3;
				worldObj.spawnEntityInWorld(entityitem);
				inventory[i] = null;
			}
		}

		if(straw > 0)
		{
			entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, new ItemStack(TFCItems.Straw, straw));
			entityitem.motionX = (float)rand.nextGaussian() * f3;
			entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
			entityitem.motionZ = (float)rand.nextGaussian() * f3;
			worldObj.spawnEntityInWorld(entityitem);
		}
	}

	public void ejectItem(int index)
	{
		EntityItem entityitem;
		new Random();

		if(inventory[index] != null)
		{
			entityitem = new EntityItem(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, inventory[index]);
			entityitem.lifespan = 48000;
			worldObj.spawnEntityInWorld(entityitem);
			inventory[index] = null;
		}

		if(inventory[0] == null && inventory[1] == null && inventory[2] == null && inventory[3] == null)
		{
			// eject straw before destroying block
			int m = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			if(m > 0)
			{
				entityitem = new EntityItem(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, new ItemStack(TFCItems.Straw, m));
				entityitem.lifespan = 48000;
				worldObj.spawnEntityInWorld(entityitem);
			}
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
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
	public int getSizeInventory()
	{
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return inventory[i];
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
	public ItemStack decrStackSize(int i, int j)
	{
		return null;
	}

	@Override
	public String getInventoryName()
	{
		return "Pottery";
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public void closeInventory()
	{
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < inventory.length; i++)
		{
			if(inventory[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Items", nbttaglist);
		nbttagcompound.setLong("burnStart", burnStart);
		nbttagcompound.setBoolean("hasRack", hasRack);
		nbttagcompound.setInteger("wood", wood);
		nbttagcompound.setInteger("straw", straw);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		inventory = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < inventory.length)
				inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
		burnStart = nbt.getLong("burnStart");
		wood = nbt.getInteger("wood");
		hasRack = nbt.getBoolean("hasRack");
		straw = nbt.getInteger("straw");
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt)
	{
		this.readFromNBT(nbt);
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void createDataNBT(NBTTagCompound nbt)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt)
	{
		/*Normally this would be the wrong approach to sending init data. However for the pottery since we have to sync a few items
		 *as well as some vars I am making an exception to keep it simple.*/
		this.writeToNBT(nbt);
	}

}
