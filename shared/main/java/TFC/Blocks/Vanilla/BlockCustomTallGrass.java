package TFC.Blocks.Vanilla;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.Core.ColorizerFoliageTFC;
import TFC.Core.ColorizerGrassTFC;
import TFC.Core.Recipes;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCustomTallGrass extends BlockTallGrass implements IShearable
{
	public BlockCustomTallGrass(int par1)
	{
		super(par1);
		float var3 = 0.4F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.8F, 0.5F + var3);
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
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return -1;
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
		ItemStack is = player.inventory.getCurrentItem();
		for(int c = 0; c < Recipes.Knives.length && is != null; c++) 
		{  
			if(is.getItem().itemID == Recipes.Knives[c].itemID)
			{
				createStraw(world, player, i, j, k);
				is.damageItem(1, player);
				break;
			}
		}
		for(int c = 0; c < Recipes.Scythes.length && is != null; c++) 
		{  
			if(is.getItem().itemID == Recipes.Scythes[c].itemID)
			{
				//Spawn the straw for the block that we've already destroyed
				createStraw(world, player, i, j, k );
				//Now check each block around the destroyed block for AOE directions
				for(int x = -1; x < 2; x++)
				{
					for(int z = -1; z < 2; z++)
					{
						if(world.getBlockId(i+x,  j,  k+z) == this.blockID)
						{
							createStraw(world, player, i + x, j, k + z);
							is.damageItem(1, player);
							world.setBlockToAir(i+x,  j,  k+z);
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

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int meta, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		//        if (world.rand.nextInt(8) != 0)
		//        {
		//            return ret;
		//        }

		ItemStack item = GetSeeds(world.rand);
		if (item != null)
		{
			ret.add(item);
		}
		return ret;
	}

	@Override
	public boolean isShearable(ItemStack item, World world, int x, int y, int z) 
	{
		return true;
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, World world, int x, int y, int z, int fortune) 
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z)));
		return ret;
	}

	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int par1)
	{
		return par1 == TFCBlocks.Grass.blockID || par1 == TFCBlocks.Grass2.blockID || 
				par1 == TFCBlocks.Dirt.blockID || par1 == TFCBlocks.Dirt2.blockID ||
				par1 == TFCBlocks.ClayGrass.blockID || par1 == TFCBlocks.ClayGrass2.blockID ||
				par1 == TFCBlocks.PeatGrass.blockID ||
				par1 == Block.tilledField.blockID;
	}

	/**
	 * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
	 */
	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		return (par1World.getFullBlockLightValue(par2, par3, par4) >= 8 || 
				par1World.canBlockSeeTheSky(par2, par3, par4)) && 
				this.canThisPlantGrowOnThisBlockID(par1World.getBlockId(par2, par3 - 1, par4));
	}

	public static ItemStack GetSeeds(Random R)
	{
		ItemStack is = null;
		if(R.nextInt(100) == 0)
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
		}
		return is;
	}

	private static final String[] field_94367_a = new String[] {"deadbush", "tallgrass", "fern"};
	@SideOnly(Side.CLIENT)
	private Icon[] field_94366_b;

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.field_94366_b = new Icon[field_94367_a.length];

		for (int i = 0; i < this.field_94366_b.length; ++i)
		{
			this.field_94366_b[i] = par1IconRegister.registerIcon(field_94367_a[i]);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int par1, int par2)
	{
		if (par2 >= this.field_94366_b.length)
		{
			par2 = 0;
		}

		return this.field_94366_b[par2];
	}
}
