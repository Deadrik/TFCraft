package TFC.Items;

import net.minecraft.block.Block;
import TFC.Items.ItemBlocks.ItemTerraBlock;

public class ItemOre3 extends ItemTerraBlock
{
	public ItemOre3(Block par1) 
	{
		super(par1);
		setHasSubtypes(true);
		this.MetaNames = new String[]{"Borax", "Olivine", "Lapis Lazuli"};
		this.setFolder("ore/");
	}
}
