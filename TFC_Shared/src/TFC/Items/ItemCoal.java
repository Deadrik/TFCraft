package TFC.Items;

import java.util.List;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;

public class ItemCoal extends ItemTerra {

	public ItemCoal(int id) {
		super(id, "/gui/items.png");
		this.setHasSubtypes(true);
        this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}
	
	@Override
	public EnumSize getSize() 
	{
		return EnumSize.TINY;
	}
	
	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.LIGHT;
	}
	
	public String getItemNameIS(ItemStack par1ItemStack)
    {
        return par1ItemStack.getItemDamage() == 1 ? "item.charcoal" : "item.coal";
    }
	
	@SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
    }

}
