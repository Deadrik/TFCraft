package TFC.Blocks.Flora;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.Blocks.BlockTerra;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFlora extends BlockTerra 
{
	IIcon[] icons;
	String[] MetaNames;

	public BlockFlora()
	{
		super(Material.plants);
		MetaNames = new String[]{"Golden Rod", "Cat Tails"};
		icons = new IIcon[MetaNames.length];
		this.setBlockBounds(0.3f, 0.0f, 0.3f, 0.7f, 0.7f, 0.7f);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return 1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		for (int i = 0; i < icons.length; ++i)
			icons[i] = par1IconRegister.registerIcon(Reference.ModID + ":" + "plants/"+MetaNames[i]);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int par1, int par2)
	{
		if (par2 >= icons.length)
			par2 = 0;
		return icons[par2];
	}

	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		return (par1World.getFullBlockLightValue(par2, par3, par4) >= 8 || 
				par1World.canBlockSeeTheSky(par2, par3, par4)) && 
				this.canThisPlantGrowOnThisBlock(par1World.getBlock(par2, par3 - 1, par4));
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block par5)
	{
		super.onNeighborBlockChange(world, i, j, k, par5);
		if(!canBlockStay(world,i,j,k))
			world.setBlockToAir(i, j, k);
	}

	protected boolean canThisPlantGrowOnThisBlock(Block par1)
	{
		return par1 == TFCBlocks.Grass || par1 == TFCBlocks.Grass2 || 
				par1 == TFCBlocks.Dirt || par1 == TFCBlocks.Dirt2 ||
				par1 == TFCBlocks.ClayGrass || par1 == TFCBlocks.ClayGrass2 ||
				par1 == TFCBlocks.PeatGrass || par1 == Blocks.farmland;
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return Item.getItemFromBlock(this);
	}

}
