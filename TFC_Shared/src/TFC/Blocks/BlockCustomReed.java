package TFC.Blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import TFC.*;
import TFC.Core.TFC_Core;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
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
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;


public class BlockCustomReed extends Block implements IPlantable
{
	public BlockCustomReed(int par1)
	{
		super(par1, Material.plants);
		float var3 = 0.375F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 1.0F, 0.5F + var3);
		this.setTickRandomly(true);
	}

	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		return this.canPlaceBlockAt(par1World, par2, par3, par4);
	}

	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockId(par2, par3 - 1, par4);
		boolean correctSoil = TFC_Core.isSoil(var5) || TFC_Core.isSand(var5);
		return var5 == this.blockID ? true : !correctSoil ? false : par1World.getBlockMaterial(par2 - 1, par3 - 1, par4) == Material.water ? true : par1World.getBlockMaterial(par2 + 1, par3 - 1, par4) == Material.water ? true : par1World.getBlockMaterial(par2, par3 - 1, par4 - 1) == Material.water ? true : par1World.getBlockMaterial(par2, par3 - 1, par4 + 1) == Material.water;
	}

	/**
	 * Checks if current block pos is valid, if not, breaks the block as dropable item. Used for reed and cactus.
	 */
	protected final void checkBlockCoordValid(World par1World, int par2, int par3, int par4)
	{
		if (!this.canBlockStay(par1World, par2, par3, par4))
		{
			this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockAndMetadataWithNotify(par2, par3, par4, 0, 0, 2);
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
		return 1;
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return Item.reed.itemID;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		this.checkBlockCoordValid(par1World, par2, par3, par4);
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par1World.isAirBlock(par2, par3 + 1, par4))
        {
            int l;

            for (l = 1; par1World.getBlockId(par2, par3 - l, par4) == this.blockID; ++l)
            {
                ;
            }

            if (l < 3)
            {
                int i1 = par1World.getBlockMetadata(par2, par3, par4);

                if (i1 == 15)
                {
                    par1World.setBlockAndMetadataWithNotify(par2, par3 + 1, par4, this.blockID, 0, 2);
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 4);
                }
                else
                {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, i1 + 1, 4);
                }
            }
        }
    }
	
	@SideOnly(Side.CLIENT)
	@Override
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return Item.reed.itemID;
    }

    @Override
    public EnumPlantType getPlantType(World world, int x, int y, int z)
    {
        return EnumPlantType.Beach;
    }

    @Override
    public int getPlantID(World world, int x, int y, int z)
    {
        return blockID;
    }

    @Override
    public int getPlantMetadata(World world, int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z);
    }
}
