package TFC.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.API.IFood;
import TFC.API.IItemFoodBlock;
import TFC.API.Enums.EnumFoodGroup;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFC_Core;
import TFC.Core.Player.FoodStatsTFC;

public class ItemFoodBlock extends ItemTerraBlock implements IItemFoodBlock, IFood
{
	int foodID;

	public ItemFoodBlock(Block par1)
	{
		super(par1);
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		if(is!=null)
			if(is.getItem() == Item.getItemFromBlock(Blocks.brown_mushroom))
				return EnumSize.VERYSMALL;
		return EnumSize.SMALL;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		if(is!=null)
			if(is.getItem() == Item.getItemFromBlock(Blocks.brown_mushroom))
				return EnumWeight.LIGHT;
		return EnumWeight.MEDIUM;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		if(!world.isRemote)
			if (foodstats.needFood())
				player.setItemInUse(is, this.getMaxItemUseDuration(is));
			else
				if (foodstats.needFood())
					player.setItemInUse(is, this.getMaxItemUseDuration(is));
		return is;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack is)
	{
		if(is != null && is.getItem() instanceof ItemFoodBlock)
			return EnumAction.eat;
		return super.getItemUseAction(is);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 32;
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		is.stackSize--;
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		player.getFoodStats().addStats(getHealAmount(is),-0.5F);
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		if(!world.isRemote)
			//foodstats.addStats(getHealAmount(is),-0.5F);
			TFC_Core.setPlayerFoodStats(player, foodstats);
		return is;
	}

	@Override
	public int getFoodId(ItemStack is)
	{
		if(is.getItem() == Item.getItemFromBlock(Blocks.brown_mushroom))
			return 61;
		else if(is.getItem() == Item.getItemFromBlock(Blocks.pumpkin))
			return 62;
		else if(is.getItem() == Item.getItemFromBlock(Blocks.melon_block))
			return 63;
		return 1;
	}

	@Override
	public int getHealAmount(ItemStack is)
	{
		if(is.getItem() == Item.getItemFromBlock(Blocks.brown_mushroom))
			return 25;
		else if(is.getItem() == Item.getItemFromBlock(Blocks.pumpkin))
			return 35;
		else if(is.getItem() == Item.getItemFromBlock(Blocks.melon_block))
			return 35;
		return 0;
	}

	@Override
	public EnumFoodGroup getFoodGroup()
	{
		return EnumFoodGroup.None;
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
	public float getDecayRate()
	{
		return 0;
	}
}