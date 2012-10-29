package TFC.Items;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import TFC.*;
import TFC.Entities.Mobs.EntitySheepTFC;

import net.minecraft.src.Block;
import net.minecraft.src.BlockCloth;
import net.minecraft.src.BlockDirectional;
import net.minecraft.src.BlockLog;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class ItemDyeCustom extends ItemTerra
{
	/** List of dye color names */
    public static final String[] dyeColorNames = new String[] {"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};
    public static final int[] dyeColors = new int[] {1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 2651799, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320};

    public ItemDyeCustom(int par1)
    {
        super(par1,"/gui/items.png");
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }

    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value
     */
    public int getIconFromDamage(int par1)
    {
        int var2 = MathHelper.clamp_int(par1, 0, 15);
        return this.iconIndex + var2 % 8 * 16 + var2 / 8;
    }

    public String getItemNameIS(ItemStack par1ItemStack)
    {
        int var2 = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 15);
        return super.getItemName() + "." + dyeColorNames[var2];
    }

    public boolean tryPlaceIntoWorld(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (!par2EntityPlayer.func_82247_a(par4, par5, par6, par7, par1ItemStack))
        {
            return false;
        }
        else
        {
            int var11;
            int var12;

            if (par1ItemStack.getItemDamage() == 15)
            {
                var11 = par3World.getBlockId(par4, par5, par6);

                BonemealEvent event = new BonemealEvent(par2EntityPlayer, par3World, var11, par4, par5, par6);
                if (MinecraftForge.EVENT_BUS.post(event))
                {
                    return false;
                }

                if (event.getResult() == Result.ALLOW)
                {
                   if (!par3World.isRemote)
                    {
                        par1ItemStack.stackSize--;
                    }
                    return true;
                }

//                if (var11 == Block.sapling.blockID)
//                {
//                    if (!par3World.isRemote)
//                    {
//                        ((BlockSapling)Block.sapling).growTree(par3World, par4, par5, par6, par3World.rand);
//                        --par1ItemStack.stackSize;
//                    }
//
//                    return true;
//                }
//
//                if (var11 == Block.mushroomBrown.blockID || var11 == Block.mushroomRed.blockID)
//                {
//                    if (!par3World.isRemote && ((BlockMushroom)Block.blocksList[var11]).fertilizeMushroom(par3World, par4, par5, par6, par3World.rand))
//                    {
//                        --par1ItemStack.stackSize;
//                    }
//
//                    return true;
//                }
//
//                if (var11 == Block.melonStem.blockID || var11 == Block.pumpkinStem.blockID)
//                {
//                    if (par3World.getBlockMetadata(par4, par5, par6) == 7)
//                    {
//                        return false;
//                    }
//
//                    if (!par3World.isRemote)
//                    {
//                        ((BlockStem)Block.blocksList[var11]).fertilizeStem(par3World, par4, par5, par6);
//                        --par1ItemStack.stackSize;
//                    }
//
//                    return true;
//                }
//
//                if (var11 == Block.crops.blockID)
//                {
//                    if (par3World.getBlockMetadata(par4, par5, par6) == 7)
//                    {
//                        return false;
//                    }
//
//                    if (!par3World.isRemote)
//                    {
//                        ((BlockCrops)Block.crops).fertilize(par3World, par4, par5, par6);
//                        --par1ItemStack.stackSize;
//                    }
//
//                    return true;
//                }

                if (var11 == Block.cocoaPlant.blockID)
                {
                    if (!par3World.isRemote)
                    {
                        par3World.setBlockMetadataWithNotify(par4, par5, par6, 8 | BlockDirectional.getDirection(par3World.getBlockMetadata(par4, par5, par6)));
                        --par1ItemStack.stackSize;
                    }

                    return true;
                }

                if (var11 == Block.grass.blockID)
                {
                    if (!par3World.isRemote)
                    {
                        --par1ItemStack.stackSize;
                        label135:

                        for (var12 = 0; var12 < 128; ++var12)
                        {
                            int var13 = par4;
                            int var14 = par5 + 1;
                            int var15 = par6;

                            for (int var16 = 0; var16 < var12 / 16; ++var16)
                            {
                                var13 += itemRand.nextInt(3) - 1;
                                var14 += (itemRand.nextInt(3) - 1) * itemRand.nextInt(3) / 2;
                                var15 += itemRand.nextInt(3) - 1;

                                if (par3World.getBlockId(var13, var14 - 1, var15) != Block.grass.blockID || par3World.isBlockNormalCube(var13, var14, var15))
                                {
                                    continue label135;
                                }
                            }

                            if (par3World.getBlockId(var13, var14, var15) == 0)
                            {
                                if (itemRand.nextInt(10) != 0)
                                {
                                    if (Block.tallGrass.canBlockStay(par3World, var13, var14, var15))
                                    {
                                        par3World.setBlockAndMetadataWithNotify(var13, var14, var15, Block.tallGrass.blockID, 1);
                                    }
                                }
                                else
                                {
                                    ForgeHooks.plantGrass(par3World, var13, var14, var15);
                                }
                            }
                        }
                    }

                    return true;
                }
            }
            else if (par1ItemStack.getItemDamage() == 3)
            {
                var11 = par3World.getBlockId(par4, par5, par6);
                var12 = par3World.getBlockMetadata(par4, par5, par6);

                if (var11 == Block.wood.blockID && BlockLog.limitToValidMetadata(var12) == 3)
                {
                    if (par7 == 0)
                    {
                        return false;
                    }

                    if (par7 == 1)
                    {
                        return false;
                    }

                    if (par7 == 2)
                    {
                        --par6;
                    }

                    if (par7 == 3)
                    {
                        ++par6;
                    }

                    if (par7 == 4)
                    {
                        --par4;
                    }

                    if (par7 == 5)
                    {
                        ++par4;
                    }

                    if (par3World.isAirBlock(par4, par5, par6))
                    {
                        par3World.setBlockWithNotify(par4, par5, par6, Block.cocoaPlant.blockID);

                        if (par3World.getBlockId(par4, par5, par6) == Block.cocoaPlant.blockID)
                        {
                            Block.blocksList[Block.cocoaPlant.blockID].updateBlockMetadata(par3World, par4, par5, par6, par7, par8, par9, par10);
                        }

                        if (!par2EntityPlayer.capabilities.isCreativeMode)
                        {
                            --par1ItemStack.stackSize;
                        }
                    }

                    return true;
                }
            }

            return false;
        }
    }

    /**
     * die sheep, place saddles, etc ...
     */
    public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving)
    {
        if (par2EntityLiving instanceof EntitySheep)
        {
            EntitySheep var3 = (EntitySheep)par2EntityLiving;
            int var4 = BlockCloth.getBlockFromDye(par1ItemStack.getItemDamage());

            if (!var3.getSheared() && var3.getFleeceColor() != var4)
            {
                var3.setFleeceColor(var4);
                --par1ItemStack.stackSize;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 16; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
}
