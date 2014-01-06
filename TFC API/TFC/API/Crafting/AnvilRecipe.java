package TFC.API.Crafting;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import TFC.Core.TFC_Core;

public class AnvilRecipe
{
	ItemStack result;
	String plan = "";
	ItemStack input1;
	ItemStack input2;
	boolean flux;
	int craftingValue;
	int anvilreq;
	boolean inheritsDamage;
	public int craftingXP = 1;
	ArrayList<String> skillsList = new ArrayList<String>();

	public void addSkills(EntityPlayer player)
	{
		for(String s : skillsList)
			TFC_Core.getSkillStats(player).increaseSkill(s, craftingXP);
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */    
	public boolean matches(AnvilRecipe A)
	{   
		if(     areItemStacksEqual(input1, A.input1) && 
				areItemStacksEqual(input2, A.input2) &&
				plan.equals(A.plan) &&
				AnvilReq.matches(anvilreq, A.anvilreq))
		{
			if(this.flux && !A.flux)
				return false;
			return true;
		}
		return false;
	}

	public boolean isComplete(AnvilManager am, AnvilRecipe A, int[] rules)
	{
		PlanRecipe pr = am.getPlan(A.plan);
		if(     areItemStacksEqual(input1, A.input1) && 
				areItemStacksEqual(input2, A.input2) &&
				plan.equals(A.plan) &&
				pr.rules[0].matches(rules, 0) && pr.rules[1].matches(rules, 1) && pr.rules[2].matches(rules, 2) && 
				craftingValue == A.craftingValue && AnvilReq.matches(anvilreq, A.anvilreq))
			if(this.flux && A.flux)
				return true;
			else if (!this.flux)
				return true;
		return false;
	}

	public boolean isComplete(AnvilRecipe A)
	{
		if(A.input1 == this.input1 && A.input2 == input2 && 
				craftingValue == A.craftingValue && plan.equals(A.plan) && AnvilReq.matches(anvilreq, A.anvilreq))
			if(this.flux && A.flux)
				return true;
			else if (!this.flux)
				return true;
		return false;
	}

	private boolean areItemStacksEqual(ItemStack is1, ItemStack is2)
	{
		if(is1 == null && is2 == null)
			return true;

		if((is1 == null && is2 != null) || (is1 != null && is2 == null)) 
			return false;

		if(is1.itemID != is2.itemID)
			return false;

		if(is1.getItemDamage() != 32767 && is1.getItemDamage() != is2.getItemDamage())
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
		ItemStack is = result;
		if(this.inheritsDamage)
			is.setItemDamage(input.getItemDamage());
		return is;
	}

	public AnvilRecipe(ItemStack in, ItemStack in2, String p, boolean flux, AnvilReq req, ItemStack result)
	{
		this(in, in2, p.toLowerCase(), 70 + new Random((in != null ? in.itemID : 0) + (result != null ? result.itemID : 0)).nextInt(60), flux, req.Tier, result);
	}

	public AnvilRecipe(ItemStack in, ItemStack in2, String p, AnvilReq req, ItemStack result)
	{
		this(in, in2, p.toLowerCase(), 70 + new Random((in != null ? in.itemID : 0) + (result != null ? result.itemID : 0)).nextInt(60), false, req.Tier, result);
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
		skillsList.add("General_Smithing");
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

	public AnvilRecipe setInheritsDamage()
	{
		inheritsDamage = true;
		return this;
	}

	public AnvilRecipe addSkill(String s)
	{
		this.skillsList.add(s);
		return this;
	}

	public int getCraftingValue()
	{
		return craftingValue;
	}

	public int getSkillTotal(EntityPlayer p)
	{
		int skill = 0;
		int total = 0;
		for (String s : skillsList)
		{
			total++;
			skill+=TFC_Core.getSkillStats(p).getSkill(s);
		}
		return skill/total;
	}
}


