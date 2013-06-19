package TFC.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import TFC.Reference;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.Util.StringUtil;

public class ItemMeltedMetal extends ItemTerra
{
	public ItemMeltedMetal(int i) 
	{
		super(i);
		setMaxDamage(100);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setFolder("ingots/");
	}	
	
	@Override
	public void registerIcons(IconRegister registerer)
    {
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder+this.getUnlocalizedName().replace("item.", "").replace("Weak ", "").replace("HC ", ""));
    }
	
	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.HEAVY;
	}
	
	@Override
	public EnumSize getSize() 
	{
		return EnumSize.SMALL;
	}
	@Override
	public boolean canStack() 
	{
		return false;
	}
	
	@Override
    public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
    {
		if(is.getItemDamage() != 0)
			arraylist.add(StringUtil.localize("gui.MeltedMetal.NotFull"));
		super.addInformation(is, player, arraylist, flag);
    }
}
