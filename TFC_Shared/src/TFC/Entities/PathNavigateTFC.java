package TFC.Entities;

import net.minecraft.src.*;

public class PathNavigateTFC extends PathNavigate
{
	private EntityLiving theEntity;
	private World worldObj;

	/** The PathEntity being followed. */
	private PathEntity currentPath;
	private float speed;
	private boolean avoidLight;

	/**
	 * The number of blocks (extra) +/- in each axis that get pulled out as cache for the pathfinder's search space
	 */
	private float pathSearchRange;
	private boolean noSunPathfind = false;

	/** Time, in number of ticks, following the current path */
	private int totalTicks;

	/**
	 * The time when the last position check was done (to detect successful movement)
	 */
	private int ticksAtLastPos;

	/**
	 * Coordinates of the entity's position last time a check was done (part of monitoring getting 'stuck')
	 */
	private Vec3D lastPosCheck = Vec3D.createVectorHelper(0.0D, 0.0D, 0.0D);

	/**
	 * Specifically, if a wooden door block is even considered to be passable by the pathfinder
	 */
	private boolean canPassOpenWoodenDoors = true;

	/** If door blocks are considered passable even when closed */
	private boolean canPassClosedWoodenDoors = false;

	/** If water blocks are avoided (at least by the pathfinder) */
	private boolean avoidsWater = false;

	/**
	 * If the entity can swim. Swimming AI enables this and the pathfinder will also cause the entity to swim straight
	 * upwards when underwater
	 */
	private boolean canSwim = false;

	public PathNavigateTFC(EntityLiving par1EntityLiving, World par2World, float par3)
	{
		super(par1EntityLiving, par2World, par3);
		this.theEntity = par1EntityLiving;
		this.worldObj = par2World;
		this.pathSearchRange = par3;
	}

	public void setAvoidsWater(boolean par1)
	{
		this.avoidsWater = par1;
	}

	public boolean getAvoidsWater()
	{
		return this.avoidsWater;
	}

	public void setBreakDoors(boolean par1)
	{
		this.canPassClosedWoodenDoors = par1;
	}

	/**
	 * Sets if the entity can enter open doors
	 */
	 public void setEnterDoors(boolean par1)
	 {
		 this.canPassOpenWoodenDoors = par1;
	 }

	 /**
	  * Returns true if the entity can break doors, false otherwise
	  */
	 public boolean getCanBreakDoors()
	 {
		 return this.canPassClosedWoodenDoors;
	 }

	 /**
	  * Sets if the path should avoid sunlight
	  */
	 public void setAvoidSun(boolean par1)
	 {
		 this.noSunPathfind = par1;
	 }

	 /**
	  * Sets the speed
	  */
	 public void setSpeed(float par1)
	 {
		 this.speed = par1;
	 }

	 /**
	  * Sets if the entity can swim
	  */
	 public void setCanSwim(boolean par1)
	 {
		 this.canSwim = par1;
	 }

	 /**
	  * Returns the path to the given coordinates
	  */
	 public PathEntity getPathToXYZ(double par1, double par3, double par5)
	 {
		 return !this.canNavigate() ? null : this.worldObj.getEntityPathToXYZ(this.theEntity, MathHelper.floor_double(par1), (int)par3, MathHelper.floor_double(par5), this.pathSearchRange, this.canPassOpenWoodenDoors, this.canPassClosedWoodenDoors, this.avoidsWater, this.canSwim);
	 }

	 /**
	  * Try to find and set a path to XYZ. Returns true if successful.
	  */
	 public boolean tryMoveToXYZ(double par1, double par3, double par5, float par7)
	 {
		 PathEntity var8 = this.getPathToXYZ((double)MathHelper.floor_double(par1), (double)((int)par3), (double)MathHelper.floor_double(par5));
		 return this.setPath(var8, par7);
	 }

