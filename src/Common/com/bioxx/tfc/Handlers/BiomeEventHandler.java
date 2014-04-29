package com.bioxx.tfc.Handlers;

import net.minecraftforge.event.terraingen.BiomeEvent;

import com.bioxx.tfc.WorldGen.BiomeDecoratorTFC;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BiomeEventHandler
{
	@SubscribeEvent
	public void onCreateDecorator(BiomeEvent.CreateDecorator event)
	{
		event.newBiomeDecorator = new BiomeDecoratorTFC(event.biome);
	}
}
