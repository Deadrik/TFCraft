package TFC.Items.Tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.API.Util.Helper;
import TFC.Core.TFCTabs;
import TFC.Items.ItemTerra;
import TFC.TileEntities.TileEntityBarrel;

public class ItemCustomBucketMilk extends ItemTerra
{
	public ItemCustomBucketMilk()
	{
		super();
		this.setMaxStackSize(1);
		setCreativeTab(TFCTabs.TFCTools);
		this.setFolder("tools/");
	}

	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		--par1ItemStack.stackSize;

		if (!par2World.isRemote)
		{
			par3EntityPlayer.clearActivePotions();
		}

		return par1ItemStack.stackSize <= 0 ? new ItemStack(TFCItems.WoodenBucketEmpty) : par1ItemStack;
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
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer par3EntityPlayer)
	{
		float var4 = 1.0F;
		double var5 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * var4;
		double var7 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * var4 + 1.62D - par3EntityPlayer.yOffset;
		double var9 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * var4;
		MovingObjectPosition var12 = Helper.getMovingObjectPositionFromPlayer(world, par3EntityPlayer, false);

		if (var12 == null)
		{
			return is;
		}
		else
		{
			if (var12.typeOfHit == MovingObjectType.BLOCK)
			{
				int i = var12.blockX;
				int j = var12.blockY;
				int k = var12.blockZ;
				if(world.getTileEntity(i,j,k) != null && world.getTileEntity(i,j,k) instanceof TileEntityBarrel){
					TileEntityBarrel TE = (TileEntityBarrel) world.getTileEntity(i,j,k);
					if(TE.checkValidAddition(13)){
						return new ItemStack(TFCItems.WoodenBucketEmpty);
					}
				}
			}
		}
		par3EntityPlayer.setItemInUse(is, this.getMaxItemUseDuration(is));
		return is;
	}

}
