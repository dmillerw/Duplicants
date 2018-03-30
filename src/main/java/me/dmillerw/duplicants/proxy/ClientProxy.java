package me.dmillerw.duplicants.proxy;

import me.dmillerw.duplicants.client.render.entity.RenderBottledSouls;
import me.dmillerw.duplicants.client.render.entity.RenderDuplicant;
import me.dmillerw.duplicants.entity.EntityBottledSouls;
import me.dmillerw.duplicants.entity.EntityDuplicant;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.UUID;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        RenderingRegistry.registerEntityRenderingHandler(EntityDuplicant.class, manager -> new RenderDuplicant(manager, 1F));
        RenderingRegistry.registerEntityRenderingHandler(EntityBottledSouls.class, manager -> new RenderBottledSouls(manager, Minecraft.getMinecraft().getRenderItem()));
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
