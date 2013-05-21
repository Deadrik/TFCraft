package TFC.Blocks.Devices;

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
import net.minecraftforge.common.ForgeDirection;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.Blocks.BlockTerraContainer;
import TFC.TileEntities.TileEntityPottery;

public class BlockPottery extends BlockTerraContainer
{
	public Icon Clay;
	public Icon Ceramic;
	public Icon Straw;

	public BlockPottery(int i)
	{
		super(i, Material.glass);
		this.setBlockBounds(0, 0, 0, 1, 0.05f, 1);
		this.setTickRandomly(true);
	}

	@Override
	public void registerIcons(IconRegister iconRegisterer)
	{
		this.Clay = iconRegisterer.registerIcon("clay/Clay");
		this.Ceramic = iconRegisterer.registerIcon("clay/Ceramic");
		this.Straw = iconRegisterer.registerIcon("plants/Straw");
	}

	@Override
	public Icon getIcon(int side, int meta)
	{
		return Straw;
	}
	
	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
    {
		if(side == ForgeDirection.UP)
		{
			return true;
		}
		return false;
    }

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{		
		//super.harvestBlock(world, entityplayer, i, j, k, l);
		int meta = world.getBlockMetadata(i, j, k);
		if(world.getBlockMetadata(i, j, k) > 0)
		{
			world.spawnEntityInWorld(new EntityItem(world, i, j, k, new ItemStack(TFCItems.Straw, meta)));
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			if(player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() == TFCItems.Straw)
			{
				int meta = world.getBlockMetadata(x, y, z);
				if(world.getBlockMetadata(x, y, z) < 15)
				{
					world.setBlockMetadataWithNotify(x, y, z, meta+1, 3);
					player.inventory.consumeInventoryItem(player.inventory.currentItem);
					return true;
				}
			}
			else
			{
				if(side == 1)
				{
					int offset = 0;
					if(world.getBlockId(x, y, z) != TFCBlocks.Pottery.blockID)
					{
						world.setBlock(x, y+1, z, TFCBlocks.Pottery.blockID);
						offset = 1;
					}
					
					TileEntityPottery te = (TileEntityPottery) world.getBlockTileEntity(x, y+offset, z);
					
					if(hitX < 0.5 && hitZ < 0.5)
					{
						te.ejectItem(0);
						te.inventory[0] = null;
					}
					else if(hitX > 0.5 && hitZ < 0.5)
					{
						te.ejectItem(1);
						te.inventory[1] = null;
					}
					else if(hitX < 0.5 && hitZ > 0.5)
					{
						te.ejectItem(2);
						te.inventory[2] = null;
					}
					else if(hitX > 0.5 && hitZ > 0.5)
					{
						te.ejectItem(3);
						te.inventory[3] = null;
					}
					te.broadcastPacketInRange(te.createUpdatePacket());
				}
			}
		}
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		int md = world.getBlockMetadata(i, j, k);
		return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + (0.0625f * (md == 0 ? 1 : md)), k + 1);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int i, int j, int k) 
	{
		int md = par1IBlockAccess.getBlockMetadata(i, j, k);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, (0.0625f * (md == 0 ? 1 : md)), 1.0F);
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.potteryRenderId;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityPottery();
	}
}
