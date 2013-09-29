package TFC.TileEntities;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityChest;
import TFC.Core.TFC_ItemHeat;
public class TileEntityChestTFC extends TileEntityChest implements IInventory
{
	private ItemStack[] chestContents = new ItemStack[18];

	/** Server sync counter (once per 20 ticks) */
	private int ticksSinceSync;

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
				this.onInventoryChanged();
				return var3;
			}
			else
			{
				var3 = this.chestContents[par1].splitStack(par2);

				if (this.chestContents[par1].stackSize == 0)
				{
					this.chestContents[par1] = null;
				}

				this.onInventoryChanged();
				return var3;
			}
		}
		else
		{
			return null;
		}
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
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		this.chestContents[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	@Override
	public String getInvName()
	{
		return "container.chest";
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
		this.chestContents = new ItemStack[this.getSizeInventory()];

		for (int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
			int var5 = var4.getByte("Slot") & 255;

			if (var5 >= 0 && var5 < this.chestContents.length)
			{
				this.chestContents[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
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

		par1NBTTagCompound.setTag("Items", var2);
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void updateContainingBlockInfo()
	{
		super.updateContainingBlockInfo();
		this.adjacentChestChecked = false;
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
			this.adjacentChestZPosition = null;

			if (this.worldObj.getBlockId(this.xCoord - 1, this.yCoord, this.zCoord) == Block.chest.blockID)
			{
				this.adjacentChestXNeg = (TileEntityChestTFC)this.worldObj.getBlockTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
			}

			if (this.worldObj.getBlockId(this.xCoord + 1, this.yCoord, this.zCoord) == Block.chest.blockID)
			{
				this.adjacentChestXPos = (TileEntityChestTFC)this.worldObj.getBlockTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
			}

			if (this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord - 1) == Block.chest.blockID)
			{
				this.adjacentChestZNeg = (TileEntityChestTFC)this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
			}

			if (this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord + 1) == Block.chest.blockID)
			{
				this.adjacentChestZPosition = (TileEntityChestTFC)this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
			}

			if (this.adjacentChestZNeg != null)
			{
				this.adjacentChestZNeg.updateContainingBlockInfo();
			}

			if (this.adjacentChestZPosition != null)
			{
				this.adjacentChestZPosition.updateContainingBlockInfo();
			}

			if (this.adjacentChestXPos != null)
			{
				this.adjacentChestXPos.updateContainingBlockInfo();
			}

			if (this.adjacentChestXNeg != null)
			{
				this.adjacentChestXNeg.updateContainingBlockInfo();
			}
		}
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		TFC_ItemHeat.HandleContainerHeatChest(this.worldObj, chestContents, xCoord, yCoord, zCoord);

		this.checkForAdjacentChests();

		if (++this.ticksSinceSync % 20 * 4 == 0)
		{

		}

		this.prevLidAngle = this.lidAngle;
		float var1 = 0.1F;
		double var4;

		if (this.numUsingPlayers > 0 && this.lidAngle == 0.0F && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null)
		{
			double var2 = this.xCoord + 0.5D;
			var4 = this.zCoord + 0.5D;

			if (this.adjacentChestZPosition != null)
			{
				var4 += 0.5D;
			}

			if (this.adjacentChestXPos != null)
			{
				var2 += 0.5D;
			}

			this.worldObj.playSoundEffect(var2, this.yCoord + 0.5D, var4, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}

		if (this.numUsingPlayers == 0 && this.lidAngle > 0.0F || this.numUsingPlayers > 0 && this.lidAngle < 1.0F)
		{
			float var8 = this.lidAngle;

			if (this.numUsingPlayers > 0)
			{
				this.lidAngle += var1;
			}
			else
			{
				this.lidAngle -= var1;
			}

			if (this.lidAngle > 1.0F)
			{
				this.lidAngle = 1.0F;
			}

			float var3 = 0.5F;

			if (this.lidAngle < var3 && var8 >= var3 && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null)
			{
				var4 = this.xCoord + 0.5D;
				double var6 = this.zCoord + 0.5D;

				if (this.adjacentChestZPosition != null)
				{
					var6 += 0.5D;
				}

				if (this.adjacentChestXPos != null)
				{
					var4 += 0.5D;
				}

				this.worldObj.playSoundEffect(var4, this.yCoord + 0.5D, var6, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}

			if (this.lidAngle < 0.0F)
			{
				this.lidAngle = 0.0F;
			}
		}
	}

	@Override
	public boolean receiveClientEvent(int par1, int par2)
	{
		if (par1 == 1)
		{
			this.numUsingPlayers = par2;
		}
		return true;
	}
	@Override
	public void openChest()
	{
		++this.numUsingPlayers;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, Block.chest.blockID, 1, this.numUsingPlayers);
	}
	@Override
	public void closeChest()
	{
		--this.numUsingPlayers;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, Block.chest.blockID, 1, this.numUsingPlayers);
	}

	@Override
	public void invalidate()
	{
		this.updateContainingBlockInfo();
		this.checkForAdjacentChests();
		super.invalidate();
	}

	@Override
	public boolean isInvNameLocalized() 
	{
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) 
	{
		return true;
	}
}
