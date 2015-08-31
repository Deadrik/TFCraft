package com.bioxx.tfc.Blocks.Devices;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.fluids.FluidStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Items.ItemBlocks.ItemOilLamp;
import com.bioxx.tfc.TileEntities.TEOilLamp;
import com.bioxx.tfc.api.TFCBlocks;

public class BlockOilLamp extends BlockTerraContainer
{
	private IIcon[] icons;
	public BlockOilLamp()
	{
		super(Material.circuits);
		this.setTickRandomly(true);
		this.setCreativeTab(TFCTabs.TFC_DECORATION);
		setLightLevel(1.0F);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if(meta >= 8)
			return 0;
		return getLightValue();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < 6; i++)
		{
			list.add(ItemOilLamp.getFullLamp(i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		int m = meta & 7;
		if(side == 0 || side == 1)
		{
			if(m == 0)
				return TFC_Textures.sheetGold;
			else if(m == 1)
				return TFC_Textures.sheetPlatinum;
			else if(m == 2)
				return TFC_Textures.sheetRoseGold;
			else if(m == 3)
				return TFC_Textures.sheetSilver;
			else if(m == 4)
				return TFC_Textures.sheetSterlingSilver;
			else if(m == 5)
				return TFC_Textures.sheetBlueSteel;
			else return TFC_Textures.sheetGold;
		}
		else return icons[m];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int i, int j, int k, int side)
	{
		return getIcon(side, access.getBlockMetadata(i, j, k));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registerer)
	{
		icons = new IIcon[6];
		icons[0] = registerer.registerIcon(Reference.MOD_ID + ":" + "metal/GoldLamp");
		icons[1] = registerer.registerIcon(Reference.MOD_ID + ":" + "metal/PlatinumLamp");
		icons[2] = registerer.registerIcon(Reference.MOD_ID + ":" + "metal/RoseGoldLamp");
		icons[3] = registerer.registerIcon(Reference.MOD_ID + ":" + "metal/SilverLamp");
		icons[4] = registerer.registerIcon(Reference.MOD_ID + ":" + "metal/SterlingSilverLamp");
		icons[5] = registerer.registerIcon(Reference.MOD_ID + ":" + "metal/BlueSteelLamp");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(x, y, z);
			if(!isLampLit(meta))
			{
				TEOilLamp te = (TEOilLamp) world.getTileEntity(x, y, z);
				if (te != null)
				{
					te.updateLampFuel(false); // Update lamp fuel, but don't burn fuel for time passed while lamp was off before turning the lamp on.
					if(te.isFuelValid())
						if(te.getFuelTimeLeft() > 0)
							world.setBlockMetadataWithNotify(x, y, z, meta-8, 3);
				}
			}
			else
			{
				TEOilLamp te = (TEOilLamp) world.getTileEntity(x, y, z);
				if (te != null)
				{
					te.updateLampFuel(true); // Update lamp fuel and burn fuel for time passed before turning the lamp off.
				}
				world.setBlockMetadataWithNotify(x, y, z, meta+8, 3);
			}
		}
		return true;
	}

	public static boolean isLampLit(int meta)
	{
		return (meta & 8) <= 0;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		return new ArrayList<ItemStack>();
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEOilLamp();
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return TFCBlocks.oilLampRenderId;
	}

	private boolean canSupportTorch(World world, int x, int y, int z)
	{
		if (World.doesBlockHaveSolidTopSurface(world, x, y, z))
		{
			return true;
		}
		else
		{
			Block block = world.getBlock(x, y, z);
			return block.canPlaceTorchOnTop(world, x, y, z);
		}
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		return canSupportTorch(world, x, y - 1, z);
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		super.updateTick(world, x, y, z, rand);
		int meta = world.getBlockMetadata(x, y, z);
		if(meta < 8)
		{
			TEOilLamp te = (TEOilLamp) world.getTileEntity(x, y, z);
			if (te != null)
			{
				te.updateLampFuel(true); // Burn fuel for time passed while lamp is on.
				if(te.getFuelTimeLeft() == 0)
					world.setBlockMetadataWithNotify(x, y, z, meta+8, 3);
			}
		}
	}

	@Override
	public int tickRate(World world)
	{
		return 20;
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		this.tryPlace(world, x, y, z);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,  EntityLivingBase entity, ItemStack is) 
	{
		TileEntity te =  world.getTileEntity(x, y, z);
		if (te instanceof TEOilLamp)
		{
			((TEOilLamp)te).create();
			FluidStack fs = FluidStack.loadFluidStackFromNBT(is.getTagCompound());
			if(fs != null)
			{
				((TEOilLamp)te).setFuelFromStack(fs);
			}
			/*else
			{
				//world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z)+8, 0x3);
			}*/
			((TEOilLamp)te).hourPlaced = (int)TFC_Time.getTotalHours();

		}
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor Block
	 */
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
	{
		if(!World.doesBlockHaveSolidTopSurface(world, x, y-1, z))
			TFC_Core.setBlockToAirWithDrops(world, x, y, z);
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
	{
		return true;
	}

	protected boolean tryPlace(World world, int x, int y, int z)
	{
		if (!this.canPlaceBlockAt(world, x, y, z))
		{
			if (world.getBlock(x, y, z) == this)
			{
				//this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
				world.setBlockToAir(x, y, z);
			}

			return false;
		}
		else
		{
			return true;
		}
	}

	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta) 
	{
		if (!world.isRemote)
		{
			TEOilLamp te = (TEOilLamp)world.getTileEntity(x, y, z);
			if((meta & 8) != 0)
				meta -=8;
			if (te != null)
			{
				if(te.getFuel() != null)
				{
					ItemStack is = new ItemStack(this, 1, meta);
					NBTTagCompound nbt = te.getFuel().writeToNBT(new NBTTagCompound());
					is.setTagCompound(nbt);
					EntityItem ei = new EntityItem(world,x,y,z,is);
					world.spawnEntityInWorld(ei);
				}
				else
				{
					ItemStack is = new ItemStack(this, 1, meta);
					EntityItem ei = new EntityItem(world,x,y,z,is);
					world.spawnEntityInWorld(ei);
				}
			}
		}
	}

	/**
	 * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
	 * x, y, z, startVec, endVec
	 */
	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 startVec, Vec3 endVec)
	{
		this.setBlockBounds(0.25F, 0.0F, 0.25f, 0.75f, 0.5F, 0.75f);
		return super.collisionRayTrace(world, x, y, z, startVec, endVec);
	}

	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if(meta >= 8)
			return;


		double centerX = x + 0.5F;
		double centerY = y + 0.6F;
		double centerZ = z + 0.5F;
		//double d3 = 0.22;
		//double d4 = 0.27;

		world.spawnParticle("smoke", centerX, centerY, centerZ, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("flame", centerX, centerY, centerZ, 0.0D, 0.0D, 0.0D);
	}

}
