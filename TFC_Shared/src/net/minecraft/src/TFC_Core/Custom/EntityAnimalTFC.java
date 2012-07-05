package net.minecraft.src.TFC_Core.Custom;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.*;
import net.minecraft.src.EntityAnimal;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import net.minecraft.src.TFC_Core.TFCSeasons;

public class EntityAnimalTFC extends EntityAnimal
{
    private int inLove;
    public int sex;
    public long animalID;
    public boolean mateForLife;
    public EntityAnimalTFC mate;
    public EntityAnimalTFC parent;
    public float size_mod;
    private int pickUp;
    public int hunger;
    public int adultAge;
    public List < Integer > fooditems = new ArrayList < Integer > (0);

    public int breeding;

    protected long adultTime;
    protected long birthTime;

    public EntityAnimalTFC(World par1World)
    {
        super(par1World);
        animalID = TFCSeasons.getTotalTicks() + entityId;
        hunger = 168000;
        pickUp = 0;
        breeding = 0;
        mateForLife = false;
        parent = null;
        mate = null;
        sex = rand.nextInt (2);
        tasks.addTask (1, new EntityAIMoveTowardsFood (this, 0.4F, 20F));
        size_mod = (float) (((rand.nextInt (5) - 2) / 10f) + 1F) * (1.0F - 0.1F * sex);
        birthTime = TFCSeasons.getTotalTicks();
        adultTime = birthTime;
        adultAge = 90;
    }

    @Override
    public void setGrowingAge(int par1)
    {
        adultTime = worldObj.getWorldInfo().getWorldTime() - par1;
        dataWatcher.updateObject(12, Integer.valueOf(par1));
    }

    public void onLivingUpdate()
    {
        if (hunger > 168000)
        {
            hunger = 168000;
        }
        /**
         * This Cancels out the growingAge from EntityAgeable
         * */
        int var1 = this.getGrowingAge();       
        super.onLivingUpdate();
        this.setGrowingAge(var1);
        
        long i = worldObj.getWorldInfo().getWorldTime() - adultTime;

        if (i < 0)
        {
            i++;
            setGrowingAge((int)i);
        }
        else if (i > 0)
        {
            i--;
            setGrowingAge((int)i);
        }
        

        if (this.getGrowingAge() != 0)
        {
            this.inLove = 0;
        }

        if (this.inLove > 0)
        {
            --this.inLove;
            String heart = "heart";

            if (this.inLove % 10 == 0)
            {
                double var2 = this.rand.nextGaussian() * 0.02D;
                double var4 = this.rand.nextGaussian() * 0.02D;
                double var6 = this.rand.nextGaussian() * 0.02D;
                this.worldObj.spawnParticle(heart, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, var2, var4, var6);
            }
        }
        else
        {
            this.breeding = 0;
        }
        if (pickUp > 0)
        {
            pickUp--;
        }
        if (hunger > 144000 && rand.nextInt (100) == 0 && health < getMaxHealth ())
        {
            health++;
        }
        for(EntityItem ei : this.capturedDrops)
        {
            ItemStack item = ei.item;
            if (item != null)
            {
                if (hunger < 144000 && fooditems.contains (item.itemID))
                {
                    renderBrokenItemStack (item);
                    item = null;
                    hunger += 24000;
                    worldObj.playSoundAtEntity (this, "random.eat", 0.5F + 0.5F * (float) rand.nextInt (2), (rand.nextFloat () - rand.nextFloat ()) * 0.2F + 1.0F);
                }
            }
        }
        if (getHealth () > 0)
        {
            List list = worldObj.getEntitiesWithinAABBExcludingEntity (this, boundingBox.expand (1.0D, 0.0D, 1.0D));

            if (list != null)
            {
                for (int j = 0 ; j < list.size () ; j++)
                {
                    Entity entity = (Entity) list.get (j);

                    if (!entity.isDead)
                    {
                        //entity.onCollideWithAnimal (this);
                    }
                }
            }
        }
    }

