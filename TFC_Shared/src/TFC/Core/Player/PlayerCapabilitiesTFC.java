package TFC.Core.Player;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.PlayerCapabilities;

public class PlayerCapabilitiesTFC extends PlayerCapabilities 
{
	public float walkSpeed = 0.1f;
	
	public float getWalkSpeed()
    {
        return this.walkSpeed;
    }
	
	public void setWalkSpeed(float f)
    {
		walkSpeed = f;
    }
	
	public void writeCapabilitiesToNBT(NBTTagCompound par1NBTTagCompound)
    {
		super.writeCapabilitiesToNBT(par1NBTTagCompound);
        NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("abilities");
        var2.setFloat("walkSpeed", this.walkSpeed);
        par1NBTTagCompound.setTag("abilities", var2);
    }

    public void readCapabilitiesFromNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.readCapabilitiesFromNBT(par1NBTTagCompound);
        if (par1NBTTagCompound.hasKey("abilities"))
        {
            NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("abilities");
            this.walkSpeed = var2.getFloat("walkSpeed");
        }
    }
}