	 /**
	  * Returns the path to the given EntityLiving
	  */
	 public PathEntity getPathToEntityLiving(EntityLiving par1EntityLiving)
	 {
		 return !this.canNavigate() ? null : this.worldObj.getPathEntityToEntity(this.theEntity, par1EntityLiving, this.pathSearchRange, this.canPassOpenWoodenDoors, this.canPassClosedWoodenDoors, this.avoidsWater, this.canSwim);
	 }

	 /**
	  * Try to find and set a path to EntityLiving. Returns true if successful.
	  */
	 public boolean tryMoveToEntityLiving(EntityLiving par1EntityLiving, float par2)
	 {
		 PathEntity var3 = this.getPathToEntityLiving(par1EntityLiving);
		 return var3 != null ? this.setPath(var3, par2) : false;
	 }

	 /**
	  * sets the active path data if path is 100% unique compared to old path, checks to adjust path for sun avoiding
	  * ents and stores end coords
	  */
	 
	 public void setAvoidsLight(boolean b){
	    	avoidLight = b;
	    }

	    public boolean getAvoidsLight(){
	    	return avoidLight;
	    }
	 public boolean setPath(PathEntity par1PathEntity, float par2)
	 {
		 if (par1PathEntity == null)
		 {
			 this.currentPath = null;
			 return false;
		 }
		 else
		 {
			 if (!par1PathEntity.isSamePath(this.currentPath))
			 {
				 this.currentPath = par1PathEntity;
			 }

			 if (this.noSunPathfind)
			 {
				 this.removeSunnyPath();
			 }
			 if (this.avoidLight && !this.worldObj.isDaytime())
	            {
	                this.removeLightedPath();
	            }
			 if (this.currentPath.getCurrentPathLength() == 0)
			 {
				 return false;
			 }
			 else
			 {
				 this.speed = par2;
				 Vec3D var3 = this.getEntityPosition();
				 this.ticksAtLastPos = this.totalTicks;
				 this.lastPosCheck.xCoord = var3.xCoord;
				 this.lastPosCheck.yCoord = var3.yCoord;
				 this.lastPosCheck.zCoord = var3.zCoord;
				 return true;
			 }
		 }
	 }

	 /**
	  * gets the actively used PathEntity
	  */
	 public PathEntity getPath()
	 {
		 return this.currentPath;
	 }

	 public void onUpdateNavigation()
	 {
		 ++this.totalTicks;

		 if (!this.noPath())
		 {
			 if (this.canNavigate())
			 {
				 this.pathFollow();
			 }

			 if (!this.noPath())
			 {
				 Vec3D var1 = this.currentPath.getPosition(this.theEntity);

				 if (var1 != null)
				 {
					 this.theEntity.getMoveHelper().setMoveTo(var1.xCoord, var1.yCoord, var1.zCoord, this.speed);
				 }
			 }
		 }
	 }

	 private void pathFollow()
	 {
		 Vec3D var1 = this.getEntityPosition();
		 int var2 = this.currentPath.getCurrentPathLength();

		 for (int var3 = this.currentPath.getCurrentPathIndex(); var3 < this.currentPath.getCurrentPathLength(); ++var3)
		 {
			 if (this.currentPath.getPathPointFromIndex(var3).yCoord != (int)var1.yCoord)
			 {
				 var2 = var3;
				 break;
			 }
		 }

		 float var8 = this.theEntity.width * this.theEntity.width;
		 int var4;

		 for (var4 = this.currentPath.getCurrentPathIndex(); var4 < var2; ++var4)
		 {
			 if (var1.squareDistanceTo(this.currentPath.getVectorFromIndex(this.theEntity, var4)) < (double)var8)
			 {
				 this.currentPath.setCurrentPathIndex(var4 + 1);
			 }
		 }

		 var4 = (int)Math.ceil((double)this.theEntity.width);
		 int var5 = (int)this.theEntity.height + 1;
		 int var6 = var4;

		 for (int var7 = var2 - 1; var7 >= this.currentPath.getCurrentPathIndex(); --var7)
		 {
			 if (this.isDirectPathBetweenPoints(var1, this.currentPath.getVectorFromIndex(this.theEntity, var7), var4, var5, var6))
			 {
				 this.currentPath.setCurrentPathIndex(var7);
				 break;
			 }
		 }

		 if (this.totalTicks - this.ticksAtLastPos > 100)
		 {
			 if (var1.squareDistanceTo(this.lastPosCheck) < 2.25D)
			 {
				 this.clearPathEntity();
			 }

			 this.ticksAtLastPos = this.totalTicks;
			 this.lastPosCheck.xCoord = var1.xCoord;
			 this.lastPosCheck.yCoord = var1.yCoord;
			 this.lastPosCheck.zCoord = var1.zCoord;
		 }
	 }

