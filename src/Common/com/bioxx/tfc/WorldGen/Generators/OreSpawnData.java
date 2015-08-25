package com.bioxx.tfc.WorldGen.Generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

public class OreSpawnData
{
	public int type, size, meta, rarity, min = 5, max = 128, vDensity, hDensity;
	public Block block;
	public HashMap<Block, List<Integer>> base;

	public OreSpawnData(String T, String S, String BN, int M, int R, String[] baseRocks)
	{
		block = Block.getBlockFromName(BN);

		if (block == null)
		{
			TerraFirmaCraft.log.error(TFC_Core.translate("error.error") + " " + TFC_Core.translate("error.OreCFG") + " " + BN);
			throw new java.lang.NullPointerException(TFC_Core.translate("error.OreCFG") + " " + BN);
		}

		meta = M;
		rarity = R;
		if ("default".equals(T))
			type = 0;
		else
			type = 1;

		if ("small".equals(S))
			size = 0;
		else if ("medium".equals(S))
			size = 1;
		else
			size = 2;

		base = new HashMap<Block, List<Integer>>();
		for (String name : baseRocks)
		{
			getOre(name);
		}
	}

	public OreSpawnData(String T, String S, String BN, int M, int R, String[] baseRocks, int Min, int Max, int v, int h)
	{
		this(T, S, BN, M, R, baseRocks);
		min = Min;
		max = Max;
		vDensity = v;
		hDensity = h;
	}

	private void getOre(String name)
	{		
		for (int i = 0; i < Global.STONE_IGIN.length; i++){
			if (name.equalsIgnoreCase(Global.STONE_IGIN[i]))
			{
				List<Integer> metadata = base.containsKey(TFCBlocks.StoneIgIn) ? base.get(TFCBlocks.StoneIgIn) : new ArrayList<Integer>();
				metadata.add(i);
				base.put(TFCBlocks.StoneIgIn, metadata);
				return;
			}
		}

		for (int i = 0; i < Global.STONE_IGEX.length; i++)
		{
			if (name.equalsIgnoreCase(Global.STONE_IGEX[i]))
			{
				List<Integer> metadata = base.containsKey(TFCBlocks.StoneIgEx) ? base.get(TFCBlocks.StoneIgEx) : new ArrayList<Integer>();
				metadata.add(i);
				base.put(TFCBlocks.StoneIgEx, metadata);
				return;
			}
		}

		for (int i = 0; i < Global.STONE_SED.length; i++)
		{
			if (name.equalsIgnoreCase(Global.STONE_SED[i]))
			{
				List<Integer> metadata = base.containsKey(TFCBlocks.StoneSed) ? base.get(TFCBlocks.StoneSed) : new ArrayList<Integer>();
				metadata.add(i);
				base.put(TFCBlocks.StoneSed, metadata);
				return;
			}
		}

		for (int i = 0; i < Global.STONE_MM.length; i++)
		{
			if (name.equalsIgnoreCase(Global.STONE_MM[i]))
			{
				List<Integer> metadata = base.containsKey(TFCBlocks.StoneMM) ? base.get(TFCBlocks.StoneMM) : new ArrayList<Integer>();
				metadata.add(i);
				base.put(TFCBlocks.StoneMM, metadata);
				return;
			}				
		}
		
		if ("igneous intrusive".equalsIgnoreCase(name))
		{
			List<Integer> metadata = base.containsKey(TFCBlocks.StoneIgIn) ? base.get(TFCBlocks.StoneIgIn) : new ArrayList<Integer>();
			metadata.add(-1);
			base.put(TFCBlocks.StoneIgIn, metadata);
			return;
		}
		else if ("igneous extrusive".equalsIgnoreCase(name))
		{
			List<Integer> metadata = base.containsKey(TFCBlocks.StoneIgEx) ? base.get(TFCBlocks.StoneIgEx) : new ArrayList<Integer>();
			metadata.add(-1);
			base.put(TFCBlocks.StoneIgEx, metadata);
			return;
		}
		else if ("sedimentary".equalsIgnoreCase(name))
		{
			List<Integer> metadata = base.containsKey(TFCBlocks.StoneSed) ? base.get(TFCBlocks.StoneSed) : new ArrayList<Integer>();
			metadata.add(-1);
			base.put(TFCBlocks.StoneSed, metadata);
			return;
		}
		else if ("metamorphic".equalsIgnoreCase(name))
		{
			List<Integer> metadata = base.containsKey(TFCBlocks.StoneMM) ? base.get(TFCBlocks.StoneMM) : new ArrayList<Integer>();
			metadata.add(-1);
			base.put(TFCBlocks.StoneMM, metadata);
			return;
		}
	}
}