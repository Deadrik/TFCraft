package TFC.Entities;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityFallingStone extends EntityFallingBlock implements IEntityAdditionalSpawnData
{
	private Block block;
	private int meta;
	
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
		this.block = par8;
		this.meta = par1World.getBlockMetadata((int)par2, (int)par4, (int)par6);
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
		buffer.writeInt(Block.getIdFromBlock(this.block));
		buffer.writeInt(this.meta);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData)
	{
		this.block = Block.getBlockById(additionalData.readInt());
		this.meta = additionalData.readInt();
	}
}
