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

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
	{
		world.setBlock(x, y, z, Blocks.air, 0, 0x2);
		onBlockDestroyedByExplosion(world, x, y, z, explosion);
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

	private void tryToFall(World world, int x, int y, int z)
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(x, y, z);
			if (canFallBelow(world, x, y - 1, z) && y >= 0)
			{
				byte byte0 = 32;
				if (!world.checkChunksExist(x - byte0, y - byte0, z - byte0, x + byte0, y + byte0, z + byte0))
				{
					world.setBlockToAir(x, y, z);
					for (; canFallBelow(world, x, y - 1, z) && y > 0; y--) { }
					if (y > 0)
						world.setBlock(x, y, z, this, meta, 2);
				}
				else
				{
					doBeforeFall(world,x,y,z);
					EntityFallingBlockTFC ent = new EntityFallingBlockTFC(world, (double)(x + 0.5F), (double)(y + 0.5F), (double)(z + 0.5F), this, meta);
					world.spawnEntityInWorld(ent);
					Random R = new Random(x * y + z);
					world.playSoundAtEntity(ent, TFC_Sounds.FALLININGDIRTSHORT, 1.0F, 0.8F + (R.nextFloat() / 2));
				}
			}
		}
	}

	protected void doBeforeFall(World world, int x, int y, int z){
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		if(!world.isRemote && world.doChunksNearChunkExist(x, y, z, 1))
		{
			int meta = world.getBlockMetadata(x, y, z);
			//boolean PosXAir = false;
			//boolean NegXAir = false;
			//boolean PosZAir = false;
			//boolean NegZAir = false;
			//boolean PosXAir2 = false;
			//boolean NegXAir2 = false;
			//boolean PosZAir2 = false;
			//boolean NegZAir2 = false;

			boolean isBelowAir = world.isAirBlock(x, y - 1, z);
			byte count = 0;
			List<Integer> sides = new ArrayList<Integer>();

			if(world.isAirBlock(x + 1, y, z))
			{
				count++;
				if(world.isAirBlock(x + 1, y - 1, z))
				{
					//PosXAir = true;
					sides.add(0);
				}
			}
			if(world.isAirBlock(x, y, z + 1))
			{
				count++;
				if(world.isAirBlock(x, y - 1, z + 1))
				{
					//PosZAir = true;
					sides.add(1);
				}
			}
			if(world.isAirBlock(x - 1, y, z))
			{
				count++;
				if(world.isAirBlock(x - 1, y - 1, z))
				{
					//NegXAir = true;
					sides.add(2);
				}
			}
			if(world.isAirBlock(x, y, z - 1))
			{
				count++;
				if(world.isAirBlock(x, y - 1, z - 1))
				{
					//NegZAir = true; 
					sides.add(3);
				}
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
					world.setBlock(x - 1, y, z, this, meta, 3);
					tryToFall(world, x - 1, y, z);
					break;
				}
				case 3:
				{
					world.setBlockToAir(x, y, z);
					world.setBlock(x, y, z - 1, this, meta, 3);
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
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if(!world.isRemote)
		{
			tryToFall(world, x, y, z);
			world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
		}
	}
}
