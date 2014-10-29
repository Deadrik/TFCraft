package com.bioxx.tfc.api;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.bioxx.tfc.Core.Schematic;

public class TreeSchematic extends Schematic
{
	private int growthStage;
	private int logCount;

	public TreeSchematic(String p) 
	{
		super(p);
	}

	@Override
	public boolean Load()
	{
		super.Load();
		int num = path.indexOf('-') + 1;
		growthStage = Integer.parseInt(path.substring(num, num + 1));
		countLogs();
		return true;
	}

	public int getGrowthStage()
	{
		return growthStage;
	}
	@Deprecated
	public int getLogCount()
	{
		return logCount;
	}

	@Deprecated
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
