package me.dmillerw.duplicants.network;

import me.dmillerw.duplicants.handler.SelectionHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class NetworkHandler {

    @SubscribeEvent
    public void onPlayerLogoff(PlayerEvent.PlayerLoggedOutEvent event) {
        SelectionHandler.INSTANCE.onDuplicantSelected(event.player, null);
    }
}
