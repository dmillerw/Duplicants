package me.dmillerw.duplicants.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import me.dmillerw.duplicants.entity.EntityDuplicant;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import static net.minecraftforge.fml.common.network.ByteBufUtils.readUTF8String;
import static net.minecraftforge.fml.common.network.ByteBufUtils.writeUTF8String;

public class SSetDuplicantSettings implements IMessage {

    private int entityId;

    private String name;
    private String skin;
    private boolean showNameplate;

    public SSetDuplicantSettings() {}

    public SSetDuplicantSettings(int entityId, String name, String skin, boolean showNameplate) {
        this.entityId = entityId;
        this.name = name;
        this.skin = skin;
        this.showNameplate = showNameplate;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityId);
        writeUTF8String(buf, name);
        writeUTF8String(buf, skin);
        buf.writeBoolean(showNameplate);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.name = readUTF8String(buf);
        this.skin = readUTF8String(buf);
        this.showNameplate = buf.readBoolean();
    }

    public static class Handler implements IMessageHandler<SSetDuplicantSettings, IMessage> {

        @Override
        public IMessage onMessage(SSetDuplicantSettings message, MessageContext ctx) {
            World world = ctx.getServerHandler().player.world;
            Entity entity = world.getEntityByID(message.entityId);
            if (entity != null && entity instanceof EntityDuplicant) {
                ((EntityDuplicant) entity).setName(message.name);
                ((EntityDuplicant) entity).setShowNameplate(message.showNameplate);
                ((EntityDuplicant) entity).setSkin(message.skin);
            }

            return null;
        }
    }
}
