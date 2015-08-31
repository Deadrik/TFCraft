package com.bioxx.tfc.Blocks.Devices;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.TileEntities.TEStand;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Interfaces.IEquipable;
import com.bioxx.tfc.api.Interfaces.IMultipleBlock;

public class BlockStand2 extends BlockStand implements IMultipleBlock, IEquipable
{
	private String[] woodNames;
	public BlockStand2()
	{
		super();
		this.setCreativeTab(TFCTabs.TFC_DECORATION);
		this.setBlockBounds(0.125f, 0, 0.125f, 0.875f, 1, 0.875f);
		woodNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, woodNames, 0,Global.WOOD_ALL.length - 16);
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "wood/BarrelHoop");
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if(side == 0 || side == 1)
			return TFC_Textures.invisibleTexture;
		else
			return this.blockIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		for(int i = 0; i < woodNames.length; i++)
			list.add(new ItemStack(this, 1, i));
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack is)
	{
		super.onBlockPlacedBy(world, i, j, k, entityliving, is);
		TileEntity te = world.getTileEntity(i,j,k);
		if (te instanceof TEStand)
		{
			TEStand tes = (TEStand) te;
			tes.yaw = (((int) (entityliving.rotationYaw % 360 + 360 + 45) / 90) * 90) % 360;
			if(tes.yaw % 180 == 0)
				tes.yaw+=180;
			world.setBlock(i,j+1,k,this);
			world.setBlockMetadataWithNotify(i, j+1, k, is.getItemDamage(),0);
			world.setTileEntity(i, j+1, k, new TEStand());
			((TEStand)world.getTileEntity(i, j+1, k)).isTop = true;
		}
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.standRenderId;
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		super.onBlockAdded(par1World, par2, par3, par4);
	}


	@Override
	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion par5Explosion)
	{
	}

	@Override
	public Block getBlockTypeForRender()
	{
		return TFCBlocks.planks2;
	}
}
