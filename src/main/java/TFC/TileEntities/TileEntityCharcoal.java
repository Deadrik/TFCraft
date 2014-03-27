package TFC.TileEntities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCharcoal extends NetworkTileEntity
{
    public ItemStack charcoal;
    public byte count;

    public TileEntityCharcoal()
    {
    	charcoal = new ItemStack(Items.coal, 1, 1);
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
