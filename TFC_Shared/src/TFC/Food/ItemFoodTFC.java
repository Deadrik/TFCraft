package TFC.Food;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.API.IFood;
import TFC.API.ISize;
import TFC.API.Enums.EnumFoodGroup;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
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
	 * foodDecayTimer
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
	}
	public ItemFoodTFC setDecayRate(float f)
	{
		this.decayRate = f;
		return this;
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
			NBTTagCompound stackTagCompound = is.getTagCompound();
			if(stackTagCompound.hasKey("foodWeight"))
			{
				float ounces = stackTagCompound.getFloat("foodWeight");
				if(ounces > 0)
					arraylist.add("Amount " + ounces+" oz / 80.0 oz");
				float decay = stackTagCompound.getFloat("foodDecay");
				if(decay > 0)
					arraylist.add(EnumChatFormatting.DARK_GRAY + "Decay " + decay/ounces*100+"%");
			}
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
	public void onUpdate(ItemStack is, World world, Entity entity, int i, boolean isSelected) 
	{
		super.onUpdate(is, world, entity, i, isSelected);
		if (!world.isRemote && is.hasTagCompound())
		{
			NBTTagCompound nbt = is.getTagCompound();
			float decay = nbt.getFloat("foodDecay");


			//if the tick timer is up then we cause decay.
			if(nbt.getInteger("decayTimer") + 23 < TFC_Time.getTotalHours())
			{
				if(decay < 0)
					decay++;
				else if(decay == 0)
				{
					decay = nbt.getFloat("foodWeight") * 0.005f;
					nbt.setFloat("foodDecay", decay);
				}
				else
				{
					decay = ((decay*1.5f)/24)*decayRate;
				}
			}

			if(nbt.getFloat("foodDecay") / nbt.getFloat("foodWeight") > 0.9f)
				is.stackSize = 0;
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		if(!world.isRemote)
		{
			if (foodstats.needFood())
				player.setItemInUse(is, this.getMaxItemUseDuration(is));
		} else if (foodstats.needFood())
			player.setItemInUse(is, this.getMaxItemUseDuration(is));

		return is;
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);

		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		if(!world.isRemote)
			foodstats.eatFood(is);
		TFC_Core.setPlayerFoodStats(player, foodstats);
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
		nbt.setInteger("foodTimer", (int)TFC_Time.getTotalHours());

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
		return (int)(getFoodDecay(stack)*10);
	}

	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return (int)(getFoodWeight(stack)*10);
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
}
