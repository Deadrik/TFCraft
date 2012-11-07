package TFC.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import TFC.*;
import TFC.Core.TFC_Sounds;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Entities.EntityFallingDirt;
import TFC.Entities.EntityFallingStone;
import TFC.Items.ItemChisel;
import TFC.Items.ItemCustomShovel;
import TFC.Items.ItemHammer;
import TFC.TileEntities.TileEntityDetailed;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockWIP extends BlockTerraContainer
{
	public BlockWIP(int i)
	{
		super(i, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.detailedRenderId;
	}

	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int i, int j, int k)
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityDetailed();
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        TileEntityDetailed te = (TileEntityDetailed) world.getBlockTileEntity(i, j, k);
        int md = world.getBlockMetadata(i, j, k);
        
        short type = te.TypeID;
        
        if (type <= 0)
            return super.getCollisionBoundingBoxFromPool(world, i, j, k);
        
        AxisAlignedBB AABB = AxisAlignedBB.getBoundingBox(0, 0, 0, 0.0, 0.0, 0.0);

        for(int subX = 0; subX < 10; subX++)
        {
        	for(int subZ = 0; subZ < 10; subZ++)
            {
        		for(int subY = 0; subY < 10; subY++)
                {
        			if (te.data.get((subX * 10 + subZ)*10 + subY))
        				AABB.getAABBPool().addOrModifyAABBInPool((double)i + subX, (double)j + subY, (double)k + subZ, 
        						(double)i + subX + 0.1, (double)j + subY + 0.1, (double)k + subZ + 0.1);
                }
            }
        }


        return AABB;
    }
	
	
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int i, int j, int k) 
    {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1, 1.0F);
    }
}
