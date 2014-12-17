package com.bioxx.tfc.TileEntities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TEMetalSheet extends NetworkTileEntity
{
	public ItemStack sheetStack;
	byte sides = 0;
	public int metalID;

	public TEMetalSheet()
	{

	}

	public void clearSides()
	{
		sides = 0;
	}

	public boolean TopExists()
	{
		return (sides & 1) > 0;
	}

	public boolean BottomExists()
	{
		return (sides & 2) > 0;
	}

	public boolean NorthExists()
	{
		return (sides & 4) > 0;
	}

	public boolean SouthExists()
	{
		return (sides & 8) > 0;
	}

	public boolean EastExists()
	{
		return (sides & 16) > 0;
	}

	public boolean WestExists()
	{
		return (sides & 32) > 0;
	}

	public void toggleBySide(int side, boolean setOn)
	{
		switch(side)
		{
		case 0:toggleBottom(setOn);break;
		case 1:toggleTop(setOn);break;
		case 2:toggleNorth(setOn);break;
		case 3:toggleSouth(setOn);break;
		case 4:toggleEast(setOn);break;
		case 5:toggleWest(setOn);break;
		}
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void toggleTop(boolean setOn)
	{
		if(TopExists() && !setOn)
			sides -= 1;
		else
			sides += 1;
	}

	public void toggleBottom(boolean setOn)
	{
		if(BottomExists() && !setOn)
			sides -= 2;
		else
			sides += 2;
	}

	public void toggleNorth(boolean setOn)
	{
		if(NorthExists() && !setOn)
			sides -= 4;
		else
			sides += 4;
	}

	public void toggleSouth(boolean setOn)
	{
		if(SouthExists() && !setOn)
			sides -= 8;
		else
			sides += 8;
	}

	public void toggleEast(boolean setOn)
	{
		if(EastExists() && !setOn)
			sides -= 16;
		else
			sides += 16;
	}

	public void toggleWest(boolean setOn)
	{
		if(WestExists() && !setOn)
			sides -= 32;
		else
			sides += 32;
	}

	public boolean isEmpty()
	{
		if(!TopExists() && !BottomExists() && !NorthExists() && !SouthExists() && !EastExists() && !WestExists())
			return true;
		return false;
	}

	@Override
	public boolean canUpdate()
	{
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) 
	{
		super.readFromNBT(nbt);
		sheetStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("sheetType"));
		sides = nbt.getByte("sides");
		metalID = nbt.getInteger("metalID");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) 
	{
		super.writeToNBT(nbt);
		nbt.setByte("sides", sides);
		NBTTagCompound st = new NBTTagCompound();
		if(sheetStack != null)
			sheetStack.writeToNBT(st);
		nbt.setTag("sheetType", st);
		nbt.setInteger("metalID", metalID);
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) {
		sides = nbt.getByte("sides");
		metalID = nbt.getInteger("metalID");
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt) {
	}

	@Override
	public void createDataNBT(NBTTagCompound nbt) {
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) {
		nbt.setByte("sides", this.sides);
		nbt.setInteger("metalID", this.metalID);
	}






	//TODO
	//	public Packet createUpdatePacket() {
	//		ByteArrayOutputStream bos=new ByteArrayOutputStream(34);
	//		DataOutputStream dos=new DataOutputStream(bos);
	//		try {
	//			dos.writeByte(PacketHandler.Packet_Init_Block_Client);
	//			dos.writeInt(xCoord);
	//			dos.writeInt(yCoord);
	//			dos.writeInt(zCoord);
	//			dos.writeByte(sides);
	//			dos.writeInt(metalID);
	//		} catch (IOException e) {
	//		}
	//		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	//	}
	//
	//	@Override
	//	public void createInitPacket(DataOutputStream outStream) throws IOException 
	//	{
	//		outStream.writeByte(sides);
	//		outStream.writeInt(metalID);
	//	}
	//
	//	@Override
	//	public void handleInitPacket(DataInputStream inStream) throws IOException 
	//	{
	//		sides = inStream.readByte();
	//		metalID = inStream.readInt();
	//		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	//	}
}
