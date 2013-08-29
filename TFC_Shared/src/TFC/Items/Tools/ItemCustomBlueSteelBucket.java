package TFC.Items.Tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import TFC.TFCItems;
import TFC.API.Enums.EnumSize;
import TFC.API.Util.Helper;
import TFC.Items.ItemTerra;

public class ItemCustomBlueSteelBucket extends ItemTerra
{
	/** field for checking if the bucket has been filled. */
	private int isFull;

	public ItemCustomBlueSteelBucket(int par1, int par2)
	{
		super(par1);
		this.isFull = par2;
		this.setFolder("tools/");
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.LARGE;
	}

	@Override
	public boolean canStack() {
		return false;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer par3EntityPlayer)
	{
		float var4 = 1.0F;
		double var5 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * var4;
		double var7 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * var4 + 1.62D - par3EntityPlayer.yOffset;
		double var9 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * var4;
		boolean var11 = this.isFull == 0;
		MovingObjectPosition var12 = Helper.getMovingObjectPositionFromPlayer(world, par3EntityPlayer, var11);

		if (var12 == null)
		{
			return par1ItemStack;
		}
		else
		{
			if (var12.typeOfHit == EnumMovingObjectType.TILE)
			{
				int i = var12.blockX;
				int j = var12.blockY;
				int k = var12.blockZ;

				if (!world.canMineBlock(par3EntityPlayer, i, j, k))
				{
					return par1ItemStack;
				}

				if (this.isFull == 0)
				{
					if (!par3EntityPlayer.canPlayerEdit(i, j, k, var12.sideHit, par1ItemStack))
					{
						return par1ItemStack;
					}

					FillBucketEvent event = new FillBucketEvent(par3EntityPlayer, par1ItemStack, world, var12);
					if (event.isCanceled())
					{
						return par1ItemStack;
					}

					if (event.getResult() == Event.Result.ALLOW)
					{
						return event.result;
					}

					if (world.getBlockId(i, j, k) == Block.lavaStill.blockID && world.getBlockMetadata(i, j, k) == 0)
					{
						world.setBlock(i, j, k, 0);

						if (par3EntityPlayer.capabilities.isCreativeMode)
						{
							return par1ItemStack;
						}

						if (--par1ItemStack.stackSize <= 0)
						{
							return new ItemStack(TFCItems.BlueSteelBucketLava);
						}

						if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(TFCItems.BlueSteelBucketLava)))
						{
							par3EntityPlayer.dropPlayerItem(new ItemStack(TFCItems.BlueSteelBucketLava.itemID, 1, 0));
						}

						return par1ItemStack;
					}
				}
				else
				{
					if (this.isFull < 0)
					{
						return new ItemStack(TFCItems.BlueSteelBucketEmpty);
					}

					if (var12.sideHit == 0)
					{
						--j;
					}

					if (var12.sideHit == 1)
					{
						++j;
					}

					if (var12.sideHit == 2)
					{
						--k;
					}

					if (var12.sideHit == 3)
					{
						++k;
					}

					if (var12.sideHit == 4)
					{
						--i;
					}

					if (var12.sideHit == 5)
					{
						++i;
					}

					if (!par3EntityPlayer.canPlayerEdit(i, j, k, var12.sideHit, par1ItemStack))
					{
						return par1ItemStack;
					}

					if (this.handleLava(world, var5, var7, var9, i, j, k) && !par3EntityPlayer.capabilities.isCreativeMode)
					{
						return new ItemStack(TFCItems.BlueSteelBucketEmpty);
					}
				}
			}
		}
		return par1ItemStack;
	}

	public boolean handleLava(World par1World, double par2, double par4, double par6, int par8, int par9, int par10)
	{
		if (this.isFull <= 0) {
			return false;
		}
		else if (!par1World.isAirBlock(par8, par9, par10) && par1World.getBlockMaterial(par8, par9, par10).isSolid()) {
			return false;
		}
		else {
			par1World.setBlock(par8, par9, par10, this.isFull);
			return true;
		}
	}

}
