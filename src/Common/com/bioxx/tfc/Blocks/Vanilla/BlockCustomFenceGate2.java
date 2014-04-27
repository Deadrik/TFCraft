package com.bioxx.tfc.Blocks.Vanilla;

import java.util.List;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TileEntities.TileEntityFenceGate;
import com.bioxx.tfc.api.IMultipleBlock;
import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.ITileEntityProvider;
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

public class BlockCustomFenceGate2 extends BlockFenceGate implements ITileEntityProvider, IMultipleBlock
{
	String[] woodNames;
	IIcon[] icons;

	public BlockCustomFenceGate2() {
		super();
		woodNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, woodNames, 0, Global.WOOD_ALL.length - 16);
		icons = new IIcon[woodNames.length];
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		int l = (par1World.getTileEntity(par2, par3, par4)!=null)?(((TileEntityFenceGate)(par1World.getTileEntity(par2, par3, par4))).getDirection()):0;
		boolean open = (par1World.getTileEntity(par2, par3, par4)!=null)?(((TileEntityFenceGate)(par1World.getTileEntity(par2, par3, par4))).getOpen()):false;
		return open ? null : (l != 2 && l != 0 ? AxisAlignedBB.getAABBPool().getAABB((double)((float)par2 + 0.375F), (double)par3, (double)par4, (double)((float)par2 + 0.625F), (double)((float)par3 + 1.5F), (double)(par4 + 1)) : AxisAlignedBB.getAABBPool().getAABB((double)par2, (double)par3, (double)((float)par4 + 0.375F), (double)(par2 + 1), (double)((float)par3 + 1.5F), (double)((float)par4 + 0.625F)));
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
			icons[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "wood/" + woodNames[i] + " Plank");
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.FenceGateRenderId;
	}

	@Override
	public IIcon getIcon(int par1, int par2)
	{
		return icons[Math.min(par2, icons.length-1)];
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
		return ((TileEntityFenceGate)(par1IBlockAccess.getTileEntity(par2, par3, par4))).getOpen();
	}

	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int l = ((TileEntityFenceGate)par1IBlockAccess.getTileEntity(par2, par3, par4)).getDirection();

		if (l != 2 && l != 0)
			this.setBlockBounds(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
		else
			this.setBlockBounds(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		int l = (MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) % 4;
		((TileEntityFenceGate)(par1World.getTileEntity(par2, par3, par4))).setDirection((byte)l);

		//par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		TileEntityFenceGate te = (TileEntityFenceGate)par1World.getTileEntity(par2, par3, par4);

		if (te.getOpen())
			te.setOpen(false);
		else
			te.setOpen(true);

		par1World.playAuxSFXAtEntity(par5EntityPlayer, 1003, par2, par3, par4, 0);
		return true;
	}

	@Override
	public Block getBlockTypeForRender()
	{
		return TFCBlocks.FenceGate2;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntityFenceGate();
	}
}
