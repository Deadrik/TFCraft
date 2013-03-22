package TFC.Blocks;

import java.util.List;
import java.util.Random;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.Core.Recipes;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Core.Direction;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

public class BlockLogNatural extends BlockTerra
{	
	public BlockLogNatural(int i) 
	{
		super(i, Material.wood);
		this.setTickRandomly(true);
	}
	
	@Override
    public void updateTick(World world, int i, int j, int k, Random rand)
    {
		if(!world.isRemote)
		{
			if(!world.isBlockOpaqueCube(i, j-1, k))
			{
				if(world.getBlockId(i+1, j, k) != blockID && world.getBlockId(i-1, j, k) != blockID && 
						world.getBlockId(i, j, k+1) != blockID && world.getBlockId(i, j, k-1) != blockID && 
						world.getBlockId(i+1, j, k+1) != blockID && world.getBlockId(i+1, j, k-1) != blockID && 
						world.getBlockId(i-1, j, k+1) != blockID && world.getBlockId(i-1, j, k-1) != blockID)
				{
					world.setBlock(i, j, k, 0);
				}
			}
		}
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
	public Icon getBlockTextureFromSideAndMetadata(int i, int j) 
	{
		if (i == 1)
		{
			return innerIcons[j];
		}
		if (i == 0)
		{
			return innerIcons[j];
		}
		return sideIcons[j];
	}
	
	String[] WoodNames = {"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
			"Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};
	
	public static Icon[] sideIcons = new Icon[16];
	public static Icon[] innerIcons = new Icon[16];
	public static Icon[] rotatedSideIcons = new Icon[16];
	
	@Override
    public void registerIcon(IconRegister registerer)
    {
		for(int i = 0; i < 16; i++)
		{
			sideIcons[i] = registerer.func_94245_a("wood/trees/" + WoodNames[i] + " Log");
			innerIcons[i] = registerer.func_94245_a("wood/trees/" + WoodNames[i] + " Log Top");
			rotatedSideIcons[i] = registerer.func_94245_a("wood/trees/" + WoodNames[i] + " Log Side");
		}
    }

	static int damage = 0;
	boolean isStone = false;

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
						if(cnt < 4)
							isStone = true;
					}
				}
//				for(int cnt = 0; cnt < Recipes.Saws.length && !isAxeorSaw; cnt++)
//				{
//					if(equip.getItem() == Recipes.Saws[cnt])
//					{
//						isAxeorSaw = true;
//					}
//				}
				for(int cnt = 0; cnt < Recipes.Hammers.length && !isAxeorSaw; cnt++)
				{
					if(equip.getItem() == Recipes.Hammers[cnt])
					{
						isHammer = true;
					}
				}
			}
			if(isAxeorSaw)
			{
				damage = -1;
				ProcessTree(world, i, j, k, l, equip);	
				
				if(damage + equip.getItemDamage() > equip.getMaxDamage())
				{
					int ind = entityplayer.inventory.currentItem;
					entityplayer.inventory.setInventorySlotContents(ind, null);
					world.setBlockAndMetadataWithNotify(i, j, k, blockID, l, 3);
				}
				else
				{
					equip.damageItem(damage, entityplayer);
				}
			}
			else if(isHammer)
			{
				EntityItem item = new EntityItem(world, i+0.5, j+0.5, k+0.5, new ItemStack(Item.stick, 1+world.rand.nextInt(3)));
				world.spawnEntityInWorld(item);
			}
			else
			{
				world.setBlockAndMetadataWithNotify(i, j, k, blockID, l, 3);
			}
		}
	}
	
	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        return true;
    }

	@Override
	public void onBlockDestroyedByExplosion(World world, int i, int j, int k, Explosion ex) 
	{
		ProcessTree(world, i, j, k, world.getBlockMetadata(i, j, k), null);
	}

	private void ProcessTree(World world, int i, int j, int k, int l, ItemStack stack)
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

			scanLogs(world,i,j+y,k,l,checkArray,6,y,6, stack);

			y++;
		}

	}

	@Override
	public int idDropped(int i, Random random, int j)
	{
		return TFCItems.Logs.itemID;
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
			dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.itemsList[TFCItems.Logs.itemID],1,l));
		}
	}

	private void scanLogs(World world, int i, int j, int k, int l, boolean[][][] checkArray,int x, int y, int z, ItemStack stack)
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
								scanLogs(world,i+offsetX, j+offsetY, k+offsetZ, l, checkArray,x+offsetX,y+offsetY,z+offsetZ, stack);
							}
						}
					}
				}
			}

			
			damage++;
			if(stack != null)
			{
				if(damage+stack.getItemDamage() <= stack.getMaxDamage())
				{
					world.setBlock(i, j, k, 0);
					world.markBlockForUpdate(i, j, k);
					if((isStone && world.rand.nextInt(10) != 0) || !isStone)
						dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.itemsList[TFCItems.Logs.itemID],1,l));
				}
			}
			else
			{
				world.setBlock(i, j, k, 0);
				world.markBlockForUpdate(i, j, k);
				dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.itemsList[TFCItems.Logs.itemID],1,l));
			}
		}
	}

}
