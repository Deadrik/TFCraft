package com.bioxx.tfc.ModSupport;

import java.util.List;

import cpw.mods.fml.common.Optional.Method;
import weather2.ServerTickHandler;
import weather2.weathersystem.WeatherManagerServer;
import weather2.weathersystem.storm.StormObject;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Weather2 implements IWeather2
{

	public Weather2() {
		
		
		
	}

	
	@Method(modid="weather2")
	public boolean isRainingOnCoord(World worldObj, int xCoord, int yCoord, int zCoord) 
	{
		int dim = worldObj.provider.dimensionId;
		WeatherManagerServer wms = ServerTickHandler.lookupDimToWeatherMan.get(dim);
		Vec3 startVec3 = Vec3.createVectorHelper(xCoord, yCoord, zCoord);
		
		List<StormObject> storms =  wms.getStormsAround(startVec3, 150);

		for (int i = 0; i < storms.size(); i++) {
			StormObject storm = storms.get(i);
			
			if (storm != null && storm.isPrecipitating()) 
			{
				double radius = (double) storm.size / 0.75;
				Vec3 location = storm.pos;
				if (startVec3.distanceTo(location) < radius)
				{
					return true;				
				}	
			}
		}
		return false;	
	}



}
