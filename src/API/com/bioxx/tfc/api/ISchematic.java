package com.bioxx.tfc.api;

import net.minecraft.nbt.NBTTagList;

public interface ISchematic 
{
	
	public boolean Load();
	/**
	 * @return Schematic "Height"
	 */
	public short getSizeY();
	
	public void setSizeY(short y);
	
	/**
	 * @return Schematic "Width"
	 */
	public short getSizeX();
	
	public void setSizeX(short x);
	
	/**
	 * @return Schematic "Length"
	 */
	public short getSizeZ();
	
	public void setSizeZ(short z);
	
	/**
	 * In normal schematic files, this is a byte array. TFC uses an int array to be able to 
	 * support 4096 ids.
	 * @return Schematic "Blocks"
	 */
	public int[] getBlockArray();
	
	public void setBlockArray(int[] array);
	
	/**
	 * @return Schematic "Data"
	 */
	public byte[] getDataArray();
	
	public void setDataArray(byte[] array);
	
	/**
	 * @return Schematic "TileEntities"
	 */
	public NBTTagList getTileEntities();
	
	public void setTileEntities(NBTTagList te);
	
	/**
	 * @return Schematic "Entities"
	 */
	public NBTTagList getEntities();
	
	public void setEntities(NBTTagList e);
	
	/**
	 * Gets the file path.
	 */
	public String getPath();
	
	/**
	 * Sets the file path for future reference.
	 * @param path The path to the schematic file
	 */
	public void setPath(String path);
	
	/**
	 * 
	 * @return Center of the schematic X Coordinate
	 */
	public int getCenterX();
	
	/**
	 * 
	 * @return Center of the schematic Z Coordinate
	 */
	public int getCenterZ();
	
	/**
	 * Used for lookups in the schematic list.
	 * @return File Index number
	 */
	public int getIndex();
}
