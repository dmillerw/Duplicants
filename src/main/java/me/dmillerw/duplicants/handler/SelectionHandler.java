package me.dmillerw.duplicants.handler;

import com.google.common.collect.Maps;
import me.dmillerw.duplicants.entity.EntityDuplicant;
import me.dmillerw.duplicants.network.packet.SSelectBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.UUID;

public enum SelectionHandler {
    INSTANCE;

    // Maintains a mapping of a players UUID, to the duplicant they currently have selected. Value can be null if
    // no duplicant is selected
    public Map<UUID, UUID> selectionMap = Maps.newHashMap();

    private UUID getSelected(EntityPlayer entityPlayer) {
        return selectionMap.get(entityPlayer.getGameProfile().getId());
    }

    public void onDuplicantSelected(@Nonnull EntityPlayer entityPlayer, EntityDuplicant entityDuplicant) {
        if (entityDuplicant != null) {
            selectionMap.put(entityPlayer.getGameProfile().getId(), entityDuplicant.getPersistentID());
        } else {
            selectionMap.remove(entityPlayer.getGameProfile().getId());
        }
    }

    public void onBlockSelected(EntityPlayer entityPlayer, SSelectBlock.Type type, BlockPos blockPos) {
        EntityDuplicant selected = EntityDuplicant.getFromWorld(entityPlayer.world, getSelected(entityPlayer));
        if (selected != null) {
            selected.getNavigator().tryMoveToXYZ(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0.5D);
        }
    }
}
