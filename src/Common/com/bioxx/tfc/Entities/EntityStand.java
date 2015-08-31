package com.bioxx.tfc.Entities;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.ItemTFCArmor;
import com.bioxx.tfc.api.TFCBlocks;

public class EntityStand extends EntityLiving
{
	private static int defaultArmorLength = 4;
	private static int defaultEquipableLength = TFC_Core.getExtraEquipInventorySize();
	private ItemStack[] armor;
	private ItemStack[] equipable;
	private float rotation;
	public int woodType;

	public EntityStand(World par1World)
	{
		super(par1World);
		setSize(1f,2f);
		//noClip = false;
		ignoreFrustumCheck = true;
		armor = new ItemStack[defaultArmorLength];
		equipable = new ItemStack[defaultEquipableLength];

		//this.setArmor(0, new ItemStack(TFCItems.SteelBoots,1,0));
	}

	public EntityStand(World par1World, float rotation, int type){
		this(par1World);
		this.rotation = rotation;
		this.woodType = type;
	}

	@Override
	protected void updateAITasks()
	{
		++this.entityAge;
	}

	@Override
	public boolean isEntityInvulnerable(){
		return false;//true;
	}

	@Override
	protected void updateEntityActionState()
	{
		this.moveStrafing = 0.0F;
		this.moveForward = 0.0F;
		this.despawnEntity();
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		int start = 20;

		for(int i = 0; i < defaultArmorLength;i++){
			this.dataWatcher.addObjectByDataType(start+i, 5);
		}
		for(int i = 0; i < defaultEquipableLength;i++){
			this.dataWatcher.addObjectByDataType(start+i+defaultArmorLength,5);
		}
		this.dataWatcher.addObject(start + EntityStand.defaultEquipableLength + EntityStand.defaultArmorLength, new Float(1));
		this.dataWatcher.addObject(start + EntityStand.defaultEquipableLength + EntityStand.defaultArmorLength + 1, Integer.valueOf(0));
	}

	@Override
	public boolean canBePushed(){
		return true;
	}
	
	@Override
	public boolean canDespawn(){
		return false;
	}

	@Override
	public void onLivingUpdate(){
		super.onLivingUpdate();
		syncData();
	}

	public void syncData()
	{
		if(dataWatcher!= null)
		{
			if(this.worldObj.isRemote)
			{
				int start = 20;
				for(int i = 0; i < defaultArmorLength;i++){
					armor[i] = this.dataWatcher.getWatchableObjectItemStack(start+i);
				}
				for(int i = 0; i < defaultEquipableLength;i++){
					equipable[i] = this.dataWatcher.getWatchableObjectItemStack(start+i+defaultArmorLength);
				}
				rotation = this.dataWatcher.getWatchableObjectFloat(start + EntityStand.defaultEquipableLength + EntityStand.defaultArmorLength);
				woodType = this.dataWatcher.getWatchableObjectInt(start + EntityStand.defaultEquipableLength + EntityStand.defaultArmorLength + 1);
			}
			else
			{
				int start = 20;

				for(int i = 0; i < defaultArmorLength;i++){
					this.dataWatcher.updateObject(start+i, armor[i]);
				}
				for(int i = 0; i < defaultEquipableLength;i++){
					this.dataWatcher.updateObject(start+i+defaultArmorLength, equipable[i]);
				}

				this.dataWatcher.updateObject(start + EntityStand.defaultEquipableLength + EntityStand.defaultArmorLength, rotation);
				this.dataWatcher.updateObject(start + EntityStand.defaultEquipableLength + EntityStand.defaultArmorLength + 1, woodType);
			}
		}
	}

