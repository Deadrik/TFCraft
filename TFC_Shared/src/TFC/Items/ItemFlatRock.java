package TFC.Items;

import TFC.Core.Helper;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC;

public class ItemFlatRock extends ItemTerra
{

    public ItemFlatRock(int id) 
    {
        super(id);
        this.hasSubtypes = true;
        this.setMaxDamage(0);
        this.maxStackSize = 25;
    }
    public ItemFlatRock(int id, String tex) 
    {
        super(id);
        texture = tex;

    }

    public static String[] blockNames = {"Granite", "Diorite", "Gabbro", 
        "Siltstone", "Mudstone", "Shale", "Claystone", "Rock Salt", "Limestone", "Conglomerate", "Dolomite", "Chert", 
        "Chalk", "Rhyolite", "Basalt", "Andesite", "Dacite", 
        "Quartzite", "Slate", "Phyllite", "Schist", "Gneiss", "Marble"};

    @Override
    public String getItemNameIS(ItemStack itemstack) 
    {
        String s = new StringBuilder().append(super.getItemName()).append(".").append(blockNames[itemstack.getItemDamage()]).toString();
        return s;
    }
    
    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
    {
        //if(par1ItemStack.stackSize == 0)
        ((EntityPlayer)par3Entity).inventory.setInventorySlotContents(par4, null);
    }
    
    public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player)
    {
        return false;
    }

    public int getIconFromDamage(int i)
    {
        switch(i)
        {
            case 0:
            case 1:
            case 2:
                return i;//igin
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                return 64+(i-3);//sed
            case 13:
            case 14:
            case 15:
            case 16:
                return 3+(i-13);//igex
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                return 74+(i-17);//mm
        }
        return i;
    }
}
