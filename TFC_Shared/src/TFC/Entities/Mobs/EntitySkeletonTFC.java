package TFC.Entities.Mobs;

import java.util.Calendar;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;
import TFC.TFCItems;
import TFC.Entities.EntityArrowTFC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntitySkeletonTFC extends EntitySkeleton
{
    public EntitySkeletonTFC(World par1World)
    {
        super(par1World);
        this.func_94058_c("");
        this.func_94061_f(false);
    }
    
    @Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.writeEntityToNBT(par1NBTTagCompound);
    	par1NBTTagCompound.setBoolean("CustomNameVisible", false);
    	
    }
    @Override
    public int getMaxHealth()
    {
        return 1000;
    }
    
    @Override
	public String getEntityName()
    {
        return "";
    }
    
    @Override
    public void onEntityUpdate()
    {
    	super.onEntityUpdate();
    	
    	if (this.isBurning())
        {
            this.attackEntityFrom(DamageSource.onFire, 50);
        }
    }
    @Override
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        if (super.attackEntityAsMob(par1Entity))
        {
            if (this.getSkeletonType() == 1 && par1Entity instanceof EntityLiving)
            {
                ((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public int getAttackStrength(Entity par1Entity)
    {
        if (this.getSkeletonType() == 1)
        {
            ItemStack var2 = this.getHeldItem();
            int var3 = 200;

            if (var2 != null)
            {
                var3 += var2.getDamageVsEntity(this);
            }

            return var3;
        }
        else
        {
            return super.getAttackStrength(par1Entity);
        }
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    @Override
    protected int getDropItemId()
    {
        return Item.arrow.itemID;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    @Override
    protected void dropFewItems(boolean par1, int par2)
    {
        int var3;
        int var4;

        if (this.getSkeletonType() == 1)
        {
            var3 = this.rand.nextInt(3 + par2) - 1;

            for (var4 = 0; var4 < var3; ++var4)
            {
                this.dropItem(Item.coal.itemID, 1);
            }
        }
        else
        {
            var3 = this.rand.nextInt(3 + par2);

            for (var4 = 0; var4 < var3; ++var4)
            {
                this.dropItem(Item.arrow.itemID, 1);
            }
        }

        var3 = this.rand.nextInt(3 + par2);

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(Item.bone.itemID, 1);
        }
    }
    @Override
    protected void dropRareDrop(int par1)
    {
        if (this.getSkeletonType() == 1)
        {
            this.entityDropItem(new ItemStack(Item.skull.itemID, 1, 1), 0.0F);
        }
    }
    @Override
    protected void addRandomArmor()
    {
    	superAddRandomArmor();
        this.setCurrentItemOrArmor(0, new ItemStack(Item.itemsList[Item.bow.itemID]));
    }
    
    public static Item getArmorItemForSlot(int par0, int par1)
    {
        switch (par0)
        {
            case 4:
                if (par1 == 0)
                {
                    return Item.itemsList[Item.helmetLeather.itemID];
                }
                else if (par1 == 1)
                {
                    return TFCItems.CopperHelmet;
                }
                else if (par1 == 2)
                {
                    return TFCItems.BronzeHelmet;
                }
                else if (par1 == 3)
                {
                    return TFCItems.WroughtIronHelmet;
                }
                else if (par1 == 4)
                {
                    return TFCItems.SteelHelmet;
                }
            case 3:
                if (par1 == 0)
                {
                	return Item.itemsList[Item.plateLeather.itemID];
                }
                else if (par1 == 1)
                {
                    return TFCItems.CopperChestplate;
                }
                else if (par1 == 2)
                {
                    return TFCItems.BronzeChestplate;
                }
                else if (par1 == 3)
                {
                    return TFCItems.WroughtIronChestplate;
                }
                else if (par1 == 4)
                {
                    return TFCItems.SteelChestplate;
                }
            case 2:
                if (par1 == 0)
                {
                	return Item.itemsList[Item.legsLeather.itemID];
                }
                else if (par1 == 1)
                {
                    return TFCItems.CopperGreaves;
                }
                else if (par1 == 2)
                {
                    return TFCItems.BronzeGreaves;
                }
                else if (par1 == 3)
                {
                    return TFCItems.WroughtIronGreaves;
                }
                else if (par1 == 4)
                {
                    return TFCItems.SteelGreaves;
                }
            case 1:
                if (par1 == 0)
                {
                	return Item.itemsList[Item.bootsLeather.itemID];
                }
                else if (par1 == 1)
                {
                    return TFCItems.CopperBoots;
                }
                else if (par1 == 2)
                {
                    return TFCItems.BronzeBoots;
                }
                else if (par1 == 3)
                {
                    return TFCItems.WroughtIronBoots;
                }
                else if (par1 == 4)
                {
                    return TFCItems.SteelBoots;
                }
            default:
                return null;
        }
    }
    
    private static final float[] armorProbability = new float[] {0.0F, 0.5F, 0.10F, 0.15F};
    private void superAddRandomArmor()
    {
        if (this.rand.nextFloat() < armorProbability[this.worldObj.difficultySetting])
        {
            int i = this.rand.nextInt(2);
            float f = this.worldObj.difficultySetting == 3 ? 0.1F : 0.25F;

            if (this.rand.nextFloat() < 0.095F)
            {
                ++i;
            }

            if (this.rand.nextFloat() < 0.095F)
            {
                ++i;
            }

            if (this.rand.nextFloat() < 0.095F)
            {
                ++i;
            }

            for (int j = 3; j >= 0; --j)
            {
                ItemStack itemstack = this.getCurrentArmor(j);

                if (j < 3 && this.rand.nextFloat() < f)
                {
                    break;
                }

                if (itemstack == null)
                {
                    Item item = getArmorItemForSlot(j + 1, i);

                    if (item != null)
                    {
                        this.setCurrentItemOrArmor(j + 1, new ItemStack(item));
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getTexture()
    {
        return this.getSkeletonType() == 1 ? "/mob/skeleton_wither.png" : super.getTexture();
    }
    @Override
    public void initCreature()
    {
        if (this.worldObj.provider instanceof WorldProviderHell && this.getRNG().nextInt(5) > 0)
        {
            this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
            this.setSkeletonType(1);
            this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.StoneKnife));
        }
        else
        {
            this.tasks.addTask(4, new EntityAIArrowAttack(this, this.moveSpeed, 60, 10.0F));
            this.addRandomArmor();
            this.func_82162_bC();
        }

        this.func_94061_f(this.rand.nextFloat() < pickUpLootProability[this.worldObj.difficultySetting]);

        if (this.getCurrentItemOrArmor(4) == null)
        {
            Calendar var1 = this.worldObj.getCurrentDate();

            if (var1.get(2) + 1 == 10 && var1.get(5) == 31 && this.rand.nextFloat() < 0.25F)
            {
                this.setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1F ? Block.pumpkinLantern : Block.pumpkin));
                this.equipmentDropChances[4] = 0.0F;
            }
        }
    }
    @Override
    public void attackEntityWithRangedAttack(EntityLiving par1EntityLiving,float par2)
    {
    	EntityArrowTFC var2 = new EntityArrowTFC(this.worldObj, this, par1EntityLiving, 1.6F, 12.0F);
        int var3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
        int var4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
        var2.setDamage(100 * 2.0F + this.rand.nextGaussian() * 0.25D + this.worldObj.difficultySetting * 0.11F);

        
        if (var3 > 0)
        {
            var2.setDamage(var2.getDamage() + var3 * 0.5D + 0.5D);
        }

        if (var4 > 0)
        {
            var2.setKnockbackStrength(var4);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0 || this.getSkeletonType() == 1)
        {
            var2.setFire(100);
        }

        this.worldObj.playSoundAtEntity(this, "random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.worldObj.spawnEntityInWorld(var2);
    }
}
