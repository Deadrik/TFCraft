package TFC.Core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

public class ShapelessRecipesTFC implements IRecipe
{
	/** Is the ItemStack that you get when craft the recipe. */
	private final ItemStack recipeOutput;

	/** Is a List of ItemStack that composes the recipe. */
	private final List recipeItems;

	public ShapelessRecipesTFC(ItemStack par1ItemStack, List par2List)
	{
		this.recipeOutput = par1ItemStack;
		this.recipeItems = par2List;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
	{
		return this.recipeOutput.copy();
	}

	public ItemStack getRecipeOutput()
	{
		return this.recipeOutput;
	}

	/**
	 * Returns the size of the recipe area
	 */
	public int getRecipeSize()
	{
		return this.recipeItems.size();
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	public boolean matches(InventoryCrafting par1InventoryCrafting, World world)
	{
		ArrayList var2 = new ArrayList(this.recipeItems);

		for (int var3 = 0; var3 < 5; ++var3)
		{
			for (int var4 = 0; var4 < 5; ++var4)
			{
				ItemStack var5 = par1InventoryCrafting.getStackInRowAndColumn(var4, var3);

				if (var5 != null)
				{
					boolean var6 = false;
					Iterator var7 = var2.iterator();

					while (var7.hasNext())
					{
						ItemStack var8 = (ItemStack)var7.next();

						if (var5.itemID == var8.itemID && (var8.getItemDamage() == -1 || var5.getItemDamage() == var8.getItemDamage()))
						{
							var6 = true;
							var2.remove(var8);
							break;
						}
					}

					if (!var6)
					{
						return false;
					}
				}
			}
		}

		return var2.isEmpty();
	}
}
