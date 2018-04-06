package me.dmillerw.citizens.network;

import me.dmillerw.citizens.network.packet.COpenGui;
import me.dmillerw.citizens.network.packet.SSetCitizenSettings;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import static me.dmillerw.citizens.Citizens.Info.MOD_ID;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);

    public static void initialize() {

        INSTANCE.registerMessage(COpenGui.Handler.class, COpenGui.class, 1, Side.CLIENT);

        INSTANCE.registerMessage(SSetCitizenSettings.Handler.class, SSetCitizenSettings.class, -1, Side.SERVER);
    }
}
