package TFC.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import TFC.Blocks.BlockTerraOre;
import TFC.Core.Helper;
import TFC.Core.TFC_Settings;
import TFC.TileEntities.TileEntityTerraSluice;
import TFC.WorldGen.Biomes.BiomeGenRiverTFC;
import net.minecraft.src.*;

public class ItemTerraGoldPan extends Item
{
    public static String[] blockNames = {"GoldPan", "GoldPanSand", "GoldPanGravel", "GoldPanClay", "GoldPanDirt"};

    private int useTimer = 0;
    public ItemTerraGoldPan(int i) 
    {
        super(i);
        setMaxDamage(0);
        setHasSubtypes(true);
        setItemName("GoldPan");
        maxStackSize = 1;
    }

    public int getIconFromDamage(int i)
    {
        int j = 1;
        if(i == 1) {
            return 2;
        }
        if(i == 2) {
            return 3;
        }
        if(i == 3) {
            return 4;
        }
        if(i == 4) {
            return 5;
        }

        return j;
    }

    @Override
    public String getItemNameIS(ItemStack itemstack) 
    {
        String s = new StringBuilder().append(super.getItemName()).append(".").append(blockNames[itemstack.getItemDamage()]).toString();
        return s;
    }

    @Override
    public String getTextureFile()
    {
        return "/bioxx/terratools.png";
    }

