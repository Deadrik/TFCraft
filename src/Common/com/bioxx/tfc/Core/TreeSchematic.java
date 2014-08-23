package com.bioxx.tfc.Core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.api.Interfaces.ISchematic;

public class TreeSchematic implements ISchematic
{
	private short height;
	private short width;
	private short length;
	private int[] blockArray;
	private byte[] dataArray;
	private NBTTagList te;
	private NBTTagList entities;
	private String path;
	private boolean isLarge;
	private int id;

	public TreeSchematic(String p)
	{
		path = p;
	}

	@Override
	public boolean Load()
	{
		NBTTagCompound tree;
		try
		{
			InputStream fis = TerraFirmaCraft.instance.getClass().getClassLoader().getResourceAsStream(path);
			tree = CompressedStreamTools.readCompressed(fis);
			height = tree.getShort("Height");
			width = tree.getShort("Width");
			length = tree.getShort("Length");

			if(tree.hasKey("Blocks"))
			{
				byte[] b = tree.getByteArray("Blocks");
				blockArray = new int[b.length];

				for(int i = 0; i < b.length; i++)
				{
					blockArray[i] = b[i];
				}
			}
			else if(tree.hasKey("BlocksInt"))
			{
				blockArray = tree.getIntArray("Blocks");
			}

			dataArray = tree.getByteArray("Data");
			te = tree.getTagList("TileEntities", 10);

			if(path.contains("_Large"))
				isLarge = true;
			else
				isLarge = false;

			int num = path.indexOf('_') + 1;
			id = Integer.parseInt(path.substring(num, num + 3));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("TFC2 FileNotFound: " + path); return false;
		}
		catch (IOException e)
		{
			System.out.println("TFC2 IOException: " + path); return false;
		}

		return true;
	}

	@Override
	public short getSizeY()
	{
		return height;
	}

	@Override
	public short getSizeX()
	{
		return width;
	}

	@Override
	public short getSizeZ()
	{
		return length;
	}

	@Override
	public int[] getBlockArray()
	{
		return blockArray;
	}

	@Override
	public byte[] getDataArray()
	{
		return dataArray;
	}

	@Override
	public NBTTagList getTileEntities()
	{
		return te;
	}

	@Override
	public String getPath()
	{
		return path;
	}

	@Override
	public void setPath(String str)
	{
		path = str;
	}

	@Override
	public void setSizeY(short y)
	{
		height = y;
	}

	@Override
	public void setSizeX(short x)
	{
		width = x;
	}

	@Override
	public void setSizeZ(short z)
	{
		length = z;
	}

	@Override
	public void setBlockArray(int[] array)
	{
		blockArray = array;
	}

	@Override
	public void setDataArray(byte[] array)
	{
		dataArray = array;
	}

	@Override
	public void setTileEntities(NBTTagList tag)
	{
		te = tag;
	}

	@Override
	public NBTTagList getEntities()
	{
		return entities;
	}

	@Override
	public void setEntities(NBTTagList e)
	{
		entities = e;
	}

	@Override
	public int getCenterX()
	{
		return (int) Math.floor((double)width / 2D);
	}

	@Override
	public int getCenterZ()
	{
		return (int) Math.floor((double)length / 2D);
	}

	public boolean getIsLarge()
	{
		return isLarge;
	}

	@Override
	public int getIndex()
	{
		return id;
	}

}