	 /**
	  * If null path or reached the end
	  */
	  public boolean noPath()
	 {
		 return this.currentPath == null || this.currentPath.isFinished();
	 }

	 /**
	  * sets active PathEntity to null
	  */
	  public void clearPathEntity()
	 {
		 this.currentPath = null;
	 }

	 private Vec3D getEntityPosition()
	 {
		 return Vec3D.createVector(this.theEntity.posX, (double)this.getPathableYPos(), this.theEntity.posZ);
	 }

	 /**
	  * Gets the safe pathing Y position for the entity depending on if it can path swim or not
	  */
	 private int getPathableYPos()
	 {
		 if (this.theEntity.isInWater() && this.canSwim)
		 {
			 int var1 = (int)this.theEntity.boundingBox.minY;
			 int var2 = this.worldObj.getBlockId(MathHelper.floor_double(this.theEntity.posX), var1, MathHelper.floor_double(this.theEntity.posZ));
			 int var3 = 0;

			 do
			 {
				 if (var2 != Block.waterMoving.blockID && var2 != Block.waterStill.blockID)
				 {
					 return var1;
				 }

				 ++var1;
				 var2 = this.worldObj.getBlockId(MathHelper.floor_double(this.theEntity.posX), var1, MathHelper.floor_double(this.theEntity.posZ));
				 ++var3;
			 }
			 while (var3 <= 16);

			 return (int)this.theEntity.boundingBox.minY;
		 }
		 else
		 {
			 return (int)(this.theEntity.boundingBox.minY + 0.5D);
		 }
	 }

	 /**
	  * If on ground or swimming and can swim
	  */
	 private boolean canNavigate()
	 {
		 return this.theEntity.onGround || this.canSwim && this.isInFluid();
	 }

	 private void removeLightedPath()
	    {
	        if (this.worldObj.getBlockLightValue(MathHelper.floor_double(this.theEntity.posX), (int)(this.theEntity.boundingBox.minY + 0.5D), MathHelper.floor_double(this.theEntity.posZ))<7)
	        {
	            for (int var1 = 0; var1 < this.currentPath.getCurrentPathLength(); ++var1)
	            {
	                PathPoint var2 = this.currentPath.getPathPointFromIndex(var1);

	                if (this.worldObj.getBlockLightValue(var2.xCoord, var2.yCoord, var2.zCoord)>7)
	                {
	                    this.currentPath.setCurrentPathLength(var1 - 1);
	                    return;
	                }
	            }
	        }
	    }
	 
	 /**
	  * Returns true if the entity is in water or lava, false otherwise
	  */
	 private boolean isInFluid()
	 {
		 return this.theEntity.isInWater() || this.theEntity.handleLavaMovement();
	 }

