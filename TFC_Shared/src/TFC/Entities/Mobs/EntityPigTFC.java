package TFC.Entities.Mobs;

import TFC.*;
import TFC.Core.TFC_Time;
import TFC.Core.TFC_Settings;
import TFC.Entities.EntityAnimalTFC;
import TFC.Entities.AI.EntityAIEatGrassTFC;
import TFC.Entities.AI.EntityAIMateTFC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

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
        this.tasks.addTask(6, new EntityAIWander(this, var2));
        this.tasks.addTask(5, this.aiEatGrass);
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
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
        this.tasks.addTask(6, new EntityAIWander(this, var2));
        this.tasks.addTask(5, this.aiEatGrass);
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
	}

    /**
     * Returns true if the newer Entity AI code should be run
     */
    @Override
    public boolean isAIEnabled()
    {
        return true;
    }
    @Override
    public int getMaxHealth()
    {
        return 500;
    }
    @Override
    public void onLivingUpdate ()
    {
        super.onLivingUpdate ();
        float t = (1.0F-(getGrowingAge()/(TFC_Time.getYearRatio() * adultAge * -TFC_Settings.dayLength)));
        setSize(0.7F*t,0.7F*t);
        if(pregnant){
			if(TFC_Time.getTotalTicks() >= conception + pregnancyTime*TFC_Settings.dayLength){
				int i = rand.nextInt(5) + 8;
				for (int x = 0; x < i;x++){
				EntityPigTFC baby = new EntityPigTFC(worldObj, this,mateSizeMod);
				giveBirth(baby);
				}			
				pregnant = false;
			}
		}

    }
    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Saddle", this.getSaddled());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setSaddled(par1NBTTagCompound.getBoolean("Saddle"));
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    @Override
    protected String getLivingSound()
    {
        return "mob.pig.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected String getHurtSound()
    {
        return "mob.pig.say";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    protected String getDeathSound()
    {
        return "mob.pig.death";
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    @Override
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
    @Override
    protected int getDropItemId()
    {
        return this.isBurning() ? Item.porkCooked.shiftedIndex : Item.porkRaw.shiftedIndex;
    }
    @Override
    protected void dropFewItems(boolean par1, int par2)
    {
        int var3 = 1;
        int var4;

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(TFCItems.Hide.shiftedIndex,1);
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
    @Override
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
    @Override
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
    @Override
    public void procreate(EntityAnimal par1EntityAnimal)
    {
        worldObj.spawnEntityInWorld(new EntityPigTFC(this.worldObj));
    }
}
