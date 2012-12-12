package TFC.Blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.Core.Recipes;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Core.Direction;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;

public class BlockLogNatural extends BlockTerra
{

	public BlockLogNatural(int i) 
	{
		super(i, Material.wood);
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < 16; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}

	@Override
	public float getBlockHardness(World par1World, int par2, int par3, int par4)
	{
		return this.blockHardness;
	}

	private boolean checkOut(World world, int i, int j, int k, int l)
	{
		if(world.getBlockId(i, j, k) == blockID && world.getBlockMetadata(i, j, k) == l)
		{
			return true;
		}
		return false;
	}

	@Override
	public int damageDropped(int j) {
		return j;
	}	

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) 
	{
		if (i == 1)
		{
			return j+144;
		}
		if (i == 0)
		{
			return j+144;
		}
		return j+128;
	}

	static int damage = 0;

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
				{
					if(equip.getItem() == Recipes.Axes[cnt])
					{
						isAxeorSaw = true;
					}
				}
				for(int cnt = 0; cnt < Recipes.Saws.length && !isAxeorSaw; cnt++)
				{
					if(equip.getItem() == Recipes.Saws[cnt])
					{
						isAxeorSaw = true;
					}
				}
				for(int cnt = 0; cnt < Recipes.Hammers.length && !isAxeorSaw; cnt++)
				{
					if(equip.getItem() == Recipes.Hammers[cnt])
					{
						isHammer = true;
					}
				}
				if(!isAxeorSaw && equip.getItem() == TFCItems.FlintPaxel)
				{
					isAxeorSaw = true;
				}
			}
			if(isAxeorSaw)
			{
				damage = -1;
				ProcessTree(world, i, j, k, l);	
				equip.damageItem(damage, entityplayer);
				if(damage > equip.getMaxDamage() - equip.getItemDamage())
				{
					int ind = entityplayer.inventory.currentItem;
					entityplayer.inventory.setInventorySlotContents(ind, null);
				}
			}
			else if(isHammer)
			{
				EntityItem item = new EntityItem(world, i+0.5, j+0.5, k+0.5, new ItemStack(Item.stick, 1+world.rand.nextInt(3)));
				world.spawnEntityInWorld(item);
			}
			else
			{
				world.setBlockAndMetadata(i, j, k, blockID, l);
			}
		}
	}
	
	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        return true;
    }

	@Override
	public void onBlockDestroyedByExplosion(World world, int i, int j, int k) 
	{
		ProcessTree(world, i, j, k, world.getBlockMetadata(i, j, k));
	}

	private void ProcessTree(World world, int i, int j, int k, int l)
	{
		int x = i;
		int y = 0;
		int z = k;
		boolean checkArray[][][] = new boolean[11][50][11];

		boolean reachedTop = false;
		while(!reachedTop)
		{
			if(l != 9 && l != 15 && world.getBlockId(x, j+y+1, z) == 0)
			{
				reachedTop = true;
			}
			else if((l == 9 || l == 15) && world.getBlockId(x, j+y+1, z) == 0
					&& world.getBlockId(x+1, j+y+1, z) != blockID && world.getBlockId(x-1, j+y+1, z) != blockID && world.getBlockId(x, j+y+1, z+1) != blockID &&
					world.getBlockId(x, j+y+1, z-1) != blockID && world.getBlockId(x-1, j+y+1, z-1) != blockID && world.getBlockId(x-1, j+y+1, z+1) != blockID && 
					world.getBlockId(x+1, j+y+1, z+1) != blockID && world.getBlockId(x+1, j+y+1, z-1) != blockID)
			{
				reachedTop = true;
			}

			scanLogs(world,i,j+y,k,l,checkArray,6,y,6);

			y++;
		}

	}

	@Override
	public int idDropped(int i, Random random, int j)
	{
		return TFCItems.Logs.shiftedIndex;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		boolean check = false;
		for(int h = -2; h <= 2; h++)
		{
			for(int g = -2; g <= 2; g++)
			{
				for(int f = -2; f <= 2; f++)
				{
					if(world.getBlockId(i+h, j+g, k+f) == blockID && world.getBlockMetadata(i+h, j+g, k+f) == world.getBlockMetadata(i, j, k))
					{
						check = true;
					}
				}
			}
		}
		if(!check)
		{
			world.setBlock(i, j, k, 0);
			dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.itemsList[TFCItems.Logs.shiftedIndex],1,l));
		}
	}

	private void scanLogs(World world, int i, int j, int k, int l, boolean[][][] checkArray,int x, int y, int z)
	{
		if(y >= 0)
		{
			checkArray[x][y][z] = true;
			int offsetX = 0;int offsetY = 0;int offsetZ = 0;

			for (offsetY = 0; offsetY <= 1; offsetY++)
			{
				for (offsetX = -2; offsetX <= 2; offsetX++)
				{
					for (offsetZ = -2; offsetZ <= 2; offsetZ++)
					{
						if(x+offsetX < 11 && x+offsetX >= 0 && z+offsetZ < 11 && z+offsetZ >= 0 && y+offsetY < 50 && y+offsetY >= 0)
						{
							if(checkOut(world, i+offsetX, j+offsetY, k+offsetZ, l) && !checkArray[x+offsetX][y+offsetY][z+offsetZ])
							{
								scanLogs(world,i+offsetX, j+offsetY, k+offsetZ, l, checkArray,x+offsetX,y+offsetY,z+offsetZ);
							}
						}
					}
				}
			}

			world.setBlockWithNotify(i, j, k, 0);
			world.markBlockForUpdate(i, j, k);
			dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.itemsList[TFCItems.Logs.shiftedIndex],1,l));
			damage++;
		}
	}

}
