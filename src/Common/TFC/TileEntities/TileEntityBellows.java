package TFC.TileEntities;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBellows extends TileEntity
{
	static final int blockMap[][] = { { 0, 1 }, { -1, 0 }, { 0, -1 }, { 1, 0 } };
	static final int blockMap2[][] = { { 0, 2 }, { -2, 0 }, { 0, -2 }, { 2, 0 } };
	public boolean shouldBlow = false;
	public int blowTimer = 0;
	public int blowDirection = 0;

	@Override
	public void updateEntity()
	{
		if(shouldBlow)
		{
			if(blowDirection == 0)
			{
				blowTimer++;
				if(worldObj.isRemote)
					GenerateSmoke();
				if(blowTimer == 5)
				{
					blowDirection = 1;
					if(!worldObj.isRemote)
						GiveAir();
				}
			}
			else
			{
				blowTimer--;
				if(blowTimer == -3)
				{
					blowDirection = 0;
					shouldBlow = false;
					this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
			}
		}
	}

	public void GenerateSmoke()
	{
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		int x = blockMap[meta][0];
		int z = blockMap[meta][1];
		Random random = new Random();
		
		float f = (float) xCoord + x + 0.5F;
		float f1 = yCoord + 0.1F + random.nextFloat() * 6F / 16F;
		float f2 = (float) zCoord + z + 0.5F;
		float f3 = 0.82F;
		float f4 = random.nextFloat() * 0.6F;
		float f5 = random.nextFloat() * -0.6F;
		float f6 = random.nextFloat() * -0.6F;
		worldObj.spawnParticle("smoke", f + f4 - 0.3F, f1, f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
	}
	
	public void GiveAir()
	{
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		int x = blockMap[meta][0];
		int z = blockMap[meta][1];
		TileEntity te = worldObj.getTileEntity(xCoord + x, yCoord, zCoord + z);
		TileEntity te2 = worldObj.getTileEntity(xCoord + x, yCoord - 1, zCoord + z);
		TileEntityFireEntity tileentityfirepit = null;

		if (te != null && te instanceof TileEntityFireEntity)
			tileentityfirepit = (TileEntityFireEntity) te;
		else if (te2 != null && te2 instanceof TileEntityForge)
			tileentityfirepit = (TileEntityFireEntity) te2;

		if (tileentityfirepit != null)
			tileentityfirepit.receiveAirFromBellows();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		shouldBlow = nbttagcompound.getBoolean("shouldBlow");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setBoolean("shouldBlow", shouldBlow);
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
	
}
