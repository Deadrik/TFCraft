package TFC.Food;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.API.IFood;
import TFC.API.ISize;
import TFC.API.TFCOptions;
import TFC.API.Constant.Global;
import TFC.API.Entities.IAnimal;
import TFC.API.Enums.EnumFoodGroup;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.API.Util.Helper;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Time;
import TFC.Core.Player.FoodStatsTFC;
import TFC.Core.Util.StringUtil;
import TFC.Items.ItemTerra;

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

	public ItemFoodTFC(int id, int foodid, EnumFoodGroup fg)
	{
		super(id);
		this.setCreativeTab(CreativeTabs.tabFood);
		this.setFolder("food/");
		foodID = foodid;
		foodgroup = fg;
		TFCItems.FoodList.add(this);
		this.setMaxDamage(100);
		this.hasSubtypes = false;
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
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		list.add(createTag(new ItemStack(this, 1), Global.FOOD_MAX_WEIGHT));
	}

	public static void addHeatInformation(ItemStack is, List arraylist)
	{
		if (is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();

			if(stackTagCompound.hasKey("temperature"))
			{
				float temp = stackTagCompound.getFloat("temperature");
				float meltTemp = TFC_ItemHeat.getMeltingPoint(is);

				if(meltTemp != -1)
					arraylist.add(TFC_ItemHeat.getHeatColorFood(temp, meltTemp));
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
			arraylist.add("\u2022" + StringUtil.localize("gui.food.salted"));
		if(stackTagCompound.hasKey("foodWeight"))
		{
			float ounces = Helper.roundNumber(stackTagCompound.getFloat("foodWeight"), 100);
			if(ounces > 0)
				arraylist.add(StringUtil.localize("gui.food.amount") + " " + ounces + " oz / " + Global.FOOD_MAX_WEIGHT + " oz");
			float decay = stackTagCompound.getFloat("foodDecay");
			if(decay > 0)
				arraylist.add(EnumChatFormatting.DARK_GRAY + StringUtil.localize("gui.food.decay") + " " + Helper.roundNumber(decay / ounces * 100, 10) + "%");
			if(TFCOptions.enableDebugMode)
				arraylist.add(EnumChatFormatting.DARK_GRAY + StringUtil.localize("gui.food.decay") + ": " + decay);
		}
	}

	public static String getFoodGroupName(EnumFoodGroup fg)
	{
		if(fg == EnumFoodGroup.Dairy)
			return (EnumChatFormatting.WHITE + StringUtil.localize("gui.food.dairy"));
		else if(fg == EnumFoodGroup.Fruit)
			return (EnumChatFormatting.DARK_PURPLE + StringUtil.localize("gui.food.fruit"));
		else if(fg == EnumFoodGroup.Vegetable)
			return (EnumChatFormatting.DARK_GREEN + StringUtil.localize("gui.food.vegetable"));
		else if(fg == EnumFoodGroup.Protein)
			return (EnumChatFormatting.DARK_RED + StringUtil.localize("gui.food.protein"));
		else if(fg == EnumFoodGroup.Grain)
			return (EnumChatFormatting.YELLOW + StringUtil.localize("gui.food.grain"));
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
		if (foodstats.needFood())
			player.setItemInUse(is, 32);

		return is;
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		if(!world.isRemote)
		{
			foodstats.eatFood(is);

		}
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		TFC_Core.setPlayerFoodStats(player, foodstats);
		return is;
	}

	public ItemStack onConsumedByEntity(ItemStack is, World world, EntityLivingBase entity)
	{
		if(entity instanceof IAnimal){
			if(!world.isRemote)
			{
				ItemFoodTFC item = (ItemFoodTFC) is.getItem();
				float weight = item.getFoodWeight(is);
				float decay = Math.max(item.getFoodDecay(is), 0);
				float eatAmount = Math.min(weight - decay, 5f);
				if(FoodStatsTFC.reduceFood(is, eatAmount)){
					is.stackSize = 0;
				}
			}
			world.playSoundAtEntity(entity, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		}
		return is;
	}

	public boolean isHot(ItemStack is)
	{
		if(TFC_ItemHeat.GetTemperature(is) > TFC_ItemHeat.getMeltingPoint(is) *0.8)
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

	public float getFoodWeight(ItemStack is)
	{
		if(is.hasTagCompound() && is.getTagCompound().hasKey("foodWeight"))
		{
			NBTTagCompound nbt = is.getTagCompound();
			return nbt.getFloat("foodWeight");
		}
		return 0f;
	}

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
	public EnumFoodGroup getFoodGroup() {
		return foodgroup;
	}

	@Override
	public int getFoodID() {
		return foodID;
	}

	@Override
	public ItemStack onDecayed(ItemStack is, World world, int i, int j, int k) 
	{
		return null;
	}
}
