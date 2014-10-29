package com.bioxx.tfc.TileEntities;

import net.minecraft.nbt.NBTTagCompound;

public class TETreeLog extends NetworkTileEntity
{
	public int dayBorn = -20;
	public boolean isBase = false;
	public int baseX = 0;
	public int baseY = 0;
	public int baseZ = 0;
	public byte schemIndex = -1;
	public byte treeID = 0;
	public byte rotation = 0;
	public byte growthStage = 0;
	private boolean doingExplosion = false;

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
		if(isBase)
		{
			schemIndex = nbt.getByte("schemIndex");
			treeID = nbt.getByte("treeID");
			this.growthStage = nbt.getByte("growthStage");
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
		if(isBase)
		{
			nbt.setByte("schemIndex", schemIndex);
			nbt.setByte("treeID", treeID);
			nbt.setByte("rotation", rotation);
			nbt.setByte("growthStage", growthStage);
		}
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) 
	{
		treeID = nbt.getByte("treeID");
		/*schemIndex = nbt.getByte("schemIndex");
		rotation = nbt.getByte("rotation");*/
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		//TreeRegistry.instance.getTreeSchematic(treeID, schemIndex);
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
			//nbt.setByte("schemID", schemIndex);
			//nbt.setByte("rotation", rotation);
		}
	}

	public void setDoingExplosion(boolean flag)
	{
		this.doingExplosion = flag;
	}

	public boolean getDoingExplosion()
	{
		return doingExplosion;
	}

}
