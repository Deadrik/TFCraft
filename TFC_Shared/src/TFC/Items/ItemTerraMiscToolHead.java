package TFC.Items;

import java.util.List;

import TFC.Core.TFCSettings;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerraMiscToolHead extends ItemTerra implements ITextureProvider
{

	public ItemTerraMiscToolHead(int i) 
	{
		super(i);
		this.setMaxDamage(100);
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
