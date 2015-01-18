package com.bioxx.tfc.Blocks.Devices;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
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

import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.TileEntities.TEOilLamp;
import com.bioxx.tfc.api.TFCBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockOilLamp extends BlockTerraContainer
{
	IIcon[] icons;
	public BlockOilLamp()
	{
		super(Material.circuits);
		this.setTickRandomly(true);
		this.setCreativeTab(TFCTabs.TFCDecoration);
		setLightLevel(0.9375F);
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
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int i = 0; i < 6; i++)
			par3List.add(new ItemStack(this, 1, i+8));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		int m = meta & 7;
		if(m == 0)
			return TFC_Textures.SheetGold;
		else if(m == 1)
			return TFC_Textures.SheetPlatinum;
		else if(m == 2)
			return TFC_Textures.SheetRoseGold;
		else if(m == 3)
			return TFC_Textures.SheetSilver;
		else if(m == 4)
			return TFC_Textures.SheetSterlingSilver;
		else if(m == 5)
			return TFC_Textures.SheetBlueSteel;
		else return TFC_Textures.SheetGold;
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

	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{

		}
		return true;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		if(metadata >= 8)
			return ret;

		Item item = getItemDropped(metadata, world.rand, fortune);
		if (item != null)
		{
			ret.add(new ItemStack(item, 1, damageDropped(metadata)));
		}
		return ret;
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
				te.updateLampFuel();
				if(te.getFuelTimeLeft() == 0)
					world.setBlockMetadataWithNotify(x, y, z, meta+8, 3);
			}
		}
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		((TEOilLamp) world.getTileEntity(x, y, z)).create();

		this.func_150109_e(world, x, y, z);
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor Block
	 */
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
	{
		//this.checkValidity(world, x, y, z, b);
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
	{
		return true;
	}

	protected boolean func_150109_e(World world, int x, int y, int z)
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
			if (te != null)
			{
				if(te.fuel != null)
				{
					ItemStack is = new ItemStack(Item.getItemFromBlock(this), 1, meta);
					NBTTagCompound nbt = te.fuel.writeToNBT(new NBTTagCompound());
					is.setTagCompound(nbt);
					EntityItem ei = new EntityItem(world,x,y,z,is);
					world.spawnEntityInWorld(ei);
				}
				else
				{
					ItemStack is = new ItemStack(Item.getItemFromBlock(this), 1, meta);
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
		this.setBlockBounds(0.4F, 0.0F, 0.4f, 0.6f, 0.5F, 0.6f);
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
		double centerY = y + 0.5F;
		double centerZ = z + 0.5F;
		//double d3 = 0.22;
		//double d4 = 0.27;

		world.spawnParticle("smoke", centerX, centerY, centerZ, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("flame", centerX, centerY, centerZ, 0.0D, 0.0D, 0.0D);
	}

}
