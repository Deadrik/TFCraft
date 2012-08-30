package TFC.Items;

import java.util.List;

import TFC.Core.TFC_Settings;
import net.minecraft.src.*;

public class ItemCustomSaw extends ItemAxe
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
	
	public void addInformation(ItemStack is, List arraylist) 
    {
        if(TFC_Settings.enableDebugMode)
            arraylist.add("Damage: "+is.getItemDamage());
    }
}