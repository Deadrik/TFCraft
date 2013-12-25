package TFC.Items;

public class ItemTuyere extends ItemTerra
{
	public int BlockMeta;
	public ItemTuyere(int id, int maxDam, int blockMeta) 
	{
		super(id);
		this.hasSubtypes = false;
		this.setMaxDamage(maxDam);
		setFolder("tools/");
		BlockMeta = blockMeta;
	}
}
