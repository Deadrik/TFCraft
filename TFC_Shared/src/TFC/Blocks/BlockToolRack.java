package TFC.Blocks;

import java.util.List;
import TFC.TFCBlocks;
import TFC.Core.TFC_Textures;
import TFC.Items.ItemProPick;
import TFC.Items.ItemWeapon;
import TFC.TileEntities.TileEntityToolRack;
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

public class BlockToolRack extends BlockTerraContainer
{
	public BlockToolRack(int par1)
	{
		super(par1, Material.wood);
		this.blockIndexInTexture = 96;
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public String getTextureFile()
	{
		return TFC_Textures.BlockSheet2;
	}

	public boolean isOpaqueCube()
	{
		return false;
	}
	
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return true;
    }
	
	/**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public int getBlockTexture(int woodType)
	{
		return blockIndexInTexture + woodType;
	}

	public int getRenderType()
	{
		return TFCBlocks.toolRackRenderId;
	}

	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			TileEntityToolRack te = (TileEntityToolRack) world.getBlockTileEntity(i, j, k);
			int dir = world.getBlockMetadata(i, j, k);
			if(te != null)
			{
				if(dir == 0)
				{
					if(hitX < 0.5 && hitY > 0.5)
					{
						handleArea(world, i, j, k, entityplayer, te, 0, 0);
					}
					else if(hitX > 0.5 && hitY > 0.5)
					{
						handleArea(world, i, j, k, entityplayer, te, 1, 0);
					}
					else if(hitX < 0.5)
					{
						handleArea(world, i, j, k, entityplayer, te, 2, 0);
					}
					else if(hitX > 0.5)
					{
						handleArea(world, i, j, k, entityplayer, te, 3, 0);
					}
				}
				else if(dir == 1)
				{
					if(hitZ < 0.5 && hitY > 0.5)
					{
						handleArea(world, i, j, k, entityplayer, te, 0, 1);
					}
					else if(hitZ > 0.5 && hitY > 0.5)
					{
						handleArea(world, i, j, k, entityplayer, te, 1, 1);
					}
					else if(hitZ < 0.5)
					{
						handleArea(world, i, j, k, entityplayer, te, 2, 1);
					}
					else if(hitZ > 0.5)
					{
						handleArea(world, i, j, k, entityplayer, te, 3, 1);
					}
				}
				else if(dir == 2)
				{
					if(hitX < 0.5 && hitY > 0.5)
					{
						handleArea(world, i, j, k, entityplayer, te, 0, 2);
					}
					else if(hitX > 0.5 && hitY > 0.5)
					{
						handleArea(world, i, j, k, entityplayer, te, 1, 2);
					}
					else if(hitX < 0.5)
					{
						handleArea(world, i, j, k, entityplayer, te, 2, 2);
					}
					else if(hitX > 0.5)
					{
						handleArea(world, i, j, k, entityplayer, te, 3, 2);
					}
				}
				else if(dir == 3)
				{
					if(hitZ < 0.5 && hitY > 0.5)
					{
						handleArea(world, i, j, k, entityplayer, te, 0, 3);
					}
					else if(hitZ > 0.5 && hitY > 0.5)
					{
						handleArea(world, i, j, k, entityplayer, te, 1, 3);
					}
					else if(hitZ < 0.5)
					{
						handleArea(world, i, j, k, entityplayer, te, 2, 3);
					}
					else if(hitZ > 0.5)
					{
						handleArea(world, i, j, k, entityplayer, te, 3, 3);
					}
				}
				te.broadcastPacketInRange(te.createUpdatePacket());
				return true;
			}
		}
		return false;
	}

	private void handleArea(World world, int i, int j, int k,EntityPlayer entityplayer, TileEntityToolRack te, int slot, int dir) 
	{
		boolean hasToolInHand = entityplayer.getCurrentEquippedItem() != null && 
				(entityplayer.getCurrentEquippedItem().getItem() instanceof ItemTool || entityplayer.getCurrentEquippedItem().getItem() instanceof ItemWeapon  || 
						entityplayer.getCurrentEquippedItem().getItem() instanceof ItemHoe || entityplayer.getCurrentEquippedItem().getItem() instanceof ItemProPick);
		if(te.storage[slot] == null && hasToolInHand)
		{
			te.storage[slot] = entityplayer.getCurrentEquippedItem().copy();
			entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
		}
		else if(te.storage[slot] != null)
		{
			te.ejectItem(slot, dir);
			te.storage[slot] = null;
		}
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityToolRack();
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int i, int j, int k) 
	{
		int dir = access.getBlockMetadata(i, j, k);

		if(dir == 0)
		{
			this.setBlockBounds(0.0F, 0F, 0.85F, 1F, 1F, 1F);
		}
		else if(dir == 1)
		{
			this.setBlockBounds(0.0F, 0F, 0.0F, 0.15F, 1F, 1F);
		}
		else if(dir == 2)
		{
			this.setBlockBounds(0.0F, 0F, 0.00F, 1F, 1F, 0.15F);
		}
		else if(dir == 3)
		{
			this.setBlockBounds(0.85F, 0F, 0.0F, 1F, 1F, 1F);
		}
	}

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
    {
    	int dir = world.getBlockMetadata(i, j, k);

		if(dir == 0)
		{
			return AxisAlignedBB.getBoundingBox(i+0.0F, j+0F, k+0.85F, i+1F, j+1F, k+1F);
		}
		else if(dir == 1)
		{
			return AxisAlignedBB.getBoundingBox(i+0.0F, j+0F, k+0.0F, i+0.15F, j+1F, k+1F);
		}
		else if(dir == 2)
		{
			return AxisAlignedBB.getBoundingBox(i+0.0F, j+0F, k+0.00F, i+1F, j+1F, k+0.15F);
		}
		else if(dir == 3)
		{
			return AxisAlignedBB.getBoundingBox(i+0.85F, j+0F, k+0.0F, i+1F, j+1F, k+1F);
		}
		
        return AxisAlignedBB.getBoundingBox(i, j, k, i+1, j+1, k+1);
    }
	
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) 
	{
		int dir = world.getBlockMetadata(i, j, k);

		if(dir == 0)
		{
			if(!world.isBlockOpaqueCube(i, j, k+1))
			{
				this.breakBlock(world, i, j, k, blockID, dir);
				world.setBlock(i, j, k, 0);
			}
		}
		else if(dir == 1)
		{
			if(!world.isBlockOpaqueCube(i-1, j, k))
			{
				this.breakBlock(world, i, j, k, blockID, dir);
				world.setBlock(i, j, k, 0);
			}
		}
		else if(dir == 2)
		{
			if(!world.isBlockOpaqueCube(i, j, k-1))
			{
				this.breakBlock(world, i, j, k, blockID, dir);
				world.setBlock(i, j, k, 0);
			}
		}
		else if(dir == 3)
		{
			if(!world.isBlockOpaqueCube(i+1, j, k))
			{
				this.breakBlock(world, i, j, k, blockID, dir);
				world.setBlock(i, j, k, 0);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	for(int i = 0; i < 16; i++)
    		par3List.add(new ItemStack(par1, 1, i));
    }

}
