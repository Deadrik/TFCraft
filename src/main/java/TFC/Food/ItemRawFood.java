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

	public ItemRawFood(int foodid, EnumFoodGroup fg)
	{
		super(foodid, fg);
	}
	public ItemRawFood(int foodid, EnumFoodGroup fg, boolean edible)
	{
		this(foodid, fg);
		edibleRaw = edible;
	}
	public ItemRawFood(int foodid, EnumFoodGroup fg, boolean edible, boolean usable)
	{
		this(foodid, fg, edible);
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
