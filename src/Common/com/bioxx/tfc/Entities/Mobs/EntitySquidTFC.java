package com.bioxx.tfc.Entities.Mobs;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.ItemCustomNameTag;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;

public class EntitySquidTFC extends EntitySquid
{
	public EntitySquidTFC(World par1World)
	{
		super(par1World);
		this.setSize(0.75F, 0.75F);
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this entity.
	 */
	@Override
	public boolean getCanSpawnHere()
	{
		return this.posY > Global.SEALEVEL-16 && this.posY <= Global.SEALEVEL && this.worldObj.checkNoEntityCollision(this.boundingBox);
	}
	
	@Override
	public boolean canDespawn(){
		return !this.hasCustomNameTag();
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400);//MaxHealth
	}
	
	@Override
	public boolean interact(EntityPlayer player){
		ItemStack itemstack = player.getHeldItem();
		if(itemstack != null && itemstack.getItem() instanceof ItemCustomNameTag && itemstack.hasTagCompound() && itemstack.stackTagCompound.hasKey("ItemName")){
			String name = itemstack.stackTagCompound.getString("ItemName");

			this.setCustomNameTag(name);
			itemstack.stackSize--;

			return true;
		}
		return true;
	}

	/**
	 * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
	 * par2 - Level of Looting used to kill this mob.
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		int j = this.rand.nextInt(3 + par2) + 1;
		for (int k = 0; k < j; ++k)
			this.entityDropItem(new ItemStack(TFCItems.dye, 1, 0), 0.0F);
		//this.dropItem(TFCItems.CalamariRaw.itemID,((2+rand.nextInt(5))));
		TFC_Core.animalDropMeat(this, TFCItems.calamariRaw, 5f+(10*this.rand.nextFloat()));
	}
}
