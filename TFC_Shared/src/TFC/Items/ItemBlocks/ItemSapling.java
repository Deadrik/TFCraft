package TFC.Items.ItemBlocks;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import TFC.Reference;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumWeight;
import TFC.Core.Util.StringUtil;

public class ItemSapling extends ItemTerraBlock
{
	public ItemSapling(int i)
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		MetaNames = Global.WOOD_ALL;
	}
	@Override
	public Icon getIconFromDamage(int par1)
    {
        return Icons[par1];
    }
	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		if(MetaNames != null)
			return StringUtil.localize("tile.sapling." + Global.WOOD_ALL[itemstack.getItemDamage()].replace(" ", ""));
		return super.getItemDisplayName(itemstack);
	}
	public static Icon[] Icons = new Icon[Global.WOOD_ALL.length];

	@Override
    public void registerIcons(IconRegister registerer)
    {
		for(int i = 0; i < Global.WOOD_ALL.length; i++)
		{
			Icons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/trees/" + Global.WOOD_ALL[i] + " Sapling");
		}
    }
	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.MEDIUM;
	}
}
