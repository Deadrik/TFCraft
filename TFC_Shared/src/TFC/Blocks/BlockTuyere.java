package TFC.Blocks;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import TFC.TFCBlocks;
import TFC.Core.TFC_Textures;

public class BlockTuyere extends BlockTerra
{
	public BlockTuyere(int par1) 
	{
		super(par1);
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.tuyereRenderId;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public Icon getIcon(int side, int meta)
	{
		int m = meta & 7;

		switch(m)
		{
		case 0: return TFC_Textures.SheetCopper;
		case 1: return TFC_Textures.SheetBronze;
		case 2: return TFC_Textures.SheetWroughtIron;
		case 3: return TFC_Textures.SheetSteel;
		case 4: return TFC_Textures.SheetBlackSteel;
		case 5: return TFC_Textures.SheetRedSteel;
		case 6: return TFC_Textures.SheetBlueSteel;
		default: return TFC_Textures.SheetCopper;
		}

	}

	@Override
	public void registerIcons(IconRegister registerer)
	{

	}
}
