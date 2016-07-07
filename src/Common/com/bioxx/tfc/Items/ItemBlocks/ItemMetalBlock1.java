package com.bioxx.tfc.Items.ItemBlocks;

import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Metal;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemMetalBlock1 extends ItemMetalBlock
{
    public ItemMetalBlock1(Block b)
    {
        super(b);
        metaNames = new String[16];
        System.arraycopy(Global.METAL_ALL, 0, metaNames, 0, 16);
        setFolder("metal/");
    }

    @Override
    public Metal getMetalType(ItemStack is)
    {
        int dam = is.getItemDamage();
        switch(dam)
        {
            case 0: return Global.BISMUTH;
            case 1: return Global.BISMUTHBRONZE;
            case 2: return Global.BLACKBRONZE;
            case 3: return Global.BLACKSTEEL;
            case 4: return Global.BLUESTEEL;
            case 5: return Global.BRASS;
            case 6: return Global.BRONZE;
            case 7: return Global.COPPER;
            case 8: return Global.GOLD;
            case 9: return Global.WROUGHTIRON;
            case 10: return Global.LEAD;
            case 11: return Global.NICKEL;
            case 12: return Global.PIGIRON;
            case 13: return Global.PLATINUM;
            case 14: return Global.REDSTEEL;
            case 15: return Global.ROSEGOLD;
        }
        return null;
    }
}
