package net.minecraft.src.TFC_Core;

import net.minecraft.src.*;
import net.minecraft.src.forge.*;

public class ItemTerraBucket  extends Item implements ITextureProvider{
    
	public ItemTerraBucket(int i)
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		this.maxStackSize = 1;
	}
	
	public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        --par1ItemStack.stackSize;

        if (par1ItemStack.getItemDamage() == 17)
        {
	        if (!par2World.isRemote)
	        {
	            par3EntityPlayer.clearActivePotions();
	        }
        }
        else if (par1ItemStack.getItemDamage() > 8)
        {
        	if (!par2World.isRemote)
	        {
        		par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 600, 0));
	        }
        }

        return par1ItemStack.stackSize <= 0 ? new ItemStack(this, 1, 0) : par1ItemStack;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.drink;
    }

	public static String[] bucketNames = {"Empty","Fresh Water","Salt Water","Milk"};
	
	@Override
	public String getItemNameIS(ItemStack itemstack) 
	{
		String s = super.getItemName();
		if (itemstack.getItemDamage() == 0)
		{
			s = new StringBuilder().append(super.getItemName()).append(".").append(bucketNames[0]).toString();
		}
		if (itemstack.getItemDamage() > 0 && itemstack.getItemDamage() < 9)
		{
			s = new StringBuilder().append(super.getItemName()).append(".").append(bucketNames[1]).toString();
		}
		if (itemstack.getItemDamage() > 8 && itemstack.getItemDamage() < 17)
		{
			s = new StringBuilder().append(super.getItemName()).append(".").append(bucketNames[2]).toString();
		}
		if (itemstack.getItemDamage() == 17)
		{
			s = new StringBuilder().append(super.getItemName()).append(".").append(bucketNames[3]).toString();
		}
		return s;
	}
	
	public int getIconFromDamage(int par1)
	{
		if (par1 == 0)
			return this.iconIndex;
		else if (par1 == 17)
			return this.iconIndex+2;
		else
			return this.iconIndex+1;
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terratools.png";
	}
	
	@Override
	public void addCreativeItems(java.util.ArrayList list)
	{
		list.add(new ItemStack(this,1,0));
		list.add(new ItemStack(this,1,1));
		list.add(new ItemStack(this,1,9));
		list.add(new ItemStack(this,1,17));
	}
	
	/**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        float var4 = 1.0F;
        double var5 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * (double)var4;
        double var7 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * (double)var4 + 1.62D - (double)par3EntityPlayer.yOffset;
        double var9 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * (double)var4;
        MovingObjectPosition var12 = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, par1ItemStack.getItemDamage() == 0);

        if (var12 == null )
        {
        	if (par1ItemStack.getItemDamage() != 0)
        	{
        		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        	}
            return par1ItemStack;
        }
        else
        {
            if (var12.typeOfHit == EnumMovingObjectType.TILE)
            {
                int var13 = var12.blockX;
                int var14 = var12.blockY;
                int var15 = var12.blockZ;

                if (!par2World.canMineBlock(par3EntityPlayer, var13, var14, var15))
                {
                    return par1ItemStack;
                }
                
                if (par1ItemStack.getItemDamage() == 0)
                {
                    if (!par3EntityPlayer.canPlayerEdit(var13, var14, var15))
                    {
                        return par1ItemStack;
                    }

                    if (par2World.getBlockId(var13, var14, var15) == mod_TFC_Core.finiteWater.blockID)
                    {
                    	int meta = par2World.getBlockMetadata(var13, var14, var15);
                        par2World.setBlockWithNotify(var13, var14, var15, 0);

                        if (par3EntityPlayer.capabilities.isCreativeMode)
                        {
                            return par1ItemStack;
                        }

                    	boolean ocean = false;
                        for (int i = - 50; i < 51; i++)
                        {
                        	for (int j = - 50; j < 51; j++)
                            {
                        		if (par2World.getBiomeGenForCoords(var13 + i, var15 + j).biomeName.contains("Ocean"))
                        		{
                        			ocean = true;
                        			break;
                        		}
                            }
                        }
                        
                        if (ocean)
                        {
                            return new ItemStack(this, 1, meta + 9);
                        }
                        else
                        {
                        	return new ItemStack(this, 1, meta + 1);
                        }
                    }

                    if (par2World.getBlockId(var13, var14, var15) == mod_TFC_Core.finiteSaltWater.blockID)
                    {
                    	int meta = par2World.getBlockMetadata(var13, var14, var15);
                        par2World.setBlockWithNotify(var13, var14, var15, 0);

                        if (par3EntityPlayer.capabilities.isCreativeMode)
                        {
                            return par1ItemStack;
                        }

                        return new ItemStack(this, 1, meta + 9);
                    }
                    
                    if (par2World.getBlockMaterial(var13, var14, var15) == Material.water)
                    {
                    	int meta = par2World.getBlockMetadata(var13, var14, var15);

                        if (par3EntityPlayer.capabilities.isCreativeMode)
                        {
                            return par1ItemStack;
                        }

                        if (par2World.getBiomeGenForCoords(var13, var15).biomeName.contains("Ocean") || par2World.getBiomeGenForCoords(var13, var15).biomeName.contains("Swamp"))
                        {
                            return new ItemStack(this, 1, meta + 9);
                        }
                        else
                        {
                        	boolean ocean = false;
                            for (int i = - 50; i < 51; i++)
                            {
                            	for (int j = - 50; j < 51; j++)
                                {
                            		if (par2World.getBiomeGenForCoords(var13 + i, var15 + j).biomeName.contains("Ocean") || par2World.getBiomeGenForCoords(var13 + i, var15 + j).biomeName.contains("Swamp"))
                            		{
                            			ocean = true;
                            			break;
                            		}
                                }
                            }
                            if (ocean)
                            {
                                return new ItemStack(this, 1, meta + 9);
                            }
                            else
                            {
                            	return new ItemStack(this, 1, meta + 1);
                            }
                        }
                    }
                }
                else if (par1ItemStack.getItemDamage() != 17)
                {
                    if (par1ItemStack.getItemDamage() < 0)
                    {
                        return new ItemStack(this, 1, 0);
                    }

                    if (var12.sideHit == 0)
                    {
                        --var14;
                    }

                    if (var12.sideHit == 1)
                    {
                        ++var14;
                    }

                    if (var12.sideHit == 2)
                    {
                        --var15;
                    }

                    if (var12.sideHit == 3)
                    {
                        ++var15;
                    }

                    if (var12.sideHit == 4)
                    {
                        --var13;
                    }

                    if (var12.sideHit == 5)
                    {
                        ++var13;
                    }

                    if (!par3EntityPlayer.canPlayerEdit(var13, var14, var15))
                    {
                        return par1ItemStack;
                    }

                    if (par2World.isAirBlock(var13, var14, var15) || !par2World.getBlockMaterial(var13, var14, var15).isSolid())
                    {
                        if (par2World.worldProvider.isHellWorld && par1ItemStack.getItemDamage() > 0 && par1ItemStack.getItemDamage() < 17)
                        {
                            par2World.playSoundEffect(var5 + 0.5D, var7 + 0.5D, var9 + 0.5D, "random.fizz", 0.5F, 2.6F + (par2World.rand.nextFloat() - par2World.rand.nextFloat()) * 0.8F);

                            for (int var16 = 0; var16 < 8; ++var16)
                            {
                                par2World.spawnParticle("largesmoke", (double)var13 + Math.random(), (double)var14 + Math.random(), (double)var15 + Math.random(), 0.0D, 0.0D, 0.0D);
                            }
                        }
                        else
                        {
                        	if (par1ItemStack.getItemDamage() > 0 && par1ItemStack.getItemDamage() < 9)
                        	{
                        		par2World.setBlockAndMetadataWithNotify(var13, var14, var15, mod_TFC_Core.finiteWater.blockID, par1ItemStack.getItemDamage() - 1);
                        	}
                        	else if (par1ItemStack.getItemDamage() > 8 && par1ItemStack.getItemDamage() < 17)
                        	{
                        		par2World.setBlockAndMetadataWithNotify(var13, var14, var15, mod_TFC_Core.finiteSaltWater.blockID, par1ItemStack.getItemDamage() - 9);
                        	}
                        }

                        if (par3EntityPlayer.capabilities.isCreativeMode)
                        {
                            return par1ItemStack;
                        }

                        return new ItemStack(this, 1, 0);
                    }
                }
                else
                {
                    par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
                }
            }
            else if (par1ItemStack.getItemDamage() == 0 && var12.entityHit instanceof EntityCow)
            {
                return new ItemStack(this, 1, 17);
            }

            return par1ItemStack;
        }
    }

}
