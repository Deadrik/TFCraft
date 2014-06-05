package com.bioxx.tfc.Items.Tools;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.EntityFishHookTFC;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISize;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCustomFishingRod extends ItemFishingRod implements ISize
{
	@SideOnly(Side.CLIENT)
	private IIcon theIcon;

	public ItemCustomFishingRod()
	{
		super();
		this.setMaxDamage(64);
		this.setMaxStackSize(1);
		this.setCreativeTab(TFCTabs.TFCTools);
		setNoRepair();
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	public boolean isFull3D()
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Returns true if this item should be rotated by 180 degrees around the Y axis when being held in an entities
	 * hands.
	 */
	public boolean shouldRotateAroundWhenRendering()
	{
		return true;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		if(is.stackTagCompound != null && is.stackTagCompound.hasKey("tickReeledIn"))
		{
			long tickReeledIn = is.stackTagCompound.getLong("tickReeledIn");
			if(TFC_Time.getTotalTicks() <= tickReeledIn + 20)
				return is;
		}

		if (player.fishEntity != null)
		{
			//int i = par3EntityPlayer.fishEntity.func_146034_e();
			//par1ItemStack.damageItem(i, par3EntityPlayer);
			if(player.fishEntity instanceof EntityFishHookTFC)
				((EntityFishHookTFC)(player.fishEntity)).reelInBobber(player, is);
			else
				player.swingItem();
		}
		else
		{
			world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isRemote)
				world.spawnEntityInWorld(new EntityFishHookTFC(world, player));
			player.swingItem();
		}
		return is;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(this.getIconString() + "_uncast");
		this.theIcon = par1IconRegister.registerIcon(this.getIconString() + "_cast");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
	{
		if (player.fishEntity != null)
			return theIcon;
		return itemIcon;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.TINY;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.LIGHT;
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.FAR;
	}

	@Override
	public boolean canStack()
	{
		return true;
	}
}
