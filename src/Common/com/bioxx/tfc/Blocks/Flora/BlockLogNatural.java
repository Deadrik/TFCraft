package com.bioxx.tfc.Blocks.Flora;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerra;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;

public class BlockLogNatural extends BlockTerra
{
	protected String[] woodNames;
	private int searchDist = 10;
	private static int damage;
	private static int logs;
	private boolean isStone;
	public IIcon[] sideIcons;
	public IIcon[] innerIcons;
	public IIcon[] rotatedSideIcons;

	public BlockLogNatural()
	{
		super(Material.wood);
		this.setTickRandomly(true);
		this.woodNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, woodNames, 0, 16);
		this.sideIcons = new IIcon[woodNames.length];
		this.innerIcons = new IIcon[woodNames.length];
		this.rotatedSideIcons = new IIcon[woodNames.length];
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if(!world.isRemote)
		{
			if(!world.getBlock(x, y - 1, z).isOpaqueCube())
			{
				if (noLogsNearby(world, x + 1, y, z) && noLogsNearby(world, x - 1, y, z) &&
					noLogsNearby(world, x, y, z + 1) && noLogsNearby(world, x, y, z - 1) &&
					noLogsNearby(world, x + 1, y, z + 1) && noLogsNearby(world, x + 1, y, z - 1) &&
					noLogsNearby(world, x - 1, y, z + 1) && noLogsNearby(world, x - 1, y, z - 1) &&
					noLogsNearby(world, x + 1, y - 1, z) && noLogsNearby(world, x - 1, y - 1, z) &&
					noLogsNearby(world, x, y - 1, z + 1) && noLogsNearby(world, x, y - 1, z - 1) &&
					noLogsNearby(world, x + 1, y - 1, z + 1) && noLogsNearby(world, x + 1, y - 1, z - 1) &&
					noLogsNearby(world, x - 1, y - 1, z + 1) && noLogsNearby(world, x - 1, y - 1, z - 1))
					world.setBlock(x, y, z, Blocks.air, 0, 0x2);
			}
		}
	}

	private boolean noLogsNearby(World world, int x, int y, int z)
	{
		return world.blockExists(x, y, z) && world.getBlock(x, y, z) != this;
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		for(int i = 0; i < woodNames.length; i++)
			list.add(new ItemStack(this, 1, i));
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		return this.blockHardness;
	}

	private boolean checkOut(World world, int x, int y, int z, int meta)
	{
		return world.getBlock(x, y, z) == this && world.getBlockMetadata(x, y, z) == meta;
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (side == 0 || side == 1)
			return innerIcons[meta];
		return sideIcons[meta];
	}

	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		for(int i = 0; i < woodNames.length; i++)
		{
			sideIcons[i] = reg.registerIcon(Reference.MOD_ID + ":" + "wood/trees/" + woodNames[i] + " Log");
			innerIcons[i] = reg.registerIcon(Reference.MOD_ID + ":" + "wood/trees/" + woodNames[i] + " Log Top");
			rotatedSideIcons[i] = reg.registerIcon(Reference.MOD_ID + ":" + "wood/trees/" + woodNames[i] + " Log Side");
		}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta)
	{		
		//we need to make sure the player has the correct tool out
		boolean isAxe = false;
		boolean isHammer = false;
		ItemStack equip = entityplayer.getCurrentEquippedItem();
		if(!world.isRemote)
		{
			if (equip != null)
			{
				int[] equipIDs = OreDictionary.getOreIDs(equip);
				for (int id : equipIDs)
				{
					String name = OreDictionary.getOreName(id);
					if (name.startsWith("itemAxe"))
					{
						isAxe = true;
						if (name.startsWith("itemAxeStone"))
						{
							isStone = true;
							break;
						}
					}
					else if (name.startsWith("itemHammer"))
					{
						isHammer = true;
						break;
					}
				}

				if (isAxe)
				{
					damage = -1;
					processTree(world, x, y, z, meta, equip);

					if (damage + equip.getItemDamage() > equip.getMaxDamage())
					{
						int ind = entityplayer.inventory.currentItem;
						entityplayer.inventory.setInventorySlotContents(ind, null);
						world.setBlock(x, y, z, this, meta, 0x2);
					}
					else
						equip.damageItem(damage, entityplayer);

					int smallStack = logs % 16;
					dropBlockAsItem(world, x, y, z, new ItemStack(TFCItems.logs, smallStack, damageDropped(meta)));
					logs -= smallStack;

					// In theory this section should never be triggered since the full stacks are dropped higher up the tree, but keeping it here just in case.
					while (logs > 0)
					{
						dropBlockAsItem(world, x, y, z, new ItemStack(TFCItems.logs, 16, damageDropped(meta)));
						logs -= 16;
					}

				}
				else if (isHammer)
				{
					EntityItem item = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(TFCItems.stick, 1 + world.rand.nextInt(3)));
					world.spawnEntityInWorld(item);
				}
			}
			else
				world.setBlock(x, y, z, this, meta, 0x2);
		}
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int side, EntityPlayer entityplayer)
	{
		int meta = world.getBlockMetadata(x, y, z);
		harvestBlock(world, entityplayer, x, y, z, meta);
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion ex)
	{
		processTree(world, x, y, z, world.getBlockMetadata(x, y, z), null);
	}

	/*private void processTree(World world, int x, int y, int z, ItemStack is)
	{
		//TODO Rewrite the treecap algorithm using a list of coords instead of the ugly array. Shoudl also use a maxmium list size to prevent 
		//any memory issues and should take shortcuts to find the top of the tree and search down
	}*/

	@Deprecated
	private void processTree(World world, int x, int y, int z, int meta, ItemStack is)
	{
		boolean[][][] checkArray = new boolean[searchDist * 2 + 1][256][searchDist * 2 + 1];
		scanLogs(world, x, y, z, meta, checkArray, (byte)0, (byte)0, (byte)0, is);
	}

	@Override
	public Item getItemDropped(int i, Random random, int j)
	{
		return TFCItems.logs;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		int meta = world.getBlockMetadata(x, y, z);
		boolean check = false;
		for(int h = -2; h <= 2; h++)
		{
			for(int g = -2; g <= 2; g++)
			{
				for(int f = -2; f <= 2; f++)
				{
					if(world.getBlock(x + h, y + g, z + f) == this && world.getBlockMetadata(x + h, y + g, z + f) == meta)
						check = true;
				}
			}
		}
		if(!check)
		{
			world.setBlockToAir(x, y, z);
			dropBlockAsItem(world, x, y, z, new ItemStack(TFCItems.logs, 1, meta));
		}
	}


	private void scanLogs(World world, int i, int j, int k, int meta, boolean[][][] checkArray, byte x, byte y, byte z, ItemStack stack)
	{
		if(y >= 0 && j + y < 256)
		{
			int offsetX = 0;int offsetY = 0;int offsetZ = 0;
			checkArray[x + searchDist][y][z + searchDist] = true;

			for (offsetX = -3; offsetX <= 3; offsetX++)
			{
				for (offsetZ = -3; offsetZ <= 3; offsetZ++)
				{
					for (offsetY = 0; offsetY <= 2; offsetY++)
					{
						if(Math.abs(x + offsetX) <= searchDist && j + y + offsetY < 256 && Math.abs(z + offsetZ) <= searchDist)
						{
							if(checkOut(world, i + x + offsetX, j + y + offsetY, k + z + offsetZ, meta)
									&& !(offsetX == 0 && offsetY == 0 && offsetZ == 0)
									&& !checkArray[x + offsetX + searchDist][y + offsetY][z + offsetZ + searchDist])
								scanLogs(world,i, j, k, meta, checkArray, (byte)(x + offsetX),(byte)(y + offsetY),(byte)(z + offsetZ), stack);
						}
					}
				}
			}

			damage++;
			if(stack != null)
			{
				if(damage+stack.getItemDamage() <= stack.getMaxDamage())
				{
					world.setBlock(i + x, j + y, k + z, Blocks.air, 0, 0x2);
					if (!isStone || world.rand.nextInt(10) != 0)
						logs++;
					if (logs >= 16)
					{
						dropBlockAsItem(world, i + x, j + y, k + z, new ItemStack(TFCItems.logs, 16, damageDropped(meta)));
						logs -= 16;						
					}
					notifyLeaves(world, i + x, j + y, k + z);
				}
			}
			else
			{
				world.setBlockToAir(i, j, k);
				logs++;
				if (logs >= 16)
				{
					dropBlockAsItem(world, i, j, k, new ItemStack(TFCItems.logs, 16, damageDropped(meta)));
					logs -= 16;						
				}
				notifyLeaves(world, i + x, j + y, k + z);
			}
		}
	}

	private void notifyLeaves(World world, int x, int y, int z)
	{
		world.notifyBlockOfNeighborChange(x + 1, y, z, Blocks.air);
		world.notifyBlockOfNeighborChange(x - 1, y, z, Blocks.air);
		world.notifyBlockOfNeighborChange(x, y, z + 1, Blocks.air);
		world.notifyBlockOfNeighborChange(x, y, z - 1, Blocks.air);
		world.notifyBlockOfNeighborChange(x, y + 1, z, Blocks.air);
		world.notifyBlockOfNeighborChange(x, y - 1, z, Blocks.air);
	}

}
