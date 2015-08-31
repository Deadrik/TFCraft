package com.bioxx.tfc.TileEntities;

import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;

public class TEPartial extends NetworkTileEntity
{
	public short typeID = -1;
	public byte metaID;
	public byte material;
	public long extraData;

	public TEPartial()
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
		if(mat == Material.ground) {material = 1;} 
		else if (mat == Material.wood)
		{
			material = 2;
		}
		else if (mat == Material.rock)
		{
			material = 3;
		}
		else if (mat == Material.iron)
		{
			material = 4;
		}
		else if (mat == Material.water)
		{
			material = 5;
		}
		else if (mat == Material.lava)
		{
			material = 6;
		}
		else if (mat == Material.leaves)
		{
			material = 7;
		}
		else if (mat == Material.plants)
		{
			material = 8;
		}
		else if (mat == Material.vine)
		{
			material = 9;
		}
		else if (mat == Material.sponge)
		{
			material = 10;
		}
		else if (mat == Material.cloth)
		{
			material = 11;
		}
		else if (mat == Material.fire)
		{
			material = 12;
		}
		else if (mat == Material.sand)
		{
			material = 13;
		}
		else if (mat == Material.circuits)
		{
			material = 14;
		}
		else if (mat == Material.glass)
		{
			material = 15;
		}
		else if (mat == Material.redstoneLight)
		{
			material = 16;
		}
		else if (mat == Material.tnt)
		{
			material = 17;
		}
		else if (mat == Material.ice)
		{
			material = 19;
		}
		else if (mat == Material.snow)
		{
			material = 20;
		}
		else if (mat == Material.craftedSnow)
		{
			material = 21;
		}
		else if (mat == Material.cactus)
		{
			material = 22;
		}
		else if (mat == Material.clay)
		{
			material = 23;
		}
		else if (mat == Material.gourd)
		{
			material = 24;
		}
		else if (mat == Material.dragonEgg)
		{
			material = 25;
		}
		else if (mat == Material.portal)
		{
			material = 26;
		}
		else if (mat == Material.cake)
		{
			material = 27;
		}
		else if (mat == Material.web)
		{
			material = 28;
		}
		else if (mat == Material.piston)
		{
			material = 29;
		}
		else if (mat == Material.grass)
		{
			material = 0;
		}
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		metaID = par1NBTTagCompound.getByte("metaID");
		typeID = par1NBTTagCompound.getShort("typeID");
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
		par1NBTTagCompound.setShort("typeID", typeID);
		par1NBTTagCompound.setByte("metaID", metaID);
		par1NBTTagCompound.setByte("material", material);
		par1NBTTagCompound.setLong("extraData", extraData);
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt)
	{
		metaID = nbt.getByte("metaID");
		typeID = nbt.getShort("typeID");
		material = nbt.getByte("material");
		extraData = nbt.getLong("extraData");
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt)
	{
		extraData = nbt.getLong("extraData");
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void createDataNBT(NBTTagCompound nbt)
	{
		nbt.setLong("extraData", extraData);
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt)
	{
		nbt.setShort("typeID", typeID);
		nbt.setByte("metaID", metaID);
		nbt.setByte("material", material);
		nbt.setLong("extraData", extraData);
	}

}
