package com.bioxx.tfc.Items.ItemBlocks;

import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Metal;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemMetalBlock2 extends ItemMetalBlock {
    public ItemMetalBlock2 (Block b)
    {
        super(b);
        metaNames = new String[Global.METAL_ALL.length-16];
        System.arraycopy(Global.METAL_ALL, 16, metaNames, 0, Global.METAL_ALL.length-16);
        setFolder("metal/");
    }
    @Override
    public Metal getMetalType(ItemStack is)
    {
        int dam = is.getItemDamage();
        switch(dam)
        {
            case 0: return Global.SILVER;
            case 1: return Global.STEEL;
            case 2: return Global.STERLINGSILVER;
            case 3: return Global.TIN;
            case 4: return Global.ZINC;
        }
        return null;
    }
}
