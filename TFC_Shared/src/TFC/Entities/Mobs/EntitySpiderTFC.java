package TFC.Entities.Mobs;

import TFC.*;
import net.minecraft.src.Entity;
import net.minecraft.src.EntitySpider;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class EntitySpiderTFC extends EntitySpider
{
    public EntitySpiderTFC(World par1World)
    {
        super(par1World);
        this.texture = "/mob/spider.png";
        this.setSize(1.4F, 0.9F);
        this.moveSpeed = 0.8F;
    }

    public int getMaxHealth()
    {
        return 800;
    }
    
    public int func_82193_c(Entity par1Entity)
    {
        return 100;
    }

}
