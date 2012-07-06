package TFC.Items;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.forge.ITextureProvider;

public class ItemIgExCobble extends ItemBlock implements ITextureProvider
{
	public static String[] blockNames = {"Rhyolite", "Basalt", "Andesite", "Dacite"};

	public ItemIgExCobble(int i) 
	{
		super(i);
		setHasSubtypes(true);
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) 
	{
	    int dam = itemstack.getItemDamage();
		String s = new StringBuilder().append(super.getItemName()).append(".").append(blockNames[dam]).toString();
		return s;
	}
	@Override
	public int getMetadata(int i) 
	{		
		return i;
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terraRock.png";
	}
}
