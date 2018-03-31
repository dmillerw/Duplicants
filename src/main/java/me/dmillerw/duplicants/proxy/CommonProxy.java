package me.dmillerw.duplicants.proxy;

import me.dmillerw.duplicants.Duplicants;
import me.dmillerw.duplicants.entity.EntityBottledSouls;
import me.dmillerw.duplicants.entity.EntityDuplicant;
import me.dmillerw.duplicants.item.ModItems;
import me.dmillerw.duplicants.network.ForgeGuiHandler;
import me.dmillerw.duplicants.network.GuiHandler;
import me.dmillerw.duplicants.network.PacketHandler;
import me.dmillerw.duplicants.proxy.handlers.OnFmlEvent;
import me.dmillerw.duplicants.proxy.handlers.OnOpenGui;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class CommonProxy implements OnFmlEvent, OnOpenGui {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        PacketHandler.initialize();

        GuiHandler.initialize();
        ForgeGuiHandler.initialize();

        EntityRegistry.registerModEntity(
                new ResourceLocation("duplicants:duplicant"),
                EntityDuplicant.class,
                "duplicant",
                0,
                Duplicants.instance,
                80,
                3,
                true);

        EntityRegistry.registerModEntity(
                new ResourceLocation("duplicants:bottledSouls"),
                EntityBottledSouls.class,
                "bottledSouls",
                1,
                Duplicants.instance,
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

    @Override
    public Gui getClientGui(EntityPlayer entityPlayer, GuiHandler.Target target) {
        return null; // NO-OP
    }

    @Override
    public Container getServerContainer(EntityPlayer entityPlayer, GuiHandler.Target target) {
        return null;
    }
}
