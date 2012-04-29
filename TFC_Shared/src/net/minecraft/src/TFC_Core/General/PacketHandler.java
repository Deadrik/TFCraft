package net.minecraft.src.TFC_Core.General;

import java.io.*;
import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.*;
import net.minecraft.src.forge.*;

public class PacketHandler implements IPacketHandler, IConnectionHandler {
    @Override
    public void onConnect(NetworkManager network)
    {
        MessageManager.getInstance().registerChannel(network, this, "TFC tileentity");
    }

    @Override
    public void onDisconnect(NetworkManager network, String message, Object[] args)
    {
        MessageManager.getInstance().removeConnection(network);
    }

    @Override
    public void onLogin(NetworkManager network, Packet1Login login)
    {
    }

    @Override
    public void onPacketData(NetworkManager network, String channel, byte[] bytes)
    {
        DataInputStream data = new DataInputStream(new ByteArrayInputStream(bytes));
        
        //Tile entities channel
    	if (channel.equals("TFC tileentity"))
    	{
    		////Initialize common variables////
            
    		
            //Internal channel packet id
            int packetid = -1;
            //Coordinates of the tile entity
            int[] coords = new int[3];
            //Common tile entity
            TileEntity tileent = null;
            
            
            ////Read common data////
            

        	try
            {
            	//Read packet id
    			packetid = data.readInt();
    			//Read coords
    	        for (int i = 0; i < 3; i++)
    	        {
    	        	coords[i] = data.readInt();
    	        }
                
                //Get tile entity with coordinates coords
                World world = mod_TFC_Core.proxy.getCurrentWorld();
                if (world != null)
                {
                	//Retrieve tile entity
                	tileent = world.getBlockTileEntity(coords[0], coords[1], coords[2]);
        	        if (tileent != null)
        	        {
        	            //Switch depending on packet id
        			    switch (packetid)
        			    {
        			    	//Log pile = 0
        			        case 0:
        			        {
        			        	//Custom tile entity
        			        	TileEntityTerraLogPile tileent1 = (TileEntityTerraLogPile) tileent;
        			        	//ItemStacks in the tile entity
        			            ItemStack[] stacks = tileent1.storage;
        			            //Number of slots
        			            int slots = stacks.length;
        			            
        			            //Read slots
        			        	for (int i = 0; i < slots; i++)
        			        	{
        			        		int itemID = data.readInt();
        			        		int stackSize = data.readInt();
        			        		int itemDamage = data.readInt();
        			        		if (itemID >= 0)
        			        		{
        			        			stacks[i] = new ItemStack(itemID, stackSize, itemDamage);
        			        		}
        			        		else
        			        		{
        			        			stacks[i] = null;
        			        		}
        			        	}
        			        }
        			    }
        	        }
                }
            }
            catch (IOException e)
            {
            	e.printStackTrace();
            }
    	}
    }

    public static Packet getPacket(TileEntityTerraLogPile tileEntity) 
    {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(bytes);
        
        
        ////Initialize variables////
        
        
        //Internal channel packet id
        int packetid = 0;
        //ItemStacks in the tile entity
        ItemStack[] stacks = tileEntity.storage;
        //Number of slots
        int slots = stacks.length;
        //Coordinates of the tile entity
        int[] coords = {tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord};
        //Data of each stack
        int[][] stackdata = new int[3][slots];
        
        //Fill stack data with stack info
    	for (int i = 0; i < slots; i++)
    	{
    		if (stacks[i] != null)
    		{
	    		stackdata[0][i] = stacks[i].itemID;
	    		stackdata[1][i] = stacks[i].stackSize;
	    		stackdata[2][i] = stacks[i].getItemDamage();
    		}
    		else
    		{
    			stackdata[0][i] = -1;
	    		stackdata[1][i] = -1;
	    		stackdata[2][i] = -1;
    		}
    	}

    	
    	////Write the variables in the data stream////
    	

    	try 
        {
        	//Write packet id
        	data.writeInt(packetid);
        	//Write coordinates
        	for (int i = 0; i < 3; i++)
        	{
                data.writeInt(coords[i]);
        	}
        	//Write stack data
        	for (int i = 0; i < slots; i++)
        	{
        		data.writeInt(stackdata[0][i]);
        		data.writeInt(stackdata[1][i]);
        		data.writeInt(stackdata[2][i]);
        	}
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
	
        
        ////Create packet////
        
    	
        //Initialize packet
        Packet250CustomPayload packet = new Packet250CustomPayload();
        //Add packet channel, data, and length
        packet.channel = "TFC tileentity";
        packet.data = bytes.toByteArray();
        packet.length = packet.data.length;
        //packet ready to be sent
        return packet;
    }
}
