package TFC.Items;

import TFC.*;
import TFC.Core.TFC_Textures;

public class ItemIgEx extends ItemTerraBlock
{
	{
		blockNames = new String[]{"Rhyolite", "Basalt", "Andesite", "Dacite"};
	}

	public ItemIgEx(int i) 
	{
		super(i);
	}

	@Override
	public String getTextureFile()
	{
		return TFC_Textures.RockSheet;
	}
}
