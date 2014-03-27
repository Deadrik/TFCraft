package TFC.Blocks.Terrain;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import TFC.API.Constant.Global;

public class BlockMMCobble extends BlockCobble
{
	public BlockMMCobble(Material material) 
	{
		super(material);
		fallInstantly = false;
		names = Global.STONE_MM;
		icons = new IIcon[names.length];
	}
}
