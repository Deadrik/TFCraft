package TFC.Items;

import java.util.List;

import TFC.Core.TFCSettings;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemTerraMiscToolHead extends ItemTerra
{

	public ItemTerraMiscToolHead(int i) 
	{
		super(i);
		this.setMaxDamage(100);
		this.setMaxStackSize(4);
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terratoolheads.png";
	}	
	
	public void addInformation(ItemStack is, List arraylist) 
    {
        if(TFCSettings.enableDebugMode)
            arraylist.add("Damage: "+is.getItemDamage());
    }
}
