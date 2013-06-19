package TFC.Food;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.API.ISize;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.Util.StringUtil;
import TFC.Items.ItemTerra;

public class ItemTerraFood extends ItemFood implements ISize
{
	public int foodID;
	private EnumSize size = EnumSize.SMALL;
	private EnumWeight weight = EnumWeight.LIGHT;
	public int Tier = 0;

	public String folder = "food/";

	public ItemTerraFood(int id, int healAmt) 
	{
		super(id, healAmt, true);
	}

	public ItemTerraFood(int id, int healAmt, float saturation, boolean wolfFood, int foodid)
	{
		super(id, healAmt, saturation, wolfFood);
		foodID = foodid;
	}

	public ItemTerraFood(int id, int healAmt, float saturation, boolean wolfFood, int foodid, int tier)
	{
		super(id, healAmt, saturation, wolfFood);
		foodID = foodid;
		Tier = tier;
	}

	public ItemTerraFood setFoodID(int id)
	{
		foodID = id;
		return this;
	}

	public ItemTerraFood setFoodTier(int tier)
	{
		Tier = tier;
		return this;
	}

	public ItemTerraFood setFolder(String s)
	{
		folder = s;
		return this;
	}

	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		return StringUtil.localize(getUnlocalizedName(itemstack).replace(" ", ""));
	}


	/***
	 * This Method is a dummy to prevent the need to fix every single line in the TFCItems.java file
	 */
	public Item setIconCoord(int i, int j)
	{return this;}

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
				{
					arraylist.add(TFC_ItemHeat.getHeatColorFood(temp, meltTemp));
				}
			}
		}
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(this, arraylist);

		this.addFoodTempInformation(is, arraylist);

		int filling = this.getHealAmount() / 10;
		if(filling > 0)
		{
			String stars = "";
			int whitestars = 5-filling;
			int blackstars = filling;

			for(int i = 0; i < blackstars; i++)
			{
				stars += "\u272e";
			}
			for(int i = 0; i < whitestars; i++)
			{
				stars += "\u2729";
			}

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
			{
				TFC_ItemHeat.HandleItemHeat(is, (int)entity.posX, (int)entity.posY, (int)entity.posZ);
			}
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		if(!world.isRemote)
		{
			if (foodstats.needFood())
			{
				player.setItemInUse(is, this.getMaxItemUseDuration(is));
			}
		}
		else
		{
			if (foodstats.needFood())
			{
				player.setItemInUse(is, this.getMaxItemUseDuration(is));
			}
		}

		return is;
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		is.stackSize--;
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		player.getFoodStats().addStats(this);
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		if(!world.isRemote)
		{
			foodstats.addStats(this);
			TFC_Core.setPlayerFoodStats(player, foodstats);
		}
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
			return this.getSize().stackSize * getWeight().multiplier <= 64 ? this.getSize().stackSize * getWeight().multiplier : 64;
			else
				return 1;
	}

	public boolean isHot(ItemStack is)
	{
		if(TFC_ItemHeat.GetTemperature(is) > TFC_ItemHeat.getMeltingPoint(is) *0.8)
			return true;
		else return false;
	}

	public Item setSize(EnumSize s)
	{
		size = s;
		return this;
	}

	public Item setWeight(EnumWeight w)
	{
		weight = w;
		return this;
	}

	@Override
	public EnumSize getSize() {
		// TODO Auto-generated method stub
		return size;
	}
	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return weight;
	}
	@Override
	public boolean canStack() {
		// TODO Auto-generated method stub
		return true;
	}
}
