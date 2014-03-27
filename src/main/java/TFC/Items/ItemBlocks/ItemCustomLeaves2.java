package TFC.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import TFC.API.Constant.Global;
import TFC.Core.Util.StringUtil;

public class ItemCustomLeaves2 extends ItemTerraBlock
{
	public ItemCustomLeaves2(Block par1)
	{
		super(par1);
		setMaxDamage(0);
		setHasSubtypes(true);
		MetaNames = Global.WOOD_ALL;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack itemstack) 
	{
		int n = 0;
		if(itemstack!=null && 16 + itemstack.getItemDamage() < Global.WOOD_ALL.length){
			n=16;
		}
		
		String s = StringUtil.localize("tile.leaves." + Global.WOOD_ALL[itemstack.getItemDamage()+n]).toString();
		return s;
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
    {

    }
}
