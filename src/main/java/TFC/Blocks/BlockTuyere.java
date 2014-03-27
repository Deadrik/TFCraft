package TFC.Blocks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.Core.TFC_Textures;

public class BlockTuyere extends BlockTerra
{
	public BlockTuyere()
	{
		super();
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
	public IIcon getIcon(int side, int meta)
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
	public void registerBlockIcons(IIconRegister registerer)
	{
		TFC_Textures.SheetBismuth = registerer.registerIcon(Reference.ModID + ":" + "metal/Bismuth");
		TFC_Textures.SheetBismuthBronze = registerer.registerIcon(Reference.ModID + ":" + "metal/Bismuth Bronze");
		TFC_Textures.SheetBlackBronze = registerer.registerIcon(Reference.ModID + ":" + "metal/Black Bronze");
		TFC_Textures.SheetBlackSteel = registerer.registerIcon(Reference.ModID + ":" + "metal/Black Steel");
		TFC_Textures.SheetBlueSteel = registerer.registerIcon(Reference.ModID + ":" + "metal/Blue Steel");
		TFC_Textures.SheetBrass = registerer.registerIcon(Reference.ModID + ":" + "metal/Brass");
		TFC_Textures.SheetBronze = registerer.registerIcon(Reference.ModID + ":" + "metal/Bronze");
		TFC_Textures.SheetCopper = registerer.registerIcon(Reference.ModID + ":" + "metal/Copper");
		TFC_Textures.SheetGold = registerer.registerIcon(Reference.ModID + ":" + "metal/Gold");
		TFC_Textures.SheetLead = registerer.registerIcon(Reference.ModID + ":" + "metal/Lead");
		TFC_Textures.SheetNickel = registerer.registerIcon(Reference.ModID + ":" + "metal/Nickel");
		TFC_Textures.SheetPigIron = registerer.registerIcon(Reference.ModID + ":" + "metal/Pig Iron");
		TFC_Textures.SheetPlatinum = registerer.registerIcon(Reference.ModID + ":" + "metal/Platinum");
		TFC_Textures.SheetRedSteel = registerer.registerIcon(Reference.ModID + ":" + "metal/Red Steel");
		TFC_Textures.SheetRoseGold = registerer.registerIcon(Reference.ModID + ":" + "metal/Rose Gold");
		TFC_Textures.SheetSilver = registerer.registerIcon(Reference.ModID + ":" + "metal/Silver");
		TFC_Textures.SheetSteel = registerer.registerIcon(Reference.ModID + ":" + "metal/Steel");
		TFC_Textures.SheetSterlingSilver = registerer.registerIcon(Reference.ModID + ":" + "metal/Sterling Silver");
		TFC_Textures.SheetTin = registerer.registerIcon(Reference.ModID + ":" + "metal/Tin");
		TFC_Textures.SheetWroughtIron = registerer.registerIcon(Reference.ModID + ":" + "metal/Wrought Iron");
		TFC_Textures.SheetZinc = registerer.registerIcon(Reference.ModID + ":" + "metal/Zinc");
	}
}
