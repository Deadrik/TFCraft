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
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerra;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.Entities.EntityFallingBlockTFC;
import com.bioxx.tfc.api.TFCBlocks;
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
		if (block == Blocks.bedrock)
			return false;
		if (block == Blocks.fire)
			return true;
		if (block == TFCBlocks.TallGrass)
			return true;
		if (block == TFCBlocks.Torch)
			return true;
		if (block == TFCBlocks.SmokeRack)
			return true;
		if (block == TFCBlocks.ToolRack)
			return true;
		if (block == TFCBlocks.Charcoal)
			return false;
		if (!block.isNormalCube())
			return true;
		Material material = block.getMaterial();
		if (material == Material.water || material == Material.lava)
			return true;
		return false;
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

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
	{
		world.setBlock(x, y, z, Blocks.air, 0, 0x2);
		onBlockDestroyedByExplosion(world, x, y, z, explosion);
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
					world.playSoundAtEntity(entityfallingblock, TFC_Sounds.FALLININGDIRTSHORT, 1.0F, 0.8F + (world.rand.nextFloat()/2));
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
	public void updateTick(World world, int i, int j, int k, Random random)
	{
		if(!world.isRemote && world.doChunksNearChunkExist(i, j, k, 1))
		{
			int meta = world.getBlockMetadata(i, j, k);

			boolean isBelowAir = BlockCollapsable.canFallBelow(world, i, j-1, k);
			byte count = 0;
			List sides = new ArrayList<Integer>();

			if(world.isAirBlock(i+1, j, k))
			{
				count++;
				if(BlockCollapsable.canFallBelow(world, i+1, j-1, k))
					sides.add(0);
			}
			if(world.isAirBlock(i, j, k+1))
			{
				count++;
				if(BlockCollapsable.canFallBelow(world, i, j-1, k+1))
					sides.add(1);
			}
			if(world.isAirBlock(i-1, j, k))
			{
				count++;
				if(BlockCollapsable.canFallBelow(world, i-1, j-1, k))
					sides.add(2);
			}
			if(world.isAirBlock(i, j, k-1))
			{
				count++;
				if(BlockCollapsable.canFallBelow(world, i, j-1, k-1))
					sides.add(3);
			}

			if(!isBelowAir && (count > 2) && sides.size() >= 1)
			{
				switch((Integer)sides.get(random.nextInt(sides.size())))
				{
				case 0:
				{
					world.setBlockToAir(i, j, k);
					world.setBlock(i+1, j, k, this, meta, 0x2);
					tryToFall(world, i+1, j, k);
					break;
				}
				case 1:
				{
					world.setBlockToAir(i, j, k);
					world.setBlock(i, j, k+1, this, meta, 0x2);
					tryToFall(world, i, j, k+1);
					break;
				}
				case 2:
				{
					world.setBlockToAir(i, j, k);
					world.setBlock(i-1, j, k, this, meta, 0x2);
					tryToFall(world, i-1, j, k);
					break;
				}
				case 3:
				{
					world.setBlockToAir(i, j, k);
					world.setBlock(i, j, k-1, this, meta, 0x2);
					tryToFall(world, i, j, k-1);
					break;
				}
				}
			}
			else if(isBelowAir)
			{
				tryToFall(world, i, j, k);
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
