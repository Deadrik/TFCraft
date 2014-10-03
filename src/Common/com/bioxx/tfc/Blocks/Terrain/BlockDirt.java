package com.bioxx.tfc.Blocks.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerra;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.Entities.EntityFallingBlockTFC;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDirt extends BlockTerra
{
	protected IIcon[] icons;
	protected int textureOffset = 0;

	public BlockDirt(int texOff)
	{
		super(Material.ground);
		this.setCreativeTab(TFCTabs.TFCBuilding);
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
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return Item.getItemFromBlock(this);
	}

	public static boolean canFallBelow(World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
		if (world.isAirBlock(x, y, z))
			return true;
		if (block == Blocks.fire)
			return true;
		Material material = block.getMaterial();
		return material == Material.water ? true : material == Material.lava;
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
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "soil/Dirt " + Global.STONE_ALL[i + textureOffset]);
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

	private void tryToFall(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (!BlockCollapsable.isNearSupport(world, x, y, z, 4, 0) && BlockCollapsable.canFallBelow(world, x, y - 1, z) && y >= 0)
		{
			byte byte0 = 32;

			if (!BlockCollapsable.fallInstantly && world.checkChunksExist(x - byte0, y - byte0, z - byte0, x + byte0, y + byte0, z + byte0))
			{
				if (!world.isRemote)
				{
					EntityFallingBlockTFC entityfallingblock = new EntityFallingBlockTFC(world, (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), this, meta);
					world.spawnEntityInWorld(entityfallingblock);
					world.playSoundAtEntity(entityfallingblock, TFC_Sounds.FALLININGROCKSHORT, 1.0F, 0.8F + (world.rand.nextFloat()/2));
				}
			}
			else
			{
				world.setBlockToAir(x, y, z);

				while (BlockCollapsable.canFallBelow(world, x, y - 1, z) && y > 0)
				{
					--y;
				}

				if (y > 0)
				{
					world.setBlock(x, y, z, this, meta, 0x2);
				}
			}
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		if (!world.isRemote)
		{
			int meta = world.getBlockMetadata(x, y, z);
			if(BlockCollapsable.isNearSupport(world, x, y, z, 4, 0))
				return;

			boolean isBelowAir = world.isAirBlock(x, y - 1, z);
			byte count = 0;
			List sides = new ArrayList<Integer>();

			if(world.isAirBlock(x + 1, y, z))
			{
				count++;
				if(world.isAirBlock(x + 1, y - 1, z))
					sides.add(0);
			}
			if(world.isAirBlock(x, y, z + 1))
			{
				count++;
				if(world.isAirBlock(x, y - 1, z + 1))
					sides.add(1);
			}
			if(world.isAirBlock(x - 1, y, z))
			{
				count++;
				if(world.isAirBlock(x - 1, y - 1, z))
					sides.add(2);
			}
			if(world.isAirBlock(x, y, z - 1))
			{
				count++;
				if(world.isAirBlock(x, y - 1, z - 1))
					sides.add(3);
			}

			if(!isBelowAir && (count > 2) && sides.size() >= 1)
			{
				switch((Integer)sides.get(random.nextInt(sides.size())))
				{
				case 0:
				{
					world.setBlockToAir(x, y, z);
					world.setBlock(x + 1, y, z, this, meta, 0x2);
					tryToFall(world, x + 1, y, z);
					break;
				}
				case 1:
				{
					world.setBlockToAir(x, y, z);
					world.setBlock(x, y, z + 1, this, meta, 0x2);
					tryToFall(world, x, y, z + 1);
					break;
				}
				case 2:
				{
					world.setBlockToAir(x, y, z);
					world.setBlock(x - 1, y, z, this, meta, 0x2);
					tryToFall(world, x - 1, y, z);
					break;
				}
				case 3:
				{
					world.setBlockToAir(x, y, z);
					world.setBlock(x, y, z - 1, this, meta, 0x2);
					tryToFall(world, x, y, z - 1);
					break;
				}
				}
			}
			else if(isBelowAir)
			{
				tryToFall(world, x, y, z);
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
	{
		if (!world.isRemote)
		{
			tryToFall(world, x, y, z);
			world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
		}
	}
}
