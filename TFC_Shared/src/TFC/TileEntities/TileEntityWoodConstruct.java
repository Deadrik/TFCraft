package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;

import TFC.*;
import TFC.Core.TFC_Time;
import TFC.Handlers.PacketHandler;
import TFC.Items.ItemOre;
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

public class TileEntityWoodConstruct extends NetworkTileEntity
{
    public byte[] woodTypes = new byte[192];
    public BitSet data;
    public int prefabID = -1;
    public long lastEditTime = 0;
    
    public static int PlankDetailLevel = 8;

    public TileEntityWoodConstruct()
    {
    	data = new BitSet(192);
    	lastEditTime = TFC_Time.getTotalTicks();
    }

    @Override
    public boolean canUpdate()
    {
        return false;
    }
    
    public void ejectContents()
    {
    	for(int i = 0; i < 192; i++)
    	{
    		if(data.get(i))
    		{
    			data.clear(i);
    			ItemStack stack = new ItemStack(TFCItems.SinglePlank, 1, woodTypes[i]);
    			
    			EntityItem e = new EntityItem(worldObj, xCoord, yCoord, zCoord, stack);
    			e.delayBeforeCanPickup = 5;
    			worldObj.spawnEntityInWorld(e);
    		}
    	}
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        woodTypes = par1NBTTagCompound.getByteArray("woodTypes");
        data = new BitSet(192);
        data.or(fromByteArray(par1NBTTagCompound.getByteArray("data")));
        //prefabID = par1NBTTagCompound.getInteger("prefabID");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setByteArray("woodTypes", woodTypes);
        par1NBTTagCompound.setByteArray("data", toByteArray(data));
        //par1NBTTagCompound.setInteger("prefabID", prefabID);
    }
    
    

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException 
	{
		int index = inStream.readInt();
		byte meta = inStream.readByte();

		this.data.flip(index);
		woodTypes[index] = meta;

		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException 
	{
		outStream.write(woodTypes);
		byte[] b = toByteArray(data);
		outStream.writeInt(b.length);
		outStream.write(b);		
	}

	@Override
	public void handleInitPacket(DataInputStream inStream) throws IOException 
	{
        inStream.readFully(woodTypes, 0, 192);
        
        int length = inStream.readInt();
        byte[] b = new byte[length];
        inStream.readFully(b);
        data.or(fromByteArray(b));
        
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream)
			throws IOException {
		
		
	}
	
	public Packet createUpdatePacket(int index, byte meta)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(255);
		DataOutputStream outStream = new DataOutputStream(bos);	
		try {
			outStream.writeByte(PacketHandler.Packet_Data_Block_Client);
			outStream.writeInt(xCoord);
			outStream.writeInt(yCoord);
			outStream.writeInt(zCoord);
			outStream.writeInt(index);
			outStream.writeByte(meta);
		} catch (IOException e) {
		}
		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}
	
	public static BitSet fromByteArray(byte[] bytes) {
	    BitSet bits = new BitSet(192);
	    for (int i = 0; i < bytes.length * 8; i++) {
	      if ((bytes[bytes.length - i / 8 - 1] & (1 << (i % 8))) > 0) {
	        bits.set(i);
	      }
	    }
	    return bits;
	  }
	
	public static byte[] toByteArray(BitSet bits) {
	    byte[] bytes = new byte[bits.length()/8+1];
	    for (int i=0; i<bits.length(); i++) {
	        if (bits.get(i)) {
	            bytes[bytes.length-i/8-1] |= 1<<(i%8);
	        }
	    }
	    return bytes;
	}

}
