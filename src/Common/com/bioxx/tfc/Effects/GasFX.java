package com.bioxx.tfc.Effects;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Textures;

public class GasFX extends EntityFX
{

	public GasFX(World world, double par2, double par4, double par6,
			double par8, double par10, double par12) 
	{
		super(world, par2, par4, par6, par8, par10, par12);
		this.setParticleIcon(TFC_Textures.gasFXIcon);
		this.setSize(1f, 1f);
		this.particleMaxAge = (int)(12.0F / (this.rand.nextFloat() * 0.9F + 0.1F));
		this.particleAlpha = 0.05f;
	}

	@Override
	public int getFXLayer()
	{
		return 1;
	}

}
