package TFC.TileEntities;

import net.minecraft.tileentity.TileEntity;

public class TileEntityFireEntity extends TileEntity
{
	public int airFromBellows;
	public int fireTemp;
	public int maxFireTemp;
	public int fuelTimeLeft;
	public int fuelBurnTemp;

	public static final int AIRTOADD = 200;

	public TileEntityFireEntity()
	{
		airFromBellows = 0;
	}

	public void receiveAirFromBellows()
	{
		if(airFromBellows < AIRTOADD*3)
			airFromBellows += AIRTOADD;
		if(airFromBellows > AIRTOADD*3)
			airFromBellows = AIRTOADD*3;
	}

	public void keepTempToRange()
	{
		if(fireTemp > maxFireTemp)
			fireTemp = maxFireTemp;
		else if(fireTemp < 0)
			fireTemp = 0;
	}

	public int getTemperatureScaled(int s)
	{
		return fireTemp * s / maxFireTemp;
	}

	int handleTemp()
	{
		if(fuelTimeLeft > 0)
		{
			fuelTimeLeft--;
			if(airFromBellows == 0)
				fuelTimeLeft--;
		}
		else if(fuelTimeLeft < 0)
			fuelTimeLeft = 0;
		if(fuelTimeLeft > 0)
			return fuelBurnTemp + airFromBellows;
		else
			return 0;
	}

	public void handleAirReduction()
	{
		if(airFromBellows > 0)
		{
			airFromBellows--;
		}
	}

	public void handleTempFlux(float desiredTemp)
	{
		if(fireTemp < desiredTemp)
		{
			if(airFromBellows == 0)
			{
				fireTemp++;
			}
			else
			{
				fireTemp+=2;
			}
		}
		else if(fireTemp > desiredTemp)
		{
			if(desiredTemp == 0)
			{
				if(airFromBellows == 0)
				{
					fireTemp-=2;
				}
				else
				{
					fireTemp-=1;
				}
			}
		}
	}
}
