package TFC.Entities;

import TFC.Core.TFCSeasons;
import TFC.Core.TFCSettings;
import net.minecraft.src.*;

public class EntityPigTFC extends EntityAnimalTFC
{
	private EntityAIEatGrassTFC aiEatGrass = new EntityAIEatGrassTFC(this);
    public EntityPigTFC(World par1World)
    {
        super(par1World);
        this.texture = "/mob/pig.png";
        this.setSize(0.9F, 0.9F);
        this.getNavigator().setAvoidsWater(true);
        float var2 = 0.25F;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
        this.tasks.addTask(2, new EntityAIMateTFC(this, var2));
        this.tasks.addTask(3, new EntityAITempt(this, 0.25F, TFCItems.WheatGrain.shiftedIndex, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 0.28F));
        this.tasks.addTask(5, new EntityAIWander(this, var2));
        this.tasks.addTask(5, this.aiEatGrass);
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }
    public EntityPigTFC(World par1World,EntityAnimalTFC mother, float F_size)
	{
    	super(par1World,mother,F_size);
    	this.texture = "/mob/pig.png";
        this.setSize(0.9F, 0.9F);
        this.getNavigator().setAvoidsWater(true);
        float var2 = 0.25F;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
        this.tasks.addTask(2, new EntityAIMateTFC(this, var2));
        this.tasks.addTask(3, new EntityAITempt(this, 0.25F, TFCItems.WheatGrain.shiftedIndex, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 0.28F));
        this.tasks.addTask(5, new EntityAIWander(this, var2));
        this.tasks.addTask(5, this.aiEatGrass);
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
	}

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    public int getMaxHealth()
    {
        return 10;
    }
    
    public void onLivingUpdate ()
    {
        super.onLivingUpdate ();
        float t = (1.0F-(getGrowingAge()/(-24000*adultAge)));
        setSize(0.7F*t,0.7F*t);
        if(pregnant){
			if(TFCSeasons.getTotalTicks() >= conception + pregnancyTime*TFCSettings.dayLength){
				int i = rand.nextInt(5) + 8;
				for (int x = 0; x < i;x++){
				EntityPigTFC baby = new EntityPigTFC(worldObj, this,mateSizeMod);
				giveBirth(baby);
				}			
				pregnant = false;
			}
		}

    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Saddle", this.getSaddled());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setSaddled(par1NBTTagCompound.getBoolean("Saddle"));
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.pig";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.pig";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.pigdeath";
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer par1EntityPlayer)
    {
        if (super.interact(par1EntityPlayer))
        {
            return true;
        }
        else if (this.getSaddled() && !this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == par1EntityPlayer))
        {
            par1EntityPlayer.mountEntity(this);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return this.isBurning() ? Item.porkCooked.shiftedIndex : Item.porkRaw.shiftedIndex;
    }
    
    protected void dropFewItems(boolean par1, int par2)
    {
        int var3 = 1;
        int var4;

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(Item.leather.shiftedIndex, 5+this.rand.nextInt(5));
        }

        var3 = 1;

        for (var4 = 0; var4 < var3; ++var4)
        {
            if (this.isBurning())
            {
                
                this.dropItem(Item.porkCooked.shiftedIndex, (int) (this.size_mod *(10+this.rand.nextInt(8))));
            }
            else
            {
                this.dropItem(Item.porkRaw.shiftedIndex, (int) (this.size_mod *(10+this.rand.nextInt(8))));
            }
        }
    }

    /**
     * Returns true if the pig is saddled.
     */
    public boolean getSaddled()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    /**
     * Set or remove the saddle of the pig.
     */
    public void setSaddled(boolean par1)
    {
        if (par1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)1));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)0));
        }
    }

    /**
     * Called when a lightning bolt hits the entity.
     */
    public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt)
    {
        if (!this.worldObj.isRemote)
        {
            EntityPigZombie var2 = new EntityPigZombie(this.worldObj);
            var2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.worldObj.spawnEntityInWorld(var2);
            this.setDead();
        }
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void fall(float par1)
    {
        super.fall(par1);

        if (par1 > 5.0F && this.riddenByEntity instanceof EntityPlayer)
        {
            ((EntityPlayer)this.riddenByEntity).triggerAchievement(AchievementList.flyPig);
        }
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityAnimal spawnBabyAnimal(EntityAnimal par1EntityAnimal)
    {
        return new EntityPigTFC(this.worldObj);
    }
}
