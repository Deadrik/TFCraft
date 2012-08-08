package TFC.Blocks;

import TFC.TileEntities.TileEntityTerraScribe;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC;
import net.minecraft.src.forge.ITextureProvider;

public class BlockTerraScribe extends BlockContainer implements ITextureProvider
{
	//TileEntityTerraSluice entity;
	private int meta;
	private int xCoord;
	private int yCoord;
	private int zCoord;

	private Class EntityClass;

	public BlockTerraScribe(int i, Class tClass)
	{
		super(i, Material.wood);
		EntityClass = tClass;
		needsRandomTick = true;
	}

	public void addCreativeItems(java.util.ArrayList list)
	{
		list.add(new ItemStack(this,1,0));
	}

	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		meta = world.getBlockMetadata(i, j, k);
		xCoord = i;
		yCoord = j;
		zCoord = k;
		//Minecraft mc = ModLoader.getMinecraftInstance();
		ItemStack equippedItem = entityplayer.getCurrentEquippedItem();
		int itemid;
		if(equippedItem != null)
		{
			itemid = entityplayer.getCurrentEquippedItem().itemID;
		} else {
			itemid = 0;
		}

		if(world.isRemote)
		{
			return true;
		} 
		else
		{
			if((TileEntityTerraScribe)world.getBlockTileEntity(i, j, k)!=null)
			{
				TileEntityTerraScribe tileentityanvil;
				tileentityanvil = (TileEntityTerraScribe)world.getBlockTileEntity(i, j, k);
				ItemStack is = entityplayer.getCurrentEquippedItem();

				entityplayer.openGui(mod_TFC.instance, mod_TFC.terraScribeGuiId, world, i, j, k);
				//ModLoader.openGUI(entityplayer, new GuiTerraScribe(entityplayer.inventory, tileentityanvil, world));
			}
			return true;
		}
	}

	@Override
	public TileEntity getBlockEntity()
	{

		//Minecraft mc = ModLoader.getMinecraftInstance();
		try
		{
			return (TileEntity) EntityClass.newInstance();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		if(i == 1) {
			return 88;
		}
		return 89;
	}

	public String getItemNameIS(ItemStack itemstack) 
	{
		String s = "terraScribe";
		return s;
	}

	@Override
	public String getTextureFile() {

		return "/bioxx/terrablocks.png";
	}
	public boolean isOpaqueCube()
	{
		return false;
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

}
