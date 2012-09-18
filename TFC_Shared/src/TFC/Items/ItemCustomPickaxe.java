package TFC.Items;

import java.util.List;

import TFC.Core.TFC_Settings;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import net.minecraft.src.*;

public class ItemCustomPickaxe extends ItemPickaxe implements ISize
{
	public ItemCustomPickaxe(int i, EnumToolMaterial e)
	{
		super(i, e);
	}

	public String getTextureFile() {
		return "/bioxx/terratools.png";
	}

	public void addInformation(ItemStack is, List arraylist) 
    {
		ItemTerra.addSizeInformation(this, arraylist);
		
        if(TFC_Settings.enableDebugMode)
            arraylist.add("Damage: "+is.getItemDamage());
    }

	@Override
	public EnumSize getSize() {
		// TODO Auto-generated method stub
		return EnumSize.MEDIUM;
	}

	@Override
	public boolean canStack() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public int getItemStackLimit()
    {
    	if(canStack())
    		return this.getSize().stackSize * getWeight().multiplier;
    	else
    		return 1;
    }

	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.MEDIUM;
	}
}