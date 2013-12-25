package TFC.Items.ItemBlocks;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import TFC.Reference;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumWeight;
import TFC.Core.Util.StringUtil;

public class ItemSapling2 extends ItemTerraBlock
{
	public ItemSapling2(int i)
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		MetaNames = Global.WOOD_ALL;
	}

	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		if(itemstack.getItemDamage()+16 < Global.WOOD_ALL.length){
		String s = StringUtil.localize("tile.sapling." + Global.WOOD_ALL[itemstack.getItemDamage()+16]).toString();
		return s;
		}
		return "Unknown";
	}
	
	@Override
	public int getMetadata(int i)
	{
		return i;
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{

	}
	@Override
	public EnumWeight getWeight(ItemStack is) {
		// TODO Auto-generated method stub
		return EnumWeight.MEDIUM;
	}
}
