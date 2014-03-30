package TFC.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import TFC.Reference;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumWeight;

public class ItemSapling extends ItemTerraBlock
{
	public static IIcon[] Icons = new IIcon[Global.WOOD_ALL.length];
	
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
	public void registerIcons(IIconRegister registerer)
	{
		for(int i = 0; i < Global.WOOD_ALL.length; i++)
			Icons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/trees/" + Global.WOOD_ALL[i] + " Sapling");
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.MEDIUM;
	}
}
