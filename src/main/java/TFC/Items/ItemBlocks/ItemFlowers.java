package TFC.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.util.IIcon;
import TFC.TFCBlocks;
import TFC.API.Constant.Global;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFlowers extends ItemTerraBlock
{

	public ItemFlowers(Block b)
	{
		super(b);
		MetaNames = Global.FLOWER_META_NAMES;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1)
	{
		return TFCBlocks.Flowers.getIcon(0, par1);
	}
}