	@Override
	public void onUpdate()
	{
		if(this.worldObj.getEntitiesWithinAABB(getClass(), this.boundingBox).size() >1){
			this.setDead();
		}

		double tempX, tempZ; 	//temp x,y,z
		tempX = this.posX;
		//t_y = this.posY;
		tempZ = this.posZ;
		super.onUpdate();
		if(this.worldObj.isRemote)
			setSize(0.125f,2f);
		else
			setSize(0.1F,2);

		this.setLocationAndAngles(tempX, this.posY, tempZ, this.rotation, 0F);
		//this.setPositionAndRotation(t_x, this.posY, t_z, this.rotation, 0F);
		this.setRotation(rotation, 0);
		this.renderYawOffset = rotation;
		this.field_70741_aB = rotation;
		this.field_70764_aw = rotation;
		this.isCollidedHorizontally = false;
		this.limbSwing = 0;
		this.limbSwingAmount = 0;
		this.newRotationYaw = rotation;
		this.swingProgress = 0;
		this.swingProgressInt = 0;
		//this.rotationYaw = rotation;
		//this.getLookHelper().setLookPosition(this.posX + lookX, this.posY + (double)this.getEyeHeight(), this.posZ + lookZ, 10.0F, (float)this.getVerticalFaceSpeed());
		//this.getNavigator().tryMoveToXYZ(this.posX + lookX, this.posY, this.posZ + lookZ, 0.5);
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		if(!worldObj.isRemote){
			for(int i = 0; i < armor.length; i++){
				if(armor[i]!=null){
					ItemStack is = new ItemStack(armor[i].getItem(), 1, armor[i].getItemDamage());
					this.entityDropItem(is, 0);
				}
			}
			for(int i = 0; i < equipable.length; i++){
				if(equipable[i]!=null){
					ItemStack is = new ItemStack(equipable[i].getItem(), 1, equipable[i].getItemDamage());
					this.entityDropItem(is, 0);
				}
			}
			Block blockToDrop = woodType < 16? TFCBlocks.armorStand : TFCBlocks.armorStand2;
			this.entityDropItem(new ItemStack(blockToDrop,1,woodType%16), 0);
		}
	}

	/**
	 * Imported from EntityLivingBase because this does not exist on the server apparently
	 */
	private Vec3 getPlayerLook(EntityLivingBase entity, float mult)
	{
		float f1;
		float f2;
		float f3;
		float f4;

		if (mult == 1.0F)
		{
			f1 = MathHelper.cos(-entity.rotationYaw * 0.017453292F - (float)Math.PI);
			f2 = MathHelper.sin(-entity.rotationYaw * 0.017453292F - (float)Math.PI);
			f3 = -MathHelper.cos(-entity.rotationPitch * 0.017453292F);
			f4 = MathHelper.sin(-entity.rotationPitch * 0.017453292F);
			return Vec3.createVectorHelper(f2 * f3, f4, f1 * f3);
		}
		else
		{
			f1 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * mult;
			f2 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * mult;
			f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
			f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
			float f5 = -MathHelper.cos(-f1 * 0.017453292F);
			float f6 = MathHelper.sin(-f1 * 0.017453292F);
			return Vec3.createVectorHelper(f4 * f5, f6, f3 * f5);
		}
	}

	@Override
	public boolean interact(EntityPlayer ep){
		if(!worldObj.isRemote){
			ItemStack is = ep.getCurrentEquippedItem();
			if(is!= null){
				if(is.getItem() instanceof ItemTFCArmor){
					ItemTFCArmor tempArmor = (ItemTFCArmor)is.getItem();
					int slot = tempArmor.getBodyPart();
					if(this.armor[slot] == null){
						this.setArmor(slot,is);
						is.stackSize--;
					}
					ep.setCurrentItemOrArmor(0, is);
				}
			}
			else {
				Vec3 hitVec = getPlayerLook(ep, 1.0f);
				double angleTan = hitVec.yCoord / Math.sqrt(hitVec.xCoord * hitVec.xCoord + hitVec.zCoord * hitVec.zCoord);

				double xzDist = Math.sqrt(Math.pow(ep.posX - this.posX,2) + Math.pow(ep.posZ - this.posZ, 2));
				double yLevel = angleTan * xzDist + ep.eyeHeight + ep.posY;
				double y = yLevel - this.posY;

				int slot = -1;
				if(y >= 0 && y < 0.3){
					slot = 0;
				}
				else if(y >=0.3 && y < 1){
					slot = 1;
				}
				else if(y >= 1 && y < 1.4){
					slot = 2;
				}
				else if(y >= 1.4 && y < 2){
					slot = 3;
				}


				if(slot != -1){
					ItemStack i = armor[slot];
					armor[slot] = null;
					giveItemToPlayer(worldObj,ep,i);
				}
			}
		}
		return true;
	}

