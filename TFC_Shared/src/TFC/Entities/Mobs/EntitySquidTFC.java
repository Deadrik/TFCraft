package TFC.Entities.Mobs;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.TFCItems;

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
		return this.posY > 128.0D && this.posY < 145.0D && this.worldObj.checkNoEntityCollision(this.boundingBox);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(400);//MaxHealth
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
		{
			this.entityDropItem(new ItemStack(Item.dyePowder, 1, 0), 0.0F);
		}
		this.dropItem(TFCItems.CalamariRaw.itemID,((2+rand.nextInt(5))));
	}
}
