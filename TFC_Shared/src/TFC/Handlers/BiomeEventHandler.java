package TFC.Handlers;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.terraingen.BiomeEvent;
import TFC.WorldGen.BiomeDecoratorTFC;

public class BiomeEventHandler 
{
	@ForgeSubscribe
	public void onCreateDecorator(BiomeEvent.CreateDecorator event) 
	{
		event.newBiomeDecorator = new BiomeDecoratorTFC(event.biome);
	}
}
