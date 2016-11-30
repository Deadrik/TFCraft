package com.bioxx.tfc.Blocks;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Items.Tools.ItemHammer;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Tools.IToolChisel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class BlockAlloyMetal extends BlockMetal
{
    public BlockAlloyMetal(Material material)
    {
        super(Material.iron);
        setCreativeTab(TFCTabs.TFC_MATERIALS);
        metalNames = new String[Global.ALLOYS_ALL.length];
        System.arraycopy(Global.ALLOYS_ALL, 0, metalNames, 0, Global.METAL_ALL.length);
        icons = new IIcon[metalNames.length];
    }
}
