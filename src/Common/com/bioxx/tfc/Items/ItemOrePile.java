package com.bioxx.tfc.Items;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFCOptions;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemOrePile extends ItemOre
{
    private short metalAmount;

    public ItemOrePile()
    {
        super();
        this.setWeight(EnumWeight.MEDIUM);
        this.setSize(EnumSize.VERYSMALL);
        metaNames = Global.ORE_METAL_ALL;
        metalAmount = 100;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List list)
    {
        for(int i = 0; i < Global.ORE_METAL_ALL.length; i++)
            list.add(new ItemStack(this, 1, i));
    }

    @Override
    public void registerIcons(IIconRegister registerer)
    {
        metaIcons = new IIcon[Global.ORE_METAL_ALL.length];
        for(int i = 0; i < Global.ORE_METAL_ALL.length; i++)
        {
            metaIcons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder+metaNames[i] + " Ore Pile");
        }
    }
    @Override
    public Metal getMetalType(ItemStack is)
    {
        int dam = is.getItemDamage();
        switch(dam)
        {
            case 0: return Global.COPPER;
            case 1: return Global.GOLD;
            case 2: return Global.PLATINUM;
            case 3: return Global.PIGIRON;
            case 4: return Global.SILVER;
            case 5: return Global.TIN;
            case 6: return Global.LEAD;
            case 7: return Global.BISMUTH;
            case 8: return Global.NICKEL;
            case 9: return Global.COPPER;
            case 10: return Global.PIGIRON;
            case 11: return Global.PIGIRON;
            case 12: return Global.ZINC;
            case 13: return Global.COPPER;
            case 14: return Global.OSMIUM;
            case 15: return Global.ALUMINUM;
            case 16: return Global.TUNGSTEN;
            case 17: return Global.TUNGSTEN;
        }
        return null;
    }

    @Override
    public boolean isSmeltable(ItemStack is)
    {
        switch(is.getItemDamage())
        {
            case 0: return true;
            case 1: return true;
            case 2: return true;
            case 3: return true;
            case 4: return true;
            case 5: return true;
            case 6: return true;
            case 7: return true;
            case 8: return true;
            case 9: return true;
            case 10: return true;
            case 11: return true;
            case 12: return true;
            case 13: return true;
            case 14: return true;
            case 15: if(TFCOptions.enableAluminumSmelting) { return true; } else { return false; }
            case 16: return true;
            case 17: return true;
            default:
                return false;
        }
    }

    @Override
    public EnumTier getSmeltTier(ItemStack is)
    {
        int dam = is.getItemDamage();
        switch(dam)
        {
            case 0: return EnumTier.TierI;
            case 1: return EnumTier.TierI;
            case 2: return EnumTier.TierIV;
            case 3: return EnumTier.TierIII;
            case 4: return EnumTier.TierI;
            case 5: return EnumTier.TierI;
            case 6: return EnumTier.TierI;
            case 7: return EnumTier.TierI;
            case 8: return EnumTier.TierIII;
            case 9: return EnumTier.TierI;
            case 10: return EnumTier.TierIII;
            case 11: return EnumTier.TierIII;
            case 12: return EnumTier.TierI;
            case 13: return EnumTier.TierI;
            case 14: return EnumTier.TierIV;
            case 15: return EnumTier.TierIII;
            case 16: return EnumTier.TierIV;
            case 17: return EnumTier.TierIV;
        }
        return EnumTier.TierX;
    }
    @Override
    public short getMetalReturnAmount(ItemStack is) { return metalAmount; }

}