package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import TFC.*;
import TFC.Blocks.BlockFarmland;
import TFC.Core.TFC_Time;
import TFC.Food.CropIndex;
import TFC.Food.CropManager;
import TFC.Handlers.PacketHandler;
import TFC.Items.ItemOre;
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

public class TileEntityFarmland extends NetworkTileEntity
{
    public long nutrientTimer = -1;
    public int[] nutrients = {18000,18000,18000};
    
    /**
     * Client only
     * */
    public long timeSinceUpdate = 0;

    public TileEntityFarmland()
    {
    	this.shouldSendInitData = false;
    }

    @Override
    public void updateEntity()
    {
        if(!worldObj.isRemote)
        {
            if(nutrientTimer <= 0)
                nutrientTimer = TFC_Time.totalHours();
            
            if(nutrientTimer < TFC_Time.totalHours())
            {
                CropIndex crop = null;
                float timeMultiplier = (float)TFC_Time.daysInYear / 360f;
                int soilMax = (int) (25000 * timeMultiplier);
                int restoreAmount = 65;
                
                if((worldObj.getBlockId(xCoord, yCoord+1, zCoord) == Block.crops.blockID))
                {
                    crop = CropManager.getInstance().getCropFromId(((TileEntityCrop)worldObj.getBlockTileEntity(xCoord, yCoord+1, zCoord)).cropId);
                    
                    if((crop.cycleType != 0))
                    {
                        if(nutrients[0] < soilMax)
                            nutrients[0] += restoreAmount + crop.nutrientExtraRestore[0];
                    }
                    if((crop.cycleType != 1))
                    {
                        if(nutrients[1] < soilMax)
                            nutrients[1] += restoreAmount + crop.nutrientExtraRestore[1];
                    }
                    if((crop.cycleType != 2))
                    {
                        if(nutrients[2] < soilMax)
                            nutrients[2] += restoreAmount + crop.nutrientExtraRestore[2];
                    }
                }
                else
                {
                    if(nutrients[0] < soilMax)
                        nutrients[0] += restoreAmount;
                    if(nutrients[1] < soilMax)
                        nutrients[1] += restoreAmount;
                    if(nutrients[2] < soilMax)
                        nutrients[2] += restoreAmount;
                    
                    if(nutrients[0] > soilMax)
                        nutrients[0] = soilMax;
                    if(nutrients[1] > soilMax)
                        nutrients[1] = soilMax;
                    if(nutrients[2] > soilMax)
                        nutrients[2] = soilMax;
                }

                nutrientTimer+=24;
                
//                if(BlockFarmland.isWaterNearby(worldObj, xCoord, yCoord, zCoord))
//                {
//                    waterSaturation += 1;
//                    if(waterSaturation > 30)
//                        waterSaturation = 30;
//                }
//                else if((worldObj.getBlockId(xCoord, yCoord+1, zCoord) == Block.crops.blockID) && crop != null)
//                {
//                    waterSaturation -= 1*crop.waterUsageMult;
//                }
//                
//                if(worldObj.isRaining() && worldObj.canBlockSeeTheSky(xCoord, yCoord+1, zCoord))
//                {
//                    waterSaturation += 3;
//                    if(waterSaturation > 30)
//                        waterSaturation = 30;
//                }
            }
        }
    }

    public void DrainNutrients(int type, float multiplier)
    {
        nutrients[type] -= 100*multiplier;
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        
        nutrients = par1NBTTagCompound.getIntArray("nutrients");
        nutrientTimer = par1NBTTagCompound.getLong("nutrientTimer");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setIntArray("nutrients", nutrients);
        par1NBTTagCompound.setLong("nutrientTimer", nutrientTimer);    
    }

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException 
	{
		nutrients[0] = inStream.readInt();
		nutrients[1] = inStream.readInt();
		nutrients[2] = inStream.readInt();		
	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream) throws IOException {
		TerraFirmaCraft.proxy.sendCustomPacketToPlayersInRange(xCoord, yCoord, zCoord, createNutrientPacket(), 5);
	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleInitPacket(DataInputStream inStream) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	public void requestNutrientData()
	{
		if(TFC_Time.getTotalTicks() > timeSinceUpdate + 1000)
		{
			timeSinceUpdate = TFC_Time.getTotalTicks();
			TerraFirmaCraft.proxy.sendCustomPacket(createNutrientRequestPacket());
		}
	}
	
	public Packet createNutrientRequestPacket()
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);

		try {
			dos.writeByte(PacketHandler.Packet_Data_Block_Server);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
		} catch (IOException e) {
		}

		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}
	
	public Packet createNutrientPacket()
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);

		try {
			dos.writeByte(PacketHandler.Packet_Data_Block_Client);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeInt(nutrients[0]);
			dos.writeInt(nutrients[1]);
			dos.writeInt(nutrients[2]);
		} catch (IOException e) {
		}

		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}
}
