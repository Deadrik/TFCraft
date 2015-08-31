package com.bioxx.tfc.Blocks.Devices;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.TileEntities.TESpawnMeter;

public class BlockSpawnMeter extends BlockTerraContainer
{
	private IIcon iconTop;
	private IIcon[] icons = new IIcon[9];

	public BlockSpawnMeter()
	{
		super(Material.rock);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
		this.setLightLevel(1F);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) 
	{
		int meta = world.getBlockMetadata(x, y, z);
		return Math.min(meta, 8);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return true;
	}

	@Override
	public int damageDropped(int j) 
	{
		return j;
	}

	@Override
	public IIcon getIcon(int i, int j) 
	{
		if(i < 2)
			return iconTop;
		return icons[j];
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		iconTop = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/MeterTop");
		for(int i = 0; i < 9; i++)
			icons[i] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Meter"+i);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TESpawnMeter();
	}
}