	public void giveItemToPlayer(World world, EntityPlayer ep, ItemStack is){
		if(world != null && ep != null && is != null){
			is.stackSize = 1;
			EntityItem ei = new EntityItem(world,ep.posX,ep.posY,ep.posZ,is);
			world.spawnEntityInWorld(ei);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
		//posX = nbttagcompound.getDouble("X");
		//posY = nbttagcompound.getDouble("Y");
		//posZ = nbttagcompound.getDouble("Z");
		rotation = nbttagcompound.getFloat("Rotation");
		woodType = nbttagcompound.getInteger("Wood");

		NBTTagList nbttaglist;
		int i;

		if (nbttagcompound.hasKey("Armor",9))
		{
			nbttaglist = nbttagcompound.getTagList("Armor",10);

			for (i = 0; i < EntityStand.defaultArmorLength; ++i)
			{
				this.armor[i] = ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i));
			}
		}

		if (nbttagcompound.hasKey("Equipable",9))
		{
			nbttaglist = nbttagcompound.getTagList("Equipable",10);

			for (i = 0; i < defaultEquipableLength; ++i)
			{
				this.equipable[i] = ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i));
			}
		}
	}

	public float getRotation(){
		return rotation;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
		//nbttagcompound.setDouble("X", posX);
		//nbttagcompound.setDouble("Y", posY);
		//nbttagcompound.setDouble("Z", posZ);

		nbttagcompound.setFloat("Rotation", rotation);
		nbttagcompound.setInteger("Wood",woodType);

		NBTTagList nbttaglist = new NBTTagList();
		NBTTagCompound nbttagcompound1;

		for (int i = 0; i < EntityStand.defaultArmorLength; ++i)
		{
			nbttagcompound1 = new NBTTagCompound();

			if (this.armor[i] != null)
			{
				this.armor[i].writeToNBT(nbttagcompound1);
			}

			nbttaglist.appendTag(nbttagcompound1);
		}

		nbttagcompound.setTag("Armor", nbttaglist);

		nbttaglist = new NBTTagList();
		for (int i = 0; i < defaultEquipableLength; ++i)
		{
			nbttagcompound1 = new NBTTagCompound();

			if (this.equipable[i] != null)
			{
				this.equipable[i].writeToNBT(nbttagcompound1);
			}

			nbttaglist.appendTag(nbttagcompound1);
		}

		nbttagcompound.setTag("Equipable", nbttaglist);
	}

	@Override
	public ItemStack getHeldItem()
	{
		return null;
	}

	public ItemStack getEquipableInSlot(int i)
	{

		if(equipable != null && defaultEquipableLength > i){
			return equipable[i];
		}
		return null;
	}

	public ItemStack getArmorInSlot(int i)
	{
		if(armor != null && defaultArmorLength > i){
			return armor[i];
		}
		return null;
	}

	public void setArmor(int i, ItemStack itemstack)
	{
		if(armor != null && defaultArmorLength > i){
			armor[i] = itemstack;
			//return true;
		}
		//return false;
	}

	public void setEquipable(int i, ItemStack itemstack)
	{
		if(equipable != null && defaultEquipableLength > i){
			equipable[i] = itemstack;
			//return true;
		}
		//return false;
	}
}
