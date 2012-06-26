package net.minecraft.src.vazkii.updatemanager;

import net.minecraft.src.BaseMod;

/**
 * Early class for the new Update Managing method. This isn't implemented anywhere, don't even bother using it.
 * @author Vazkii
 */
public abstract class UMBaseMod {
	
	public BaseMod mod;
	
	public UMBaseMod(BaseMod mod){
		this.mod = mod;
		//UMCore.addMod(this);
	}
	
	public abstract String getUpdateURL();
	
	public abstract String getModURL();
	
	public abstract ModType getModType();
	
	public String getModName(){
		return null;
	}
	
	public String getChangelogURL(){
		return null;
	}
	
	public String getDirectDownloadLink(){
		return null;
	}
	
	public String getModAuthor(){
		return null;
	}

}
