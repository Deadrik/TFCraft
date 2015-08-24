package com.bioxx.tfc.Blocks.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
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

public class BlockSand extends BlockTerra
{
	protected IIcon[] icons = new IIcon[Global.STONE_ALL.length];
	protected int textureOffset = 0;

	public BlockSand(int texOff)
	{
		super(Material.sand);
		this.setCreativeTab(TFCTabs.TFCBuilding);
		textureOffset = texOff;
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

	/**
	 * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	 */
	@Override
	public IIcon getIcon(IBlockAccess bAccess, int x, int y, int z, int side)
	{
		int meta = bAccess.getBlockMetadata(x, y, z);
		if(meta >= icons.length) return icons[icons.length - 1];
		return icons[meta];
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
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
		for(int i = 0; i < count; i++)
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "sand/Sand " + Global.STONE_ALL[i + textureOffset]);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		if (!world.isRemote && world.doChunksNearChunkExist(x, y, z, 1))
		{
			int meta = world.getBlockMetadata(x, y, z);

			boolean canFallOneBelow = BlockCollapsible.canFallBelow(world, x, y - 1, z);
			byte count = 0;
			List<Integer> sides = new ArrayList<Integer>();

			if (world.isAirBlock(x + 1, y, z))
			{
				count++;
				if (BlockCollapsible.canFallBelow(world, x + 1, y - 1, z))
					sides.add(0);
			}
			if (world.isAirBlock(x, y, z + 1))
			{
				count++;
				if (BlockCollapsible.canFallBelow(world, x, y - 1, z + 1))
					sides.add(1);
			}
			if (world.isAirBlock(x - 1, y, z))
			{
				count++;
				if (BlockCollapsible.canFallBelow(world, x - 1, y - 1, z))
					sides.add(2);
			}
			if (world.isAirBlock(x, y, z - 1))
			{
				count++;
				if (BlockCollapsible.canFallBelow(world, x, y - 1, z - 1))
					sides.add(3);
			}

			if (!canFallOneBelow && count > 2 && sides.size() >= 1)
			{
				switch (sides.get(random.nextInt(sides.size())))
				{
				case 0:
				{
					world.setBlockToAir(x, y, z);
					world.setBlock(x + 1, y, z, this, meta, 0x2);
					BlockCollapsible.tryToFall(world, x + 1, y, z, this);
					break;
				}
				case 1:
				{
					world.setBlockToAir(x, y, z);
					world.setBlock(x, y, z + 1, this, meta, 0x2);
					BlockCollapsible.tryToFall(world, x, y, z + 1, this);
					break;
				}
				case 2:
				{
					world.setBlockToAir(x, y, z);
					world.setBlock(x - 1, y, z, this, meta, 0x2);
					BlockCollapsible.tryToFall(world, x - 1, y, z, this);
					break;
				}
				case 3:
				{
					world.setBlockToAir(x, y, z);
					world.setBlock(x, y, z - 1, this, meta, 0x2);
					BlockCollapsible.tryToFall(world, x, y, z - 1, this);
					break;
				}
				}
			}
			else if (canFallOneBelow)
			{
				BlockCollapsible.tryToFall(world, x, y, z, this);
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if(!world.isRemote)
		{
			BlockCollapsible.tryToFall(world, x, y, z, this);
			world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
		}
	}
}
