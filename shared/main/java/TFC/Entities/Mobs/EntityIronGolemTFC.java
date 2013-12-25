package TFC.Entities.Mobs;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.Core.TFC_MobData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityIronGolemTFC extends EntityIronGolem
{
	private int attackTimer;


	public EntityIronGolemTFC(World par1World)
	{
		super(par1World);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(TFC_MobData.IronGolemDamage);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(TFC_MobData.IronGolemHealth);//MaxHealth
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if (this.attackTimer > 0)
		{
			--this.attackTimer;
		}

		if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7D && this.rand.nextInt(5) == 0)
		{
			int var1 = MathHelper.floor_double(this.posX);
			int var2 = MathHelper.floor_double(this.posY - 0.20000000298023224D - this.yOffset);
			int var3 = MathHelper.floor_double(this.posZ);
			int var4 = this.worldObj.getBlockId(var1, var2, var3);
			int meta = this.worldObj.getBlockMetadata(var1, var2, var3);
			// Fix for invisible grass texture
			if(var4 == TFCBlocks.Grass.blockID || var4 == TFCBlocks.Grass2.blockID
					|| var4 == TFCBlocks.ClayGrass.blockID || var4 == TFCBlocks.ClayGrass2.blockID
					|| var4 == TFCBlocks.PeatGrass.blockID
					|| var4 == TFCBlocks.DryGrass.blockID || var4 == TFCBlocks.DryGrass2.blockID)
			{
				var4 = TFCBlocks.Dirt.blockID;
				meta = 1;
			}

			if (var4 > 0)
			{
				this.worldObj.spawnParticle("tilecrack_" + var4 + "_" + meta, this.posX + (this.rand.nextFloat() - 0.5D) * this.width, this.boundingBox.minY + 0.1D, this.posZ + (this.rand.nextFloat() - 0.5D) * this.width, 4.0D * (this.rand.nextFloat() - 0.5D), 0.5D, (this.rand.nextFloat() - 0.5D) * 4.0D);
			}
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		this.attackTimer = 10;
		this.worldObj.setEntityState(this, (byte)4);
		boolean var2 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), TFC_MobData.IronGolemDamage + this.rand.nextInt(150));

		if (var2)
		{
			par1Entity.motionY += 0.4000000059604645D;
		}

		this.worldObj.playSoundAtEntity(this, "mob.irongolem.throw", 1.0F, 1.0F);
		return var2;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleHealthUpdate(byte par1)
	{
		super.handleHealthUpdate(par1);
		if (par1 == 4)
		{
			this.attackTimer = 10;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getAttackTimer()
	{
		return this.attackTimer;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		Random ran = new Random();
		int k = 3 + ran.nextInt(3);
		for (int l = 0; l < k; ++l) {
			this.dropItem(TFCItems.WroughtIronIngot.itemID, 1);
		}
	}

}
