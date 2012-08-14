package TFC.Core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

import TFC.TileEntities.TileEntityCrop;
import TFC.TileEntities.TileEntityPartial;
import TFC.TileEntities.TileEntityTerraAnvil;
import TFC.TileEntities.TileEntityTerraBloomery;
import TFC.TileEntities.TileEntityTerraFirepit;
import TFC.TileEntities.TileEntityTerraLogPile;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC;
import net.minecraft.src.forge.IConnectionHandler;
import net.minecraft.src.forge.IPacketHandler;
import net.minecraft.src.forge.MessageManager;

public class PacketHandler implements IPacketHandler, IConnectionHandler {

    @Override
    public void onConnect(NetworkManager network) {
        MessageManager.getInstance().registerChannel(network, this, "TerraFirmaCraft");

    }

    @Override
    public void onDisconnect(NetworkManager network, String message, Object[] args) {

        PlayerInfo PI = new PlayerInfo(mod_TFC.proxy.getPlayer(network).username);
        for(int i = 0; i < PlayerManagerTFC.getInstance().Players.size(); i++)
        {
            if(PlayerManagerTFC.getInstance().Players.get(i).Name.equalsIgnoreCase(PI.Name))
            {
                System.out.println("PlayerManager Successfully removed player " + mod_TFC.proxy.getPlayer(network).username);
                PlayerManagerTFC.getInstance().Players.remove(i);
            }  
        }
        MessageManager.getInstance().removeConnection(network);
    }

    @Override
    public void onLogin(NetworkManager network, Packet1Login login) 
    {
        PlayerManagerTFC.getInstance().Players.add(new PlayerInfo(login.username));

        ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
        DataOutputStream dos=new DataOutputStream(bos);
        EntityPlayer player = mod_TFC.proxy.getPlayer(network);
        World world= player.worldObj;

        if(world.isRemote)
        {
            try
            {
                dos.writeByte(3);

            } 
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Packet250CustomPayload pkt=new Packet250CustomPayload();
            pkt.channel="TerraFirmaCraft";
            pkt.data=bos.toByteArray();
            pkt.length=bos.size();
            pkt.isChunkDataPacket=false;
            System.out.println("Client requesting seed info");

            mod_TFC.proxy.sendCustomPacket(pkt);
        }
    }

