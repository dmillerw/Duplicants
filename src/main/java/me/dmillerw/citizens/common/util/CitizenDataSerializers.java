package me.dmillerw.citizens.common.util;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class CitizenDataSerializers {

    public static final DataSerializer<Optional<String>> JAVA_OPTIONAL_STRING = new DataSerializer<Optional<String>>() {
        @Override
        public void write(PacketBuffer buf, Optional<String> value) {
            buf.writeBoolean(value.isPresent());
            value.ifPresent((s) -> {
                buf.writeInt(s.length());
                buf.writeString(s);
            });
        }

        @Override
        public Optional<String> read(PacketBuffer buf) throws IOException {
            return !buf.readBoolean() ? Optional.empty() : Optional.of(buf.readString(buf.readInt()));
        }

        @Override
        public DataParameter<Optional<String>> createKey(int id) {
            return new DataParameter<>(id, this);
        }

        @Override
        public Optional<String> copyValue(Optional<String> value) {
            return value;
        }
    };

    public static final DataSerializer<Optional<UUID>> JAVA_OPTIONAL_UUID = new DataSerializer<Optional<UUID>>() {

        public void write(PacketBuffer buf, Optional<UUID> value) {
            buf.writeBoolean(value.isPresent());
            value.ifPresent(buf::writeUniqueId);
        }

        public Optional<UUID> read(PacketBuffer buf) throws IOException {
            return !buf.readBoolean() ? Optional.empty() : Optional.of(buf.readUniqueId());
        }

        public DataParameter<Optional<UUID>> createKey(int id) {
            return new DataParameter<>(id, this);
        }

        public Optional<UUID> copyValue(Optional<UUID> value) {
            return value;
        }
    };
}
