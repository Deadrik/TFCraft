package TFC.Items.Tools;

import net.minecraft.item.EnumToolMaterial;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
import TFC.Items.ItemTerra;
public class ItemMiscToolHead extends ItemTerra
{
	private EnumToolMaterial material = null;
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
	public ItemMiscToolHead(int i, EnumToolMaterial m) 
	{
		this(i);
		material = m;
	}
	
	public EnumToolMaterial getMaterial(){
		return material;
	}
}
