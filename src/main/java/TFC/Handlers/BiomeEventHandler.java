package TFC.Handlers;

import net.minecraftforge.event.terraingen.BiomeEvent;
import TFC.WorldGen.BiomeDecoratorTFC;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BiomeEventHandler
{
	@SubscribeEvent
	public void onCreateDecorator(BiomeEvent.CreateDecorator event)
	{
		event.newBiomeDecorator = new BiomeDecoratorTFC(event.biome);
	}
}
