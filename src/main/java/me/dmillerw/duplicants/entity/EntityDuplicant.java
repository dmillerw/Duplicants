package me.dmillerw.duplicants.entity;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import me.dmillerw.duplicants.inventory.InventoryDuplicant;
import me.dmillerw.duplicants.network.GuiHandler;
import me.dmillerw.duplicants.util.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class EntityDuplicant extends EntityLiving {

    private static final DataParameter<String> NAME = EntityDataManager.createKey(EntityDuplicant.class, DataSerializers.STRING);
    private static final DataParameter<String> SKIN = EntityDataManager.createKey(EntityDuplicant.class, DataSerializers.STRING);
    private static final DataParameter<Boolean> SHOW_NAMEPLATE = EntityDataManager.createKey(EntityDuplicant.class, DataSerializers.BOOLEAN);

    public final InventoryDuplicant inventory = new InventoryDuplicant(this);

    public String clientSkinCached = "";
    public GameProfile clientProfile = null;

    public EntityDuplicant(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {
        super.entityInit();

        this.dataManager.register(NAME, "");
        this.dataManager.register(SKIN, "STEVE");
        this.dataManager.register(SHOW_NAMEPLATE, false);
    }

    public String getName() {
        return this.dataManager.get(NAME);
    }

    public void setName(String name) {
        this.dataManager.set(NAME, name);
    }

    public String getSkin() {
        return this.dataManager.get(SKIN);
    }

    public void setSkin(String skin) {
        this.dataManager.set(SKIN, skin);
    }

    public boolean getShowNameplate() {
        return this.dataManager.get(SHOW_NAMEPLATE);
    }

    public void setShowNameplate(boolean showNameplate) {
        this.dataManager.set(SHOW_NAMEPLATE, showNameplate);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString(getName());
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();

        if (world.isRemote) {
            String skin = getSkin();
            if (!skin.equals(cachedUniqueIdString)) {
                cachedUniqueIdString = skin;
                clientProfile = updateGameprofile(new GameProfile(null, skin));
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

        compound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagCompound()));

        if (!getName().isEmpty()) compound.setString("Name", getName());
        compound.setBoolean("ShowNameplate", getShowNameplate());
        if (!getSkin().isEmpty()) compound.setString("Skin", getSkin());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        this.inventory.readFromNBT(compound.getCompoundTag("Inventory"));

        if (compound.hasKey("Name"))
            setName(compound.getString("Name"));
        else
            setName("");

        if (compound.hasKey("ShowNameplate")) setShowNameplate(compound.getBoolean("ShowNameplate"));

        if (compound.hasKey("Skin"))
            setSkin(compound.getString("Skin"));
        else
            setSkin("");
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
