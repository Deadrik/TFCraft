package com.bioxx.tfc.Handlers;

import net.minecraftforge.event.terraingen.BiomeEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import com.bioxx.tfc.WorldGen.BiomeDecoratorTFC;
import com.bioxx.tfc.WorldGen.TFCBiome;

public class BiomeEventHandler
{
	@SubscribeEvent
	public void onCreateDecorator(BiomeEvent.CreateDecorator event)
	{
		event.newBiomeDecorator = new BiomeDecoratorTFC((TFCBiome) event.biome);
	}
}
