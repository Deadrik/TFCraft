package TFC.Blocks.Terrain;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.API.Constant.Global;
import TFC.Core.TFC_Core;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFlowers extends BlockBush
{
	// not used at the moment ... 
	public static final String[] flowerNames = new String[] {"dandelion", "poppy", "blueOrchid", "allium", "houstonia", "tulipRed", "tulipOrange", "tulipWhite", "tulipPink", "oxeyeDaisy"};

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public BlockFlowers()
	{
		super(Material.plants);
		this.setTickRandomly(true);
		float var4 = 0.2F;
		this.setBlockBounds(0.5F - var4, 0.0F, 0.5F - var4, 0.5F + var4, var4 * 3.0F, 0.5F + var4);
	}

	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2)
	{
		if (par2 >= this.icons.length) par2 = 0;
		return this.icons[par2];
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		this.icons = new IIcon[Global.FLOWER_META_NAMES.length];
		for (int i = 0; i < this.icons.length; ++i)
		{
			// The first 10 flowers are from vanilla
			this.icons[i] = register.registerIcon((icons.length > 10 ? Reference.ModID + ":plants/" : "") + Global.FLOWER_META_NAMES[i]);
		}
	}

	/**
	 * Determines the damage on the item the block drops. Used in cloth and wood.
	 */
	public int damageDropped(int par1)
	{
		return par1;
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < this.icons.length; ++i)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}

	/**
	 * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
	 */
	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		return (par1World.getFullBlockLightValue(par2, par3, par4) >= 8 ||
				par1World.canBlockSeeTheSky(par2, par3, par4)) &&
				this.canThisPlantGrowOnThisBlock(par1World.getBlock(par2, par3 - 1, par4));
	}

	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		Block var5 = par1World.getBlock(par2, par3, par4);
		return (par1World.isAirBlock(par2, par3, par4) ||
				var5.getMaterial().isReplaceable())
				&& this.canThisPlantGrowOnThisBlock(var5);
	}

	protected boolean canThisPlantGrowOnThisBlock(Block block)
	{
		return TFC_Core.isSoil(block);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	@Override
	public int getRenderType()
	{
		return 1;
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
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		this.checkAndDropBlock(par1World, par2, par3, par4);
	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5)
	{
		this.checkAndDropBlock(par1World, par2, par3, par4);
	}
}
