package TFC.Items.Tools;

import TFC.API.TFCTabs;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Items.ItemTerra;
public class ItemMiscToolHead extends ItemTerra
{
	public ItemMiscToolHead(int i) 
	{
		super(i);
		this.setMaxDamage(100);
		this.setMaxStackSize(4);
		setCreativeTab(TFCTabs.TFCUnfinished);
		this.setFolder("toolheads/");
		this.setWeight(EnumWeight.MEDIUM);
		this.setSize(EnumSize.SMALL);
	}
}
