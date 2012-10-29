package TFC.Entities.Mobs;

import java.util.ArrayList;
import java.util.Random;

import TFC.*;
import TFC.Core.TFC_Time;
import TFC.Core.TFC_Settings;
import TFC.Entities.EntityAnimalTFC;
import TFC.Entities.AI.EntityAIAvoidEntityTFC;
import TFC.Entities.AI.EntityAIEatGrassTFC;
import TFC.Entities.AI.EntityAIMateTFC;
import TFC.Entities.AI.EntityAIPanicTFC;

import net.minecraft.src.Block;
import net.minecraft.src.EntityAIFollowParent;
import net.minecraft.src.EntityAILookIdle;
import net.minecraft.src.EntityAIPanic;
import net.minecraft.src.EntityAISwimming;
import net.minecraft.src.EntityAITempt;
import net.minecraft.src.EntityAIWander;
import net.minecraft.src.EntityAIWatchClosest;
import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityDeer extends EntityAnimalTFC
{    

    /** The eat grass AI task for this mob. */
    private EntityAIEatGrassTFC aiEatGrass = new EntityAIEatGrassTFC(this);
    private boolean running;

    public EntityDeer(World par1World)
    {
        super(par1World);
        running = false;
        this.texture = "/mob/deer.png";
        this.setSize(0.9F, 1.3F);
        float var2 = 0.23F / 1.1F;
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
        this.tasks.addTask(2, new EntityAIMateTFC(this, var2));
        this.tasks.addTask(3, new EntityAIPanicTFC(this, var2*2, false, true));
        this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityPlayer.class, 12.0F, 0.5F, 0.7F));
        this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityWolfTFC.class, 8.0F, 0.5F, 0.7F));
        this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityBear.class, 16.0F, 0.25F, 0.3F));
        this.tasks.addTask(3, new EntityAITempt(this, 0.25F, Item.wheat.shiftedIndex, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 0.25F));
        this.tasks.addTask(5, this.aiEatGrass);
        //this.tasks.addTask(5, new EntityAIRutt(this, var2));
        this.tasks.addTask(6, new EntityAIWander(this, var2));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }
    public EntityDeer(World par1World,EntityAnimalTFC mother, float F_size)
	{
    	super(par1World,mother,F_size);
    	running = false;
        this.texture = "/mob/deer.png";
        this.setSize(0.9F, 1.3F);
        float var2 = 0.23F / 1.1F;
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
        this.tasks.addTask(2, new EntityAIMateTFC(this, var2));
        this.tasks.addTask(3, new EntityAIPanicTFC(this, var2*2, false, true));
        this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityPlayer.class, 12.0F, 0.5F, 0.7F));
        this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityWolfTFC.class, 8.0F, 0.5F, 0.7F));
        this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityBear.class, 16.0F, 0.25F, 0.3F));
        this.tasks.addTask(3, new EntityAITempt(this, 0.25F, Item.wheat.shiftedIndex, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 0.25F));
        this.tasks.addTask(5, this.aiEatGrass);
        //this.tasks.addTask(5, new EntityAIRutt(this, var2));
        this.tasks.addTask(6, new EntityAIWander(this, var2));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
	}
    
    /**
     * Returns true if the newer Entity AI code should be run
     */
    protected boolean isAIEnabled()
    {
        return true;
    }

    protected void updateAITasks()
    {
        super.updateAITasks();
    }
    
    public boolean getRunning(){
    	return running;
    }
    
    public void setRunning(boolean r){
    	running = r;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        int g = getGrowingAge();
        float t = (1.0F-(g/(TFC_Time.getYearRatio() * adultAge * -TFC_Settings.dayLength)));
        setSize(0.9F*t,0.9F*t);
        if(g <= (-12000*adultAge)){
        	this.texture = "/mob/deer_fawn.png";
        }
        else{
        	this.texture = "/mob/deer.png";
        }
        if(pregnant){
			if(TFC_Time.getTotalTicks() >= conception + TFC_Time.getYearRatio() * pregnancyTime * TFC_Settings.dayLength){
				EntityDeer baby = new EntityDeer(worldObj, this,mateSizeMod);
				giveBirth(baby);
				pregnant = false;
			}
		}

        super.onLivingUpdate();
    }

    public int getMaxHealth()
    {
        return 8;
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean par1, int par2)
    {
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return Block.cloth.blockID;
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer par1EntityPlayer)
    {
        return super.interact(par1EntityPlayer);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Sheared", this.getSheared());
        par1NBTTagCompound.setByte("Color", (byte)this.getFleeceColor());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setSheared(par1NBTTagCompound.getBoolean("Sheared"));
        this.setFleeceColor(par1NBTTagCompound.getByte("Color"));
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.sheep";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.sheep";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.sheep";
    }

    public int getFleeceColor()
    {
        return this.dataWatcher.getWatchableObjectByte(16) & 15;
    }

    public void setFleeceColor(int par1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 240 | par1 & 15)));
    }

    /**
     * returns true if a sheeps wool has been sheared
     */
    public boolean getSheared()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 16) != 0;
    }

    /**
     * make a sheep sheared if set to true
     */
    public void setSheared(boolean par1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (par1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 16)));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -17)));
        }
    }

    /**
     * This method is called when a sheep spawns in the world to select the color of sheep fleece.
     */
    public static int getRandomFleeceColor(Random par0Random)
    {
        int var1 = par0Random.nextInt(100);
        return var1 < 5 ? 15 : (var1 < 10 ? 7 : (var1 < 15 ? 8 : (var1 < 18 ? 12 : (par0Random.nextInt(500) == 0 ? 6 : 0))));
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityAnimal spawnBabyAnimal(EntityAnimal par1EntityAnimal)
    {
        EntityDeer var2 = (EntityDeer)par1EntityAnimal;
        EntityDeer var3 = new EntityDeer(this.worldObj);

        if (this.rand.nextBoolean())
        {
            var3.setFleeceColor(this.getFleeceColor());
        }
        else
        {
            var3.setFleeceColor(var2.getFleeceColor());
        }

        return var3;
    }

    /**
     * This function applies the benefits of growing back wool and faster growing up to the acting entity. (This
     * function is used in the AIEatGrass)
     */
    public void eatGrassBonus()
    {
        this.setSheared(false);

        if (this.isChild())
        {
            int var1 = this.getGrowingAge() + 1200;

            if (var1 > 0)
            {
                var1 = 0;
            }

            this.setGrowingAge(var1);
        }
    }

}
