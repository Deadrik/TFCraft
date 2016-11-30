package com.bioxx.tfc.Items;

import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISmeltable;
import com.bioxx.tfc.api.Metal;
import net.minecraft.item.ItemStack;

public class ItemAlloy extends ItemOreSmall implements ISmeltable
{
    private short metalAmount;

    public ItemAlloy()
    {
        super();
        setFolder("metal/");
        setCreativeTab(Cre.TECHNODE_TAB);
        this.setWeight(EnumWeight.MEDIUM);
        this.setSize(EnumSize.TINY);
        metaNames = Global.ALLOYS_ALL;
        metalAmount = 10;
    }

    public ItemAlloy setMetaNames(String[] itemNames)
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
            case 0: return Global.BISMUTHBRONZE;
            case 1: return Global.BLACKBRONZE;
            case 2: return Global.BLACKSTEEL;
            case 3: return Global.BLUESTEEL;
            case 4: return Global.BRASS;
            case 5: return Global.BRONZE;
            case 6: return Global.HCBLACKSTEEL;
            case 7: return Global.HCBLUESTEEL;
            case 8: return Global.HCREDSTEEL;
            case 9: return Global.REDSTEEL;
            case 10: return Global.ROSEGOLD;
            case 11: return Global.STERLINGSILVER;
            case 12: return Global.WEAKSTEEL;
            case 13: return Global.WEAKBLUESTEEL;
            case 14: return Global.WEAKREDSTEEL;
            case 15: return Global.ELECTRUM;
            case 16: return Global.CUPRONICKEL;

        }
        return null;
    }

    @Override
    public short getMetalReturnAmount(ItemStack is) { return metalAmount; }

    @Override
    public boolean isSmeltable(ItemStack is) { return true; }

    @Override
    public EnumTier getSmeltTier(ItemStack is) { return EnumTier.TierI; }