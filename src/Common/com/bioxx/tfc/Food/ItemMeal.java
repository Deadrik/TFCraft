package com.bioxx.tfc.Food;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import net.minecraftforge.client.MinecraftForgeClient;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.Render.Item.FoodItemRenderer;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.FoodRegistry;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.Util.Helper;

public class ItemMeal extends ItemTerra implements IFood
{
	//private PotionEffect foodEffect;

	//private boolean alwaysEdible = false;

	public ItemMeal()
	{
		super();
		this.hasSubtypes = true;
		this.metaNames = new String[]{"Meal0","Meal1","Meal2","Meal3","Meal4","Meal5","Meal6","Meal7","Meal8","Meal9","Meal10",};
		this.metaIcons = new IIcon[11];
		this.setFolder("food/");
		this.stackable = false;
		this.setCreativeTab(null);
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		super.registerIcons(registerer);
		MinecraftForgeClient.registerItemRenderer(this, new FoodItemRenderer());
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		list.add(createTag(new ItemStack(this, 1)));
	}

	//Creates empty food to prevent NBT errors when food is loaded in NEI
	public static ItemStack createTag(ItemStack is)
	{
		if (!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());

		Food.setWeight(is, 0);
		Food.setDecay(is, 0);
		Food.setDecayTimer(is, (int) TFC_Time.getTotalHours() + 1);
		return is;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return this.getUnlocalizedName();
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);
		if(!TFC_Core.showShiftInformation())
		{
			arraylist.add("");
		}

