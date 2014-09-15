package com.bioxx.tfc.Blocks.Devices;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Items.ItemLogs;
import com.bioxx.tfc.Items.Pottery.ItemPotteryBase;
import com.bioxx.tfc.TileEntities.TEPottery;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
	public IIcon getIcon(IBlockAccess bAccess, int x, int y, int z, int side)
	{
		return this.getIcon(side, bAccess.getBlockMetadata(x, y, z));
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		if(side == ForgeDirection.UP)
			return true;
		return false;
	}

	public int idDropped(int metadata, Random rand, int fortune)
	{
		return 0;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			TEPottery te = (TEPottery) world.getTileEntity(x, y, z);

			if (te.isLit() || player.inventory.getCurrentItem() != null && 
					(player.inventory.getCurrentItem().getItem() == TFCItems.FlintSteel || player.inventory.getCurrentItem().getItem() == TFCItems.FireStarter))
				return false;

			if(player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() == TFCItems.Straw && !player.isSneaking())
			{
				te.addStraw(player.inventory.getCurrentItem(), player);
				return true;
			}
			else if(player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() instanceof ItemLogs && !player.isSneaking() && te.straw == 8)
			{
				te.addLog(player.inventory.getCurrentItem(), player);
				return true;
			}
			else if(((player.inventory.getCurrentItem() == null || !(player.inventory.getCurrentItem().getItem() instanceof ItemPotteryBase))))
			{
				if(te.wood > 0)
				{
					te.ejectItem(3+te.wood);
					te.wood--;
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
				}
			}
		}
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		TEPottery te = (TEPottery) world.getTileEntity(x, y, z);
		int h = 0;
		int w = 0;
		if(te!= null)
		{
			h = te.straw == 0 ? 1 : te.straw;
			w = (te.wood > 0 ? 1 : 0) + (te.wood > 4 ? 1 : 0);
		}
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + (0.0625f * h) + (0.25f * w), z + 1);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z)
	{
		TEPottery te = (TEPottery) access.getTileEntity(x, y, z);
		int h = 0;
		int w = 0;
		if(te!= null)
		{
			h = te.straw == 0 ? 1 : te.straw;
			w = (te.wood > 0 ? 1 : 0) + (te.wood > 4 ? 1 : 0);
		}
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

	public void Eject(World world, int x, int y, int z)
	{
		if(!world.isRemote && (TEPottery)world.getTileEntity(x, y, z) != null)
		{
			TEPottery te;
			te = (TEPottery)world.getTileEntity(x, y, z);
			te.ejectContents();
			world.removeTileEntity(x, y, z);
		}
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		Eject(world, x, y, z);
		return world.setBlockToAir(x, y, z);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TEPottery();
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if(!world.isRemote)
		{
			if(!world.isSideSolid(x, y - 1, z, ForgeDirection.UP))
			{
				((TEPottery)world.getTileEntity(x, y, z)).ejectContents();
				world.setBlockToAir(x, y, z);
				return;
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side)
	{
		return true;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}
}
