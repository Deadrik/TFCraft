package TFC.Blocks.Vanilla;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import TFC.Reference;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.Core.ColorizerFoliageTFC;
import TFC.Core.ColorizerGrassTFC;
import TFC.Core.Recipes;
import TFC.Core.TFC_Core;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCustomTallGrass extends BlockTallGrass implements IShearable
{
	private static final String[] MetaNames = new String[] {"tallgrass", "fern", "shortgrass"};
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public BlockCustomTallGrass()
	{
		super();
		float var3 = 0.4F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.8F, 0.5F + var3);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < MetaNames.length; ++i)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public int getBlockColor()
	{
		double var1 = 0.5D;
		double var3 = 1.0D;
		return ColorizerGrassTFC.getGrassColor(var1, var3);
	}

	@Override
	public int getRenderColor(int par1)
	{
		return par1 == 0 ? 16777215 : ColorizerFoliageTFC.getFoliageColorBasic();
	}

	@Override
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return TerraFirmaCraft.proxy.grassColorMultiplier(par1IBlockAccess, par2, par3, par4);
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return null;
	}

	@Override
	public int quantityDroppedWithBonus(int par1, Random par2Random)
	{
		return 1 + par2Random.nextInt(par1 * 2 + 1);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int i, int j, int k, int l)
	{
		super.harvestBlock(world, player, i, j, k, l);
		Random rand = new Random();
		ItemStack is = player.inventory.getCurrentItem();
		for(int c = 0; c < Recipes.Knives.length && is != null; c++)
		{
			if(is.getItem() == Recipes.Knives[c])
			{
				createStraw(world, player, i, j, k);
				is.damageItem(1, player);
				break;
			}
		}

		for(int c = 0; c < Recipes.Scythes.length && is != null; c++)
		{
			if(is.getItem() == Recipes.Scythes[c])
			{
				//Spawn the straw for the block that we've already destroyed
				createStraw(world, player, i, j, k );
				//Now check each block around the destroyed block for AOE directions
				for(int x = -1; x < 2; x++)
				{
					for(int z = -1; z < 2; z++)
					{
						if(world.getBlock(i + x, j, k + z) == this)
						{
							createStraw(world, player, i + x, j, k + z);
							is.damageItem(1, player);
							world.setBlockToAir(i + x, j, k + z);
						}
					}
				}
				break;
			}
		}
	}

	private void createStraw(World world, EntityPlayer player, int i, int j, int k)
	{
		EntityItem ei = new EntityItem(world, i+0.5F, j+0.5F, k+0.5F, new ItemStack(TFCItems.Straw, 1));
		world.spawnEntityInWorld(ei);
	}

	private void createJute(World world, EntityPlayer player, int i, int j, int k)
	{
		EntityItem ei = new EntityItem(world, i + 0.5F, j + 0.5F, k + 0.5F, new ItemStack(TFCItems.Jute, 1));
		world.spawnEntityInWorld(ei);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		//if (world.rand.nextInt(8) != 0) return ret;
		ItemStack item = GetSeeds(world.rand);
		if (item != null)
			ret.add(item);
		return ret;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) 
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z)));
		return ret;
	}

	protected boolean canThisPlantGrowOnThisBlock(Block par1)
	{
		return TFC_Core.isSoil(par1); // || par1 == Block.tilledField.blockID;
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

	public static ItemStack GetSeeds(Random R)
	{
		ItemStack is = null;
		/*if(R.nextInt(100) == 0)
		{
			int r = R.nextInt(19);
			switch(r)
			{
			case 0:
				is = new ItemStack(TFCItems.SeedsWheat,1); break;
			case 1:
				is = new ItemStack(TFCItems.SeedsMaize,1); break;
			case 2:
				is = new ItemStack(TFCItems.SeedsTomato,1); break;
			case 3:
				is = new ItemStack(TFCItems.SeedsBarley,1); break;
			case 4:
				is = new ItemStack(TFCItems.SeedsRye,1); break;
			case 5:
				is = new ItemStack(TFCItems.SeedsOat,1); break;
			case 6:
				is = new ItemStack(TFCItems.SeedsRice,1); break;
			case 7:
				is = new ItemStack(TFCItems.SeedsPotato,1); break;
			case 8:
				is = new ItemStack(TFCItems.SeedsOnion,1); break;
			case 9:
				is = new ItemStack(TFCItems.SeedsCabbage,1); break;
			case 10:
				is = new ItemStack(TFCItems.SeedsGarlic,1); break;
			case 11:
				is = new ItemStack(TFCItems.SeedsCarrot,1); break;
			case 12:
				is = new ItemStack(TFCItems.SeedsYellowBellPepper,1); break;
			case 13:
				is = new ItemStack(TFCItems.SeedsRedBellPepper,1); break;
			case 14:
				is = new ItemStack(TFCItems.SeedsSoybean,1); break;
			case 15:
				is = new ItemStack(TFCItems.SeedsGreenbean,1); break;
			case 16:
				is = new ItemStack(TFCItems.SeedsSquash,1); break;
			}
		}*/
		return is;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		this.icons = new IIcon[MetaNames.length];
		for (int i = 0; i < this.icons.length; ++i)
		{
			this.icons[i] = register.registerIcon((i > 1 ? Reference.ModID + ":plants/" : "") + MetaNames[i]);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int par1, int par2)
	{
		if (par2 >= this.icons.length) par2 = 0;
		return this.icons[par2];
	}
}
