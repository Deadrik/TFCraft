package TFC.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import TFC.Blocks.BlockOre;
import TFC.Blocks.BlockOre2;
import TFC.Blocks.BlockOre3;
import TFC.Core.Helper;
import TFC.Core.TFC_Settings;
import TFC.Core.Vector3f;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.TFCBlocks;
import net.minecraft.src.World;

public class ItemProPick extends ItemTerra
{
    List vecArray;

    public ItemProPick(int i) 
    {
        super(i);
        maxStackSize = 1;
        this.setTabToDisplayOn(CreativeTabs.tabTools);
    }

    @Override
    public String getTextureFile()
    {
        return "/bioxx/terratools.png";
    }

    @Override
    public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float HitX, float HitY, float HitZ) 
    {
        if(!world.isRemote && world.getBlockId(x, y, z) != TFCBlocks.ToolRack.blockID)
        {
            ArrayList oreArray = new ArrayList<String>();
            ArrayList oreNumArray = new ArrayList<Integer>();
            
            boolean isOre = false;
            
            int id = world.getBlockId((int)x, (int)y, (int)z);
            if(id == TFCBlocks.Ore.blockID)
            {
                isOre = true;
                int meta = world.getBlockMetadata((int)x, (int)y, (int)z);
                oreArray.add(BlockOre.getItemNameDamage(((BlockOre)TFCBlocks.Ore).damageDropped(meta)));
            }
            else if(id == TFCBlocks.Ore2.blockID)
            {
                isOre = true;
                int meta = world.getBlockMetadata((int)x, (int)y, (int)z);
                oreArray.add(BlockOre2.getItemNameDamage(((BlockOre2)TFCBlocks.Ore2).damageDropped(meta)));
            }
            else if(id == TFCBlocks.Ore3.blockID)
            {
                isOre = true;
                int meta = world.getBlockMetadata((int)x, (int)y, (int)z);
                oreArray.add(BlockOre3.getItemNameDamage(((BlockOre3)TFCBlocks.Ore3).damageDropped(meta)));
            }
            //sides XN(0), XP(1), YN(2), YP(3), ZN(4), ZP(5);
            
            for (int i = -12; i < 12 && !isOre; i++)
            {
                for (int j = -12; j < 12; j++)
                {
                    for (int k = -12; k < 12; k++)
                    {
                        int oreid = world.getBlockId((int)x+i, (int)y+k, (int)z+j);

                        ItemStack is;
                        if(oreid == TFCBlocks.Ore.blockID)
                        {
                            int meta = world.getBlockMetadata((int)x+i, (int)y+k, (int)z+j);

                            if(!oreArray.contains(BlockOre.getItemNameDamage(((BlockOre)TFCBlocks.Ore).damageDropped(meta))))
                            {
                                oreArray.add(BlockOre.getItemNameDamage(((BlockOre)TFCBlocks.Ore).damageDropped(meta)));
                                oreNumArray.add(1);
                            }
                            else
                            {
                                int index = oreArray.indexOf(BlockOre.getItemNameDamage(((BlockOre)TFCBlocks.Ore).damageDropped(meta)));
                                oreNumArray.set(index, (Integer)oreNumArray.toArray()[index]+1);
                            }


                        }
                        else if(oreid == TFCBlocks.Ore2.blockID)
                        {
                            int meta = world.getBlockMetadata((int)x+i, (int)y+k, (int)z+j);

                            if(meta != 6)
                            {
                                if(!oreArray.contains(BlockOre2.getItemNameDamage(((BlockOre2)TFCBlocks.Ore2).damageDropped(meta))))
                                {
                                    oreArray.add(BlockOre2.getItemNameDamage(((BlockOre2)TFCBlocks.Ore2).damageDropped(meta)));
                                    oreNumArray.add(1);
                                }
                                else
                                {
                                    int index = oreArray.indexOf(BlockOre2.getItemNameDamage(((BlockOre2)TFCBlocks.Ore2).damageDropped(meta)));
                                    oreNumArray.set(index, (Integer)oreNumArray.toArray()[index]+1);
                                }
                            }
                        }
                        else if(oreid == TFCBlocks.Ore3.blockID)
                        {
                            int meta = world.getBlockMetadata((int)x+i, (int)y+k, (int)z+j);

                            if(!oreArray.contains(BlockOre3.getItemNameDamage(((BlockOre3)TFCBlocks.Ore3).damageDropped(meta))))
                            {
                                oreArray.add(BlockOre3.getItemNameDamage(((BlockOre3)TFCBlocks.Ore3).damageDropped(meta)));
                                oreNumArray.add(1);
                            }
                            else
                            {
                                int index = oreArray.indexOf(BlockOre3.getItemNameDamage(((BlockOre3)TFCBlocks.Ore3).damageDropped(meta)));
                                oreNumArray.set(index, (Integer)oreNumArray.toArray()[index]+1);
                            }
                        }
                    }
                }
            }

            Random random = new Random((long) ((x*z)+y));
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
    
    @Override
	public EnumSize getSize() {
		// TODO Auto-generated method stub
		return EnumSize.SMALL;
	}

	@Override
	public boolean canStack() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.LIGHT;
	}
}
