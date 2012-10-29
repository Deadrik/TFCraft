package TFC.Items;

import TFC.*;

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
		return "/bioxx/terraRock.png";
	}
}
