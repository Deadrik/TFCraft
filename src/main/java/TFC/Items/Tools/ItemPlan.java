package TFC.Items.Tools;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import TFC.Reference;
import TFC.API.Enums.EnumSize;
import TFC.Core.TFCTabs;
import TFC.Items.ItemTerra;
public class ItemPlan extends ItemTerra
{
	public IIcon icons;
	public ItemPlan() 
	{
		super();
		setCreativeTab(TFCTabs.TFCTools);
		setHasSubtypes(true);
		setFolder("tools/");
		this.setSize(EnumSize.MEDIUM);
	}

	@Override
	public IIcon getIconFromDamage(int i)
	{
		return icons;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		//registerer.registerIcon(textureFolder+"Blueprint");
		icons = registerer.registerIcon(Reference.ModID + ":" + "tools/Blueprint");
	}
}
