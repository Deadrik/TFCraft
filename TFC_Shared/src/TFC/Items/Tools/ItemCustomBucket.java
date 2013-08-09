package TFC.Items.Tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.API.Entities.IAnimal.GenderEnum;
import TFC.API.Enums.EnumSize;
import TFC.Core.Helper;
import TFC.Entities.Mobs.EntityCowTFC;
import TFC.Items.ItemTerra;
import TFC.TileEntities.TileEntityBarrel;

public class ItemCustomBucket extends ItemTerra
{
	/** field for checking if the bucket has been filled. */
	private int isFull;

	public ItemCustomBucket(int par1, int par2)
	{
		super(par1);
		this.isFull = par2;
		this.setFolder("tools/");
	}

	@Override
	public EnumSize getSize() {
		// TODO Auto-generated method stub
		return EnumSize.MEDIUM;
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

					if ((world.getBlockId(i, j, k) == Block.waterStill.blockID || world.getBlockId(i, j, k) == TFCBlocks.finiteWater.blockID) && world.getBlockMetadata(i, j, k) <=2)
					{
						world.setBlock(i, j, k, 0);

						if (par3EntityPlayer.capabilities.isCreativeMode)
						{
							return par1ItemStack;
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

					if(world.getBlockId(i,j,k)==TFCBlocks.Barrel.blockID){
						TileEntityBarrel te = (TileEntityBarrel)world.getBlockTileEntity(i, j, k);
						if(te.checkValidAddition(this.isFull)){
							return new ItemStack(TFCItems.WoodenBucketEmpty);
						}
					}

					if (var12.sideHit == 0)
					{
						--j;
					}
					else if (var12.sideHit == 1)
					{
						++j;
					}
					else if (var12.sideHit == 2)
					{
						--k;
					}
					else if (var12.sideHit == 3)
					{
						++k;
					}
					else if (var12.sideHit == 4)
					{
						--i;
					}
					else if (var12.sideHit == 5)
					{
						++i;
					}



					if (!par3EntityPlayer.canPlayerEdit(i, j, k, var12.sideHit, par1ItemStack))
					{
						return par1ItemStack;
					}

					if (world.isAirBlock(i, j, k) || !world.getBlockMaterial(i, j, k).isSolid())
					{
						if (world.provider.isHellWorld && this.isFull == TFCBlocks.finiteWater.blockID)
						{
							world.playSoundEffect(var5 + 0.5D, var7 + 0.5D, var9 + 0.5D, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

							for (int var16 = 0; var16 < 8; ++var16)
							{
								world.spawnParticle("largesmoke", i + Math.random(), j + Math.random(), k + Math.random(), 0.0D, 0.0D, 0.0D);
							}
						}
						else
						{
							if(world.getBlockId(i, j, k) == TFCBlocks.finiteWater.blockID)
							{
								int bucketMeta = 0;
								int blockMeta = world.getBlockMetadata(i, j, k);
								if(blockMeta > 0)
								{
									bucketMeta = bucketMeta + blockMeta;
									world.setBlock(i, j, k, TFCBlocks.finiteWater.blockID);
									world.setBlock(i, j+1, k, TFCBlocks.finiteWater.blockID, bucketMeta, 0x02);
								}
								else
								{
									world.setBlock(i, j, k, TFCBlocks.finiteWater.blockID, 0, 0x02);
								}
							}
							else if(this.isFull != 1)
							{
								world.setBlock(i, j, k, this.isFull);
							}
						}

						if (par3EntityPlayer.capabilities.isCreativeMode)
						{
							return par1ItemStack;
						}

						return new ItemStack(TFCItems.WoodenBucketEmpty);
					}
				}
			}
			else if (this.isFull == 0 && var12.entityHit instanceof EntityCowTFC && ((EntityCowTFC)var12.entityHit).getGender() == GenderEnum.FEMALE)
			{
				return new ItemStack(TFCItems.WoodenBucketMilk);
			}

			return par1ItemStack;
		}
	}
}
