package com.bioxx.tfc.Items;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISmeltable;
import com.bioxx.tfc.api.Metal;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemNugget extends ItemTerra implements ISmeltable
{
    private short metalAmount;

    public ItemNugget()
    {
        super();
        setMaxDamage(0);
        setHasSubtypes(true);
        metaNames = new String[]{
                "Bismuth", "Bismuth Bronze", "Black Bronze", "Black Steel", "Blue Steel", "Brass", "Bronze", "Copper", "Gold",
                "Wrought Iron", "Lead", "Nickel", "Pig Iron", "Platinum",
                "Red Steel", "Rose Gold", "Silver", "Steel", "Sterling Silver", "Tin", "Zinc", "Electrum", "Cupronickel"};
        setFolder("ingots/");
        setCreativeTab(TFCTabs.TFC_MATERIALS);
        this.setWeight(EnumWeight.MEDIUM);
        this.setSize(EnumSize.TINY);
        metalAmount = 10;

    }

    @Override
    public void registerIcons(IIconRegister registerer)
    {
        metaIcons = new IIcon[metaNames.length];
        for(int i = 0; i < metaNames.length; i++)
        {
            metaIcons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[i] + " Nugget");
        }
    }

    @Override
    public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
    {
        if(getMetalType(is) != null)
        {
            if (TFC_Core.showShiftInformation())
            {
                arraylist.add(TFC_Core.translate("gui.units") + ": " + getMetalReturnAmount(is));
            }
            else
            {
                arraylist.add(TFC_Core.translate("gui.ShowHelp"));
            }
        }
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
            case 16: return Global.SILVER;
            case 17: return Global.STEEL;
            case 18: return Global.STERLINGSILVER;
            case 19: return Global.TIN;
            case 20: return Global.ZINC;
            case 21: return Global.ELECTRUM;
            case 22: return Global.CUPRONICKEL;
        }
        return null;
    }

    @Override
    public short getMetalReturnAmount(ItemStack is)
    { return metalAmount; }

    @Override
    public boolean isSmeltable(ItemStack is)
    { return true; }

    @Override
    public EnumTier getSmeltTier(ItemStack is)
    { return EnumTier.TierI; }

}

