package me.dmillerw.citizens.client.gui;

import me.dmillerw.citizens.Citizens;
import me.dmillerw.citizens.entity.EntityDuplicant;
import me.dmillerw.citizens.network.PacketHandler;
import me.dmillerw.citizens.network.packet.SSetCitizenSettings;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import java.io.IOException;

public class GuiDuplicantVisualSettings extends GuiScreen {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Citizens.Info.MOD_ID, "textures/gui/settings.png");

    private int guiLeft;
    private int guiTop;

    private int xSize = 176;
    private int ySize = 166;

    private final EntityDuplicant entityDuplicant;

    private GuiButtonExt buttonShowNameplate;

    private GuiTextField nameField;
    private GuiTextField skinField;

    private boolean showNameplate;

    public GuiDuplicantVisualSettings(EntityDuplicant entityDuplicant) {
        this.entityDuplicant = entityDuplicant;
        this.showNameplate = entityDuplicant.getShowNameplate();
    }

    @Override
    public void initGui() {
        super.initGui();

        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

        nameField = new GuiTextField(0, fontRenderer, guiLeft + 7, guiTop + 21, 110, 16);
        nameField.setText(entityDuplicant.getName());
        skinField = new GuiTextField(1, fontRenderer, guiLeft + 7, guiTop + 89, 110, 16);
        skinField.setText(entityDuplicant.getSkin());

        addButton(buttonShowNameplate = new GuiButtonExt(0, guiLeft + 7, guiTop + 55, 110, 16, showNameplate ? "TRUE" : "FALSE"));
        addButton(new GuiButtonExt(1, guiLeft + 111, guiTop + 143, 27, 16, "Cancel"));
        addButton(new GuiButtonExt(2, guiLeft + 142, guiTop + 143, 27, 16, "Ok"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);

        fontRenderer.drawString("Name:", guiLeft + 7, guiTop + 10, 4210752);
        fontRenderer.drawString("Show Nameplate:", guiLeft + 7, guiTop + 44, 4210752);
        fontRenderer.drawString("Skin:", guiLeft + 7, guiTop + 78, 4210752);

        nameField.drawTextBox();
        skinField.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (nameField.mouseClicked(mouseX, mouseY, mouseButton)) return;
        if (skinField.mouseClicked(mouseX, mouseY, mouseButton)) return;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        if (nameField.textboxKeyTyped(typedChar, keyCode)) return;
        if (skinField.textboxKeyTyped(typedChar, keyCode)) return;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            showNameplate = !showNameplate;
            buttonShowNameplate.displayString = showNameplate ? "TRUE" : "FALSE";
        } else if (button.id == 1) {
            this.mc.displayGuiScreen(null);
            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        } else if (button.id == 2) {
            this.mc.displayGuiScreen(null);
            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }

            SSetCitizenSettings packet = new SSetCitizenSettings(entityDuplicant.getEntityId(), nameField.getText(), skinField.getText(), showNameplate);
            PacketHandler.INSTANCE.sendToServer(packet);
        }
    }
}
