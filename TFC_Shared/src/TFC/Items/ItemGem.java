package TFC.Items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemGem extends ItemTerra
{
	public static String[] gemNames = {"Chipped", "Flawed", "Normal", "Flawless", "Exquisite"};
	private Icon[] icons = new Icon[5];
	public ItemGem(int i) 
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	public void addCreativeItems(java.util.ArrayList list)
	{
		list.add(new ItemStack(this,0, 0));
		list.add(new ItemStack(this,0, 1));
		list.add(new ItemStack(this,0, 2));
		list.add(new ItemStack(this,0, 3));
		list.add(new ItemStack(this,0, 4));
	}

	@Override
	public Icon getIconFromDamage(int i)
	{
		return icons[i];
	}

	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		String s = new StringBuilder().append(super.getItemDisplayName(itemstack)).append(".").append(gemNames[itemstack.getItemDamage()]).toString();
		return s;
	}
	
	@Override
	public void updateIcons(IconRegister registerer)
    {
		icons[0] = registerer.registerIcon("gems/"+gemNames[0] + " " + getUnlocalizedName().replace("item.", ""));
		icons[1] = registerer.registerIcon("gems/"+gemNames[1] + " " + getUnlocalizedName().replace("item.", ""));
		icons[2] = registerer.registerIcon("gems/"+gemNames[2] + " " + getUnlocalizedName().replace("item.", ""));
		icons[3] = registerer.registerIcon("gems/"+gemNames[3] + " " + getUnlocalizedName().replace("item.", ""));
		icons[4] = registerer.registerIcon("gems/"+gemNames[4] + " " + getUnlocalizedName().replace("item.", ""));
    }

}
