package TFC.Entities;

import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityFallingStone extends EntityFallingSand implements IEntityAdditionalSpawnData
{
	public EntityFallingStone(World par1World)
	{
		super(par1World);
		this.shouldDropItem = true;
		this.setIsAnvil(true);

	}

	public EntityFallingStone(World par1World, double par2, double par4, double par6, int par8, int par9)
	{
		super(par1World, par2, par4, par6, par8, par9);
		this.setIsAnvil(true);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setBoolean("HurtEntities", true);
		nbttagcompound.setFloat("FallHurtAmount", 100);
		nbttagcompound.setInteger("FallHurtMax", 2000);
	}

	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		data.writeInt(this.blockID);
		data.writeInt(this.metadata);
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		this.blockID = data.readInt();
		this.metadata = data.readInt();
	}
}
