package net.minecraft.src.TFC_Core;

import net.minecraft.src.World;

public abstract interface ITickCallback
{
  public abstract void tickCallback(World world);
}
