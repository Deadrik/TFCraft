package com.bioxx.tfc.Food;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.IFood;
import com.bioxx.tfc.api.ISize;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Util.Helper;

public class ItemFoodTFC extends ItemTerra implements ISize, IFood
{
	/**
	 * Food can contain multiple NBT Tags including
	 * temperature:
	 * foodWeight:
	 * foodDecay
	 * decayTimer
	 */

	public int foodID;

	private EnumFoodGroup foodgroup;

	public float decayRate = 1.0f;

	public boolean isEdible = true;
	public boolean canBeUsedRaw = true;

	public ItemFoodTFC(int foodid, EnumFoodGroup fg)
	{
		super();
		this.setCreativeTab(TFCTabs.TFCFoods);
		this.setFolder("food/");
		foodID = foodid;
		foodgroup = fg;
		TFCItems.FoodList.add(this);
		this.setMaxDamage(100);
		this.hasSubtypes = false;
	}

	public ItemFoodTFC(int foodid, EnumFoodGroup fg, boolean edible)
	{
		this(foodid, fg);
		isEdible = edible;
	}
	public ItemFoodTFC(int foodid, EnumFoodGroup fg, boolean edible, boolean usable)
	{
		this(foodid, fg, edible);
		canBeUsedRaw = usable;
	}

	public ItemFoodTFC setDecayRate(float f)
	{
		this.decayRate = f;
		return this;
	}

	@Override
	public float getDecayRate()
	{
		return decayRate*(TFC_Time.getYearRatio(96));
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		list.add(createTag(new ItemStack(this, 1), Global.FOOD_MAX_WEIGHT));
	}

	public static void addHeatInformation(ItemStack is, List arraylist)
	{
		if (is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();

			if(TFC_ItemHeat.HasTemp(is))
			{
				float meltTemp = TFC_ItemHeat.IsCookable(is);
				if(meltTemp != -1)
					arraylist.add(TFC_ItemHeat.getHeatColorFood(TFC_ItemHeat.GetTemp(is), meltTemp));
			}
		}
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(is, arraylist);

		arraylist.add(getFoodGroupName(this.getFoodGroup()));

		addHeatInformation(is, arraylist);
		if (is.hasTagCompound())
		{
			addFoodInformation(is, player, arraylist);
		}
	}

	public static void addFoodInformation(ItemStack is, EntityPlayer player, List arraylist)
	{
		NBTTagCompound stackTagCompound = is.getTagCompound();
		if(stackTagCompound.hasKey("isSalted"))
			arraylist.add("\u2022" + StatCollector.translateToLocal("gui.food.salted"));
		if(stackTagCompound.hasKey("foodWeight"))
		{
			float ounces = Helper.roundNumber(stackTagCompound.getFloat("foodWeight"), 100);
			if(ounces > 0)
				arraylist.add(StatCollector.translateToLocal("gui.food.amount") + " " + ounces + " oz / " + Global.FOOD_MAX_WEIGHT + " oz");
			float decay = stackTagCompound.getFloat("foodDecay");
			if(decay > 0)
				arraylist.add(EnumChatFormatting.DARK_GRAY + StatCollector.translateToLocal("gui.food.decay") + " " + Helper.roundNumber(decay / ounces * 100, 10) + "%");
			if(TFCOptions.enableDebugMode)
				arraylist.add(EnumChatFormatting.DARK_GRAY + StatCollector.translateToLocal("gui.food.decay") + ": " + decay);
		}
	}

	public static String getFoodGroupName(EnumFoodGroup fg)
	{
		if(fg == EnumFoodGroup.Dairy)
			return (EnumChatFormatting.WHITE + StatCollector.translateToLocal("gui.food.dairy"));
		else if(fg == EnumFoodGroup.Fruit)
			return (EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("gui.food.fruit"));
		else if(fg == EnumFoodGroup.Vegetable)
			return (EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocal("gui.food.vegetable"));
		else if(fg == EnumFoodGroup.Protein)
			return (EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("gui.food.protein"));
		else if(fg == EnumFoodGroup.Grain)
			return (EnumChatFormatting.YELLOW + StatCollector.translateToLocal("gui.food.grain"));
		else
			return "N/A";
	}

