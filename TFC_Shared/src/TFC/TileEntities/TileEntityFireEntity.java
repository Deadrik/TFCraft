package TFC.TileEntities;

import net.minecraft.src.TileEntity;

public class TileEntityFireEntity extends TileEntity
{
	public float airFromBellows;
	public float airFromBellowsTime;



	public TileEntityFireEntity()
	{
		airFromBellows = 0F;
		airFromBellowsTime = 0;
	}



	public void receiveAirFromBellows()
	{
		if(airFromBellowsTime < 360) {
			airFromBellowsTime += 120;
		}
		if(airFromBellowsTime > 360) {
			airFromBellowsTime = 360;
		}
	}

}
