package TFC.Items;


public class ItemOre2 extends ItemTerraBlock
{
	public ItemOre2(int i) 
	{
		super(i);
		setHasSubtypes(true);
		this.MetaNames = new String[]{"Kaolinite", "Gypsum", "Satinspar", "Selenite", "Graphite", "Kimberlite", 
		        "Petrified Wood", "Sulfur", "Jet", "Microcline", "Pitchblende", "Cinnabar", "Cryolite", "Saltpeter", "Serpentine", "Sylvite"};
		this.setFolder("ore/");
	}

}
