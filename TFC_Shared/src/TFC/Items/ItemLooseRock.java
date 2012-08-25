package TFC.Items;

import TFC.Core.Helper;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC;

public class ItemLooseRock extends ItemTerra
{

    public ItemLooseRock(int id) 
    {
        super(id);
        this.hasSubtypes = true;
        this.setMaxDamage(0);
    }
    public ItemLooseRock(int id, String tex) 
    {
        super(id);
        texture = tex;

    }

    int[][] map = 
        {   {0,-1,0},
            {0,1,0},
            {0,0,-1},
            {0,0,1},
            {-1,0,0},
            {1,0,0},
        };

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
        MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(entityplayer, world);
        if(objectMouseOver == null) {
            return false;
        }
        int x = objectMouseOver.blockX;
        int y = objectMouseOver.blockY;
        int z = objectMouseOver.blockZ;
        int side = objectMouseOver.sideHit;
        int dir = MathHelper.floor_double((double)(entityplayer.rotationYaw * 4F / 360F) + 0.5D) & 3;

        double xCoord = x + map[side][0];
        double yCoord = y + map[side][1];
        double zCoord = z + map[side][2];

        if((entityplayer.posX > xCoord+1  || entityplayer.posX < xCoord) || 
                (entityplayer.posZ > zCoord+1 || entityplayer.posZ < zCoord) || 
                (entityplayer.posY > yCoord+1.63 || entityplayer.posY < yCoord-1))
        {
            if(!world.isRemote)
            {
                if(world.getBlockId(x, y, z) == Block.snow.blockID)
                {
                    if(     (itemstack.getItemDamage() < 3 && world.setBlockAndMetadata(x, y, z, mod_TFC.terraStoneIgInCobble.blockID, itemstack.getItemDamage())) || 
                            (itemstack.getItemDamage() < 13 && world.setBlockAndMetadata(x, y, z, mod_TFC.terraStoneSedCobble.blockID, itemstack.getItemDamage() - 3))|| 
                            (itemstack.getItemDamage() < 17 && world.setBlockAndMetadata(x, y, z, mod_TFC.terraStoneIgExCobble.blockID, itemstack.getItemDamage() - 13))|| 
                            (itemstack.getItemDamage() < 23 && world.setBlockAndMetadata(x, y, z, mod_TFC.terraStoneMMCobble.blockID, itemstack.getItemDamage() - 17)))
                    {
                        if(!world.isRemote)
                        {
                            world.markBlockNeedsUpdate(x, y, z);
                            itemstack.stackSize = itemstack.stackSize-1;   
                        }
                        return true; 
                    }
                }
                else if((world.getBlockId(x + map[side][0], y + map[side][1], z + map[side][2]) == 0 || world.getBlockId(x + map[side][0], y + map[side][1], z + map[side][2]) == Block.snow.blockID) &&
                        (itemstack.getItemDamage() < 3 && world.setBlockAndMetadata(x + map[side][0], y + map[side][1], z + map[side][2], mod_TFC.terraStoneIgInCobble.blockID, itemstack.getItemDamage())) || 
                        (itemstack.getItemDamage() < 13 && world.setBlockAndMetadata(x + map[side][0], y + map[side][1], z + map[side][2], mod_TFC.terraStoneSedCobble.blockID, itemstack.getItemDamage() - 3))|| 
                        (itemstack.getItemDamage() < 17 && world.setBlockAndMetadata(x + map[side][0], y + map[side][1], z + map[side][2], mod_TFC.terraStoneIgExCobble.blockID, itemstack.getItemDamage() - 13))|| 
                        (itemstack.getItemDamage() < 23 && world.setBlockAndMetadata(x + map[side][0], y + map[side][1], z + map[side][2], mod_TFC.terraStoneMMCobble.blockID, itemstack.getItemDamage() - 17)))
                {
                    if(!world.isRemote)
                    {
                        world.markBlockNeedsUpdate(x + map[side][0], y + map[side][1], z + map[side][2]);
                        itemstack.stackSize = itemstack.stackSize-1;   
                    }
                    return true; 
                }
            }

        }

        return false;
    }

    public static String[] blockNames = {"Granite", "Diorite", "Gabbro", 
        "Siltstone", "Mudstone", "Shale", "Claystone", "Rock Salt", "Limestone", "Conglomerate", "Dolomite", "Chert", 
        "Chalk", "Rhyolite", "Basalt", "Andesite", "Dacite", 
        "Quartzite", "Slate", "Phyllite", "Schist", "Gneiss", "Marble"};

    @Override
    public String getItemNameIS(ItemStack itemstack) 
    {
        String s = new StringBuilder().append(super.getItemName()).append(".").append(blockNames[itemstack.getItemDamage()]).toString();
        return s;
    }

    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
    {

    }

    public int getIconFromDamage(int i)
    {
        switch(i)
        {
            case 0:
            case 1:
            case 2:
                return 176+i;//igin
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                return 192+(i-3);//sed
            case 13:
            case 14:
            case 15:
            case 16:
                return 179+(i-13);//igex
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                return 201+(i-17);//mm
        }
        return 176+i;
    }

    @Override
    public void addCreativeItems(java.util.ArrayList list)
    {
        for(int i = 0; i < 23; i++) {
            list.add(new ItemStack(this,1,i));
        }
    }
}
