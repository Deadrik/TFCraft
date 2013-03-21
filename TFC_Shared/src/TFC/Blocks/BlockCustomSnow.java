package TFC.Blocks;

import java.util.Random;

import TFC.*;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Time;
import TFC.WorldGen.TFCBiome;
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

public class BlockCustomSnow extends BlockTerra
{
	public BlockCustomSnow(int par1)
	{
		super(par1, Material.snow);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		this.setTickRandomly(true);
	}


	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockId(par2, par3 - 1, par4);
		return var5 != 0 && (var5 == Block.leaves.blockID || Block.blocksList[var5].isOpaqueCube()) ? true : false;
	}

	
	private boolean canSnowStay(World par1World, int par2, int par3, int par4)
	{
		if (!this.canPlaceBlockAt(par1World, par2, par3, par4))
		{
			par1World.setBlockAndMetadataWithNotify(par2, par3, par4, 0, 0, 2);
			return false;
		}
		else
		{
			return true;
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}
	@Override
	public int getRenderType()
	{
		return TFCBlocks.snowRenderId;
	}

	@Override
	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
	{
		dropBlockAsItem(par1World, par3, par4, par5, par6, 0);
		par2EntityPlayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return Item.snowball.itemID;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		int var5 = par1World.getBlockMetadata(par2, par3, par4);
		if(var5 > 0)
		{
			double speed = 0.58D + 0.4D * (15/var5/15);

			par5Entity.motionX *= speed;
			par5Entity.motionZ *= speed;
		}
	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		this.canSnowStay(par1World, par2, par3, par4);
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 1;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 7;
		float var6 = (float)(2 * (1 + var5)) / 16.0F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var6, 1.0F);
	}

	@Override
	public int tickRate(World world)
	{
		return 50;
	}

	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		if (par1World.getSavedLightValue(EnumSkyBlock.Block, par2, par3, par4) > 11)
		{
			if(meta > 1 && par5Random.nextInt(5) == 0) {
				par1World.setBlockMetadataWithNotify(par2, par3, par4, meta-1, 3);
			} else if(meta == 1 && par5Random.nextInt(5) == 0) {
				par1World.setBlockAndMetadataWithNotify(par2, par3, par4, 0, 0, 2);
			}
		}
		
		if(par1World.isRaining() && TFC_Climate.getHeightAdjustedTemp(par2, par3, par4) <= 0)//Raining and Below Freezing
		{      
			if(meta < 3 && par1World.getBlockMaterial(par2, par3-1, par4) != Material.leaves) 
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, meta+1, 3);
			} 
			else if(meta < 15 && par5Random.nextInt(8) == 0 && par1World.getBlockMaterial(par2, par3-1, par4) != Material.leaves)
            {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, meta+1, 3);
            }
			else if(meta < 3 && par5Random.nextInt(3) == 0 && par1World.getBlockMaterial(par2, par3-1, par4) == Material.leaves)
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, meta+1, 3);
			}
		}
		else if(par1World.isRaining() && TFC_Climate.getHeightAdjustedTemp(par2, par3, par4) >= 0)//Raining and above freezing
        {      
            if(meta <= 15 && par1World.getBlockMaterial(par2, par3-1, par4) != Material.leaves) 
            {
                if(meta > 1) 
                {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, meta-1, 3);
                } 
                else 
                {
                    par1World.setBlockAndMetadataWithNotify(par2, par3, par4, 0, 0, 2);
                }
            } 
            else if(meta <= 15 && par1World.getBlockMaterial(par2, par3-1, par4) == Material.leaves)
            {
                if(meta > 1) {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, meta-1, 3);
                } else {
                    par1World.setBlockAndMetadataWithNotify(par2, par3, par4, 0, 0, 2);
                }
            }
        }
		else if(TFC_Climate.getHeightAdjustedTemp(par2, par3, par4) >= 0F)//Above fReezing
		{
			if(meta > 1 ) {
				par1World.setBlockMetadataWithNotify(par2, par3, par4, meta-1, 3);
			} else if(meta == 1) {
				par1World.setBlockAndMetadataWithNotify(par2, par3, par4, 0, 0, 2);
			}
		}
		else//Below Freezing
		{
		    if(meta > 1 && par5Random.nextInt(5) == 0) 
		    {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, meta-1, 3);
            } 
		    else if(meta == 1 && par5Random.nextInt(5) == 0)
            {
                par1World.setBlockAndMetadataWithNotify(par2, par3, par4, 0, 0, 2);
            }
		}
		par1World.markBlockForUpdate(par2, par3, par4);
	}
	
	@Override
    public void registerIcon(IconRegister registerer)
    {
		this.field_94336_cN = registerer.func_94245_a("Snow");
    }
}
