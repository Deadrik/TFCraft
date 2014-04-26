package TFC.API;

import net.minecraft.item.Item;

public class Metal
{
	public String Name;
	public Item MeltedItem;
	public Item Ingot;
	
	public Metal(String name)
	{
		Name = name;
	}

	public Metal(String name, Item m, Item i)
	{
		this(name);
		MeltedItem = m;
		Ingot = i;
	}
}
