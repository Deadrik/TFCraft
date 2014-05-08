package TFC;

import net.minecraft.block.Block;
import TFC.Core.TFC_Core;

public class ServerOverrides 
{
	public static boolean isValidSurface(int id)
	{
		return TFC_Core.isFence(id) || (Block.blocksList[id] != null && Block.blocksList[id].getRenderType() == 11);
	}
}
