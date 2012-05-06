package net.minecraft.src.TFC_Core.General;

import java.util.Random;

import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class AnvilRecipe
{
    ItemStack result;
    CraftingRule rule0;
    CraftingRule rule1;
    CraftingRule rule2;
    Item input1;
    Item input2;
    boolean flux;
    int craftingValue;
    
    
    /**
     * Used to check if a recipe matches current crafting inventory
     */    
    public boolean matches(AnvilRecipe A)
    {
        if(A.input1 == this.input1 && A.input2 == input2)
        {
            if(this.flux && !A.flux)
                return false;
        }
        return true;
    }
    
    public boolean isComplete(AnvilRecipe A)
    {
        if(A.input1 == this.input1 && A.input2 == input2 && A.rule0 == this.rule0 && A.rule1 == this.rule1 && A.rule2 == this.rule2 && craftingValue == A.craftingValue)
        {
            if(this.flux && A.flux == true)
                return true;
        }
        return false;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult()
    {
        return result;
    }
    
    public AnvilRecipe(Random r, Item in, Item p, int cv, CraftingRule rule0, CraftingRule rule1, CraftingRule rule2, boolean flux, int v)
    {
        input1 = in;
        input2 = p;
        this.rule0 = rule0;
        this.rule1 = rule1;
        this.rule2 = rule2;
        this.flux = flux;
        if(v > 0)
            this.craftingValue = cv + (-v + r.nextInt(v*2));
        else
            this.craftingValue = cv;
    }
    
    public AnvilRecipe(Random r, Item in, Item p, int cv, CraftingRule rule0, CraftingRule rule1, CraftingRule rule2, boolean flux)
    {
        input1 = in;
        input2 = p;
        this.rule0 = rule0;
        this.rule1 = rule1;
        this.rule2 = rule2;
        this.flux = flux;

            this.craftingValue = cv;
    }
    
    public AnvilRecipe(Item in, Item p, boolean flux)
    {
        input1 = in;
        input2 = p;
        this.flux = flux;
    }

    public int getCraftingValue()
    {
        return craftingValue;
    }
    
    public CraftingRule[] getRules()
    {
        return new CraftingRule[]{rule0,rule1,rule2};
    }
    
}


