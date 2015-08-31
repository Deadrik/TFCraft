package com.bioxx.tfc.Entities.AI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.Mobs.EntityChickenTFC;
import com.bioxx.tfc.Entities.Mobs.EntityPheasantTFC;
import com.bioxx.tfc.TileEntities.TENestBox;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Entities.IAnimal.GenderEnum;

public class EntityAIFindNest extends EntityAIBase
{
	private EntityCreature theCreature;
	/*private double shelterX;
	private double shelterY;
	private double shelterZ;*/
	private int currentTick;
	//private double movementSpeed;
	private World theWorld;
	private int movement;
	private final double field_75404_b;
	private int maxSittingTicks;

	//To prevent chickens from trying to sit in unreachable nests. See below in updateTask, if the chicken doesnt move >0.5 m in 40 ticks, it 
	//gives up, and waits 1 day before trying to sit in a nest box located at the specified coordinates
	private Map<String, Long> failureDepressionMap;
	private double compoundDistance;
	private int lastCheckedTick;
	private boolean end;

	/** X Coordinate of a nearby sitable block */
	private int sitableBlockX = -1;

	/** Y Coordinate of a nearby sitable block */
	private int sitableBlockY =-1;

	/** Z Coordinate of a nearby sitable block */
	private int sitableBlockZ =-1;

	public EntityAIFindNest(EntityAnimal eAnimal, double par2)
	{
		this.theCreature = eAnimal;
		//this.movementSpeed = par2;
		this.field_75404_b = par2;
		this.theWorld = eAnimal.worldObj;
		this.failureDepressionMap = new HashMap<String,Long>();
		this.setMutexBits(5);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		if(theCreature instanceof EntityChickenTFC && !(theCreature instanceof EntityPheasantTFC))
		{
			return ((EntityChickenTFC) theCreature).isAdult() &&((EntityChickenTFC) theCreature).getFamiliarity() >= 15 &&
					this.theWorld.getBlock((int)theCreature.posX, (int)theCreature.posY,(int)theCreature.posZ) != TFCBlocks.nestBox &&
					this.theWorld.getBlock((int)theCreature.posX, (int)theCreature.posY - 1,(int)theCreature.posZ) != TFCBlocks.nestBox &&
					this.getNearbySitableBlockDistance() &&
					((EntityChickenTFC) theCreature).getGender() == GenderEnum.FEMALE;
		}
		/*else if(theCreature instanceof EntityPheasantTFC)
		{
			if(((EntityPheasantTFC)theCreature).isAdult() && ((EntityPheasantTFC)theCreature).getFamiliarity() >= 15 &&
					this.theWorld.getBlock((int)theCreature.posX, (int)theCreature.posY,(int)theCreature.posZ) != TFCBlocks.NestBox &&
					this.theWorld.getBlock((int)theCreature.posX, (int)theCreature.posY - 1,(int)theCreature.posZ) != TFCBlocks.NestBox &&
					this.getNearbySitableBlockDistance() &&
					((EntityPheasantTFC)theCreature).getGender()==GenderEnum.FEMALE)
				return true;
			else
				return false;
		}*/
		return false;
	}

	@Override
	public boolean continueExecuting()
	{
		if (this.theCreature.getDistanceSq(sitableBlockX + 0.5, sitableBlockY, sitableBlockZ + 0.5) < 0.2)
			this.theCreature.getNavigator().clearPathEntity();

		if(this.end)
		{
			this.end = false;
			return end;
		}
		return this.currentTick <= this.maxSittingTicks && this.movement <= 60 && this.isSittableBlock(this.theCreature.worldObj, this.sitableBlockX, this.sitableBlockY, this.sitableBlockZ);
	}

	protected boolean getNearbySitableBlockDistance()
	{
		int i = (int)this.theCreature.posY;
		double d0 = 2.147483647E9D;

		for (int j = (int) this.theCreature.posX - 16; j < this.theCreature.posX + 16.0D; ++j)
		{
			for (int k = (int) this.theCreature.posZ - 16; k < this.theCreature.posZ + 16.0D; ++k)
			{
				for(int l = i; l < i+4; l++)
				{
					if (this.isSittableBlock(this.theCreature.worldObj, j, l, k) && this.theCreature.worldObj.isAirBlock(j, l + 1, k))
					{
						double d1 = this.theCreature.getDistanceSq(j, l, k);

						if (d1 < d0)
						{
							this.sitableBlockX = j;
							this.sitableBlockY = l;
							this.sitableBlockZ = k;
							d0 = d1;
						}
					}
				}
			}
		}
		return d0 < 2.147483647E9D;
	}
	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting()
	{
		this.theCreature.getNavigator().tryMoveToXYZ(this.sitableBlockX + 0.5D, this.sitableBlockY + 1, this.sitableBlockZ + 0.5D, this.field_75404_b);
		this.currentTick = 0;
		this.movement = 0;
		this.compoundDistance = 0;
		this.lastCheckedTick = 0;
		this.end = false;
		this.maxSittingTicks = this.theCreature.getRNG().nextInt(this.theCreature.getRNG().nextInt(1200) + 1200) + 1200;
	}

	@Override
	public void updateTask()
	{
		++this.currentTick;
		
		if (this.theCreature.getDistanceSq(this.sitableBlockX, this.sitableBlockY + 1, this.sitableBlockZ) > 1.0D)
		{
			this.theCreature.getNavigator().tryMoveToXYZ(this.sitableBlockX + 0.5D, this.sitableBlockY + 1, this.sitableBlockZ + 0.5D, this.field_75404_b);
			++this.movement;
			this.compoundDistance += this.theCreature.getDistance(this.theCreature.lastTickPosX, this.theCreature.lastTickPosY, this.theCreature.lastTickPosZ);
			if(this.currentTick - 40 > this.lastCheckedTick)
			{
				ArrayList<EntityChickenTFC> crowd = (ArrayList<EntityChickenTFC>) theCreature.worldObj.getEntitiesWithinAABB(EntityChickenTFC.class, theCreature.boundingBox.expand(24, 2, 24));
				ArrayList<EntityChickenTFC> invalid = new ArrayList<EntityChickenTFC>();
				for(EntityChickenTFC chicken : crowd){
					if(chicken.getGender().equals(GenderEnum.MALE) || chicken.isChild()){
						invalid.add(chicken);
					}
				}
				crowd.removeAll(invalid);
				crowd.remove(theCreature);
				if(this.compoundDistance < 0.5 || crowd.size() >= 10)
				{
					failureDepressionMap.put((this.sitableBlockX + "," + this.sitableBlockY + "," + this.sitableBlockZ), TFC_Time.getTotalTicks() + 24000);
					this.end = true;
				}
				else
				{
					this.lastCheckedTick = this.currentTick;
				}
			}
		}
		else
		{
			--this.movement;
		}
	}

	/**
	 * Determines whether the creature wants to sit on the block at given coordinate
	 */
	protected boolean isSittableBlock(World world, int x, int y, int z)
	{
		if(failureDepressionMap.containsKey((x + "," + y + "," + z)))
		{
			long time = failureDepressionMap.get((x + "," + y + "," + z));
			if(time > TFC_Time.getTotalTicks())
				return false;
			else
				failureDepressionMap.remove(new int[]{x, y, z});
		}

		Block block = world.getBlock(x, y, z);
		//int meta = world.getBlockMetadata(x, y, z);
		if (block == TFCBlocks.nestBox)
		{
			TENestBox tileentitynest = (TENestBox) world.getTileEntity(x, y, z);

			if (!tileentitynest.hasBird() || tileentitynest.getBird() == theCreature)
				return true;
		}

		return false;
	}
}
