package me.dmillerw.citizens.proxy;

import me.dmillerw.citizens.Citizens;
import me.dmillerw.citizens.entity.EntityCitizen;
import me.dmillerw.citizens.entity.EntitySeat;
import me.dmillerw.citizens.network.GuiHandler;
import me.dmillerw.citizens.network.PacketHandler;
import me.dmillerw.citizens.proxy.handlers.OnFmlEvent;
import me.dmillerw.citizens.worldgen.GenerateCampingSpot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class CommonProxy implements OnFmlEvent {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        PacketHandler.initialize();
        GuiHandler.initialize();

        EntityRegistry.registerModEntity(
                new ResourceLocation("citizens:citizen"),
                EntityCitizen.class,
                "citizen",
                0,
                Citizens.instance,
                80,
                3,
                true);

        EntityRegistry.registerModEntity(
                new ResourceLocation("citizens:seat"),
                EntitySeat.class,
                "seat",
                1,
                Citizens.instance,
                10,
                10,
                false);

        GenerateCampingSpot.register();
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }
}
