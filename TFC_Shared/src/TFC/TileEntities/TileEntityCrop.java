package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import TFC.*;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Time;
import TFC.Food.CropIndex;
import TFC.Food.CropManager;
import TFC.Handlers.PacketHandler;
import TFC.Items.ItemOre;
import TFC.WorldGen.TFCBiome;
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

public class TileEntityCrop extends NetworkTileEntity
{
	public float growth;
	public int cropId;
	private long growthTimer;//Tracks the time since the plant was planted
	private long plantedTime;//Tracks the time when the plant was planted
	private byte sunLevel;

	public TileEntityCrop()
	{
		growth = 0.1f;
		plantedTime = TFC_Time.getTotalTicks();
		growthTimer = TFC_Time.getTotalTicks();
		sunLevel = 5;
	}

	private boolean checkedSun = false;
	@Override
	public void updateEntity()
	{
		Random R = new Random();
		if(!worldObj.isRemote && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 0)
		{
			float timeMultiplier = 360/TFC_Time.daysInYear;
			
			
			CropIndex crop = CropManager.getInstance().getCropFromId(cropId);

			long time = TFC_Time.getTotalTicks();

			if(growthTimer < time && sunLevel > 0)
			{
				if(crop.needsSunlight && worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord))
				{
					sunLevel++;
					if(sunLevel > 30) sunLevel = 30;
				}
				
				TileEntityFarmland tef = (TileEntityFarmland) worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord);

				float ambientTemp = TFC_Climate.getHeightAdjustedTempSpecificDay(TFC_Time.getDayOfYearFromTick(growthTimer), xCoord, yCoord, zCoord);
				float tempAdded = 0;

				if(!crop.dormantInFrost && ambientTemp < crop.minGrowthTemp)
				{
					tempAdded = -0.03f * (crop.minGrowthTemp - ambientTemp);
				}
				else if(crop.dormantInFrost && ambientTemp < crop.minGrowthTemp)
				{
					if(growth > 1)
						tempAdded = -0.03f * (crop.minGrowthTemp - ambientTemp);
				}
				else if(ambientTemp < 28)
				{
					tempAdded = ambientTemp* 0.00035f;
				}
				else if(ambientTemp < 37)
				{
					tempAdded = (28-(ambientTemp-28))* 0.0003f;
				}

				if(!crop.dormantInFrost && ambientTemp < crop.minAliveTemp)
				{
					worldObj.setBlock(xCoord, yCoord, zCoord, 0);
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
				else if(crop.dormantInFrost && ambientTemp < crop.minAliveTemp)
				{
					if(growth > 1)
					{
						worldObj.setBlock(xCoord, yCoord, zCoord, 0);
						worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					}
				}

				int nutriType = crop.cycleType;
				int nutri = tef != null ? tef.nutrients[nutriType] : 18000;
				float waterBoost = TFC.Blocks.BlockFarmland.isWaterNearby(worldObj, xCoord, yCoord, zCoord) ? 0.3f : 0;

				float nutriMult = (0.2f + ((float)nutri >= 18000 ? 1 : (float)nutri/18000) * 0.5f);

				if(tef != null)
				{
					if(tef.nutrients[nutriType] > 0)
						tef.DrainNutrients(nutriType, crop.nutrientUsageMult);
				}

				float growthRate = ((((float)crop.numGrowthStages/(float)crop.growthTime)+tempAdded)*nutriMult) * timeMultiplier;

				int oldGrowth = (int) Math.floor(growth);

				growth += growthRate;

				if(oldGrowth < (int) Math.floor(growth))
				{
					this.broadcastPacketInRange(createCropUpdatePacket());
				}

				if((crop.maxLifespan == -1 && growth > crop.numGrowthStages+((float)crop.numGrowthStages/2)) || growth < 0)
				{
					worldObj.setBlock(xCoord, yCoord, zCoord, 0);
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}

				growthTimer += (R.nextInt(2)+23)*TFC_Time.hourLength;
			}
			else if(crop.needsSunlight && sunLevel <= 0)
			{
				worldObj.setBlock(xCoord, yCoord, zCoord, 0);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
	}

	public float getEstimatedGrowth(CropIndex crop)
	{
		return ((float)crop.numGrowthStages/(growthTimer-plantedTime/TFC_Time.dayLength))*1.5f;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	 public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		growth = par1NBTTagCompound.getFloat("growth");
		cropId = par1NBTTagCompound.getInteger("cropId");
		growthTimer = par1NBTTagCompound.getLong("growthTimer");
		plantedTime = par1NBTTagCompound.getLong("plantedTime");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	 public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setFloat("growth", growth);
		nbt.setInteger("cropId", cropId);
		nbt.setLong("growthTimer", growthTimer);
		nbt.setLong("plantedTime", plantedTime);
	}

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException {

		cropId = inStream.readInt();
		growth = inStream.readFloat();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException {
		outStream.writeInt(cropId);
		outStream.writeFloat(growth);
	}

	@Override
	public void handleInitPacket(DataInputStream inStream) throws IOException {
		cropId = inStream.readInt();
		growth = inStream.readFloat();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	public Packet createCropUpdatePacket()
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);	
		try {
			dos.writeByte(PacketHandler.Packet_Data_Block_Client);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeInt(cropId);
			dos.writeFloat(growth);
		} catch (IOException e) {
		}
		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream)
			throws IOException {
		// TODO Auto-generated method stub
		
	}
}
