package TFC.Entities.Mobs;

import TFC.*;
import TFC.Core.TFC_MobDamage;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import java.util.Calendar;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class EntityZombieTFC extends EntityZombie
{
    private int field_82234_d = 0;

    public EntityZombieTFC(World par1World)
    {
        super(par1World);
    }
    @Override
    public int getMaxHealth()
    {
        return 1000;
    }

    /**
     * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
     */
    @Override
    public int getTotalArmorValue()
    {
        int var1 = super.getTotalArmorValue() + 2;

        if (var1 > 20)
        {
            var1 = 20;
        }

        return var1;
    }

    @Override
    public int getAttackStrength(Entity par1Entity)
    {
        ItemStack var2 = this.getHeldItem();
        int var3 = TFC_MobDamage.ZombieDamage;

        if (var2 != null)
        {
            var3 += var2.getDamageVsEntity(this);
        }

        return var3;
    }
    
    @Override
    protected void dropRareDrop(int par1)
    {
        switch (this.rand.nextInt(3))
        {
            case 0:
                this.dropItem(TFCItems.WroughtIronIngot.shiftedIndex, 1);
                break;
            case 1:
                this.dropItem(TFCItems.Carrot.shiftedIndex, 1);
                break;
            case 2:
                this.dropItem(TFCItems.Potato.shiftedIndex, 1);
        }
    }

    @Override
    protected void func_82164_bB()
    {
        super.func_82164_bB();
        this.setCurrentItemOrArmor(1, null); 
        this.setCurrentItemOrArmor(2, null); 
        this.setCurrentItemOrArmor(3, null); 
        this.setCurrentItemOrArmor(4, null); 

        if (this.rand.nextFloat() < (this.worldObj.difficultySetting == 3 ? 0.05F : 0.01F))
        {
            int var1 = this.rand.nextInt(3);

            if (var1 == 0)
            {
                this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.BronzePick));
            }
            else
            {
                this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.BronzeShovel));
            }
        }
    }


    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte par1)
    {
        if (par1 == 16)
        {
            this.worldObj.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "mob.zombie.remedy", 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F);
        }
        else
        {
            super.handleHealthUpdate(par1);
        }
    }
}
