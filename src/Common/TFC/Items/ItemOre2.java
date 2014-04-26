package TFC.Items;

import net.minecraft.block.Block;
import TFC.Items.ItemBlocks.ItemTerraBlock;

public class ItemOre2 extends ItemTerraBlock
{
	public ItemOre2(Block par1) 
	{
		super(par1);
		setHasSubtypes(true);
		this.MetaNames = new String[]{"Kaolinite", "Gypsum", "Satinspar", "Selenite", "Graphite", "Kimberlite", 
		        "Petrified Wood", "Sulfur", "Jet", "Microcline", "Pitchblende", "Cinnabar", "Cryolite", "Saltpeter", "Serpentine", "Sylvite"};
		this.setFolder("ore/");
	}

}
