package com.bioxx.tfc.Blocks;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.Constant.Global;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockAlloyMetal extends BlockMetal
{
    public BlockAlloyMetal(Material material)
    {
        super(Material.iron);
        setCreativeTab(TFCTabs.TFC_MATERIALS);
        metalNames = new String[Global.ALLOYS_USABLE.length];
        System.arraycopy(Global.ALLOYS_USABLE, 0, metalNames, 0, Global.ALLOYS_USABLE.length);
        icons = new IIcon[metalNames.length];
    }
}
