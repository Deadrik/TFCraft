package TFC.Entities;

import java.io.DataOutput;

import TFC.TileEntities.TEStand;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityStand extends EntityLiving
{
	public TEStand standTE;
	public EntityStand(World par1World)
	{
		super(par1World);
		setSize(0.5f,2f);
		this.setHealth(1);
	}

	public EntityStand(World par1World, TEStand TE){
		this(par1World);
		standTE = TE;
		posX = TE.xCoord;
		posY = TE.yCoord;
		posZ = TE.zCoord;
	}

	@Override
	public void setHealth(float par1){
		this.dataWatcher.updateObject(6, Float.valueOf(MathHelper.clamp_float(par1, 0.0F, 1)));
	}

	@Override
	protected void entityInit() {
		super.entityInit();

	}

	@Override
	public void moveEntity(double d1,double d2,double d3){

	}

	@Override
	public void onUpdate(){
		if(standTE == null){
			standTE = (TEStand)worldObj.getBlockTileEntity((int)posX, (int)posY, (int)posZ);
			if(standTE == null){
				setDead();
			}
		}
		this.rotationYaw = ((int)(((standTE.yaw-225)%360)/90))*90F;
		this.rotationYawHead = this.rotationYaw;
	}

	@Override
	public boolean isEntityInvulnerable()
	{
		return true;
	}
	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		posX = nbttagcompound.getDouble("X");
		posY = nbttagcompound.getDouble("Y");
		posZ = nbttagcompound.getDouble("Z");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setDouble("X", posX);
		nbttagcompound.setDouble("Y", posY);
		nbttagcompound.setDouble("Z", posZ);
	}

	@Override
	public ItemStack getHeldItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getCurrentItemOrArmor(int i) {
		if(standTE !=null){
			return standTE.getStackInSlot(i);
		}
		return null;
	}

	@Override
	public void setCurrentItemOrArmor(int i, ItemStack itemstack) {
		if(standTE!=null){
			standTE.setInventorySlotContents(i, itemstack);
		}
	}

	@Override
	public ItemStack[] getLastActiveItems() {
		// TODO Auto-generated method stub
		if(standTE!=null){
			return standTE.items;
		}
		return new ItemStack[5];
	}

}
