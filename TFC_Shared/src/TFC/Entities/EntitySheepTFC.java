package TFC.Entities;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import TFC.Core.TFCSeasons;
import TFC.Core.TFCSettings;

import net.minecraft.src.*;
import net.minecraftforge.common.IShearable;

public class EntitySheepTFC extends EntityAnimalTFC implements IShearable
{
    /**
     * Holds the RGB table of the sheep colors - in OpenGL glColor3f values - used to render the sheep colored fleece.
     */
    public static final float[][] fleeceColorTable = new float[][] {{1.0F, 1.0F, 1.0F}, {0.95F, 0.7F, 0.2F}, {0.9F, 0.5F, 0.85F}, {0.6F, 0.7F, 0.95F}, {0.9F, 0.9F, 0.2F}, {0.5F, 0.8F, 0.1F}, {0.95F, 0.7F, 0.8F}, {0.3F, 0.3F, 0.3F}, {0.6F, 0.6F, 0.6F}, {0.3F, 0.6F, 0.7F}, {0.7F, 0.4F, 0.9F}, {0.2F, 0.4F, 0.8F}, {0.5F, 0.4F, 0.3F}, {0.4F, 0.5F, 0.2F}, {0.8F, 0.3F, 0.3F}, {0.1F, 0.1F, 0.1F}};

    /**
     * Used to control movement as well as wool regrowth. Set to 40 on handleHealthUpdate and counts down with each
     * tick.
     */
    private int sheepTimer;

    /** The eat grass AI task for this mob. */
    private EntityAIEatGrassTFC aiEatGrass = new EntityAIEatGrassTFC(this);

    public EntitySheepTFC(World par1World)
    {
        super(par1World);
        this.texture = "/mob/sheep.png";
        this.setSize(0.9F, 1.3F);
        float var2 = 0.23F;
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
        this.tasks.addTask(2, new EntityAIMateTFC(this, var2));
        this.tasks.addTask(3, new EntityAITempt(this, 0.25F, TFCItems.WheatGrain.shiftedIndex, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 0.25F));
        this.tasks.addTask(5, this.aiEatGrass);
        this.tasks.addTask(6, new EntityAIWander(this, var2));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }
    public EntitySheepTFC(World par1World,EntityAnimalTFC mother, float F_size)
	{
    	super(par1World,mother,F_size);
        this.texture = "/mob/sheep.png";
        this.setSize(0.9F, 1.3F);
        float var2 = 0.23F;
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
        this.tasks.addTask(2, new EntityAIMateTFC(this, var2));
        this.tasks.addTask(3, new EntityAITempt(this, 0.25F, TFCItems.WheatGrain.shiftedIndex, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 0.25F));
        this.tasks.addTask(5, this.aiEatGrass);
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
        this.sheepTimer = this.aiEatGrass.eatGrassTick;
        super.updateAITasks();
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (this.worldObj.isRemote)
        {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }
        
        float t = (1.0F-(getGrowingAge()/(-24000*adultAge)));
        setSize(0.9F*t,0.9F*t);
        if(pregnant){
			if(TFCSeasons.getTotalTicks() >= conception + pregnancyTime*TFCSettings.dayLength){
				int i = rand.nextInt(3) + 1;
				for (int x = 0; x<i;x++){
				EntitySheepTFC baby = new EntitySheepTFC(worldObj, this,mateSizeMod);
				giveBirth(baby);
				}
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
        if (!this.getSheared())
        {
            this.entityDropItem(new ItemStack(Block.cloth.blockID, 1, this.getFleeceColor()), 0.0F);
        }
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return Block.cloth.blockID;
    }
    
    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte par1)
    {
        if (par1 == 10)
        {
            this.sheepTimer = 40;
        }
        else
        {
            super.handleHealthUpdate(par1);
        }
    }
    
    public float func_44003_c(float par1)
    {
        return this.sheepTimer <= 0 ? 0.0F : (this.sheepTimer >= 4 && this.sheepTimer <= 36 ? 1.0F : (this.sheepTimer < 4 ? ((float)this.sheepTimer - par1) / 4.0F : -((float)(this.sheepTimer - 40) - par1) / 4.0F));
    }

    public float func_44002_d(float par1)
    {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36)
        {
            float var2 = ((float)(this.sheepTimer - 4) - par1) / 32.0F;
            return ((float)Math.PI / 5F) + ((float)Math.PI * 7F / 100F) * MathHelper.sin(var2 * 28.7F);
        }
        else
        {
            return this.sheepTimer > 0 ? ((float)Math.PI / 5F) : this.rotationPitch / (180F / (float)Math.PI);
        }
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
        EntitySheepTFC var2 = (EntitySheepTFC)par1EntityAnimal;
        EntitySheepTFC var3 = new EntitySheepTFC(this.worldObj);

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
    	super.eatGrassBonus();
        this.setSheared(false);
    }

    @Override
    public boolean isShearable(ItemStack item, World world, int X, int Y, int Z) 
    {
        return !getSheared() && !isChild();
    }

    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item, World world, int X, int Y, int Z, int fortune) 
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        setSheared(true);
        int i = 1 + rand.nextInt(3);
        for (int j = 0; j < i; j++)
        {
            ret.add(new ItemStack(Block.cloth.blockID, 1, getFleeceColor()));
        }
        return ret;
    }

}
