package TFC.Entities.Mobs;

import java.util.ArrayList;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.init.Items;
import net.minecraft.world.World;
import TFC.API.Entities.IAnimal;
import TFC.Core.TFC_Core;

public class EntityPheasantTFC extends EntityChickenTFC
{
	private final EntityAIEatGrass aiEatGrass = new EntityAIEatGrass(this);

	public EntityPheasantTFC(World par1World)
	{
		super(par1World);
	}

	public EntityPheasantTFC(World world, IAnimal mother,  ArrayList<Float> data)
	{
		super(world, mother, data);
	}

	@Override
	public void addAI()
	{

	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50);//MaxHealth
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		timeUntilNextEgg = 10000;
		super.onLivingUpdate();
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		float ageMod = TFC_Core.getPercentGrown(this);
		this.dropItem(Items.feather, (int)(ageMod * this.size_mod * (5 + this.rand.nextInt(10))));

		if(isAdult())
		{
			float foodWeight = ageMod * (this.size_mod * 40);//528 oz (33lbs) is the average yield of lamb after slaughter and processing
			TFC_Core.animalDropMeat(this, Items.chicken, foodWeight);
			this.dropItem(Items.bone, rand.nextInt(2) + 1);
		}
	}

	@Override
	public EntityPheasantTFC createChild(EntityAgeable entityageable)
	{
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(mateSizeMod);
		return new EntityPheasantTFC(worldObj, this, data);
	}

	@Override
	public boolean canMateWith(IAnimal animal) 
	{
		if(animal.getGender() != this.getGender() && animal.isAdult() && animal instanceof EntityPheasantTFC)
			return true;
		else
			return false;
	}

	@Override
	public EntityAgeable createChildTFC(EntityAgeable entityageable)
	{
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(entityageable.getEntityData().getFloat("MateSize"));
		return new EntityPheasantTFC(worldObj, this, data);
	}
}