    public boolean wantsItem (ItemStack itemstack1)
    {
        if (fooditems.contains (itemstack1.itemID) && hunger < 200000 && pickUp == 0)
        {
            ItemStack item = null;

            if(!capturedDrops.isEmpty())
                item = this.capturedDrops.get(0).item;

            if (item != null)
            {
                dropItem (item.itemID, 1);
            }
            item = itemstack1.copy ();
            if (item.stackSize > 1)
            {
                dropItem (item.itemID, item.stackSize - 1);
            }
            EntityItem ei = new EntityItem(this.worldObj);
            ei.item = item;
            capturedDrops.add(0, ei);
            pickUp = 600;
            return true;

        }
        return false;
    }

    @Override
    public int getMaxHealth()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT (NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT (par1NBTTagCompound);
        par1NBTTagCompound.setLong ("Animal ID", animalID);
        par1NBTTagCompound.setInteger ("Sex", sex);
        par1NBTTagCompound.setFloat ("Size Modifier", size_mod);
        par1NBTTagCompound.setInteger ("Hunger", hunger);
        if (mate != null)
        {
            par1NBTTagCompound.setLong ("Mate number", mate.animalID);
        }
        else
        {
            par1NBTTagCompound.setLong ("Mate number", -1);
        }
        if (isChild () && parent != null)
        {
            par1NBTTagCompound.setLong ("Parent", parent.animalID);
        }
        else
        {
            par1NBTTagCompound.setLong ("Parent", -1);
        }
        
        par1NBTTagCompound.setLong("AdultTime",adultTime);
        par1NBTTagCompound.setLong("BirthTime",birthTime);
    }


    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT (NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT (par1NBTTagCompound);
        animalID = par1NBTTagCompound.getLong ("Animal ID");
        sex = par1NBTTagCompound.getInteger ("Sex");
        size_mod = par1NBTTagCompound.getFloat ("Size Modifier");
        hunger = par1NBTTagCompound.getInteger ("Hunger");
        long i = par1NBTTagCompound.getLong ("Mate number");
        if (i != -1)
        {
            for (int j = 0 ; j < worldObj.loadedEntityList.size () ; j++)
            {
                Entity k = (Entity) worldObj.loadedEntityList.get (j);
                if (k instanceof EntityAnimalTFC)
                {
                    if (i == ((EntityAnimalTFC) k).animalID)
                    {
                        mate = (EntityAnimalTFC) k;
                        break;
                    }
                }
            }
        }
        if (isChild () && par1NBTTagCompound.getLong ("Parent") != -1)
        {
            i = par1NBTTagCompound.getLong ("Parent");
            for (int j = 0 ; j < worldObj.loadedEntityList.size () ; j++)
            {
                Entity k = (Entity) worldObj.loadedEntityList.get (j);
                if (k instanceof EntityAnimalTFC)
                {
                    if (i == ((EntityAnimalTFC) k).animalID)
                    {
                        parent = (EntityAnimalTFC) k;
                        break;
                    }
                }
            }
        }
        adultTime = par1NBTTagCompound.getLong("AdultTime");
        birthTime = par1NBTTagCompound.getLong("BirthTime");
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere ()
    {
        int i = MathHelper.floor_double (posX);
        int j = MathHelper.floor_double (boundingBox.minY);
        int k = MathHelper.floor_double (posZ);
        return worldObj.getBlockId (i, j - 1, k) == Block.grass.blockID && worldObj.getFullBlockLightValue (i, j, k) > 8 && super.getCanSpawnHere ();
    }

    public boolean func_48135_b (EntityAnimalTFC par1EntityAnimal)
    {
        if (par1EntityAnimal == this)
        {
            return false;
        }

        if (par1EntityAnimal.getClass () != getClass ())
        {
            return false;
        }
        else
        {
            return isInLove () && par1EntityAnimal.isInLove ();
        }
    }

    public EntityAnimal spawnBabyAnimal(EntityAnimal var1){
        return null;}

}
