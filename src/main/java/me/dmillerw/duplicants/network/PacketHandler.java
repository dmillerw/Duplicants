package me.dmillerw.duplicants.network;

import me.dmillerw.duplicants.network.packet.CSelectDuplicant;
import me.dmillerw.duplicants.network.packet.SSelectBlock;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import static me.dmillerw.duplicants.Duplicants.Info.MOD_ID;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);

    public static void initialize() {

        INSTANCE.registerMessage(CSelectDuplicant.Handler.class, CSelectDuplicant.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(SSelectBlock.Handler.class, SSelectBlock.class, -1, Side.SERVER);
    }
}
