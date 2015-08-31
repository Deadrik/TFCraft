package com.bioxx.tfc.Blocks.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerra;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.Constant.Global;

public class BlockGravel extends BlockTerra
{
	protected IIcon[] icons;
	protected int textureOffset;

	public BlockGravel(int texOff)
	{
		super(Material.ground);
		this.setCreativeTab(TFCTabs.TFC_BUILDING);
		textureOffset = texOff;
		this.setTickRandomly(true);
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		// Change to false if this block should not be added to the creative tab
		Boolean addToCreative = true;

		if(addToCreative)
		{
			int count;
			if(textureOffset == 0) count = 16;
			else count = Global.STONE_ALL.length - 16;

			for(int i = 0; i < count; i++)
				list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		if (fortune > 3)
		{
			fortune = 3;
		}

		if(world.rand.nextInt(10 - fortune * 3) == 0)
		{
			ret.add(new ItemStack(Items.flint , 1));
		}
		else
		{
			ret.add(new ItemStack(Item.getItemFromBlock(this), 1, damageDropped(metadata)));
		}
		return ret;
	}

	@Override
	public IIcon getIcon(IBlockAccess bAccess, int x, int y, int z, int side)
	{
		int meta = bAccess.getBlockMetadata(x, y, z);
		if(meta >= icons.length) return icons[icons.length - 1];
		return icons[meta];
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if(meta >= icons.length) return icons[icons.length - 1];
		return icons[meta];
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		int count = (textureOffset == 0 ? 16 : Global.STONE_ALL.length - 16);
		icons = new IIcon[count];
		for (int i = 0; i < count; i++)
			icons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + "soil/Gravel " + Global.STONE_ALL[i + textureOffset]);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
	}

	@Override
	public int tickRate(World world)
	{
		return 3;
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{
		if (!world.isRemote && world.doChunksNearChunkExist(i, j, k, 1) && !BlockCollapsible.isNearSupport(world, i, j, k, 4, 0))
		{
			int meta = world.getBlockMetadata(i, j, k);

			boolean canFallOneBelow = BlockCollapsible.canFallBelow(world, i, j-1, k);
			byte count = 0;
			List<Integer> sides = new ArrayList<Integer>();

			if(world.isAirBlock(i+1, j, k))
			{
				count++;
				if(BlockCollapsible.canFallBelow(world, i+1, j-1, k))
					sides.add(0);
			}
			if(world.isAirBlock(i, j, k+1))
			{
				count++;
				if(BlockCollapsible.canFallBelow(world, i, j-1, k+1))
					sides.add(1);
			}
			if(world.isAirBlock(i-1, j, k))
			{
				count++;
				if(BlockCollapsible.canFallBelow(world, i-1, j-1, k))
					sides.add(2);
			}
			if(world.isAirBlock(i, j, k-1))
			{
				count++;
				if(BlockCollapsible.canFallBelow(world, i, j-1, k-1))
					sides.add(3);
			}

			if (!canFallOneBelow && count > 2 && !sides.isEmpty())
			{
				switch (sides.get(random.nextInt(sides.size())))
				{
				case 0:
				{
					world.setBlockToAir(i, j, k);
					world.setBlock(i+1, j, k, this, meta, 0x2);
					BlockCollapsible.tryToFall(world, i + 1, j, k, this);
					break;
				}
				case 1:
				{
					world.setBlockToAir(i, j, k);
					world.setBlock(i, j, k+1, this, meta, 0x2);
					BlockCollapsible.tryToFall(world, i, j, k + 1, this);
					break;
				}
				case 2:
				{
					world.setBlockToAir(i, j, k);
					world.setBlock(i-1, j, k, this, meta, 0x2);
					BlockCollapsible.tryToFall(world, i - 1, j, k, this);
					break;
				}
				case 3:
				{
					world.setBlockToAir(i, j, k);
					world.setBlock(i, j, k-1, this, meta, 0x2);
					BlockCollapsible.tryToFall(world, i, j, k - 1, this);
					break;
				}
				}
			}
			else if(canFallOneBelow)
			{
				BlockCollapsible.tryToFall(world, i, j, k, this);
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
	{
		if (!world.isRemote)
		{
			BlockCollapsible.tryToFall(world, x, y, z, this);
			world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
		}
	}
}
