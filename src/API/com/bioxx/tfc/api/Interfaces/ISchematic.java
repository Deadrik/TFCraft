package com.bioxx.tfc.api.Interfaces;

import net.minecraft.nbt.NBTTagList;

public interface ISchematic 
{
	
	boolean load();
	/**
	 * @return Schematic "Height"
	 */
	short getSizeY();
	
	void setSizeY(short y);
	
	/**
	 * @return Schematic "Width"
	 */
	short getSizeX();
	
	void setSizeX(short x);
	
	/**
	 * @return Schematic "Length"
	 */
	short getSizeZ();
	
	void setSizeZ(short z);
	
	/**
	 * In normal schematic files, this is a byte array. TFC uses an int array to be able to 
	 * support 4096 ids.
	 * @return Schematic "Blocks"
	 */
	int[] getBlockArray();
	
	void setBlockArray(int[] array);
	
	/**
	 * @return Schematic "Data"
	 */
	byte[] getDataArray();
	
	void setDataArray(byte[] array);
	
	/**
	 * @return Schematic "TileEntities"
	 */
	NBTTagList getTileEntities();
	
	void setTileEntities(NBTTagList te);
	
	/**
	 * @return Schematic "Entities"
	 */
	NBTTagList getEntities();
	
	void setEntities(NBTTagList e);
	
	/**
	 * Gets the file path.
	 */
	String getPath();
	
	/**
	 * Sets the file path for future reference.
	 * @param path The path to the schematic file
	 */
	void setPath(String path);
	
	/**
	 * 
	 * @return Center of the schematic X Coordinate
	 */
	int getCenterX();
	
	/**
	 * 
	 * @return Center of the schematic Z Coordinate
	 */
	int getCenterZ();
	
	/**
	 * Used for lookups in the schematic list.
	 * @return File Index number
	 */
	int getIndex();
}