    @Override
    public void onPacketData(NetworkManager network, String channel, byte[] data) 
    {
        DataInputStream dis=new DataInputStream(new ByteArrayInputStream(data));
        if(channel.contentEquals("TerraFirmaCraft"))
        {
            byte type = 0;
            int x;
            int y;
            int z;
            try {
                type = dis.readByte();
            } catch (IOException e) {
                return;
            }

            EntityPlayer player = mod_TFC.proxy.getPlayer(network);
            World world= player.worldObj;

            if(type == 0)
            {
                try {
                    x = dis.readInt();
                    y = dis.readInt();
                    z = dis.readInt();
                } catch (IOException e) {
                    return;
                }
                if(world != null)
                {
                    TileEntity te=world.getBlockTileEntity(x, y, z);

                    if (te instanceof TileEntityTerraAnvil) 
                    {
                        TileEntityTerraAnvil icte = (TileEntityTerraAnvil)te;
                        int id;
                        int tier;
                        try {
                            id = dis.readInt();
                        } catch (IOException e) {
                            return;
                        } 
                        icte.handlePacketData(id);
                    }
                    else if (te instanceof TileEntityTerraFirepit) 
                    {
                        TileEntityTerraFirepit icte = (TileEntityTerraFirepit)te;

                        int charcoal;
                        try {
                            charcoal = dis.readInt();
                        } catch (IOException e) 
                        {
                            return;
                        } 
                        icte.handlePacketData(charcoal);
                    }
                    else if (te instanceof TileEntityTerraBloomery) 
                    {
                        TileEntityTerraBloomery icte = (TileEntityTerraBloomery)te;
                        int orecount;
                        int coalcount;
                        float outcount;
                        int dam;
                        try {
                            orecount = dis.readInt();
                            coalcount = dis.readInt();
                            outcount = dis.readFloat();
                            dam = dis.readInt();
                        } catch (IOException e) {
                            return;
                        } 
                        icte.handlePacketData(orecount, coalcount, outcount, dam);
                    }
                    else if (te instanceof TileEntityPartial) 
                    {
                        TileEntityPartial icte = (TileEntityPartial)te;
                        short id;
                        byte meta;
                        byte mat;
                        long ex;
                        try {
                            id = dis.readShort();
                            meta = dis.readByte();
                            mat = dis.readByte();
                            ex = dis.readLong();
                        } catch (IOException e) {
                            return;
                        } 
                        icte.handlePacketData(id, meta, mat, ex);
                    }
                    else if (te instanceof TileEntityCrop) 
                    {
                        TileEntityCrop icte = (TileEntityCrop)te;
                        int id;
                        float growth;

                        try {
                            id = dis.readInt();
                            growth = dis.readFloat();

                        } catch (IOException e) {
                            return;
                        } 
                        icte.handlePacketData(id, growth);
                    }
                }
            }
            else if(type == 1)//Init Tile Entities
            {
                try {
                    x = dis.readInt();
                    y = dis.readInt();
                    z = dis.readInt();
                } catch (IOException e) {
                    return;
                }
                ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
                DataOutputStream dos=new DataOutputStream(bos);
                TileEntity te = (TileEntity) world.getBlockTileEntity(x, y, z);
                if(te != null)
                {
                    if (te instanceof TileEntityPartial) 
                    {
                        try 
                        {
                            dos.writeByte(0);
                            dos.writeInt(x);
                            dos.writeInt(y);
                            dos.writeInt(z);
                            dos.writeShort(((TileEntityPartial)te).TypeID);
                            dos.writeByte(((TileEntityPartial)te).MetaID);
                            dos.writeByte(((TileEntityPartial)te).material);
                            dos.writeLong(((TileEntityPartial)te).extraData);

                        } catch (IOException e) 
                        {
                            // IMPOSSIBLE?
                        }
                        Packet250CustomPayload pkt=new Packet250CustomPayload();
                        pkt.channel="TerraFirmaCraft";
                        pkt.data=bos.toByteArray();
                        pkt.length=bos.size();
                        pkt.isChunkDataPacket=true;

                        mod_TFC.proxy.sendCustomPacketToPlayer(player.username, pkt);
                    }
                    else if (te instanceof TileEntityCrop) 
                    {
                        try 
                        {
                            dos.writeByte(0);
                            dos.writeInt(x);
                            dos.writeInt(y);
                            dos.writeInt(z);
                            dos.writeInt(((TileEntityCrop)te).cropId);
                            dos.writeFloat(((TileEntityCrop)te).growth);

                        } catch (IOException e) 
                        {
                            // IMPOSSIBLE?
                        }
                        Packet250CustomPayload pkt=new Packet250CustomPayload();
                        pkt.channel="TerraFirmaCraft";
                        pkt.data=bos.toByteArray();
                        pkt.length=bos.size();
                        pkt.isChunkDataPacket=true;

                        mod_TFC.proxy.sendCustomPacketToPlayer(player.username, pkt);
                    }
                }
            }
            else if(type == 2)//Keypress changes
            {
                byte change;
                if(keyTimer + 1 < TFCSeasons.getTotalTicks())
                {
                    keyTimer = TFCSeasons.getTotalTicks();
                    try 
                    {
                        change = dis.readByte();
                        if(change == 0)//ChiselMode
                        {
                            PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);

                            if(pi!=null) pi.switchChiselMode();
                        }
                    } 
                    catch (IOException e) 
                    {
                        return;
                    } 
                }
            }
            else if(type == 3)//Initialization
            {
                if(world.isRemote)
                {
                    long seed = 0;
                    try 
                    {
                        seed = dis.readLong();

                    } catch (IOException e) 
                    {
                        // IMPOSSIBLE?
                    }
                    TFC_Core.SetupWorld(world, seed);
                }
                else
                {
                    ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
                    DataOutputStream dos=new DataOutputStream(bos);
                    try
                    {
                        dos.writeByte(3);
                        dos.writeLong(world.getSeed());

                    } 
                    catch (IOException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    Packet250CustomPayload pkt=new Packet250CustomPayload();
                    pkt.channel="TerraFirmaCraft";
                    pkt.data=bos.toByteArray();
                    pkt.length=bos.size();
                    pkt.isChunkDataPacket=false;

                    mod_TFC.proxy.sendCustomPacketToPlayer(player.username, pkt);
                }
            }
        }
    }
    static long keyTimer = 0;

    public static Packet getPacket(TileEntityTerraAnvil tileEntity, int id) 
    {
        ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
        DataOutputStream dos=new DataOutputStream(bos);
        int x=tileEntity.xCoord;
        int y=tileEntity.yCoord;
        int z=tileEntity.zCoord;
        try 
        {
            dos.writeByte(0);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(z);
            dos.writeInt(id);
        } 
        catch (IOException e) 
        {

        }
        Packet250CustomPayload pkt=new Packet250CustomPayload();
        pkt.channel="TerraFirmaCraft";
        pkt.data=bos.toByteArray();
        pkt.length=bos.size();
        pkt.isChunkDataPacket=true;
        return pkt;
    }

