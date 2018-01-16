package me.dmillerw.duplicants.proxy;

import me.dmillerw.duplicants.client.render.entity.RenderDuplicant;
import me.dmillerw.duplicants.entity.EntityDuplicant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.UUID;

public class ClientProxy extends CommonProxy {

    public static UUID selectedDuplicant;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        RenderingRegistry.registerEntityRenderingHandler(EntityDuplicant.class, manager -> new RenderDuplicant(manager, 1F));
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public void onDuplicantSelected(EntityPlayer entityPlayer, EntityDuplicant entityDuplicant) {
        if (entityDuplicant != null) {
            selectedDuplicant = entityDuplicant.getPersistentID();
        } else {
            selectedDuplicant = null;
        }
    }
}
