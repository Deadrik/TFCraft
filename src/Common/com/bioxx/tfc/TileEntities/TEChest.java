package com.bioxx.tfc.TileEntities;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Containers.ContainerChestTFC;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;

public class TEChest extends TileEntityChest implements IInventory
{
	private ItemStack[] chestContents = new ItemStack[18];
	public int type;
	/** Server sync counter (once per 20 ticks) */
	private int ticksSinceSync;
	public int cooldown = 5;
	private boolean typeSynced;
	public boolean isDoubleChest;

	public TEChest()
	{

	}

	public TEChest(int i, boolean isDouble)
	{
		type = i;
		isDoubleChest = isDouble;
	}

	@Override
	public int getSizeInventory()
	{
		return 18;
	}

	@Override
	public ItemStack getStackInSlot(int par1)
	{
		return this.chestContents[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (this.chestContents[par1] != null)
		{
			ItemStack var3;
			if (this.chestContents[par1].stackSize <= par2)
			{
				var3 = this.chestContents[par1];
				this.chestContents[par1] = null;
				this.markDirty();
				return var3;
			}
			else
			{
				var3 = this.chestContents[par1].splitStack(par2);
				if (this.chestContents[par1].stackSize == 0)
					this.chestContents[par1] = null;
				this.markDirty();
				return var3;
			}
		}
		else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (this.chestContents[par1] != null)
		{
			ItemStack var2 = this.chestContents[par1];
			this.chestContents[par1] = null;
			return var2;
		}
		else
			return null;
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		this.chestContents[par1] = par2ItemStack;
		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		this.markDirty();
	}

	@Override
	public String getInventoryName()
	{
		return "container.chest";
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		NBTTagList var2 = nbt.getTagList("Items", 10);
		this.chestContents = new ItemStack[this.getSizeInventory()];
		this.type = nbt.getInteger("woodtype");
		for (int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = var2.getCompoundTagAt(var3);
			int var5 = var4.getByte("Slot") & 255;
			if (var5 >= 0 && var5 < this.chestContents.length)
				this.chestContents[var5] = ItemStack.loadItemStackFromNBT(var4);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagList var2 = new NBTTagList();

		for (int var3 = 0; var3 < this.chestContents.length; ++var3)
		{
			if (this.chestContents[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte)var3);
				this.chestContents[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}

		nbt.setTag("Items", var2);
		nbt.setInteger("woodtype", type);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("woodtype", type);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		type = pkt.func_148857_g().getInteger("woodtype");
		// Reset the check flag in case the client has desynced from the server
		this.adjacentChestChecked = false;
		this.typeSynced = true;
		this.cooldown = 0;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	public boolean checkType(IBlockAccess access, int x, int y, int z)
	{
		TileEntity te =  access.getTileEntity(x, y, z);
		if (te instanceof TEChest)
		{
			TEChest chest = (TEChest) access.getTileEntity(x, y, z);
			if (chest.type == this.type && this.cooldown == 0 && chest.cooldown == 0)
				return true;
		}
		return false;
	}

	@Override
	public void checkForAdjacentChests()
	{
		if (!this.adjacentChestChecked)
		{
			this.adjacentChestChecked = true;
			this.adjacentChestZNeg = null;
			this.adjacentChestXPos = null;
			this.adjacentChestXNeg = null;
			this.adjacentChestZPos = null;

			if (isChest(xCoord - 1, yCoord, zCoord))
				this.adjacentChestXNeg = (TEChest)this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);

			if (isChest(xCoord + 1, yCoord, zCoord))
				this.adjacentChestXPos = (TEChest)this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);

			if (isChest(xCoord, yCoord, zCoord - 1))
				this.adjacentChestZNeg = (TEChest)this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);

			if (isChest(xCoord, yCoord, zCoord + 1))
				this.adjacentChestZPos = (TEChest)this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);

			/*
			* Use updateSide because this method runs every render tick and 
			* there's no need to also edit the blockType and blockMetadata
			*/
			if (this.adjacentChestZNeg != null)
			{
				((TEChest) this.adjacentChestZNeg).updateSide(this, 0);
			}

			if (this.adjacentChestZPos != null)
			{
				((TEChest) this.adjacentChestZPos).updateSide(this, 2);
			}

			if (this.adjacentChestXPos != null)
			{
				((TEChest) this.adjacentChestXPos).updateSide(this, 1);
			}

			if (this.adjacentChestXNeg != null)
			{
				((TEChest) this.adjacentChestXNeg).updateSide(this, 3);
			}
		}
	}

