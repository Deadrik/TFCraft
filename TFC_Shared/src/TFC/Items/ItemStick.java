package TFC.Items;

import net.minecraft.creativetab.CreativeTabs;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;

public class ItemStick extends ItemTerra
{
    public ItemStick(int i)
    {
        super(i);
        setMaxDamage(0);
        setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
    
    @Override
	public int getMetadata(int i)
    {
        return i;
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
}
