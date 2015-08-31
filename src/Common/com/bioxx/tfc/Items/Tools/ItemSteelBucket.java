package com.bioxx.tfc.Items.Tools;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;

import cpw.mods.fml.common.eventhandler.Event;

import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Util.Helper;

public class ItemSteelBucket extends ItemTerra
{
	protected Block bucketContents;

	public ItemSteelBucket(Block par2)
	{
		super();
		this.bucketContents = par2;
		this.setFolder("tools/");
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.LARGE;
	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		//float var4 = 1.0F;
		boolean var11 = this.bucketContents == Blocks.air;
		MovingObjectPosition mop = Helper.getMovingObjectPositionFromPlayer(world, player, var11);

		if (mop == null)
		{
			return is;
		}
		else
		{
			FillBucketEvent event = new FillBucketEvent(player, is, world, mop);
			if (MinecraftForge.EVENT_BUS.post(event))
			{
				return is;
			}

			if (event.getResult() == Event.Result.ALLOW)
			{
				if (player.capabilities.isCreativeMode)
				{
					return is;
				}

				if (--is.stackSize <= 0)
				{
					return event.result;
				}

				if (!player.inventory.addItemStackToInventory(event.result))
				{
					player.dropPlayerItemWithRandomChoice(event.result, false);
				}

				return is;
			}
			if (mop.typeOfHit == MovingObjectType.BLOCK)
			{
				int i = mop.blockX;
				int j = mop.blockY;
				int k = mop.blockZ;



				if (!world.canMineBlock(player, i, j, k))
				{
					return is;
				}

				if (this.bucketContents == Blocks.air)
				{
					if (!player.canPlayerEdit(i, j, k, mop.sideHit, is) || !(world.getBlock(i, j, k) instanceof IFluidBlock))
					{
						return is;
					}

					Fluid fluid = ((IFluidBlock)world.getBlock(i, j, k)).getFluid();
					if (fluid != null)
					{
						ItemStack filledIS = FluidContainerRegistry.fillFluidContainer(new FluidStack(fluid, 1000), is);
						if (filledIS != null)
						{
							world.setBlockToAir(i, j, k);

							if (--is.stackSize <= 0)
							{
								return filledIS;
							}

							if (!player.inventory.addItemStackToInventory(filledIS))
							{
								player.entityDropItem(filledIS, 0);
							}

							return is;
						}
					}
				}
				else
				{
					if (this.bucketContents == Blocks.air)
					{
						return this.getContainerItem(is);
					}

					if (mop.sideHit == 0) --j;
					if (mop.sideHit == 1) ++j;
					if (mop.sideHit == 2) --k;
					if (mop.sideHit == 3) ++k;
					if (mop.sideHit == 4) --i;
					if (mop.sideHit == 5) ++i;

					if (!player.canPlayerEdit(i, j, k, mop.sideHit, is))
					{
						return is;
					}

					if (this.tryPlaceContainedLiquid(world, i, j, k) && !player.capabilities.isCreativeMode)
					{
						return this.getContainerItem(is);
					}
				}
			}
		}
		return is;
	}

	/**
	 * Attempts to place the liquid contained inside the bucket.
	 */
	public boolean tryPlaceContainedLiquid(World world, int x, int y, int z)
	{
		if (this.bucketContents == Blocks.air)
		{
			return false;
		}
		else
		{
			Material material = world.getBlock(x, y, z).getMaterial();
			boolean flag = !material.isSolid();

			if (!world.isAirBlock(x, y, z) && !flag)
			{
				return false;
			}
			else
			{
				if (world.provider.isHellWorld && (this.bucketContents == TFCBlocks.freshWater || this.bucketContents == TFCBlocks.saltWater))
				{
					world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

					for (int l = 0; l < 8; ++l)
					{
						world.spawnParticle("largesmoke", x + Math.random(), y + Math.random(), z + Math.random(), 0.0D, 0.0D, 0.0D);
					}
				}
				else
				{
					if (!world.isRemote && flag && !material.isLiquid())
					{
						world.func_147480_a(x, y, z, true);
					}

					world.setBlock(x, y, z, this.bucketContents, 0, 3);
				}

				return true;
			}
		}
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.SHORT;
	}
}
