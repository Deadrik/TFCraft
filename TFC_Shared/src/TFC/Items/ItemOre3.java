package TFC.Items;


public class ItemOre3 extends ItemTerraBlock
{
	public ItemOre3(int i) 
	{
		super(i);
		setHasSubtypes(true);
		this.MetaNames = new String[]{"Borax", "Olivine", "Lapis Lazuli"};
		this.setFolder("ore/");
	}
}
