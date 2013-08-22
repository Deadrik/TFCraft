package TFC.TileEntities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

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

        float bAir = airFromBellows*(1+(float)airFromBellowsTime/120);

        AddedAir = (float)(26+bAir)/25/16;//1038.225 Max //0.3625

        AddedAir += 0.1045F;//Added to make up for removing the height from the equation.

        return fuelBurnTemp + (fuelBurnTemp * AddedAir);
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
	public void handleDataPacket(DataInputStream inStream) throws IOException
	{
		
	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException
	{
		
	}

	@Override
	public void handleInitPacket(DataInputStream inStream) throws IOException
	{
		
	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream) throws IOException 
	{
		
	}
}
