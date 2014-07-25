package com.bioxx.tfc.TileEntities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.bioxx.tfc.TFCBlocks;

public class TEMetalTrapDoor extends NetworkTileEntity
{
	public ItemStack sheetStack;
	public byte sides = 0;

	public TEMetalTrapDoor()
	{

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
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) 
	{
		super.writeToNBT(nbt);
		nbt.setByte("sides", sides);
		NBTTagCompound st = new NBTTagCompound();
		sheetStack.writeToNBT(st);
		nbt.setTag("sheetType", st);
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) {
		sides = nbt.getByte("sides");
		sheetStack = new ItemStack(TFCBlocks.MetalTrapDoor, 1, nbt.getInteger("metalID"));
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
		nbt.setInteger("metalID", sheetStack != null ? sheetStack.getItemDamage() : 0);
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
