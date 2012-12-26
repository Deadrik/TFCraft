package TFC.Core;

import java.util.Random;

import TFC.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

public class HeatIndex
{
    public float specificHeat;
    public float meltTemp;
    public float boilTemp;

    private ItemStack output;
    private int outputMin;
    private int outputMax;

    private ItemStack morph;
    public ItemStack input;

    public HeatIndex(ItemStack in, float sh, float melt, float boil, ItemStack out)
    {
        input = in;
        specificHeat = sh;
        meltTemp = melt;
        boilTemp = boil;
        outputMin = 0;
        outputMax = 0;
        output = out;
    }

    public HeatIndex(ItemStack in, HeatRaw raw, ItemStack out)
    {
        input = in;
        specificHeat = raw.specificHeat;
        meltTemp = raw.meltTemp;
        boilTemp = raw.boilTemp;
        outputMin = 0;
        outputMax = 0;
        output = out;
    }

    public Item getOutputItem()
    {
        if(output!= null)
            return output.getItem();
        else return null;
    }

    public int getOutputDamage()
    {
        if(output!= null)
            return output.getItemDamage();
        else return 0;
    }

    public HeatIndex setMinMax(int min, int max)
    {
        outputMin = min;
        outputMax = max;
        return this;
    }

    public HeatIndex setMorph(ItemStack is)
    {
        morph = is;
        return this;
    }

    public ItemStack getMorph()
    {
        return morph;
    }

    public ItemStack getOutput(Random R)
    {
        int rand = 0;
        if(outputMax - outputMin > 0) 
        {
            rand = outputMin + R.nextInt(outputMax - outputMin);
            return new ItemStack(getOutputItem(),output.stackSize, 100-rand);
        }
        return new ItemStack(getOutputItem(),output.stackSize, 0);
    }

    public boolean matches(ItemStack is)
    {
        if(is != null)
        {
            boolean b = is.getItem().getHasSubtypes();
            if(is.getItem() != input.getItem())
                return false;
            else if(is.getItem().getHasSubtypes() && 
                    (input.getItemDamage() != -1 && 
                    is.getItemDamage() != input.getItemDamage()))
                return false;
        }
        else return false;
        return true;
    }
}
