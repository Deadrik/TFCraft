package net.minecraft.src.TFC_Core.General;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.TFC_Core.TileEntityTerraLogPile;
import net.minecraft.src.TFC_Game.TileEntityTerraFirepit;
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
        MessageManager.getInstance().removeConnection(network);
    }

    @Override
    public void onLogin(NetworkManager network, Packet1Login login) {
    }

    @Override
    public void onPacketData(NetworkManager network, String channel, byte[] data) {
        DataInputStream dis=new DataInputStream(new ByteArrayInputStream(data));
        int x;
        int y;
        int z;
        try {
            x = dis.readInt();
            y = dis.readInt();
            z = dis.readInt();
        } catch (IOException e) {
            return;
        }
        World world= mod_TFC_Core.proxy.getCurrentWorld();
        TileEntity te=world.getBlockTileEntity(x, y, z);

        if (te instanceof TileEntityTerraLogPile) 
        {
            int[] items = new int[4];
            TileEntityTerraLogPile icte = (TileEntityTerraLogPile)te;
            icte.handlePacketData();
        }
    }

    public static Packet getPacket(TileEntityTerraLogPile tileEntity) 
    {
        ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
        DataOutputStream dos=new DataOutputStream(bos);
        int x=tileEntity.xCoord;
        int y=tileEntity.yCoord;
        int z=tileEntity.zCoord;

        Packet250CustomPayload pkt=new Packet250CustomPayload();
        pkt.channel="TerraFirmaCraft";
        pkt.data=bos.toByteArray();
        pkt.length=bos.size();
        pkt.isChunkDataPacket=true;
        return pkt;
    }
}
