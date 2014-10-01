package com.bioxx.tfc.Blocks.Liquids;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Sounds;

public class BlockFreshWater extends BlockCustomLiquid
{
	public BlockFreshWater(Fluid fluid)
	{
		super(fluid, Material.water);
	}

	@Override
	public void updateTick(World w, int x, int y, int z, Random rand)
	{
		// Play frog sound at night
		if(!w.isRemote && w.getBlockLightValue(x, y, z) < 7 && TFC_Climate.getHeightAdjustedTemp(w, x, y, z) > 10 && w.isAirBlock(x, y+1, z))
		{
			if(w.rand.nextInt(100) < 25)
			{
				boolean closeToShore = false;
				for(int x1 = x-6; x1 <x+6 && !closeToShore;x1++){
					for(int z1 = z-6; z1 <z+6  && !closeToShore;z1++){
						if(TFC_Core.isGrass(w.getBlock(x1, y, z1))){
							closeToShore = true;
						}
					}
				}
				if(closeToShore){
					float mod = w.rand.nextFloat();
					w.playSoundEffect(x, y, z, TFC_Sounds.FROG, (mod < 0.55F ? mod : 0.55F), (mod < 0.41F ? mod + 0.8F : 0.8F));
				}
			}
		}
		super.updateTick(w, x, y, z, rand);
	}

	@Override
	protected Block getInverseBlock() 
	{
		return TFCBlocks.FreshWaterStationary;
	}
}
