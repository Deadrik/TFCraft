package TFC.Entities;

import TFC.*;
import java.util.ArrayList;
import java.util.List;
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
import net.minecraftforge.common.IMinecartCollisionHandler;
import net.minecraftforge.common.MinecartRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.minecart.MinecartCollisionEvent;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;
import net.minecraftforge.event.entity.minecart.MinecartUpdateEvent;

public class EntityCustomMinecart extends EntityMinecart
{

    public EntityCustomMinecart(World par1World)
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

    public EntityCustomMinecart(World world, int type)
    {
    	this(world);
    	minecartType = type;
    }

    public EntityCustomMinecart(World par1World, double par2, double par4, double par6, int par8)
    {
        this(par1World);
        this.setPosition(par2, par4 + (double)this.yOffset, par6);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
        this.minecartType = par8;
    }

    @Override
    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return 18;
    }
    
    public boolean isStorageCart()
    {
        return true;
    }
}
