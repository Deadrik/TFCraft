package com.bioxx.tfc.TileEntities;

import net.minecraft.nbt.NBTTagCompound;

import com.bioxx.tfc.Core.TreeRegistry;

public class TETreeLog extends NetworkTileEntity
{
	public int dayBorn = -20;
	public boolean isBase = false;
	public int baseX = 0; 
	public int baseY = 0;
	public int baseZ = 0;
	public byte xOffset = 0;
	public byte zOffset = 0;
	public byte schemID = -1;
	public byte treeID = 0;
	public byte rotation = 0;

	public TETreeLog()
	{

	}

	@Override
	public boolean canUpdate()
	{
		return false;
	}

	@Override
	protected boolean shouldSendInitData()
	{
		return isBase;
	}

	public TETreeLog getBaseTE()
	{
		return (TETreeLog) worldObj.getTileEntity(baseX, baseY, baseZ);
	}

	public void Setup(int x, int y, int z)
	{
		baseX = x;
		baseY = y;
		baseZ = z;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) 
	{
		super.readFromNBT(nbt);
		dayBorn = nbt.getInteger("dayBorn");
		isBase = nbt.getBoolean("isBase");
		baseX = nbt.getInteger("baseX");
		baseY = nbt.getInteger("baseY");
		baseZ = nbt.getInteger("baseZ");
		xOffset = nbt.getByte("xOffset");
		zOffset = nbt.getByte("zOffset");
		if(isBase)
		{
			schemID = nbt.getByte("schemID");
			treeID = nbt.getByte("treeID");
			rotation = nbt.getByte("rotation");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) 
	{
		super.writeToNBT(nbt);
		nbt.setInteger("dayBorn", dayBorn);
		nbt.setBoolean("isBase", isBase);
		nbt.setInteger("baseX", baseX);
		nbt.setInteger("baseY", baseY);
		nbt.setInteger("baseZ", baseZ);
		nbt.setByte("xOffset", xOffset);
		nbt.setByte("zOffset", zOffset);
		if(isBase)
		{
			nbt.setByte("schemID", schemID);
			nbt.setByte("treeID", treeID);
			nbt.setByte("rotation", rotation);
		}
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) 
	{
		treeID = nbt.getByte("treeID");
		schemID = nbt.getByte("schemID");
		rotation = nbt.getByte("rotation");
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		TreeRegistry.instance.getTreeSchematic(treeID, false);//Something needs to change if we want to have growing trees. Large tag doesn't make sense.
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt) {}

	@Override
	public void createDataNBT(NBTTagCompound nbt) {}

	@Override
	public void createInitNBT(NBTTagCompound nbt) 
	{
		if(isBase)
		{
			nbt.setByte("treeID", treeID);
			nbt.setByte("schemID", schemID);
			nbt.setByte("rotation", rotation);
		}
	}

	public void Setup(byte index, byte b, int localX, int localY, int localZ, byte x, byte z) 
	{
		this.isBase = true;
		treeID = index;
		rotation = b;
		xOffset = x;
		zOffset = z;
		Setup(localX, localY, localZ);
	}
}
