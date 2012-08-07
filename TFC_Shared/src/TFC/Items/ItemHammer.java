package TFC.Items;

import java.util.List;

import TFC.Core.Helper;
import TFC.Core.TFCSettings;
import TFC.Core.TFC_Core;
import TFC.TileEntities.TileEntityTerraAnvil;
import TFC.WorldGen.TFCBiome;
import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;

public class ItemHammer extends ItemTool
implements ITextureProvider
{
    public ItemHammer(int i, EnumToolMaterial e)
    {
        super(i, 0, e, new Block[] {});
    }

    public String getTextureFile() {
        return "/bioxx/terratools.png";
    }

    public void addInformation(ItemStack is, List arraylist) 
    {
        if(TFCSettings.enableDebugMode)
            arraylist.add("Damage: "+is.getItemDamage());
    }
    
    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int l)
    {
        
            int id = 0;
            int id2 = world.getBlockId(x, y, z);
            int meta = 0;
            int meta2 = world.getBlockMetadata(x, y, z);
            TFCBiome biome = (TFCBiome) world.getBiomeGenForCoords(x, z);
            if(y < biome.Layer3)
            {
                id = biome.Layer3Type; meta = biome.Layer3Meta;
            }
            else if(y < biome.Layer2)
            {
                id = biome.Layer2Type; meta = biome.Layer2Meta;
            }
            else
            {
                id = biome.Layer1Type; meta = biome.Layer1Meta;
            }
            
            if(TFC_Core.isRawStone(world, x, y, z) && 
                    id2 == id && 
                    meta2 == meta)
            {
                MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(player, world);
                if(objectMouseOver == null) {
                    return false;
                }       
                int side = objectMouseOver.sideHit;
                if(side == 1)
                {
                world.setBlock(x, y, z, mod_TFC_Core.terraAnvil.blockID);
                mod_TFC_Core.terraAnvil.blockActivated(world, x, y, z, player);
                return false;
                }
            }
        
        return true;
    }
}