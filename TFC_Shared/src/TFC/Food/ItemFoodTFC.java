package TFC.Food;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.API.IFood;
import TFC.API.ISize;
import TFC.API.Enums.EnumFoodGroup;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.Player.FoodStatsTFC;
import TFC.Core.Util.StringUtil;
import TFC.Items.ItemTerra;

public class ItemFoodTFC extends ItemTerra implements ISize, IFood
{
	public int foodID;

	public String folder = "food/";
	public static int[] FoodList = new int[1024];
	private EnumFoodGroup foodgroup;

	public ItemFoodTFC(int id, int foodid, EnumFoodGroup fg)
	{
		super(id);
		this.setCreativeTab(CreativeTabs.tabFood);
		foodID = foodid;
		FoodList[foodID] = this.itemID;
		foodgroup = fg;
	}

	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		return StringUtil.localize(getUnlocalizedName(itemstack).replace(" ", ""));
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + folder + this.getUnlocalizedName().replace("item.", ""));
	}

	public static void addFoodTempInformation(ItemStack is, List arraylist)
	{
		if (is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();

			if(stackTagCompound.hasKey("temperature"))
			{
				float temp = stackTagCompound.getFloat("temperature");
				float meltTemp = 0;

				meltTemp = TFC_ItemHeat.getMeltingPoint(is);

				if(meltTemp != -1)
					arraylist.add(TFC_ItemHeat.getHeatColorFood(temp, meltTemp));
			}

			if(stackTagCompound.hasKey("foodweight"))
			{
				float ounces = stackTagCompound.getFloat("foodweight");
				arraylist.add(ounces+"oz/10.0oz");
			}
		}
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(is, arraylist);

		if(this.getFoodGroup() == EnumFoodGroup.Dairy)
			arraylist.add(EnumChatFormatting.WHITE + StringUtil.localize("gui.Food.Dairy"));
		else if(this.getFoodGroup() == EnumFoodGroup.Fruit)
			arraylist.add(EnumChatFormatting.DARK_PURPLE + StringUtil.localize("gui.Food.Fruit"));
		else if(this.getFoodGroup() == EnumFoodGroup.Vegetable)
			arraylist.add(EnumChatFormatting.DARK_GREEN + StringUtil.localize("gui.Food.Vegetable"));
		else if(this.getFoodGroup() == EnumFoodGroup.Protein)
			arraylist.add(EnumChatFormatting.DARK_RED + StringUtil.localize("gui.Food.Protein"));
		else if(this.getFoodGroup() == EnumFoodGroup.Grain)
			arraylist.add(EnumChatFormatting.YELLOW + StringUtil.localize("gui.Food.Grain"));

		this.addFoodTempInformation(is, arraylist);

		//int filling = this.getHealAmount() / 10;
		int filling = 10;
		if(filling > 0)
		{
			String stars = "";
			int whitestars = 5-filling;
			int blackstars = filling;

			for(int i = 0; i < blackstars; i++)
				stars += "\u272e";
			for(int i = 0; i < whitestars; i++)
				stars += "\u2729";

			arraylist.add(StringUtil.localize("gui.FoodPrep.Filling") + ": " + stars);
		}
	}

	@Override
	public void onUpdate(ItemStack is, World world, Entity entity, int i, boolean isSelected) 
	{
		if (!world.isRemote && is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();

			if(stackTagCompound.hasKey("temperature"))
				TFC_ItemHeat.HandleItemHeat(is, (int)entity.posX, (int)entity.posY, (int)entity.posZ);
		}
		if (!world.isRemote && is.stackSize > 1)
		{

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
		is.stackSize--;
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		//player.getFoodStats().addStats(this);
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		if(!world.isRemote)
			//foodstats.addStats(this);
			TFC_Core.setPlayerFoodStats(player, foodstats);
		return is;
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	@Override
	public int getItemStackLimit()
	{
		if(canStack())
			return this.getSize(null).stackSize * getWeight(null).multiplier <= 64 ? this.getSize(null).stackSize * getWeight(null).multiplier : 64;
			else
				return 1;
	}

	public boolean isHot(ItemStack is)
	{
		if(TFC_ItemHeat.GetTemperature(is) > TFC_ItemHeat.getMeltingPoint(is) *0.8)
			return true;
		else
			return false;
	}

	@Override
	public EnumSize getSize(ItemStack is) 
	{
		return size;
	}
	@Override
	public EnumWeight getWeight(ItemStack is) 
	{
		if(is.getTagCompound() != null && is.getTagCompound().hasKey("foodweight"))
			return EnumWeight.HEAVY;
		return weight;
	}
	@Override
	public boolean canStack() 
	{
		return true;
	}

	@Override
	public EnumFoodGroup getFoodGroup() {
		// TODO Auto-generated method stub
		return foodgroup;
	}
}
