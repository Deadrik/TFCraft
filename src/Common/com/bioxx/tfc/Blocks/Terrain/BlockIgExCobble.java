package com.bioxx.tfc.Blocks.Terrain;

import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockIgExCobble extends BlockCobble
{
	public BlockIgExCobble(Material material)
	{
		super(material);
		fallInstantly = false;
		names = Global.STONE_IGEX;
		icons = new IIcon[names.length];
	}

	@Override
	public int tickRate(World world)
	{
		return 3;
	}
}
