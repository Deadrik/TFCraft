package com.bioxx.tfc.Items.Pottery;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Metal.Alloy;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.TileEntities.TEPottery;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISize;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPotteryBase extends ItemTerra implements ISize
{
	public IIcon ClayIcon;
	public IIcon CeramicIcon;

	public ItemPotteryBase() 
	{
		super();
		this.hasSubtypes = true;
		this.setFolder("pottery/");
		setCreativeTab(TFCTabs.TFCPottery);
		this.MetaNames = new String[]{"",""};
		this.setWeight(EnumWeight.MEDIUM);
		this.setSize(EnumSize.SMALL);
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.ClayIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + MetaNames[0]);
		this.CeramicIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + MetaNames[1]);
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < MetaNames.length; i++)
			list.add(new ItemStack(this, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage)
	{
		if(damage == 0)
			return this.ClayIcon;
		else
			return this.CeramicIcon;
	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List arraylist)
	{
		if (TFC_Core.showShiftInformation()) 
		{
			arraylist.add(StatCollector.translateToLocal("gui.Help"));
			arraylist.add(StatCollector.translateToLocal("gui.PotteryBase.Inst0"));
		} else
			arraylist.add(StatCollector.translateToLocal("gui.ShowHelp"));
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote && entityplayer.isSneaking())
		{
			TEPottery te;
			if(side == 1)
			{
				int offset = 0;
				if(world.getBlock(x, y, z) != TFCBlocks.Pottery && world.isAirBlock(x, y + 1, z))
				{
					//We only want the pottery to be placeable if the block is solid on top.
					if(!world.isSideSolid(x, y, z, ForgeDirection.UP))
						return false;
					world.setBlock(x, y + 1, z, TFCBlocks.Pottery);
					offset = 1;
				}


				if(world.getTileEntity(x, y+offset, z) != null && world.getTileEntity(x, y + offset, z) instanceof TEPottery) 
				{
					te = (TEPottery) world.getTileEntity(x, y + offset, z);
					if(hitX < 0.5 && hitZ < 0.5)
					{
						if(te.canAddItem(0))
						{
							te.inventory[0] = new ItemStack(this, 1, itemstack.getItemDamage());
							te.inventory[0].stackTagCompound = itemstack.stackTagCompound;
							itemstack.stackSize--;
							world.markBlockForUpdate(x, y + offset, z);
						}
					}
					else if(hitX > 0.5 && hitZ < 0.5)
					{
						if(te.canAddItem(1))
						{
							te.inventory[1] = new ItemStack(this,1,itemstack.getItemDamage());
							te.inventory[1].stackTagCompound = itemstack.stackTagCompound;
							itemstack.stackSize--;
							world.markBlockForUpdate(x, y + offset, z);
						}
					}
					else if(hitX < 0.5 && hitZ > 0.5)
					{
						if(te.canAddItem(2))
						{
							te.inventory[2] = new ItemStack(this,1,itemstack.getItemDamage());
							te.inventory[2].stackTagCompound = itemstack.stackTagCompound;
							itemstack.stackSize--;
							world.markBlockForUpdate(x, y + offset, z);
						}
					}
					else if(hitX > 0.5 && hitZ > 0.5)
						if(te.canAddItem(3))
						{
							te.inventory[3] = new ItemStack(this,1,itemstack.getItemDamage());
							te.inventory[3].stackTagCompound = itemstack.stackTagCompound;
							itemstack.stackSize--;
							world.markBlockForUpdate(x, y + offset, z);
						}
				}
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * Called when the pottery object is done being cooked in a kiln.
	 */
	public void onDoneCooking(World world, ItemStack is, Alloy.EnumTier furnaceTier)
	{
	}

	//	private Packet sendInitPacket(NetworkTileEntity te, int x, int y, int z) throws IOException
	//	{
	//		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
	//		DataOutputStream dos=new DataOutputStream(bos);
	//		try
	//		{
	//			//The packet type sent determines who is expected to process this packet, the client or the server.
	//			dos.writeByte(PacketHandler.Packet_Init_Block_Client);
	//			dos.writeInt(x);
	//			dos.writeInt(y);
	//			dos.writeInt(z);
	//			te.createInitPacket(dos);
	//		} 
	//		catch (IOException e)
	//		{
	//		}
	//		return PacketHandler.getPacket(bos);
	//	}

}
