package com.bioxx.tfc.Blocks.Liquids;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Sounds;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFreshWater extends BlockCustomLiquid
{
	public BlockFreshWater(Fluid fluid)
	{
		super(fluid, Material.water);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
	 * when first determining what to render.
	 */
	public int colorMultiplier(IBlockAccess bAccess, int x, int y, int z)
	{
		if (this.blockMaterial != Material.water)
			return 16777215;
		else
			return TerraFirmaCraft.proxy.waterColorMultiplier(bAccess, x, y, z);
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, Block block, int l)
	{
		//TODO break a frozen block back into fresh water flowing block
		super.breakBlock(world, i, j, k, block, l);
	}

	@Override
	public void updateTick(World w, int x, int y, int z, Random rand)
	{
		// Play frog sound at night
		if(!w.isRemote && w.getBlockLightValue(x, y, z) < 7 && TFC_Climate.getHeightAdjustedTemp(x, y, z) > 10 && w.isAirBlock(x, y+1, z))
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
}
