package me.dmillerw.citizens.common.proxy;

import me.dmillerw.citizens.Citizens;
import me.dmillerw.citizens.common.colony.ColonyManager;
import me.dmillerw.citizens.common.entity.EntityCitizen;
import me.dmillerw.citizens.common.entity.EntitySeat;
import me.dmillerw.citizens.common.network.GuiHandler;
import me.dmillerw.citizens.common.network.PacketHandler;
import me.dmillerw.citizens.common.proxy.handlers.OnFmlEvent;
import me.dmillerw.citizens.common.util.CitizenDataSerializers;
import me.dmillerw.citizens.common.worldgen.GenerateCampingSpot;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class CommonProxy implements OnFmlEvent {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        PacketHandler.initialize();
        GuiHandler.initialize();

        DataSerializers.registerSerializer(CitizenDataSerializers.JAVA_OPTIONAL_STRING);
        DataSerializers.registerSerializer(CitizenDataSerializers.JAVA_OPTIONAL_UUID);

        MinecraftForge.EVENT_BUS.register(ColonyManager.class);

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
