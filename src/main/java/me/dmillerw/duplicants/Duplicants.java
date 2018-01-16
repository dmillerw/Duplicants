package me.dmillerw.duplicants;

import me.dmillerw.duplicants.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static me.dmillerw.duplicants.Duplicants.Info.MOD_ID;
import static me.dmillerw.duplicants.Duplicants.Info.MOD_NAME;

@Mod(modid = MOD_ID, name = MOD_NAME)
public class Duplicants {

    public static final class Info {

        public static final String MOD_ID = "duplicants";
        public static final String MOD_NAME = "Duplicants";
    }

    @Mod.Instance(MOD_ID)
    public static Duplicants instance;

    @SidedProxy(serverSide = "me.dmillerw.duplicants.proxy.CommonProxy", clientSide = "me.dmillerw.duplicants.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
