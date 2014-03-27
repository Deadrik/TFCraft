package TFC.Blocks.Terrain;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import TFC.TFCBlocks;
import TFC.API.Constant.Global;

public class BlockIgEx extends BlockStone
{
	public BlockIgEx(Material material)
	{
		super(material);
		this.dropBlock = TFCBlocks.StoneIgExCobble;
		names = Global.STONE_IGEX;
		icons = new IIcon[names.length];
		looseStart = Global.STONE_IGEX_START;
		gemChance = 0;
	}
}
