package TFC.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import TFC.Reference;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumSize;

public class ItemPlankBlock2 extends ItemTerraBlock
{
	public ItemPlankBlock2(Block par1) 
	{
		super(par1);
		MetaNames = new String[Global.WOOD_ALL.length-16];
		icons = new IIcon[MetaNames.length];
		System.arraycopy(Global.WOOD_ALL, 16, MetaNames, 0, Global.WOOD_ALL.length-16);
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		for(int i = 0; i < MetaNames.length; i++)
		{
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/"+MetaNames[i]+" Plank");
		}
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.MEDIUM;
	}
}
