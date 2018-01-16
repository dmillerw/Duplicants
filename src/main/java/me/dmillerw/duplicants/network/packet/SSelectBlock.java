package me.dmillerw.duplicants.network.packet;

import io.netty.buffer.ByteBuf;
import me.dmillerw.duplicants.handler.SelectionHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SSelectBlock implements IMessage {

    public static enum Type {
        RIGHT_CLICK, LEFT_CLICK;
    }

    public Type type;
    public BlockPos selectedBlockPos;

    public SSelectBlock() {

    }

    public SSelectBlock(Type type, BlockPos selectedBlockPos) {
        this.type = type;
        this.selectedBlockPos = selectedBlockPos;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(type.ordinal());
        buf.writeLong(selectedBlockPos.toLong());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        type = Type.values()[buf.readByte()];
        selectedBlockPos = BlockPos.fromLong(buf.readLong());
    }

    public static class Handler implements IMessageHandler<SSelectBlock, IMessage> {

        @Override
        public IMessage onMessage(SSelectBlock message, MessageContext ctx) {
            SelectionHandler.INSTANCE.onBlockSelected(ctx.getServerHandler().player, message.type, message.selectedBlockPos);
            return null;
        }
    }
}
