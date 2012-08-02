package TFC.Items;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerraIngot extends ItemTerra implements ITextureProvider
{

	public ItemTerraIngot(int i) 
	{
		super(i);
		this.maxStackSize = 8;
	}

	public void addCreativeItems(java.util.ArrayList list)
	{
		list.add(new ItemStack(this));
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrasprites.png";
	}

}
