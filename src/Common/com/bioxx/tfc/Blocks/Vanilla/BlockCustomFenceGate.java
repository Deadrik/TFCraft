package com.bioxx.tfc.Blocks.Vanilla;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.TileEntities.TEFenceGate;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Interfaces.IMultipleBlock;

public class BlockCustomFenceGate extends BlockFenceGate implements ITileEntityProvider, IMultipleBlock
{
	public String[] woodNames;
	public IIcon[] icons;
	public BlockCustomFenceGate()
	{
		super();
		woodNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, woodNames, 0, 16);
		icons = new IIcon[woodNames.length];
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		int direction = 0;
		boolean open = false;
		TileEntity te = world.getTileEntity(x, y, z);

		if (te instanceof TEFenceGate) // Fixes any possible ClassCastExceptions
		{
			direction = ((TEFenceGate) te).getDirection();
			open = ((TEFenceGate) te).getOpen();
		}

		if (open)
			return null; // Gate is open so it has no bounding box.
		else
			return (direction != 2 && direction != 0 ? AxisAlignedBB.getBoundingBox(x + 0.375F, y, z, x + 0.625F, y + 1.5F, z + 1) : AxisAlignedBB.getBoundingBox(x, y, z + 0.375F, x + 1, y + 1.5F, z + 0.625F));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) 
	{
		for(int i = 0; i < woodNames.length; i++)
			par3List.add(new ItemStack(this, 1, i));
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		for(int i = 0; i < woodNames.length; i++)
			icons[i] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "wood/" + woodNames[i] + " Plank");
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.fenceGateRenderId;
	}

	@Override
	public IIcon getIcon(int par1, int par2)
	{
		return icons[par2];
	}

	@Override
	public int damageDropped(int par1)
	{
		return par1;
	}

	@Override
	public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return icons[par1IBlockAccess.getBlockMetadata(par2, par3, par4)];
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		if (par1IBlockAccess.getTileEntity(par3, par3, par4) instanceof TEFenceGate)
			return ((TEFenceGate) (par1IBlockAccess.getTileEntity(par2, par3, par4))).getOpen();
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int l = ((TEFenceGate)par1IBlockAccess.getTileEntity(par2, par3, par4)).getDirection();

		if (l != 2 && l != 0)
		{
			this.setBlockBounds(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
		}
		else
		{
			this.setBlockBounds(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
		}
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		int l = (MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3) % 4;
		((TEFenceGate)(par1World.getTileEntity(par2, par3, par4))).setDirection((byte)l);

		//par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		TEFenceGate te = (TEFenceGate)par1World.getTileEntity(par2, par3, par4);

		if (te.getOpen())
			te.setOpen(false);
		else
			te.setOpen(true);

		par1World.playAuxSFXAtEntity(par5EntityPlayer, 1003, par2, par3, par4, 0);
		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		TEFenceGate te = (TEFenceGate) world.getTileEntity(x, y, z);
		boolean flag = world.isBlockIndirectlyGettingPowered(x, y, z);

		if (flag || block.getMaterial() != Material.air && block.canProvidePower())
		{
			if (flag && !te.getOpen())
			{
				te.setOpen(true);
				world.playAuxSFXAtEntity((EntityPlayer) null, 1003, x, y, z, 0);
			}
			else if (!flag && te.getOpen())
			{
				te.setOpen(false);
				world.playAuxSFXAtEntity((EntityPlayer) null, 1003, x, y, z, 0);
			}

		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEFenceGate();
	}

	@Override
	public Block getBlockTypeForRender()
	{
		return TFCBlocks.fenceGate;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess bAccess, int x, int y, int z, int side)
	{
		return true;
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}
}
