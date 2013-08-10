package TFC.Entities.Mobs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.world.World;
import TFC.API.ICausesDamage;
import TFC.API.Enums.EnumDamageType;
import TFC.Core.TFC_MobDamage;

public class EntityEndermanTFC extends EntityEnderman implements ICausesDamage
{
    public static boolean[] carriableBlocks = new boolean[256];


    public EntityEndermanTFC(World par1World)
    {
        super(par1World);

    }
    
    @Override
    public int getMaxHealth()
    {
        return 2000;
    }

    @Override
    public int getAttackStrength(Entity par1Entity)
    {
        return TFC_MobDamage.EndermanDamage;
    }

	@Override
	public EnumDamageType GetDamageType() {
		// TODO Auto-generated method stub
		return EnumDamageType.GENERIC;
	}

}
