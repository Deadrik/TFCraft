package TFC.Items.Tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.API.Entities.IAnimal.GenderEnum;
import TFC.API.Enums.EnumSize;
import TFC.API.Util.Helper;
import TFC.Entities.Mobs.EntityCowTFC;
import TFC.Items.ItemTerra;
import TFC.TileEntities.TileEntityBarrel;
import cpw.mods.fml.common.eventhandler.Event;

public class ItemCustomBucket extends ItemTerra
{
	/** field for checking if the bucket has been filled. */
	private int isFull;

	public ItemCustomBucket(int par2)
	{
		super();
		this.isFull = par2;
		this.setFolder("tools/");
		this.setSize(EnumSize.MEDIUM);
	}

	@Override
	public boolean canStack() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer par3EntityPlayer)
	{
		float var4 = 1.0F;
		double var5 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * var4;
		double var7 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * var4 + 1.62D - par3EntityPlayer.yOffset;
		double var9 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * var4;
		boolean var11 = this.isFull == 0;
		MovingObjectPosition var12 = Helper.getMovingObjectPositionFromPlayer(world, par3EntityPlayer, var11);

		if (var12 == null)
		{
			return is;
		}
		else
		{
			if (var12.typeOfHit == MovingObjectType.BLOCK)
			{
				int i = var12.blockX;
				int j = var12.blockY;
				int k = var12.blockZ;

				if (!world.canMineBlock(par3EntityPlayer, i, j, k))
				{
					return is;
				}

				if (this.isFull == 0)
				{
					if (!par3EntityPlayer.canPlayerEdit(i, j, k, var12.sideHit, is))
					{
						return is;
					}

					FillBucketEvent event = new FillBucketEvent(par3EntityPlayer, is, world, var12);
					if (event.isCanceled())
					{
						return is;
					}

					if (event.getResult() == Event.Result.ALLOW)
					{
						return event.result;
					}

					if ((world.getBlock(i, j, k) == Blocks.water) && world.getBlockMetadata(i, j, k) <=2)
					{
						world.setBlockToAir(i, j, k);

						if (par3EntityPlayer.capabilities.isCreativeMode)
						{
							return is;
						}

						return new ItemStack(TFCItems.WoodenBucketWater);
					}
				}
				else
				{
					if (this.isFull < 0)
					{
						return new ItemStack(TFCItems.WoodenBucketEmpty);
					}

					if(world.getBlock(i,j,k)==TFCBlocks.Barrel)
					{
						TileEntityBarrel te = (TileEntityBarrel)world.getTileEntity(i, j, k);
						if(te.checkValidAddition(this.isFull))
						{
							return new ItemStack(TFCItems.WoodenBucketEmpty);
						}
					}
					return new ItemStack(TFCItems.WoodenBucketEmpty);
				}
			}
			else if (this.isFull == 0 && var12.entityHit instanceof EntityCowTFC && ((EntityCowTFC)var12.entityHit).getGender() == GenderEnum.FEMALE)
			{
				return new ItemStack(TFCItems.WoodenBucketMilk);
			}

			return is;
		}
	}
}
