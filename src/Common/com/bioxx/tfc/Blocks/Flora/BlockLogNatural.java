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

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Blocks.BlockTerra;
import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLogNatural extends BlockTerra
{
	String[] woodNames;
	int searchDist = 10;
	static int damage = 0;
	boolean isStone = false;
	public static IIcon[] sideIcons;
	public static IIcon[] innerIcons;
	public static IIcon[] rotatedSideIcons;

	public BlockLogNatural()
	{
		super(Material.wood);
		this.setTickRandomly(true);
		woodNames = Global.WOOD_ALL.clone();
		System.arraycopy(Global.WOOD_ALL, 0, woodNames, 0, 16);
		sideIcons = new IIcon[woodNames.length];
		innerIcons = new IIcon[woodNames.length];
		rotatedSideIcons = new IIcon[woodNames.length];
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random rand)
	{
		if(!world.isRemote)
			if(!world.getBlock(i, j-1, k).isOpaqueCube())
				if(world.getBlock(i+1, j, k) != this && world.getBlock(i-1, j, k) != this && 
				world.getBlock(i, j, k+1) != this && world.getBlock(i, j, k-1) != this && 
				world.getBlock(i+1, j, k+1) != this && world.getBlock(i+1, j, k-1) != this && 
				world.getBlock(i-1, j, k+1) != this && world.getBlock(i-1, j, k-1) != this&&
				world.getBlock(i+1, j-1, k) != this && world.getBlock(i-1, j-1, k) != this && 
				world.getBlock(i, j-1, k+1) != this && world.getBlock(i, j-1, k-1) != this && 
				world.getBlock(i+1, j-1, k+1) != this && world.getBlock(i+1, j-1, k-1) != this && 
				world.getBlock(i-1, j-1, k+1) != this && world.getBlock(i-1, j-1, k-1) != this)
					world.setBlock(i, j, k, Blocks.air, 0, 0x2);
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < woodNames.length; i++)
			list.add(new ItemStack(this,1,i));
	}

	@Override
	public float getBlockHardness(World par1World, int par2, int par3, int par4)
	{
		return this.blockHardness;
	}

	private boolean checkOut(World world, int i, int j, int k, int l)
	{
		if(world.getBlock(i, j, k) == this && world.getBlockMetadata(i, j, k) == l)
			return true;
		return false;
	}

	@Override
	public int damageDropped(int j)
	{
		return j;
	}

	@Override
	public IIcon getIcon(int i, int j)
	{
		if (i == 1)
			return innerIcons[j];
		if (i == 0)
			return innerIcons[j];
		return sideIcons[j];
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		for(int i = 0; i < woodNames.length; i++)
		{
			sideIcons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/trees/" + woodNames[i] + " Log");
			innerIcons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/trees/" + woodNames[i] + " Log Top");
			rotatedSideIcons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/trees/" + woodNames[i] + " Log Side");
		}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{		
		//we need to make sure the player has the correct tool out
		boolean isAxeorSaw = false;
		boolean isHammer = false;
		ItemStack equip = entityplayer.getCurrentEquippedItem();
		if(!world.isRemote)
		{
			if(equip!=null)
			{
				for(int cnt = 0; cnt < Recipes.Axes.length && !isAxeorSaw; cnt++)
					if(equip.getItem() == Recipes.Axes[cnt])
					{
						isAxeorSaw = true;
						if(cnt < 4)
							isStone = true;
					}
				//				for(int cnt = 0; cnt < Recipes.Saws.length && !isAxeorSaw; cnt++)
				//				{
				//					if(equip.getItem() == Recipes.Saws[cnt])
				//					{
				//						isAxeorSaw = true;
				//					}
				//				}
				for(int cnt = 0; cnt < Recipes.Hammers.length && !isAxeorSaw; cnt++)
					if(equip.getItem() == Recipes.Hammers[cnt])
						isHammer = true;
			}
			if(isAxeorSaw)
			{
				damage = -1;
				ProcessTree(world, i, j, k, l, equip);	

				if(damage + equip.getItemDamage() > equip.getMaxDamage())
				{
					int ind = entityplayer.inventory.currentItem;
					entityplayer.inventory.setInventorySlotContents(ind, null);
					world.setBlock(i, j, k, this, l, 0x2);
				}
				else
					equip.damageItem(damage, entityplayer);
			}
			else if(isHammer)
			{
				EntityItem item = new EntityItem(world, i+0.5, j+0.5, k+0.5, new ItemStack(TFCItems.Stick, 1+world.rand.nextInt(3)));
				world.spawnEntityInWorld(item);
			}
			else
				world.setBlock(i, j, k, this, l, 0x2);
		}
	}

	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		return true;
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int i, int j, int k, Explosion ex)
	{
		ProcessTree(world, i, j, k, world.getBlockMetadata(i, j, k), null);
	}

	private void ProcessTree(World world, int i, int j, int k, ItemStack stack)
	{
		//TODO Rewrite the treecap algorithm using a list of coords instead of the ugly array. Shoudl also use a maxmium list size to prevent 
		//any memory issues and should take shortcuts to find the top of the tree and search down
	}

	@Deprecated
	private void ProcessTree(World world, int i, int j, int k, int l, ItemStack stack)
	{
		boolean[][][] checkArray = new boolean[searchDist * 2 + 1][256][searchDist * 2 + 1];
		scanLogs(world, i, j, k, l, checkArray, (byte)0, (byte)0, (byte)0, stack);
	}

	@Override
	public Item getItemDropped(int i, Random random, int j)
	{
		return TFCItems.Logs;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block)
	{
		int meta = world.getBlockMetadata(i, j, k);
		boolean check = false;
		for(int h = -2; h <= 2; h++)
		{
			for(int g = -2; g <= 2; g++)
			{
				for(int f = -2; f <= 2; f++)
				{
					if(world.getBlock(i+h, j+g, k+f) == this && world.getBlockMetadata(i+h, j+g, k+f) == meta)
						check = true;
				}
			}
		}
		if(!check)
		{
			world.setBlockToAir(i, j, k);
			dropBlockAsItem(world, i, j, k, new ItemStack(TFCItems.Logs, 1, meta));
		}
	}


	private void scanLogs(World world, int i, int j, int k, int l, boolean[][][] checkArray, byte x, byte y, byte z, ItemStack stack)
	{
		if(y >= 0 && j + y < 256)
		{
			int offsetX = 0;int offsetY = 0;int offsetZ = 0;
			checkArray[x+searchDist][y][z+searchDist] = true;

			for (offsetX = -3; offsetX <= 3; offsetX++)
				for (offsetZ = -3; offsetZ <= 3; offsetZ++)
					for (offsetY = 0; offsetY <= 2; offsetY++) 
						if(Math.abs(x+offsetX) <= searchDist && j + y + offsetY < 256 && Math.abs(z+offsetZ) <= searchDist)
							if(checkOut(world, i+x+offsetX, j+y+offsetY, k+z+offsetZ, l) 
									&& !(offsetX == 0 && offsetY == 0 && offsetZ == 0)
									&& !checkArray[x+offsetX+searchDist][y+offsetY][z+offsetZ+searchDist])
								scanLogs(world,i, j, k, l, checkArray, (byte)(x+offsetX),(byte)(y+offsetY),(byte)(z+offsetZ), stack);

			damage++;
			if(stack != null)
			{
				if(damage+stack.getItemDamage() <= stack.getMaxDamage())
				{
					world.setBlock(i + x, j + y, k + z, Blocks.air, 0, 0x2);
					if((isStone && world.rand.nextInt(10) != 0) || !isStone)
						dropBlockAsItem(world, i + x, j + y, k + z, new ItemStack(TFCItems.Logs, 1, damageDropped(l)));
					notifyLeaves(world, i + x, j + y, k + z);
				}
			}
			else
			{
				world.setBlockToAir(i, j, k);
				dropBlockAsItem(world, i, j, k, new ItemStack(TFCItems.Logs, 1, damageDropped(l)));
				notifyLeaves(world, i + x, j + y, k + z);
			}
		}
	}

	private void notifyLeaves(World world, int i, int j, int k)
	{
		world.notifyBlockOfNeighborChange(i + 1, j, k, Blocks.air);
		world.notifyBlockOfNeighborChange(i - 1, j, k, Blocks.air);
		world.notifyBlockOfNeighborChange(i, j, k + 1, Blocks.air);
		world.notifyBlockOfNeighborChange(i, j, k - 1, Blocks.air);
		world.notifyBlockOfNeighborChange(i, j + 1, k, Blocks.air);
		world.notifyBlockOfNeighborChange(i, j - 1, k, Blocks.air);
	}

}
