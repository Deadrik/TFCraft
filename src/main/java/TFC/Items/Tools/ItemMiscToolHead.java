package TFC.Items.Tools;

import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFCTabs;
import TFC.Items.ItemTerra;
public class ItemMiscToolHead extends ItemTerra
{
	private ToolMaterial material = null;

	public ItemMiscToolHead()
	{
		super();
		this.setMaxDamage(100);
		this.setMaxStackSize(4);
		setCreativeTab(TFCTabs.TFCUnfinished);
		this.setFolder("toolheads/");
		this.setWeight(EnumWeight.MEDIUM);
		this.setSize(EnumSize.SMALL);
	}

	public ItemMiscToolHead(ToolMaterial m)
	{
		this();
		material = m;
	}

	public ToolMaterial getMaterial()
	{
		return material;
	}
}
