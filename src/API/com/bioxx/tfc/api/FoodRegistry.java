package com.bioxx.tfc.api;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;

import com.bioxx.tfc.api.Enums.EnumFoodGroup;

public class FoodRegistry 
{
	private static final FoodRegistry INSTANCE = new FoodRegistry();
	public static final FoodRegistry getInstance()
	{
		return INSTANCE;
	}

	private int proteinCount;
	private Map<Integer, Item> proteinMap;
	private int vegetableCount = 10000;
	private Map<Integer, Item> vegetableMap;
	private int fruitCount = 20000;
	private Map<Integer, Item> fruitMap;
	private int grainCount = 30000;
	private Map<Integer, Item> grainMap;
	private int dairyCount = 40000;
	private Map<Integer, Item> dairyMap;

	private FoodRegistry()
	{
		proteinMap = new HashMap<Integer, Item>();
		vegetableMap = new HashMap<Integer, Item>();
		fruitMap = new HashMap<Integer, Item>();
		grainMap = new HashMap<Integer, Item>();
		dairyMap = new HashMap<Integer, Item>();
	}

	public int registerFood(EnumFoodGroup efg, Item i)
	{
		switch(efg)
		{
		case Protein:
		{
			proteinMap.put(proteinCount, i);
			return proteinCount++;
		}
		case Vegetable:
		{
			vegetableMap.put(vegetableCount, i);
			return vegetableCount++;
		}
		case Fruit:
		{
			fruitMap.put(fruitCount, i);
			return fruitCount++;
		}
		case Grain:
		{
			grainMap.put(grainCount, i);
			return grainCount++;
		}
		case Dairy:
		{
			dairyMap.put(dairyCount, i);
			return dairyCount++;
		}
		default:
		{
			return -1;
		}
		}
	}

	public Item getFood(int id)
	{
		if(proteinMap.containsKey(id))
			return proteinMap.get(id);
		if(vegetableMap.containsKey(id))
			return vegetableMap.get(id);
		if(fruitMap.containsKey(id))
			return fruitMap.get(id);
		if(grainMap.containsKey(id))
			return grainMap.get(id);
		if(dairyMap.containsKey(id))
			return dairyMap.get(id);
		return null;
	}

	public EnumFoodGroup getFoodGroup(int id)
	{
		if(proteinMap.containsKey(id))
			return EnumFoodGroup.Protein;
		if(vegetableMap.containsKey(id))
			return EnumFoodGroup.Vegetable;
		if(fruitMap.containsKey(id))
			return EnumFoodGroup.Fruit;
		if(grainMap.containsKey(id))
			return EnumFoodGroup.Grain;
		if(dairyMap.containsKey(id))
			return EnumFoodGroup.Dairy;
		return EnumFoodGroup.None;
	}
}
