package TFC.Blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import TFC.*;
import TFC.WorldGen.Generators.WorldGenCustomCedarTrees;
import TFC.WorldGen.Generators.WorldGenCustomHugeTrees;
import TFC.WorldGen.Generators.WorldGenCustomRedwoodTrees;
import TFC.WorldGen.Generators.WorldGenCustomShortTrees;
import TFC.WorldGen.Generators.WorldGenCustomWillowTrees;
import TFC.WorldGen.Generators.WorldGenDouglasFir;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class BlockCustomSapling extends BlockCustomFlower
{
	public BlockCustomSapling(int i, int j)
	{
		super(i, j);
		float f = 0.4F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
		this.blockIndexInTexture = j;
	}

	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	for(int i = 0; i < 16; i++)
    		par3List.add(new ItemStack(par1, 1, i));
    }
	
	@Override
	public int damageDropped(int i)
	{
		return i;
	}
	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		return j+blockIndexInTexture;
	}

	@Override
	public String getTextureFile() 
	{
		return "/bioxx/terrablocks.png";
	}

	public void growTree(World world, int i, int j, int k, Random random)
	{
		int l = world.getBlockMetadata(i, j, k);
		world.setBlock(i, j, k, 0);
		Object obj = null;
		switch(l)
		{
		case 1:
		{
			obj = new WorldGenCustomShortTrees(false,1);
			break;
		}
		case 2:
		{
			obj = new WorldGenCustomShortTrees(false,2);
			break;
		}
		case 3:
		{
			obj = new WorldGenCustomShortTrees(false,3);
			break;
		}
		case 4:
		{
			obj = new WorldGenDouglasFir(false, 4, false);
			break;
		}
		case 5:
		{
			obj = new WorldGenCustomShortTrees(false,5);
			break;
		}
		case 6:
		{
			obj = new WorldGenCustomShortTrees(false,6);
			break;
		}
		case 7:
		{
			obj = new WorldGenCustomShortTrees(false,7);
			break;
		}
		case 8:
		{
			obj = new WorldGenCustomShortTrees(false,8);
			break;
		}
		case 9:
		{
			obj = new WorldGenCustomRedwoodTrees(false,9);
			break;
		}
		case 10:
		{
			obj = new WorldGenCustomShortTrees(false,10);
			break;
		}
		case 11:
		{
			obj = new WorldGenCustomShortTrees(false,11);
			break;
		}
		case 12:
		{
			obj = new WorldGenCustomCedarTrees(false,12);
			break;
		}
		case 13:
		{
			obj = new WorldGenCustomShortTrees(false,13);
			break;
		}
		case 14:
		{
			obj = new WorldGenCustomWillowTrees(false,14);
			break;
		}
		case 15:
		{
			obj = new WorldGenCustomHugeTrees(false,10 + random.nextInt(20), 15, 15);
			break;
		}
		default:
		{
			obj = new WorldGenCustomShortTrees(false,0);
			break;
		}
		}
		if (obj!= null && !((WorldGenerator) obj).generate(world, random, i, j, k))
		{
			world.setBlockAndMetadata(i, j, k, blockID, l);
		}
	}
	
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if(par1World.getBlockMaterial(par2, par3-1, par4) != Material.grass)
		{

			int meta = par1World.getBlockMetadata(par2, par3, par4);
			this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(this,1,meta));
			par1World.setBlock(par2, par3, par4, 0);
		}
	}
	
	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{
		if (world.isRemote)
		{
			return;
		}
		super.updateTick(world, i, j, k, random);
		int meta = world.getBlockMetadata(i, j, k);
		int growSpeed = 30;
		if(meta == 1 || meta == 8 || meta == 11) {
			growSpeed = 20;
		} else if(meta == 5 || meta == 15 || meta == 0 || meta == 13) {
			growSpeed = 40;
		} else if(meta == 9 || meta == 14) {
			growSpeed = 60;
		}

		if (world.getBlockLightValue(i, j + 1, k) >= 9 && random.nextInt(growSpeed) == 0)
		{           
			growTree(world, i, j, k, random);
		}
	}
}
