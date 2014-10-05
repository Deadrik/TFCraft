package com.bioxx.tfc.Core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.api.Interfaces.ISchematic;

public class TreeSchematic implements ISchematic
{
	private int height;
	private int width;
	private int centerX;
	private int length;
	private int centerZ;
	private int[] blockArray;
	private byte[] dataArray;
	private NBTTagList te;
	private NBTTagList entities;
	private String path;
	private int id;
	private int growthStage;
	private int logCount;

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
			centerX = getCenter(width);
			length = tree.getShort("Length");
			centerZ = getCenter(length);

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

			int num = path.indexOf('_') + 1;
			id = Integer.parseInt(path.substring(num, num + 3));

			num += 4;
			growthStage = Integer.parseInt(path.substring(num, num + 1));
			
		}
		catch (FileNotFoundException e)
		{
			System.out.println("TFC FileNotFound: " + path); return false;
		}
		catch (IOException e)
		{
			System.out.println("TFC IOException: " + path); return false;
		}

		countLogs();
		return true;
	}

	@Override
	public int getSizeY()
	{
		return height;
	}

	@Override
	public int getSizeX()
	{
		return width;
	}

	@Override
	public int getSizeZ()
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
	public void setSizeY(int y)
	{
		height = y;
	}

	@Override
	public void setSizeX(int x)
	{
		width = x;
	}

	@Override
	public void setSizeZ(int z)
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
		return centerX;
	}

	@Override
	public int getCenterZ()
	{
		return centerZ;
	}

	@Override
	public int getIndex()
	{
		return id;
	}

	@Override
	public int getGrowthStage()
	{
		return growthStage;
	}

	@Override
	public int getLogCount()
	{
		return logCount;
	}


	//*****************
	// Private methods
	//*****************
	private int getCenter(int v)
	{
		if(v % 2 == 1)
			return (v + 1) / 2;
		return v / 2;
	}

	private void countLogs()
	{
		int index;
		int id;
		for(int y = 0; y < height; y++)
		{
			for(int z = 0; z < length; z++)
			{
				for(int x = 0; x < width; x++)
				{
					index = x + width * (z + length * y);
					id = blockArray[index];
					if(Block.getBlockById(id).getMaterial() == Material.wood)
						logCount++;
				}
			}
		}
	}
}
