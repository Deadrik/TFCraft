package TFC.Items;

import net.minecraft.client.renderer.texture.IconRegister;
import TFC.API.IQuiverAmmo;
import TFC.API.Enums.EnumAmmo;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;

public class ItemArrow extends ItemTerra implements IQuiverAmmo
{
	public ItemArrow(int id) 
	{
		super(id);
		this.setSize(EnumSize.LARGE);
		this.setWeight(EnumWeight.LIGHT);
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon("minecraft:arrow");
	}

	@Override
	public EnumAmmo getAmmoType() 
	{
		return EnumAmmo.ARROW;
	}
}
