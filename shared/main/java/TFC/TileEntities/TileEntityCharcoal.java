package TFC.TileEntities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import TFC.Core.TFC_ItemHeat;
import TFC.Handlers.PacketHandler;
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
import net.minecraft.world.gen.feature.*;

public class TileEntityCharcoal extends NetworkTileEntity
{
    public ItemStack charcoal;
    public byte count;

    public TileEntityCharcoal()
    {
    	charcoal = new ItemStack(Item.coal, 1, 1);
        count = 0;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        count = nbttagcompound.getByte("count");
    }
    
    @Override
    public boolean canUpdate()
    {
    	return false;
    }

    @Override
    public void updateEntity()
    {

    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setByte("count", count);
    }

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException {
		count = inStream.readByte();
	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream)
			throws IOException {	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException {
		outStream.writeByte(count);	
	}

	@Override
	public void handleInitPacket(DataInputStream inStream) throws IOException {
		count = inStream.readByte();
	}

}
