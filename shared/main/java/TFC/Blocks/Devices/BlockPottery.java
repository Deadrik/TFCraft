package TFC.Blocks.Devices;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.Blocks.BlockTerraContainer;
import TFC.TileEntities.TileEntityFoodPrep;
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
		this.setHardness(2.0f);
	}

	@Override
	public void registerIcons(IconRegister iconRegisterer)
	{
		this.Clay = iconRegisterer.registerIcon(Reference.ModID + ":" + "clay/Clay");
		this.Ceramic = iconRegisterer.registerIcon(Reference.ModID + ":" + "clay/Ceramic");
		this.Straw = iconRegisterer.registerIcon(Reference.ModID + ":" + "plants/Straw");
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
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		if(metadata > 0)
		{
			ret.add(new ItemStack(TFCItems.Straw, metadata));
		}
		return ret;
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
					if(player.capabilities.isCreativeMode)
					{
						world.setBlockMetadataWithNotify(x, y, z, 15, 2);
					} else
					{
						world.setBlockMetadataWithNotify(x, y, z, meta+1, 2);
					}

					player.inventory.decrStackSize(player.inventory.currentItem, 1);
					return true;
				}
			}
			else
			{
				if(side == 1 && player.isSneaking())
				{
					int offset = 0;
					/*if(world.getBlockId(x, y, z) != TFCBlocks.Pottery.blockID)
					{
						world.setBlock(x, y+1, z, TFCBlocks.Pottery.blockID);
						offset = 1;
					}*/

					TileEntityPottery te = (TileEntityPottery) world.getBlockTileEntity(x, y+offset, z);

					if(hitX < 0.5 && hitZ < 0.5)
					{
						te.ejectItem(0);
					}
					else if(hitX > 0.5 && hitZ < 0.5)
					{
						te.ejectItem(1);
					}
					else if(hitX < 0.5 && hitZ > 0.5)
					{
						te.ejectItem(2);
					}
					else if(hitX > 0.5 && hitZ > 0.5)
					{
						te.ejectItem(3);
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
	
	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int id)
	{
		if(!world.isRemote)
		{
			if(!world.isBlockOpaqueCube(i, j-1, k))
			{
				((TileEntityPottery)world.getBlockTileEntity(i, j, k)).ejectContents();
				world.setBlock(i, j, k, 0);
				return;
			}
		}
	}
}
