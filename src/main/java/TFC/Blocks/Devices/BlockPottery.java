package TFC.Blocks.Devices;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.Blocks.BlockTerraContainer;
import TFC.Items.ItemLogs;
import TFC.Items.Pottery.ItemPotteryBase;
import TFC.TileEntities.TileEntityPottery;

public class BlockPottery extends BlockTerraContainer
{
	public IIcon Clay;
	public IIcon Ceramic;
	public IIcon Straw;

	public BlockPottery()
	{
		super(Material.glass);
		this.setBlockBounds(0, 0, 0, 1, 0.05f, 1);
		this.setHardness(2.0f);
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.Clay = iconRegisterer.registerIcon(Reference.ModID + ":" + "clay/Clay");
		this.Ceramic = iconRegisterer.registerIcon(Reference.ModID + ":" + "clay/Ceramic");
		this.Straw = iconRegisterer.registerIcon(Reference.ModID + ":" + "plants/Straw");
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		return Straw;
	}

	@Override
	public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return this.getIcon(par5, par1IBlockAccess.getBlockMetadata(par2, par3, par4));
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		if(side == ForgeDirection.UP)
			return true;
		return false;
	}

	public int idDropped(int i, Random random, int j)
	{
		return 0;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			TileEntityPottery te = (TileEntityPottery) world.getTileEntity(x, y, z);
			if(player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() == TFCItems.Straw && !player.isSneaking())
			{
				te.addStraw(player.inventory.getCurrentItem());
				return true;
			}
			else if(player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() instanceof ItemLogs && !player.isSneaking() && te.straw == 8)
			{
				te.addLog(player.inventory.getCurrentItem());
				return true;
			}
			else if((player.inventory.getCurrentItem() == null || !(player.inventory.getCurrentItem().getItem() instanceof ItemPotteryBase)))
			{
				if(te.wood > 0)
				{
					te.ejectItem(3+te.wood);
					te.wood--;
					//te.broadcastPacketInRange(te.createUpdatePacket());
					world.markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);
				}
				else if(te.wood == 0 && side == 1 && player.isSneaking())
				{
					if(hitX < 0.5 && hitZ < 0.5)
						te.ejectItem(0);
					else if(hitX > 0.5 && hitZ < 0.5)
						te.ejectItem(1);
					else if(hitX < 0.5 && hitZ > 0.5)
						te.ejectItem(2);
					else if(hitX > 0.5 && hitZ > 0.5)
						te.ejectItem(3);
					world.markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);
					//te.broadcastPacketInRange(te.createUpdatePacket());
				}
			}
		}
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		TileEntityPottery te = (TileEntityPottery) world.getTileEntity(i, j, k);
		int h =te.straw == 0 ? 1 : te.straw;
		int w = (te.wood > 0 ? 1 : 0) + (te.wood > 4 ? 1 : 0);
		return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + (0.0625f*h) + (0.25f * w), k + 1);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int i, int j, int k) 
	{
		TileEntityPottery te = (TileEntityPottery) access.getTileEntity(i, j, k);
		int h =te.straw == 0 ? 1 : te.straw;
		int w = (te.wood > 0 ? 1 : 0) + (te.wood > 4 ? 1 : 0);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, (0.0625f*h) + (0.25f * w), 1.0F);
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

	public void Eject(World world, int par2, int par3, int par4)
	{
		if(!world.isRemote && (TileEntityPottery)world.getTileEntity(par2, par3, par4)!=null)
		{
			TileEntityPottery te;
			te = (TileEntityPottery)world.getTileEntity(par2, par3, par4);
			te.ejectContents();
			world.removeTileEntity(par2, par3, par4);
		}
	}

	@Override
	public boolean removedByPlayer(World par1World, EntityPlayer player, int par2, int par3, int par4)
	{
		Eject(par1World,par2,par3,par4);
		return par1World.setBlockToAir(par2,par3,par4);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntityPottery();
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block)
	{
		if(!world.isRemote)
			if(!world.getBlock(i, j-1, k).isOpaqueCube())
			{
				((TileEntityPottery)world.getTileEntity(i, j, k)).ejectContents();
				world.setBlockToAir(i, j, k);
				return;
			}
	}
}