		if (is.hasTagCompound())
		{

			ItemFoodTFC.addFoodHeatInformation(is, arraylist);
			addFoodInformation(is, player, arraylist);

			if(TFC_Core.showShiftInformation())
			{
				addFGInformation(is, arraylist);
			}
		}
		else
		{
			arraylist.add(TFC_Core.translate("gui.badnbt"));
			TerraFirmaCraft.LOG.error(TFC_Core.translate("error.error") + " " + is.getUnlocalizedName() + " " +
					TFC_Core.translate("error.NBT") + " " + TFC_Core.translate("error.Contact"));
		}
	}

	public void addFoodInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		float ounces = Helper.roundNumber(Food.getWeight(is), 100);
		if (ounces > 0)
			arraylist.add(TFC_Core.translate("gui.food.amount") + " " + ounces + " oz / " + Global.FOOD_MAX_WEIGHT + " oz");
		float decay = Food.getDecay(is);
		if (decay > 0)
			arraylist.add(EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.food.decay") + " " + Helper.roundNumber(decay / ounces * 100, 10) + "%");
		if (TFCOptions.enableDebugMode)
		{
			arraylist.add(EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.food.decay") + ": " + decay);
			arraylist.add(EnumChatFormatting.DARK_GRAY + "Decay Rate: " + this.getDecayRate(is));
		}

		if (TFC_Core.showCtrlInformation())
			ItemFoodTFC.addTasteInformation(is, player, arraylist);
		else
			arraylist.add(TFC_Core.translate("gui.showtaste"));
	}

	protected void addFGInformation(ItemStack is, List<String> arraylist)
	{
		int[] fg = Food.getFoodGroups(is);
		for (int i = 0; i < fg.length; i++)
		{
			if (fg[i] != -1)
				arraylist.add(localize(fg[i]));
		}
	}

	protected String localize(int id)
	{
		return ItemFoodTFC.getFoodGroupColor(FoodRegistry.getInstance().getFoodGroup(id)) +
				TFC_Core.translate(FoodRegistry.getInstance().getFood(id).getUnlocalizedName() + ".name");
	}

	protected float[] getNutritionalWeights(int[] foodGroups)
	{
		float[] nw = new float[foodGroups.length];
		float[] fw = getFoodWeights();
		float totalWeight = 0;
		for(int i  = 0; i < foodGroups.length; i++)
		{
			if(foodGroups[i] != -1)
			{
				totalWeight += fw[i];
			}
		}

		for(int i  = 0; i < foodGroups.length; i++)
		{
			nw[i] = fw[i]/totalWeight;
		}
		return nw;
	}

	protected float[] getFoodWeights()
	{
		return new float[]{10f,4f,4f,2f};
	}

	/**
	 * @param fs
	 * @param amount This should be the amount that is actually consumed aka (weight - decay)
	 * @return The exact amount that should enter the stomach
	 */
	protected float getEatAmount(FoodStatsTFC fs, float amount)
	{
		float eatAmount = Math.min(amount, 5);
		float stomachDiff = fs.stomachLevel+eatAmount-fs.getMaxStomach(fs.player);
		if(stomachDiff > 0)
			eatAmount-=stomachDiff;
		return eatAmount;
	}

	protected float getFillingBoost()
	{
		return 1.0f;
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);

		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		if(!world.isRemote)
		{
			//add the nutrition contents
			if (is.hasTagCompound())
			{
				float weight = Food.getWeight(is);
				float decay = Math.max(Food.getDecay(is), 0);
				float eatAmount = getEatAmount(foodstats, weight - decay);
				float tasteFactor = foodstats.getTasteFactor(is);

				int[] fg = Food.getFoodGroups(is);
				float[] nWeights = getNutritionalWeights(fg);
				for (int i = 0; i < fg.length; i++ )
				{
					if (fg[i] != -1)
						foodstats.addNutrition(FoodRegistry.getInstance().getFoodGroup(fg[i]), eatAmount * nWeights[i] * 2.5f);
				}

				//fill the stomach
				foodstats.stomachLevel += eatAmount * getFillingBoost();
				foodstats.setSatisfaction(foodstats.getSatisfaction() + ((eatAmount / 3f) * tasteFactor), fg);

				//Now remove the eaten amount from the itemstack.
				if (FoodStatsTFC.reduceFood(is, eatAmount))
				{
					is.stackSize = 0;
				}
			}
			else
			{
				String error = TFC_Core.translate("error.error") + " " + is.getUnlocalizedName() + " " +
						TFC_Core.translate("error.NBT") + " " + TFC_Core.translate("error.Contact");
				TerraFirmaCraft.LOG.error(error);
				TFC_Core.sendInfoMessage(player, new ChatComponentText(error));
			}
		}
		TFC_Core.setPlayerFoodStats(player, foodstats);
		return is;
	}

	/*private float getMaxWeight(ItemStack is)
	{
		int[] fg = Food.getFoodGroups(is);
		float w = 0;
		for(int i = 0; i < fg.length; i++)
		{
			if(fg[i] != -1)
				w += this.getFoodWeights()[i];
		}
		return w;
	}*/

	public static boolean isWarm(ItemStack is)
	{
		return TFC_ItemHeat.getTemp(is) > TFC_ItemHeat.isCookable(is) * 0.1;
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
	public float getDecayRate(ItemStack is)
	{
		return Food.getDecayRate(is);
	}

	@Override
	public ItemStack onDecayed(ItemStack is, World world, int i, int j, int k)
	{
		return null;
	}

	@Override
	public boolean isEdible(ItemStack is)
	{
		return false;
	}

	@Override
	public boolean isUsable(ItemStack is)
	{
		return false;
	}

	@Override
	public int getTasteSweet(ItemStack is) {
		int base = 0;
		if (is != null && is.hasTagCompound() && is.getTagCompound().hasKey("tasteSweet"))
			base = is.getTagCompound().getInteger("tasteSweet");
		return base + Food.getSweetMod(is);
	}

	@Override
	public int getTasteSour(ItemStack is) {
		int base = 0;
		if (is != null && is.hasTagCompound() && is.getTagCompound().hasKey("tasteSour"))
			base = is.getTagCompound().getInteger("tasteSour");
		return base + Food.getSourMod(is);
	}

	@Override
	public int getTasteSalty(ItemStack is) {
		int base = 0;
		if (is != null && is.hasTagCompound() && is.getTagCompound().hasKey("tasteSalty"))
			base = is.getTagCompound().getInteger("tasteSalty");
		return base + Food.getSaltyMod(is);
	}

	@Override
	public int getTasteBitter(ItemStack is) {
		int base = 0;
		if (is != null && is.hasTagCompound() && is.getTagCompound().hasKey("tasteBitter"))
			base = is.getTagCompound().getInteger("tasteBitter");
		return base + Food.getBitterMod(is);
	}

	@Override
	public int getTasteSavory(ItemStack is) {
		int base = 0;
		if (is != null && is.hasTagCompound() && is.getTagCompound().hasKey("tasteUmami"))
			base = is.getTagCompound().getInteger("tasteUmami");
		return base + Food.getSavoryMod(is);
	}

	@Override
	public float getFoodMaxWeight(ItemStack is) {
		return 20;
	}

	@Override
	public boolean renderDecay() {
		return true;
	}

	@Override
	public boolean renderWeight() {
		return true;
	}
}
