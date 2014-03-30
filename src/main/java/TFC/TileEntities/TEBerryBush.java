package TFC.TileEntities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TEBerryBush extends TileEntity
{
	public int dayHarvested = -1000;
	public int dayFruited = -1000;
	public boolean hasFruit = false;

	public TEBerryBush()
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
		dayHarvested = nbt.getInteger("dayHarvested");
		dayFruited = nbt.getInteger("dayFruited");
		hasFruit = nbt.getBoolean("hasFruit");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("dayHarvested", dayHarvested);
		nbt.setInteger("dayFruited", dayFruited);
		nbt.setBoolean("hasFruit", hasFruit);
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
