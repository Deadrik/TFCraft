package TFC.Items;

import net.minecraft.client.renderer.texture.IIconRegister;
import TFC.API.IQuiverAmmo;
import TFC.API.Enums.EnumAmmo;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;

public class ItemArrow extends ItemTerra implements IQuiverAmmo
{
	public ItemArrow()
	{
		super();
		this.setSize(EnumSize.LARGE);
		this.setWeight(EnumWeight.LIGHT);
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon("minecraft:arrow");
	}

	@Override
	public EnumAmmo getAmmoType() 
	{
		return EnumAmmo.ARROW;
	}
}
