package com.bioxx.tfc.Blocks.Flora;

import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Climate;

public class BlockFlower2 extends BlockFlower
{
	public BlockFlower2()
	{
		super();
		flowerNames = new String[]{"flower_rose", "flower_blue_orchid", "flower_allium", "flower_houstonia", "flower_tulip_red", "flower_tulip_orange", "flower_tulip_white", "flower_tulip_pink", "flower_oxeye_daisy"};
	}

	@Override
	public boolean canGrowConditions(World world, int x, int y, int z, int flowerMeta)
	{
		//float evt = TFC_Climate.getCacheManager(world).getEVTLayerAt(x, z).floatdata1;
		float rain = TFC_Climate.getRainfall(world, x, 144, z);
		float bioTemperature =TFC_Climate.getBioTemperatureHeight(world, x, y, z) ;
		return bioTemperature > 5 && rain > 250;
	}
}
