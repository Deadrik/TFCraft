package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import TFC.Handlers.PacketHandler;

public class TileEntityPartial extends NetworkTileEntity
{
	public short TypeID = -1;
	public byte MetaID = 0;
	public byte material = 0;
	public long extraData = 0;

	public TileEntityPartial()
	{

	}

	@Override
	public boolean canUpdate()
	{
		return false;
	}

	public Material getMaterial()
	{
		switch(material)
		{
		case 1:
			return Material.ground;
		case 2:
			return Material.wood;
		case 3:
			return Material.rock;
		case 4:
			return Material.iron;
		case 5:
			return Material.water;
		case 6:
			return Material.lava;
		case 7:
			return Material.leaves;
		case 8:
			return Material.plants;
		case 9:
			return Material.vine;
		case 10:
			return Material.sponge;
		case 11:
			return Material.cloth;
		case 12:
			return Material.fire;
		case 13:
			return Material.sand;
		case 14:
			return Material.circuits;
		case 15:
			return Material.glass;
		case 16:
			return Material.redstoneLight;
		case 17:
			return Material.tnt;
		case 19:
			return Material.ice;
		case 20:
			return Material.snow;
		case 21:
			return Material.craftedSnow;
		case 22:
			return Material.cactus;
		case 23:
			return Material.clay;
		case 24:
			return Material.gourd;
		case 25:
			return Material.dragonEgg;
		case 26:
			return Material.portal;
		case 27:
			return Material.cake;
		case 28:
			return Material.web;
		case 29:
			return Material.piston;
		default:
			return Material.grass;
		}
	}

	public void setMaterial(Material mat)
	{
		byte n = 1;
		if(mat == Material.ground) {material = 1;} 
		else if(mat == Material.wood){n++; material = 2;}
		else if(mat == Material.rock){n++; material = 3;}
		else if(mat == Material.iron){n++; material = 4;}
		else if(mat == Material.water){n++; material = 5;}
		else if(mat == Material.lava){n++; material = 6;}
		else if(mat == Material.leaves){n++; material = 7;}
		else if(mat == Material.plants){n++; material = 8;}
		else if(mat == Material.vine){n++; material = 9;}
		else if(mat == Material.sponge){n++; material = 10;}
		else if(mat == Material.cloth){n++; material = 11;}
		else if(mat == Material.fire){n++; material = 12;}
		else if(mat == Material.sand){n++; material = 13;}
		else if(mat == Material.circuits){n++; material = 14;}
		else if(mat == Material.glass){n++; material = 15;}
		else if(mat == Material.redstoneLight){n++; material = 16;}
		else if(mat == Material.tnt){n++; material = 17;}
		else if(mat == Material.ice){n++; material = 19;}
		else if(mat == Material.snow){n++; material = 20;}
		else if(mat == Material.craftedSnow){n++; material = 21;}
		else if(mat == Material.cactus){n++; material = 22;}
		else if(mat == Material.clay){n++; material = 23;}
		else if(mat == Material.gourd){n++; material = 24;}
		else if(mat == Material.dragonEgg){n++; material = 25;}
		else if(mat == Material.portal){n++; material = 26;}
		else if(mat == Material.cake){n++; material = 27;}
		else if(mat == Material.web){n++; material = 28;}
		else if(mat == Material.piston){n++; material = 29;}
		else if(mat == Material.grass){n++; material = 0;}
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		MetaID = par1NBTTagCompound.getByte("metaID");
		TypeID = par1NBTTagCompound.getShort("typeID");
		material = par1NBTTagCompound.getByte("material");
		extraData = par1NBTTagCompound.getLong("extraData");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setShort("typeID", TypeID);
		par1NBTTagCompound.setByte("metaID", MetaID);
		par1NBTTagCompound.setByte("material", material);
		par1NBTTagCompound.setLong("extraData", extraData);
	}

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException 
	{
		extraData = inStream.readLong();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException 
	{
		outStream.writeShort(TypeID);
		outStream.writeByte(MetaID);
		outStream.writeByte(material);
		outStream.writeLong(extraData);
	}

	@Override
	public void handleInitPacket(DataInputStream inStream) throws IOException 
	{
		TypeID = inStream.readShort();
		MetaID = inStream.readByte();
		material = inStream.readByte();
		extraData = inStream.readLong();

		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream)
			throws IOException {
		// TODO Auto-generated method stub

	}

	public Packet createUpdatePacket()
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream outStream = new DataOutputStream(bos);	
		try {
			outStream.writeByte(PacketHandler.Packet_Data_Block_Client);
			outStream.writeInt(xCoord);
			outStream.writeInt(yCoord);
			outStream.writeInt(zCoord);
			outStream.writeLong(extraData);	
		} catch (IOException e) {
		}
		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}
}
