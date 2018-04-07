package me.dmillerw.citizens.common.entity;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import me.dmillerw.citizens.common.colony.Colony;
import me.dmillerw.citizens.common.colony.ColonyManager;
import me.dmillerw.citizens.common.inventory.InventoryCitizen;
import me.dmillerw.citizens.common.network.GuiHandler;
import me.dmillerw.citizens.common.util.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Optional;
import java.util.UUID;

import static me.dmillerw.citizens.common.util.CitizenDataSerializers.JAVA_OPTIONAL_STRING;
import static me.dmillerw.citizens.common.util.CitizenDataSerializers.JAVA_OPTIONAL_UUID;
import static net.minecraft.network.datasync.DataSerializers.BOOLEAN;
import static net.minecraft.network.datasync.DataSerializers.VARINT;

public class EntityCitizen extends EntityLiving {

    // Core
    private static final DataParameter<Integer> ID = EntityDataManager.createKey(EntityCitizen.class, VARINT);
    private static final DataParameter<Optional<UUID>> OWNER = EntityDataManager.createKey(EntityCitizen.class, JAVA_OPTIONAL_UUID);

    // Visuals
    private static final DataParameter<Optional<String>> NAME = EntityDataManager.createKey(EntityCitizen.class, JAVA_OPTIONAL_STRING);
    private static final DataParameter<Optional<String>> SKIN = EntityDataManager.createKey(EntityCitizen.class, JAVA_OPTIONAL_STRING);
    private static final DataParameter<Boolean> SHOW_NAMEPLATE = EntityDataManager.createKey(EntityCitizen.class, BOOLEAN);

    public final InventoryCitizen inventory = new InventoryCitizen(this);

    private boolean registeredWithColony = false;

    /* CLIENT STUFF */
    public String clientSkinCached = "";
    public GameProfile clientProfile = null;

    public EntityCitizen(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {
        super.entityInit();

        this.dataManager.register(ID, -1);
        this.dataManager.register(OWNER, Optional.empty());

        this.dataManager.register(NAME, Optional.empty());
        this.dataManager.register(SKIN, Optional.empty());
        this.dataManager.register(SHOW_NAMEPLATE, false);
    }

    public int getId() {
        return this.dataManager.get(ID);
    }

    public void setId(int id) {
        this.dataManager.set(ID, id);
    }

    public Optional<UUID> getOwner() {
        return this.dataManager.get(OWNER);
    }

    public void setOwner(UUID owner) {
        this.dataManager.set(OWNER, Optional.ofNullable(owner));
    }

    public Optional<String> getCitizenName() {
        return this.dataManager.get(NAME);
    }

    public void setCitizenName(String name) {
        this.dataManager.set(NAME, Optional.ofNullable(name));
    }

    public Optional<String> getSkin() {
        return this.dataManager.get(SKIN);
    }

    public void setSkin(String skin) {
        this.dataManager.set(SKIN, Optional.ofNullable(skin));
    }

    public boolean getShowNameplate() {
        return this.dataManager.get(SHOW_NAMEPLATE);
    }

    public void setShowNameplate(boolean showNameplate) {
        this.dataManager.set(SHOW_NAMEPLATE, showNameplate);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString(getCitizenName().isPresent() ? getCitizenName().get() : "");
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();

        if (!world.isRemote) {
            if (!registeredWithColony && getId() >= 0 && getOwner().isPresent()) {
                Colony colony = ColonyManager.getInstance().getColony(getOwner().get());
                colony.addCitizen(this);

                registeredWithColony = true;
            }
        }

        if (world.isRemote) {
            if (getSkin().isPresent()) {
                String skin = getSkin().get();
                if (!skin.equals(clientSkinCached)) {
                    clientSkinCached = skin;
                    clientProfile = updateGameprofile(new GameProfile(null, skin));
                }
            } else {
                clientSkinCached = "";
                clientProfile = null;
            }
        }

        EntityPlayer closest = world.getClosestPlayerToEntity(this, 16D);
        if (closest == null) return;

        getLookHelper().setLookPosition(closest.posX, closest.posY + (double) closest.getEyeHeight(), closest.posZ, 10.0F, (float) getVerticalFaceSpeed());
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setInteger("Id", getId());

        if (getOwner().isPresent()) {
            UUID owner = getOwner().get();
            compound.setLong("OwnerMostBits", owner.getMostSignificantBits());
            compound.setLong("OwnerLeastBits", owner.getLeastSignificantBits());
        }

        compound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagCompound()));

        if (getCitizenName().isPresent()) compound.setString("Name", getCitizenName().get());
        if (getSkin().isPresent()) compound.setString("Skin", getSkin().get());
        compound.setBoolean("ShowNameplate", getShowNameplate());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        setId(compound.getInteger("Id"));

        if (compound.hasKey("OwnerMostBits") && compound.hasKey("OwnerLeastBits")) {
            setOwner(new UUID(compound.getLong("OwnerMostBits"), compound.getLong("OwnerLeastBits")));
        } else {
            setOwner(null);
        }

        this.inventory.readFromNBT(compound.getCompoundTag("Inventory"));

        if (compound.hasKey("Name"))
            setCitizenName(compound.getString("Name"));
        else
            setCitizenName(null);

        if (compound.hasKey("ShowNameplate")) setShowNameplate(compound.getBoolean("ShowNameplate"));

        if (compound.hasKey("Skin"))
            setSkin(compound.getString("Skin"));
        else
            setSkin(null);
    }


    @Override
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
        return super.getItemStackFromSlot(slotIn);
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (!player.world.isRemote) {
            GuiHandler.openGui(GuiHandler.GuiKey.DUPLICANT_INVENTORY, player, new GuiHandler.Target(this.getEntityId()));
        }
        return true;
    }

    @Override
    protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier) {
        InventoryUtil.dropInventory(this, this.inventory);
        InventoryUtil.dropInventory(this, (NonNullList<ItemStack>) this.getArmorInventoryList());
        InventoryUtil.dropInventory(this, (NonNullList<ItemStack>) this.getHeldEquipment());
    }

    public static GameProfile updateGameprofile(GameProfile input) {
        final MinecraftSessionService sessionService = Minecraft.getMinecraft().getSessionService();
        final PlayerProfileCache profileCache = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerProfileCache();

        if (input != null && !StringUtils.isNullOrEmpty(input.getName())) {
            if (input.isComplete() && input.getProperties().containsKey("textures")) {
                return input;
            } else if (profileCache != null && sessionService != null) {
                GameProfile gameprofile = profileCache.getGameProfileForUsername(input.getName());

                if (gameprofile == null) {
                    return input;
                } else {
                    Property property = (Property) Iterables.getFirst(gameprofile.getProperties().get("textures"), (Object) null);

                    if (property == null) {
                        gameprofile = sessionService.fillProfileProperties(gameprofile, true);
                    }

                    return gameprofile;
                }
            } else {
                return input;
            }
        } else {
            return input;
        }
    }
}
