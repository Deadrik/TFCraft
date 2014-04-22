package TFC.Items.ItemBlocks;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import TFC.Reference;
import TFC.API.Constant.Global;

public class ItemPlankBlock2 extends ItemTerraBlock
{
	public ItemPlankBlock2(int i) 
	{
		super(i);
		MetaNames = new String[Global.WOOD_ALL.length-16];
		icons = new Icon[MetaNames.length];
		System.arraycopy(Global.WOOD_ALL, 16, MetaNames, 0, Global.WOOD_ALL.length-16);
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{
		for(int i = 0; i < MetaNames.length; i++)
		{
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/"+MetaNames[i]+" Plank");
		}
	}
}
