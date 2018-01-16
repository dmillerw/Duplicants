package me.dmillerw.duplicants.network.packet;

import io.netty.buffer.ByteBuf;
import me.dmillerw.duplicants.Duplicants;
import me.dmillerw.duplicants.entity.EntityDuplicant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class CSelectDuplicant implements IMessage {

    public UUID duplicantUuid;

    public CSelectDuplicant() {

    }

    public CSelectDuplicant(EntityDuplicant entityDuplicant) {
        this.duplicantUuid = entityDuplicant.getPersistentID();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(duplicantUuid.getMostSignificantBits());
        buf.writeLong(duplicantUuid.getLeastSignificantBits());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        duplicantUuid = new UUID(buf.readLong(), buf.readLong());
    }

    public static class Handler implements IMessageHandler<CSelectDuplicant, IMessage> {

        @Override
        public IMessage onMessage(CSelectDuplicant message, MessageContext ctx) {
            EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();
            World world = player.world;

            Duplicants.proxy.onDuplicantSelected(player, EntityDuplicant.getFromWorld(world, message.duplicantUuid));

            return null;
        }
    }
}
