package TFC.Items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import TFC.Reference;
import TFC.API.Enums.EnumSize;
import TFC.Core.TFCTabs;

public class ItemUnfinishedArmor extends ItemTerra
{
	public ItemUnfinishedArmor() 
	{
		super();
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		setCreativeTab(TFCTabs.TFCUnfinished);
		this.setFolder("armor/");
		this.setSize(EnumSize.LARGE);
	}

	public ItemUnfinishedArmor(String tex)
	{
		super();
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + this.getUnlocalizedName().replace("item.", "").replace("Unfinished ", "").replace("Stage2 ", ""));
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack)
	{
		String s = new StringBuilder().append(super.getItemStackDisplayName(itemstack)).toString();
		return s;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		if(itemstack.getItemDamage() == 1) {
			return getUnlocalizedName().concat("2");
		}
		return super.getUnlocalizedName(itemstack);
	}

	@Override
	public int getItemStackLimit()
	{
		if(canStack()) {
			return this.getSize(null).stackSize;
		} else {
			return 1;
		}
	}

	@Override
	public boolean canStack() 
	{
		return true;
	}

}
