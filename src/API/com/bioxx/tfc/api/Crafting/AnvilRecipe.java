package com.bioxx.tfc.api.Crafting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Constant.Global;

public class AnvilRecipe
{
	public ItemStack result;
	public String plan = "";
	public ItemStack input1;
	public ItemStack input2;
	public boolean flux;
	public int craftingValue;
	public int anvilreq;
	public boolean inheritsDamage;
	public int craftingXP = 1;
	public List<String> skillsList = new ArrayList<String>();
	public static int craftingBoundDefault = 50;

	public AnvilRecipe(ItemStack in, ItemStack in2, String p, boolean flux, AnvilReq req, ItemStack result)
	{
		this(in, in2, p.toLowerCase(), 0, flux, req.Tier, result);
		this.craftingValue = 70 + new Random(TFC_Core.getSuperSeed(AnvilManager.world)+(in != null ? Item.getIdFromItem(in.getItem()) : 0) + (result != null ? Item.getIdFromItem(result.getItem()) : 0)).nextInt(craftingBoundDefault);
	}

	public AnvilRecipe(ItemStack in, ItemStack in2, String p, AnvilReq req, ItemStack result)
	{
		this(in, in2, p.toLowerCase(), 0, false, req.Tier, result);
		this.craftingValue = 70 + new Random(TFC_Core.getSuperSeed(AnvilManager.world)+(in != null ? Item.getIdFromItem(in.getItem()) : 0) + (result != null ? Item.getIdFromItem(result.getItem()) : 0)).nextInt(craftingBoundDefault);
	}

	public AnvilRecipe setCraftingBound(int max)
	{
		craftingValue = 70 + new Random(TFC_Core.getSuperSeed(AnvilManager.world)+(input1 != null ? Item.getIdFromItem(input1.getItem()) : 0) + (result != null ? Item.getIdFromItem(result.getItem()) : 0)).nextInt(max);
		return this;
	}

	public AnvilRecipe(ItemStack in, ItemStack in2, String p, int cv, boolean flux, int req, ItemStack result)
	{
		input1 = in;
		input2 = in2;
		this.flux = flux;
		this.craftingValue = cv;
		anvilreq = req;
		this.result = result;
		inheritsDamage = false;
		this.plan = p;
		skillsList.add(Global.SKILL_GENERAL_SMITHING);
	}

	public AnvilRecipe(ItemStack in, ItemStack p, boolean flux, AnvilReq req)
	{
		this(in, p, flux, req.Tier);
	}

	public AnvilRecipe(ItemStack in, ItemStack p, boolean flux, int req)
	{
		input1 = in;
		input2 = p;
		this.flux = flux;
		anvilreq = req;
		inheritsDamage = false;
	}

	public AnvilRecipe(ItemStack in, ItemStack p, String s, boolean flux, int req)
	{
		this(in, p, flux, req);
		this.plan = s;
	}

	public AnvilRecipe(ItemStack in, ItemStack p, boolean flux, AnvilReq req, ItemStack res)
	{
		this(in, p, req, res);
		this.flux = flux;
	}

	public AnvilRecipe(ItemStack in, ItemStack p, AnvilReq req, ItemStack res)
	{
		input1 = in;
		input2 = p;
		anvilreq = req.Tier;
		this.result = res;
		inheritsDamage = false;
	}

	public AnvilRecipe clearRecipeSkills()
	{
		skillsList.clear();
		return this;
	}

	public AnvilRecipe setCraftingXP(int xp)
	{
		this.craftingXP = xp;
		return this;
	}

	public AnvilRecipe setInheritsDamage()
	{
		inheritsDamage = true;
		return this;
	}

	public AnvilRecipe addRecipeSkill(String s)
	{
		this.skillsList.add(s);
		return this;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */    
	public boolean matches(AnvilRecipe recipe)
	{   
		if(     areItemStacksEqual(input1, recipe.input1) && 
				areItemStacksEqual(input2, recipe.input2) &&
				plan.equals(recipe.plan) &&
				AnvilReq.matches(anvilreq, recipe.anvilreq))
		{
			return !this.flux || recipe.flux;
		}
		return false;
	}

	public boolean isComplete(AnvilManager am, AnvilRecipe recipe, int[] rules)
	{
		PlanRecipe pr = am.getPlan(recipe.plan);
		if(     areItemStacksEqual(input1, recipe.input1) && 
				areItemStacksEqual(input2, recipe.input2) &&
				plan.equals(recipe.plan) &&
				pr.rules[0].matches(rules, 0) && pr.rules[1].matches(rules, 1) && pr.rules[2].matches(rules, 2) && 
				craftingValue == recipe.craftingValue && AnvilReq.matches(anvilreq, recipe.anvilreq))
			if(this.flux && recipe.flux)
				return true;
			else if (!this.flux)
				return true;
		return false;
	}

	public boolean isComplete(AnvilRecipe recipe)
	{
		if(recipe.input1 == this.input1 && recipe.input2 == input2 && 
				craftingValue == recipe.craftingValue && plan.equals(recipe.plan) && AnvilReq.matches(anvilreq, recipe.anvilreq))
			if(this.flux && recipe.flux)
				return true;
			else if (!this.flux)
				return true;
		return false;
	}

	private boolean areItemStacksEqual(ItemStack is1, ItemStack is2)
	{
		if (is1 != null && is2 != null)
		{
			if (is1.getItem() != is2.getItem())
				return false;

			if (is1.getItemDamage() != 32767 && is1.getItemDamage() != is2.getItemDamage())
				return false;
		}
		else if (is1 == null && is2 != null || is1 != null && is2 == null) // XOR, if both are null return true
			return false;

		return true;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult()
	{
		return result;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult(ItemStack input)
	{
		ItemStack is = result.copy();
		if(this.inheritsDamage)
			is.setItemDamage(input.getItemDamage());
		return is;
	}



	public int getCraftingValue()
	{
		return craftingValue;
	}

	public float getSkillMult(EntityPlayer p)
	{
		float skill = 0;
		float total = 0;
		for (String s : skillsList)
		{
			total++;
			skill+=TFC_Core.getSkillStats(p).getSkillMultiplier(s);
		}
		if(total > 0)
			return skill/total;
		return 0;
	}

	public String getPlan()
	{
		return plan;
	}

	public ItemStack getInput1()
	{
		return input1;
	}

	public ItemStack getInput2()
	{
		return input2;
	}

	public boolean isFlux()
	{
		return flux;
	}

	public int getAnvilreq()
	{
		return anvilreq;
	}

	public boolean isInheritsDamage()
	{
		return inheritsDamage;
	}

	public int getCraftingXP()
	{
		return craftingXP;
	}

	public List<String> getSkillsList()
	{
		return skillsList;
	}
}


