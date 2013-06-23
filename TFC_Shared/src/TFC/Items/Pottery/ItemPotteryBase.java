package TFC.Items.Pottery;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.API.ISize;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Handlers.PacketHandler;
import TFC.Items.ItemTerra;
import TFC.TileEntities.NetworkTileEntity;
import TFC.TileEntities.TileEntityPottery;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPotteryBase extends ItemTerra implements ISize
{
	public Icon ClayIcon;
	public Icon CeramicIcon;

	public ItemPotteryBase(int id) 
	{
		super(id);
		this.hasSubtypes = true;
		this.setFolder("pottery/");
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.MetaNames = new String[]{"",""};
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{
		this.ClayIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + MetaNames[0]);
		this.CeramicIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + MetaNames[1]);
	}

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < MetaNames.length; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int damage)
	{
		if(damage == 0)
			return this.ClayIcon;
		else return this.CeramicIcon;    		
	}

	@Override
	public EnumSize getSize() 
	{
		return EnumSize.SMALL;
	}
	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.MEDIUM;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			TileEntityPottery te;
			if(side == 1)
			{
				int offset = 0;
				if(world.getBlockId(x, y, z) != TFCBlocks.Pottery.blockID)
				{
					world.setBlock(x, y+1, z, TFCBlocks.Pottery.blockID);
					offset = 1;
				}
				
				te = (TileEntityPottery) world.getBlockTileEntity(x, y+offset, z);
				
				if(hitX < 0.5 && hitZ < 0.5)
				{
					if(te.inventory[0] == null)
					{
						te.inventory[0] = new ItemStack(this,1,itemstack.getItemDamage());
						te.inventory[0].stackTagCompound = itemstack.stackTagCompound;
						itemstack.stackSize--;
						try {
							te.broadcastPacketInRange(sendInitPacket(te, x, y+offset, z));
						} catch (IOException e) {}
					}
				}
				else if(hitX > 0.5 && hitZ < 0.5)
				{
					if(te.inventory[1] == null)
					{
						te.inventory[1] = new ItemStack(this,1,itemstack.getItemDamage());
						te.inventory[1].stackTagCompound = itemstack.stackTagCompound;
						itemstack.stackSize--;
						try {
							te.broadcastPacketInRange(sendInitPacket(te, x, y+offset, z));
						} catch (IOException e) {}
					}
				}
				else if(hitX < 0.5 && hitZ > 0.5)
				{
					if(te.inventory[2] == null)
					{
						te.inventory[2] = new ItemStack(this,1,itemstack.getItemDamage());
						te.inventory[2].stackTagCompound = itemstack.stackTagCompound;
						itemstack.stackSize--;
						try {
							te.broadcastPacketInRange(sendInitPacket(te, x, y+offset, z));
						} catch (IOException e) {}
					}
				}
				else if(hitX > 0.5 && hitZ > 0.5)
				{
					if(te.inventory[3] == null)
					{
						te.inventory[3] = new ItemStack(this,1,itemstack.getItemDamage());
						te.inventory[3].stackTagCompound = itemstack.stackTagCompound;
						itemstack.stackSize--;
						try {
							te.broadcastPacketInRange(sendInitPacket(te, x, y+offset, z));
						} catch (IOException e) {}
					}
				}
				
			}
		}
		return true;
	}
	
	private Packet sendInitPacket(NetworkTileEntity te, int x, int y, int z) throws IOException
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);
		try 
		{
			//The packet type sent determines who is expected to process this packet, the client or the server.
			dos.writeByte(PacketHandler.Packet_Init_Block_Client);
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(z);
			te.createInitPacket(dos);
		} 
		catch (IOException e) 
		{

		}
		return te.setupCustomPacketData(bos.toByteArray(), bos.size());
	}

}
