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

public class BlockMetal2 extends BlockMetal
{
    public BlockMetal2(Material material)
    {
        super(Material.iron);
        setCreativeTab(TFCTabs.TFC_MATERIALS);
        metalNames = new String[Global.METAL_ALL.length-16];
        System.arraycopy(Global.METAL_ALL, 16, metalNames, 0, Global.METAL_ALL.length-16);
        icons = new IIcon[metalNames.length];
    }
}
