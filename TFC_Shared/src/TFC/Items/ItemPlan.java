package TFC.Items;

import net.minecraft.client.renderer.texture.IconRegister;
import TFC.Core.TFCTabs;
import TFC.Enums.EnumSize;
public class ItemPlan extends ItemTerra
{

	public ItemPlan(int i) 
	{
		super(i);
		setCreativeTab(TFCTabs.TFCTools);
		setFolder("tools/");
	}
	
	@Override
    public void updateIcons(IconRegister registerer)
    {
		registerer.registerIcon(this.textureFolder+"Blueprint");
    }
	
	@Override
	public EnumSize getSize() {
		return EnumSize.MEDIUM;
	}
}
