package TFC.Items;


public class ItemOre1 extends ItemTerraBlock
{
	public ItemOre1(int i) 
	{
		super(i);
		setHasSubtypes(true);
		this.MetaNames = new String[]{"Native Copper", "Native Gold", "Native Platinum", "Hematite", "Native Silver", "Cassiterite", "Galena", "Bismuthinite", "Garnierite", 
				"Malachite", "Magnetite", "Limonite", "Sphalerite", "Tetrahedrite", 
				"Bituminous Coal", "Lignite"};
		this.setFolder("ore/");
	}
}
