package com.bioxx.tfc.Items.ItemBlocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.TileEntities.TEPottery;
import com.bioxx.tfc.TileEntities.TEVessel;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.IEquipable;
import com.bioxx.tfc.api.Util.Helper;

public class ItemLargeVessel extends ItemTerraBlock implements IEquipable
{
	private static final int MAX_LIQUID = 5000;

	public ItemLargeVessel(Block block)
	{
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
		this.setCreativeTab(TFCTabs.TFC_POTTERY);
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.LARGE;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.HEAVY;
	}

	@Override
	public int getItemStackLimit(ItemStack is)
	{
		if(is.hasTagCompound())
			return 1;
		return super.getItemStackLimit(is);
	}

	public void createTooltip(NBTTagCompound nbt, List<String> arraylist)
	{
		if(nbt != null)
		{
			boolean addFluid = false;
			if(nbt.hasKey("fluidNBT"))
			{
				FluidStack fluid = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("fluidNBT"));
				if( fluid != null )
				{
					addFluid = true;
					arraylist.add(EnumChatFormatting.BLUE + fluid.getFluid().getLocalizedName(fluid));
				}
			}

			if(!addFluid && nbt.hasKey("Items") )
			{
				NBTTagList nbttaglist = nbt.getTagList("Items", 10);
				if( nbttaglist != null )
				{
					int numItems = nbttaglist.tagCount();
					boolean showMoreText = false;
					if( numItems > 4 && !TFC_Core.showShiftInformation())
					{
						numItems = 3;
						showMoreText = true;
					}
					for( int i = 0; i < numItems; i++ )
					{
						NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
						if( nbttagcompound1 != null )
						{
							ItemStack onlyItem = ItemStack.loadItemStackFromNBT(nbttagcompound1);
							if( onlyItem != null )
							{
								arraylist.add(EnumChatFormatting.GOLD + Integer.toString(onlyItem.stackSize)+"x " + onlyItem.getDisplayName() );
							}
						}
					}
					if( showMoreText )
					{
						arraylist.add(TFC_Core.translate("gui.Barrel.MoreItems"));
					}
				}
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);
		createTooltip(is.getTagCompound(), arraylist);
		if (TFC_Core.showShiftInformation())
		{
			arraylist.add(TFC_Core.translate("gui.Help"));
			arraylist.add(TFC_Core.translate("gui.PotteryBase.Inst0"));
		}
		else
			arraylist.add(TFC_Core.translate("gui.ShowHelp"));
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		MovingObjectPosition mop = Helper.getMovingObjectPositionFromPlayer(world, player, true);

		if (mop == null)
		{
			return super.onItemUse(is, player, world, x, y, z, side, hitX, hitY, hitZ);
		}
		else
		{
			if (mop.typeOfHit == MovingObjectType.BLOCK)
			{
				int i = mop.blockX;
				int j = mop.blockY;
				int k = mop.blockZ;

				if (!player.canPlayerEdit(i, j, k, mop.sideHit, is) || !(world.getBlock(i, j, k) instanceof IFluidBlock) || is.hasTagCompound() || is.getItemDamage() == 0)
				{
					return super.onItemUse(is, player, world, x, y, z, side, hitX, hitY, hitZ);
				}

				Fluid fluid = ((IFluidBlock) world.getBlock(i, j, k)).getFluid();
				int temp = fluid.getTemperature();
				int volume = 0;

				if (temp < Global.HOT_LIQUID_TEMP && fluid != TFCFluids.HOTWATER)
				{
					world.setBlockToAir(i, j, k);
					if (fluid == TFCFluids.FRESHWATER || fluid == TFCFluids.SALTWATER)
					{
						volume = MAX_LIQUID;
					}
					else
					{
						volume = 1000;
					}

					if (is.stackSize == 1)
					{
						ItemBarrels.fillItemBarrel(is, new FluidStack(fluid, volume), MAX_LIQUID);
					}
					else
					{
						is.stackSize--;
						ItemStack outIS = is.copy();
						outIS.stackSize = 1;
						ItemBarrels.fillItemBarrel(outIS, new FluidStack(fluid, volume), MAX_LIQUID);
						if (!player.inventory.addItemStackToInventory(outIS))
						{
							player.entityDropItem(outIS, 0);
						}
					}
				}
				return true;
			}
		}

		return super.onItemUse(is, player, world, x, y, z, side, hitX, hitY, hitZ);
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{
		if(metadata > 0)
		{
			if (!world.setBlock(x, y, z, field_150939_a, metadata&15, 3))
			{
				return false;
			}

			if (world.getBlock(x, y, z) == field_150939_a)
			{
				field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
				field_150939_a.onPostBlockPlaced(world, x, y, z, 0);

				TEVessel te = (TEVessel) world.getTileEntity(x, y, z);
				te.barrelType = metadata;
				return true;
			}
		}
		else if(metadata == 0 && side == 1 && player.isSneaking())
		{
			Block base = world.getBlock(x, y-1, z);
			if(base != TFCBlocks.pottery && world.isAirBlock(x, y, z))
			{
				//We only want the pottery to be placeable if the block is solid on top.
				if(!world.isSideSolid(x, y-1, z, ForgeDirection.UP))
					return false;
				world.setBlock(x, y, z, TFCBlocks.pottery);
			}
			else
			{
				return false;
			}

			if (world.getTileEntity(x, y, z) instanceof TEPottery)
			{
				TEPottery te = (TEPottery) world.getTileEntity(x, y, z);
				if(te.canAddItem(0))
				{
					te.inventory[0] = stack.copy();
					te.inventory[0].stackSize = 1;
					world.markBlockForUpdate(x, y, z);
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public EquipType getEquipType(ItemStack is)
	{
		return EquipType.BACK;
	}

	@Override
	public void onEquippedRender()
	{
		GL11.glTranslatef(0, 0.0f, -0.2F);
	}

	@Override
	public boolean getTooHeavyToCarry(ItemStack is)
	{
		return is.hasTagCompound();
	}
}