	private boolean isChest(int x, int y, int z)
	{
		return this.worldObj.getBlock(x, y, z) == TFCBlocks.chest && checkType(worldObj, x, y, z);
	}

	private void updateSide(TileEntityChest teChest, int side)
	{
		if (teChest.isInvalid())
		{
			this.adjacentChestChecked = false;
		}
		else if (this.adjacentChestChecked)
		{
			switch (side)
			{
			case 0:
				if (this.adjacentChestZPos != teChest)
				{
					this.adjacentChestChecked = false;
				}

				break;
			case 1:
				if (this.adjacentChestXNeg != teChest)
				{
					this.adjacentChestChecked = false;
				}

				break;
			case 2:
				if (this.adjacentChestZNeg != teChest)
				{
					this.adjacentChestChecked = false;
				}

				break;
			case 3:
				if (this.adjacentChestXPos != teChest)
				{
					this.adjacentChestChecked = false;
				}
				break;
			}
		}
	}

	@Override
	public void updateEntity()
	{
		//super.updateEntity();

		TFC_Core.handleItemTicking(this, this.worldObj, xCoord, yCoord, zCoord);

		if (type == 0 && !typeSynced) // Cooldown only required for oak chests
		{
			if (cooldown == 0)
			{
				this.adjacentChestChecked = false;
				this.isDoubleChest = false;
				typeSynced = true;
			}
			else if (cooldown > 0)
			{
				cooldown--;
			}
		}
		else if (cooldown != 0)
		{
			cooldown = 0;
		}

		this.checkForAdjacentChests();
		this.ticksSinceSync++;

		if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0)
		{
			this.numPlayersUsing = 0;
			float f = 5.0F;
			List list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(this.xCoord - f, this.yCoord - f, this.zCoord - f, this.xCoord + 1 + f, this.yCoord + 1 + f, this.zCoord + 1 + f));
			Iterator iterator = list.iterator();

			while (iterator.hasNext())
			{
				EntityPlayer entityplayer = (EntityPlayer)iterator.next();

				if (entityplayer.openContainer instanceof ContainerChestTFC)
				{
					IInventory iinventory = ((ContainerChestTFC)entityplayer.openContainer).getLowerChestInventory();

					if (iinventory == this || iinventory instanceof InventoryLargeChest && ((InventoryLargeChest)iinventory).isPartOfLargeChest(this))
					{
						++this.numPlayersUsing;
					}
				}
			}
		}

		this.prevLidAngle = this.lidAngle;
		float var1 = 0.1F;
		double var4;

		if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null)
		{
			double var2 = this.xCoord + 0.5D;
			var4 = this.zCoord + 0.5D;
			if (this.adjacentChestZPos != null)
				var4 += 0.5D;
			if (this.adjacentChestXPos != null)
				var2 += 0.5D;
			this.worldObj.playSoundEffect(var2, this.yCoord + 0.5D, var4, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}

		if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F)
		{
			if (this.numPlayersUsing > 0)
				this.lidAngle += var1;
			else
				this.lidAngle -= var1;

			if (this.lidAngle > 1.0F)
				this.lidAngle = 1.0F;

			float var8 = this.lidAngle;
			float var3 = 0.5F;
			if (this.lidAngle < var3 && var8 >= var3 && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null)
			{
				var4 = this.xCoord + 0.5D;
				double var6 = this.zCoord + 0.5D;
				if (this.adjacentChestZPos != null)
					var6 += 0.5D;
				if (this.adjacentChestXPos != null)
					var4 += 0.5D;
				this.worldObj.playSoundEffect(var4, this.yCoord + 0.5D, var6, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}

			if (this.lidAngle < 0.0F)
				this.lidAngle = 0.0F;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
	}

	@Override
	public void openInventory()
	{
		++this.numPlayersUsing;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, TFCBlocks.chest, 1, this.numPlayersUsing);
	}

	@Override
	public void closeInventory()
	{
		--this.numPlayersUsing;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, TFCBlocks.chest, 1, this.numPlayersUsing);
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return true;
	}
}
