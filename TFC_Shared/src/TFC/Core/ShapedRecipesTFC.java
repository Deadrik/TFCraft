package TFC.Core;

import TFC.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

public class ShapedRecipesTFC implements IRecipe
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

	public boolean matches(InventoryCrafting inventorycrafting, World world)
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
