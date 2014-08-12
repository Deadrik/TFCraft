package com.bioxx.tfc.Blocks.Devices;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Items.ItemBlocks.ItemMetalTrapDoor;
import com.bioxx.tfc.TileEntities.TEGrill;
import com.bioxx.tfc.api.TFCOptions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGrill extends BlockTerraContainer
{
	public BlockGrill()
	{
		super(Material.iron);
		this.setBlockBounds(0, 0, 0, 1, 0.05f, 1);
		this.setCreativeTab(TFCTabs.TFCDevices);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		super.onBlockActivated(world, x, y, z, entityplayer, side, hitX, hitY, hitZ);
		if(entityplayer.inventory.getCurrentItem() != null && entityplayer.inventory.getCurrentItem().getItem() instanceof ItemMetalTrapDoor)
			return false;
		TEGrill teb = (TEGrill)world.getTileEntity(x, y, z);
		entityplayer.openGui(TerraFirmaCraft.instance, 43, world, x, y, z);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEGrill();
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}

	/***********************************************************************************
	 * 
	 * Client Only Code Below This Point
	 * 
	 ***********************************************************************************/
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Grill Wrought Iron");
	}

	/*@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int i, int j, int k, int meta)
	{
		TEGrill te = (TEGrill) access.getTileEntity(i, j, k);
		if(te!= null)
			return TFC_Textures.SheetWroughtIron;//Change me later to use a metal type defined in the TE
		else
			return TFC_Textures.SheetWroughtIron;
	}*/

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if(meta == 0)
		{
			if(TFCOptions.use2DGrill && (side == 0 || side == 1))
			{
				return blockIcon;
			}
			return TFC_Textures.SheetWroughtIron;
		}
		return blockIcon;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{
		return TFCBlocks.grillRenderId;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		return true;
	}
}
