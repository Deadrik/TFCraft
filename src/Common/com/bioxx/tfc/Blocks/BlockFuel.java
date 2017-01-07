package com.bioxx.tfc.Blocks;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;



public class BlockFuel extends BlockTerra {

    public BlockFuel() {
        super(Material.ground);
        this.setCreativeTab(TFCTabs.TFC_MATERIALS);
    }

    @Override
    public void registerBlockIcons(IIconRegister registerer)
    {
        this.blockIcon = registerer.registerIcon(Reference.MOD_ID + ":" + "Coke Block");
    }

    @Override
    public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta) {
        dropBlockAsItem(world, x, y, z, new ItemStack(this, 1, meta));
    }
}