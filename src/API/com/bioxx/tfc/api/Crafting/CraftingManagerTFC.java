package com.bioxx.tfc.api.Crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class CraftingManagerTFC
{
	private static final CraftingManagerTFC INSTANCE = new CraftingManagerTFC();
	public static final CraftingManagerTFC getInstance()
	{
		return INSTANCE;
	}

	private List<IRecipe> recipes;

	private CraftingManagerTFC()
	{
		recipes = new ArrayList<IRecipe>();

		Collections.sort(recipes, new RecipeSorterTFC(this));
		//System.out.println(new StringBuilder().append(recipes.size()).append(" recipes").toString());
	}

	public ShapedRecipesTFC addRecipe(ItemStack itemstack, Object aobj[])
	{
		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;
		if (aobj[i] instanceof String[])
		{
			String as[] = (String[])aobj[i++];
			for (int l = 0; l < as.length; l++)
			{
				String s2 = as[l];
				k++;
				j = s2.length();
				s = new StringBuilder().append(s).append(s2).toString();
			}
		}
		else
		{
			while (aobj[i] instanceof String)
			{
				String s1 = (String)aobj[i++];
				k++;
				j = s1.length();
				s = new StringBuilder().append(s).append(s1).toString();
			}
		}
		HashMap<Character, ItemStack> hashmap = new HashMap<Character, ItemStack>();
		for (; i < aobj.length; i += 2)
		{
			Character character = (Character)aobj[i];
			ItemStack itemstack1 = null;
			if (aobj[i + 1] instanceof Item)
			{
				itemstack1 = new ItemStack((Item)aobj[i + 1]);
			}
			else if (aobj[i + 1] instanceof Block)
			{
				itemstack1 = new ItemStack((Block)aobj[i + 1], 1, -1);
			}
			else if (aobj[i + 1] instanceof ItemStack)
			{
				itemstack1 = (ItemStack)aobj[i + 1];
			}
			hashmap.put(character, itemstack1);
		}

		ItemStack aitemstack[] = new ItemStack[j * k];
		for (int i1 = 0; i1 < j * k; i1++)
		{
			char c = s.charAt(i1);
			if (hashmap.containsKey(Character.valueOf(c)))
			{
				aitemstack[i1] = hashmap.get(Character.valueOf(c)).copy();
			}
			else
			{
				aitemstack[i1] = null;
			}
		}

		ShapedRecipesTFC shapedRecipesTFC = new ShapedRecipesTFC(j, k, aitemstack, itemstack);
		recipes.add(shapedRecipesTFC);
		return shapedRecipesTFC;
	}

	public ShapelessRecipesTFC addShapelessRecipe(ItemStack itemstack, Object aobj[])
	{
		ArrayList<ItemStack> arraylist = new ArrayList<ItemStack>();
		Object aobj1[] = aobj;
		int i = aobj1.length;
		for (int j = 0; j < i; j++)
		{
			Object obj = aobj1[j];
			if (obj instanceof ItemStack)
			{
				arraylist.add(((ItemStack)obj).copy());
				continue;
			}
			if (obj instanceof Item)
			{
				arraylist.add(new ItemStack((Item)obj));
				continue;
			}
			if (obj instanceof Block)
			{
				arraylist.add(new ItemStack((Block)obj));
			}
			else
			{
				throw new RuntimeException("Invalid shapeless recipy!");
			}
		}
		ShapelessRecipesTFC recipesTFC = new ShapelessRecipesTFC(itemstack, arraylist);
		recipes.add(recipesTFC);
		return recipesTFC;
	}

	public ItemStack findMatchingRecipe(InventoryCrafting inventorycrafting, World world)
	{
		/*int i = 0;
		ItemStack itemstack = null;
		ItemStack itemstack1 = null;
		for (int j = 0; j < inventorycrafting.getSizeInventory(); j++)
		{
			ItemStack itemstack2 = inventorycrafting.getStackInSlot(j);
			if (itemstack2 == null)
			{
				continue;
			}
			if (i == 0)
			{
				itemstack = itemstack2;
			}
			if (i == 1)
			{
				itemstack1 = itemstack2;
			}
			i++;
		}
		
		if (i == 2 && itemstack.getItem() == itemstack1.getItem() && itemstack.stackSize == 1 && itemstack1.stackSize == 1 && itemstack.getItem().isRepairable())
		{
			//            Item item = Item.itemsList[itemstack.itemID];
			//            int l = item.getMaxDamage() - itemstack.getItemDamageForDisplay();
			//            int i1 = item.getMaxDamage() - itemstack1.getItemDamageForDisplay();
			//            int j1 = l + i1 + (item.getMaxDamage() * 10) / 100;
			//            int k1 = item.getMaxDamage() - j1;
			//            if (k1 < 0)
			//            {
			//                k1 = 0;
			//            }
			//            return new ItemStack(itemstack.itemID, 1, k1);
		}*/
		for (int k = 0; k < recipes.size(); k++)
		{
			IRecipe irecipe = recipes.get(k);
			if (irecipe.matches(inventorycrafting, world))
			{
				return irecipe.getCraftingResult(inventorycrafting);
			}
		}

		return null;
	}

	public List<IRecipe> getRecipeList()
	{
		return recipes;
	}
}
