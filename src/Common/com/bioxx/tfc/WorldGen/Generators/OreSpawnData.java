package com.bioxx.tfc.WorldGen.Generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

public class OreSpawnData
{
	public int type, size, meta, rarity, min = 5, max = 128, vDensity, hDensity;
	public Block block;
	public Map<Block, List<Integer>> base;

	public OreSpawnData(String t, String s, String blockName, int m, int r, String[] baseRocks)
	{
		block = Block.getBlockFromName(blockName);

		if (block == null)
		{
			TerraFirmaCraft.LOG.error(TFC_Core.translate("error.error") + " " + TFC_Core.translate("error.OreCFG") + " " + blockName);
			throw new java.lang.NullPointerException(TFC_Core.translate("error.OreCFG") + " " + blockName);
		}

		meta = m;
		rarity = r;
		if ("default".equals(t))
			type = 0;
		else
			type = 1;

		if ("small".equals(s))
			size = 0;
		else if ("medium".equals(s))
			size = 1;
		else
			size = 2;

		base = new HashMap<Block, List<Integer>>();
		for (String name : baseRocks)
		{
			getOre(name);
		}
	}

	public OreSpawnData(String t, String s, String blockName, int m, int r, String[] baseRocks, int minHeight, int maxHeight, int v, int h)
	{
		this(t, s, blockName, m, r, baseRocks);
		min = minHeight;
		max = maxHeight;
		vDensity = v;
		hDensity = h;
	}

	private void getOre(String name)
	{		
		for (int i = 0; i < Global.STONE_IGIN.length; i++){
			if (name.equalsIgnoreCase(Global.STONE_IGIN[i]))
			{
				List<Integer> metadata = base.containsKey(TFCBlocks.stoneIgIn) ? base.get(TFCBlocks.stoneIgIn) : new ArrayList<Integer>();
				metadata.add(i);
				base.put(TFCBlocks.stoneIgIn, metadata);
				return;
			}
		}

		for (int i = 0; i < Global.STONE_IGEX.length; i++)
		{
			if (name.equalsIgnoreCase(Global.STONE_IGEX[i]))
			{
				List<Integer> metadata = base.containsKey(TFCBlocks.stoneIgEx) ? base.get(TFCBlocks.stoneIgEx) : new ArrayList<Integer>();
				metadata.add(i);
				base.put(TFCBlocks.stoneIgEx, metadata);
				return;
			}
		}

		for (int i = 0; i < Global.STONE_SED.length; i++)
		{
			if (name.equalsIgnoreCase(Global.STONE_SED[i]))
			{
				List<Integer> metadata = base.containsKey(TFCBlocks.stoneSed) ? base.get(TFCBlocks.stoneSed) : new ArrayList<Integer>();
				metadata.add(i);
				base.put(TFCBlocks.stoneSed, metadata);
				return;
			}
		}

		for (int i = 0; i < Global.STONE_MM.length; i++)
		{
			if (name.equalsIgnoreCase(Global.STONE_MM[i]))
			{
				List<Integer> metadata = base.containsKey(TFCBlocks.stoneMM) ? base.get(TFCBlocks.stoneMM) : new ArrayList<Integer>();
				metadata.add(i);
				base.put(TFCBlocks.stoneMM, metadata);
				return;
			}				
		}
		
		if ("igneous intrusive".equalsIgnoreCase(name))
		{
			List<Integer> metadata = base.containsKey(TFCBlocks.stoneIgIn) ? base.get(TFCBlocks.stoneIgIn) : new ArrayList<Integer>();
			metadata.add(-1);
			base.put(TFCBlocks.stoneIgIn, metadata);
			return;
		}
		else if ("igneous extrusive".equalsIgnoreCase(name))
		{
			List<Integer> metadata = base.containsKey(TFCBlocks.stoneIgEx) ? base.get(TFCBlocks.stoneIgEx) : new ArrayList<Integer>();
			metadata.add(-1);
			base.put(TFCBlocks.stoneIgEx, metadata);
			return;
		}
		else if ("sedimentary".equalsIgnoreCase(name))
		{
			List<Integer> metadata = base.containsKey(TFCBlocks.stoneSed) ? base.get(TFCBlocks.stoneSed) : new ArrayList<Integer>();
			metadata.add(-1);
			base.put(TFCBlocks.stoneSed, metadata);
			return;
		}
		else if ("metamorphic".equalsIgnoreCase(name))
		{
			List<Integer> metadata = base.containsKey(TFCBlocks.stoneMM) ? base.get(TFCBlocks.stoneMM) : new ArrayList<Integer>();
			metadata.add(-1);
			base.put(TFCBlocks.stoneMM, metadata);
			return;
		}
	}
}