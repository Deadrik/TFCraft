package net.minecraft.src.TFC_Core;

import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.General.PacketHandler;
import net.minecraft.src.TFC_Core.Items.ItemTerraSmallOre;

public class TileEntityPartial extends TileEntity
{
    public short TypeID = -1;
    public byte MetaID = 0;
    public byte material = 0;
    public long extraData = 0;

    public TileEntityPartial()
    {
        
    }

    public void validate()
    {
        super.validate();
        if (this.worldObj.isRemote)
        {
            PacketHandler.requestInitialData(this);
        }
    }
    
    @Override
    public boolean canUpdate()
    {
        return false;
    }
    
    public Material getMaterial()
    {
        switch(material)
        {
            case 1:
                return Material.ground;
            case 2:
                return Material.wood;
            case 3:
                return Material.rock;
            case 4:
                return Material.iron;
            case 5:
                return Material.water;
            case 6:
                return Material.lava;
            case 7:
                return Material.leaves;
            case 8:
                return Material.plants;
            case 9:
                return Material.vine;
            case 10:
                return Material.sponge;
            case 11:
                return Material.cloth;
            case 12:
                return Material.fire;
            case 13:
                return Material.sand;
            case 14:
                return Material.circuits;
            case 15:
                return Material.glass;
            case 16:
                return Material.redstoneLight;
            case 17:
                return Material.tnt;
            case 18:
                return Material.unused;
            case 19:
                return Material.ice;
            case 20:
                return Material.snow;
            case 21:
                return Material.craftedSnow;
            case 22:
                return Material.cactus;
            case 23:
                return Material.clay;
            case 24:
                return Material.pumpkin;
            case 25:
                return Material.dragonEgg;
            case 26:
                return Material.portal;
            case 27:
                return Material.cake;
            case 28:
                return Material.web;
            case 29:
                return Material.piston;
            default:
                return Material.grass;
        }
    }

    public void setMaterial(Material mat)
    {
        byte n = 1;
        if(mat == Material.ground) {material = n;} 
        else if(mat == Material.wood){n++; material = n;}
        else if(mat == Material.rock){n++; material = n;}
        else if(mat == Material.iron){n++; material = n;}
        else if(mat == Material.water){n++; material = n;}
        else if(mat == Material.lava){n++; material = n;}
        else if(mat == Material.leaves){n++; material = n;}
        else if(mat == Material.plants){n++; material = n;}
        else if(mat == Material.vine){n++; material = n;}
        else if(mat == Material.sponge){n++; material = n;}
        else if(mat == Material.cloth){n++; material = n;}
        else if(mat == Material.fire){n++; material = n;}
        else if(mat == Material.sand){n++; material = n;}
        else if(mat == Material.circuits){n++; material = n;}
        else if(mat == Material.glass){n++; material = n;}
        else if(mat == Material.redstoneLight){n++; material = n;}
        else if(mat == Material.tnt){n++; material = n;}
        else if(mat == Material.unused){n++; material = n;}
        else if(mat == Material.ice){n++; material = n;}
        else if(mat == Material.snow){n++; material = n;}
        else if(mat == Material.craftedSnow){n++; material = n;}
        else if(mat == Material.cactus){n++; material = n;}
        else if(mat == Material.clay){n++; material = n;}
        else if(mat == Material.pumpkin){n++; material = n;}
        else if(mat == Material.dragonEgg){n++; material = n;}
        else if(mat == Material.portal){n++; material = n;}
        else if(mat == Material.cake){n++; material = n;}
        else if(mat == Material.web){n++; material = n;}
        else if(mat == Material.piston){n++; material = n;}
        else if(mat == Material.grass){n++; material = 0;}
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        MetaID = par1NBTTagCompound.getByte("metaID");
        TypeID = par1NBTTagCompound.getShort("typeID");
        material = par1NBTTagCompound.getByte("material");
        extraData = par1NBTTagCompound.getLong("extraData");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("typeID", (short) TypeID);
        par1NBTTagCompound.setByte("metaID", MetaID);
        par1NBTTagCompound.setByte("material", material);
        par1NBTTagCompound.setLong("extraData", extraData);
    }

    public void handlePacketData(short id, byte meta, byte mat, long ex)
    {
        TypeID = id;
        MetaID = meta;
        material = mat;
        extraData = ex;
        this.worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
    }
}