	 /**
	  * Trims path data from the end to the first sun covered block
	  */
	 private void removeSunnyPath()
	 {
		 if (!this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.theEntity.posX), (int)(this.theEntity.boundingBox.minY + 0.5D), MathHelper.floor_double(this.theEntity.posZ)))
		 {
			 for (int var1 = 0; var1 < this.currentPath.getCurrentPathLength(); ++var1)
			 {
				 PathPoint var2 = this.currentPath.getPathPointFromIndex(var1);

				 if (this.worldObj.canBlockSeeTheSky(var2.xCoord, var2.yCoord, var2.zCoord))
				 {
					 this.currentPath.setCurrentPathLength(var1 - 1);
					 return;
				 }
			 }
		 }
	 }

	 /**
	  * Returns true when an entity of specified size could safely walk in a straight line between the two points. Args:
	  * pos1, pos2, entityXSize, entityYSize, entityZSize
	  */
	 private boolean isDirectPathBetweenPoints(Vec3D par1Vec3D, Vec3D par2Vec3D, int par3, int par4, int par5)
	 {
		 int var6 = MathHelper.floor_double(par1Vec3D.xCoord);
		 int var7 = MathHelper.floor_double(par1Vec3D.zCoord);
		 double var8 = par2Vec3D.xCoord - par1Vec3D.xCoord;
		 double var10 = par2Vec3D.zCoord - par1Vec3D.zCoord;
		 double var12 = var8 * var8 + var10 * var10;

		 if (var12 < 1.0E-8D)
		 {
			 return false;
		 }
		 else
		 {
			 double var14 = 1.0D / Math.sqrt(var12);
			 var8 *= var14;
			 var10 *= var14;
			 par3 += 2;
			 par5 += 2;

			 if (!this.isSafeToStandAt(var6, (int)par1Vec3D.yCoord, var7, par3, par4, par5, par1Vec3D, var8, var10))
			 {
				 return false;
			 }
			 else
			 {
				 par3 -= 2;
				 par5 -= 2;
				 double var16 = 1.0D / Math.abs(var8);
				 double var18 = 1.0D / Math.abs(var10);
				 double var20 = (double)(var6 * 1) - par1Vec3D.xCoord;
				 double var22 = (double)(var7 * 1) - par1Vec3D.zCoord;

				 if (var8 >= 0.0D)
				 {
					 ++var20;
				 }

				 if (var10 >= 0.0D)
				 {
					 ++var22;
				 }

				 var20 /= var8;
				 var22 /= var10;
				 int var24 = var8 < 0.0D ? -1 : 1;
				 int var25 = var10 < 0.0D ? -1 : 1;
				 int var26 = MathHelper.floor_double(par2Vec3D.xCoord);
				 int var27 = MathHelper.floor_double(par2Vec3D.zCoord);
				 int var28 = var26 - var6;
				 int var29 = var27 - var7;

				 do
				 {
					 if (var28 * var24 <= 0 && var29 * var25 <= 0)
					 {
						 return true;
					 }

					 if (var20 < var22)
					 {
						 var20 += var16;
						 var6 += var24;
						 var28 = var26 - var6;
					 }
					 else
					 {
						 var22 += var18;
						 var7 += var25;
						 var29 = var27 - var7;
					 }
				 }
				 while (this.isSafeToStandAt(var6, (int)par1Vec3D.yCoord, var7, par3, par4, par5, par1Vec3D, var8, var10));

				 return false;
			 }
		 }
	 }

	 /**
	  * Returns true when an entity could stand at a position, including solid blocks under the entire entity. Args:
	  * xOffset, yOffset, zOffset, entityXSize, entityYSize, entityZSize, originPosition, vecX, vecZ
	  */
	 private boolean isSafeToStandAt(int par1, int par2, int par3, int par4, int par5, int par6, Vec3D par7Vec3D, double par8, double par10)
	 {
		 int var12 = par1 - par4 / 2;
		 int var13 = par3 - par6 / 2;

		 if (!this.isPositionClear(var12, par2, var13, par4, par5, par6, par7Vec3D, par8, par10))
		 {
			 return false;
		 }
		 else
		 {
			 for (int var14 = var12; var14 < var12 + par4; ++var14)
			 {
				 for (int var15 = var13; var15 < var13 + par6; ++var15)
				 {
					 double var16 = (double)var14 + 0.5D - par7Vec3D.xCoord;
					 double var18 = (double)var15 + 0.5D - par7Vec3D.zCoord;

					 if (var16 * par8 + var18 * par10 >= 0.0D)
					 {
						 int var20 = this.worldObj.getBlockId(var14, par2 - 1, var15);

						 if (var20 <= 0)
						 {
							 return false;
						 }

						 Material var21 = Block.blocksList[var20].blockMaterial;

						 if (var21 == Material.water && !this.theEntity.isInWater())
						 {
							 return false;
						 }

						 if (var21 == Material.lava)
						 {
							 return false;
						 }
					 }
				 }
			 }

			 return true;
		 }
	 }

	 /**
	  * Returns true if an entity does not collide with any solid blocks at the position. Args: xOffset, yOffset,
	  * zOffset, entityXSize, entityYSize, entityZSize, originPosition, vecX, vecZ
	  */
	 private boolean isPositionClear(int par1, int par2, int par3, int par4, int par5, int par6, Vec3D par7Vec3D, double par8, double par10)
	 {
		 for (int var12 = par1; var12 < par1 + par4; ++var12)
		 {
			 for (int var13 = par2; var13 < par2 + par5; ++var13)
			 {
				 for (int var14 = par3; var14 < par3 + par6; ++var14)
				 {
					 double var15 = (double)var12 + 0.5D - par7Vec3D.xCoord;
					 double var17 = (double)var14 + 0.5D - par7Vec3D.zCoord;

					 if (var15 * par8 + var17 * par10 >= 0.0D)
					 {
						 int var19 = this.worldObj.getBlockId(var12, var13, var14);

						 if (var19 > 0 && !Block.blocksList[var19].getBlocksMovement(this.worldObj, var12, var13, var14))
						 {
							 return false;
						 }
					 }
				 }
			 }
		 }

		 return true;
	 }
}
/*
    private EntityLiving theEntity;
    private World worldObj;


    private PathEntity currentPath;
    private float speed;

    private boolean avoidLight;


    private float pathSearchRange;
    private boolean noSunPathfind = false;


    private int totalTicks;

    private int ticksAtLastPos;

    private Vec3D lastPosCheck = Vec3D.createVectorHelper(0.0D, 0.0D, 0.0D);


    private boolean canPassOpenWoodenDoors = true;


    private boolean canPassClosedWoodenDoors = false;


    private boolean avoidsWater = false;

    private boolean canSwim = false;

    public PathNavigateTFC(EntityLiving par1EntityLiving, World par2World, float par3)
    {
    	super(par1EntityLiving, par2World, par3);
        this.theEntity = par1EntityLiving;
        this.worldObj = par2World;
        this.pathSearchRange = par3;
    }
    public void setAvoidsLight(boolean b){
    	avoidLight = b;
    }

    public boolean getAvoidsLight(){
    	return avoidLight;
    }
    public boolean setPath(PathEntity par1PathEntity, float par2)
    {
        if (par1PathEntity == null)
        {
            this.currentPath = null;
            return false;
        }
        else
        {
            if (this.avoidLight && !this.worldObj.isDaytime())
            {
                this.removeLightedPath();
            }
        }
        return super.setPath(par1PathEntity, par2);
    }


    private void pathFollow()
    {
        Vec3D var1 = this.getEntityPosition();
        int var2 = this.currentPath.getCurrentPathLength();

        for (int var3 = this.currentPath.getCurrentPathIndex(); var3 < this.currentPath.getCurrentPathLength(); ++var3)
        {
            if (this.currentPath.getPathPointFromIndex(var3).yCoord != (int)var1.yCoord)
            {
                var2 = var3;
                break;
            }
        }

        float var8 = this.theEntity.width * this.theEntity.width;
        int var4;

        for (var4 = this.currentPath.getCurrentPathIndex(); var4 < var2; ++var4)
        {
            if (var1.squareDistanceTo(this.currentPath.getVectorFromIndex(this.theEntity, var4)) < (double)var8)
            {
                this.currentPath.setCurrentPathIndex(var4 + 1);
            }
        }

        var4 = (int)Math.ceil((double)this.theEntity.width);
        int var5 = (int)this.theEntity.height + 1;
        int var6 = var4;

        for (int var7 = var2 - 1; var7 >= this.currentPath.getCurrentPathIndex(); --var7)
        {
            if (this.isDirectPathBetweenPoints(var1, this.currentPath.getVectorFromIndex(this.theEntity, var7), var4, var5, var6))
            {
                this.currentPath.setCurrentPathIndex(var7);
                break;
            }
        }

        if (this.totalTicks - this.ticksAtLastPos > 100)
        {
            if (var1.squareDistanceTo(this.lastPosCheck) < 2.25D)
            {
                this.clearPathEntity();
            }

            this.ticksAtLastPos = this.totalTicks;
            this.lastPosCheck.xCoord = var1.xCoord;
            this.lastPosCheck.yCoord = var1.yCoord;
            this.lastPosCheck.zCoord = var1.zCoord;
        }
    }

    private Vec3D getEntityPosition()
    {
        return Vec3D.createVector(this.theEntity.posX, (double)this.getPathableYPos(), this.theEntity.posZ);
    }


    private int getPathableYPos()
    {
        if (this.theEntity.isInWater() && this.canSwim)
        {
            int var1 = (int)this.theEntity.boundingBox.minY;
            int var2 = this.worldObj.getBlockId(MathHelper.floor_double(this.theEntity.posX), var1, MathHelper.floor_double(this.theEntity.posZ));
            int var3 = 0;

            do
            {
                if (var2 != Block.waterMoving.blockID && var2 != Block.waterStill.blockID)
                {
                    return var1;
                }

                ++var1;
                var2 = this.worldObj.getBlockId(MathHelper.floor_double(this.theEntity.posX), var1, MathHelper.floor_double(this.theEntity.posZ));
                ++var3;
            }
            while (var3 <= 16);

            return (int)this.theEntity.boundingBox.minY;
        }
        else
        {
            return (int)(this.theEntity.boundingBox.minY + 0.5D);
        }
    }


    private boolean canNavigate()
    {
        return this.theEntity.onGround || this.canSwim && this.isInFluid();
    }


    private boolean isInFluid()
    {
        return this.theEntity.isInWater() || this.theEntity.handleLavaMovement();
    }


    private void removeLightedPath()
    {
        if (this.worldObj.getBlockLightValue(MathHelper.floor_double(this.theEntity.posX), (int)(this.theEntity.boundingBox.minY + 0.5D), MathHelper.floor_double(this.theEntity.posZ))<7)
        {
            for (int var1 = 0; var1 < this.currentPath.getCurrentPathLength(); ++var1)
            {
                PathPoint var2 = this.currentPath.getPathPointFromIndex(var1);

                if (this.worldObj.getBlockLightValue(var2.xCoord, var2.yCoord, var2.zCoord)>7)
                {
                    this.currentPath.setCurrentPathLength(var1 - 1);
                    return;
                }
            }
        }
    }


    private boolean isDirectPathBetweenPoints(Vec3D par1Vec3D, Vec3D par2Vec3D, int par3, int par4, int par5)
    {
        int var6 = MathHelper.floor_double(par1Vec3D.xCoord);
        int var7 = MathHelper.floor_double(par1Vec3D.zCoord);
        double var8 = par2Vec3D.xCoord - par1Vec3D.xCoord;
        double var10 = par2Vec3D.zCoord - par1Vec3D.zCoord;
        double var12 = var8 * var8 + var10 * var10;

        if (var12 < 1.0E-8D)
        {
            return false;
        }
        else
        {
            double var14 = 1.0D / Math.sqrt(var12);
            var8 *= var14;
            var10 *= var14;
            par3 += 2;
            par5 += 2;

            if (!this.isSafeToStandAt(var6, (int)par1Vec3D.yCoord, var7, par3, par4, par5, par1Vec3D, var8, var10))
            {
                return false;
            }
            else
            {
                par3 -= 2;
                par5 -= 2;
                double var16 = 1.0D / Math.abs(var8);
                double var18 = 1.0D / Math.abs(var10);
                double var20 = (double)(var6 * 1) - par1Vec3D.xCoord;
                double var22 = (double)(var7 * 1) - par1Vec3D.zCoord;

                if (var8 >= 0.0D)
                {
                    ++var20;
                }

                if (var10 >= 0.0D)
                {
                    ++var22;
                }

                var20 /= var8;
                var22 /= var10;
                int var24 = var8 < 0.0D ? -1 : 1;
                int var25 = var10 < 0.0D ? -1 : 1;
                int var26 = MathHelper.floor_double(par2Vec3D.xCoord);
                int var27 = MathHelper.floor_double(par2Vec3D.zCoord);
                int var28 = var26 - var6;
                int var29 = var27 - var7;

                do
                {
                    if (var28 * var24 <= 0 && var29 * var25 <= 0)
                    {
                        return true;
                    }

                    if (var20 < var22)
                    {
                        var20 += var16;
                        var6 += var24;
                        var28 = var26 - var6;
                    }
                    else
                    {
                        var22 += var18;
                        var7 += var25;
                        var29 = var27 - var7;
                    }
                }
                while (this.isSafeToStandAt(var6, (int)par1Vec3D.yCoord, var7, par3, par4, par5, par1Vec3D, var8, var10));

                return false;
            }
        }
    }


    private boolean isSafeToStandAt(int par1, int par2, int par3, int par4, int par5, int par6, Vec3D par7Vec3D, double par8, double par10)
    {
        int var12 = par1 - par4 / 2;
        int var13 = par3 - par6 / 2;

        if (!this.isPositionClear(var12, par2, var13, par4, par5, par6, par7Vec3D, par8, par10))
        {
            return false;
        }
        else
        {
            for (int var14 = var12; var14 < var12 + par4; ++var14)
            {
                for (int var15 = var13; var15 < var13 + par6; ++var15)
                {
                    double var16 = (double)var14 + 0.5D - par7Vec3D.xCoord;
                    double var18 = (double)var15 + 0.5D - par7Vec3D.zCoord;

                    if (var16 * par8 + var18 * par10 >= 0.0D)
                    {
                        int var20 = this.worldObj.getBlockId(var14, par2 - 1, var15);

                        if (var20 <= 0)
                        {
                            return false;
                        }

                        Material var21 = Block.blocksList[var20].blockMaterial;

                        if (var21 == Material.water && !this.theEntity.isInWater())
                        {
                            return false;
                        }

                        if (var21 == Material.lava)
                        {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    }

    private boolean isPositionClear(int par1, int par2, int par3, int par4, int par5, int par6, Vec3D par7Vec3D, double par8, double par10)
    {
        for (int var12 = par1; var12 < par1 + par4; ++var12)
        {
            for (int var13 = par2; var13 < par2 + par5; ++var13)
            {
                for (int var14 = par3; var14 < par3 + par6; ++var14)
                {
                    double var15 = (double)var12 + 0.5D - par7Vec3D.xCoord;
                    double var17 = (double)var14 + 0.5D - par7Vec3D.zCoord;

                    if (var15 * par8 + var17 * par10 >= 0.0D)
                    {
                        int var19 = this.worldObj.getBlockId(var12, var13, var14);

                        if (var19 > 0 && !Block.blocksList[var19].getBlocksMovement(this.worldObj, var12, var13, var14))
                        {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}
 */