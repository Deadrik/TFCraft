package TFC.Items;

import TFC.Core.TFC_Textures;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemIgIn extends ItemTerraBlock
{
	{blockNames = new String[]{"Granite", "Diorite", "Gabbro"};}


	public ItemIgIn(int i) 
	{
		super(i);
	}

	@Override
	public String getTextureFile()
	{
		return TFC_Textures.RockSheet;
	}
}
