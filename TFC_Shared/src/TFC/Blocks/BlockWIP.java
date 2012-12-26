package TFC.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
import net.minecraft.world.gen.feature.*;

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
