package TFC.Items;

import net.minecraft.src.Item;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerraMeltedMetal extends ItemTerra implements ITextureProvider
{
	public ItemTerraMeltedMetal(int i) 
	{
		super(i);
		setMaxDamage(100);
		this.setMaxStackSize(1);
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrasprites.png";
	}	
}
