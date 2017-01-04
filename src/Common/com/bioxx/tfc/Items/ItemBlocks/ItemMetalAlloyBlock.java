package com.bioxx.tfc.Items.ItemBlocks;

import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Metal;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemMetalAlloyBlock extends ItemMetalBlock {
    public ItemMetalAlloyBlock(Block b)
    {
        super(b);
        metaNames = new String[Global.ALLOYS_USABLE.length];
        System.arraycopy(Global.ALLOYS_USABLE, 0, metaNames, 0, Global.ALLOYS_USABLE.length);
        setFolder("metal/");
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
            case 6: return Global.REDSTEEL;
            case 7: return Global.ROSEGOLD;
            case 8: return Global.STERLINGSILVER;
            case 9: return Global.ELECTRUM;
            case 10: return Global.CUPRONICKEL;
        }
        return null;
    }
}
