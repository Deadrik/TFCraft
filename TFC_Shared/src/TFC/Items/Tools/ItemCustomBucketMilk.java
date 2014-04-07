package TFC.Items.Tools;

import TFC.*;
import TFC.API.IFood;
import TFC.API.Enums.EnumFoodGroup;
import TFC.API.Util.Helper;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Sounds;
import TFC.Core.Player.FoodStatsTFC;
import TFC.Items.ItemTerra;
import TFC.TileEntities.TileEntityBarrel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;

public class ItemCustomBucketMilk extends ItemTerra implements IFood
{
	public ItemCustomBucketMilk(int par1)
	{
		super(par1);
		this.setMaxStackSize(1);
		setCreativeTab(TFCTabs.TFCTools);
		this.setFolder("tools/");
	}
	
	@Override
	public boolean canStack() {
		// TODO Auto-generated method stub
		return false;
	}

	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		if(!world.isRemote && foodstats.needFood()){
			
			world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);

			foodstats.eatFood(is);
			foodstats.restoreWater(player, 8000);

			TFC_Core.setPlayerFoodStats(player, foodstats);
			
			is.itemID = TFCItems.WoodenBucketEmpty.itemID;
		}
		return is;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 32;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.drink;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer entity)
	{
		MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, entity, true);
		FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(entity);
		Boolean canDrink = fs.getMaxWater(entity) - 500 > fs.waterLevel;

		if(mop == null)
		{
			if(is.getItemDamage() > 1 && canDrink) {
				entity.setItemInUse(is, this.getMaxItemUseDuration(is));
			}
		}
		else
		{
			if (mop.typeOfHit == EnumMovingObjectType.TILE)
			{
				int i = mop.blockX;
				int j = mop.blockY;
				int k = mop.blockZ;
				if(world.getBlockTileEntity(i,j,k) != null && world.getBlockTileEntity(i,j,k) instanceof TileEntityBarrel){
					TileEntityBarrel TE = (TileEntityBarrel) world.getBlockTileEntity(i,j,k);
					if(TE.checkValidAddition(13)){
						return new ItemStack(TFCItems.WoodenBucketEmpty);
					}
				}
			}
		}
		entity.setItemInUse(is, this.getMaxItemUseDuration(is));
		return is;
	}

	@Override
	public EnumFoodGroup getFoodGroup() {
		// TODO Auto-generated method stub
		return EnumFoodGroup.Dairy;
	}

	@Override
	public int getFoodID() {
		return -1;
	}

}
