package TFC.Items.ItemBlocks;

import TFC.Items.ItemTerra;


public class ItemTuyere extends ItemTerra
{
	public int BlockMeta;
	public ItemTuyere(int id, int maxDam, int blockMeta) 
	{
		super(id);
		this.setMaxDamage(maxDam);
		setFolder("tools/");
		BlockMeta = blockMeta;
	}
}
