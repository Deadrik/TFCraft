package TFC.Entities;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityFallingStone extends EntityFallingBlock implements IEntityAdditionalSpawnData
{
	// Block field_145811_e is private, therefore it's not possible at the moment
	// to extend EntityFallingBlock class. metadata field_145814_a is public.
	public EntityFallingStone(World par1World)
	{
		super(par1World);
		this.field_145813_c/*shouldDropItem*/ = true;
		this.func_145806_a/*setIsAnvil*/(true);
	}

	public EntityFallingStone(World par1World, double par2, double par4, double par6, Block par8, int par9)
	{
		super(par1World, par2, par4, par6, par8, par9);
		this.func_145806_a/*setIsAnvil*/(true);
//		this.field_145811_e = par8;
		this.field_145814_a = par9;
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
	public void writeSpawnData(ByteBuf buffer)
	{
//		buffer.writeInt(Block.getIdFromBlock(this.field_145811_e));
		buffer.writeInt(this.field_145814_a);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData)
	{
//		this.field_145811_e = Block.getBlockById(additionalData.readInt());
		this.field_145814_a = additionalData.readInt();
	}
}
