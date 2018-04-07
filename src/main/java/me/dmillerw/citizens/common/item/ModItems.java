package me.dmillerw.citizens.common.item;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static me.dmillerw.citizens.Citizens.Info.MOD_ID;

/**
 * @author dmillerw
 */
@GameRegistry.ObjectHolder(MOD_ID)
public class ModItems {

    @Mod.EventBusSubscriber
    public static class Loader {

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(

            );
        }
    }
}