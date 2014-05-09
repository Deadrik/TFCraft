package TFC.Items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import TFC.TFCItems;
import TFC.API.HeatRegistry;
import TFC.API.ISmeltable;
import TFC.API.Metal;
import TFC.API.TFC_ItemHeat;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFCTabs;
import TFC.Core.Util.StringUtil;

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

	@Override
	public void addItemInformation(ItemStack is, EntityPlayer player, List arraylist)
	{
		if(TFC_ItemHeat.HasTemp(is))
		{
			String s = "";
			if(HeatRegistry.getInstance().isTemperatureDanger(is))
			{
				s += EnumChatFormatting.WHITE + StringUtil.localize("gui.ingot.danger") + " | ";
			}

			if(HeatRegistry.getInstance().isTemperatureWeldable(is))
			{
				s += EnumChatFormatting.WHITE + StringUtil.localize("gui.ingot.weldable") + " | ";
			}

			if(HeatRegistry.getInstance().isTemperatureWorkable(is))
			{
				s += EnumChatFormatting.WHITE + StringUtil.localize("gui.ingot.workable");
			}

			if(!s.equals(""))
				arraylist.add(s);
		}
	}

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		list.add(new ItemStack(this, 1, 100));
		list.add(new ItemStack(this, 1, 200));
		list.add(new ItemStack(this, 1, 300));
		list.add(new ItemStack(this, 1, 400));
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
