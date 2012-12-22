package TFC.Blocks;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import TFC.*;
import TFC.Core.*;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Items.ItemChisel;
import TFC.Items.ItemHammer;
import TFC.TileEntities.TileEntityDetailed;
import TFC.TileEntities.TileEntityPartial;
import TFC.TileEntities.TileEntitySuperDetailed;
import TFC.TileEntities.TileEntityWoodConstruct;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.TileEntity;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;
import net.minecraft.src.Block;

public class BlockSuperDetailed extends BlockDetailed
{
	public static int lockX = 0;
	public static int lockY = 0;
	public static int lockZ = 0;
	
	public BlockSuperDetailed(int par1)
	{
		super(par1);
	}
	
	@Override
	public int getRenderType()
	{
		return TFCBlocks.superDetailedRenderId;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) 
	{
		return new TileEntitySuperDetailed();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ) 
	{
		boolean hasHammer = false;
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
		for(int i = 0; i < 9;i++)
		{
			if(entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() instanceof ItemHammer)
				hasHammer = true;
		}
		if(entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() instanceof ItemChisel && hasHammer && world.isRemote &&
				pi.lockMatches(x, y, z))
		{
			TileEntitySuperDetailed te = (TileEntitySuperDetailed) world.getBlockTileEntity(x, y, z);
			
			lockX = x; lockY = y; lockZ = z;
			
			TerraFirmaCraft.proxy.sendCustomPacket(te.createActivatePacket(xSelected, ySelected, zSelected));
		}
		return false;
	}
	
	public boolean onBlockActivatedServer(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ) 
	{
			int id = world.getBlockId(x, y, z);
			byte meta = (byte) world.getBlockMetadata(x, y, z);

			int mode = 0;
			PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
			if(pi!=null) mode = pi.ChiselMode;

			if(mode == 3 && xSelected != -10)
			{
				//ItemChisel.CreateDetailed(world, x, y, z, id, meta, side, hitX, hitY, hitZ);
				TileEntityDetailed te = (TileEntityDetailed) world.getBlockTileEntity(x, y, z);
				int index = (xSelected * 8 + zSelected) * 8 + ySelected;

				if(index >= 0)
				{
					System.out.println("xSelected: " +xSelected + " ySelected: " + ySelected + " zSelected: " + zSelected + " index: " + index);
					te.data.flip(index);
					te.broadcastPacketInRange(te.createUpdatePacket(index));
				}
				return true;
			}
		return false;
	}
}
