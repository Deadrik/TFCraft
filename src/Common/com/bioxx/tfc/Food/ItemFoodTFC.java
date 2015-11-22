package com.bioxx.tfc.Food;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Core.Player.SkillStats.SkillRank;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.Render.Item.FoodItemRenderer;
import com.bioxx.tfc.api.*;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ICookableFood;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.Interfaces.IMergeableFood;
import com.bioxx.tfc.api.Interfaces.ISize;
import com.bioxx.tfc.api.Util.Helper;

public class ItemFoodTFC extends ItemTerra implements ISize, ICookableFood, IMergeableFood
{
	/**
	 * Food can contain multiple NBT Tags including
	 * temperature:
	 * foodWeight:
	 * foodDecay
	 * decayTimer
	 */

	private EnumFoodGroup foodgroup;

	public int foodID;
	public float decayRate = 1.0f;
	public boolean edible = true;
	public boolean canBeUsedRaw = true;
	protected int tasteSweet;
	protected int tasteSour;
	protected int tasteSalty;
	protected int tasteBitter;
	protected int tasteUmami;
	protected boolean canBeSmoked;
	protected float smokeAbsorb;

	public IIcon cookedIcon;
	protected boolean hasCookedIcon;

	public ItemFoodTFC(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um)
	{
		super();
		this.setCreativeTab(TFCTabs.TFC_FOODS);
		this.setFolder("food/");
		foodgroup = fg;
		TFCItems.foodList.add(this);
		this.setMaxDamage(100);
		this.hasSubtypes = false;
		smokeAbsorb = 0.5f;
		tasteSweet = sw;
		tasteSour = so;
		tasteSalty = sa;
		tasteBitter = bi;
		tasteUmami = um;
		foodID = FoodRegistry.getInstance().registerFood(fg, this);
	}

	public ItemFoodTFC(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, boolean edible)
	{
		this(fg, sw, so, sa, bi, um);
		this.edible = edible;
	}

	public ItemFoodTFC(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, boolean edible, boolean usable)
	{
		this(fg, sw, so, sa, bi, um, edible);
		canBeUsedRaw = usable;
	}

	public ItemFoodTFC setDecayRate(float f)
	{
		this.decayRate = f;
		return this;
	}

	public ItemFoodTFC setCanSmoke()
	{
		this.canBeSmoked = true;
		return this;
	}

