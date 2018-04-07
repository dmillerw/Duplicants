package me.dmillerw.citizens.common.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static me.dmillerw.citizens.Citizens.Info.MOD_ID;

@GameRegistry.ObjectHolder(MOD_ID)
public class ModBlocks {

    public static final BlockCampfire campfire = null;
    @GameRegistry.ObjectHolder(MOD_ID + ":campfire")
    public static final ItemBlock campfire_item = null;

    public static final BlockStoneSeat stone_seat = null;
    @GameRegistry.ObjectHolder(MOD_ID + ":stone_seat")
    public static final ItemBlock stone_seat_item = null;

    @Mod.EventBusSubscriber
    public static class Loader {

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            event.getRegistry().registerAll(
                    new BlockCampfire(),
                    new BlockStoneSeat()
            );
        }

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(
                    itemBlock(campfire),
                    itemBlock(stone_seat)
            );
        }
    }

    private static ItemBlock itemBlock(Block block) {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return itemBlock;
    }
}
