package TFC.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import TFC.Reference;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;

public class ItemOreSmall extends ItemOre
{
	public ItemOreSmall(int i)
	{
		super(i);
	}

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < 14; i++) {
			list.add(new ItemStack(this,1,i));}
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{
		icons = new Icon[14];
		for(int i = 0; i < 14; i++) {
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + textureFolder+MetaNames[i]+" Small Ore");
		}
	}

	@Override
	public EnumSize getSize() 
	{
		return EnumSize.TINY;
	}

	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.HEAVY;
	}

	@Override
	public short GetMetalReturnAmount(ItemStack is) 
	{
		int dam = is.getItemDamage();
		switch(dam)
		{
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13: return 10;
		}
		return 0;
	}
}
