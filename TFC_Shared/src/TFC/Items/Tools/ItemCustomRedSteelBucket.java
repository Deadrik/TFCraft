package TFC.Items.Tools;

import TFC.*;
import TFC.Core.Helper;
import TFC.Entities.Mobs.EntityCowTFC;
import TFC.Enums.EnumSize;
import TFC.Items.ItemTerra;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class ItemCustomRedSteelBucket extends ItemTerra
{
	/** field for checking if the bucket has been filled. */
	private int isFull;

	public ItemCustomRedSteelBucket(int par1, int par2)
	{
		super(par1);
		this.isFull = par2;
		this.setFolder("tools/");
	}

	@Override
	public EnumSize getSize() {
		// TODO Auto-generated method stub
		return EnumSize.LARGE;
	}

	@Override
	public boolean canStack() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer par3EntityPlayer)
	{
		float var4 = 1.0F;
		double var5 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * (double)var4;
		double var7 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * (double)var4 + 1.62D - (double)par3EntityPlayer.yOffset;
		double var9 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * (double)var4;
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

					if (world.getBlockMaterial(i, j, k) == Material.water && world.getBlockMetadata(i, j, k) == 0)
                    {
                        world.setBlock(i, j, k, 0);

                        if (par3EntityPlayer.capabilities.isCreativeMode)
                        {
                            return par1ItemStack;
                        }

                        if (--par1ItemStack.stackSize <= 0)
                        {
                            return new ItemStack(TFCItems.RedSteelBucketWater);
                        }

                        if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(TFCItems.RedSteelBucketWater)))
                        {
                            par3EntityPlayer.dropPlayerItem(new ItemStack(TFCItems.RedSteelBucketWater.itemID, 1, 0));
                        }

                        return par1ItemStack;
                    }
				}
				else
				{
					if (this.isFull < 0)
					{
						return new ItemStack(TFCItems.RedSteelBucketEmpty);
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

					if (this.handleWaterInHell(world, var5, var7, var9, i, j, k) && !par3EntityPlayer.capabilities.isCreativeMode)
					{
						return new ItemStack(TFCItems.RedSteelBucketEmpty);
					}
				}
			}
		}
		return par1ItemStack;
	}

	public boolean handleWaterInHell(World par1World, double par2, double par4, double par6, int par8, int par9, int par10)
	{
		if (this.isFull <= 0)
		{
			return false;
		}
		else if (!par1World.isAirBlock(par8, par9, par10) && par1World.getBlockMaterial(par8, par9, par10).isSolid())
		{
			return false;
		}
		else
		{
			if (par1World.provider.isHellWorld && this.isFull == Block.waterMoving.blockID)
			{
				par1World.playSoundEffect(par2 + 0.5D, par4 + 0.5D, par6 + 0.5D, "random.fizz", 0.5F, 2.6F + (par1World.rand.nextFloat() - par1World.rand.nextFloat()) * 0.8F);

				for (int var11 = 0; var11 < 8; ++var11)
				{
					par1World.spawnParticle("largesmoke", (double)par8 + Math.random(), (double)par9 + Math.random(), (double)par10 + Math.random(), 0.0D, 0.0D, 0.0D);
				}
			}
			else
			{
				par1World.setBlock(par8, par9, par10, this.isFull);
			}

			return true;
		}
	}

}
