package com.bioxx.tfc.Core;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Interfaces.ISchematic;

public class TreeRegistry
{
	public static TreeRegistry instance = new TreeRegistry();
	private HashMap treeTypeHash = new HashMap();
	private Vector<Vector<ISchematic>> treeList;
	private String treePath = "assets/terrafirmacraft/schematics/trees/";

	public TreeRegistry()
	{
		treeList = new Vector<Vector<ISchematic>>();
	}

	public void RegisterTree(ISchematic treeSchematic, String name)
	{
		int index = checkValidity(name);

		if(index < 0)
		{
			System.out.println("[TFC] Registering Tree Type \"" + name + "\" Failed! There are no trees with that name in TFC.");
		}
		else
		{
			if(treeList.size() < treeTypeHash.size())
				treeList.setSize(treeTypeHash.size());

			if(treeList.get(index) == null)
				treeList.set(index, new Vector<ISchematic>());

			treeList.get(index).add(treeSchematic);
		}
	}

	public void LoadTrees()
	{
		try
		{
			File root = new File(TerraFirmaCraft.instance.getClass().getClassLoader().getResource(treePath).toURI());
			for( File f : root.listFiles())
			{
				String schemType = f.getName().substring(0, f.getName().indexOf('_'));
				if(f.isFile())
				{
					TreeSchematic schem = new TreeSchematic(treePath + f.getName());
					if(schem.Load())
						RegisterTree(schem, schemType);
				}
			}
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
	}

	public void LoadTree(File f)
	{
		try
		{
			File root = new File(TerraFirmaCraft.instance.getClass().getClassLoader().getResource(treePath).toURI());
			String schemType = f.getName().substring(0, f.getName().indexOf('_'));
			if(f.isFile())
			{
				TreeSchematic schem = new TreeSchematic(treePath + f.getName());
				if(schem.Load())
					RegisterTree(schem, schemType);
			}
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
	}

	public TreeSchematic getRandomTreeSchematic(int treeID)
	{
		Vector v = treeList.get(treeID);
		if(v != null)
			return (TreeSchematic) v.get(new Random().nextInt(v.size()));
		return null;
	}

	public TreeSchematic getTreeSchematic(int treeID, int schemIndex)
	{
		Vector v = treeList.get(treeID);
		if(v != null)
		{
			TreeSchematic ts;
			for(int l = 0; l < v.size(); l++)
			{
				ts = (TreeSchematic)v.get(l);
				if(ts.getIndex() == schemIndex)
					return (TreeSchematic) v.get(schemIndex);
			}
		}
		return null;
	}

	public TreeSchematic getRandomTreeSchematicWithGrowthStage(int treeID, int growthStage)
	{
		Vector v = treeList.get(treeID);
		if(v != null)
		{
			HashMap<Integer, ISchematic> treeListTmp = new HashMap();
			TreeSchematic ts;
			int index = 0;
			for(int l = 0; l < v.size(); l++)
			{
				ts = (TreeSchematic)v.get(l);
				if(ts.getGrowthStage() == growthStage)
					treeListTmp.put(index++, ts);
			}
			if(treeListTmp.size() == 1)
				return (TreeSchematic) treeListTmp.get(0);
			if(treeListTmp.size() > 1)
				return (TreeSchematic) treeListTmp.get(new Random().nextInt(treeListTmp.size()));
		}
		return null;
	}

	public void LoadTreeTypes()
	{
		int i = 0;
		addWoodType(new TreeConfiguration(Global.WOOD_ALL[i], i, 500F, 1200F, 5F, 15F, 0.25F, 2F, false));i++; //Oak
		addWoodType(new TreeConfiguration(Global.WOOD_ALL[i], i, 300F, 1600F, -5F, 18F, 0.25F, 1F, false));i++; //Aspen
		addWoodType(new TreeConfiguration(Global.WOOD_ALL[i], i, 200F, 500F, -10F, 12F, 0F, 1F, false));i++; //Birch
		addWoodType(new TreeConfiguration(Global.WOOD_ALL[i], i, 250F, 16000F, 3F, 24F, 0F, 1F, false));i++; //Chestnut

		addWoodType(new TreeConfiguration(Global.WOOD_ALL[i], i, 750F, 16000F, 1F, 14F, 0F, 1F, true));i++; //Douglas Fir
		addWoodType(new TreeConfiguration(Global.WOOD_ALL[i], i, 250F, 16000F, 4F, 24F, 0F, 1F, false));i++; //Hickory
		addWoodType(new TreeConfiguration(Global.WOOD_ALL[i], i, 250F, 16000F, 3F, 20F, 0F, 1F, false));i++; //Maple
		addWoodType(new TreeConfiguration(Global.WOOD_ALL[i], i, 250F, 16000F, 4F, 24F, 0.5F, 2F, false));i++; //Ash

		addWoodType(new TreeConfiguration(Global.WOOD_ALL[i], i, 250F, 16000F, -15F, 24F, 0.5F, 2F, true));i++; //Pine
		addWoodType(new TreeConfiguration(Global.WOOD_ALL[i], i, 4000F, 16000F, 10F, 16F, 0F, 0.5F, true));i++; //Sequoia (Redwood) | Why 2 names??
		addWoodType(new TreeConfiguration(Global.WOOD_ALL[i], i, 250F, 16000F, -5F, 24F, 0F, 1F, true));i++; //Spruce
		addWoodType(new TreeConfiguration(Global.WOOD_ALL[i], i, 400F, 16000F, 6F, 30F, 0F, 1F, false));i++; //Sycamore

		addWoodType(new TreeConfiguration(Global.WOOD_ALL[i], i, 250F, 16000F, -5F, 24F, 0F, 2F, true));i++; //White Ceder
		addWoodType(new TreeConfiguration(Global.WOOD_ALL[i], i, 400F, 16000F, 4F, 30F, 0F, 1F, false));i++; //White Elm
		addWoodType(new TreeConfiguration(Global.WOOD_ALL[i], i, 4000F, 16000F, 10F, 30F, 0F, 0.5F, false));i++; //Willow
		addWoodType(new TreeConfiguration(Global.WOOD_ALL[i], i, 4000F, 16000F, 24F, 44F, 0F, 1F, false));i++; //Kapok

		addWoodType(new TreeConfiguration(Global.WOOD_ALL[i], i, 75F, 1000F, 20F, 50F, 0F, 1F, false));i++; //Acacia (Utacacia) | Why 2 names?? | Utacacia are the types you see in savannah, Acacia are large jungle trees
	}

	public void addWoodType(TreeConfiguration configuration)
	{
		if(!treeTypeHash.containsKey(configuration.name))
			treeTypeHash.put(configuration.name, configuration);
	}

	public int checkValidity(String n)
	{
		int index = indexFromString(n);
		if(index >= 0 ) return index;
		return -1;
	}

	/**
	 * @param n Name of the Tree type. Used as the Key in the hash map for lookups.
	 * @return Tree index that is unique to that tree
	 */
	public int indexFromString(String n)
	{
		if(treeTypeHash.containsKey(n))
			return ((TreeConfiguration) treeTypeHash.get(n)).index;
		return -1;
	}

	/**
	 * @param n Name of the Tree type. Used as the Key in the hash map for lookups.
	 * @return Full TreeConfiguration file
	 */
	public TreeConfiguration treeFromString(String n)
	{
		if(treeTypeHash.containsKey(n))
			return ((TreeConfiguration) treeTypeHash.get(n));
		return null;
	}
	/**
	 * @param id The tree type ID.
	 * @return Full TreeConfiguration file
	 */
	public TreeConfiguration treeFromID(int id)
	{
		for(;treeTypeHash.values().iterator().hasNext();)
		{
			TreeConfiguration config = (TreeConfiguration)treeTypeHash.values().iterator().next();
			if(config.index == id)
				return config;
		}
		return null;
	}

	public TreeConfiguration getRandomTree()
	{
		int id = new Random().nextInt(treeTypeHash.values().toArray().length);
		return treeFromID(id);
	}
}
