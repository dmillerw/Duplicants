package me.dmillerw.citizens.common.colony;

import com.google.common.collect.Maps;
import me.dmillerw.citizens.common.entity.EntityCitizen;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class Colony {

    private ColonyManager colonyManager;
    private UUID owner;

    private Map<Integer, EntityCitizen> loadedCitizens = Maps.newHashMap();

    private int nextCitizenId = 0;
    public int nextCitizenId() {
        int id = nextCitizenId;
        nextCitizenId++;
        return id;
    }

    public Colony() {}

    public Colony(UUID uuid) {
        this.owner = uuid;
    }

    public void initialize(ColonyManager colonyManager) {
        this.colonyManager = colonyManager;
    }

    public void addCitizen(EntityCitizen citizen) {
        citizen.setId(nextCitizenId());
        this.loadedCitizens.put(citizen.getId(), citizen);
    }

    public boolean isOurCitizen(EntityCitizen citizen) {
        return loadedCitizens.containsKey(citizen.getId());
    }

    public void tick() {
        Iterator<Map.Entry<Integer, EntityCitizen>> iterator = loadedCitizens.entrySet().iterator();
        while (iterator.hasNext()) {
            EntityCitizen citizen = iterator.next().getValue();
            if (citizen.isDead) iterator.remove();
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setLong("ownerMostBits", owner.getMostSignificantBits());
        compound.setLong("ownerLeastBits", owner.getLeastSignificantBits());
        compound.setInteger("nextCitizenId", nextCitizenId);

        return compound;
    }

    public void readFromNBT(NBTTagCompound compound) {
        owner = new UUID(compound.getLong("ownerMostBits"), compound.getLong("ownerLeastBits"));
        nextCitizenId = compound.getInteger("nextCitizenId");
    }
}
