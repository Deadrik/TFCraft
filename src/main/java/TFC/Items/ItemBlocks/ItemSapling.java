package TFC.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import TFC.Reference;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumWeight;
import TFC.Core.Util.StringUtil;

public class ItemSapling extends ItemTerraBlock
{
	public ItemSapling(Block par1)
	{
		super(par1);
		setMaxDamage(0);
		setHasSubtypes(true);
		MetaNames = Global.WOOD_ALL;
	}
	@Override
	public IIcon getIconFromDamage(int par1)
	{
		return Icons[par1];
	}
	@Override
	public String getItemStackDisplayName(ItemStack itemstack) 
	{
		if(MetaNames != null) {
			return StringUtil.localize("tile.sapling." + Global.WOOD_ALL[itemstack.getItemDamage()].replace(" ", ""));
		}
		return super.getItemStackDisplayName(itemstack);
	}
	public static IIcon[] Icons = new IIcon[Global.WOOD_ALL.length];

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		for(int i = 0; i < Global.WOOD_ALL.length; i++)
		{
			Icons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/trees/" + Global.WOOD_ALL[i] + " Sapling");
		}
	}
	@Override
	public EnumWeight getWeight(ItemStack is) {
		// TODO Auto-generated method stub
		return EnumWeight.MEDIUM;
	}
}
