package me.dmillerw.citizens.client.handler;

import me.dmillerw.citizens.common.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author dmillerw
 */
@Mod.EventBusSubscriber
public class ClientRegistryHandler {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        registerItemModel(ModBlocks.campfire_item);
        registerItemModel(ModBlocks.stone_seat_item);
    }

    private static void forceState(Block block, ModelResourceLocation location) {
        ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return location;
            }
        });
    }

    private static void registerItemModel(Item item) {
        ModelResourceLocation resourceLocation = new ModelResourceLocation(item.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, resourceLocation);
    }

    private static void registerItemModel(Item item, String tag, Enum<? extends IStringSerializable> variant) {
        ModelResourceLocation resourceLocation = new ModelResourceLocation(item.getRegistryName(), tag + "=" + ((IStringSerializable)variant).getName());
        ModelLoader.setCustomModelResourceLocation(item, variant.ordinal(), resourceLocation);
    }
}