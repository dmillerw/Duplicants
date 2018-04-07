package me.dmillerw.citizens.common.colony;

import com.google.common.collect.Maps;
import me.dmillerw.citizens.Citizens;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Map;
import java.util.UUID;

public class ColonyManager extends WorldSavedData {

    private static final String KEY = Citizens.Info.MOD_ID + ":colony_manager";
    private static ColonyManager instance;

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        if (instance == null) {
            ColonyManager manager = (ColonyManager) event.getWorld().getMapStorage().getOrLoadData(ColonyManager.class, KEY);
            if (manager == null) {
                manager = new ColonyManager();
                event.getWorld().getMapStorage().setData(KEY, manager);
            }

            ColonyManager.instance = manager;
            ColonyManager.instance.initialize();
        }
    }

    @SubscribeEvent
    public static void onWorldSave(WorldEvent.Save event) {
        if (instance != null) {
            if (instance.pollDirty()) {
                event.getWorld().getMapStorage().setData(KEY, instance);
            }
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) return;
        ColonyManager.getInstance().tick();
    }

    public static ColonyManager getInstance() {
        return instance;
    }

    private Map<UUID, Colony> colonies = Maps.newHashMap();
    private boolean isDirty = false;

    public ColonyManager() {
        super(KEY);
    }

    public void initialize() {
        colonies.values().forEach((c) -> c.initialize(this));
    }

    public void markDirty() {
        this.isDirty = true;
    }

    public boolean pollDirty() {
        boolean dirty = isDirty;
        isDirty = false;
        return dirty;
    }

    public Colony getColony(UUID uuid) {
        Colony colony = colonies.get(uuid);
        if (colony == null) {
            colony = new Colony(uuid);
            colonies.put(uuid, colony);
        }

        return colony;
    }

    public Colony getColony(EntityPlayer entityPlayer) {
        Colony colony = colonies.get(entityPlayer.getGameProfile().getId());
        if (colony == null) {
            colony = new Colony(entityPlayer.getGameProfile().getId());
            colonies.put(entityPlayer.getGameProfile().getId(), colony);
        }

        return colony;
    }

    public void tick() {
        colonies.values().forEach(Colony::tick);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();
        colonies.forEach((u, c) -> {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setLong("mostBits", u.getMostSignificantBits());
            tag.setLong("leastBits", u.getLeastSignificantBits());

            c.writeToNBT(tag);

            list.appendTag(tag);
        });
        compound.setTag("colonies", list);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        colonies.clear();

        NBTTagList list = compound.getTagList("colonies", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            UUID uuid = new UUID(tag.getLong("mostBits"), tag.getLong("leastBits"));
            Colony colony = new Colony(uuid);
            colony.readFromNBT(tag);

            colonies.put(uuid, colony);
        }
    }
}
