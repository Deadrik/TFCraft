package com.bioxx.tfc.api;

import java.util.Random;
import java.util.Vector;

public class TreeSchemManager
{
	private int ID;
	/**
	 * Vector of Vectors. List of schematics is sorted first by growth stage, then schem ID
	 */
	private Vector<Vector<TreeSchematic>> treeList;

	public TreeSchemManager(int id)
	{
		this.ID = id;
		this.treeList = new Vector<Vector<TreeSchematic>>();
	}

	public void addSchem(TreeSchematic ts)
	{
		int growth = ts.getGrowthStage();
		if(treeList.size() <= growth)
			treeList.setSize(growth + 1);
		//System.out.println("Adding " + ts.getIndex() + ":" + ts.getPath() + ": Stage " + growth);
		if(treeList.get(growth) == null)
			treeList.set(growth, new Vector<TreeSchematic>());

		treeList.get(growth).add(ts);
	}

	/**
	 * @return Returns a treeschem of any growth stage
	 */
	public TreeSchematic getRandomSchematic(Random R)
	{
		Vector<TreeSchematic> v = treeList.get(R.nextInt(treeList.size()));
		if(v != null)
		{
			return v.get(R.nextInt(v.size()));
		}
		return null;
	}

	/**
	 * @return Returns a treeschem of specified growth stage
	 */
	public TreeSchematic getRandomSchematic(Random R, int growthStage)
	{
		//Gets the list of schems for the specified growth stages
		Vector<TreeSchematic> v = treeList.get(growthStage);
		if(v != null)
		{
			return v.get(R.nextInt(v.size()));
		}
		return null;
	}

	public TreeSchematic getSchematic(int schem, int growthStage)
	{
		//Gets the list of schems for the specified growth stages
		Vector<TreeSchematic> v = treeList.get(growthStage);
		if(v != null)
		{
			return v.get(schem);
		}
		return null;
	}
}
