package TFC.Blocks;

import java.util.ArrayList;

import TFC.*;
import TFC.Core.*;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Items.ItemChisel;
import TFC.Items.ItemHammer;
import TFC.TileEntities.TileEntityDetailed;
import TFC.TileEntities.TileEntityPartial;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockDetailed extends BlockPartial
{
	public BlockDetailed(int par1)
	{
		super(par1, Material.rock);
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

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ) 
	{
		boolean hasHammer = false;
		for(int i = 0; i < 9;i++)
		{
			if(entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() instanceof ItemHammer)
				hasHammer = true;
		}
		if(entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() instanceof ItemChisel && hasHammer && !world.isRemote)
		{
			int id = world.getBlockId(x, y, z);
			byte meta = (byte) world.getBlockMetadata(x, y, z);

			int mode = 0;
			PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
			if(pi!=null) mode = pi.ChiselMode;

			if(mode == 3)
			{
				ItemChisel.CreateDetailed(world, x, y, z, id, meta, side, hitX, hitY, hitZ);
				return true;
			}
			else if(mode == 4)
			{
				TileEntityDetailed te = (TileEntityDetailed) world.getBlockTileEntity(x, y, z);
				world.setBlock(x, y, z, TFCBlocks.WIP.blockID);
				world.setBlockTileEntity(x, y, z, te);
				return true;
			}
		}
		return false;
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        TileEntityDetailed te = (TileEntityDetailed) world.getBlockTileEntity(i, j, k);
        int md = world.getBlockMetadata(i, j, k);
        
        short type = te.TypeID;
        
        if (type <= 0)
            return super.getCollisionBoundingBoxFromPool(world, i, j, k);
        
        AxisAlignedBB AABB = AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, 1);

//        for(int subX = 0; subX < 10; subX++)
//        {
//        	for(int subZ = 0; subZ < 10; subZ++)
//            {
//        		for(int subY = 0; subY < 10; subY++)
//                {
//        			if (te.data.get((subX * 10 + subZ)*10 + subY))
//        				AABB.getAABBPool().addOrModifyAABBInPool((double)i + subX, (double)j + subY, (double)k + subZ, 
//        						(double)i + subX + 0.1, (double)j + subY + 0.1, (double)k + subZ + 0.1);
//                }
//            }
//        }


        return AABB;
    }
	
	
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int i, int j, int k) 
    {
		PlayerInfo pi = PlayerManagerTFC.getInstance().getClientPlayer();
		int depth = pi.ChiselDetailZoom;
		setBlockBounds(0.0F+ (0.1F * depth), 0.0F+ (0.1F * depth), 0.0F+ (0.1F * depth), 1.0F-(0.1F * depth), 1-(0.1F * depth), 1.0F-(0.1F * depth));
    }

}
