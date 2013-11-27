package TFC.Blocks;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import TFC.TFCBlocks;
import TFC.TileEntities.TileEntityPartial;

public class BlockStair extends BlockPartial
{

    public BlockStair(int par1, Material m)
    {
        super(par1, m);
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getRenderType()
    {
        return TFCBlocks.stairRenderId;
    }
    
    @Override
	public void registerIcons(IconRegister iconRegisterer)
    {

    }
    
    @Override
    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        int var7 = par1World.getBlockMetadata(par2, par3, par4);
        int var8 = var7 & 3;
        float var9 = 0.0F;
        float var10 = 0.5F;
        float var11 = 0.5F;
        float var12 = 1.0F;

        if ((var7 & 4) != 0)
        {
            var9 = 0.5F;
            var10 = 1.0F;
            var11 = 0.0F;
            var12 = 0.5F;
        }

        this.setBlockBounds(0.0F, var9, 0.0F, 1.0F, var10, 1.0F);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);

        if (var8 == 0)
        {
            this.setBlockBounds(0.5F, var11, 0.0F, 1.0F, var12, 1.0F);
            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
        else if (var8 == 1)
        {
            this.setBlockBounds(0.0F, var11, 0.0F, 0.5F, var12, 1.0F);
            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
        else if (var8 == 2)
        {
            this.setBlockBounds(0.0F, var11, 0.5F, 1.0F, var12, 1.0F);
            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
        else if (var8 == 3)
        {
            this.setBlockBounds(0.0F, var11, 0.0F, 1.0F, var12, 0.5F);
            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }

        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }
    @Override
    public void onBlockDestroyedByExplosion(World world, int i, int j, int k, Explosion ex) 
    {
        if(!world.isRemote)
        {
        }
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return AxisAlignedBB.getBoundingBox(i, j, k, i+1, j+1, k+1);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int i, int j, int k)
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
    @Override
    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {   

    }

	@Override
    public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
    {
		boolean solidSides[][] = {
				{ true, false, false, false, false, true },
				{ true, false, false, false, true, false },
				{ true, false, false, true, false, false },
				{ true, false, true, false, false, false },
				{ false, true, false, false, false, true },
				{ false, true, false, false, true, false },
				{ false, true, false, true, false, false },
				{ false, true, true, false, false, false }
		};

		int meta = world.getBlockMetadata(x, y, z);
		return solidSides[meta][side.ordinal()];
    }
}
