package TFC.Food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.API.ISize;
import TFC.API.Enums.EnumFoodGroup;

public class ItemRawFood extends ItemFoodTFC implements ISize
{
	public boolean edibleRaw = false;
	public boolean canBeUsedRaw = true;

	public ItemRawFood(int id, int foodid, EnumFoodGroup fg)
	{
		super(id, foodid, fg);
	}
	public ItemRawFood(int id, int foodid, EnumFoodGroup fg, boolean edible)
	{
		this(id, foodid, fg);
		edibleRaw = edible;
	}
	public ItemRawFood(int id, int foodid, EnumFoodGroup fg, boolean edible, boolean usable)
	{
		this(id, foodid, fg, edible);
		canBeUsedRaw = usable;
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		if(!world.isRemote && edibleRaw)
			return super.onEaten(is, world, player);
		return is;
	}
}
