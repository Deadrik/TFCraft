package TFC.Items;

import java.util.List;

import TFC.*;
import TFC.Core.Helper;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Settings;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import TFC.TileEntities.TileEntityFarmland;
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
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.UseHoeEvent;

public class ItemCustomHoe extends ItemHoe implements ISize
{
	public ItemCustomHoe(int i, EnumToolMaterial e)
	{
		super(i, e);
		setCreativeTab(TFCTabs.TFCTools);
	}

	@Override
    public void func_94581_a(IconRegister registerer)
    {
    	this.iconIndex = registerer.func_94245_a("tools/"+this.getUnlocalizedName().replace("item.", ""));
    }
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) 
	{
		if (world.isRemote || world.getBlockId(x, y, z) == TFCBlocks.ToolRack.blockID)
		{
			return false;
		}
		else
		{
			UseHoeEvent event = new UseHoeEvent(player, stack, world, x, y, z);
            if (MinecraftForge.EVENT_BUS.post(event))
            {
                return false;
            }

            if (event.getResult() == Result.ALLOW)
            {
                stack.damageItem(1, player);
                return true;
            }
            
			int var8 = world.getBlockId(x, y, z);
			int var9 = world.getBlockId(x, y + 1, z);

			boolean isDirt = TFC_Core.isDirt(var8);
			
			if (side != 1 || var9 != 0 || (!TFC_Core.isGrass(var8) && !isDirt))
			{
				return false;
			}
			else
			{
				Block var10 = var8 == TFCBlocks.Dirt.blockID || var8 == TFCBlocks.Grass.blockID || var8 == TFCBlocks.DryGrass.blockID ? TFCBlocks.Dirt : 
					var8 == TFCBlocks.Dirt2.blockID || var8 == TFCBlocks.Grass2.blockID || var8 == TFCBlocks.DryGrass2.blockID ? TFCBlocks.Dirt2 : null;
				if(var10 != null)
				{
					int meta = world.getBlockMetadata(x, y, z);
					if(var10.blockID == TFCBlocks.Dirt.blockID)
					{
						world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), var10.stepSound.getStepSound(), (var10.stepSound.getVolume() + 1.0F) / 2.0F, var10.stepSound.getPitch() * 0.8F);

						if (world.isRemote)
						{
							return true;
						}
						else
						{
							world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.tilledSoil.blockID, meta,3);
							world.markBlockForUpdate(x, y, z);
							stack.damageItem(1, player);
							
							if(isDirt)
							{
								TileEntityFarmland te = (TileEntityFarmland) world.getBlockTileEntity(x, y, z);
								te.nutrients[0] = 100;
								te.nutrients[1] = 100;
								te.nutrients[2] = 100;
							}
							return true;
						}
					}
					else if(var10.blockID == TFCBlocks.Dirt2.blockID)
					{
						world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), var10.stepSound.getStepSound(), (var10.stepSound.getVolume() + 1.0F) / 2.0F, var10.stepSound.getPitch() * 0.8F);

						if (world.isRemote)
						{
							return true;
						}
						else
						{
							world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.tilledSoil2.blockID, meta,3);
							world.markBlockForUpdate(x, y, z);
							stack.damageItem(1, player);
							
							if(isDirt)
							{
								TileEntityFarmland te = (TileEntityFarmland) world.getBlockTileEntity(x, y, z);
								te.nutrients[0] = 100;
								te.nutrients[1] = 100;
								te.nutrients[2] = 100;
							}
							return true;
						}
					}
				}
			}
			return false;
		}
	}
	
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
    {
		ItemTerra.addSizeInformation(this, arraylist);
		
        if(TFC_Settings.enableDebugMode)
            arraylist.add("Damage: " + is.getItemDamage());
    }
	@Override
	public int getItemStackLimit()
    {
    	if(canStack())
    		return this.getSize().stackSize * getWeight().multiplier;
    	else
    		return 1;
    }

	@Override
	public EnumSize getSize() {
		return EnumSize.LARGE;
	}
	
	@Override
	public boolean canStack() 
	{
		return false;
	}

	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.LIGHT;
	}
}