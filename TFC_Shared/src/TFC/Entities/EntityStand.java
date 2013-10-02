package TFC.Entities;

import TFC.TileEntities.TEStand;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityStand extends EntityLivingBase
{
	public TEStand standTE;
	public EntityStand(World par1World)
	{
		super(par1World);
		setSize(1f,2f);
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
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
		
	}

	@Override
	public ItemStack getHeldItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getCurrentItemOrArmor(int i) {
		return standTE.getStackInSlot(i);
	}

	@Override
	public void setCurrentItemOrArmor(int i, ItemStack itemstack) {
		standTE.setInventorySlotContents(i, itemstack);
		
	}

	@Override
	public ItemStack[] getLastActiveItems() {
		// TODO Auto-generated method stub
		return null;
	}

}
