package me.dmillerw.duplicants.ai.inventory;

import com.google.common.collect.Maps;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.WorldCapabilityData;

import java.util.Map;

public class InventoryWorldData extends WorldSavedData {

    private static final String KEY = "duplicants:inventory_blocks";

    private final Map<BlockPos, Consumer> consumers = Maps.newHashMap();
    private final Map<BlockPos, Provider> providers = Maps.newHashMap();
    private final Map<BlockPos, Producer> producers = Maps.newHashMap();
    private final Map<BlockPos, Storage> storage = Maps.newHashMap();

    public InventoryWorldData() {
        super(KEY);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {

    }
}
