package me.dmillerw.citizens.network;

import me.dmillerw.citizens.client.gui.GuiCitizenInventory;
import me.dmillerw.citizens.client.gui.GuiDuplicantVisualSettings;
import me.dmillerw.citizens.entity.EntityDuplicant;
import me.dmillerw.citizens.inventory.ContainerCitizenInventory;
import me.dmillerw.citizens.network.packet.COpenGui;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.EnumMap;

public class GuiHandler {

    public static enum TargetType {

        BLOCK, ENTITY, OTHER
    }

    public static class Target {

        public TargetType targetType;

        public BlockPos blockPos = BlockPos.ORIGIN;
        public int rawEntityId = -1;

        public Target() {
        }

        public Target(BlockPos blockPos) {
            this.targetType = TargetType.BLOCK;
            this.blockPos = blockPos;
        }

        public Target(int entityId) {
            this.targetType = TargetType.ENTITY;
            this.rawEntityId = entityId;
        }

        public Entity getEntity(World world) {
            return world.getEntityByID(this.rawEntityId);
        }
    }

    public static enum GuiKey {

        DUPLICANT_INVENTORY(true),
        DUPLICANT_SETTINGS(false);

        public final boolean hasContainer;

        private GuiKey(boolean hasContainer) {
            this.hasContainer = hasContainer;
        }
    }

    public static interface ClientGuiHandler {

        public Gui getGui(EntityPlayer entityPlayer, Target target);
    }

    public static interface ServerGuiHandler {

        public Container getContainer(EntityPlayer entityPlayer, Target target);
    }

    private static EnumMap<GuiKey, ClientGuiHandler> clientGuiHandlers = new EnumMap<>(GuiKey.class);
    private static EnumMap<GuiKey, ServerGuiHandler> serverGuiHandlers = new EnumMap<>(GuiKey.class);

    public static void initialize() {
        clientGuiHandlers.put(GuiKey.DUPLICANT_INVENTORY, (p, t) -> new GuiCitizenInventory(p, (EntityDuplicant) t.getEntity(p.world)));
        clientGuiHandlers.put(GuiKey.DUPLICANT_SETTINGS, (p, t) -> new GuiDuplicantVisualSettings((EntityDuplicant) t.getEntity(p.world)));

        serverGuiHandlers.put(GuiKey.DUPLICANT_INVENTORY, (p, t) -> new ContainerCitizenInventory(p, (EntityDuplicant) t.getEntity(p.world)));
    }

    public static void openGui(GuiKey guiKey, EntityPlayer entityPlayer, Target target) {
        if (!entityPlayer.world.isRemote) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) entityPlayer;

            if (guiKey.hasContainer) {
                Container container = GuiHandler.getContainer(guiKey, entityPlayer, target);

                if (container != null) {
                    entityPlayerMP.getNextWindowId();
                    entityPlayerMP.closeContainer();

                    int windowId = entityPlayerMP.currentWindowId;

                    COpenGui packet = new COpenGui(guiKey, target, windowId);
                    PacketHandler.INSTANCE.sendTo(packet, entityPlayerMP);

                    entityPlayerMP.openContainer = container;
                    entityPlayerMP.openContainer.windowId = windowId;
                    entityPlayerMP.openContainer.addListener(entityPlayerMP);

                    MinecraftForge.EVENT_BUS.post(new PlayerContainerEvent.Open(entityPlayer, entityPlayer.openContainer));
                }
            } else {
                entityPlayerMP.getNextWindowId();
                entityPlayerMP.closeContainer();

                int windowId = entityPlayerMP.currentWindowId;

                COpenGui packet = new COpenGui(guiKey, target, windowId);
                PacketHandler.INSTANCE.sendTo(packet, entityPlayerMP);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static Gui getGui(GuiKey key, EntityPlayer player, Target target) {
        if (clientGuiHandlers.containsKey(key))
            return clientGuiHandlers.get(key).getGui(player, target);
        else
            return null;
    }

    public static Container getContainer(GuiKey key, EntityPlayer player, Target target) {
        if (serverGuiHandlers.containsKey(key))
            return serverGuiHandlers.get(key).getContainer(player, target);
        else
            return null;
    }
}
