package TFC.Blocks.Terrain;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import TFC.TFCBlocks;
import TFC.API.Constant.Global;

public class BlockMM extends BlockStone
{
	public static boolean fallInstantly = false;

	public BlockMM(Material material)
	{
		super(material);
		this.dropBlock = TFCBlocks.StoneMMCobble;
		names = Global.STONE_MM;
		icons = new IIcon[names.length];
		looseStart = Global.STONE_MM_START;
		gemChance = 3;
	}
}
