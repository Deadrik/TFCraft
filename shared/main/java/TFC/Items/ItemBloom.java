package TFC.Items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import TFC.TFCItems;
import TFC.API.ISmeltable;
import TFC.API.Metal;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFCTabs;

public class ItemBloom extends ItemTerra implements ISmeltable
{
	public ItemBloom(int i) 
	{
		super(i);
		setHasSubtypes(true);
		setCreativeTab(TFCTabs.TFCMaterials);
		this.setWeight(EnumWeight.HEAVY);
		this.setSize(EnumSize.LARGE);
	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List arraylist)
	{
		arraylist.add(is.getItemDamage()+"%");
	}

	public void addCreativeItems(java.util.ArrayList list)
	{
		list.add(new ItemStack(this));
	}

	@Override
	public Metal GetMetalType(ItemStack is) 
	{
		return Global.WROUGHTIRON;
	}

	@Override
	public short GetMetalReturnAmount(ItemStack is) 
	{
		return (short) is.getItemDamage();
	}

	@Override
	public boolean isSmeltable(ItemStack is) 
	{
		if(this.itemID == TFCItems.Bloom.itemID) {
			return true;
		}
		return false;
	}

	@Override
	public EnumTier GetSmeltTier(ItemStack is) 
	{
		return EnumTier.TierIII;
	}

}
