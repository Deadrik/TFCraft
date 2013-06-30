package TFC.TileEntities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class TileEntityThatch extends NetworkTileEntity
{
	//Z-shaped
	boolean[][] corners={{false,false},{false,false}};
	boolean[][] sides={{false,false,false,false},{false,false,false,false}};
	
	public TileEntityThatch(){
		
	}

	public boolean getCorner(int x, int z){
		boolean result = false;
		if((x==1||x==0)&&(z==1||z==0)){
			result = corners[x][z];
		}
		return result;
	}
	
	@Override
	public void updateEntity()
	{
		sides[0][0] = worldObj.getBlockTileEntity(xCoord,yCoord,zCoord+1) instanceof TileEntityThatch;
		sides[1][0] = worldObj.getBlockTileEntity(xCoord+1,yCoord,zCoord) instanceof TileEntityThatch;
		sides[2][0] = worldObj.getBlockTileEntity(xCoord-1,yCoord,zCoord) instanceof TileEntityThatch;
		sides[3][0] = worldObj.getBlockTileEntity(xCoord,yCoord,zCoord-1) instanceof TileEntityThatch;
		
		sides[0][1] = worldObj.getBlockTileEntity(xCoord,yCoord+1,zCoord+1) instanceof TileEntityThatch;
		sides[1][1] = worldObj.getBlockTileEntity(xCoord+1,yCoord+1,zCoord) instanceof TileEntityThatch;
		sides[2][1] = worldObj.getBlockTileEntity(xCoord-1,yCoord+1,zCoord) instanceof TileEntityThatch;
		sides[3][1] = worldObj.getBlockTileEntity(xCoord,yCoord+1,zCoord-1) instanceof TileEntityThatch;
		
		if(sides[0][1]&&sides[2][1]){
		corners[0][0] = ((TileEntityThatch)worldObj.getBlockTileEntity(xCoord,yCoord+1,zCoord+1)).getCorner(0, 1)&&
				((TileEntityThatch)worldObj.getBlockTileEntity(xCoord-1,yCoord+1,zCoord)).getCorner(1,0);
		}
		if(sides[0][1]&&sides[1][1]){
			corners[1][0] = ((TileEntityThatch)worldObj.getBlockTileEntity(xCoord,yCoord+1,zCoord+1)).getCorner(1, 1)&&
					((TileEntityThatch)worldObj.getBlockTileEntity(xCoord+1,yCoord+1,zCoord)).getCorner(0,0);
			}
		if(sides[3][1]&&sides[2][1]){
			corners[0][1] = ((TileEntityThatch)worldObj.getBlockTileEntity(xCoord,yCoord+1,zCoord-1)).getCorner(0, 0)&&
					((TileEntityThatch)worldObj.getBlockTileEntity(xCoord-1,yCoord+1,zCoord)).getCorner(1,1);
			}
		if(sides[1][1]&&sides[3][1]){
			corners[1][1] = ((TileEntityThatch)worldObj.getBlockTileEntity(xCoord,yCoord+1,zCoord-1)).getCorner(1, 0)&&
					((TileEntityThatch)worldObj.getBlockTileEntity(xCoord+1,yCoord+1,zCoord)).getCorner(0,1);
			}
	}
	
	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream)
			throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleInitPacket(DataInputStream inStream) throws IOException {
		// TODO Auto-generated method stub
		
	}

	
}
