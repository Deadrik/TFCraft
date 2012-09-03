package TFC.TileEntities;

import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;

public abstract class TileEntityNetwork extends TileEntity {
	
	public abstract void handlePacketData();
	
	public abstract Packet getInitPacket(TileEntity tileEntity);

}
