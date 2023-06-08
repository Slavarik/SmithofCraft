package com.lurk.lurkmod.client.gui;

import com.lurk.lurkmod.LurkMod;
import com.lurk.lurkmod.common.menus.ModFurnaceMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.awt.*;

public class ModFurnaceGUI extends AbstractContainerScreen<ModFurnaceMenu> {
    ModFurnaceMenu menu;
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(LurkMod.MODID, "textures/gui/furnace_gui.png");

    public ModFurnaceGUI(ModFurnaceMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.menu = pMenu;
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        float dt = menu.blockEntity.temperature / 300f;

        blit(pPoseStack,leftPos + 27,topPos + 63,22,169, (int) (122f * dt),8);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}