package me.dmillerw.duplicants.proxy;

import me.dmillerw.duplicants.Duplicants;
import me.dmillerw.duplicants.entity.EntityDuplicant;
import me.dmillerw.duplicants.handler.SelectionHandler;
import me.dmillerw.duplicants.network.PacketHandler;
import me.dmillerw.duplicants.proxy.handlers.OnDuplicantSelected;
import me.dmillerw.duplicants.proxy.handlers.OnFmlEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class CommonProxy implements OnFmlEvent, OnDuplicantSelected {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        PacketHandler.initialize();

        EntityRegistry.registerModEntity(new ResourceLocation("duplicants:duplicant"), EntityDuplicant.class, "duplicant", 0, Duplicants.instance, 80, 3, true, 0x000000, 0xFFFFFF);
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public void onDuplicantSelected(EntityPlayer entityPlayer, EntityDuplicant entityDuplicant) {
        SelectionHandler.INSTANCE.onDuplicantSelected(entityPlayer, entityDuplicant);
    }
}
