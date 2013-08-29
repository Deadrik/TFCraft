package TFC.Items.Tools;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import TFC.Reference;
import TFC.API.TFCTabs;
import TFC.API.Enums.EnumSize;
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
		this.setSize(EnumSize.MEDIUM);
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
}
