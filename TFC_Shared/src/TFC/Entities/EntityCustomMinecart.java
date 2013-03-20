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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.minecart.MinecartCollisionEvent;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;
import net.minecraftforge.event.entity.minecart.MinecartUpdateEvent;

public class EntityCustomMinecart extends EntityMinecartChest
{

	public EntityCustomMinecart(World par1World)
    {
        super(par1World);
    }

    public EntityCustomMinecart(World par1, double par2, double par4, double par6)
    {
        super(par1, par2, par4, par6);
    }

    public void func_94095_a(DamageSource par1DamageSource)
    {
        super.func_94095_a(par1DamageSource);
        this.dropItemWithOffset(Block.chest.blockID, 1, 0.0F);
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return 18;
    }

    public int func_94087_l()
    {
        return 1;
    }

    public Block func_94093_n()
    {
        return Block.chest;
    }

    public int func_94085_r()
    {
        return 8;
    }
}
