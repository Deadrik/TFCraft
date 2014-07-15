package com.bioxx.tfc.Blocks.Devices;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.TileEntities.TEGrill;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGrill extends BlockTerraContainer
{
	public BlockGrill()
	{
		super();
		this.setBlockBounds(0, 0, 0, 1, 0.05f, 1);
		this.setCreativeTab(TFCTabs.TFCDevices);
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



	/***********************************************************************************
	 * 
	 * Client Only Code Below This Point
	 * 
	 ***********************************************************************************/

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int i, int j, int k, int meta)
	{
		TEGrill te = (TEGrill) access.getTileEntity(i, j, k);
		if(te!= null)
			return TFC_Textures.SheetWroughtIron;//Change me later to use a metal type defined in the TE
		else
			return TFC_Textures.SheetWroughtIron;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j)
	{
		return TFC_Textures.SheetWroughtIron;
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
