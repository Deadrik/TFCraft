package TFC.Items.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.Core.TFCTabs;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import TFC.Items.ItemTerra;

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
    public void registerIcons(IconRegister registerer)
    {
    	this.itemIcon = registerer.registerIcon("tools/"+this.getUnlocalizedName().replace("item.", ""));
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float HitX, float HitY, float HitZ) 
    {
        if(world.isRemote && world.getBlockId(x, y, z) != TFCBlocks.ToolRack.blockID)
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
            
            int id = world.getBlockId(x, y, z);
            int meta = world.getBlockMetadata(x, y, z);
            if(id == TFCBlocks.Ore.blockID)
            {
                isOre = true;
                
                oreArray.add(new ItemStack(id,1,meta).getItem().getItemDisplayName(new ItemStack(id,1,meta)));
            }
            else if(id == TFCBlocks.Ore2.blockID)
            {
                isOre = true;
                oreArray.add(new ItemStack(id,1,meta).getItem().getItemDisplayName(new ItemStack(id,1,meta)));
            }
            else if(id == TFCBlocks.Ore3.blockID)
            {
                isOre = true;
                oreArray.add(new ItemStack(id,1,meta).getItem().getItemDisplayName(new ItemStack(id,1,meta)));
            }
            //sides XN(0), XP(1), YN(2), YP(3), ZN(4), ZP(5);          
            for (int i = -12; i < 12 && !isOre; i++)
            {
                for (int j = -12; j < 12; j++)
                {
                    for (int k = -12; k < 12; k++)
                    {
                    	meta = world.getBlockMetadata(x+i, y+k, z+j);
                        int oreid = world.getBlockId(x+i, y+k, z+j);

                        
                        if(oreid == TFCBlocks.Ore.blockID)
                        {                   
                        	ItemStack is = new ItemStack(oreid,1,meta);
                            String itemName = is.getItem().getItemDisplayName(is);
                            if(!oreArray.contains(itemName))
                            {
                                oreArray.add(itemName);
                                oreNumArray.add(1);
                            }
                            else
                            {
                                int index = oreArray.indexOf(itemName);
                                oreNumArray.set(index, (Integer)oreNumArray.toArray()[index]+1);
                            }


                        }
                        else if(oreid == TFCBlocks.Ore2.blockID)
                        {
                        	ItemStack is = new ItemStack(oreid,1,meta);
                            String itemName = is.getItem().getItemDisplayName(is);
                            if(meta != 6)
                            {
                                if(!oreArray.contains(itemName))
                                {
                                    oreArray.add(itemName);
                                    oreNumArray.add(1);
                                }
                                else
                                {
                                    int index = oreArray.indexOf(itemName);
                                    oreNumArray.set(index, (Integer)oreNumArray.toArray()[index]+1);
                                }
                            }
                        }
                        else if(oreid == TFCBlocks.Ore3.blockID)
                        {
                        	ItemStack is = new ItemStack(oreid,1,meta);
                            String itemName = is.getItem().getItemDisplayName(is);
                            if(!oreArray.contains(itemName))
                            {
                                oreArray.add(itemName);
                                oreNumArray.add(1);
                            }
                            else
                            {
                                int index = oreArray.indexOf(itemName);
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
        }
        itemstack.setItemDamage(itemstack.getItemDamage()+1);
        if(itemstack.getItemDamage() >= itemstack.getMaxDamage())
            itemstack.stackSize = 0;
        return true;
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
