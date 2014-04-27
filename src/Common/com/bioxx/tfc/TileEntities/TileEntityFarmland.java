package com.bioxx.tfc.TileEntities;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityFarmland extends TileEntity
{
	public long nutrientTimer = -1;
	public int[] nutrients = {6666,6666,6666, 0};

	/**
	 * Client only
	 * */
	public long timeSinceUpdate = 0;

	public TileEntityFarmland()
	{
	}

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			if(nutrientTimer <= 0)
				nutrientTimer = TFC_Time.getTotalHours();

			if(nutrientTimer < TFC_Time.getTotalHours())
			{
				CropIndex crop = null;
				int soilMax = getSoilMax();
				int restoreAmount = 139;

				if((worldObj.getBlock(xCoord, yCoord + 1, zCoord) == TFCBlocks.Crops))
				{
					crop = CropManager.getInstance().getCropFromId(((TECrop)worldObj.getTileEntity(xCoord, yCoord + 1, zCoord)).cropId);

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
				}

				if(nutrients[0] > soilMax)
					nutrients[0] = soilMax;
				if(nutrients[1] > soilMax)
					nutrients[1] = soilMax;
				if(nutrients[2] > soilMax)
					nutrients[2] = soilMax;

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

	public int getSoilMax()
	{
		float timeMultiplier = TFC_Time.daysInYear / 360f;
		return (int) (25000 * timeMultiplier);
	}

	public void DrainNutrients(int type, float multiplier)
	{
		float timeMultiplier = 360f / TFC_Time.daysInYear;
		nutrients[type] -= (100 * multiplier) * timeMultiplier;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		nutrients = nbt.getIntArray("nutrients");
		nutrientTimer = nbt.getLong("nutrientTimer");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setIntArray("nutrients", nutrients);
		nbt.setLong("nutrientTimer", nutrientTimer); 
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.func_148857_g());
	}

	public void requestNutrientData()
	{
		if(TFC_Time.getTotalTicks() > timeSinceUpdate + 1000)
		{
			timeSinceUpdate = TFC_Time.getTotalTicks();
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			//TerraFirmaCraft.proxy.sendCustomPacket(createNutrientRequestPacket());
		}
	}







//	
//	@Override
//	public void handleDataPacket(DataInputStream inStream) throws IOException 
//	{
//		nutrients[0] = inStream.readInt();
//		nutrients[1] = inStream.readInt();
//		nutrients[2] = inStream.readInt();		
//		nutrients[3] = inStream.readInt();	
//	}
//
//	@Override
//	public void handleDataPacketServer(DataInputStream inStream) throws IOException {
//		TerraFirmaCraft.proxy.sendCustomPacketToPlayersInRange(xCoord, yCoord, zCoord, createNutrientPacket(), 5);
//	}
//
//
//	public Packet createNutrientRequestPacket()
//	{
//		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
//		DataOutputStream dos=new DataOutputStream(bos);
//
//		try {
//			dos.writeByte(PacketHandler.Packet_Data_Block_Server);
//			dos.writeInt(xCoord);
//			dos.writeInt(yCoord);
//			dos.writeInt(zCoord);
//		} catch (IOException e) {
//		}
//
//		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
//	}
//
//	public Packet createNutrientPacket()
//	{
//		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
//		DataOutputStream dos=new DataOutputStream(bos);
//
//		try {
//			dos.writeByte(PacketHandler.Packet_Data_Block_Client);
//			dos.writeInt(xCoord);
//			dos.writeInt(yCoord);
//			dos.writeInt(zCoord);
//			dos.writeInt(nutrients[0]);
//			dos.writeInt(nutrients[1]);
//			dos.writeInt(nutrients[2]);
//			dos.writeInt(nutrients[3]);
//		} catch (IOException e) {
//		}
//
//		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
//	}
}
