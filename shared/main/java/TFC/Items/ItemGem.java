package TFC.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import TFC.Reference;

public class ItemGem extends ItemTerra
{
	private Icon[] icons = new Icon[5];
	public ItemGem(int i) 
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		this.MetaNames = new String[]{"Chipped", "Flawed", "Normal", "Flawless", "Exquisite"};
	}

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		list.add(new ItemStack(this,1, 0));
		list.add(new ItemStack(this,1, 1));
		list.add(new ItemStack(this,1, 2));
		list.add(new ItemStack(this,1, 3));
		list.add(new ItemStack(this,1, 4));
	}

	@Override
	public Icon getIconFromDamage(int i)
	{
		return icons[i];
	}
	
	@Override
	public void registerIcons(IconRegister registerer)
    {
		icons[0] = registerer.registerIcon(Reference.ModID + ":" + "gems/"+MetaNames[0] + " " + getUnlocalizedName().replace("item.", ""));
		icons[1] = registerer.registerIcon(Reference.ModID + ":" + "gems/"+MetaNames[1] + " " + getUnlocalizedName().replace("item.", ""));
		icons[2] = registerer.registerIcon(Reference.ModID + ":" + "gems/"+MetaNames[2] + " " + getUnlocalizedName().replace("item.", ""));
		icons[3] = registerer.registerIcon(Reference.ModID + ":" + "gems/"+MetaNames[3] + " " + getUnlocalizedName().replace("item.", ""));
		icons[4] = registerer.registerIcon(Reference.ModID + ":" + "gems/"+MetaNames[4] + " " + getUnlocalizedName().replace("item.", ""));
    }

}
