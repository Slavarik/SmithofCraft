package com.lurk.lurkmod.item;

import com.lurk.lurkmod.client.gui.ModSmithingGUI;
import com.lurk.lurkmod.common.menus.ModSmithingMenu;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public abstract class IModTool extends Item {

    public enum ToolType {HAMMER, CHISEL, FILE};

    public IModTool(Properties pProperties) {
        super(pProperties);
    }

    public abstract int getTier();

    public abstract ToolType getType();
}
