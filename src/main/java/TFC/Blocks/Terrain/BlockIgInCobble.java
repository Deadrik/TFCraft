package TFC.Blocks.Terrain;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import TFC.API.Constant.Global;

public class BlockIgInCobble extends BlockCobble
{
	public BlockIgInCobble(Material material)
	{
		super(material);
		fallInstantly = false;
		names = Global.STONE_IGIN;
		icons = new IIcon[names.length];
	}
}
