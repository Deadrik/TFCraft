package TFC.Items;

import net.minecraft.src.Item;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerraMiscTool extends ItemTerra implements ITextureProvider
{

	public ItemTerraMiscTool(int i) 
	{
		super(i);
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terratools.png";
	}	
}
