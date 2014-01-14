package TFC.Blocks.Flora;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlowing;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.IShearable;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.Blocks.Vanilla.BlockCustomFlowing;
import TFC.Core.ColorizerFoliageTFC;
import TFC.Core.ColorizerGrassTFC;
import TFC.Core.Recipes;
import TFC.Core.TFC_Core;
import TFC.TileEntities.TESeaWeed;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTallSeaGrassFlowing extends BlockCustomFlowing implements ITileEntityProvider
{
	public BlockTallSeaGrassFlowing(int par1)
	{
		super(par1, Material.water);
		float var3 = 0.5F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 1.0F, 0.5F + var3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor()
	{
		return 16777215;
	}
	
	@Override
	public float getBlockBrightness(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		float var5 = par1IBlockAccess.getLightBrightness(par2, par3, par4);
		float var6 = par1IBlockAccess.getLightBrightness(par2, par3+1, par4);
		return var5 > var6 ? var5 : var6;
	}
	
	@Override
	public int getMixedBrightnessForBlock(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = par1IBlockAccess.getLightBrightnessForSkyBlocks(par2, par3, par4, 0);
		int var6 = par1IBlockAccess.getLightBrightnessForSkyBlocks(par2, par3+1, par4, 0);
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
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		if (this.blockMaterial != Material.water)
			return 16777215;
		else
			return TerraFirmaCraft.proxy.waterColorMultiplier(par1IBlockAccess, par2, par3, par4);
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return -1;
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.seaWeedRenderId;
	}

	@Override
	public int quantityDroppedWithBonus(int par1, Random par2Random)
	{
		return 1 + par2Random.nextInt(par1 * 2 + 1);
	}

	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockId(par2, par3 - 1, par4);
		boolean correctSoil = TFC_Core.isSoil(var5) || TFC_Core.isSand(var5) || TFC_Core.isGravel(var5);
		return !correctSoil ? false : canThisPlantGrowUnderThisBlockID(par1World.getBlockId(par2, par3 + 1, par4)) && par1World.getBlockMaterial(par2, par3, par4) == Material.water;
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, int l, int id)
	{
		TESeaWeed te = (TESeaWeed)(world.getBlockTileEntity(i, j, k));
		int type = -1;
		if(te != null){
			type = te.getType();
		}
		
		int blockId = world.getBlockId(i,j,k);
		if(blockId == Block.ice.blockID){
			world.setBlock(i, j, k, TFCBlocks.SeaGrassFrozen.blockID);
		}
		super.breakBlock(world, i, j, k, l, id);
		if(blockId == Block.ice.blockID){
			world.setBlockMetadataWithNotify(i, j, k, type, 1);
			te = (TESeaWeed)(world.getBlockTileEntity(i, j, k));
			te.setType(type);
		}
		
		if(world.isAirBlock(i, j, k)){
			if(te != null){
				if(type == 1 || type == 2)
					world.setBlock(i, j, k, TFCBlocks.FreshWaterStill.blockID, 0, 1);
				else if(type==0)
					world.setBlock(i, j, k, Block.waterStill.blockID, 0, 1);
			}
		}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int i, int j, int k, int l)
	{
		super.harvestBlock(world, player, i, j, k, l);
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
			ret.add(item);
		return ret;
	}

	protected boolean canThisPlantGrowUnderThisBlockID(int par1)
	{
		return true;//TFC_Core.isSaltWater(par1)|| TFC_Core.isFreshWater(par1);
	}
	
	@Override
	protected void updateFlow(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockMetadata(par2, par3, par4);
		TESeaWeed te = (TESeaWeed)(par1World.getBlockTileEntity(par2,par3,par4));
		int type = -1;
		if(te!=null){
			type = te.getType();
		}
		par1World.setBlock(par2, par3, par4, this.blockID + 1, var5, 2);
		te = (TESeaWeed)(par1World.getBlockTileEntity(par2,par3,par4));
		if(te!=null){
			te.setType(type);
		}
	}

	/**
	 * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
	 */
	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		boolean a,b,c;
		a = (par1World.getFullBlockLightValue(par2, par3, par4) >= 0);
		b = this.canThisPlantGrowUnderThisBlockID(par1World.getBlockId(par2, par3 + 1, par4));
		c = this.canThisPlantGrowOnThisBlockID(par1World.getBlockId(par2, par3 - 1, par4));
		return  a &&
				b &&
				c;
	}

	@Override
	public void onBlockAdded(World world,int i,int j,int k){
		int type = 0;
		if(!world.isAirBlock(i, j+1, k)){
			type = TFC_Core.isFreshWater(world.getBlockId(i, j+1, k))?1:0;
		}
		else if(TFC_Core.isFreshWater(world.getBlockId(i+1, j, k))||
				TFC_Core.isFreshWater(world.getBlockId(i, j, k+1))||
				TFC_Core.isFreshWater(world.getBlockId(i-1, j, k))||
				TFC_Core.isFreshWater(world.getBlockId(i, j, k-1))){
			type = 2;
		}
		TESeaWeed te = (TESeaWeed)(world.getBlockTileEntity(i,j,k));
		te.setType(type);
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

	@Override
	protected void flowIntoBlock(World world, int x, int y, int z, int oldX , int oldY , int oldZ, int newFlowDecay)
	{
		if (this.liquidCanDisplaceBlock(world, x, y, z))
		{
			int i1 = world.getBlockId(x, y, z);

			if (i1 > 0)
			{
				if (this.blockMaterial == Material.lava)
				{
					this.triggerLavaMixEffects(world, x, y, z);
				}
				else
				{
					Block.blocksList[i1].dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
				}
			}

			TESeaWeed te = (TESeaWeed)(world.getBlockTileEntity(oldX, oldY, oldZ));
			int type = -1;
			if(te != null)
			{
				type = te.getType();
			}
			switch(type)
			{
			case 0: world.setBlock(x, y, z, Block.waterMoving.blockID, newFlowDecay, 2); break;
			case 1:
			case 2: world.setBlock(x, y, z, TFCBlocks.FreshWaterFlowing.blockID, newFlowDecay, 2); break;
			default: break;
			}
		}
	}

	/**
	 * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
	 * blockID passed in. Args: blockID
	 */
	protected boolean canThisPlantGrowOnThisBlockID(int par1)
	{
		return TFC_Core.isSoil(par1) || TFC_Core.isSand(par1) || TFC_Core.isGravel(par1);
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
		this.checkFlowerChange(par1World, par2, par3, par4);
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		this.checkFlowerChange(par1World, par2, par3, par4);
		TESeaWeed te = (TESeaWeed)(par1World.getBlockTileEntity(par2, par3, par4));
		int type = -1;
		if(te!= null){
			type = te.getType();
		}
		super.updateTick(par1World, par2, par3, par4, par5Random);
		te = (TESeaWeed)(par1World.getBlockTileEntity(par2, par3, par4));
		if(te!= null){
			te.setType(type);
		}
	}

	protected final void checkFlowerChange(World par1World, int par2, int par3, int par4)
	{
		if (!this.canBlockStay(par1World, par2, par3, par4))
		{
			this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlock(par2, par3, par4, 0, 0, 1);
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
	private Icon pondWeed;
	private Icon seaWeed;

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister registerer)
	{
		seaWeed = registerer.registerIcon("tallgrass");//registerer.registerIcon(Reference.ModID + ":" + "plants/Sea Grass");
		pondWeed = registerer.registerIcon("fern");//registerer.registerIcon(Reference.ModID + ":" + "plants/Pond Grass");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int par1, int par2)
	{
		return TFCBlocks.FreshWaterFlowing.getIcon(par1, par2);//this.seaWeed;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TESeaWeed();
	}
}
