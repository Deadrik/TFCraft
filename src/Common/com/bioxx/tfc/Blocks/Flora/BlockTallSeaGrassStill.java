package com.bioxx.tfc.Blocks.Flora;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.ColorizerFoliageTFC;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TESeaWeed;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTallSeaGrassStill extends Block implements ITileEntityProvider
{
	@SideOnly(Side.CLIENT)
	private IIcon pondWeed;
	private IIcon seaWeed;

	public BlockTallSeaGrassStill()
	{
		super( Material.water);
		float var3 = 0.5F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 1.0F, 0.5F + var3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor()
	{
		return 16777215;
	}

	//	@Override
	//	public float getBlockBrightness(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	//	{
	//		float var5 = par1IBlockAccess.getBlock(par2, par3, par4).getLightValue();
	//		float var6 = par1IBlockAccess.getBlock(par2, par3+1, par4).getLightValue();
	//		return var5 > var6 ? var5 : var6;
	//	}

	@Override
	public int getMixedBrightnessForBlock(IBlockAccess bAccess, int x, int y, int z)
	{
		int var5 = bAccess.getLightBrightnessForSkyBlocks(x, y, z, 0);
		int var6 = bAccess.getLightBrightnessForSkyBlocks(x, y + 1, z, 0);
		int var7 = var5 & 255;
		int var8 = var6 & 255;
		int var9 = var5 >> 16 & 255;
		int var10 = var6 >> 16 & 255;
		return (var7 > var8 ? var7 : var8) | (var9 > var10 ? var9 : var10) << 16;
	}

	@Override
	public int getRenderColor(int par1)
	{
		return par1 == 0 ? 16777215 : ColorizerFoliageTFC.getFoliageColorBasic();
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
	 * when first determining what to render.
	 */
	public int colorMultiplier(IBlockAccess bAccess, int x, int y, int z)
	{
		if (this.blockMaterial != Material.water)
			return 16777215;
		else
			return TerraFirmaCraft.proxy.waterColorMultiplier(bAccess, x, y, z);
	}

	@Override
	public Item getItemDropped(int par1, Random R, int par3)
	{
		return null;
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.seaWeedRenderId;
	}

	@Override
	public int quantityDroppedWithBonus(int i, Random R)
	{
		return 1 + R.nextInt(i * 2 + 1);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		Block b = world.getBlock(x, y - 1, z);
		boolean correctSoil = TFC_Core.isSoil(b) || TFC_Core.isSand(b) || TFC_Core.isGravel(b);
		return !correctSoil ? false : canThisPlantGrowUnderThisBlock(world.getBlock(x, y + 1, z)) && world.getBlock(x, y, z).getMaterial() == Material.water;
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, Block block, int l)
	{
		TESeaWeed te = (TESeaWeed)(world.getTileEntity(i, j, k));
		int type = -1;
		if(te != null)
			type = te.getType();

		if(block == TFCBlocks.Ice)
			world.setBlock(i, j, k, TFCBlocks.SeaGrassFrozen);

		super.breakBlock(world, i, j, k, block, l);
		if(block == TFCBlocks.Ice)
		{
			world.setBlockMetadataWithNotify(i, j, k, type, 1);
			te = (TESeaWeed)(world.getTileEntity(i, j, k));
			te.setType(type);
		}
		if(world.isAirBlock(i, j, k))
		{
			if(te != null)
			{
				if(type == 1 || type == 2)
					world.setBlock(i, j, k, TFCBlocks.FreshWater, 0, 1);
				else if(type==0)
					world.setBlock(i, j, k, TFCBlocks.SaltWater, 0, 1);
			}
		}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int i, int j, int k, int l)
	{
		super.harvestBlock(world, player, i, j, k, l);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		//        if (world.rand.nextInt(8) != 0)
		//        {
		//            return ret;
		//        }

		ItemStack item = GetSeeds(world.rand);
		if (item != null)
			ret.add(item);
		return ret;
	}

	protected boolean canThisPlantGrowUnderThisBlock(Block par1)
	{
		return true;//TFC_Core.isSaltWater(par1)|| TFC_Core.isFreshWater(par1);
	}

	@Override
	public int tickRate(World world)
	{
		return this.blockMaterial == Material.water ? 5 : (this.blockMaterial == Material.lava ? 30 : 0);
	}

	/**
	 * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
	 */
	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		boolean a, b, c;
		a = (world.getFullBlockLightValue(x, y, z) >= 0);
		b = this.canThisPlantGrowUnderThisBlock(world.getBlock(x, y + 1, z));
		c = this.canThisPlantGrowOnThisBlock(world.getBlock(x, y - 1, z));
		return  a &&
				b &&
				c;
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k)
	{
		int type = -1;
		if(!world.isAirBlock(i, j + 1, k))
		{
			type = TFC_Core.isFreshWater(world.getBlock(i, j + 1, k)) ? 1 : 0;
		}
		else if(TFC_Core.isFreshWater(world.getBlock(i + 1, j, k))||
				TFC_Core.isFreshWater(world.getBlock(i, j, k + 1))||
				TFC_Core.isFreshWater(world.getBlock(i - 1, j, k))||
				TFC_Core.isFreshWater(world.getBlock(i, j, k - 1)))
		{
			type = 2;
		}
		TESeaWeed te = (TESeaWeed)(world.getTileEntity(i, j, k));
		if(te != null)
		{
			te.setType(type);
		}
		super.onBlockAdded(world, i, j, k);
	}

	@Override
	public boolean canCollideCheck(int par1, boolean par2)
	{
		return true;
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

	/**
	 * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
	 * blockID passed in. Args: blockID
	 */
	protected boolean canThisPlantGrowOnThisBlock(Block b)
	{
		return TFC_Core.isSoil(b) || TFC_Core.isSand(b) || TFC_Core.isGravel(b);
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
	{
		super.onNeighborBlockChange(world, x, y, z, b);
		this.checkFlowerChange(world, x, y, z);
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, int x, int y, int z, Random R)
	{
		this.checkFlowerChange(world, x, y, z);
		super.updateTick(world, x, y, z, R);
	}

	protected final void checkFlowerChange(World world, int x, int y, int z)
	{
		if (!this.canBlockStay(world, x, y, z))
		{
			this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
	 */
	public int getRenderBlockPass()
	{
		return this.blockMaterial == Material.water ? 1 : 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		seaWeed = registerer.registerIcon("tallgrass");//registerer.registerIcon(Reference.ModID + ":" + "plants/Sea Grass");
		pondWeed = registerer.registerIcon("fern");//registerer.registerIcon(Reference.ModID + ":" + "plants/Pond Grass");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int par1, int par2)
	{
		return TFCBlocks.FreshWater.getIcon(par1, par2);//Block.waterMoving.getIcon(0,0);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2)
	{
		return new TESeaWeed();
	}
}
