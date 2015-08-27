package com.bioxx.tfc.TileEntities;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.AxisAlignedBB;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TEWaterPlant extends NetworkTileEntity
{
	private Block blockType;

	public TEWaterPlant()
	{

	}

	public void setBlock(Block block)
	{		
		if(block.isOpaqueCube()){
			this.blockType = block;
		}
	}

	@Override
	public boolean canUpdate()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}

	public Block getBlockFromType()
	{
		if(!this.worldObj.isRemote)
			return this.blockType;
		else
			return this.blockType;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		blockType = Block.getBlockById(nbttagcompound.getInteger("block"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("block", Block.getIdFromBlock(blockType));
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

	@Override
	public void handleInitPacket(NBTTagCompound nbt) {
		this.blockType = Block.getBlockById(nbt.getInteger("blockType"));
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) {
		nbt.setInteger("blockType", Block.getIdFromBlock(blockType));
	}

}
