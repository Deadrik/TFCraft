package TFC.Items.Tools;

import TFC.*;
import TFC.API.Util.Helper;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
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

public class ItemCustomBucketMilk extends ItemTerra
{
	public ItemCustomBucketMilk(int par1)
	{
		super(par1);
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
			if (var12.typeOfHit == EnumMovingObjectType.TILE)
			{
				int i = var12.blockX;
				int j = var12.blockY;
				int k = var12.blockZ;
				if(world.getBlockTileEntity(i,j,k) != null && world.getBlockTileEntity(i,j,k) instanceof TileEntityBarrel){
					TileEntityBarrel TE = (TileEntityBarrel) world.getBlockTileEntity(i,j,k);
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
