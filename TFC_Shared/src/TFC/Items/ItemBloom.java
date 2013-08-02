package TFC.Items;

import java.awt.image.BufferedImage;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;

public class ItemBloom extends ItemTerra
{
	EnumSize size = EnumSize.LARGE;
	BufferedImage bi;
	public ItemBloom(int i) 
	{
		super(i);
		setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setFolder("/");

	}
	
	@Override
	public void registerIcons(IconRegister registerer)
    {
		this.itemIcon = null;
    }


	@Override
	public EnumSize getSize() {
		return size;
	}

	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.HEAVY;
	}

	public ItemBloom setSize(EnumSize s)
	{
		size = s;
		return this;
	}
	
	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List arraylist)
    {
		arraylist.add(is.getItemDamage()+"");
    }

	public void addCreativeItems(java.util.ArrayList list)
	{
		list.add(new ItemStack(this));
	}

}
