package me.dmillerw.citizens.client.gui.modal;

import me.dmillerw.citizens.Citizens;
import me.dmillerw.citizens.client.gui.element.LayoutManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class GuiModal extends GuiScreen {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Citizens.Info.MOD_ID, "textures/gui/expandable.png");

    private LayoutManager layoutManager = new LayoutManager();

    private int guiLeft;
    private int guiTop;

    private int xSize = 176;
    private int ySize = 166;

    @Override
    public void initGui() {
        super.initGui();

        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_TEXTURE);

        final int cornerSize = 5;
        final int xDistance = xSize - (cornerSize * 2);
        final int yDistance = ySize - (cornerSize * 2);

        // Corners
        // Top-Left
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, 5, 5, 5, 5);
        // Top-Right
        drawTexturedModalRect(guiLeft + xDistance, guiTop, 6, 0, 5, 5, 5, 5);
        //Bottom-Left
        drawTexturedModalRect(guiLeft, guiTop + yDistance, 0, 6, 5, 5, 5, 5);
        // Bottom-Right
        drawTexturedModalRect(guiLeft + xDistance, guiTop + yDistance, 6, 6, 5, 5, 5, 5);

        // Connectors
        // Top-Left -> Top-Right
        drawTexturedModalRect(guiLeft + cornerSize, guiTop, 6, 0, xDistance - cornerSize, 5, 1, 5);
        // Bottom-Left -> Bottom-Right
        drawTexturedModalRect(guiLeft + cornerSize, guiTop + yDistance, 6, 6, xDistance - cornerSize, 5, 1, 5);
        //Top-Left -> Bottom-Left
        drawTexturedModalRect(guiLeft, guiTop + cornerSize, 0, 6, cornerSize, yDistance - cornerSize, 5, 1);
        // Top-Right -> Bottom-Right
        drawTexturedModalRect(guiLeft + xDistance, guiTop + cornerSize, 6, 6, cornerSize, yDistance - cornerSize, 5, 1);

        // Center
        drawTexturedModalRect(guiLeft + cornerSize, guiTop + cornerSize, 5, 5, xDistance - cornerSize, yDistance - cornerSize, 1, 1);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, int textureWidth, int textureHeight) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double) (x), (double) (y + height), (double) this.zLevel).tex((double) ((float) (textureX) * 0.00390625F), (double) ((float) (textureY + textureHeight) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y + height), (double) this.zLevel).tex((double) ((float) (textureX + textureWidth) * 0.00390625F), (double) ((float) (textureY + textureHeight) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y), (double) this.zLevel).tex((double) ((float) (textureX + textureWidth) * 0.00390625F), (double) ((float) (textureY) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double) (x), (double) (y), (double) this.zLevel).tex((double) ((float) (textureX) * 0.00390625F), (double) ((float) (textureY) * 0.00390625F)).endVertex();
        tessellator.draw();
    }
}
