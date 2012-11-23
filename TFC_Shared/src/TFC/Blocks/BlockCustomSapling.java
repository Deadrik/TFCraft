package TFC.Blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import TFC.*;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.TileEntities.TileEntitySapling;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.Generators.WorldGenCustomCedarTrees;
import TFC.WorldGen.Generators.WorldGenCustomHugeTrees;
import TFC.WorldGen.Generators.WorldGenCustomRedwoodTrees;
import TFC.WorldGen.Generators.WorldGenCustomShortTrees;
import TFC.WorldGen.Generators.WorldGenCustomWillowTrees;
import TFC.WorldGen.Generators.WorldGenDouglasFir;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class BlockCustomSapling extends BlockContainer
{
	public BlockCustomSapling(int i, int j)
	{
		super(i, Material.plants);
		float f = 0.4F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
		this.blockIndexInTexture = j;
		this.setTickRandomly(true);
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
		Object obj = TFCBiome.getTreeGen(l, random.nextBoolean());
		
		
		if (obj!= null && !((WorldGenerator) obj).generate(world, random, i, j, k))
		{
			world.setBlockAndMetadata(i, j, k, blockID, l);
		}
	}
	
	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int par5)
	{
		if(world.getBlockMaterial(i, j-1, k) != Material.grass)
		{

			int meta = world.getBlockMetadata(i, j, k);
			this.dropBlockAsItem_do(world, i, j, k, new ItemStack(this,1,meta));
			world.setBlock(i, j, k, 0);
		}
		
		this.checkChange(world, i, j, k);
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
		float growSpeed = 1;
		if(meta == 1 || meta == 11) {
			growSpeed = 1.2f;
		} else if(meta == 5 || meta == 0 || meta == 13) {
			growSpeed = 1.4f;
		} else if(meta == 9 || meta == 14|| meta == 15) {
			growSpeed = 1.6f;
		}
		
		TileEntitySapling te = (TileEntitySapling) world.getBlockTileEntity(i, j, k);
		
		if(te != null && te.growTime == 0)
		{
			te.growTime = (long) ((TFC_Time.getTotalTicks() + (TFC_Time.dayLength * 7) * growSpeed)+(world.rand.nextFloat()*TFC_Time.dayLength));
		}

		if (world.getBlockLightValue(i, j + 1, k) >= 9 && te!= null && TFC_Time.getTotalTicks() > te.growTime)
		{           
			growTree(world, i, j, k, random);
		}
		
		this.checkChange(world, i, j, k);
	}
	
	/**
	 * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
	 */
	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		return (par1World.getFullBlockLightValue(par2, par3, par4) >= 8 || par1World.canBlockSeeTheSky(par2, par3, par4)) && this.canThisPlantGrowOnThisBlockID(par1World.getBlockId(par2, par3 - 1, par4));
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getBlockId(par2, par3, par4);
        return (var5 == 0 || blocksList[var5].blockMaterial.isReplaceable()) && this.canThisPlantGrowOnThisBlockID(par1World.getBlockId(par2, par3 - 1, par4));
    }

	/**
	 * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
	 * blockID passed in. Args: blockID
	 */
	protected boolean canThisPlantGrowOnThisBlockID(int par1)
	{
		return TFC_Core.isSoil(par1);
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	/**
	 * The type of render function that is called for this block
	 */
	public int getRenderType()
	{
		return 1;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntitySapling();
	}
    
    protected void checkChange(World par1World, int par2, int par3, int par4)
    {
        if (!this.canBlockStay(par1World, par2, par3, par4))
        {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockWithNotify(par2, par3, par4, 0);
        }
    }
}
