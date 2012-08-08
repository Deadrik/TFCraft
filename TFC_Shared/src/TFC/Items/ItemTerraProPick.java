package TFC.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import TFC.Blocks.BlockTerraOre;
import TFC.Blocks.BlockTerraOre2;
import TFC.Blocks.BlockTerraOre3;
import TFC.Core.Helper;
import TFC.Core.TFCSettings;
import TFC.Core.Vector3f;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC;
import net.minecraft.src.mod_TFC;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerraProPick extends Item implements ITextureProvider
{
    List vecArray;

    public ItemTerraProPick(int i) 
    {
        super(i);
        maxStackSize = 1;
    }

    @Override
    public String getTextureFile()
    {
        return "/bioxx/terratools.png";
    }

    public void addInformation(ItemStack is, List arraylist) 
    {
        if(TFCSettings.enableDebugMode)
            arraylist.add("Damage: "+is.getItemDamage());
    }
    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x0, int y0, int z0, int l)
    {
        if(!world.isRemote)
        {
            MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(entityplayer, world);
            if(objectMouseOver == null) {
                return false;
            }		
            int x = objectMouseOver.blockX;
            int y = objectMouseOver.blockY;
            int z = objectMouseOver.blockZ;
            int side = objectMouseOver.sideHit;

            ArrayList oreArray = new ArrayList<String>();
            ArrayList oreNumArray = new ArrayList<Integer>();
            
            boolean isOre = false;
            
            int id = world.getBlockId(x, y, z);
            if(id == mod_TFC.terraOre.blockID)
            {
                isOre = true;
                int meta = world.getBlockMetadata(x, y, z);
                oreArray.add(BlockTerraOre.getItemNameDamage(((BlockTerraOre)mod_TFC.terraOre).damageDropped(meta)));
            }
            else if(id == mod_TFC.terraOre2.blockID)
            {
                isOre = true;
                int meta = world.getBlockMetadata(x, y, z);
                oreArray.add(BlockTerraOre2.getItemNameDamage(((BlockTerraOre2)mod_TFC.terraOre2).damageDropped(meta)));
            }
            else if(id == mod_TFC.terraOre3.blockID)
            {
                isOre = true;
                int meta = world.getBlockMetadata(x, y, z);
                oreArray.add(BlockTerraOre3.getItemNameDamage(((BlockTerraOre3)mod_TFC.terraOre3).damageDropped(meta)));
            }
            //sides XN(0), XP(1), YN(2), YP(3), ZN(4), ZP(5);
            
            for (int i = -12; i < 12 && !isOre; i++)
            {
                for (int j = -12; j < 12; j++)
                {
                    for (int k = -12; k < 12; k++)
                    {
                        int oreid = world.getBlockId(x+i, y+k, z+j);

                        ItemStack is;
                        if(oreid == mod_TFC.terraOre.blockID)
                        {
                            int meta = world.getBlockMetadata(x+i, y+k, z+j);

                            if(!oreArray.contains(BlockTerraOre.getItemNameDamage(((BlockTerraOre)mod_TFC.terraOre).damageDropped(meta))))
                            {
                                oreArray.add(BlockTerraOre.getItemNameDamage(((BlockTerraOre)mod_TFC.terraOre).damageDropped(meta)));
                                oreNumArray.add(1);
                            }
                            else
                            {
                                int index = oreArray.indexOf(BlockTerraOre.getItemNameDamage(((BlockTerraOre)mod_TFC.terraOre).damageDropped(meta)));
                                oreNumArray.set(index, (Integer)oreNumArray.toArray()[index]+1);
                            }


                        }
                        else if(oreid == mod_TFC.terraOre2.blockID)
                        {
                            int meta = world.getBlockMetadata(x+i, y+k, z+j);

                            if(meta != 6)
                            {
                                if(!oreArray.contains(BlockTerraOre2.getItemNameDamage(((BlockTerraOre2)mod_TFC.terraOre2).damageDropped(meta))))
                                {
                                    oreArray.add(BlockTerraOre2.getItemNameDamage(((BlockTerraOre2)mod_TFC.terraOre2).damageDropped(meta)));
                                    oreNumArray.add(1);
                                }
                                else
                                {
                                    int index = oreArray.indexOf(BlockTerraOre2.getItemNameDamage(((BlockTerraOre2)mod_TFC.terraOre2).damageDropped(meta)));
                                    oreNumArray.set(index, (Integer)oreNumArray.toArray()[index]+1);
                                }
                            }
                        }
                        else if(oreid == mod_TFC.terraOre3.blockID)
                        {
                            int meta = world.getBlockMetadata(x+i, y+k, z+j);

                            if(!oreArray.contains(BlockTerraOre3.getItemNameDamage(((BlockTerraOre3)mod_TFC.terraOre3).damageDropped(meta))))
                            {
                                oreArray.add(BlockTerraOre3.getItemNameDamage(((BlockTerraOre3)mod_TFC.terraOre3).damageDropped(meta)));
                                oreNumArray.add(1);
                            }
                            else
                            {
                                int index = oreArray.indexOf(BlockTerraOre3.getItemNameDamage(((BlockTerraOre3)mod_TFC.terraOre3).damageDropped(meta)));
                                oreNumArray.set(index, (Integer)oreNumArray.toArray()[index]+1);
                            }
                        }
                    }
                }
            }

            Random random = new Random((x*z)+y);
            if(oreArray.toArray().length > 0 && !isOre && random.nextInt(100) < 60)
            {
                int rand = random.nextInt(oreArray.toArray().length);
                if((Integer)oreNumArray.toArray()[rand] < 10) {
                    entityplayer.addChatMessage("Found Traces of " + oreArray.toArray()[rand]);
                } else if((Integer)oreNumArray.toArray()[rand] < 20) {
                    entityplayer.addChatMessage("Found a small sample of " + oreArray.toArray()[rand]);
                } else if((Integer)oreNumArray.toArray()[rand] < 40) {
                    entityplayer.addChatMessage("Found a medium sample of " + oreArray.toArray()[rand]);
                } else if((Integer)oreNumArray.toArray()[rand] < 80) {
                    entityplayer.addChatMessage("Found a large sample of " + oreArray.toArray()[rand]);
                } else {
                    entityplayer.addChatMessage("Found a very large sample of " + oreArray.toArray()[rand]);
                }
            }
            else if(isOre)
            {
                entityplayer.addChatMessage("Found " + oreArray.toArray()[0]);
            }
            else 
            {
                entityplayer.addChatMessage("Found nothing of interest.");
            }

            itemstack.setItemDamage(itemstack.getItemDamage()+1);
            if(itemstack.getItemDamage() >= itemstack.getMaxDamage())
                itemstack.stackSize = 0;

            return true;
        }
        return false;
    }
}
