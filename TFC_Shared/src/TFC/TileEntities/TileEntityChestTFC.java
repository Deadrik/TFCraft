package TFC.TileEntities;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import TFC.Core.TFC_ItemHeat;

public class TileEntityChestTFC extends TileEntityChest implements IInventory
{
	private ItemStack[] chestContents = new ItemStack[18];

	/** Determines if the check for adjacent chests has taken place. */
	public boolean adjacentChestChecked = false;

	/** Contains the chest tile located adjacent to this one (if any) */
	public TileEntityChestTFC adjacentChestZNeg;
	public TileEntityChestTFC adjacentChestXPos;
	public TileEntityChestTFC adjacentChestXNeg;
	public TileEntityChestTFC adjacentChestZPosition;
	
	/** The current angle of the lid (between 0 and 1) */
	public float lidAngle;

	/** The angle of the lid last tick */
	public float prevLidAngle;

	/** The number of players currently using this chest */
	public int numUsingPlayers;

	/** Server sync counter (once per 20 ticks) */
	private int ticksSinceSync;

	@Override
	public int getSizeInventory() {
		return 18;
	}

	@Override
	public void checkForAdjacentChests()
	{
		if (!this.adjacentChestChecked) {
			this.adjacentChestChecked = true;
			this.adjacentChestZNeg = null;
			this.adjacentChestXPos = null;
			this.adjacentChestXNeg = null;
			this.adjacentChestZPosition = null;

			if (this.worldObj.getBlockId(this.xCoord - 1, this.yCoord, this.zCoord) == Block.chest.blockID) {
				this.adjacentChestXNeg = (TileEntityChestTFC)this.worldObj.getBlockTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
			}
			if (this.worldObj.getBlockId(this.xCoord + 1, this.yCoord, this.zCoord) == Block.chest.blockID) {
				this.adjacentChestXPos = (TileEntityChestTFC)this.worldObj.getBlockTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
			}
			if (this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord - 1) == Block.chest.blockID) {
				this.adjacentChestZNeg = (TileEntityChestTFC)this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
			}
			if (this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord + 1) == Block.chest.blockID) {
				this.adjacentChestZPosition = (TileEntityChestTFC)this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
			}

			if (this.adjacentChestZNeg != null) {
				this.adjacentChestZNeg.updateContainingBlockInfo();
			}
			if (this.adjacentChestZPosition != null) {
				this.adjacentChestZPosition.updateContainingBlockInfo();
			}
			if (this.adjacentChestXPos != null) {
				this.adjacentChestXPos.updateContainingBlockInfo();
			}
			if (this.adjacentChestXNeg != null) {
				this.adjacentChestXNeg.updateContainingBlockInfo();
			}
		}
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		TFC_ItemHeat.HandleContainerHeatChest(this.worldObj, chestContents, xCoord, yCoord, zCoord);
		this.checkForAdjacentChests();

		if (++this.ticksSinceSync % 20 * 4 == 0) {
		}

		this.prevLidAngle = this.lidAngle;
		float var1 = 0.1F;
		double var4;

		if (this.numUsingPlayers > 0 && this.lidAngle == 0.0F && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
			double var2 = this.xCoord + 0.5D;
			var4 = this.zCoord + 0.5D;

			if (this.adjacentChestZPosition != null) {
				var4 += 0.5D;
			}

			if (this.adjacentChestXPos != null) {
				var2 += 0.5D;
			}

			this.worldObj.playSoundEffect(var2, this.yCoord + 0.5D, var4, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}

		if (this.numUsingPlayers == 0 && this.lidAngle > 0.0F || this.numUsingPlayers > 0 && this.lidAngle < 1.0F) {
			float var8 = this.lidAngle;

			if (this.numUsingPlayers > 0) {
				this.lidAngle += var1;
			}
			else {
				this.lidAngle -= var1;
			}

			if (this.lidAngle > 1.0F) {
				this.lidAngle = 1.0F;
			}

			float var3 = 0.5F;

			if (this.lidAngle < var3 && var8 >= var3 && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
				var4 = this.xCoord + 0.5D;
				double var6 = this.zCoord + 0.5D;

				if (this.adjacentChestZPosition != null) {
					var6 += 0.5D;
				}

				if (this.adjacentChestXPos != null) {
					var4 += 0.5D;
				}

				this.worldObj.playSoundEffect(var4, this.yCoord + 0.5D, var6, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}

			if (this.lidAngle < 0.0F) {
				this.lidAngle = 0.0F;
			}
		}
	}
	
	@Override
	public boolean receiveClientEvent(int par1, int par2) {
		if (par1 == 1) {
			this.numUsingPlayers = par2;
		}
		return true;
	}
	
	@Override
	public void openChest() {
		++this.numUsingPlayers;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, Block.chest.blockID, 1, this.numUsingPlayers);
	}
	
	@Override
	public void closeChest() {
		--this.numUsingPlayers;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, Block.chest.blockID, 1, this.numUsingPlayers);
	}
	
	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return false;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		Block type = getBlockType();
		return AxisAlignedBB.getAABBPool().getAABB(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
	}
}
