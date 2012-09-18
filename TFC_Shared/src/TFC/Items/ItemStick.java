package TFC.Items;

import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import net.minecraft.src.*;

public class ItemStick extends ItemTerra
{
    String[] Names = {"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
            "Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};

    public ItemStick(int i)
    {
        super(i);
        setMaxDamage(0);
        setHasSubtypes(true);
        this.setTabToDisplayOn(CreativeTabs.tabMaterials);
    }

    public int getIconFromDamage(int i)
    {
        return 16;
    }
    public int getMetadata(int i)
    {
        return i;
    }

    @Override
    public String getTextureFile()
    {
        return "/bioxx/terrasprites.png";
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
