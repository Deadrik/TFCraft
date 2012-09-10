package TFC.Items;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemSed extends ItemTerraBlock
{
	{blockNames = new String[]{"Siltstone", "Mudstone", "Shale", "Claystone", "Rock Salt", "Limestone", "Conglomerate", "Dolomite", "Chert", "Chalk"};}

	public ItemSed(int i) 
	{
		super(i);
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terraRock.png";
	}
}
