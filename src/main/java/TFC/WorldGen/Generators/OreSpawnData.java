package TFC.WorldGen.Generators;

import java.util.HashMap;

import net.minecraft.block.Block;
import TFC.TFCBlocks;

public class OreSpawnData
{
	public int type, size, meta, rarity, min = 5, max = 128, vDensity, hDensity;
	public Block block;
	public HashMap<Block, Integer> base;

	public OreSpawnData(String T, String S, String BN, int M, int R, String[] baseRocks)
	{
		block = Block.getBlockFromName(BN);
		meta = M;
		rarity = R;
		if(T.equals("default"))
			type = 0;
		else
			type = 1;

		if(S.equals("small"))
			size = 0;
		else if(S.equals("medium"))
			size = 1;
		else
			size = 2;

		base = new HashMap<Block, Integer>();
		for(int i = 0, j = 0; i < baseRocks.length; i++)
		{
			getOre(baseRocks[i]);
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
		if(name.equalsIgnoreCase("granite"))
		{
			this.base.put(TFCBlocks.StoneIgIn, 0);
		}
		else if(name.equalsIgnoreCase("diorite"))
		{
			this.base.put(TFCBlocks.StoneIgIn, 1);
		}
		else if(name.equalsIgnoreCase("gabbro"))
		{
			this.base.put(TFCBlocks.StoneIgIn, 2);
		}
		else if(name.equalsIgnoreCase("shale"))
		{
			this.base.put(TFCBlocks.StoneSed, 0);
		}
		else if(name.equalsIgnoreCase("claystone"))
		{
			this.base.put(TFCBlocks.StoneSed, 1);
		}
		else if(name.equalsIgnoreCase("rock salt"))
		{
			this.base.put(TFCBlocks.StoneSed, 2);
		}
		else if(name.equalsIgnoreCase("limestone"))
		{
			this.base.put(TFCBlocks.StoneSed, 3);
		}
		else if(name.equalsIgnoreCase("conglomerate"))
		{
			this.base.put(TFCBlocks.StoneSed, 4);
		}
		else if(name.equalsIgnoreCase("dolomite"))
		{
			this.base.put(TFCBlocks.StoneSed, 5);
		}
		else if(name.equalsIgnoreCase("chert"))
		{
			this.base.put(TFCBlocks.StoneSed, 6);
		}
		else if(name.equalsIgnoreCase("chalk"))
		{
			this.base.put(TFCBlocks.StoneSed, 7);
		}
		else if(name.equalsIgnoreCase("rhyolite"))
		{
			this.base.put(TFCBlocks.StoneIgEx, 0);
		}
		else if(name.equalsIgnoreCase("basalt"))
		{
			this.base.put(TFCBlocks.StoneIgEx, 1);
		}
		else if(name.equalsIgnoreCase("andesite"))
		{
			this.base.put(TFCBlocks.StoneIgEx, 2);
		}
		else if(name.equalsIgnoreCase("dacite"))
		{
			this.base.put(TFCBlocks.StoneIgEx, 3);
		}
		else if(name.equalsIgnoreCase("quartzite"))
		{
			this.base.put(TFCBlocks.StoneMM, 0);
		}
		else if(name.equalsIgnoreCase("slate"))
		{
			this.base.put(TFCBlocks.StoneMM, 1);
		}
		else if(name.equalsIgnoreCase("phyllite"))
		{
			this.base.put(TFCBlocks.StoneMM, 2);
		}
		else if(name.equalsIgnoreCase("schist"))
		{
			this.base.put(TFCBlocks.StoneMM, 3);
		}
		else if(name.equalsIgnoreCase("gneiss"))
		{
			this.base.put(TFCBlocks.StoneMM, 4);
		}
		else if(name.equalsIgnoreCase("marble"))
		{
			this.base.put(TFCBlocks.StoneMM, 5);
		}
		else if(name.equalsIgnoreCase("igneous intrusive"))
		{
			this.base.put(TFCBlocks.StoneIgIn, -1);
		}
		else if(name.equalsIgnoreCase("igneous extrusive"))
		{
			this.base.put(TFCBlocks.StoneIgEx, -1);
		}
		else if(name.equalsIgnoreCase("sedimentary"))
		{
			this.base.put(TFCBlocks.StoneSed, -1);
		}
		else if(name.equalsIgnoreCase("metamorphic"))
		{
			this.base.put(TFCBlocks.StoneMM, -1);
		}
		this.base.put(TFCBlocks.StoneIgIn, -1);
	}
}