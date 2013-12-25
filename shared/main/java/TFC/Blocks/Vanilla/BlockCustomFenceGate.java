package TFC.Blocks.Vanilla;

import TFC.TFCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.util.Icon;

public class BlockCustomFenceGate extends BlockFenceGate {

	public BlockCustomFenceGate(int par1) {
		super(par1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Icon getIcon(int par1, int par2)
    {
        return TFCBlocks.Planks.getBlockTextureFromSide(par1);
    }
}
