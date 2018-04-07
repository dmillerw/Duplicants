package me.dmillerw.citizens.common.network.packet;

import io.netty.buffer.ByteBuf;
import me.dmillerw.citizens.common.network.GuiHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class COpenGui implements IMessage {

    private GuiHandler.GuiKey guiKey;
    private GuiHandler.Target target;
    private int windowId;

    public COpenGui() {}

    public COpenGui(GuiHandler.GuiKey guiKey, GuiHandler.Target target, int windowId) {
        this.guiKey = guiKey;
        this.target = target;
        this.windowId = windowId;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte((byte)guiKey.ordinal());
        buf.writeByte((byte)target.targetType.ordinal());
        buf.writeLong(target.blockPos.toLong());
        buf.writeInt(target.rawEntityId);
        buf.writeInt(windowId);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        guiKey = GuiHandler.GuiKey.values()[buf.readByte()];
        target = new GuiHandler.Target();
        target.targetType = GuiHandler.TargetType.values()[buf.readByte()];
        target.blockPos = BlockPos.fromLong(buf.readLong());
        target.rawEntityId = buf.readInt();
        windowId = buf.readInt();
    }

    public static class Handler implements IMessageHandler<COpenGui, IMessage> {

        @Override
        public IMessage onMessage(COpenGui message, MessageContext ctx) {
            EntityPlayer entityPlayer = FMLClientHandler.instance().getClient().player;
            Gui gui = GuiHandler.getGui(message.guiKey, entityPlayer, message.target);

            if (gui != null) {
                FMLClientHandler.instance().showGuiScreen(gui);
                entityPlayer.openContainer.windowId = message.windowId;
            }

            return null;
        }
    }
}
