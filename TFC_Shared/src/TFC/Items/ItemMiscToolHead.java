package TFC.Items;

import java.util.List;

import TFC.Core.TFC_Settings;
import TFC.Enums.EnumSize;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemMiscToolHead extends ItemTerra
{

	public ItemMiscToolHead(int i) 
	{
		super(i);
		this.setMaxDamage(100);
		this.setMaxStackSize(4);
		this.setTabToDisplayOn(CreativeTabs.tabMisc);
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terratoolheads.png";
	}
	
	@Override
	public EnumSize getSize() {
		return EnumSize.MEDIUM;
	}
}