    public static Packet getPacket(TileEntityTerraFirepit tileEntity) {
        ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
        DataOutputStream dos=new DataOutputStream(bos);
        int x=tileEntity.xCoord;
        int y=tileEntity.yCoord;
        int z=tileEntity.zCoord;

        try 
        {
            dos.writeByte(0);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(z);
            dos.writeInt(tileEntity.charcoalCounter);

        } catch (IOException e) 
        {
            // IMPOSSIBLE?
        }
        Packet250CustomPayload pkt=new Packet250CustomPayload();
        pkt.channel="TerraFirmaCraft";
        pkt.data=bos.toByteArray();
        pkt.length=bos.size();
        pkt.isChunkDataPacket=true;
        return pkt;
    }

    public static Packet getPacket(
            TileEntityTerraBloomery tileEntity, int oreCount,
            int charcoalCount, float outCount, int oreDamage)
    {
        ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
        DataOutputStream dos=new DataOutputStream(bos);
        int x=tileEntity.xCoord;
        int y=tileEntity.yCoord;
        int z=tileEntity.zCoord;

        try 
        {
            dos.writeByte(0);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(z);
            dos.writeInt(oreCount);
            dos.writeInt(charcoalCount);
            dos.writeFloat(outCount);
            dos.writeInt(oreDamage);

        } catch (IOException e) 
        {
            // IMPOSSIBLE?
        }
        Packet250CustomPayload pkt=new Packet250CustomPayload();
        pkt.channel="TerraFirmaCraft";
        pkt.data=bos.toByteArray();
        pkt.length=bos.size();
        pkt.isChunkDataPacket=true;
        return pkt;
    }

    public static void requestInitialData(TileEntity te) {
        if (!TFC_Core.isClient()) return;
        else 
        {
            try
            {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(bos);
                Packet250CustomPayload pkt = new Packet250CustomPayload();

                dos.writeByte(1);
                dos.writeInt(te.xCoord);
                dos.writeInt(te.yCoord);
                dos.writeInt(te.zCoord);
                dos.close();

                pkt.channel="TerraFirmaCraft";
                pkt.data=bos.toByteArray();
                pkt.length=bos.size();
                pkt.isChunkDataPacket=true;

                mod_TFC.proxy.sendCustomPacket(pkt);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void broadcastPartialData(TileEntityPartial te) {
        if (TFC_Core.isClient()) return;
        else 
        {
            try
            {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(bos);
                Packet250CustomPayload pkt = new Packet250CustomPayload();

                dos.writeByte(0);
                dos.writeInt(te.xCoord);
                dos.writeInt(te.yCoord);
                dos.writeInt(te.zCoord);
                dos.writeShort(te.TypeID);
                dos.writeByte(te.MetaID);
                dos.writeByte(te.material);
                dos.writeLong(te.extraData);
                dos.close();

                pkt.channel="TerraFirmaCraft";
                pkt.data=bos.toByteArray();
                pkt.length=bos.size();
                pkt.isChunkDataPacket=true;

                mod_TFC.proxy.sendCustomPacket(pkt);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void broadcastCropData(TileEntityCrop te) {
        if (TFC_Core.isClient()) return;
        else 
        {
            try
            {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(bos);
                Packet250CustomPayload pkt = new Packet250CustomPayload();

                dos.writeByte(0);
                dos.writeInt(te.xCoord);
                dos.writeInt(te.yCoord);
                dos.writeInt(te.zCoord);
                dos.writeInt(te.cropId);
                dos.writeFloat(te.growth);
                dos.close();

                pkt.channel="TerraFirmaCraft";
                pkt.data=bos.toByteArray();
                pkt.length=bos.size();
                pkt.isChunkDataPacket=true;

                mod_TFC.proxy.sendCustomPacket(pkt);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void sendKeyPress(int type) //0 = chiselmode
    {
        if (!TFC_Core.isClient()) return;
        else 
        {
            try
            {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(bos);
                Packet250CustomPayload pkt = new Packet250CustomPayload();

                dos.writeByte(2);
                dos.writeByte(type);
                dos.close();

                pkt.channel="TerraFirmaCraft";
                pkt.data=bos.toByteArray();
                pkt.length=bos.size();
                pkt.isChunkDataPacket=false;

                mod_TFC.proxy.sendCustomPacket(pkt);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
