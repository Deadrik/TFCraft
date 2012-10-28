package TFC.Food;

import java.util.List;

import net.minecraft.src.*;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.Player.TFC_PlayerClient;
import TFC.Core.Player.TFC_PlayerServer;
import TFC.Items.ItemTerra;
import TFC.Items.ItemTerraFood;

public class TFCMeal extends ItemTerra
{
	PotionEffect foodEffect;
	
	String texture;

	private boolean alwaysEdible = false;
	
	public TFCMeal(int id, String tex) 
    {
        super(id);
        texture = tex;
    }
	
	@Override
	public String getTextureFile()
	{
		return texture;
	}
	
	public void addInformation(ItemStack is, List arraylist) 
	{
	    if (is.hasTagCompound())
        {
            NBTTagCompound stackTagCompound = is.getTagCompound();

            if(stackTagCompound.hasKey("temperature"))
            {
                float temp = stackTagCompound.getFloat("temperature");
                float meltTemp = 0;
                
                meltTemp = TFC_ItemHeat.getMeltingPoint(is);

                if(meltTemp != -1)
                {
                    if(is.getItem() instanceof ItemFood)
                        arraylist.add(TFC_ItemHeat.getHeatColorFood(temp, meltTemp));
                }
            }
            
            if(foodEffect != null)
            	arraylist.add("Effect: " + StatCollector.translateToLocal(foodEffect.getEffectName()));
            
            int energy = getMealEnergy(is)/10;
            int power = getMealPower(is)/10;
            int filling = getMealFilling(is)/10;
            
            if(energy != -1)
            {
            	String stars = "";
            	int whitestars = energy > 5 ? 5 : energy;
            	int blackstars = energy > 5 ? energy-5 : 0;
            	whitestars -= blackstars;
            	
            	for(int i = 0; i < blackstars; i++)
            	{
            		stars += "\u272e";
            	}
            	for(int i = 0; i < whitestars; i++)
            	{
            		stars += "\u2729";
            	}
            	
            	arraylist.add("Energy: " + stars);
            }
            
            if(power != -1)
            {
            	String stars = "";
            	int whitestars = power > 5 ? 5 : power;
            	int blackstars = power > 5 ? power-5 : 0;
            	whitestars -= blackstars;
            	
            	for(int i = 0; i < blackstars; i++)
            	{
            		stars += "\u272e";
            	}
            	for(int i = 0; i < whitestars; i++)
            	{
            		stars += "\u2729";
            	}
            	
            	arraylist.add("Power: " + stars);
            }
            
            if(filling != -1)
            {
            	String stars = "";
            	int whitestars = filling > 5 ? 5 : filling;
            	int blackstars = filling > 5 ? filling-5 : 0;
            	whitestars -= blackstars;
            	
            	for(int i = 0; i < blackstars; i++)
            	{
            		stars += "\u272e";
            	}
            	for(int i = 0; i < whitestars; i++)
            	{
            		stars += "\u2729";
            	}
            	
            	arraylist.add("Filling: " + stars);
            }
            
        }
	}
	
	@Override
    public void onUpdate(ItemStack is, World world, Entity entity, int i, boolean isSelected) 
    {
        if (!world.isRemote && is.hasTagCompound())
        {
            NBTTagCompound stackTagCompound = is.getTagCompound();

            if(stackTagCompound.hasKey("temperature"))
            {
            	TFC_ItemHeat.HandleItemHeat(is, (int)entity.posX, (int)entity.posY, (int)entity.posZ);
            }
        }
    }
	
	@Override
	public ItemStack onFoodEaten(ItemStack is, World world, EntityPlayer player)
    {
        //--is.stackSize;
        TFC_PlayerServer playerServer = (TFC_PlayerServer) ((EntityPlayerMP)player).getServerPlayerBase("TFC Player Server");
        playerServer.getFoodStatsTFC().addStats(getMealFilling(is), (float)getMealEnergy(is)/100f);
        world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        this.addFoodEffect(is, world, player);
        is = new ItemStack(Item.bowlEmpty,1);
        return is;
    }
	
	public int getMealPower(ItemStack is)
	{
		if (is.hasTagCompound())
        {
            NBTTagCompound stackTagCompound = is.getTagCompound();

            if(stackTagCompound.hasKey("effectpower"))
            {
            	return stackTagCompound.getByte("effectpower");
            }
            else return -1;
        }
		return -1;
	}
	
	public int getMealFilling(ItemStack is)
	{
		if (is.hasTagCompound())
        {
            NBTTagCompound stackTagCompound = is.getTagCompound();

            if(stackTagCompound.hasKey("filling"))
            {
            	return stackTagCompound.getByte("filling");
            }
            else return -1;
        }
		return -1;
	}
	
	/**
	 * Energy is divided by 100 when it is sent to food stats to give a 0.0 - 1.0 float
	 * */
	public int getMealEnergy(ItemStack is)
	{
		if (is.hasTagCompound())
        {
            NBTTagCompound stackTagCompound = is.getTagCompound();

            if(stackTagCompound.hasKey("energy"))
            {
            	return stackTagCompound.getByte("energy");
            }
            else return -1;
        }
		return -1;
	}
	
	protected void addFoodEffect(ItemStack is, World world, EntityPlayer player)
    {
        if (!world.isRemote && this.foodEffect != null)
        {
        	int Power = 1 + (getMealPower(is)/100);

            player.addPotionEffect(new PotionEffect(foodEffect.getPotionID(), foodEffect.getDuration()*Power, foodEffect.getAmplifier()*Power));
        }
    }
	
	public boolean getShareTag()
    {
        return true;
    }
	
	/**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 64;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.eat;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
    {
    	if(!world.isRemote)
    	{
    	TFC_PlayerServer playerserver = ((TFC.Core.Player.TFC_PlayerServer)((EntityPlayerMP)player).getServerPlayerBase("TFC Player Server"));
    	
    	int energy = getMealEnergy(is)/100;
        int filling = getMealFilling(is);
        
        if (playerserver.getFoodStatsTFC().needFood() && filling+(filling / 3 * energy * 2.0F) <= 140)
        {
            player.setItemInUse(is, this.getMaxItemUseDuration(is));
        }
    	}

        return is;
    }
    
    /**
     * sets a potion effect on the item. Args: int potionId, int duration (will be multiplied by 20), int amplifier,
     * float probability of effect happening
     */
    public TFCMeal setPotionEffect(PotionEffect potioneffect)
    {
    	foodEffect = potioneffect;
        return this;
    }

    /**
     * Set the field 'alwaysEdible' to true, and make the food edible even if the player don't need to eat.
     */
    public TFCMeal setAlwaysEdible()
    {
        this.alwaysEdible = true;
        return this;
    }

}
