package com.bioxx.tfc.Items.Tools;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.EntityFishHookTFC;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISize;

public class ItemCustomFishingRod extends ItemFishingRod implements ISize
{
	@SideOnly(Side.CLIENT)
	private IIcon[] uncastIconArray;
	private IIcon[] castIconArray;

	public ItemCustomFishingRod()
	{
		super();
		this.setMaxDamage(64);
		this.setMaxStackSize(1);
		this.setCreativeTab(TFCTabs.TFC_TOOLS);
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

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack is)
    {
		if(is.stackTagCompound != null && is.stackTagCompound.hasKey("swing") && is.stackTagCompound.getBoolean("swing")){
			is.stackTagCompound.setBoolean("swing", false);
			return false;
		}
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
			{
				((EntityFishHookTFC)(player.fishEntity)).reelInBobber(player, is);
			}
			else
			{
				player.setItemInUse(is, 1);
				//player.swingItem();
			}
		}
		else
		{
			if(is.stackTagCompound == null){
				is.setTagCompound(new NBTTagCompound());
			}
			player.setItemInUse(is, getMaxItemUseDuration(is));
			//player.swingItem();
		}
		return is;
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(player.fishEntity instanceof EntityFishHookTFC)
		{
			((EntityFishHookTFC)(player.fishEntity)).reelInBobber(player, is);
		}
		/*else
		{
			//player.setItemInUse(is, 1);
			//player.swingItem();
		}*/
		return false;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack is, World world, EntityPlayer player, int inUseCount)
	{
		if(player.fishEntity == null && is.stackTagCompound != null){
			world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isRemote){
				world.spawnEntityInWorld(new EntityFishHookTFC(world, player, is.getMaxItemUseDuration() - inUseCount));
				
					is.stackTagCompound.setBoolean("fishing", true);
				
			}
			is.stackTagCompound.setBoolean("swing", true);
			player.swingItem();
			is.stackTagCompound.setBoolean("fishing", true);
			is.stackTagCompound.setInteger("usedUses",0);
		}
		else if(is.stackTagCompound != null){
			is.stackTagCompound.setBoolean("fishing", true);
		}
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack is)
	{
		return 72000;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		uncastIconArray = new IIcon[3];
		castIconArray = new IIcon[8];
		for (int i = 0; i < castIconArray.length; ++i)
			castIconArray[i] = par1IconRegister.registerIcon(Reference.MOD_ID+":"+this.getIconString() +"_cast_" + i);
		for (int i = 0; i < uncastIconArray.length; ++i)
			uncastIconArray[i] = par1IconRegister.registerIcon(Reference.MOD_ID+":"+this.getIconString() +"_uncast_" + i);
		this.itemIcon = uncastIconArray[0];
	}

	@SideOnly(Side.CLIENT)
	public IIcon getItemIconForUseDuration(int par1, boolean cast)
	{
		par1 = Math.min(Math.max(par1, 0),7);
		if(cast)
			return castIconArray[par1];
		return uncastIconArray[par1];
	}

	@Override
	public IIcon getIcon(ItemStack is, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
	{
		boolean cast = player.fishEntity != null;//is.hasTagCompound() && is.stackTagCompound.hasKey("fishing") && is.stackTagCompound.getBoolean("fishing");

		if(!is.hasTagCompound()){
			is.setTagCompound(new NBTTagCompound());
		}
		is.stackTagCompound.setBoolean("fishing", cast);
		if(usingItem == null){
			useRemaining = this.getMaxItemUseDuration(is); 
		}

		if(!cast){
			int j = Math.max(Math.min(this.getMaxItemUseDuration(is) - useRemaining + 10, 60) / 20 - 1, 0);
			if(!is.hasTagCompound()){
				is.setTagCompound(new NBTTagCompound());
			}
			is.stackTagCompound.setInteger("usedUses", this.getMaxItemUseDuration(is) - useRemaining);
			return getItemIconForUseDuration(Math.min(j,uncastIconArray.length -1),cast);
		}
		else{
			int tension = 0;
			if(is.hasTagCompound() && is.stackTagCompound.hasKey("tension")){
				tension = is.stackTagCompound.getInteger("tension");
			}
			int originalTex = tension / 100;
			int texShift = (tension % 100 + 1) % 31;
			return getItemIconForUseDuration(Math.min(originalTex + (texShift == 10?1:0),castIconArray.length-1),cast);
		}
	}


	@Override
	public IIcon getIcon(ItemStack is, int renderPass){
		if(is.hasTagCompound() && is.stackTagCompound.hasKey("fishing") && is.stackTagCompound.getBoolean("fishing")){
			return castIconArray[0];
		}
		return uncastIconArray[0];
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

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack is)
	{
		if (is.stackTagCompound != null && is.stackTagCompound.hasKey("fishing") && is.stackTagCompound.getBoolean("fishing"))
		{
			return EnumAction.bow;
		}
		return EnumAction.none;
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