	public static String getFoodGroupColor(EnumFoodGroup fg)
	{
		if(fg == EnumFoodGroup.Dairy)
			return EnumChatFormatting.WHITE.toString();
		else if(fg == EnumFoodGroup.Fruit)
			return EnumChatFormatting.DARK_PURPLE.toString();
		else if(fg == EnumFoodGroup.Vegetable)
			return EnumChatFormatting.DARK_GREEN.toString();
		else if(fg == EnumFoodGroup.Protein)
			return EnumChatFormatting.DARK_RED.toString();
		else if(fg == EnumFoodGroup.Grain)
			return EnumChatFormatting.YELLOW.toString();
		else
			return "N/A";
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		if (foodstats.needFood() && isEdible)
			player.setItemInUse(is, 32);

		return is;
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		if(!world.isRemote && isEdible)
			foodstats.eatFood(is);

		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		TFC_Core.setPlayerFoodStats(player, foodstats);
		return is;
	}

	public ItemStack onConsumedByEntity(ItemStack is, World world, EntityLivingBase entity)
	{
		if(entity instanceof IAnimal)
		{
			if(!world.isRemote)
			{
				ItemFoodTFC item = (ItemFoodTFC) is.getItem();
				float weight = item.getFoodWeight(is);
				float decay = Math.max(item.getFoodDecay(is), 0);
				float eatAmount = Math.min(weight - decay, 5f);
				if(FoodStatsTFC.reduceFood(is, eatAmount))
					is.stackSize = 0;
			}
			world.playSoundAtEntity(entity, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		}
		return is;
	}

	public boolean isHot(ItemStack is)
	{
		if(TFC_ItemHeat.GetTemp(is) > TFC_ItemHeat.IsCookable(is) *0.8)
			return true;
		else
			return false;
	}

	public static ItemStack createTag(ItemStack is, float weight)
	{
		NBTTagCompound nbt = is.getTagCompound();
		if(nbt == null)
			nbt = new NBTTagCompound();
		nbt.setFloat("foodWeight", weight);
		nbt.setFloat("foodDecay", -24);
		nbt.setInteger("decayTimer", (int)TFC_Time.getTotalHours()+1);

		is.setTagCompound(nbt);
		return is;
	}

	public static ItemStack createTag(ItemStack is, float weight, float decay)
	{
		is = createTag(is, weight);
		is.getTagCompound().setFloat("foodDecay", decay);
		return is;
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

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.eat;
	}

	@Override
	public int getDisplayDamage(ItemStack stack)
	{
		float decay = getFoodDecay(stack);
		float weight = getFoodWeight(stack);
		int percent = (int)((decay/weight)*100);
		percent = percent > 0 ? percent < 100 ? percent : 100 : 0;
		return percent;
	}

	@Override
	public boolean isDamaged(ItemStack stack)
	{
		return true;
	}

	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return 100;
	}

	@Override
	public EnumSize getSize(ItemStack is) 
	{
		return size;
	}
	@Override
	public EnumWeight getWeight(ItemStack is) 
	{
		if(is!= null && is.getTagCompound() != null && is.getTagCompound().hasKey("foodWeight"))
			return EnumWeight.HEAVY;
		return weight;
	}
	@Override
	public boolean canStack()
	{
		return false;
	}

	@Override
	public EnumFoodGroup getFoodGroup()
	{
		return foodgroup;
	}

	@Override
	public int getFoodID()
	{
		return foodID;
	}

	@Override
	public ItemStack onDecayed(ItemStack is, World world, int i, int j, int k)
	{
		return null;
	}

	@Override
	public boolean isEdible() {
		return isEdible;
	}

	@Override
	public boolean isUsable() {
		return canBeUsedRaw;
	}
}
