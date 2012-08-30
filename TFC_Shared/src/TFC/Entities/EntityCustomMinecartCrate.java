package TFC.Entities;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.*;
import net.minecraftforge.common.IMinecartCollisionHandler;
import net.minecraftforge.common.MinecartRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.minecart.MinecartCollisionEvent;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;
import net.minecraftforge.event.entity.minecart.MinecartUpdateEvent;

public class EntityCustomMinecartCrate extends EntityMinecart
{	
    public EntityCustomMinecartCrate(World par1World)
    {
        super(par1World);
        this.cargoItems = new ItemStack[18];
        this.fuel = 0;
        this.field_70499_f = false;
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.7F);
        this.yOffset = this.height / 2.0F;

        maxSpeedRail = defaultMaxSpeedRail;
        maxSpeedGround = defaultMaxSpeedGround;
        maxSpeedAirLateral = defaultMaxSpeedAirLateral;
        maxSpeedAirVertical = defaultMaxSpeedAirVertical;
        dragAir = defaultDragAir;
    }

    public EntityCustomMinecartCrate(World world, int type)
    {
    	this(world);
    	minecartType = 1;
    }

    public EntityCustomMinecartCrate(World par1World, double par2, double par4, double par6, int par8)
    {
        this(par1World);
        this.setPosition(par2, par4 + (double)this.yOffset, par6);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
        this.minecartType = 1;
    }

    @Override
    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return 18;
    }
    
    protected void entityInit()
    {
        this.dataWatcher.addObject(16, new Byte((byte)0));
        this.dataWatcher.addObject(17, new Integer(0));
        this.dataWatcher.addObject(18, new Integer(1));
        this.dataWatcher.addObject(19, new Integer(1));
    }
}
