package me.dmillerw.citizens.proxy;

import me.dmillerw.citizens.Citizens;
import me.dmillerw.citizens.entity.EntityBottledSouls;
import me.dmillerw.citizens.entity.EntityDuplicant;
import me.dmillerw.citizens.item.ModItems;
import me.dmillerw.citizens.network.GuiHandler;
import me.dmillerw.citizens.network.PacketHandler;
import me.dmillerw.citizens.proxy.handlers.OnFmlEvent;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
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
                new ResourceLocation("citizens:duplicant"),
                EntityDuplicant.class,
                "duplicant",
                0,
                Citizens.instance,
                80,
                3,
                true);

        EntityRegistry.registerModEntity(
                new ResourceLocation("citizens:bottledSouls"),
                EntityBottledSouls.class,
                "bottledSouls",
                1,
                Citizens.instance,
                80,
                3,
                true);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        final ItemStack waterBottle = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER);
        BrewingRecipeRegistry.addRecipe(waterBottle, new ItemStack(Items.CARROT_ON_A_STICK), new ItemStack(ModItems.bottledSouls));
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }
}
