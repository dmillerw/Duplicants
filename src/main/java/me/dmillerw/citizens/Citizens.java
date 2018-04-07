package me.dmillerw.citizens;

import me.dmillerw.citizens.common.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static me.dmillerw.citizens.Citizens.Info.MOD_ID;
import static me.dmillerw.citizens.Citizens.Info.MOD_NAME;

@Mod(modid = MOD_ID, name = MOD_NAME)
public class Citizens {

    public static final class Info {

        public static final String MOD_ID = "citizens";
        public static final String MOD_NAME = "Citizens";
    }

    @Mod.Instance(MOD_ID)
    public static Citizens instance;

    @SidedProxy(serverSide = "me.dmillerw.citizens.common.proxy.CommonProxy", clientSide = "me.dmillerw.citizens.common.proxy.ClientProxy")
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
