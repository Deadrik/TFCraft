package TFC.Items;

import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.Core.StructureBlueprint;
import TFC.TileEntities.TileEntityDetailed;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
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

public class ItemBlueprint extends ItemTerra
{
	public ItemBlueprint(int i)
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		setItemName("Blueprint");
		setCreativeTab(CreativeTabs.tabMisc);
		setIconIndex(0);
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terratools.png";
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		if(stack.stackTagCompound != null)
			player.openGui(TerraFirmaCraft.instance, 34, player.worldObj, 0, 0, 0);
        return stack;
    }
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) 
	{
		if(!world.isRemote && stack.stackTagCompound == null &&
				(world.getBlockId(x, y, z) == TFCBlocks.Detailed.blockID || world.getBlockId(x, y, z) == TFCBlocks.SuperDetailed.blockID))
		{
			TileEntityDetailed te = (TileEntityDetailed) world.getBlockTileEntity(x, y, z);
			byte[] data = te.data.toByteArray();
			
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setByteArray("", data);
			
			stack.setTagCompound(nbt);
			return true;			
		}
		//player.openGui(TerraFirmaCraft.instance, 34, player.worldObj, 0, 0, 0);
		return false;
	}
	
	public String getItemDisplayName(ItemStack par1ItemStack)
    {
		if(par1ItemStack.stackTagCompound != null)
			return par1ItemStack.stackTagCompound.getString("Name");
		else return "Blueprint";
    }
}
