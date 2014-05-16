package TFC.Items.ItemBlocks;

import TFC.API.Constant.Global;

public class ItemFence2 extends ItemFence
{
	public ItemFence2(int i) 
	{
		super(i);
		MetaNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, MetaNames, 0, Global.WOOD_ALL.length - 16);
	}
}