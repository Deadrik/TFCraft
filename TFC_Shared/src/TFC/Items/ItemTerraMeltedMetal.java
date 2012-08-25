package TFC.Items;

import net.minecraft.src.Item;

public class ItemTerraMeltedMetal extends ItemTerra
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
