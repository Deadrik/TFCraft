package com.bioxx.tfc.Items.ItemBlocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.TileEntities.TEBarrel;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.IEquipable;
import com.bioxx.tfc.api.Util.Helper;

public class ItemBarrels extends ItemTerraBlock implements IEquipable
{
	private static final int MAX_LIQUID = 10000;

	public ItemBarrels(Block par1)
	{
		super(par1);
		setMaxDamage(0);
		setHasSubtypes(true);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
		this.metaNames = Global.WOOD_ALL;
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

	public void readFromItemNBT(NBTTagCompound nbt, List<String> arraylist)
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

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);
		readFromItemNBT(is.getTagCompound(), arraylist);
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

				if (!player.canPlayerEdit(i, j, k, mop.sideHit, is) || !(world.getBlock(i, j, k) instanceof IFluidBlock) || is.hasTagCompound())
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

		if (!world.setBlock(x, y, z, field_150939_a, metadata&15, 3))
		{
			return false;
		}

		if (world.getBlock(x, y, z) == field_150939_a)
		{
			field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
			field_150939_a.onPostBlockPlaced(world, x, y, z, 0);

			TEBarrel te = (TEBarrel) world.getTileEntity(x, y, z);
			te.barrelType = metadata;
		}

		return true;
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < metaNames.length; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}

	@Override
	public EquipType getEquipType(ItemStack is)
	{
		return EquipType.BACK;
	}

	@Override
	public void onEquippedRender()
	{
		GL11.glTranslatef(0, -0.3f, -0F);
	}

	@Override
	public boolean getTooHeavyToCarry(ItemStack is)
	{
		return is.hasTagCompound() && is.getTagCompound().hasKey("Sealed");
	}

	public static ItemStack fillItemBarrel(ItemStack is, FluidStack fs, int maxFluid)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		if(is.hasTagCompound())
		{
			nbt = is.getTagCompound();
		}

		if(nbt.hasKey("Sealed"))
			return is;

		//Attempt to merge fluidstacks first
		if(nbt.hasKey("fluidNBT"))
		{
			FluidStack ifs = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("fluidNBT"));
			if(ifs.isFluidEqual(fs) && ifs.amount < maxFluid)
			{
				ifs.amount += fs.amount;
				fs.amount = ifs.amount % maxFluid;
				ifs.amount = Math.min(ifs.amount, maxFluid);
				nbt.setTag("fluidNBT", ifs.writeToNBT(new NBTTagCompound()));
				nbt.setBoolean("Sealed", true);
				nbt.setInteger("SealTime", (int)TFC_Time.getTotalHours());
			}
			else return is;
		}
		else
		{
			nbt.setTag("fluidNBT", fs.writeToNBT(new NBTTagCompound()));
			nbt.setBoolean("Sealed", true);
			nbt.setInteger("SealTime", (int)TFC_Time.getTotalHours());
		}

		is.setTagCompound(nbt);
		return is;
	}
}