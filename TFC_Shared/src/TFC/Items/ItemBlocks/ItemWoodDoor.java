package TFC.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Items.ItemTerra;

public class ItemWoodDoor extends ItemTerra
{
	private int woodType;
	
    public ItemWoodDoor(int par1, int woodID)
    {
        super(par1);
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabRedstone);
        woodType = woodID;
        setFolder("wood/");
    }


    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    @Override
    public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ)
    {
        if (side != 1)
        {
            return false;
        }
        else
        {
            ++j;
            Block var11;

            // No more clunky switch statement
            var11 = TFCBlocks.Doors[woodType];

            if (player.canPlayerEdit(i, j, k, side, is) && player.canPlayerEdit(i, j + 1, k, side, is))
            {
                if (!var11.canPlaceBlockAt(world, i, j, k))
                {
                    return false;
                }
                else
                {
                    int var12 = MathHelper.floor_double((player.rotationYaw + 180.0F) * 4.0F / 360.0F - 0.5D) & 3;
                    placeDoorBlock(world, i, j, k, var12, var11);
                    --is.stackSize;
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
    }

    public static void placeDoorBlock(World par0World, int par1, int par2, int par3, int par4, Block par5Block)
    {
        byte var6 = 0;
        byte var7 = 0;

        if (par4 == 0)
        {
            var7 = 1;
        }

        if (par4 == 1)
        {
            var6 = -1;
        }

        if (par4 == 2)
        {
            var7 = -1;
        }

        if (par4 == 3)
        {
            var6 = 1;
        }

        int var8 = (par0World.isBlockNormalCube(par1 - var6, par2, par3 - var7) ? 1 : 0) + (par0World.isBlockNormalCube(par1 - var6, par2 + 1, par3 - var7) ? 1 : 0);
        int var9 = (par0World.isBlockNormalCube(par1 + var6, par2, par3 + var7) ? 1 : 0) + (par0World.isBlockNormalCube(par1 + var6, par2 + 1, par3 + var7) ? 1 : 0);
        boolean var10 = par0World.getBlockId(par1 - var6, par2, par3 - var7) == par5Block.blockID || par0World.getBlockId(par1 - var6, par2 + 1, par3 - var7) == par5Block.blockID;
        boolean var11 = par0World.getBlockId(par1 + var6, par2, par3 + var7) == par5Block.blockID || par0World.getBlockId(par1 + var6, par2 + 1, par3 + var7) == par5Block.blockID;
        boolean var12 = false;

        if (var10 && !var11)
        {
            var12 = true;
        }
        else if (var9 > var8)
        {
            var12 = true;
        }

        par0World.setBlock(par1, par2, par3, par5Block.blockID, par4, 0x2);
        par0World.setBlock(par1, par2 + 1, par3, par5Block.blockID, 8 | (var12 ? 1 : 0), 0x2);
        par0World.notifyBlocksOfNeighborChange(par1, par2, par3, par5Block.blockID);
        par0World.notifyBlocksOfNeighborChange(par1, par2 + 1, par3, par5Block.blockID);
    }
    
    @Override
	public EnumSize getSize() {
		return EnumSize.HUGE;
	}

	@Override
	public EnumWeight getWeight() {
		return EnumWeight.HEAVY;
	}
}
