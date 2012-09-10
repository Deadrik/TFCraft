package TFC.Items;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemMM extends ItemTerraBlock
{
	{
		blockNames = new String[]{ "Quartzite", "Slate", "Phyllite", "Schist", "Gneiss", "Marble"};
	}
	
	public ItemMM(int i) 
	{
		super(i);
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terraRock.png";
	}
}
