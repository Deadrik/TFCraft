package TFC.Blocks.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.Blocks.BlockTerra;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSand extends BlockTerra
{
	IIcon[] icons = new IIcon[16];

	public BlockSand()
	{
		super(Material.sand);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int i = 0; i < 16; i++)
			par3List.add(new ItemStack(par1, 1, i));
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	public static boolean canFallBelow(World world, int i, int j, int k)
	{
		Block l = world.getBlock(i, j, k);
		if (world.isAirBlock(i, j, k))
			return true;
		if (l == Blocks.fire)
			return true;
		Material material = l.getMaterial();
		if (material == Material.water)
			return true;
		return material == Material.lava;
	}

	/**
	 * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	 */
	@Override
	public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return icons[par1IBlockAccess.getBlockMetadata(par2, par3, par4)];
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	@Override
	public IIcon getIcon(int par1, int par2)
	{
		return icons[par2];
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		for(int i = 0; i < 16; i++)
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "sand/Sand"+i);
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k)
	{
		world.scheduleBlockUpdate(i, j, k, this, tickRate(world));
	}

	private void tryToFall(World world, int i, int j, int k)
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(i, j, k);
			if (canFallBelow(world, i, j - 1, k) && j >= 0)
			{
				byte byte0 = 32;
				if (!world.checkChunksExist(i - byte0, j - byte0, k - byte0, i + byte0, j + byte0, k + byte0))
				{
					world.setBlockToAir(i, j, k);
					for (; canFallBelow(world, i, j - 1, k) && j > 0; j--) { }
					if (j > 0)
						world.setBlock(i, j, k, this, meta, 2);
				}
				else
				{
					EntityFallingBlock ent = new EntityFallingBlock(world, i + 0.5F, j + 0.5F, k + 0.5F, this, meta);
					world.spawnEntityInWorld(ent);
					Random R = new Random(i*j+k);
					world.playSoundAtEntity(ent, "dirt.slide.short", 1.0F, 0.8F + (R.nextFloat()/2));
				}
			}
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(i, j, k);
			boolean PosXAir = false;
			boolean NegXAir = false;
			boolean PosZAir = false;
			boolean NegZAir = false;
			boolean PosXAir2 = false;
			boolean NegXAir2 = false;
			boolean PosZAir2 = false;
			boolean NegZAir2 = false;	

			boolean isBelowAir = world.isAirBlock(i, j-1, k);
			byte count = 0;
			List sides = new ArrayList<Integer>();

			if(world.isAirBlock(i+1, j, k))
			{
				count++;
				if(world.isAirBlock(i+1, j-1, k))
				{
					//PosXAir = true;
					sides.add(0);
				}
			}
			if(world.isAirBlock(i, j, k+1))
			{
				count++;
				if(world.isAirBlock(i, j-1, k+1))
				{
					//PosZAir = true;
					sides.add(1);
				}
			}
			if(world.isAirBlock(i-1, j, k))
			{
				count++;
				if(world.isAirBlock(i-1, j-1, k))
				{
					//NegXAir = true;
					sides.add(2);
				}
			}
			if(world.isAirBlock(i, j, k-1))
			{
				count++;
				if(world.isAirBlock(i, j-1, k-1))
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
					world.setBlock(i-1, j, k, this, meta, 3);
					tryToFall(world, i-1, j, k);
					break;
				}
				case 3:
				{
					world.setBlockToAir(i, j, k);
					world.setBlock(i, j, k-1, this, meta, 3);
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
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block)
	{
		if(!world.isRemote)
		{
			tryToFall(world, i, j, k);
			world.scheduleBlockUpdate(i, j, k, this, tickRate(world));
		}
	}
}
