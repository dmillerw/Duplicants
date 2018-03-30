package me.dmillerw.duplicants.item;

import com.sun.istack.internal.NotNull;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;

import static me.dmillerw.duplicants.Duplicants.Info.MOD_ID;

/**
 * @author dmillerw
 */
@GameRegistry.ObjectHolder(MOD_ID)
public class ModItems {

    public static final ItemSelector selector = null;
    public static final ItemBottledSouls bottledSouls = null;

    @Mod.EventBusSubscriber
    public static class Loader {

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(
                    new ItemSelector(),
                    new ItemBottledSouls()
            );
        }
    }
}