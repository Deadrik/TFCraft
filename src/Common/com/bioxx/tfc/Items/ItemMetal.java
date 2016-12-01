package com.bioxx.tfc.Items;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISmeltable;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFCOptions;
import net.minecraft.item.ItemStack;

public class ItemMetal extends ItemOreSmall implements ISmeltable
{
    private short metalAmount;

    public ItemMetal()
    {
        super();
        setFolder("ingots/");
        setCreativeTab(TFCTabs.TFC_MATERIALS);
        this.setWeight(EnumWeight.MEDIUM);
        this.setSize(EnumSize.TINY);
        metaNames = Global.METAL_ALLOY_ALL;
        metalAmount = 10;
    }

    public ItemMetal setMetaNames(String[] itemNames)
    {
        metaNames = itemNames.clone();
        this.hasSubtypes = true;
        return this;
    }

    @Override
    public Metal getMetalType(ItemStack is)
    {
        int dam = is.getItemDamage();
        switch(dam)
        {
            case 0: return Global.BISMUTH;
            case 1: return Global.COPPER;
            case 2: return Global.GOLD;
            case 3: return Global.WROUGHTIRON;
            case 4: return Global.LEAD;
            case 5: return Global.NICKEL;
            case 6: return Global.PIGIRON;
            case 7: return Global.PLATINUM;
            case 8: return Global.SILVER;
            case 9: return Global.STEEL;
            case 10: return Global.TIN;
            case 11: return Global.ZINC;
            case 12: return Global.OSMIUM;
            case 13: if(TFCOptions.enableAluminumSmelting) { return Global.ALUMINUM; } else { return null; }
            case 14: return Global.TUNGSTEN;
            case 15: return Global.BISMUTHBRONZE;
            case 16: return Global.BLACKBRONZE;
            case 17: return Global.BLACKSTEEL;
            case 18: return Global.BLUESTEEL;
            case 19: return Global.BRASS;
            case 20: return Global.BRONZE;
            case 21: return Global.HCBLACKSTEEL;
            case 22: return Global.HCBLUESTEEL;
            case 23: return Global.HCREDSTEEL;
            case 24: return Global.REDSTEEL;
            case 25: return Global.ROSEGOLD;
            case 26: return Global.STERLINGSILVER;
            case 27: return Global.WEAKSTEEL;
            case 28: return Global.WEAKBLUESTEEL;
            case 29: return Global.WEAKREDSTEEL;
            case 30: return Global.ELECTRUM;
            case 31: return Global.CUPRONICKEL;
        }
        return null;
    }

    @Override
    public short getMetalReturnAmount(ItemStack is) { return metalAmount; }

    @Override
    public boolean isSmeltable(ItemStack is) { return true; }

    @Override
    public EnumTier getSmeltTier(ItemStack is)
    {
        int dam = is.getItemDamage();
        switch(dam) {
            case 0: return EnumTier.TierI;
            case 1: return EnumTier.TierI;
            case 2: return EnumTier.TierI;
            case 3: return EnumTier.TierI;
            case 4: return EnumTier.TierI;
            case 5: return EnumTier.TierI;
            case 6: return EnumTier.TierI;
            case 7: return EnumTier.TierI;
            case 8: return EnumTier.TierI;
            case 9: return EnumTier.TierI;
            case 10: return EnumTier.TierI;
            case 11: return EnumTier.TierI;
            case 12: return EnumTier.TierI;
            case 13: return EnumTier.TierI;
            case 14: return EnumTier.TierI;
            case 15: return EnumTier.TierI;
            case 16: return EnumTier.TierI;
            case 17: return EnumTier.TierI;
            case 18: return EnumTier.TierI;
            case 19: return EnumTier.TierI;
            case 20: return EnumTier.TierI;
            case 21: return EnumTier.TierX;
            case 22: return EnumTier.TierX;
            case 23: return EnumTier.TierX;
            case 24: return EnumTier.TierI;
            case 25: return EnumTier.TierI;
            case 26: return EnumTier.TierI;
            case 27: return EnumTier.TierI;
            case 28: return EnumTier.TierI;
            case 29: return EnumTier.TierI;
            case 30: return EnumTier.TierI;
            case 31: return EnumTier.TierI;
        }

        return EnumTier.TierX;
    }
}
