package TFC.Items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import TFC.Reference;
import TFC.API.Enums.EnumSize;
import TFC.Core.TFCTabs;

public class ItemUnfinishedArmor extends ItemTerra
{
	public ItemUnfinishedArmor(int id) 
	{
		super(id);
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		setCreativeTab(TFCTabs.TFCUnfinished);
		this.setFolder("armor/");
	}
	
	public ItemUnfinishedArmor(int id, String tex) 
	{
		super(id);
	}
	
	@Override
	public void registerIcons(IconRegister registerer)
    {
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + this.getUnlocalizedName().replace("item.", "").replace("Unfinished ", "").replace("Stage2 ", ""));
    }
	
	@Override
    public String getItemDisplayName(ItemStack itemstack)
    {
		String s = new StringBuilder().append(super.getItemDisplayName(itemstack)).toString();
        return s;
    }
	
	@Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
		if(itemstack.getItemDamage() == 1)
    		return getUnlocalizedName().concat("2");
    	return super.getUnlocalizedName(itemstack);
    }
	
	@Override
	public int getItemStackLimit()
    {
    	if(canStack())
    		return this.getSize().stackSize;
    	else
    		return 1;
    }

	@Override
	public EnumSize getSize() {
		return EnumSize.LARGE;
	}
	
	@Override
	public boolean canStack() 
	{
		return true;
	}

}
