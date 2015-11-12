package com.bioxx.tfc.api.Crafting;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.TileEntities.TEBarrel;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IFood;

public class BarrelPreservativeRecipe {
	private boolean requiresBrined;
	private boolean requiresPickled;
	private boolean requiresSalted;
	private boolean requiresDried;
	private boolean requiresSmoked;
	private boolean requiresInfused;
	
	private boolean requiresSealed;
	
	private boolean allowGrains = true;
	private boolean allowProteins = true;
	private boolean allowVegetables = true;
	private boolean allowFruit = true;
	private boolean allowDairy = true;
	
	private FluidStack liquidPerOz;
	
	private float environmentalDecayFactor = -1;
	private float baseDecayModifier = -1;
	private String preservingString;
	
	/***
	 * Checks a barrel to see if it should be preserving. Allows Overriding this function in subclass
	 * @param xCoord
	 * @param yCoord
	 * @param zCoord
	 * @param fluid
	 * @param itemStack
	 * @param sealed
	 * @return
	 */
	public boolean checkForPreservation(TEBarrel barrel, FluidStack fluid, ItemStack itemStack, boolean sealed){
		if(itemStack == null || fluid == null)
		{ 
			return false; 
		}
		if(!(itemStack.getItem() instanceof IFood))
		{
			return false;
		}
		if(fluid.getFluid() != liquidPerOz.getFluid())
		{
			return false;
		}
		IFood iFood = ((IFood)itemStack.getItem());
		if(!allowGrains && iFood.getFoodGroup() == EnumFoodGroup.Grain)
		{
			return false;
		}
		if(!allowProteins && iFood.getFoodGroup() == EnumFoodGroup.Protein)
		{
			return false;
		}
		if(!allowFruit && iFood.getFoodGroup() == EnumFoodGroup.Fruit)
		{
			return false;
		}
		if(!allowVegetables && iFood.getFoodGroup() == EnumFoodGroup.Vegetable)
		{
			return false;
		}
		if(!allowDairy && iFood.getFoodGroup() == EnumFoodGroup.Dairy)
		{
			return false;
		}
		if(requiresBrined && !Food.isBrined(itemStack))
		{
			return false;
		}
		if(requiresPickled && !Food.isPickled(itemStack))
		{
			return false;
		}
		if(requiresSalted && !Food.isSalted(itemStack))
		{
			return false;
		}
		if(requiresDried && !Food.isDried(itemStack))
		{
			return false;
		}
		if(requiresSmoked && !Food.isSmoked(itemStack))
		{
			return false;
		}
		if(requiresInfused && !Food.isInfused(itemStack))
		{
			return false;
		}
		if(requiresSealed && !sealed)
		{
			return false;
		}
		float w = Food.getWeight(itemStack);
		return liquidPerOz.amount * w <= fluid.amount;
	}
	
	public BarrelPreservativeRecipe(FluidStack liquidPerOz, String unlocalizedPreservingLabel){
		this.liquidPerOz = liquidPerOz;
		this.preservingString = unlocalizedPreservingLabel;
	}
	
	public BarrelPreservativeRecipe setRequiresBrined(boolean b)
	{
		requiresBrined = b;
		return this;
	}
	
	public BarrelPreservativeRecipe setRequiresPickled(boolean b)
	{
		requiresPickled = b;
		return this;
	}
	
	public BarrelPreservativeRecipe setRequiresSalted(boolean b)
	{
		requiresSalted = b;
		return this;
	}
	
	public BarrelPreservativeRecipe setRequiresDried(boolean b)
	{
		requiresDried = b;
		return this;
	}
	
	public BarrelPreservativeRecipe setRequiresSmoked(boolean b)
	{
		requiresSmoked = b;
		return this;
	}
	
	public BarrelPreservativeRecipe setRequiresInfused(boolean b)
	{
		requiresInfused = b;
		return this;
	}
	
	public BarrelPreservativeRecipe setRequiresSealed(boolean b)
	{
		requiresSealed = b;
		return this;
	}
	
	public BarrelPreservativeRecipe setAllowProtien(boolean b){
		allowProteins = b;
		return this;
	}
	
	public BarrelPreservativeRecipe setAllowGrains(boolean b){
		allowGrains = b;
		return this;
	}
	
	public BarrelPreservativeRecipe setAllowFruit(boolean b){
		allowFruit = b;
		return this;
	}
	
	public BarrelPreservativeRecipe setAllowVegetable(boolean b){
		allowVegetables = b;
		return this;
	}
	
	public BarrelPreservativeRecipe setAllowDairy(boolean b){
		allowDairy = b;
		return this;
	}

	public BarrelPreservativeRecipe setEnvironmentalDecayFactor(float rate) {
		environmentalDecayFactor = rate;
		return this;
	}

	public BarrelPreservativeRecipe setBaseDecayModifier(float rate) {
		baseDecayModifier = rate;
		return this;
	}
	
	
	public float getEnvironmentalDecayFactor(){
		return environmentalDecayFactor;
	}
	
	public float getBaseDecayModifier(){
		return baseDecayModifier;
	}

	public String getPreservingString() {
		return preservingString;
	}

}
