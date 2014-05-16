package TFC.Blocks.Vanilla;

import net.minecraft.block.material.Material;
import net.minecraft.util.Icon;
import TFC.API.Constant.Global;

public class BlockCustomFence2 extends BlockCustomFence
{
	public BlockCustomFence2(int par1, String par2Str, Material par3Material)
	{
		super(par1,"par2Str", par3Material);
		this.woodNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, this.woodNames, 0, Global.WOOD_ALL.length - 16);
		this.iconsPost = new Icon[woodNames.length];
		this.iconsPostTop = new Icon[woodNames.length];
	}
}
