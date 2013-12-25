package TFC.TileEntities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TileEntityFireEntity extends NetworkTileEntity
{
	public float airFromBellows;
	public float airFromBellowsTime;
	public float fireTemperature;
	public float ambientTemp;
	public float MaxFireTemp;
	public float fuelTimeLeft;
	public float fuelBurnTemp;
	public float AddedAir;

	public static final float AIRTOADD = 240;



	public TileEntityFireEntity()
	{
		airFromBellows = 0F;
		airFromBellowsTime = 0;
	}



	public void receiveAirFromBellows()
	{
		if(airFromBellowsTime < AIRTOADD*3) {
			airFromBellowsTime += AIRTOADD;
		}
		if(airFromBellowsTime > AIRTOADD*3) {
			airFromBellowsTime = AIRTOADD*3;
		}
	}

	public void keepTempToRange()
	{
		if(fireTemperature > MaxFireTemp) {
			fireTemperature = MaxFireTemp;
		} else if(fireTemperature < ambientTemp) {
			fireTemperature = ambientTemp;
		}
	}

	public int getTemperatureScaled(int s)
	{
		return (int) ((int)(fireTemperature * s) / MaxFireTemp);
	}

	public float handleTemp()
	{
		fuelTimeLeft--;
		if(airFromBellowsTime > 0)
		{
			fuelTimeLeft--;
		}

		float bAir = airFromBellows*(1+airFromBellowsTime/AIRTOADD);

		AddedAir = (26+bAir)/25/16;//1038.225 Max //0.3625

		AddedAir += 0.1045F;//Added to make up for removing the height from the equation.

		return fuelBurnTemp + (fuelBurnTemp * AddedAir);
	}

	public void handleAirReduction()
	{
		if(airFromBellowsTime > 0)
		{
			airFromBellowsTime--;
			airFromBellows = airFromBellowsTime/AIRTOADD*10;
		}
	}

	public void handleTempFlux(float desiredTemp)
	{
		if(fireTemperature < desiredTemp)
		{
			fireTemperature+=1.35F;
		}
		else if(fireTemperature > desiredTemp)
		{
			if(desiredTemp != ambientTemp)
			{
				if(airFromBellows == 0) {
					fireTemperature-=0.125F;
				} else {
					fireTemperature-=0.08F;
				}
			}
		}
	}



	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException {
		// TODO Auto-generated method stub

	}



	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException {
		// TODO Auto-generated method stub

	}



	@Override
	public void handleInitPacket(DataInputStream inStream) throws IOException {
		// TODO Auto-generated method stub

	}



	@Override
	public void handleDataPacketServer(DataInputStream inStream)
			throws IOException {
		// TODO Auto-generated method stub

	}
}