    @Override
    public boolean tryPlaceIntoWorld(ItemStack itemstack, EntityPlayer entityplayer, World world, int x0, int y0, int z0, int l, float par8, float par9, float par10)
    {

        MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(entityplayer, world);
        if(objectMouseOver == null) {
            return false;
        }		
        int x = objectMouseOver.blockX;
        int y = objectMouseOver.blockY;
        int z = objectMouseOver.blockZ;
        int side = objectMouseOver.sideHit;

        if(!world.isRemote)
        {            
            //Do this if it's an empty pan
            if(itemstack.getItemDamage() == 0)
            {
                boolean isBiomeRiver = false;
                for (int i = -25; i < 25; i++)
                {
                    for (int j = -25; j < 25; j++)
                    {
                        if(world.getBiomeGenForCoords(x+i, z+j) instanceof BiomeGenRiverTFC)
                        {					
                            isBiomeRiver = true;
                        }
                    }
                }
                List L = new ArrayList<Integer>();
                for (int i = -10; i < 10; i++)
                {
                    for (int j = -10; j < 10; j++)
                    {
                        int k = world.getBiomeGenForCoords(x+i, z+j).biomeID;
                        if(!L.contains(k)) {
                            L.add(k);
                        }
                    }
                }
                Random R = new Random();
                if(world.getBlockId(x, y-1, z) == Block.sand.blockID && isBiomeRiver)
                {
                    if(R.nextInt(10) == 0) {
                        world.setBlockWithNotify(x, y-1, z, 0);
                    }
                    itemstack.setItemDamage(1);
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, itemstack);
                    return true;
                }
                else if(world.getBlockId(x, y-1, z) == Block.gravel.blockID)
                {
                    if(R.nextInt(10) == 0) {
                        world.setBlockWithNotify(x, y-1, z, 0);
                    }
                    itemstack.setItemDamage(2);
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, itemstack);
                    return true;
                }
                else if(world.getBlockId(x, y-2, z) == Block.sand.blockID && isBiomeRiver)
                {
                    if(R.nextInt(10) == 0) {
                        world.setBlockWithNotify(x, y-2, z, 0);
                    }
                    itemstack.setItemDamage(1);
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, itemstack);
                    return true;
                }
                else if(world.getBlockId(x, y-2, z) == Block.gravel.blockID)
                {
                    if(R.nextInt(10) == 0) {
                        world.setBlockWithNotify(x, y-2, z, 0);
                    }
                    itemstack.setItemDamage(2);
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, itemstack);
                    return true;
                }
                entityplayer.addChatMessage("Must use on sand in or near a river. Or gravel from under a water source.");
            }
            else
            {
                entityplayer.addExhaustion(0.2f);

                float gemMod = 1;
                float oreMod = 1;
                if(itemstack.getItemDamage() == 1)
                    gemMod = 0.75f;
                else if(itemstack.getItemDamage() == 2)
                    oreMod = 0.7f;

                int blockAboveId = world.getBlockId(x, y+1, z);
                int blockAboveMeta = world.getBlockMetadata(x, y+1, z);

                if(TFC_Settings.enableDebugMode)
                    System.out.println(new StringBuilder().append(blockAboveId).append(" ").append(blockAboveMeta).toString());

                if(blockAboveId == Block.waterStill.blockID && blockAboveMeta > 0)
                {
                    System.out.println(new StringBuilder().append("True").toString());
                    useTimer = 20;
                    Random random = new Random();

                    ArrayList coreSample = new ArrayList<Item>();
                    ArrayList coreSampleStacks = new ArrayList<ItemStack>();

                    for(int i = -50; i <= 50; i += 2)
                    {
                        for(int k = -50; k <= 50; k += 2)
                        {
                            for(int j = y; j > y-35; j--)
                            {
                                if(world.getBlockId(i+x, j, k+z) == TFCBlocks.terraOre.blockID)
                                {
                                    int m = world.getBlockMetadata(i+x, j, k+z);
                                    if(!coreSample.contains(BlockTerraOre.getDroppedItem(m)))
                                    {
                                        //coreSample.add(BlockTerraOre.getItemNameDamage(((BlockTerraOre)mod_TFC_Core.terraOre).damageDropped(meta)));
                                        if(m!= 14 && m != 15)
                                        {
                                            coreSample.add(BlockTerraOre.getDroppedItem(m));
                                            coreSampleStacks.add(new ItemStack(BlockTerraOre.getDroppedItem(m), 1, m));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if(random.nextInt((int) (250 * oreMod)) == 0 && !coreSampleStacks.isEmpty())
                    {
                        entityplayer.inventory.addItemStackToInventory((ItemStack)coreSampleStacks.toArray()[random.nextInt(coreSampleStacks.toArray().length)]);
                    }
                    else if(random.nextInt((int) (400 * gemMod)) == 0)
                    {
                        ArrayList items = new ArrayList<ItemStack>();
                        items.add(new ItemStack(TFCItems.terraGemAgate,1,0));
                        items.add(new ItemStack(TFCItems.terraGemAmethyst,1,0));
                        items.add(new ItemStack(TFCItems.terraGemBeryl,1,0));
                        items.add(new ItemStack(TFCItems.terraGemEmerald,1,0));
                        items.add(new ItemStack(TFCItems.terraGemGarnet,1,0));
                        items.add(new ItemStack(TFCItems.terraGemJade,1,0));
                        items.add(new ItemStack(TFCItems.terraGemJasper,1,0));
                        items.add(new ItemStack(TFCItems.terraGemOpal,1,0));
                        items.add(new ItemStack(TFCItems.terraGemRuby,1,0));
                        items.add(new ItemStack(TFCItems.terraGemSapphire,1,0));
                        items.add(new ItemStack(TFCItems.terraGemTourmaline,1,0));
                        items.add(new ItemStack(TFCItems.terraGemTopaz,1,0));
                        items.add(new ItemStack(Item.goldNugget,1,0));

                        entityplayer.inventory.addItemStackToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);

                    }
                    else if(random.nextInt((int) (800 * gemMod)) == 0)
                    {
                        ArrayList items = new ArrayList<ItemStack>();
                        items.add(new ItemStack(TFCItems.terraGemAgate,1,1));
                        items.add(new ItemStack(TFCItems.terraGemAmethyst,1,1));
                        items.add(new ItemStack(TFCItems.terraGemBeryl,1,1));
                        items.add(new ItemStack(TFCItems.terraGemEmerald,1,1));
                        items.add(new ItemStack(TFCItems.terraGemGarnet,1,1));
                        items.add(new ItemStack(TFCItems.terraGemJade,1,1));
                        items.add(new ItemStack(TFCItems.terraGemJasper,1,1));
                        items.add(new ItemStack(TFCItems.terraGemOpal,1,1));
                        items.add(new ItemStack(TFCItems.terraGemRuby,1,1));
                        items.add(new ItemStack(TFCItems.terraGemSapphire,1,1));
                        items.add(new ItemStack(TFCItems.terraGemTourmaline,1,1));
                        items.add(new ItemStack(TFCItems.terraGemTopaz,1,1));
                        items.add(new ItemStack(TFCItems.terraGemDiamond,1,0));
                        items.add(new ItemStack(Item.goldNugget,2,0));

                        entityplayer.inventory.addItemStackToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);
                    }
                    else if(random.nextInt((int) (1600 * gemMod)) == 0)
                    {
                        ArrayList items = new ArrayList<ItemStack>();
                        items.add(new ItemStack(TFCItems.terraGemAgate,1,2));
                        items.add(new ItemStack(TFCItems.terraGemAmethyst,1,2));
                        items.add(new ItemStack(TFCItems.terraGemBeryl,1,2));
                        items.add(new ItemStack(TFCItems.terraGemEmerald,1,2));
                        items.add(new ItemStack(TFCItems.terraGemGarnet,1,2));
                        items.add(new ItemStack(TFCItems.terraGemJade,1,2));
                        items.add(new ItemStack(TFCItems.terraGemJasper,1,2));
                        items.add(new ItemStack(TFCItems.terraGemOpal,1,2));
                        items.add(new ItemStack(TFCItems.terraGemRuby,1,2));
                        items.add(new ItemStack(TFCItems.terraGemSapphire,1,2));
                        items.add(new ItemStack(TFCItems.terraGemTourmaline,1,2));
                        items.add(new ItemStack(TFCItems.terraGemTopaz,1,2));
                        items.add(new ItemStack(TFCItems.terraGemDiamond,1,1));

                        entityplayer.inventory.addItemStackToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);
                    }
                    else if(random.nextInt((int) (3200 * gemMod)) == 0)
                    {
                        ArrayList items = new ArrayList<ItemStack>();
                        items.add(new ItemStack(TFCItems.terraGemAgate,1,3));
                        items.add(new ItemStack(TFCItems.terraGemAmethyst,1,3));
                        items.add(new ItemStack(TFCItems.terraGemBeryl,1,3));
                        items.add(new ItemStack(TFCItems.terraGemEmerald,1,3));
                        items.add(new ItemStack(TFCItems.terraGemGarnet,1,3));
                        items.add(new ItemStack(TFCItems.terraGemJade,1,3));
                        items.add(new ItemStack(TFCItems.terraGemJasper,1,3));
                        items.add(new ItemStack(TFCItems.terraGemOpal,1,3));
                        items.add(new ItemStack(TFCItems.terraGemRuby,1,3));
                        items.add(new ItemStack(TFCItems.terraGemSapphire,1,3));
                        items.add(new ItemStack(TFCItems.terraGemTourmaline,1,3));
                        items.add(new ItemStack(TFCItems.terraGemTopaz,1,3));
                        items.add(new ItemStack(TFCItems.terraGemDiamond,1,2));

                        entityplayer.inventory.addItemStackToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);
                    }
                    else if(random.nextInt((int) (6400 * gemMod)) == 0)
                    {
                        ArrayList items = new ArrayList<ItemStack>();
                        items.add(new ItemStack(TFCItems.terraGemAgate,1,4));
                        items.add(new ItemStack(TFCItems.terraGemAmethyst,1,4));
                        items.add(new ItemStack(TFCItems.terraGemBeryl,1,4));
                        items.add(new ItemStack(TFCItems.terraGemEmerald,1,4));
                        items.add(new ItemStack(TFCItems.terraGemGarnet,1,4));
                        items.add(new ItemStack(TFCItems.terraGemJade,1,4));
                        items.add(new ItemStack(TFCItems.terraGemJasper,1,4));
                        items.add(new ItemStack(TFCItems.terraGemOpal,1,4));
                        items.add(new ItemStack(TFCItems.terraGemRuby,1,4));
                        items.add(new ItemStack(TFCItems.terraGemSapphire,1,4));
                        items.add(new ItemStack(TFCItems.terraGemTourmaline,1,4));
                        items.add(new ItemStack(TFCItems.terraGemTopaz,1,4));
                        items.add(new ItemStack(TFCItems.terraGemDiamond,1,3));

                        entityplayer.inventory.addItemStackToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);

                    }
                    else if(random.nextInt((int) (12800 * gemMod)) == 0)
                    {
                        entityplayer.inventory.addItemStackToInventory(new ItemStack(TFCItems.terraGemDiamond,1,2));
                    }

                    itemstack.setItemDamage(0);
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, itemstack);
                    return true;
                }
                else
                {
                    entityplayer.addChatMessage("Must use under flowing water.");
                }
            }
        }
        return true;
    }
}
