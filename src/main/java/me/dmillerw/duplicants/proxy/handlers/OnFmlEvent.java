package me.dmillerw.duplicants.proxy.handlers;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface OnFmlEvent {

    public void preInit(FMLPreInitializationEvent event);
    public void init(FMLInitializationEvent event);
    public void postInit(FMLPostInitializationEvent event);
}
