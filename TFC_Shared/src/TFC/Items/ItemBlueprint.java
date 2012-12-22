package TFC.Items;

import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.Core.StructureBlueprint;
import TFC.TileEntities.TileEntityDetailed;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.World;

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
        return par1ItemStack.stackTagCompound.getString("Name");
    }
}
