package TFC.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.Blocks.BlockOre;
import TFC.Blocks.BlockOre2;
import TFC.Blocks.BlockOre3;
import TFC.Chunkdata.ChunkData;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Containers.MessageQue;
import TFC.Core.Helper;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Settings;
import TFC.Core.Vector3f;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.texture.IconRegister;
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
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;

public class ItemProPick extends ItemTerra
{
    List vecArray;

    public ItemProPick(int i) 
    {
        super(i);
        maxStackSize = 1;
        setCreativeTab(TFCTabs.TFCTools);
    }
    
    @Override
    public void func_94581_a(IconRegister registerer)
    {
    	this.iconIndex = registerer.func_94245_a("tools/"+this.getUnlocalizedName().replace("item.", ""));
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float HitX, float HitY, float HitZ) 
    {
        if(!world.isRemote && world.getBlockId(x, y, z) != TFCBlocks.ToolRack.blockID)
        {
//        	Random rand = new Random(x*z+y);
//        	ChunkData data = ChunkDataManager.getData(x >> 4, z >> 4);
//        	
//        	int currentLayer = y < TerraFirmaCraft.RockLayer3Height ? 3 : y < TerraFirmaCraft.RockLayer2Height ? 2 : 1;
//        	
//        	if(currentLayer == 1)
//        	{
//        		if(data.oreList1.size() > 0)
//        		{
//        			String Ore = data.oreList1.get(rand.nextInt(data.oreList1.size()));
//        			MessageQue.instance.addMessage("Ore");
//        		}
//        	}
        	
        	
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
