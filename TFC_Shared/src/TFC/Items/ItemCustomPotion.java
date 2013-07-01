package TFC.Items;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import TFC.Core.TFC_Core;

public class ItemCustomPotion extends ItemPotion
{
    /** maps potion damage values to lists of effect names */
    private HashMap effectCache = new HashMap();
    private static final Map field_77835_b = new LinkedHashMap();

    public ItemCustomPotion(int par1)
    {
        super(par1);
    }

    
    @Override
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer player)
    {
        if (!player.capabilities.isCreativeMode)
        {
            --par1ItemStack.stackSize;
        }
        
        if (!par2World.isRemote)
        {
            List var4 = this.getEffects(par1ItemStack);

            if (var4 != null)
            {
                Iterator var5 = var4.iterator();

                while (var5.hasNext())
                {
                    PotionEffect var6 = (PotionEffect)var5.next();
                    player.addPotionEffect(new PotionEffect(var6));
                }
            }
            else
            {
            	TFC_Core.getPlayerFoodStats(player).restoreWater(player, 2400);
            }
        }

        if (!player.capabilities.isCreativeMode)
        {
            if (par1ItemStack.stackSize <= 0)
            {
                return new ItemStack(Item.glassBottle);
            }

            player.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
        }

        return par1ItemStack;
    }
    
    @Override
	public void registerIcons(IconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);
        Item.potion.registerIcons(par1IconRegister);
    }
}
