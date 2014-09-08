package com.bioxx.tfc.Entities;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.InventoryPlayerTFC;
import com.bioxx.tfc.Items.ItemTFCArmor;
import com.bioxx.tfc.TileEntities.TEStand;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityStand extends EntityLiving
{
	private static int defaultArmorLength = 4;
	private static int defaultEquipableLength = TFC_Core.getExtraEquipInventorySize();
	private ItemStack[] armor;
	private ItemStack[] equipable;
	private float rotation = 0F;
	
	public EntityStand(World par1World)
	{
		super(par1World);
		setSize(1f,2f);
		this.setHealth(1);
		//noClip = false;
		ignoreFrustumCheck = true;
		armor = new ItemStack[defaultArmorLength];
		equipable = new ItemStack[defaultEquipableLength];
		
		//this.setArmor(0, new ItemStack(TFCItems.SteelBoots,1,0));
	}
	
	public EntityStand(World par1World, float rotation){
		this(par1World);
		this.rotation = rotation;
	}
	
	@Override
	protected void updateAITasks()
    {
        ++this.entityAge;
    }
	
	@Override
	protected void updateEntityActionState()
    {
        this.moveStrafing = 0.0F;
        this.moveForward = 0.0F;
        this.despawnEntity();
    }

	@Override
	public void setHealth(float par1)
	{
		this.dataWatcher.updateObject(6, Float.valueOf(MathHelper.clamp_float(par1, 0.0F, 1)));
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		int start = 45;
		
		for(int i = 0; i < defaultArmorLength;i++){
			this.dataWatcher.addObjectByDataType(start+i, 5);
		}
		for(int i = 0; i < defaultEquipableLength;i++){
			this.dataWatcher.addObjectByDataType(start+i+defaultArmorLength,5);
		}
	}
	
	@Override
	public boolean canBePushed(){
		return true;
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
			if(!this.worldObj.isRemote)
			{
				int start = 45;
				for(int i = 0; i < defaultArmorLength;i++){
					armor[i] = this.dataWatcher.getWatchableObjectItemStack(start+i);
				}
				for(int i = 0; i < defaultEquipableLength;i++){
					equipable[i] = this.dataWatcher.getWatchableObjectItemStack(start+i+defaultArmorLength);
				}
			}
			else
			{
				int start = 45;
				
				for(int i = 0; i < defaultArmorLength;i++){
					this.dataWatcher.updateObject(start+i, armor[i]);
				}
				for(int i = 0; i < defaultEquipableLength;i++){
					this.dataWatcher.updateObject(start+i+defaultArmorLength, equipable[i]);
				}
			}
		}
	}
	
	@Override
	public void onUpdate()
	{
		if(this.rotationYaw != 0.0 && this.rotationYaw != 90.0 && this.rotationYaw != 180.0 && this.rotationYaw != 270.0){
			System.out.println(this.rotationYaw);
		}
		if(this.worldObj.getEntitiesWithinAABB(getClass(), this.boundingBox).size() >1){
			this.setDead();
		}
		
		double t_x, t_z; 	//temp x,y,z
		t_x = this.posX;
		//t_y = this.posY;
		t_z = this.posZ;
		super.onUpdate();
		if(this.worldObj.isRemote)
			setSize(0.125f,2f);
		else
			setSize(0.6F,0);
		
		this.setLocationAndAngles(t_x, this.posY, t_z, this.rotation, 0F);
		//this.setPositionAndRotation(t_x, this.posY, t_z, this.rotation, 0F);
		this.setRotation(rotation, 0);
		//this.rotationYaw = rotation;
		//this.getLookHelper().setLookPosition(this.posX + lookX, this.posY + (double)this.getEyeHeight(), this.posZ + lookZ, 10.0F, (float)this.getVerticalFaceSpeed());
		//this.getNavigator().tryMoveToXYZ(this.posX + lookX, this.posY, this.posZ + lookZ, 0.5);
	}

	@Override
	public boolean interact(EntityPlayer ep){
		ItemStack is = ep.getCurrentEquippedItem();
		if(is!= null){
			if(is.getItem() instanceof ItemTFCArmor){
				ItemTFCArmor tempArmor = (ItemTFCArmor)is.getItem();
				int slot = tempArmor.getBodyPart();
				this.setArmor(slot,is);
				is.stackSize--;
				ep.setCurrentItemOrArmor(0, is);
			}
		}
		else{
			System.out.println(this.worldObj.isRemote?"client":"server");
			for(int i =0;i<defaultArmorLength;i++){
				System.out.println(armor[i]);
			}
		}
		return true;
	}
	
	@Override
	public boolean isEntityInvulnerable()
	{
		return true;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
		//posX = nbttagcompound.getDouble("X");
		//posY = nbttagcompound.getDouble("Y");
		//posZ = nbttagcompound.getDouble("Z");
		rotation = nbttagcompound.getFloat("Rotation");
		
		 NBTTagList nbttaglist;
	        int i;

	        if (nbttagcompound.hasKey("Armor",9))
	        {
	            nbttaglist = nbttagcompound.getTagList("Armor",10);

	            for (i = 0; i < this.defaultArmorLength; ++i)
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

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
		//nbttagcompound.setDouble("X", posX);
		//nbttagcompound.setDouble("Y", posY);
		//nbttagcompound.setDouble("Z", posZ);
		
		
		NBTTagList nbttaglist = new NBTTagList();
        NBTTagCompound nbttagcompound1;

        for (int i = 0; i < this.defaultArmorLength; ++i)
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
