package TFC.WorldGen.Generators;

import TFC.TFCBlocks;

public class OreSpawnData
{
	public int type, size, id, meta, rarity, min = 5, max = 128, vDensity, hDensity;
	public int[] base;


	public OreSpawnData(String T, String S, int ID, int M, int R, String[] baseRocks)
	{
		id = ID;
		meta = M;
		rarity = R;
		if(T.equals("default")) {
			type = 0;
		} else {
			type = 1;
		}
		if(S.equals("small")) {
			size = 0;
		} else if(S.equals("medium")) {
			size = 1;
		} else {
			size = 2;
		}

		base = new int[baseRocks.length*2];
		for(int i = 0, j = 0; i < baseRocks.length; i++)
		{
			int[] _ore = getOre(baseRocks[i]);
			base[j++] = _ore[0];
			base[j++] = _ore[1];
		}
	}

	public OreSpawnData(String T, String S, int ID, int M, int R, String[] baseRocks, int Min, int Max, int v, int h)
	{
		this(T, S, ID, M, R, baseRocks);
		min = Min;
		max = Max;
		vDensity = v;
		hDensity = h;
	}

	private int[] getOre(String name)
	{

		if(name.equalsIgnoreCase("granite"))
		{
			return new int[] {TFCBlocks.StoneIgIn.blockID, 0};
		}
		else if(name.equalsIgnoreCase("diorite"))
		{
			return new int[] {TFCBlocks.StoneIgIn.blockID, 1};
		}
		else if(name.equalsIgnoreCase("gabbro"))
		{
			return new int[] {TFCBlocks.StoneIgIn.blockID, 2};
		}
		else if(name.equalsIgnoreCase("shale"))
		{
			return new int[] {TFCBlocks.StoneSed.blockID, 0};
		}
		else if(name.equalsIgnoreCase("claystone"))
		{
			return new int[] {TFCBlocks.StoneSed.blockID, 1};
		}
		else if(name.equalsIgnoreCase("rock salt"))
		{
			return new int[] {TFCBlocks.StoneSed.blockID, 2};
		}
		else if(name.equalsIgnoreCase("limestone"))
		{
			return new int[] {TFCBlocks.StoneSed.blockID, 3};
		}
		else if(name.equalsIgnoreCase("conglomerate"))
		{
			return new int[] {TFCBlocks.StoneSed.blockID, 4};
		}
		else if(name.equalsIgnoreCase("dolomite"))
		{
			return new int[] {TFCBlocks.StoneSed.blockID, 5};
		}
		else if(name.equalsIgnoreCase("chert"))
		{
			return new int[] {TFCBlocks.StoneSed.blockID, 6};
		}
		else if(name.equalsIgnoreCase("chalk"))
		{
			return new int[] {TFCBlocks.StoneSed.blockID, 7};
		}
		else if(name.equalsIgnoreCase("rhyolite"))
		{
			return new int[] {TFCBlocks.StoneIgEx.blockID, 0};
		}
		else if(name.equalsIgnoreCase("basalt"))
		{
			return new int[] {TFCBlocks.StoneIgEx.blockID, 1};
		}
		else if(name.equalsIgnoreCase("andesite"))
		{
			return new int[] {TFCBlocks.StoneIgEx.blockID, 2};
		}
		else if(name.equalsIgnoreCase("dacite"))
		{
			return new int[] {TFCBlocks.StoneIgEx.blockID, 3};
		}
		else if(name.equalsIgnoreCase("quartzite"))
		{
			return new int[] {TFCBlocks.StoneMM.blockID, 0};
		}
		else if(name.equalsIgnoreCase("slate"))
		{
			return new int[] {TFCBlocks.StoneMM.blockID, 1};
		}
		else if(name.equalsIgnoreCase("phyllite"))
		{
			return new int[] {TFCBlocks.StoneMM.blockID, 2};
		}
		else if(name.equalsIgnoreCase("schist"))
		{
			return new int[] {TFCBlocks.StoneMM.blockID, 3};
		}
		else if(name.equalsIgnoreCase("gneiss"))
		{
			return new int[] {TFCBlocks.StoneMM.blockID, 4};
		}
		else if(name.equalsIgnoreCase("marble"))
		{
			return new int[] {TFCBlocks.StoneMM.blockID, 5};
		}
		else if(name.equalsIgnoreCase("igneous intrusive"))
		{
			return new int[] {TFCBlocks.StoneIgIn.blockID, -1};
		}
		else if(name.equalsIgnoreCase("igneous extrusive"))
		{
			return new int[] {TFCBlocks.StoneIgEx.blockID, -1};
		}
		else if(name.equalsIgnoreCase("sedimentary"))
		{
			return new int[] {TFCBlocks.StoneSed.blockID, -1};
		}
		else if(name.equalsIgnoreCase("metamorphic"))
		{
			return new int[] {TFCBlocks.StoneMM.blockID, -1};
		}
		return new int[] {TFCBlocks.StoneIgIn.blockID, -1};
	}
}