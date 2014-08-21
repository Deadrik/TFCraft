package com.bioxx.tfc.Food;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.FoodRegistry;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.Util.Helper;

public class ItemMeal extends ItemTerra implements IFood
{
	PotionEffect foodEffect;
	private boolean alwaysEdible = false;
	private String[] tasteArray = {"item.meal.terrible", "item.meal.poor", "item.meal.decent", "item.meal.good","item.meal.great","item.meal.fantastic"};

	public ItemMeal()
	{
		super();
		this.hasSubtypes = true;
		this.MetaNames = new String[]{"Meal0","Meal1","Meal2","Meal3","Meal4","Meal5","Meal6","Meal7","Meal8","Meal9","Meal10",};
		this.MetaIcons = new IIcon[11];
		this.setFolder("food/");
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		// Removes meals from creative tab because without NBT data, they are useless
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return this.getUnlocalizedName();
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemFoodTFC.addHeatInformation(is, arraylist);

		if (is.hasTagCompound())
		{
			NBTTagCompound nbt = is.getTagCompound();

			addFGInformation(is, arraylist);

			/*if(nbt.hasKey("satisfaction"))
			{
				float _sat = Helper.roundNumber(nbt.getFloat("satisfaction"),100);
				if(!isWarm(is))
					_sat *= 0.25f;
				int satIndex = Math.min(1+(int)(5 *_sat), 5);
				arraylist.add("Taste: " + StatCollector.translateToLocal(tasteArray[satIndex]) + EnumChatFormatting.DARK_GRAY + " (" + (_sat * 100) + "%)");
			}
			else
			{
				arraylist.add("Taste: " + StatCollector.translateToLocal(tasteArray[0]) + EnumChatFormatting.DARK_GRAY + " (0.0%)");
			}*/

			if(nbt.hasKey("foodWeight"))
			{
				float ounces = nbt.getFloat("foodWeight");
				if(ounces > 0)
					arraylist.add("Amount " + Helper.roundNumber(ounces, 10) + "oz");
				float decay = nbt.getFloat("foodDecay");
				if(decay > 0)
					arraylist.add(EnumChatFormatting.DARK_GRAY + "Decay " + Helper.roundNumber(decay / ounces * 100, 10) + "%");
			}
			else
			{
				arraylist.add(StatCollector.translateToLocal("gui.badnbt"));
			}

			if (TFC_Core.showCtrlInformation()) 
				ItemFoodTFC.addTasteInformation(is, player, arraylist);
			else
				arraylist.add(StatCollector.translateToLocal("gui.showtaste"));
		}
		else
		{
			arraylist.add(StatCollector.translateToLocal("gui.badnbt"));
		}
	}

	protected void addFGInformation(ItemStack is, List arraylist)
	{
		if (is.hasTagCompound())
		{
			NBTTagCompound nbt = is.getTagCompound();
			if(nbt.hasKey("FG"))
			{
				int[] fg = nbt.getIntArray("FG");
				for(int i = 0; i < fg.length; i++)
				{
					if(fg[i] != -1)
						arraylist.add(localize(fg[i]));
				}
			}
		}
	}

	private String getExpertString(int taste)
	{
		if(taste < 20) return "0-20";
		else if(taste < 40) return "20-40";
		else if(taste < 60) return "40-60";
		else if(taste < 80) return "60-80";
		else if(taste < 100) return "80-100";
		else return "100+";
	}

	@Override
	public int getDisplayDamage(ItemStack stack)
	{
		float decay = getFoodDecay(stack);
		float weight = getFoodWeight(stack);
		int percent = (int)((decay / weight) * 100);
		percent = percent > 0 ? percent < 100 ? percent : 100 : 0;
		return percent;
	}

	@Override
	public boolean isDamaged(ItemStack stack)
	{
		return false;
	}

	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return 100;
	}

	protected String localize(int id)
	{
		return ItemFoodTFC.getFoodGroupColor(FoodRegistry.getInstance().getFoodGroup(id)) + 
				StatCollector.translateToLocal(FoodRegistry.getInstance().getFood(id).getUnlocalizedName() + ".name");
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);

		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		if(!world.isRemote)
			foodstats.eatFood(is);
		TFC_Core.setPlayerFoodStats(player, foodstats);
		return is;
	}

	public static boolean isWarm(ItemStack is)
	{
		if(TFC_ItemHeat.GetTemp(is) > TFC_ItemHeat.IsCookable(is) * 0.1)
			return true;
		else
			return false;
	}

	@Override
	public float getFoodWeight(ItemStack is)
	{
		if(is.hasTagCompound() && is.getTagCompound().hasKey("foodWeight"))
		{
			NBTTagCompound nbt = is.getTagCompound();
			return nbt.getFloat("foodWeight");
		}
		return 0f;
	}

	@Override
	public float getFoodDecay(ItemStack is)
	{
		if(is.hasTagCompound() && is.getTagCompound().hasKey("foodDecay"))
		{
			NBTTagCompound nbt = is.getTagCompound();
			return nbt.getFloat("foodDecay");
		}
		return 0f;
	}

	public float getSatisfaction(ItemStack is) 
	{
		if(is.hasTagCompound() && is.getTagCompound().hasKey("satisfaction"))
		{
			NBTTagCompound nbt = is.getTagCompound();
			return nbt.getFloat("satisfaction");
		}
		return 0f;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 32;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.eat;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);

		//The player needs to be able to fit the food into his stomach
		if(foodstats.needFood())
			player.setItemInUse(is, this.getMaxItemUseDuration(is));

		return is;
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.SMALL;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.MEDIUM;
	}

	@Override
	public EnumFoodGroup getFoodGroup()
	{
		return null;
	}

	@Override
	public int getFoodID()
	{
		return 0;
	}

	@Override
	public float getDecayRate()
	{
		return 0;
	}

	@Override
	public ItemStack onDecayed(ItemStack is, World world, int i, int j, int k)
	{
		return null;
	}

	@Override
	public boolean isEdible()
	{
		return false;
	}

	@Override
	public boolean isUsable()
	{
		return false;
	}

	@Override
	public int getTasteSweet(ItemStack is) {
		if(is != null && is.getTagCompound().hasKey("tasteSweet"))
			return is.getTagCompound().getInteger("tasteSweet");
		return 0;
	}

	@Override
	public int getTasteSour(ItemStack is) {
		if(is != null && is.getTagCompound().hasKey("tasteSour"))
			return is.getTagCompound().getInteger("tasteSour");
		return 0;
	}

	@Override
	public int getTasteSalty(ItemStack is) {
		if(is != null && is.getTagCompound().hasKey("tasteSalty"))
			return is.getTagCompound().getInteger("tasteSalty");
		return 0;
	}

	@Override
	public int getTasteBitter(ItemStack is) {
		if(is != null && is.getTagCompound().hasKey("tasteBitter"))
			return is.getTagCompound().getInteger("tasteBitter");
		return 0;
	}

	@Override
	public int getTasteSavory(ItemStack is) {
		if(is != null && is.getTagCompound().hasKey("tasteUmami"))
			return is.getTagCompound().getInteger("tasteUmami");
		return 0;
	}

	@Override
	public float getFoodMaxWeight(ItemStack is) {
		return 20;
	}

	@Override
	public boolean renderDecay() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean renderWeight() {
		// TODO Auto-generated method stub
		return false;
	}
}
