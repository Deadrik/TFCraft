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

public class EntityCowTFC extends EntityAnimalTFC
{
	private EntityAIEatGrassTFC aiEatGrass = new EntityAIEatGrassTFC(this);
	
    public EntityCowTFC(World par1World)
    {
        super(par1World);
        this.texture = "/mob/cow.png";
        this.setSize(0.9F, 1.3F);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
        this.tasks.addTask(2, new EntityAIMateTFC(this, 0.2F));
        this.tasks.addTask(3, new EntityAITempt(this, 0.25F, TFCItems.WheatGrain.itemID, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 0.25F));
        this.tasks.addTask(5, this.aiEatGrass);
        this.tasks.addTask(6, new EntityAIWander(this, 0.2F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }
    
    public EntityCowTFC(World par1World,EntityAnimalTFC mother, float F_size)
	{
    	super(par1World,mother,F_size);
    	this.texture = "/mob/cow.png";
        this.setSize(0.9F, 1.3F);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
        this.tasks.addTask(2, new EntityAIMateTFC(this, 0.2F));
        this.tasks.addTask(3, new EntityAITempt(this, 0.25F, TFCItems.WheatGrain.itemID, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 0.25F));
        this.tasks.addTask(5, this.aiEatGrass);
        this.tasks.addTask(6, new EntityAIWander(this, 0.2F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
	}
    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        
        float t = (1.0F-(getGrowingAge()/(TFC_Time.getYearRatio() * adultAge * -TFC_Settings.dayLength)));
        if(pregnant){
			if(TFC_Time.getTotalTicks() >= conception + pregnancyTime*TFC_Settings.dayLength){
				EntityCowTFC baby = new EntityCowTFC(worldObj, this,mateSizeMod);
				giveBirth(baby);
				pregnant = false;
			}
		}

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

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    @Override
    protected String getLivingSound()
    {
        return "mob.cow.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected String getHurtSound()
    {
        return "mob.cow.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    protected String getDeathSound()
    {
        return "mob.cow.hurt";
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    @Override
    protected float getSoundVolume()
    {
        return 0.4F;
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    @Override
    protected int getDropItemId()
    {
        return Item.leather.itemID;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    @Override
    protected void dropFewItems(boolean par1, int par2)
    {
        int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);
        int var4;


            this.dropItem(TFCItems.Hide.itemID,1);

            if (this.isBurning())
            {
                
                this.dropItem(Item.beefCooked.itemID, (int) (this.size_mod *(15+this.rand.nextInt(10))));
            }
            else
            {
                this.dropItem(Item.beefRaw.itemID, (int) (this.size_mod *(15+this.rand.nextInt(10))));
            }

    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    @Override
    public boolean interact(EntityPlayer par1EntityPlayer)
    {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();

        if (var2 != null && var2.itemID == TFCItems.WoodenBucketEmpty.itemID)
        {
            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, new ItemStack(TFCItems.WoodenBucketMilk));
            return true;
        }
        else
        {
            return super.interact(par1EntityPlayer);
        }
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    @Override
    public void procreate(EntityAnimal par1EntityAnimal)
    {
        worldObj.spawnEntityInWorld(new EntityCowTFC(this.worldObj));
    }
}
