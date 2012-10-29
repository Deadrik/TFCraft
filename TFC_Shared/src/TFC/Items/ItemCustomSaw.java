package TFC.Items;

import java.util.List;

import TFC.*;
import TFC.Core.TFC_Settings;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;

public class ItemCustomSaw extends ItemCustomAxe implements ISize
{
	public ItemCustomSaw(int i, EnumToolMaterial e)
	{
		super(i, e);
		this.setMaxDamage((int)(e.getMaxUses()*0.85));
		this.efficiencyOnProperMaterial = e.getEfficiencyOnProperMaterial()*1.35F;
	}
	@Override
	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
	{
		return par2Block != null && par2Block.blockMaterial == Material.wood ? this.efficiencyOnProperMaterial*1.35F : super.getStrVsBlock(par1ItemStack, par2Block);
	}

	public String getTextureFile() {
		return "/bioxx/terratools.png";
	}
	
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
    {
		ItemTerra.addSizeInformation(this, arraylist);
		
        if(TFC_Settings.enableDebugMode)
            arraylist.add("Damage: "+is.getItemDamage());
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
		return EnumSize.MEDIUM;
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