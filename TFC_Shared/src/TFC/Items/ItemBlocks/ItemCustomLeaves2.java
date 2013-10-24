package TFC.Items.ItemBlocks;

import TFC.TFCBlocks;
import TFC.API.Constant.Global;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;

public class ItemCustomLeaves2 extends ItemTerraBlock
{
	public ItemCustomLeaves2(int i)
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		MetaNames = Global.WOOD_ALL;
	}
	
	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		int n = 0;
		if(itemstack!=null && 16 + itemstack.getItemDamage() < Global.WOOD_ALL.length){
			n=16;
		}
		
		String s = new StringBuilder().append(super.getItemDisplayName(itemstack)).append(".").append(Global.WOOD_ALL[itemstack.getItemDamage()+n]).toString();
		return s;
	}
	
	@Override
	public void registerIcons(IconRegister registerer)
    {

    }
}
