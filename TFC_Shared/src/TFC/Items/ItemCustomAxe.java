package TFC.Items;

import java.util.List;

import TFC.*;
import TFC.Core.TFC_Settings;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemAxe;
import net.minecraft.src.ItemStack;

public class ItemCustomAxe extends ItemAxe implements ISize
{
	private int weaponDamage = 1;
	
	public ItemCustomAxe(int i, EnumToolMaterial e)
	{
		super(i, e);
		this.setMaxDamage(e.getMaxUses());
		this.weaponDamage = 2 + e.getDamageVsEntity();
	}
	
	@Override
    public String getTextureFile()
    {
        return "/bioxx/terratools.png";
    }

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
    {
		ItemTerra.addSizeInformation(this, arraylist);
		
        if(TFC_Settings.enableDebugMode)
            arraylist.add("Damage: " + is.getItemDamage());
    }
	
	public int getItemStackLimit()
    {
    	if(canStack())
    		return this.getSize().stackSize * getWeight().multiplier;
    	else
    		return 1;
    }

	@Override
	public EnumSize getSize() {
		return EnumSize.LARGE;
	}
	
	@Override
	public boolean canStack() 
	{
		return false;
	}

	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.MEDIUM;
	}
}