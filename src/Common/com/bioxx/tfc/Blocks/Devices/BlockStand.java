package com.bioxx.tfc.Blocks.Devices;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.TileEntities.TEStand;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Interfaces.IEquipable;
import com.bioxx.tfc.api.Interfaces.IMultipleBlock;

public class BlockStand extends BlockTerraContainer implements IMultipleBlock, IEquipable
{
	private String[] woodNames;
	public BlockStand()
	{
		super(Material.wood);
		this.setCreativeTab(TFCTabs.TFC_DECORATION);
		this.setBlockBounds(0.125f, 0, 0.125f, 0.875f, 1, 0.875f);
		woodNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, woodNames, 0,16);
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

	/**
	 * Returns the block texture based on the side being looked at.  Args: side
	 */
	/*@Override
	public Icon getBlockTextureFromSideAndMetadata(int par1)
	{
		return par1 == 1 ? this.blockIndexInTexture - 1 : (par1 == 0 ? this.blockIndexInTexture - 1 : (par1 == 3 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture));
	}*/

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5)
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEStand();
	}

	@Override
	public Block getBlockTypeForRender()
	{
		return TFCBlocks.planks;
	}

	@Override
	public EquipType getEquipType(ItemStack is) {
		return null;
	}

	@Override
	public void onEquippedRender() {
		GL11.glTranslatef(-0.0F, -0.8F, -0.63F);
		GL11.glScalef(1.8F, 1.8F, 1.8F);
		GL11.glRotatef(90, 0F, 1F, 0F);
		//GL11.glRotatef(180, 0, 0, 1);
	}

	@Override
	public boolean getTooHeavyToCarry(ItemStack is)
	{
		return false;
	}
}
