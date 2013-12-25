package TFC.Entities.Mobs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.Core.TFC_MobData;

public class EntityPigZombieTFC extends EntityZombieTFC
{
	/** Above zero if this PigZombie is Angry. */
	private int angerLevel = 0;

	/** A random delay until this PigZombie next makes a sound. */
	private int randomSoundDelay = 0;

	public EntityPigZombieTFC(World par1World)
	{
		super(par1World);
	}


	/**
	 * Causes this PigZombie to become angry at the supplied Entity (which will be a player).
	 */
	private void becomeAngryAt(Entity par1Entity)
	{
		this.entityToAttack = par1Entity;
		this.angerLevel = 400 + this.rand.nextInt(400);
		this.randomSoundDelay = this.rand.nextInt(40);
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		int var3 = this.rand.nextInt(2 + par2);
		int var4;

		for (var4 = 0; var4 < var3; ++var4)
		{
			this.dropItem(Item.rottenFlesh.itemID, 1);
		}

		var3 = this.rand.nextInt(2 + par2);

		for (var4 = 0; var4 < var3; ++var4)
		{
			this.dropItem(Item.goldNugget.itemID, 1);
		}
	}
	@Override
	protected void dropRareDrop(int par1)
	{
		this.dropItem(Item.ingotGold.itemID, 1);
	}

	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected int getDropItemId()
	{
		return Item.rottenFlesh.itemID;
	}

	@Override
	protected void addRandomArmor()
	{
		this.setCurrentItemOrArmor(0, new ItemStack(Item.swordGold));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(TFC_MobData.PigZombieDamage);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(TFC_MobData.PigZombieHealth);//MaxHealth
	}
}
