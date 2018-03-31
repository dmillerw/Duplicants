package me.dmillerw.duplicants.network;

import me.dmillerw.duplicants.Duplicants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;

public class ForgeGuiHandler implements IGuiHandler {

    public static void initialize() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Duplicants.instance, new ForgeGuiHandler());
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
}
