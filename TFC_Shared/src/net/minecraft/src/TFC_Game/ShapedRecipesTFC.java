package net.minecraft.src.TFC_Game;

import net.minecraft.src.*;

public class ShapedRecipesTFC
implements IRecipe
{
	private int recipeWidth;
	private int recipeHeight;
	private ItemStack recipeItems[];
	private ItemStack recipeOutput;
	public final int recipeOutputItemID;

	public ShapedRecipesTFC(int i, int j, ItemStack aitemstack[], ItemStack itemstack)
	{
		recipeOutputItemID = itemstack.itemID;
		recipeWidth = i;
		recipeHeight = j;
		recipeItems = aitemstack;
		recipeOutput = itemstack;
	}

	private boolean compare(InventoryCrafting inventorycrafting, int i, int j, boolean flag)
	{
		for (int k = 0; k < 5; k++)
		{
			for (int l = 0; l < 5; l++)
			{
				int i1 = k - i;
				int j1 = l - j;
				ItemStack itemstack = null;
				if (i1 >= 0 && j1 >= 0 && i1 < recipeWidth && j1 < recipeHeight)
				{
					if (flag)
					{
						itemstack = recipeItems[recipeWidth - i1 - 1 + j1 * recipeWidth];
					}
					else
					{
						itemstack = recipeItems[i1 + j1 * recipeWidth];
					}
				}
				ItemStack itemstack1 = inventorycrafting.getStackInRowAndColumn(k, l);
				if (itemstack1 == null && itemstack == null)
				{
					continue;
				}
				if (itemstack1 == null && itemstack != null || itemstack1 != null && itemstack == null)
				{
					return false;
				}
				if (itemstack.itemID != itemstack1.itemID)
				{
					return false;
				}
				if (itemstack.getItemDamage() != -1 && itemstack.getItemDamage() != itemstack1.getItemDamage())
				{
					return false;
				}
			}
		}

		return true;
	}

	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting)
	{
		return new ItemStack(recipeOutput.itemID, recipeOutput.stackSize, recipeOutput.getItemDamage());
	}

	public ItemStack getRecipeOutput()
	{
		return recipeOutput;
	}

	public int getRecipeSize()
	{
		return recipeWidth * recipeHeight;
	}

	public boolean matches(InventoryCrafting inventorycrafting)
	{
		for (int i = 0; i <= 5 - recipeWidth; i++)
		{
			for (int j = 0; j <= 5 - recipeHeight; j++)
			{
				if (compare(inventorycrafting, i, j, true))
				{
					return true;
				}
				if (compare(inventorycrafting, i, j, false))
				{
					return true;
				}
			}
		}

		return false;
	}
}
