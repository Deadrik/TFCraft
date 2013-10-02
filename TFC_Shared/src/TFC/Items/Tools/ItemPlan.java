package TFC.Items.Tools;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import TFC.Reference;
import TFC.API.Enums.EnumSize;
import TFC.Core.TFCTabs;
import TFC.Items.ItemTerra;
public class ItemPlan extends ItemTerra
{
	public Icon icons;
	public ItemPlan(int i) 
	{
		super(i);
		setCreativeTab(TFCTabs.TFCTools);
		setHasSubtypes(true);
		setFolder("tools/");
	}
	
	@Override
    public Icon getIconFromDamage(int i)
    {
        return icons;
    }
	
	@Override
    public void registerIcons(IconRegister registerer)
    {
		//registerer.registerIcon(textureFolder+"Blueprint");
		icons = registerer.registerIcon(Reference.ModID + ":" + "tools/Blueprint");
    }
	
	@Override
	public EnumSize getSize() {
		return EnumSize.MEDIUM;
	}
}
