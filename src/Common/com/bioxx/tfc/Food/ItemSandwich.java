package com.bioxx.tfc.Food;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.api.Food;

public class ItemSandwich extends ItemMeal
{

	public ItemSandwich()
	{
		super();
		this.hasSubtypes = true;
		this.metaNames = new String[]{"Sandwich Wheat","Sandwich Oat","Sandwich Barley","Sandwich Rye","Sandwich Corn","Sandwich Rice"};
		this.metaIcons = new IIcon[6];
		this.setFolder("food/");
	}

	@Override
	protected void addFGInformation(ItemStack is, List<String> arraylist)
	{
		int[] fg = Food.getFoodGroups(is);
		for (int i = 0; i < fg.length; i++)
		{
			if (i == 5 && fg[5] == fg[0])
				return;
			if (fg[i] != -1)
				arraylist.add(localize(fg[i]));
		}
	}

	@Override
	protected float getEatAmount(FoodStatsTFC fs, float amount)
	{
		float eatAmount = Math.min(amount, 10);
		float stomachDiff = fs.stomachLevel+eatAmount-fs.getMaxStomach(fs.player);
		if(stomachDiff > 0)
			eatAmount-=stomachDiff;
		return eatAmount;
	}

	@Override
	protected float getFillingBoost()
	{
		return 1.25f;
	}

	@Override
	protected float[] getFoodWeights()
	{
		return new float[]{2f,3f,2f,2f,1f};
	}

	@Override
	public float getFoodMaxWeight(ItemStack is) {
		return 10;
	}

	@Override
	public boolean renderDecay() {
		return true;
	}

	@Override
	public boolean renderWeight() {
		return false;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		list.add(createTag(new ItemStack(this, 1)));
	}

	//Creates empty food to prevent NBT errors when food is loaded in NEI
	public static ItemStack createTag(ItemStack is)
	{
		ItemMeal.createTag(is);
		int[] foodGroups = new int[] { -1, -1, -1, -1 };
		Food.setFoodGroups(is, foodGroups);
		return is;
	}
}
