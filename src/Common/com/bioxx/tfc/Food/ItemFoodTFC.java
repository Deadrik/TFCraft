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
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Core.Player.SkillStats.SkillRank;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.FoodRegistry;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.TFC_ItemHeat;
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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
	public boolean isEdible = true;
	public boolean canBeUsedRaw = true;
	protected int tasteSweet = 0;
	protected int tasteSour = 0;
	protected int tasteSalty = 0;
	protected int tasteBitter = 0;
	protected int tasteUmami = 0;
	protected boolean canSmoke = false;
	protected float smokeAbsorb = 0.5f;

	public IIcon cookedIcon;
	protected boolean hasCookedIcon = false;

	public ItemFoodTFC(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um)
	{
		super();
		this.setCreativeTab(TFCTabs.TFCFoods);
		this.setFolder("food/");
		foodgroup = fg;
		TFCItems.FoodList.add(this);
		this.setMaxDamage(100);
		this.hasSubtypes = false;
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
		isEdible = edible;
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
		this.canSmoke = true;
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
			this.cookedIcon = registerer.registerIcon(Reference.ModID + ":" + this.textureFolder + name.replace("item.", "")+"Cooked");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack is, int par2)
	{
		float cookedLevel = Food.getCooked(is);
		int r = 255 - (int)(160 * (Math.max(cookedLevel-600, 0) / 600f)); 
		int b = 255 - (int)(160 * (Math.max(cookedLevel-600, 0) / 600f));
		int g = 255 - (int)(160 * (Math.max(cookedLevel-600, 0) / 600f));
		int rbg = (r << 16) + (b << 8) + g;
		return rbg;
	}

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
				mult *= 1f - (0.5f * this.getSmokeAbsorbMultiplier());
		}
		return decayRate / (TFC_Time.getYearRatio(96)) * mult;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		list.add(createTag(new ItemStack(this, 1), Global.FOOD_MAX_WEIGHT));
	}

	@Override
	public String getItemStackDisplayName(ItemStack is)
	{
		String s = "";
		if(Food.isPickled(is))
			s += StatCollector.translateToLocal("word.pickled") + " ";
		else if(Food.isBrined(is))
			s += StatCollector.translateToLocal("word.brined") + " ";

		if(Food.isSalted(is))
			s += StatCollector.translateToLocal("word.salted") + " ";
		if(Food.isCooked(is))
			s += StatCollector.translateToLocal("word.cooked") + " ";
		else if(Food.isSmoked(is))
			s += StatCollector.translateToLocal("word.smoked") + " ";
		s += StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(is) + ".name");
		s += getCookedLevelString(is);
		return s.trim();
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
			case 0: s+=StatCollector.translateToLocal("food.cooked.0");break;
			case 1: s+=StatCollector.translateToLocal("food.cooked.1");break;
			case 2: s+=StatCollector.translateToLocal("food.cooked.2");break;
			case 3: s+=StatCollector.translateToLocal("food.cooked.3");break;
			default: s+=StatCollector.translateToLocal("food.cooked.4");break;
			}
			s+= ")";
		}
		return s;
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
			addFoodInformation(is, player, arraylist);
		else
			arraylist.add(StatCollector.translateToLocal("gui.badnbt"));
	}

	public void addFoodInformation(ItemStack is, EntityPlayer player, List arraylist)
	{
		NBTTagCompound tag = is.getTagCompound();
		/*if(tag.hasKey("isSalted"))
			arraylist.add("\u2022" + StatCollector.translateToLocal("gui.food.salted"));*/
		if(tag.hasKey("foodWeight") && tag.getFloat("foodWeight") != 999)
		{
			float ounces = Helper.roundNumber(tag.getFloat("foodWeight"), 100);
			if(ounces > 0)
				arraylist.add(StatCollector.translateToLocal("gui.food.amount") + " " + ounces + " oz / " + Global.FOOD_MAX_WEIGHT + " oz");
			float decay = tag.getFloat("foodDecay");
			if(decay > 0)
				arraylist.add(EnumChatFormatting.DARK_GRAY + StatCollector.translateToLocal("gui.food.decay") + " " + Helper.roundNumber(decay / ounces * 100, 10) + "%");
			if(TFCOptions.enableDebugMode)
			{
				arraylist.add(EnumChatFormatting.DARK_GRAY + StatCollector.translateToLocal("gui.food.decay") + ": " + decay);
				arraylist.add(EnumChatFormatting.DARK_GRAY +  "Decay Rate: " + this.getDecayRate(is));
			}

			if (TFC_Core.showCtrlInformation()) 
				ItemFoodTFC.addTasteInformation(is, player, arraylist);
			else
				arraylist.add(StatCollector.translateToLocal("gui.showtaste"));
		}
	}

	public static void addTasteInformation(ItemStack is, EntityPlayer player, List arraylist) 
	{
		int sweet = ((IFood)is.getItem()).getTasteSweet(is);
		int sour = ((IFood)is.getItem()).getTasteSour(is);
		int salty = ((IFood)is.getItem()).getTasteSalty(is);
		int bitter = ((IFood)is.getItem()).getTasteBitter(is);
		int savory = ((IFood)is.getItem()).getTasteSavory(is);

		if(TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_COOKING) == SkillRank.Novice)
		{
			arraylist.add(EnumChatFormatting.DARK_GRAY + translate("gui.taste.sweet") + ": " + EnumChatFormatting.WHITE + (sweet > 50 ? translate("gui.taste.novice.sweet1") : translate("gui.taste.novice.sweet0")));
			arraylist.add(EnumChatFormatting.DARK_GRAY + translate("gui.taste.sour") + ": " + EnumChatFormatting.WHITE + (sour > 50 ? translate("gui.taste.novice.sour1") : translate("gui.taste.novice.sour0")));
			arraylist.add(EnumChatFormatting.DARK_GRAY + translate("gui.taste.salty") + ": " + EnumChatFormatting.WHITE + (salty > 50 ? translate("gui.taste.novice.salty1") : translate("gui.taste.novice.salty0")));
			arraylist.add(EnumChatFormatting.DARK_GRAY + translate("gui.taste.bitter") + ": " + EnumChatFormatting.WHITE + (bitter > 50 ? translate("gui.taste.novice.bitter1") : translate("gui.taste.novice.bitter0")));
			arraylist.add(EnumChatFormatting.DARK_GRAY + translate("gui.taste.savory") + ": " + EnumChatFormatting.WHITE + (savory > 50 ? translate("gui.taste.novice.savory1") : translate("gui.taste.novice.savory0")));
		}
		else if(TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_COOKING) == SkillRank.Adept)
		{
			arraylist.add(EnumChatFormatting.DARK_GRAY + translate("gui.taste.sweet") + ": "+EnumChatFormatting.WHITE + createAdeptString(sweet, "sweet"));
			arraylist.add(EnumChatFormatting.DARK_GRAY + translate("gui.taste.sour") + ": "+EnumChatFormatting.WHITE + createAdeptString(sour, "sour"));
			arraylist.add(EnumChatFormatting.DARK_GRAY + translate("gui.taste.salty") + ": "+EnumChatFormatting.WHITE + createAdeptString(salty, "salty"));
			arraylist.add(EnumChatFormatting.DARK_GRAY + translate("gui.taste.bitter") + ": "+EnumChatFormatting.WHITE + createAdeptString(bitter, "bitter"));
			arraylist.add(EnumChatFormatting.DARK_GRAY + translate("gui.taste.savory") + ": "+EnumChatFormatting.WHITE + createAdeptString(savory, "savory"));
		}
		else if(TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_COOKING) == SkillRank.Expert)
		{
			arraylist.add(EnumChatFormatting.DARK_GRAY + translate("gui.taste.sweet") + ": "+EnumChatFormatting.WHITE + createExpertString(sweet));
			arraylist.add(EnumChatFormatting.DARK_GRAY + translate("gui.taste.sour") + ": "+EnumChatFormatting.WHITE + createExpertString(sour));
			arraylist.add(EnumChatFormatting.DARK_GRAY + translate("gui.taste.salty") + ": "+EnumChatFormatting.WHITE + createExpertString(salty));
			arraylist.add(EnumChatFormatting.DARK_GRAY + translate("gui.taste.bitter") + ": "+EnumChatFormatting.WHITE + createExpertString(bitter));
			arraylist.add(EnumChatFormatting.DARK_GRAY + translate("gui.taste.savory") + ": "+EnumChatFormatting.WHITE + createExpertString(savory));
		}
		else if(TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_COOKING) == SkillRank.Master)
		{
			arraylist.add(EnumChatFormatting.DARK_GRAY + translate("gui.taste.sweet") + ": " + EnumChatFormatting.WHITE + sweet);
			arraylist.add(EnumChatFormatting.DARK_GRAY + translate("gui.taste.sour") + ": " + EnumChatFormatting.WHITE + sour);
			arraylist.add(EnumChatFormatting.DARK_GRAY + translate("gui.taste.salty") + ": " + EnumChatFormatting.WHITE + salty);
			arraylist.add(EnumChatFormatting.DARK_GRAY + translate("gui.taste.bitter") + ": " + EnumChatFormatting.WHITE + bitter);
			arraylist.add(EnumChatFormatting.DARK_GRAY + translate("gui.taste.savory") + ": " + EnumChatFormatting.WHITE + savory);
		}
	}

	private static String createExpertString(int val)
	{
		if(val < 20) return "0-20";
		else if(val < 40) return "21-40";
		else if(val < 60) return "41-60";
		else if(val < 80) return "61-80";
		else if(val < 100) return "81-100";
		else return "100+";
	}

	private static String createAdeptString(int val, String name)
	{
		if(val < 20) return translate("gui.taste.adept."+name+"0");
		else if(val < 40) return translate("gui.taste.adept."+name+"1");
		else if(val < 60) return translate("gui.taste.adept."+name+"2");
		else if(val < 80) return translate("gui.taste.adept."+name+"3");
		else if(val < 100) return translate("gui.taste.adept."+name+"4");
		else return translate("gui.taste.adept."+name+"5");
	}

	//For ease of use
	private static String translate(String s)
	{
		return StatCollector.translateToLocal(s);
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
		if(!world.isRemote && this.isEdible(is))
		{
			if(is.hasTagCompound())
			{
				NBTTagCompound nbt = is.getTagCompound();
				float weight = ((IFood)(is.getItem())).getFoodWeight(is);
				float decay = Math.max(((IFood)(is.getItem())).getFoodDecay(is), 0);

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
		if(TFC_ItemHeat.GetTemp(is) > TFC_ItemHeat.IsCookable(is) * 0.8)
			return true;
		else
			return false;
	}

	public static ItemStack createTag(ItemStack is)
	{
		NBTTagCompound nbt = is.getTagCompound();
		if(nbt == null)
			nbt = new NBTTagCompound();
		nbt.setFloat("foodWeight", 999);
		nbt.setFloat("foodDecay", -24);
		nbt.setInteger("decayTimer", (int)TFC_Time.getTotalHours() + 1);

		is.setTagCompound(nbt);
		return is;
	}

	public static ItemStack createTag(ItemStack is, float weight)
	{
		NBTTagCompound nbt = is.getTagCompound();
		if(nbt == null)
			nbt = new NBTTagCompound();
		nbt.setFloat("foodWeight", weight);
		nbt.setFloat("foodDecay", -24);
		nbt.setInteger("decayTimer", (int)TFC_Time.getTotalHours() + 1);

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
		float decay = getFoodDecay(is);
		float weight = getFoodWeight(is);
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
		float weight = getFoodWeight(is);
		if(weight < 32)
			return EnumSize.TINY;
		else if(weight < 64)
			return EnumSize.VERYSMALL;
		else if(weight < 96)
			return EnumSize.SMALL;
		else if(weight < 128)
			return EnumSize.MEDIUM;
		else if(weight < 160)
			return EnumSize.LARGE;
		else if(weight < 192)
			return EnumSize.VERYLARGE;
		else
			return EnumSize.HUGE;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		float weight = getFoodWeight(is);
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
		return isEdible || Food.isCooked(is);
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
			base = is.getTagCompound().getInteger("tasteSweet");
			base += Food.getCookedProfile(is)[0];
			base += Food.getFuelProfile(is)[0]*getSmokeAbsorbMultiplier();
		}
		return Math.max(base + getTasteSweetMod(is), 0);
	}

	public int getTasteSweetMod(ItemStack is) {
		int mod = 0;
		if(is != null)
			mod = is.getTagCompound().getInteger("tasteSweetMod");
		return mod;
	}

	@Override
	public int getTasteSour(ItemStack is) {
		int base = tasteSour;
		if(is != null)
		{
			base = is.getTagCompound().getInteger("tasteSour");
			base += Food.getCookedProfile(is)[1];
			base += Food.getFuelProfile(is)[1]*getSmokeAbsorbMultiplier();
		}
		if(Food.isPickled(is))
			base += 30;
		return Math.max(base + getTasteSourMod(is), 0);
	}

	public int getTasteSourMod(ItemStack is) {
		int mod = 0;
		if(is != null)
			mod = is.getTagCompound().getInteger("tasteSourMod");
		return mod;
	}

	@Override
	public int getTasteSalty(ItemStack is) {
		int base = tasteSalty;
		if(is != null)
		{
			base = is.getTagCompound().getInteger("tasteSalty");
			base += Food.getCookedProfile(is)[2];
			base += Food.getFuelProfile(is)[2]*getSmokeAbsorbMultiplier();
		}
		if(Food.isSalted(is))
			base += 40;
		if(Food.isPickled(is))
			base += 10;

		return Math.max(base + getTasteSaltyMod(is), 0);
	}

	public int getTasteSaltyMod(ItemStack is) {
		int mod = 0;
		if(is != null)
			mod = is.getTagCompound().getInteger("tasteSaltyMod");
		return mod;
	}

	@Override
	public int getTasteBitter(ItemStack is) {
		int base = tasteBitter;
		if(is != null)
		{
			base = is.getTagCompound().getInteger("tasteBitter");
			base += Food.getCookedProfile(is)[3];
			base += Food.getFuelProfile(is)[3]*getSmokeAbsorbMultiplier();
		}
		return Math.max(base + getTasteBitterMod(is), 0);
	}

	public int getTasteBitterMod(ItemStack is) {
		int mod = 0;
		if(is != null)
			mod = is.getTagCompound().getInteger("tasteBitterMod");
		return mod;
	}

	@Override
	public int getTasteSavory(ItemStack is) {
		int base = tasteUmami;
		if(is != null)
		{
			base = is.getTagCompound().getInteger("tasteUmami");
			base += TFC_Core.getCookedTasteProfile(is)[4];
			base += Food.getFuelProfile(is)[4]*getSmokeAbsorbMultiplier();
		}
		return Math.max(base + getTasteSavoryMod(is), 0);
	}

	public int getTasteSavoryMod(ItemStack is) {
		int mod = 0;
		if(is != null)
			mod = is.getTagCompound().getInteger("tasteUmamiMod");
		return mod;
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
		return canSmoke;
	}

	@Override
	public float getSmokeAbsorbMultiplier() {
		return this.smokeAbsorb;
	}

	public ItemFoodTFC setSmokeAbsorbMultiplier(float s) {
		smokeAbsorb = s;
		return this;
	}
}