	public ItemFoodTFC setHasCookedIcon()
	{
		this.hasCookedIcon = true;
		return this;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		super.registerIcons(registerer);
		if(hasCookedIcon)
		{
			String name = this.getUnlocalizedName();
			this.cookedIcon = registerer.registerIcon(Reference.MOD_ID + ":" + this.textureFolder + name.replace("item.", "")+" Cooked");
		}
		MinecraftForgeClient.registerItemRenderer(this, new FoodItemRenderer());
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		if(Food.isCooked(stack) && cookedIcon != null)
			return cookedIcon;
		return this.itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	/*
	 * requiresMultipleRenderPasses is true so the dropped item uses the
	 * correct icon but we don't actually need the extra render passes.
	 */
	@Override
	public int getRenderPasses(int metadata)
	{
		return 1;
	}

	/*@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack is, int par2)
	{
		float cookedLevel = Food.getCooked(is);
		int r = 255 - (int)(160 * (Math.max(cookedLevel-600, 0) / 600f)); 
		int b = 255 - (int)(160 * (Math.max(cookedLevel-600, 0) / 600f));
		int g = 255 - (int)(160 * (Math.max(cookedLevel-600, 0) / 600f));
		int rbg = (r << 16) + (b << 8) + g;
		return rbg;
	}*/

	@Override
	public float getDecayRate(ItemStack is)
	{
		float mult = 1.0f;
		if(Food.isCooked(is))
		{
			mult *= 0.75f;
			if(Food.isPickled(is) || Food.isSalted(is))
				mult *= 0.75f;
			if(Food.isSmoked(is))
				mult *= 1f - (0.25f * this.getSmokeAbsorbMultiplier());
		}
		else
		{
			if(Food.isPickled(is) || Food.isSalted(is))
				mult *= 0.5f;
			if(Food.isSmoked(is))
				mult *= 1f - (0.25f * this.getSmokeAbsorbMultiplier());
			if(Food.isDried(is))
				mult *= 0.25f;
		}
		return decayRate * (TFC_Time.getYearRatio(96)) * mult;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		list.add(ItemFoodTFC.createTag(new ItemStack(this, 1), Global.FOOD_MAX_WEIGHT));
	}

	@Override
	public String getItemStackDisplayName(ItemStack is)
	{
		StringBuilder name = new StringBuilder();
		if (is.hasTagCompound()) // Circular reference avoidance
		{
			if(Food.isPickled(is))
				name.append(TFC_Core.translate("word.pickled")).append(' ');
			else if(Food.isBrined(is) && !Food.isDried(is))
				name.append(TFC_Core.translate("word.brined")).append(' ');

			if(Food.isSalted(is))
				name.append(TFC_Core.translate("word.salted")).append(' ');
			if(Food.isCooked(is))
				name.append(TFC_Core.translate("word.cooked")).append(' ');
			else if(Food.isSmoked(is))
				name.append(TFC_Core.translate("word.smoked")).append(' ');

			if(Food.isDried(is) && !Food.isCooked(is))
				name.append(TFC_Core.translate("word.dried")).append(' ');
			if(Food.isInfused(is))
				name.append(TFC_Core.translate(Food.getInfusion(is) + ".name")).append(' ');
		}

		return name.append(TFC_Core.translate(this.getUnlocalizedName(is) + ".name")).append(getCookedLevelString(is)).toString();
	}

	protected String getCookedLevelString(ItemStack is)
	{
		String s = "";
		if(Food.isCooked(is))
		{
			s+= " (";
			int cookedLevel = ((int)Food.getCooked(is)-600)/120;
			switch(cookedLevel)
			{
			case 0: s+=TFC_Core.translate("food.cooked.0");break;
			case 1: s+=TFC_Core.translate("food.cooked.1");break;
			case 2: s+=TFC_Core.translate("food.cooked.2");break;
			case 3: s+=TFC_Core.translate("food.cooked.3");break;
			default: s+=TFC_Core.translate("food.cooked.4");break;
			}
			s+= ")";
		}
		return s;
	}

	public static void addFoodHeatInformation(ItemStack is, List<String> arraylist)
	{
		if (TFC_ItemHeat.hasTemp(is))
		{
			float meltTemp = TFC_ItemHeat.isCookable(is);
			if (meltTemp != -1)
				arraylist.add(TFC_ItemHeat.getHeatColorFood(TFC_ItemHeat.getTemp(is), meltTemp));
		}
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);
		arraylist.add(ItemFoodTFC.getFoodGroupName(this.getFoodGroup()));

		if (is.hasTagCompound())
		{
			ItemFoodTFC.addFoodHeatInformation(is, arraylist);
			addFoodInformation(is, player, arraylist);
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
		if (ounces > 0 && ounces <= Global.FOOD_MAX_WEIGHT)
			arraylist.add(TFC_Core.translate("gui.food.amount") + " " + ounces + " oz / " + Global.FOOD_MAX_WEIGHT + " oz");

		float decay = Food.getDecay(is);
		if (decay > 0)
			arraylist.add(EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.food.decay") + " " + Helper.roundNumber(decay / ounces * 100, 10) + "%");
		if (TFCOptions.enableDebugMode)
		{
			arraylist.add(EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.food.decay") + ": " + decay);
			arraylist.add(EnumChatFormatting.DARK_GRAY + "Decay Rate: " + Helper.roundNumber(this.getDecayRate(is), 100));
		}

		if (TFC_Core.showCtrlInformation())
			ItemFoodTFC.addTasteInformation(is, player, arraylist);
		else
			arraylist.add(TFC_Core.translate("gui.showtaste"));
	}

	public static void addTasteInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		IFood food = (IFood) is.getItem();
		int sweet = food.getTasteSweet(is);
		int sour = food.getTasteSour(is);
		int salty = food.getTasteSalty(is);
		int bitter = food.getTasteBitter(is);
		int savory = food.getTasteSavory(is);
		SkillRank rank = TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_COOKING);
		if(Food.hasMealSkill(is))
			rank = SkillRank.values()[Food.getMealSkill(is)];

		int[] prefs = TFC_Core.getPlayerFoodStats(player).getPrefTaste();

		String sSweet = EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.taste.sweet") + ": " + EnumChatFormatting.WHITE;
		String sSour = EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.taste.sour") + ": " + EnumChatFormatting.WHITE;
		String sSalty = EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.taste.salty") + ": " + EnumChatFormatting.WHITE;
		String sBitter = EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.taste.bitter") + ": " + EnumChatFormatting.WHITE;
		String sSavory = EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.taste.savory") + ": " + EnumChatFormatting.WHITE;

		if(rank == SkillRank.Novice)
		{
			sSweet += (sweet > prefs[0] ? TFC_Core.translate("gui.taste.novice.sweet1") : TFC_Core.translate("gui.taste.novice.sweet0"));
			sSour += (sour > prefs[1] ? TFC_Core.translate("gui.taste.novice.sour1") : TFC_Core.translate("gui.taste.novice.sour0"));
			sSalty += (salty > prefs[2] ? TFC_Core.translate("gui.taste.novice.salty1") : TFC_Core.translate("gui.taste.novice.salty0"));
			sBitter += (bitter > prefs[3] ? TFC_Core.translate("gui.taste.novice.bitter1") : TFC_Core.translate("gui.taste.novice.bitter0"));
			sSavory += (savory > prefs[4] ? TFC_Core.translate("gui.taste.novice.savory1") : TFC_Core.translate("gui.taste.novice.savory0"));
		}
		else if(rank == SkillRank.Adept)
		{
			sSweet += ItemFoodTFC.createBasicString(sweet, prefs[0], "sweet");
			sSour += ItemFoodTFC.createBasicString(sour, prefs[1], "sour");
			sSalty += ItemFoodTFC.createBasicString(salty, prefs[2], "salty");
			sBitter += ItemFoodTFC.createBasicString(bitter, prefs[3], "bitter");
			sSavory += ItemFoodTFC.createBasicString(savory, prefs[4], "savory");
		}
		else if(rank == SkillRank.Expert)
		{
			sSweet += ItemFoodTFC.createExpertString(sweet, prefs[0], "sweet");
			sSour += ItemFoodTFC.createExpertString(sour, prefs[1], "sour");
			sSalty += ItemFoodTFC.createExpertString(salty, prefs[2], "salty");
			sBitter += ItemFoodTFC.createExpertString(bitter, prefs[3], "bitter");
			sSavory += ItemFoodTFC.createExpertString(savory, prefs[4], "savory");
		}
		else if(rank == SkillRank.Master)
		{
			sSweet += ItemFoodTFC.createBasicString(sweet, prefs[0], "sweet") + " (" + (sweet - prefs[0]) + ")";
			sSour += ItemFoodTFC.createBasicString(sour, prefs[1], "sour") + " (" + (sour - prefs[1]) + ")";
			sSalty += ItemFoodTFC.createBasicString(salty, prefs[2], "salty") + " (" + (salty - prefs[2]) + ")";
			sBitter += ItemFoodTFC.createBasicString(bitter, prefs[3], "bitter") + " (" + (bitter - prefs[3]) + ")";
			sSavory += ItemFoodTFC.createBasicString(savory, prefs[4], "savory") + " (" + (savory - prefs[4]) + ")";
		}

		arraylist.add(sSweet);
		arraylist.add(sSour);
		arraylist.add(sSalty);
		arraylist.add(sBitter);
		arraylist.add(sSavory);
	}

	private static String createExpertString(int val, int pref, String name)
	{
		int abs = Math.abs(val - pref);

		String out = ItemFoodTFC.createBasicString(val, pref, name);

		if(abs <= 5)
			out += " (+-5)";
		else if(abs <= 10)
			out += " (+-10)";
		else if(abs <= 15)
			out += " (+-15)";
		else if(abs <= 20)
			out += " (+-20)";

		return out;
	}

	private static String createBasicString(int val, int pref, String name)
	{
		int dif = val - pref;

		if(dif >= -5 && dif <= 5)
			return TFC_Core.translate("gui.taste.4") + " " + TFC_Core.translate("gui.taste." + name);
		else if(dif >= -10 && dif < -5)
			return TFC_Core.translate("gui.taste.3") + " " + TFC_Core.translate("gui.taste." + name);
		else if(dif >= -15 && dif < -10)
			return TFC_Core.translate("gui.taste.2") + " " + TFC_Core.translate("gui.taste." + name);
		else if(dif >= -20 && dif < -15)
			return TFC_Core.translate("gui.taste.1") + " " + TFC_Core.translate("gui.taste." + name);
		else if(dif < -20)
			return TFC_Core.translate("gui.taste.0") + " " + TFC_Core.translate("gui.taste." + name);
		else if(dif > 5 && dif <= 10)
			return TFC_Core.translate("gui.taste.5") + " " + TFC_Core.translate("gui.taste." + name);
		else if(dif > 10 && dif <= 15)
			return TFC_Core.translate("gui.taste.6") + " " + TFC_Core.translate("gui.taste." + name);
		else if(dif > 15 && dif <= 20)
			return TFC_Core.translate("gui.taste.7") + " " + TFC_Core.translate("gui.taste." + name);
		else if(dif > 20)
			return TFC_Core.translate("gui.taste.8") + " " + TFC_Core.translate("gui.taste." + name);

		return "";
	}

	public static String getFoodGroupName(EnumFoodGroup fg)
	{
		if(fg == EnumFoodGroup.Dairy)
			return (EnumChatFormatting.WHITE + TFC_Core.translate("gui.food.dairy"));
		else if(fg == EnumFoodGroup.Fruit)
			return (EnumChatFormatting.DARK_PURPLE + TFC_Core.translate("gui.food.fruit"));
		else if(fg == EnumFoodGroup.Vegetable)
			return (EnumChatFormatting.DARK_GREEN + TFC_Core.translate("gui.food.vegetable"));
		else if(fg == EnumFoodGroup.Protein)
			return (EnumChatFormatting.DARK_RED + TFC_Core.translate("gui.food.protein"));
		else if(fg == EnumFoodGroup.Grain)
			return (EnumChatFormatting.YELLOW + TFC_Core.translate("gui.food.grain"));
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
		if (foodstats.needFood() && this.isEdible(is))
			player.setItemInUse(is, 32);

		return is;
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		if(!world.isRemote && this.isEdible(is))
		{
			if(is.hasTagCompound())
			{
				//NBTTagCompound nbt = is.getTagCompound();
				float weight = Food.getWeight(is);
				float decay = Math.max(Food.getDecay(is), 0);

				float eatAmount = Math.min(weight - decay, 5f);
				float stomachDiff = foodstats.stomachLevel+eatAmount-foodstats.getMaxStomach(foodstats.player);
				if(stomachDiff > 0)
					eatAmount-=stomachDiff;

				float tasteFactor = foodstats.getTasteFactor(is);
				foodstats.addNutrition(((IFood)(is.getItem())).getFoodGroup(), eatAmount*tasteFactor);
				foodstats.stomachLevel += eatAmount*tasteFactor;
				if(FoodStatsTFC.reduceFood(is, eatAmount))
					is.stackSize = 0;
			}
			else
			{
				foodstats.addNutrition(((IFood)(is.getItem())).getFoodGroup(), 1f);

				String error = TFC_Core.translate("error.error") + " " + is.getUnlocalizedName() + " " +
								TFC_Core.translate("error.NBT") + " " + TFC_Core.translate("error.Contact");
				TerraFirmaCraft.LOG.error(error);
				TFC_Core.sendInfoMessage(player, new ChatComponentText(error));
			}
		}

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
				float weight = Food.getWeight(is);
				float decay = Math.max(Food.getDecay(is), 0);
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
		return TFC_ItemHeat.getTemp(is) > TFC_ItemHeat.isCookable(is) * 0.8;
	}

	public static ItemStack createTag(ItemStack is)
	{
		return ItemFoodTFC.createTag(is, 999);
	}

	public static ItemStack createTag(ItemStack is, float weight)
	{
		if (!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());

		Food.setWeight(is, weight);
		Food.setDecay(is, -24);
		Food.setDecayTimer(is, (int) TFC_Time.getTotalHours() + 1);

		return is;
	}

	public static ItemStack createTag(ItemStack is, float weight, float decay)
	{
		is = ItemFoodTFC.createTag(is, weight);
		Food.setDecay(is, decay);
		return is;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack is)
	{
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack is)
	{
		return EnumAction.eat;
	}

	@Override
	public int getDisplayDamage(ItemStack is)
	{
		float decay = Food.getDecay(is);
		float weight = Food.getWeight(is);
		int percent = (int) ((decay / weight) * 100);
		percent = percent > 0 ? percent < 100 ? percent : 100 : 0;
		return percent;
	}

	@Override
	public boolean isDamaged(ItemStack is)
	{
		/*if (is.hasTagCompound())
			return true;
		else*/
		return false;
	}

	@Override
	public int getMaxDamage(ItemStack is)
	{
		return 100;
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		float weight = Food.getWeight(is);
		if(weight <= 20)
			return EnumSize.TINY;
		else if(weight <= 40)
			return EnumSize.VERYSMALL;
		else if(weight <= 80)
			return EnumSize.SMALL;
		else
			return EnumSize.MEDIUM;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		float weight = Food.getWeight(is);
		if(weight < 80)
			return EnumWeight.LIGHT;
		else if(weight < 160)
			return EnumWeight.MEDIUM;
		else
			return EnumWeight.HEAVY;
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
	public ItemStack onDecayed(ItemStack is, World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public boolean isEdible(ItemStack is)
	{
		return edible || Food.isCooked(is);
	}

	@Override
	public boolean isUsable(ItemStack is)
	{
		return canBeUsedRaw || Food.isCooked(is);
	}

	@Override
	public int getTasteSweet(ItemStack is) {
		int base = tasteSweet;
		if(is != null)
		{
			if(is.getTagCompound().hasKey("tasteSweet"))
				base = is.getTagCompound().getInteger("tasteSweet");
			base += Food.getCookedProfile(is)[0];
			base += Food.getFuelProfile(is)[0]*getSmokeAbsorbMultiplier();
		}
		return Math.max(base + Food.getSweetMod(is), 0);
	}

	@Override
	public int getTasteSour(ItemStack is) {
		int base = tasteSour;
		if(is != null)
		{
			if(is.getTagCompound().hasKey("tasteSour"))
				base = is.getTagCompound().getInteger("tasteSour");
			base += Food.getCookedProfile(is)[1];
			base += Food.getFuelProfile(is)[1]*getSmokeAbsorbMultiplier();
		}
		if (Food.isBrined(is))
			base += 5;
		if(Food.isPickled(is))
			base += 30;
		return Math.max(base + Food.getSourMod(is), 0);
	}

	@Override
	public int getTasteSalty(ItemStack is) {
		int base = tasteSalty;
		if(is != null)
		{
			if(is.getTagCompound().hasKey("tasteSalty"))
				base = is.getTagCompound().getInteger("tasteSalty");
			base += Food.getCookedProfile(is)[2];
			base += Food.getFuelProfile(is)[2]*getSmokeAbsorbMultiplier();
		}
		if(Food.isSalted(is))
			base += 40;
		if(Food.isBrined(is))
			base += 10;

		return Math.max(base + Food.getSaltyMod(is), 0);
	}

	@Override
	public int getTasteBitter(ItemStack is) {
		int base = tasteBitter;
		if(is != null)
		{
			if(is.getTagCompound().hasKey("tasteBitter"))
				base = is.getTagCompound().getInteger("tasteBitter");
			base += Food.getCookedProfile(is)[3];
			base += Food.getFuelProfile(is)[3]*getSmokeAbsorbMultiplier();
		}
		return Math.max(base + Food.getBitterMod(is), 0);
	}

	@Override
	public int getTasteSavory(ItemStack is) {
		int base = tasteUmami;
		if(is != null)
		{
			if(is.getTagCompound().hasKey("tasteUmami"))
				base = is.getTagCompound().getInteger("tasteUmami");
			base += Food.getCookedProfile(is)[4];
			base += Food.getFuelProfile(is)[4]*getSmokeAbsorbMultiplier();
		}
		return Math.max(base + Food.getSavoryMod(is), 0);
	}

	@Override
	public float getFoodMaxWeight(ItemStack is) {
		return 160;
	}

	@Override
	public boolean renderDecay() {
		return true;
	}

	@Override
	public boolean renderWeight() {
		return true;
	}

	@Override
	public boolean canSmoke() {
		return canBeSmoked;
	}

	@Override
	public float getSmokeAbsorbMultiplier()
	{
		return this.smokeAbsorb;
	}

	public ItemFoodTFC setSmokeAbsorbMultiplier(float s) {
		smokeAbsorb = s;
		return this;
	}
}
