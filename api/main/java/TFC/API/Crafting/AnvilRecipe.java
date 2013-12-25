package TFC.API.Crafting;

import net.minecraft.item.ItemStack;
import TFC.API.Enums.CraftingRuleEnum;

public class AnvilRecipe
{
    ItemStack result;
    CraftingRuleEnum rule0;
    CraftingRuleEnum rule1;
    CraftingRuleEnum rule2;
    ItemStack input1;
    ItemStack input2;
    boolean flux;
    int craftingValue;
    int anvilreq;
    boolean inheritsDamage;
    
    
    /**
     * Used to check if a recipe matches current crafting inventory
     */    
    public boolean matches(AnvilRecipe A)
    {   
        if(     areItemStacksEqual(input1, A.input1) && 
                areItemStacksEqual(input2, A.input2) &&
                AnvilReq.matches(anvilreq, A.anvilreq))
        {
            if(this.flux && !A.flux)
                return false;
            return true;
        }
        return false;
    }
    
    public boolean isComplete(AnvilRecipe A, int[] rules)
    {
        if(     areItemStacksEqual(input1, A.input1) && 
                areItemStacksEqual(input2, A.input2) &&
                rule0.matches(rules, 0) && rule1.matches(rules, 1) && rule2.matches(rules, 2) && 
                craftingValue == A.craftingValue && AnvilReq.matches(anvilreq, A.anvilreq))
        {
            if(this.flux && A.flux)
                return true;
            else if (!this.flux)
                return true;
        }
        return false;
    }
    
    public boolean isComplete(AnvilRecipe A)
    {
        if(A.input1 == this.input1 && A.input2 == input2 && 
                craftingValue == A.craftingValue && AnvilReq.matches(anvilreq, A.anvilreq))
        {
            if(this.flux && A.flux)
                return true;
            else if (!this.flux)
                return true;
        }
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
    
    public AnvilRecipe(ItemStack in, ItemStack p, int cv, CraftingRuleEnum rule0, CraftingRuleEnum rule1, CraftingRuleEnum rule2, boolean flux, AnvilReq req, ItemStack result)
    {
        input1 = in;
        input2 = p;
        this.rule0 = rule0;
        this.rule1 = rule1;
        this.rule2 = rule2;
        this.flux = flux;
        this.craftingValue = cv;
        anvilreq = req.Tier;
        this.result = result;
        inheritsDamage = false;
    }
    
    public AnvilRecipe(ItemStack in, ItemStack p, int cv, CraftingRuleEnum rule0, CraftingRuleEnum rule1, CraftingRuleEnum rule2, boolean flux, int req, ItemStack result)
    {
        input1 = in;
        input2 = p;
        this.rule0 = rule0;
        this.rule1 = rule1;
        this.rule2 = rule2;
        this.flux = flux;
        this.craftingValue = cv;
        anvilreq = req;
        this.result = result;
        inheritsDamage = false;
    }
    
    public AnvilRecipe(ItemStack in, ItemStack p, boolean flux, AnvilReq req)
    {
        input1 = in;
        input2 = p;
        this.flux = flux;
        anvilreq = req.Tier;
        inheritsDamage = false;
    }
    
    public AnvilRecipe(ItemStack in, ItemStack p, boolean flux, int req)
    {
        input1 = in;
        input2 = p;
        this.flux = flux;
        anvilreq = req;
        inheritsDamage = false;
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

    public int getCraftingValue()
    {
        return craftingValue;
    }
    
    public CraftingRuleEnum[] getRules()
    {
        return new CraftingRuleEnum[]{rule0,rule1,rule2};
    }
    
}


