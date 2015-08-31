package com.bioxx.tfc.Blocks.Vanilla;

import net.minecraft.block.BlockPumpkin;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Core.TFCTabs;

public class BlockCustomPumpkin extends BlockPumpkin
{
	private boolean isLit;
	@SideOnly(Side.CLIENT)
	private IIcon topIcon;
	@SideOnly(Side.CLIENT)
	private IIcon faceIcon;

	public BlockCustomPumpkin(boolean lit)
	{
		super(lit);
		this.isLit = lit;
		this.setCreativeTab(TFCTabs.TFC_BUILDING);
	}

	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return side == 1 ? this.topIcon : side == 0 ? this.topIcon : // Top or Bottom Side
        	meta == 2 && side == 2 ? this.faceIcon : meta == 3 && side == 5 ? this.faceIcon : // Face Side
        		meta == 0 && side == 3 ? this.faceIcon : meta == 1 && side == 4 ? this.faceIcon : // Face Side
        			this.blockIcon; // Blank Side
    }

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		// Intentionally blank to override the creation of snow and iron golems.
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.topIcon = iconRegister.registerIcon(this.getTextureName() + "_top");
		this.blockIcon = iconRegister.registerIcon(this.getTextureName() + "_side");
		this.faceIcon = this.isLit ? iconRegister.registerIcon(this.getTextureName() + "_face_on") : this.blockIcon; // Only have a face when lit.
	}

}
