package TFC.Core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

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
	@Override
	public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
	{
		return this.recipeOutput.copy();
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return this.recipeOutput;
	}

	/**
	 * Returns the size of the recipe area
	 */
	@Override
	public int getRecipeSize()
	{
		return this.recipeItems.size();
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(InventoryCrafting par1InventoryCrafting, World world)
	{
		ArrayList var2 = new ArrayList(this.recipeItems);

		for (int var3 = 0; var3 < 5; ++var3)
		{
			for (int var4 = 0; var4 < 5; ++var4)
			{
				ItemStack inputIS = par1InventoryCrafting.getStackInRowAndColumn(var4, var3);

				if (inputIS != null)
				{
					boolean var6 = false;
					Iterator var7 = var2.iterator();

					while (var7.hasNext())
					{
						ItemStack var8 = (ItemStack)var7.next();

						if (inputIS.itemID == var8.itemID && (var8.getItemDamage() == -1 || inputIS.getItemDamage() == var8.getItemDamage()) &&
								(var8.hasTagCompound() && var8.getTagCompound().hasKey("noTemp") && 
										(!inputIS.hasTagCompound() || (inputIS.hasTagCompound() && !var8.getTagCompound().hasKey("temperature"))))/*&& 
								((!var8.hasTagCompound() && !inputIS.hasTagCompound()) || (var8.hasTagCompound() && NBTMatches(var8, inputIS)))*/)
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
	
	private boolean NBTMatches(ItemStack recipeIS, ItemStack inputIS)
	{
		NBTTagCompound nbt = recipeIS.getTagCompound();
		NBTTagCompound inbt = inputIS.getTagCompound();
		Iterator i = nbt.getTags().iterator();
		
		if(inbt == null)
			return false;
		
		while(i.hasNext())
		{
			NBTBase tag = (NBTBase)i.next();
			if(inbt.hasKey(tag.getName()))
				if(!inbt.getTag(tag.getName()).equals(tag))
					return false;
		}
		return true;
	}
}
