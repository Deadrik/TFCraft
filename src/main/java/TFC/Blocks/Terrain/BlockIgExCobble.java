package TFC.Blocks.Terrain;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import TFC.API.Constant.Global;

public class BlockIgExCobble extends BlockCobble {
	public BlockIgExCobble(Material material)
	{
		super(material);
		fallInstantly = false;
		names = Global.STONE_IGEX;
		icons = new IIcon[names.length];
	}

	@Override
	public int tickRate(World world)
	{
		return 3;
	}
}
