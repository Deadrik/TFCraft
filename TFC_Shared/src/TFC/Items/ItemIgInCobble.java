package TFC.Items;

import java.util.List;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemIgInCobble extends ItemBlock
{
	public static String[] blockNames = {"Granite", "Diorite", "Gabbro"};

	public ItemIgInCobble(int i) 
	{
		super(i);
		setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) 
	{
		String s = new StringBuilder().append(super.getItemName()).append(".").append(blockNames[itemstack.getItemDamage()]).toString();
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
