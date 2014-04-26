package TFC.Items;

public class ItemTuyere extends ItemTerra
{
	public int BlockMeta;
	public ItemTuyere(int maxDam, int blockMeta)
	{
		super();
		this.hasSubtypes = false;
		this.setMaxDamage(maxDam);
		setFolder("tools/");
		BlockMeta = blockMeta;
	}
}
